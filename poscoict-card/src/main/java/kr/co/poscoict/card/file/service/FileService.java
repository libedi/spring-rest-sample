package kr.co.poscoict.card.file.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import kr.co.poscoict.card.common.except.CardBusinessException;
import kr.co.poscoict.card.common.model.Images;
import kr.co.poscoict.card.common.util.MessageSourceUtil;
import kr.co.poscoict.card.file.mapper.FileMapper;
import kr.co.poscoict.card.file.model.FileInfo;


/**
 * File Service
 * @author Sangjun, Park
 *
 */
@Service
public class FileService {
	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MessageSourceUtil messageSource;
	
	@Value("${upload.path.root}")
	private String rootUploadPath;
	
	@Value("${url.nas}")
	private String nasUrl;
	
	/**
	 * File 조회
	 * @return
	 */
	public List<FileInfo> getFileList() {
		return this.fileMapper.selectFiles(new FileInfo());
	}
	
	/**
	 * File Upload
	 * @param multipartFile
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public FileInfo upload(MultipartFile multipartFile) throws IllegalStateException, IOException, InterruptedException {
		if(!this.isImage(multipartFile)) {
			throw new CardBusinessException(this.messageSource.getMessage("msg.valid.image"));
		}
		MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
		multiValueMap.add("files", new ByteArrayResource(multipartFile.getBytes()) {
			@Override
			public String getFilename() {
				return multipartFile.getOriginalFilename();
			}
		});
		return this.restTemplate.postForObject(nasUrl, multiValueMap, FileInfo.class);
	}

	/**
	 * File Download
	 * @param fileInfo
	 * @return
	 */
	public Object download(FileInfo fileInfo) {
		List<FileInfo> fileList = this.fileMapper.selectFiles(fileInfo);
		return fileList.isEmpty() ? this.downloadApi(fileInfo.getFileId()) : fileList.get(0);
	}
	
	/**
	 * File Download API call
	 * @param id
	 * @return
	 */
	public ResponseEntity<byte[]> downloadApi(String id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
		HttpEntity<String> entity = new HttpEntity<String>(headers); 
		return this.restTemplate.exchange(nasUrl + id, HttpMethod.GET, entity, byte[].class);
	}

	/**
	 * File Delete
	 * @param id
	 * @throws IOException 
	 */
	public void delete(String id) throws IOException {
		this.restTemplate.delete(nasUrl + id);
	}
	
	/**
	 * 
	 * @param multipartFile
	 * @return
	 */
	private boolean isImage(MultipartFile multipartFile) {
		return Images.isImage(FilenameUtils.getExtension(multipartFile.getOriginalFilename()).toLowerCase());
	}

}
