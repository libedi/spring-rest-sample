package kr.co.poscoict.card.framework.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.co.poscoict.card.common.model.User;
import kr.co.poscoict.card.common.service.CommonService;

/**
 * Posco UserDetailsService
 * @author Sangjun, Park
 *
 */
public class PoscoUserService implements UserDetailsService {

	@Autowired
	private CommonService commonService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.commonService.getUser(username);
		if(user == null) {
			throw new UsernameNotFoundException("No found username : " + username);
		}
		return user;
	}

}
