/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.rpm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhncorp.lucy.security.xss.XssPreventer;
import com.skplanet.impay.ccs.common.log.idms.service.IdmsCustomerLogService;
import com.skplanet.impay.ccs.common.model.Page;
import com.skplanet.impay.ccs.common.service.MessageByLocaleServiceImpl;
import com.skplanet.impay.ccs.common.service.mapper.CodeNameMapper;
import com.skplanet.impay.ccs.common.service.mapper.UserMapper;
import com.skplanet.impay.ccs.common.util.CustomStringUtils;
import com.skplanet.impay.ccs.framework.security.model.CustomUserDetails;
import com.skplanet.impay.ccs.rpm.model.RpmSearch;
import com.skplanet.impay.ccs.rpm.model.TjurRcpt;
import com.skplanet.impay.ccs.rpm.model.TjurUploadRcpt;
import com.skplanet.impay.ccs.rpm.service.mapper.TransferRcptMapper;

/**
 * 이관 접수 Service
 * @author Sangjun, Park
 *
 */
@Service
public class TransferRcptService {
	
	@Autowired
	private TransferRcptMapper transferRcptMapper;
	
	@Autowired
	private MessageByLocaleServiceImpl message;
	
	@Autowired
	private CodeNameMapper codeNameMapper;
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private IdmsCustomerLogService idmsCustomerLogService;
	
	/**
	 * 이관 접수 리스트 조회 (페이징 처리)
	 * @param rpmSearch 접수/처리 조회 조건
	 * @return Page<TjurRcpt>
	 */
	public Page<TjurRcpt> getTransferRcptListByPaging(RpmSearch rpmSearch) {
		final List<TjurRcpt> list = new ArrayList<TjurRcpt>();
		int count = this.transferRcptMapper.selectCnslCpTjurListCount(rpmSearch);
		if(count > 0){
			transferRcptMapper.selectCnslCpTjurListByPaging(rpmSearch, new ResultHandler<TjurRcpt>(){
				@Override
				public void handleResult(ResultContext<? extends TjurRcpt> context) {
					TjurRcpt obj = context.getResultObject();
					if(StringUtils.isNotEmpty(obj.getCommcClf())){
						obj.setCommcClfNm(codeNameMapper.selectCodeName(obj.getCommcClf()));
					}
					if(StringUtils.isNotEmpty(obj.getCnslClfUprCd())){
						obj.setCnslClfUprCdNm(codeNameMapper.selectCodeName(obj.getCnslClfUprCd()));
					}
					if(StringUtils.isNotEmpty(obj.getCnslTypCd())){
						obj.setCnslTypCdNm(codeNameMapper.selectCodeName(obj.getCnslTypCd()));
					}
					if(StringUtils.isNotEmpty(obj.getRcptMthdCd())){
						obj.setRcptMthdCdNm(codeNameMapper.selectCodeName(obj.getRcptMthdCd()));
					}
					if(StringUtils.equals("D", obj.getFlg())){
						obj.setRsltNotiMthdNm(obj.getProcRslt());
					} else if(StringUtils.isNotEmpty(obj.getRsltNotiMthd())){
						if(StringUtils.equals("M", obj.getRsltNotiMthd())){
							obj.setRsltNotiMthdNm("E-mail");
						} else if(StringUtils.equals("S", obj.getRsltNotiMthd())){
							obj.setRsltNotiMthdNm("SMS");
						}
					}
					if(StringUtils.isNotEmpty(obj.getCnslCtnt())){
						obj.setCnslCtnt(obj.getCnslCtnt().replaceAll("<br/>", " "));
					}
					if(StringUtils.isNotEmpty(obj.getProcCtnt())){
						obj.setProcCtnt(obj.getProcCtnt().replaceAll("<br/>", " "));
					}
					list.add(obj);
				}
			});
		}
		return new Page<TjurRcpt>(count, list);
	}
	
	/**
	 * 이관 접수 리스트 총건수 조회
	 * @param rpmSearch 접수/처리 조회 조건
	 * @return int
	 */
	public int getTransferRcptListCount(RpmSearch rpmSearch) {
		return this.transferRcptMapper.selectCnslCpTjurListCount(rpmSearch);
	}
	
