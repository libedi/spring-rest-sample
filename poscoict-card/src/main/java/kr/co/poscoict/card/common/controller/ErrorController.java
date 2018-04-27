package kr.co.poscoict.card.common.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.poscoict.card.common.service.CommonService;
import kr.co.poscoict.card.common.util.MessageSourceUtil;

/**
 * 오류 컨트롤러
 * @author Sangjun, Park
 *
 */
@Controller
public class ErrorController {
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private MessageSourceUtil messageSource;
	
	/**
	 * 에러페이지 이동
	 * @param model
	 * @param code
	 * @return
	 */
	@GetMapping("/error/{code}")
	public String error(Model model, @PathVariable(value = "code") String code) {
		if(StringUtils.isEmpty(code) || !StringUtils.isNumeric(code)) {
			code = "500";
		}
		model.addAttribute("title", this.messageSource.getMessage("error.msg.title." + code));
		model.addAttribute("description", this.messageSource.getMessage("error.msg.desc." + code));
		model.addAttribute("footer", this.messageSource.getMessage("error.msg.footer"));
		model.addAttribute("homeUrl", this.commonService.getHostUrl().getHomeUrl());
		return "/error/error";
	}
}
