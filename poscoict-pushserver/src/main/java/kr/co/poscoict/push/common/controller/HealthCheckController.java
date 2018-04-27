package kr.co.poscoict.push.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

/**
 * Health Check Controller
 * @author Sangjun, Park
 *
 */
@ApiIgnore
@RestController
public class HealthCheckController {
	
	/**
	 * Health Checking
	 * @return
	 */
	@RequestMapping(value = "/healthcheck", method = {RequestMethod.GET, RequestMethod.POST})
	public String healthCheck() {
		return "poscoict_healthcheck_ok";
	}
}