	/**
	 * 이관 접수 리스트 조회
	 * @param rpmSearch 접수/처리 조회 조건
	 * @return List<TjurRcpt>
	 */
	public List<TjurRcpt> getTransferRcptList(RpmSearch rpmSearch) {
		final List<TjurRcpt> list = new ArrayList<TjurRcpt>();
		this.transferRcptMapper.selectCnslCpTjurList(rpmSearch, new ResultHandler<TjurRcpt>(){
			@Override
			public void handleResult(ResultContext<? extends TjurRcpt> context) {
				TjurRcpt obj = context.getResultObject();
				if(StringUtils.isNotEmpty(obj.getCommcClf())){
					obj.setCommcClfNm(codeNameMapper.selectCodeName(obj.getCommcClf()));
				}
				if(StringUtils.isNotEmpty(obj.getCnslClfUprCd())){
					obj.setCnslClfUprCdNm(codeNameMapper.selectCodeName(obj.getCnslClfUprCd()));
				}
				if(StringUtils.isNotEmpty(obj.getCnslTypCd())){
					obj.setCnslTypCdNm(codeNameMapper.selectCodeName(obj.getCnslTypCd()));
				}
				if(StringUtils.isNotEmpty(obj.getRcptMthdCd())){
					obj.setRcptMthdCdNm(codeNameMapper.selectCodeName(obj.getRcptMthdCd()));
				}
				if(StringUtils.equals("D", obj.getFlg())){
					obj.setRsltNotiMthdNm(obj.getProcRslt());
				} else if(StringUtils.isNotEmpty(obj.getRsltNotiMthd())){
					if(StringUtils.equals("M", obj.getRsltNotiMthd())){
						obj.setRsltNotiMthdNm("E-mail");
					} else if(StringUtils.equals("S", obj.getRsltNotiMthd())){
						obj.setRsltNotiMthdNm("SMS");
					}
				}
				if(StringUtils.isNotEmpty(obj.getCnslCtnt())){
					obj.setCnslCtnt(obj.getCnslCtnt().replaceAll("<br/>", "\n"));
				}
				if(StringUtils.isNotEmpty(obj.getProcCtnt())){
					obj.setProcCtnt(obj.getProcCtnt().replaceAll("<br/>", "\n"));
				}
				list.add(obj);
			}
		});
		return list;
	}

