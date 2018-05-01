package com.skplanet.impay.ccs.common.service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.skplanet.impay.ccs.common.model.ZipCode;

/**
 * 우편 번호 Service
 * @author Jang
 *
 */

@Service
public class ZipcodeService {

	private static Logger logger = LoggerFactory.getLogger(ZipcodeService.class);
	
	private final static String URL = "http://biz.epost.go.kr/KpostPortal/openapi?regkey=ef14f56a4eb3f7f381445921647842";

	/**
	 * 
	 * @param zipCode 우편 번호 정보
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> zipcodeSearch(ZipCode zipCode) throws Exception {
		
		String query = URLDecoder.decode(zipCode.getQuery().replaceAll(" ", ""), "UTF-8");
		int currentPage = zipCode.getPageParam().getPageIndex();
		logger.info(query.toString());
		
		String target = "postNew";
		String fullUrl = URL+"&target="+target+"&countPerPage=5&currentPage="+currentPage+"&query="+URLEncoder.encode(query, "EUC-KR");
		
		logger.info(fullUrl);
		
		Document doc = Jsoup.connect(fullUrl).get();
		
		String errorCode = doc.select("error_code").text();
		String errorMessage = doc.select("message").text();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<ZipCode> list = new ArrayList<ZipCode>();
		
		if (errorCode == null || errorCode.equals("")) {
			
			Elements elements = doc.select("item");
			
			for (Element element : elements) {
				ZipCode rstZipCode = new ZipCode();
				rstZipCode.setZipCode(element.select("postcd").text());
				rstZipCode.setAddress(element.select("address").text());
				
				list.add(rstZipCode);
			}
			paramMap.put("totalCount", Integer.parseInt(doc.select("totalcount").text()));
			paramMap.put("list", list);
			
		}else {
			paramMap.put("errorMessage", errorMessage);
			paramMap.put("errorCode", errorCode);
		}
		return paramMap;
	}
}