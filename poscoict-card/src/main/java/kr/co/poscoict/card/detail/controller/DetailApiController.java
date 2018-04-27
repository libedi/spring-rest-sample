package kr.co.poscoict.card.detail.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kr.co.poscoict.card.common.model.Search;
import kr.co.poscoict.card.common.model.User;
import kr.co.poscoict.card.detail.model.Account;
import kr.co.poscoict.card.detail.model.Approve;
import kr.co.poscoict.card.detail.model.Dept;
import kr.co.poscoict.card.detail.model.DetailSearch;
import kr.co.poscoict.card.detail.model.Org;
import kr.co.poscoict.card.detail.model.Project;
import kr.co.poscoict.card.detail.model.RequestAdjust;
import kr.co.poscoict.card.detail.model.Task;
import kr.co.poscoict.card.detail.model.Type;
import kr.co.poscoict.card.detail.service.DetailService;

/**
 * 정산 상세 REST api
 * @author Sangjun, Park
 *
 */
@RestController
@RequestMapping("/cards")
public class DetailApiController {
	
	@Autowired
	private DetailService detailService;
	
	/**
	 * 부서 검색
	 * @param search
	 * @param user
	 * @return
	 */
	@GetMapping(path = "/depts")
	@ResponseStatus(HttpStatus.OK)
	public List<Dept> getDeptList(Search search, @AuthenticationPrincipal User user) {
		search.setUser(user);
		return this.detailService.getDetpList(search);
	}
	
	/**
	 * 계정 검색
	 * @param search
	 * @param user
	 * @return
	 */
	@GetMapping(path = "/accnts")
	@ResponseStatus(HttpStatus.OK)
	public List<Account> getAccountList(Search search, @AuthenticationPrincipal User user) {
		search.setUser(user);
		return this.detailService.getAccountLIst(search);
	}
	
	/**
	 * 승인권자 검색
	 * @param search
	 * @param user
	 * @return
	 */
	@GetMapping(path = "/apprvs")
	@ResponseStatus(HttpStatus.OK)
	public List<Approve> getApproveList(Search search, @AuthenticationPrincipal User user) {
		search.setUser(user);
		return this.detailService.getApproveList(search);
	}
	
	/**
	 * 프로젝트 검색
	 * @param search
	 * @param user
	 * @return
	 */
	@GetMapping(path = "/prjs")
	@ResponseStatus(HttpStatus.OK)
	public List<Project> getProjectList(Search search, @AuthenticationPrincipal User user) {
		search.setUser(user);
		return this.detailService.getProjectList(search);
	}
	
	/**
	 * Task 검색
	 * @param search
	 * @param user
	 * @return
	 */
	@GetMapping(path = "/tasks")
	@ResponseStatus(HttpStatus.OK)
	public List<Task> getTaskList(DetailSearch search, @AuthenticationPrincipal User user) {
		search.setUser(user);
		return this.detailService.getTaskList(search);
	}
	
	/**
	 * 수행조직 검색
	 * @param search
	 * @param user
	 * @return
	 */
	@GetMapping(path = "/orgs")
	@ResponseStatus(HttpStatus.OK)
	public List<Org> getOrgList(DetailSearch search, @AuthenticationPrincipal User user) {
		search.setUser(user);
		return this.detailService.getOrgList(search);
	}
	
	/**
	 * 원가유형 검색
	 * @param search
	 * @param user
	 * @return
	 */
	@GetMapping(path = "/types")
	@ResponseStatus(HttpStatus.OK)
	public List<Type> getTypeList(DetailSearch search, @AuthenticationPrincipal User user) {
		search.setUser(user);
		return this.detailService.getTypeList(search);
	}
	
	/**
	 * 정산 승인요청
	 * @param request
	 * @param user
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 */
	@PostMapping(path = "/reqAdj")
	@ResponseStatus(HttpStatus.CREATED)
	public void requestAdjust(@RequestBody @Valid RequestAdjust request, @AuthenticationPrincipal User user)
			throws UnsupportedEncodingException, Exception {
		request.setUser(user);
		this.detailService.requestAdjust(request);
	}

}
