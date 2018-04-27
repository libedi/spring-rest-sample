package kr.co.poscoict.card.detail.controller;

import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.poscoict.card.adjust.model.CardHistory;
import kr.co.poscoict.card.adjust.service.AdjustService;
import kr.co.poscoict.card.common.model.User;
import kr.co.poscoict.card.common.service.CommonService;
import kr.co.poscoict.card.common.util.EncoderUtil;

/**
 * 정산 상세 Controller
 * @author Sangjun, Park
 *
 */
@Controller
public class DetailController {
	private static final Logger logger = LoggerFactory.getLogger(DetailController.class);
	
	private final String ROOT_PATH = "/detail/";

	@Autowired
	private AdjustService adjustService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private EncoderUtil encoderUtil;
	
	/**
	 * 카드정산 승인페이지
	 * @param model
	 * @throws UnsupportedEncodingException 
	 */
	@GetMapping("/detail")
	public String detail(Model model, Integer seq, @AuthenticationPrincipal User user) throws UnsupportedEncodingException {
		logger.debug("{}", seq);
		User superUser = this.commonService.getUser(user.getSuperempcd());
		CardHistory card = this.adjustService.getCardHistoryDetail(seq);
		model.addAttribute("encodeId", this.encoderUtil.encodeBySeed(user.getEmpcd()));
		model.addAttribute("user", user);
		model.addAttribute("superUser", superUser);
		model.addAttribute("home", this.commonService.getHostUrl().getHomeUrl());
		model.addAttribute("urls", this.commonService.getHostUrl());
		model.addAttribute("card", card);
		model.addAttribute("autoApproveInfo", 
				this.adjustService.getAutoApproveInfo(Long.parseLong(card.getApprtot()), user.getUserId(), superUser.getUserId()));
		model.addAttribute("today", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
		return this.ROOT_PATH + "detail";
	}
	
	/**
	 * 부서 검색 페이지
	 * @param model
	 */
	@GetMapping("/detail/selectDept")
	public void selectDept(Model model, @RequestParam(value = "index", required = true) String index) {
		model.addAttribute("index", index);
	}
	
	/**
	 * 계정 검색 페이지
	 * @param model
	 */
	@GetMapping("/detail/selectAccnt")
	public void selectAccount(Model model, @RequestParam(value = "index", required = true) String index) {
		model.addAttribute("index", index);}
	
	/**
	 * 승인권자 검색 페이지
	 * @param model
	 */
	@GetMapping("/detail/selectAppr")
	public void selectAprrove(Model model) {}
	
	/**
	 * 프로젝트 검색 페이지
	 * @param model
	 */
	@GetMapping("/detail/selectPrj")
	public void selectProject(Model model, @RequestParam(value = "index", required = true) String index) {
		model.addAttribute("index", index);
	}
	
	/**
	 * Task 검색 페이지
	 * @param model
	 */
	@GetMapping("/detail/selectTask")
	public void selectTask(Model model, @RequestParam(value = "index", required = true) String index,
										@RequestParam(value = "projectId", required = true) String projectId) {
		model.addAttribute("index", index);
		model.addAttribute("projectId", projectId);
	}
	
	/**
	 * 수행조직 검색 페이지
	 * @param model
	 */
	@GetMapping("/detail/selectOrg")
	public void selectOrg(Model model, @RequestParam(value = "index", required = true) String index,
										@RequestParam(value = "projectId", required = true) String projectId) {
		model.addAttribute("index", index);
		model.addAttribute("projectId", projectId);
	}
	
	/**
	 * 원가유형 검색 페이지
	 * @param model
	 */
	@GetMapping("/detail/selectType")
	public void selectType(Model model, @RequestParam(value = "index", required = true) String index,
										@RequestParam(value = "projectId", required = true) String projectId,
										@RequestParam(value = "projectNumber", required = true) String projectNumber) {
		model.addAttribute("index", index);
		model.addAttribute("projectId", projectId);
		model.addAttribute("projectNumber", projectNumber);
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
