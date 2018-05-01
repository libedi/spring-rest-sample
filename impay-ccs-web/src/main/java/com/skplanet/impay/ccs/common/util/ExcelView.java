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
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import com.skplanet.impay.ccs.framework.view.AbstractExcel2007View;

/**
 * [Mandatory] Excel View
 *
 * @see       [Optional] 관련정보
 * <PRE>
 * 1. ClassName: ExcelView
 * 2. FileName : ExcelView.java
 * 3. Package  : com.skplanet.impay.ccs.common.util
 * 4. 작성자   : 주영길
 * 5. 작성일   : 2015. 11. 26. 오후 3:12:52
 * 6. 변경이력
 *            이름   :      일자   : 변경내용
 *     ———————————————————————————————————————————
 *            주영길 :    2015. 11. 26.      : 신규 개발.
 *
 * </PRE>
 * <br/>
 * <br/>
 * Copyright 2015 by SK Planet, Inc.,
 *
 * This software is the confidential and proprietary information
 * of SK Planet, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with SK Planet.
 */
@Component("excelView")
public class ExcelView extends AbstractExcel2007View {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 시트명
		Sheet sheet = workbook.createSheet((String) model.get("sheetName"));
		
		// 헤더 리스트
		List<List<String>> headerList = (List<List<String>>) model.get("headerList"); 
		// 데이터 리스트 
		List<List<String>> dataList = (List<List<String>>) model.get("dataList");
		// 데이터 좌우정렬
		short[] dataAlignArray = (short[]) model.get("dataAlignArray"); 
		// 병합정보 
		List<String> mergeList = (List<String>) model.get("mergeList"); 
		
		// 행 번호
		int rowIdx = 0; 
		
		// 헤더, 데이터 생성
		rowIdx += createCell(workbook, sheet, rowIdx, headerList, null);
		createCell(workbook, sheet, rowIdx, dataList, dataAlignArray);
		
		// 병합
		mergeCell(sheet, mergeList);
	}

	/**
	 * [Mandatory] 셀 병합
	 *
	 * @param sheet
	 * @param mergeList void
	 * @warning  [Optional]함수의 제약사항이나 주의해야 할 점
	 * @execption      [Mandatory]throw하는 exception들에 대한 설명
	 * @see      [Optional]관련 정보(관련 함수, 관련 모듈)
	 */
	private void mergeCell(Sheet sheet, List<String> mergeList) {
		if(mergeList != null){
			for(String range : mergeList){
				sheet.addMergedRegion(CellRangeAddress.valueOf(range));
			}
		}
	}

	/**
	 * [Mandatory] 셀 생성
	 *
	 * @param workbook
	 * @param sheet
	 * @param rowIdx
	 * @param dataList
	 * @param dataCsList
	 * @return int
	 * @warning  [Optional]함수의 제약사항이나 주의해야 할 점
	 * @execption      [Mandatory]throw하는 exception들에 대한 설명
	 * @see      [Optional]관련 정보(관련 함수, 관련 모듈)
	 */
	private int createCell(Workbook workbook, Sheet sheet, int rowIdx, List<List<String>> dataList, short[] dataCsList) {
		if(dataList != null){
			int[] maxWidth = new int[dataList.get(0).size()];
			
			for(List<String> item : dataList){
				Row row = sheet.createRow(rowIdx++);
				
				for (int j=0; j < item.size(); j++){
					Cell cell = row.createCell(j);
					CellStyle dataCs = createCellStyle(workbook);
					
					// 정렬 스타일
					if(dataCsList != null){
						dataCs.setAlignment(dataCsList[j]);
						dataCs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
					}else{
						dataCs.setAlignment(CellStyle.ALIGN_CENTER);
						dataCs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
					}
					cell.setCellStyle(dataCs);
					cell.setCellValue(item.get(j)); // 헤더 텍스트 생성
					sheet.autoSizeColumn(j);
					if(maxWidth != null){
						if(maxWidth[j] < (sheet.getColumnWidth(j) + 512)){
							maxWidth[j] = sheet.getColumnWidth(j) + 512;
						} else {
							sheet.setColumnWidth(j, maxWidth[j]);
						}
					}
					
				}
			}
		}
		return rowIdx;
	}
	
	/**
	 * [Mandatory] 셀 기본 스타일 생성
	 *
	 * @param workbook
	 * @return CellStyle
	 * @warning  [Optional]함수의 제약사항이나 주의해야 할 점
	 * @execption      [Mandatory]throw하는 exception들에 대한 설명
	 * @see      [Optional]관련 정보(관련 함수, 관련 모듈)
	 */
	private CellStyle createCellStyle(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
	    font.setFontHeightInPoints((short)10);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		return cellStyle;
	}

}
