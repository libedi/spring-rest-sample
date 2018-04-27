package kr.co.poscoict.card.adjust.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.poscoict.card.adjust.mapper.AdjustMapper;
import kr.co.poscoict.card.adjust.model.Card;
import kr.co.poscoict.card.adjust.model.CardExcept;
import kr.co.poscoict.card.adjust.model.CardHistory;
import kr.co.poscoict.card.adjust.model.CardSearch;
import kr.co.poscoict.card.common.except.CardBusinessException;
import kr.co.poscoict.card.common.model.Code;
import kr.co.poscoict.card.detail.model.AutoApprove;

/**
 * Main Service
 * @author Sangjun, Park
 *
 */
@Service
public class AdjustService {
	
	@Autowired
	private AdjustMapper adjustMapper;
	
	/**
	 * To 기간 조회
	 * @return
	 */
	public String getToDate() {
		return this.adjustMapper.selectToDate();
	}
	
	/**
	 * 정산제외 사유코드 조회
	 * @return
	 */
	public List<Code> getExceptCodeList() {
		return this.adjustMapper.selectExceptCodeList();
	}
	
	/**
	 * 대상카드 조회
	 * @param empcd
	 * @return
	 */
	public List<Card> getTargetCardList(String empcd) {
		return this.adjustMapper.selectTargetCardList(empcd);
	}
	
	/**
	 * 카드내역 조회
	 * @param search
	 * @return
	 */
	public List<CardHistory> getCardHistoryList(CardSearch search) {
		return this.adjustMapper.selectCardHistoryList(search);
	}

	/**
	 * 카드 상세내역 조회
	 * @param seq
	 * @return
	 */
	public CardHistory getCardHistoryDetail(Integer seq) {
		return this.adjustMapper.selectCardHistoryDetail(seq);
	}
	
	/**
	 * 카드 상세내역 조회 (인증정보 포함)
	 * @param seq
	 * @return
	 */
	public CardHistory getCardHistoryDetailWithAppr(Integer seq) {
		CardHistory result = this.adjustMapper.selectCardHistoryDetail(seq);
		CardHistory temp = this.adjustMapper.selectApprInfo(result);
		if(temp != null) {
			result.setApprPerson(temp.getApprPerson());
			result.setApprDate(temp.getApprDate());
			result.setApprComments(temp.getApprComments());
		}
		return result;
	}

	/**
	 * 결산검증기간여부 조회
	 * @param empcd
	 * @return
	 */
	public boolean isValidTerm(String empcd) {
		return !StringUtils.equals(this.adjustMapper.selectValidTermYn(empcd), "Y");
	}
	
	/**
	 * 결산검증진행기간 알림 메시지 조회
	 * @return
	 */
	public String getValidTermMessage() {
		return this.adjustMapper.selectValidTermMessage();
	}

	/**
	 * 정산 중복건수 조회
	 * @param cardHistory
	 * @return
	 */
	public int getDuplicateCount(CardHistory cardHistory) {
		return this.adjustMapper.selectDuplicateCount(cardHistory);
	}
	
	/**
	 * 정산제외 처리
	 * @param cardExcept
	 */
	public void exceptAdjust(CardExcept cardExcept) {
		this.adjustMapper.callAdjustExcept(cardExcept);
		if(!StringUtils.equals(cardExcept.getReturnCode(), "S")) {
			throw new CardBusinessException(cardExcept.getReturnMessage());
		}
	}
	
	/**
	 * 정산취소 처리
	 * @param cardExcept
	 */
	public void cancelAdjust(CardExcept cardExcept) {
		this.adjustMapper.callAdjustCancel(cardExcept);
		if(!StringUtils.equals(cardExcept.getReturnCode(), "S")) {
			throw new CardBusinessException(cardExcept.getReturnMessage());
		}
	}
	
	/**
	 * 정산자의 자가결재 금액 및 여부, 기본 승인권자의 승인가능 전결금액 조회
	 * @param apprtot
	 * @param userId
	 * @param superUserId
	 * @return
	 */
	public AutoApprove getAutoApproveInfo(Long apprtot, Integer userId, Integer superUserId) {
		AutoApprove autoApprove = new AutoApprove();
		long selfApproveAmt = this.adjustMapper.selectSelfApproveAmt(userId);
		if(selfApproveAmt > apprtot) {
			autoApprove.setSelfApproveYn("Y");
			autoApprove.setAutoApproveAmt(selfApproveAmt);
		} else {
			autoApprove.setSelfApproveYn("N");
			autoApprove.setAutoApproveAmt(this.adjustMapper.selectAutoApproveAmt(superUserId));
		}
		return autoApprove;
	}
}
