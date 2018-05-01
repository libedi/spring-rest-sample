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

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.skplanet.impay.ccs.framework.view.AbstractExcel2007View;

/**
 * 엑셀다운로드 
 * @author jisu park
 *
 */
@Component("excelDownloadFile")
public class ExcelDownloadFile extends AbstractExcel2007View {
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,	HttpServletResponse response) throws Exception {				
		Map<String, Object> excelList = (Map<String, Object>) model.get("excelList");		
		List<String> colName = (List<String>) excelList.get("colName");
		Map<String,Object> colValue = (Map<String,Object>) excelList.get("colValue");

		Font font = workbook.createFont();
	    font.setFontHeightInPoints((short)10);
		CellStyle cs = workbook.createCellStyle();
		cs.setBorderTop(CellStyle.BORDER_THIN);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setFont(font);
		
		Sheet sheet = workbook.createSheet((String) model.get("sheetName"));		
		creatColum(sheet, colName, cs);
		
		for ( int i = 0; i < colValue.size(); i++ ) {
			createRow(sheet, (List<String>)colValue.get(String.valueOf(i)), i+1, cs);
		}
		
		for ( int i = 0; i < colName.size(); i++ ) {
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, (sheet.getColumnWidth(i)));  // 윗줄만으로는 컬럼의 width 가 부족하여 더 늘려야 함.
		}
	}
	
	private void creatColum(Sheet sheet, List<String> coln, CellStyle cs) {
		Row header = sheet.createRow(0);
		Cell cell = null;
		for (int i = 0; i < coln.size(); i++) {
			cell = header.createCell(i);
			cell.setCellValue(coln.get(i));
			cell.setCellStyle(cs);
			sheet.setColumnWidth(i, (sheet.getColumnWidth(i)));
		}
	}

	private void createRow(Sheet sheet, List<String> colv, int rowNum, CellStyle cs) {
		Row row = sheet.createRow(rowNum);
		Cell cell = null;		
		for ( int i = 0 ; i < colv.size(); i++ ) {
			cell = row.createCell(i);
			cell.setCellValue(colv.get(i));
			cell.setCellStyle(cs);
		}
	}	
}