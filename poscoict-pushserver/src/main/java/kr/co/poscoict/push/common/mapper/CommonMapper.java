package kr.co.poscoict.push.common.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.co.poscoict.push.common.model.User;

@Mapper
public interface CommonMapper {

	User selectUser(String empcd);

}
