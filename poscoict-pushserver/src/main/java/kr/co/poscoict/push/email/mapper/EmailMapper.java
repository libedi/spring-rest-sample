package kr.co.poscoict.push.email.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.poscoict.push.email.model.Refer;
import kr.co.poscoict.push.email.model.ReferMail;

@Mapper
public interface EmailMapper {

	void insertEmail(ReferMail referMail);

	void updateEmail(ReferMail referMail);
	
	List<Refer> selectEmail(ReferMail referMail);
}
