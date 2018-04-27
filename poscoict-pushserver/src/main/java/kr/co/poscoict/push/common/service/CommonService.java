package kr.co.poscoict.push.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.poscoict.push.common.mapper.CommonMapper;
import kr.co.poscoict.push.common.model.User;

/**
 * 공통 Service
 * @author Sangjun, Park
 *
 */
@Service
public class CommonService {
	@Autowired
	private CommonMapper commonMapper;
	
	/**
	 * 사용자 정보 조회
	 * @param empcd
	 * @return
	 */
	public User getUser(String empcd) {
		return this.commonMapper.selectUser(empcd);
	}

}
