package kr.co.poscoict.batch.pst.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.poscoict.batch.model.PushInfo;

@Mapper
public interface PostgresqlMapper {
	void insertPushInfo(PushInfo pushInfo);
	
	PushInfo selectPushInfo(String empcd);
	
	List<PushInfo> selectPushTarget(Map<String, Object> paramValueMap);
}
