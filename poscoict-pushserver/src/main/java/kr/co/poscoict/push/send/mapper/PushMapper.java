package kr.co.poscoict.push.send.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * Push Mapper
 * @author Sangjun, Park
 *
 */
@Mapper
public interface PushMapper {
	
	boolean selectPushEnable(String empcd);
	
}
