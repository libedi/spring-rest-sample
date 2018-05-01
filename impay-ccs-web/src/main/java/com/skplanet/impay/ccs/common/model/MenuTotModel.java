/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.model;

import java.util.List;

/**
 * @author PP58701
 *
 */
public class MenuTotModel {

	private List<MenuModel> bos_menu1st = null;
	private List<MenuModel> bos_menu2nd = null;
	private List<MenuModel> bos_menu3rd = null;

	private List<MenuModel> ccs_menu1st = null;
	private List<MenuModel> ccs_menu2nd = null;
	private List<MenuModel> ccs_menu3rd = null;

	private List<MenuModel> cms_menu1st = null;
	private List<MenuModel> cms_menu2nd = null;
	private List<MenuModel> cms_menu3rd = null;

	private List<MenuAuthGrpModel> menuAuthGrp = null;

	public List<MenuModel> getBos_menu1st() {
		return bos_menu1st;
	}

	public void setBos_menu1st(List<MenuModel> bos_menu1st) {
		this.bos_menu1st = bos_menu1st;
	}

	public List<MenuModel> getBos_menu2nd() {
		return bos_menu2nd;
	}

	public void setBos_menu2nd(List<MenuModel> bos_menu2nd) {
		this.bos_menu2nd = bos_menu2nd;
	}

	public List<MenuModel> getBos_menu3rd() {
		return bos_menu3rd;
	}

	public void setBos_menu3rd(List<MenuModel> bos_menu3rd) {
		this.bos_menu3rd = bos_menu3rd;
	}

	public List<MenuModel> getCcs_menu1st() {
		return ccs_menu1st;
	}

	public void setCcs_menu1st(List<MenuModel> ccs_menu1st) {
		this.ccs_menu1st = ccs_menu1st;
	}

	public List<MenuModel> getCcs_menu2nd() {
		return ccs_menu2nd;
	}

	public void setCcs_menu2nd(List<MenuModel> ccs_menu2nd) {
		this.ccs_menu2nd = ccs_menu2nd;
	}

	public List<MenuModel> getCcs_menu3rd() {
		return ccs_menu3rd;
	}

	public void setCcs_menu3rd(List<MenuModel> ccs_menu3rd) {
		this.ccs_menu3rd = ccs_menu3rd;
	}

	public List<MenuModel> getCms_menu1st() {
		return cms_menu1st;
	}

	public void setCms_menu1st(List<MenuModel> cms_menu1st) {
		this.cms_menu1st = cms_menu1st;
	}

	public List<MenuModel> getCms_menu2nd() {
		return cms_menu2nd;
	}

	public void setCms_menu2nd(List<MenuModel> cms_menu2nd) {
		this.cms_menu2nd = cms_menu2nd;
	}

	public List<MenuModel> getCms_menu3rd() {
		return cms_menu3rd;
	}

	public void setCms_menu3rd(List<MenuModel> cms_menu3rd) {
		this.cms_menu3rd = cms_menu3rd;
	}

	public List<MenuAuthGrpModel> getMenuAuthGrp() {
		return menuAuthGrp;
	}

	public void setMenuAuthGrp(List<MenuAuthGrpModel> menuAuthGrp) {
		this.menuAuthGrp = menuAuthGrp;
	}

}
