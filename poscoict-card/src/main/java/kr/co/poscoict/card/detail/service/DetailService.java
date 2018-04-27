package kr.co.poscoict.card.detail.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.poscoict.card.common.except.CardBusinessException;
import kr.co.poscoict.card.common.model.Search;
import kr.co.poscoict.card.common.util.MessageSourceUtil;
import kr.co.poscoict.card.detail.mapper.DetailMapper;
import kr.co.poscoict.card.detail.model.Account;
import kr.co.poscoict.card.detail.model.Approve;
import kr.co.poscoict.card.detail.model.Dept;
import kr.co.poscoict.card.detail.model.DetailSearch;
import kr.co.poscoict.card.detail.model.Invoice;
import kr.co.poscoict.card.detail.model.Org;
import kr.co.poscoict.card.detail.model.Project;
import kr.co.poscoict.card.detail.model.RequestAdjust;
import kr.co.poscoict.card.detail.model.Task;
import kr.co.poscoict.card.detail.model.Type;

/**
 * 정산 상세 service
 * @author Sangjun, Park
 *
 */
@Service
public class DetailService {
	private final String COMMA_REGEXP = "\\s*,\\s*";
	
	@Autowired
	private DetailMapper detailMapper;
	
	@Autowired
	private MessageSourceUtil messageSource;
	
	/**
	 * 부서 검색
	 * @param search
	 * @return
	 */
	public List<Dept> getDetpList(Search search) {
		return this.detailMapper.selectDeptList(search);
	}
	
	/**
	 * 승인권자 검색
	 * @param search
	 * @return
	 */
	public List<Approve> getApproveList(Search search) {
		return this.detailMapper.selectApproveList(search);
	}
	
	/**
	 * 계정 검색
	 * @param search
	 * @return
	 */
	public List<Account> getAccountLIst(Search search) {
		return this.detailMapper.selectAccountList(search);
	}
	
	/**
	 * 프로젝트 검색
	 * @param search
	 * @return
	 */
	public List<Project> getProjectList(Search search) {
		List<Project> list = this.detailMapper.selectProjectList(search);
		for(Project obj : list) {
			if(StringUtils.equals(obj.getProjectStatusCode(), "1000")) {
				obj.setProjectStatusCodeNm(this.messageSource.getMessage("text.project.status.1000"));			// 계약
			} else if(StringUtils.equals(obj.getProjectStatusCode(), "UNAPPROVED")) {
				obj.setProjectStatusCodeNm(this.messageSource.getMessage("text.project.status.unapproved"));	// 견적
			} else if(StringUtils.equals(obj.getProjectStatusCode(), "APPROVED")) {
				obj.setProjectStatusCodeNm(this.messageSource.getMessage("text.project.status.approved"));		// 실행
			} else {
				obj.setProjectStatusCodeNm(obj.getProjectStatusCode());
			}
		}
		return list;
	}

	/**
	 * Task 검색
	 * @param search
	 * @return
	 */
	public List<Task> getTaskList(DetailSearch search) {
		return this.detailMapper.selectTaskList(search);
	}

	/**
	 * 수행조직 검색
	 * @param search
	 * @return
	 */
	public List<Org> getOrgList(DetailSearch search) {
		return this.detailMapper.selectOrgList(search);
	}

	/**
	 * 원가유형 검색
	 * @param search
	 * @return
	 */
	public List<Type> getTypeList(DetailSearch search) {
		return this.detailMapper.selectTypeList(search);
	}
	
	/**
	 * 세션 ID 조회
	 * @param userId
	 * @return
	 */
	public String getSessionId(int userId) {
		this.detailMapper.initSessionId(userId);
		return this.detailMapper.selectSessionId();
	}
	
	/**
	 * 승인 요청
	 * @param request
	 * @throws CloneNotSupportedException
	 */
	@Transactional
	public void requestAdjust(RequestAdjust request) throws CloneNotSupportedException {
		Invoice invoice = this.createInvoiceHeader(request);
		// 승인요청전 추가검증
		// 0.1 회계일자 검증
		if(this.detailMapper.selectCheckGlDate(invoice) < 2) {
			throw new CardBusinessException(this.messageSource.getMessage("msg.valid.closegldate"));
		}
		// 0.2 프로젝트 금액일경우 프로젝트 공기 검증
		for(Invoice lineData : this.createInvoiceDataList(invoice, request)) {
			if(StringUtils.isNotEmpty(lineData.getProjectNumber())) {
				if(this.detailMapper.selectCheckProjectDate(lineData) == 0) {
					throw new CardBusinessException(this.messageSource.getMessage("msg.valid.projectdate"));					
				}
			}
		}
		// 1. 벤더정보 조회
		Invoice vendor = this.detailMapper.selectVendorInfo(invoice.getCardno());
		if(vendor == null) {
			throw new CardBusinessException(this.messageSource.getMessage("msg.error.no-card-data"));
		}
		invoice.setVendorId(vendor.getVendorId());
		invoice.setVendorSiteId(vendor.getVendorSiteId());
		
		// 2. TERMS ID 조회
		Invoice terms = this.detailMapper.selectTermsId(invoice);
		if(terms == null) {
			throw new CardBusinessException(this.messageSource.getMessage("msg.error.no-terms-data"));
		}
		invoice.setTermsId(terms.getTermsId());
		
		// 3. 세션 ID 조회
		invoice.setSessionId(this.getSessionId(invoice.getUser().getUserId()));
		
		// 4. transfer ID 카드 테이블에 수정
		this.detailMapper.updateCardApprovalList(invoice);
		
		// 5. 전표 헤더 생성
		this.detailMapper.insertInvoiceHeader(invoice);
		
		for(Invoice lineData : this.createInvoiceDataList(invoice, request)) {
			// 6. 전표 ITEM 생성
			this.detailMapper.insertInvoiceItem(lineData);
		}
		
		// 7. 전표 TAX 생성
		if(StringUtils.equals(invoice.getHasVat(),"1")) {
			this.detailMapper.insertInvoiceTax(invoice);
		}
		
		// 8-1. workflow ID 발번
		invoice.setWfId(this.detailMapper.selectWorkflowId());
		// 8-2. workflow 헤더정보 생성
		this.detailMapper.insertApprovalHead(invoice);
		// 8-3. workflow 라인정보(승인권자) 생성
		for(Invoice approve: this.createApprovalEmployeeList(invoice, request)) {
			this.detailMapper.insertApprovalLine(approve);
		}
		// 9. 승인 요청
		this.detailMapper.callErpApprove(invoice);
		
		if(StringUtils.isNotEmpty(invoice.getFileId())) {
			// 10. 첨부파일을 Documentum에 등록
			this.detailMapper.insertDocuFile(invoice);
		}
	}
	
