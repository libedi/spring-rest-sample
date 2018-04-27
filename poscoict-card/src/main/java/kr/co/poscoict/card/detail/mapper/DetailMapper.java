package kr.co.poscoict.card.detail.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.poscoict.card.common.model.Search;
import kr.co.poscoict.card.detail.model.Account;
import kr.co.poscoict.card.detail.model.Approve;
import kr.co.poscoict.card.detail.model.Dept;
import kr.co.poscoict.card.detail.model.DetailSearch;
import kr.co.poscoict.card.detail.model.Invoice;
import kr.co.poscoict.card.detail.model.Org;
import kr.co.poscoict.card.detail.model.Project;
import kr.co.poscoict.card.detail.model.Task;
import kr.co.poscoict.card.detail.model.Type;

/**
 * 정산 상세 mapper
 * @author Sangjun, Park
 *
 */
@Mapper
public interface DetailMapper {

	List<Dept> selectDeptList(Search search);
	
	List<Approve> selectApproveList(Search search);
	
	List<Account> selectAccountList(Search search);
	
	List<Project> selectProjectList(Search search);

	List<Task> selectTaskList(DetailSearch search);

	List<Org> selectOrgList(DetailSearch search);

	List<Type> selectTypeList(DetailSearch search);
	
	Invoice selectVendorInfo(String cardno);
	
	Invoice selectTermsId(Invoice invoice);
	
	void updateCardApprovalList(Invoice invoice);

	void initSessionId(int userId);
	
	String selectSessionId();
	
	int selectCheckGlDate(Invoice invoice);
	
	int selectCheckProjectDate(Invoice invoice);
	
	void insertInvoiceHeader(Invoice invoice);
	
	void insertInvoiceItem(Invoice invoice);
	
	void insertInvoiceTax(Invoice invoice);
	
	long selectWorkflowId();
	
	void callInsertApprovalHead(Invoice invoice);
	
	void callInsertApprovalLine(Invoice invoice);
	
	void insertApprovalHead(Invoice invoice);
	
	void insertApprovalLine(Invoice invoice);
	
	void callErpApprove(Invoice invoice);

	void insertDocuFile(Invoice invoice);

}
