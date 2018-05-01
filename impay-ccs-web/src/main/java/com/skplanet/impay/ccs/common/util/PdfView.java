/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.util;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.skplanet.impay.ccs.common.model.PdfModel;
import com.skplanet.impay.ccs.framework.view.AbstractPdfView;

import freemarker.template.Template;

/**
 * PDF 생성 View
 * @author Sangjun, Park
 *
 */
@Component("pdfView")
public class PdfView extends AbstractPdfView {
	
	@Value("${file.path.css}")
	private String CSS_FILE_PATH;
	
	@Value("${file.path.font}")
	private String FONT_FILE_PATH;
	
	@Value("${file.path.image}")
	private String IMG_FILE_PATH;

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ServletContext servletContext = request.getServletContext();
		PdfModel pdfModel = (PdfModel) model.get("model");
		
		if(pdfModel.isUseHtml()){
			// CSS
			CSSResolver cssResolver = null;
    		if(StringUtils.isNotEmpty(pdfModel.getCssFileName())){
    			cssResolver = new StyleAttrCSSResolver();
        		CssFile cssFile = XMLWorkerHelper.getCSS(Files.newInputStream(Paths.get(servletContext.getRealPath(CSS_FILE_PATH), pdfModel.getCssFileName())));
        		cssResolver.addCss(cssFile);
    		} else {
    			cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
    		}

			// HTML and Font
			XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
			fontProvider.register(Paths.get(servletContext.getRealPath(FONT_FILE_PATH), "NanumGothic-Regular.ttf").toString(), "NanumGothic");
			CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
			HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
			htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
			
			// Pipeline
			PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
			HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
			CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
			
			XMLWorker worker = new XMLWorker(css, true);
			XMLParser parser = new XMLParser(worker, Charset.forName("UTF-8"));
			
			pdfModel.put("imgPath", servletContext.getRealPath(IMG_FILE_PATH));
			
			Template template = freeMarkerConfigurer.createConfiguration().getTemplate(pdfModel.getViewFileName());
			String htmlStr = FreeMarkerTemplateUtils.processTemplateIntoString(template, pdfModel.getHtmlDataMap())
							.replaceAll("\"", "\\\"").replaceAll("\'", "\\\'");
			
			logger.debug("buildPdfDocument() : HTML STRING\n{}", htmlStr);
			
			parser.parse(new ByteArrayInputStream(htmlStr.getBytes("UTF-8")));
			
		} else {
			String textStr = pdfModel.getTextStr();
        	logger.debug("buildPdfDocument() : TEXT STRING - {}", textStr);
        	// 폰트 설정
        	Font font = new Font(BaseFont.createFont(Paths.get(servletContext.getRealPath(FONT_FILE_PATH), "NanumGothic-Regular.ttf").toString(), 
        			BaseFont.IDENTITY_H, BaseFont.EMBEDDED), pdfModel.getFontSize());
        	// Document 에 추가
    		document.add(new Paragraph(textStr, font));
		}
	}
	
}