	/**
	 * 전표 헤더 정보 생성
	 * @param request
	 * @return Invoice
	 */
	private Invoice createInvoiceHeader(RequestAdjust request) {
		Invoice header = new Invoice();
		header.setSeq(request.getSeq());
		header.setHasVat(request.getHasVat());
		header.setCardno(request.getCardno());
		header.setGlDate(request.getGlDateSubmit());
		header.setAttend(request.getAttend());
		header.setFileId(request.getFileId());
		header.setFileNm(request.getFileNm());
		header.setDescription(request.getDescription().get(0));
		header.setApprLevelCnt(request.getEmpcd().split(this.COMMA_REGEXP).length);
		header.setUser(request.getUser());
		return header;
	}

	/**
	 * 전표 라인 정보 생성
	 * @param header
	 * @param request
	 * @return
	 * @throws CloneNotSupportedException 
	 */
	private List<Invoice> createInvoiceDataList(Invoice header, RequestAdjust request) throws CloneNotSupportedException {
		List<Invoice> list = new ArrayList<>();
		if(StringUtils.equals("D", request.getAdjustType())) {
			// 부서 경비
			for(int i=0, size=request.getDeptCode().size(); i<size; i++) {
				Invoice data = (Invoice) header.clone();
				data.setDeptCode(request.getDeptCode().get(i));
				data.setMainAccCode(request.getMainAccCode().get(i));
				data.setSubAccCode(request.getSubAccCode().get(i));
				data.setAttributeCategory(request.getAttributeCategory().get(i));
				data.setDescription(request.getDescription().get(i));
				data.setTransactionAmount(request.getAppramt().get(i));
				list.add(data);
			}
		} else {
			// 프로젝트 경비
			for(int i=0, size=request.getProjectNumber().size(); i<size; i++) {
				Invoice data = (Invoice) header.clone();
				data.setProjectId(request.getProjectId().get(i));
				data.setProjectName(request.getProjectName().get(i));
				data.setProjectNumber(request.getProjectNumber().get(i));
				data.setProjectType(request.getProjectType().get(i));
				data.setTaskId(request.getTaskId().get(i));
				data.setTaskName(request.getTaskName().get(i));
				data.setTaskNumber(request.getTaskNumber().get(i));
				data.setExpOrgId(request.getExpOrgId().get(i));
				data.setExpOrgName(request.getExpOrgName().get(i));
				data.setExpType(request.getExpType().get(i));
				data.setDeptCode(request.getSegment2().get(i));
				data.setMainAccCode(request.getMainAccCode().get(i));
				data.setSubAccCode(request.getSubAccCode().get(i));
				data.setAttributeCategory(request.getAttributeCategory().get(i));
				data.setDescription(request.getDescription().get(i));
				data.setTransactionAmount(request.getAppramt().get(i));
				list.add(data);
			}
		}
		return list;
	}

	/**
	 * 승인권자 정보 생성
	 * @param invoice
	 * @param request
	 * @return
	 * @throws CloneNotSupportedException
	 */
	private List<Invoice> createApprovalEmployeeList(Invoice invoice, RequestAdjust request) throws CloneNotSupportedException {
		List<Invoice> list = new ArrayList<>();
		List<String> empIds = Arrays.asList(request.getEmpId().split(this.COMMA_REGEXP));
		for(int i=0, size=empIds.size(); i<size; i++) {
			Invoice empInfo = (Invoice) invoice.clone();
			empInfo.setApprLevel(i + 1);
			empInfo.setEmpId(i == 0 ? invoice.getUser().getEmpId() : empIds.get(i - 1));
			empInfo.setSupervisorId(empIds.get(i));
			list.add(empInfo);
		}
		return list;
	}
}
