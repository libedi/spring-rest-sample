package kr.co.poscoict.batch.ora.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.poscoict.batch.model.User;

@Mapper
public interface OracleMapper {
	List<User> selectUser(Map<String, Object> paramValueMap);
}
