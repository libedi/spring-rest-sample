package kr.co.poscoict.card.file.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.co.poscoict.card.common.util.FileDownloadView;
import kr.co.poscoict.card.file.model.FileInfo;
import kr.co.poscoict.card.file.service.FileService;

/**
 * File Controller
 * @author Sangjun, Park
 *
 */
@Controller
public class FileController {
	@Autowired
	private FileService fileService;
	
	@Autowired
	private FileDownloadView fileDownloadView;
	
	/**
	 * File 조회
	 * @return
	 */
	@GetMapping(path = "/files")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<FileInfo> getFileList() {
		return this.fileService.getFileList();
	}
	
	/**
	 * File Upload
	 * @param multipartFiles
	 * @throws IOException
	 * @throws IllegalStateException
	 * @throws InterruptedException
	 */
	@PostMapping(path = "/files")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public FileInfo upload(@RequestParam("files") MultipartFile multipartFiles)
			throws IOException, IllegalStateException, InterruptedException {
		return this.fileService.upload(multipartFiles);
	}
	
	/**
	 * File Download
	 * @param fileInfo
	 * @return
	 */
	@GetMapping(path = "/files/{id}")
	public ModelAndView download(@PathVariable String id) {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileId(id);
		Map<String, Object> model = new HashMap<>();
		model.put("model", this.fileService.download(fileInfo));
		return new ModelAndView(this.fileDownloadView, model);
	}
	
	/**
	 * File Download API
	 * @param id
	 * @param response
	 */
	@GetMapping(path = "/api/files/{id}")
	public ModelAndView downloadApi(@PathVariable String id) {
		Map<String, Object> model = new HashMap<>();
		model.put("model", this.fileService.downloadApi(id));
		return new ModelAndView(this.fileDownloadView, model);
	}
	
	/**
	 * File Delete
	 * @param id
	 * @throws IOException 
	 */
	@DeleteMapping(path = "/files/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable String id) throws IOException {
		this.fileService.delete(id);
	}
}
