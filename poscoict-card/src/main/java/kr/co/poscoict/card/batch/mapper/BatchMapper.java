package kr.co.poscoict.card.batch.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.poscoict.card.common.model.User;

@Mapper
public interface BatchMapper {
	List<User> selectPushTargetUserList();
}
