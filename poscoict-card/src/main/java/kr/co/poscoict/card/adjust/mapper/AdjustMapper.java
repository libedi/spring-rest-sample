package kr.co.poscoict.card.adjust.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.poscoict.card.adjust.model.Card;
import kr.co.poscoict.card.adjust.model.CardExcept;
import kr.co.poscoict.card.adjust.model.CardHistory;
import kr.co.poscoict.card.adjust.model.CardSearch;
import kr.co.poscoict.card.common.model.Code;

/**
 * Adjust Mapper
 * @author jcs
 *
 */
@Mapper
public interface AdjustMapper {

	String selectToDate();
	
	List<Code> selectExceptCodeList();
	
	List<Card> selectTargetCardList(String empcd);

	List<CardHistory> selectCardHistoryList(CardSearch search);

	CardHistory selectCardHistoryDetail(Integer seq);
	
	CardHistory selectApprInfo(CardHistory cardHistory);

	String selectValidTermYn(String empcd);
	
	String selectValidTermMessage();
	
	int selectDuplicateCount(CardHistory cardHistory);
	
	void callAdjustExcept(CardExcept cardExcept);
	
	void callAdjustCancel(CardExcept cardExcept);
	
	long selectAutoApproveAmt(int userId);
	
	long selectSelfApproveAmt(int userId);
}
