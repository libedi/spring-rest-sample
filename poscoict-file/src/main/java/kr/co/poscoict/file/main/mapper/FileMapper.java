package kr.co.poscoict.file.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.poscoict.file.main.model.FileInfo;

/**
 * File Mapper interface
 * @author Sangjun, Park
 *
 */
@Mapper
public interface FileMapper {

	void insertFile(FileInfo fileInfo);
	
	List<FileInfo> selectFileList(FileInfo fileInfo);

	void deleteFile(String id);

}
