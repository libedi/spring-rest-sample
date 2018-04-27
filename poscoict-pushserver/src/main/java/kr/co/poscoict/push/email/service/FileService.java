package kr.co.poscoict.push.email.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import kr.co.poscoict.push.common.util.MessageSourceUtil;
import kr.co.poscoict.push.email.model.FileInfo;

/**
 * File Service
 * @author Sangjun, Park
 *
 */
@Service
public class FileService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MessageSourceUtil messageSource;
	
	@Value("${url.nas}")
	private String nasUrl;
	
	/**
	 * 파일 리스트 조회
	 * @param fileIdList
	 * @return
	 */
	public List<FileInfo> getFiles(List<String> fileIdList) {
		List<FileInfo> fileList = new ArrayList<>();
		for(String fileId : fileIdList) {
			fileList.add(this.restTemplate.getForObject(this.nasUrl + fileId, FileInfo.class));
		}
		return fileList;
	}
	
	/**
	 * 파일 조회
	 * @param fileId
	 * @return
	 * @throws FileNotFoundException 
	 */
	public FileInfo getFile(String fileId) throws FileNotFoundException {
		List<FileInfo> list = this.getFiles(Arrays.asList("fileId"));
		if(list.isEmpty()) {
			throw new FileNotFoundException(this.messageSource.getMessage("msg.file.no-found"));
		}
		return list.get(0);
	}

}
