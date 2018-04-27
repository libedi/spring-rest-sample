package kr.co.poscoict.card.adjust.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.poscoict.card.adjust.service.AdjustService;
import kr.co.poscoict.card.common.model.HostInfo;
import kr.co.poscoict.card.common.model.User;
import kr.co.poscoict.card.common.service.CommonService;
import kr.co.poscoict.card.common.util.EncoderUtil;
import kr.co.poscoict.card.common.util.JwtClientUtil;

/**
 * Main Controller
 * @author jcs
 *
 */
@Controller
public class AdjustController {
	private final Logger logger = LoggerFactory.getLogger(AdjustController.class);
	/**
	 * 날짜 패턴 : yyyy-MM-dd
	 */
	private final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private final String ROOT_PATH = "/adjust/";
	
	@Autowired
	private AdjustService adjustService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private JwtClientUtil jwtClientUtil;
	
	@Autowired
	private EncoderUtil encoderUtil;
	
	/**
	 * 루트 접근시 메인 페이지 이동
	 * @return
	 */
	@GetMapping("/")
	public String index() {
		return "forward:/adjust";
	}
	
	/**
	 * 카드정산 페이지
	 * @param model
	 * @throws Exception 
	 */
	@GetMapping("/adjust")
	public String adjust(Model model, @AuthenticationPrincipal User user) throws Exception {
		HostInfo hostInfo = this.commonService.getHostUrl();
		model.addAttribute("encodeId", this.encoderUtil.encodeBySeed(user.getEmpcd()));
		model.addAttribute("toDate", this.adjustService.getToDate());
		model.addAttribute("fromDate", LocalDate.now().minusMonths(2).format(this.DATE_PATTERN));
		model.addAttribute("urls", hostInfo);
		boolean isValidTerm = this.adjustService.isValidTerm(user.getEmpcd());
		model.addAttribute("isValidTerm", isValidTerm);
		if(isValidTerm) {
			model.addAttribute("validTermMessage", this.adjustService.getValidTermMessage());
		}
		model.addAttribute("jwt", this.jwtClientUtil.getAuthenticationToken(hostInfo.getPushUrl()));
		return this.ROOT_PATH + "adjust";
	}
	
	/**
	 * 카드선택 페이지
	 * @param model
	 */
	@GetMapping("/adjust/selectCard")
	public void selectCard(Model model, @AuthenticationPrincipal User user) {
		model.addAttribute("cardList", this.adjustService.getTargetCardList(user.getEmpcd()));
	}
	
	/**
	 * Database Exception Handler
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = {SQLException.class, DataAccessException.class})
	public String handleDatabaseError(Exception e) {
		logger.error("DATABASE ERROR : {}", e.getMessage(), e);
		return "redirect:/error/500";
	}
	
	/**
	 * Exception Handler
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	public String handleServerError(Exception e) {
		logger.error("SERVER ERROR : {}", e.getMessage(), e);
		return "redirect:/error/500";
	}
	
}