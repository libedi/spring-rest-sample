package kr.co.poscoict.push.send.controller;

import java.net.URLDecoder;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.posco.securyti.StringCrypto;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import kr.co.poscoict.push.send.model.Badge;
import kr.co.poscoict.push.send.model.Push;
import kr.co.poscoict.push.send.service.PushService;

/**
 * Push Controller
 * @author Sangjun, Park
 *
 */
@RestController
public class PushController {
	@Autowired
	private PushService pushService;
	
	/**
	 * Push 발송
	 * @param push
	 * @param encodedEmpcd 암호화된 직번
	 * @throws RestClientException 
	 * @throws Exception
	 */
	@ApiOperation(value = "Push 발송", authorizations = {
			@Authorization(value = "jwt")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "푸시발송요청 성공")
	})
	@PostMapping(path = "/push/{encodedEmpcd}")
	@ResponseStatus(HttpStatus.OK)
	public void push(
			@ApiParam(value = "푸시 데이터", required = true) @RequestBody @Valid Push push,
			@ApiParam(value = "암호화된 직번", required = true) @PathParam("encodedEmpcd") @PathVariable(value = "encodedEmpcd") String encodedEmpcd)
			throws Exception {
		push.setEmpcd(StringCrypto.decrypt(URLDecoder.decode(encodedEmpcd, "UTF-8")));
		this.pushService.push(push);
	}
	
	/**
	 * 뱃지건수 조회
	 * @param encodedEmpcd 암호화된 직번
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "뱃지건수 조회")
	@ApiResponses({
		@ApiResponse(code = 200, message = "뱃지건수 조회 성공")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "encodedEmpcd", value = "암호화된 직번", required = true, dataType = "string", paramType = "path")
	})
	@GetMapping(path = "/badges/{encodedEmpcd}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Badge getBadgeCount(@PathVariable(value = "encodedEmpcd") String encodedEmpcd) throws Exception {
		return this.pushService.getAllBadgeCount(StringCrypto.decrypt(URLDecoder.decode(encodedEmpcd, "UTF-8")));
	}
	
}
