package kr.co.poscoict.card.common.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.co.poscoict.card.common.model.User;

/**
 * Badge Mapper
 * @author Sangjun, Park
 *
 */
@Mapper
public interface CommonMapper {
	
	User selectUser(String empcd);

	int selectTargetCount(String empcd);

}
