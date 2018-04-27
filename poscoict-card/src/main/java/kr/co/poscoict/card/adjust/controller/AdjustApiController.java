package kr.co.poscoict.card.adjust.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kr.co.poscoict.card.adjust.model.CardExcept;
import kr.co.poscoict.card.adjust.model.CardHistory;
import kr.co.poscoict.card.adjust.model.CardSearch;
import kr.co.poscoict.card.adjust.service.AdjustService;
import kr.co.poscoict.card.common.model.User;


/**
 * Main API Controller
 * @author Sangjun, Park
 *
 */
@RestController
@RequestMapping("/cards")
public class AdjustApiController {
	
	@Autowired
	private AdjustService adjustService;
	
	/**
	 * 카드내역 정보 조회
	 * @param card
	 * @return
	 */
	@GetMapping(path = "/{cardno}")
	@ResponseStatus(HttpStatus.OK)
	public List<CardHistory> getCardHistoryList(@PathVariable(value = "cardno") String cardno, CardSearch search,
			@AuthenticationPrincipal User user) {
		search.setCardno(cardno);
		search.setUser(user);
		return this.adjustService.getCardHistoryList(search);
	}
	
	/**
	 * 카드 상세내역 조회
	 * @param seq
	 * @return
	 */
	@GetMapping(path = "/seq/{seq}")
	@ResponseStatus(HttpStatus.OK)
	public CardHistory getCardHistoryDetail(@PathVariable(value = "seq") String seq) {
		return this.adjustService.getCardHistoryDetailWithAppr(Integer.valueOf(seq));
	}
	
	/**
	 * 기등록된 정산 중복건수 조회
	 * @param cardHistory
	 * @return
	 */
	@GetMapping(path = "/dupl")
	@ResponseStatus(HttpStatus.OK)
	public int getDuplicateCount(CardHistory cardHistory) {
		return this.adjustService.getDuplicateCount(cardHistory);
	}
	
	/**
	 * 정산제외 처리
	 * @param cardExcept
	 */
	@PutMapping(path = "/adjust/except")
	@ResponseStatus(HttpStatus.OK)
	public void exceptAdjust(@RequestBody @Valid CardExcept cardExcept, @AuthenticationPrincipal User user) {
		this.adjustService.exceptAdjust(cardExcept);
	}
	
	/**
	 * 정산취소 처리
	 * @param cardExcept
	 */
	@PutMapping(path = "/adjust/cancel")
	@ResponseStatus(HttpStatus.OK)
	public void cancelAdjust(@RequestBody @Valid CardExcept cardExcept, @AuthenticationPrincipal User user) {
		cardExcept.setUser(user);
		this.adjustService.cancelAdjust(cardExcept);
	}
}