	/**
	 * 이관 접수 리스트 엑셀 데이터 생성
	 * @param rpmSearch 접수/처리 조회 조건
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getTransferRcptListExcelDown(RpmSearch rpmSearch, CustomUserDetails userInfo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<List<String>> headerList = new ArrayList<>();
		List<String> colName = new ArrayList<>();
		colName.add(message.getMessage("rpm.tjur.th.date.tjur"));		// 이관 일자
		colName.add(message.getMessage("rpm.tjur.th.cp"));				// 가맹점
		colName.add(message.getMessage("rpm.commc"));					// 이통사
		colName.add(message.getMessage("rpm.tjur.th.mphnno"));			// 휴대폰 번호
		colName.add(message.getMessage("rpm.tjur.th.date.pay"));		// 결제 일자
		colName.add(message.getMessage("rpm.tjur.th.payamt"));			// 결제 금액
		colName.add(message.getMessage("rpm.tjur.th.cpnm"));			// 가맹점명
		colName.add(message.getMessage("rpm.tjur.th.goodsnm"));			// 상품명
		colName.add(message.getMessage("rpm.tjur.th.gubun.cnsl"));		// 상담구분
		colName.add(message.getMessage("rpm.tjur.th.type.cnsl"));		// 상담유형
		colName.add(message.getMessage("rpm.tjur.th.type.rcpt"));		// 접수유형
		colName.add(message.getMessage("rpm.tjur.th.stat.proc"));		// 처리상태
		colName.add(message.getMessage("rpm.tjur.th.date.proc"));		// 처리일시
		colName.add(message.getMessage("rpm.tjur.th.content.cnsl"));	// 상담내용
		colName.add(message.getMessage("rpm.tjur.th.content.proc"));	// 처리내용
		colName.add(message.getMessage("rpm.tjur.th.noti.result"));		// 결과통보
		headerList.add(colName);
		
		List<List<String>> dataList = new ArrayList<>();
		List<TjurRcpt> list = this.getTransferRcptList(rpmSearch);
		// 고객 정보 LOG 수집
		if(!list.isEmpty()){
			idmsCustomerLogService.printIdmsLog(list.get(0).getRcptNo(), "X", "CCS0039", "40001", idmsCustomerLogService.MAX_CUSTOMER_COUNT, userInfo);
		}
		for(TjurRcpt model : list){
			List<String> colValue = new ArrayList<>();
			colValue.add(model.getRegDt());
			colValue.add(model.getEntpNm());
			colValue.add(model.getCommcClfNm());
			colValue.add(model.getMphnNo());
			colValue.add(model.getTrdDt());
			colValue.add(CustomStringUtils.formatMoney(model.getPayAmt()));
			colValue.add(model.getPaySvcNm());
			colValue.add(model.getGodsNm());
			colValue.add(model.getCnslClfUprCdNm());
			colValue.add(model.getCnslTypCdNm());
			colValue.add(model.getRcptMthdCdNm());
			colValue.add(model.getTjurProcYn());
			colValue.add(model.getProcDt());
			colValue.add(XssPreventer.unescape(model.getCnslCtnt()));
			colValue.add(XssPreventer.unescape(model.getProcCtnt()));
			colValue.add(XssPreventer.unescape(model.getRsltNotiMthdNm()));
			dataList.add(colValue);
		}
		short[] dataAlignArray = new short[]{CellStyle.ALIGN_CENTER, CellStyle.ALIGN_LEFT, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER,
				CellStyle.ALIGN_RIGHT, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_LEFT, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, 
				CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_LEFT, CellStyle.ALIGN_LEFT, CellStyle.ALIGN_LEFT};
		
		String title = message.getMessage("rpm.tjur.title");
		resultMap.put("excelName", title);		// Excel File Name 설정
		resultMap.put("sheetName", title);		// sheet name 설정
		resultMap.put("headerList", headerList);
		resultMap.put("dataList", dataList);
		resultMap.put("dataAlignArray", dataAlignArray);
		return resultMap;
	}

	/**
	 * 업로드 이관 접수건 조회
	 * @param rcptNo 자료 등록 번호(접수 번호)
	 * @return TjurUploadRcpt
	 */
	public TjurUploadRcpt getUploadTransferRcpt(String rcptNo) {
		TjurUploadRcpt tjurUploadRcpt = this.transferRcptMapper.selectCnslFileUpld(rcptNo);
		tjurUploadRcpt.setRcptMthdCdNm(codeNameMapper.selectCodeName(tjurUploadRcpt.getRcptMthdCd()));
		tjurUploadRcpt.setCnslCtnt(XssPreventer.unescape(tjurUploadRcpt.getCnslCtnt()));
		if(StringUtils.isNotEmpty(tjurUploadRcpt.getProcCtnt())){
			tjurUploadRcpt.setProcCtnt(XssPreventer.unescape(tjurUploadRcpt.getProcCtnt()));
		}
		if(StringUtils.isNotEmpty(tjurUploadRcpt.getProcRslt())){
			tjurUploadRcpt.setProcRslt(XssPreventer.unescape(tjurUploadRcpt.getProcRslt()));
		}
		tjurUploadRcpt.setRegr(userMapper.selectUserId(tjurUploadRcpt.getRegr()));
		if(!StringUtils.equals("SYSTEM", tjurUploadRcpt.getLastChgr()) && StringUtils.isNotEmpty(tjurUploadRcpt.getLastChgr())){
			tjurUploadRcpt.setLastChgr(userMapper.selectUserId(tjurUploadRcpt.getLastChgr()));
		}
		return tjurUploadRcpt;
	}

	/**
	 * 업로드 이관 접수건 저장
	 * @param tjurUploadRcpt 이관 접수 정보
	 * @param userInfo 사용자 정보
	 * @return int
	 */
	public int saveUploadTransferRcpt(TjurUploadRcpt tjurUploadRcpt, CustomUserDetails userInfo) {
		tjurUploadRcpt.setLastChgr(userInfo.getUserSeq());
		return this.transferRcptMapper.updateCnslFileUpld(tjurUploadRcpt);
	}

}
