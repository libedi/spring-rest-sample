package kr.co.poscoict.card.file.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.poscoict.card.file.model.FileInfo;

/**
 * File Mapper interface
 * @author Sangjun, Park
 *
 */
@Mapper
public interface FileMapper {

	List<FileInfo> selectFiles(FileInfo fileInfo);

}
