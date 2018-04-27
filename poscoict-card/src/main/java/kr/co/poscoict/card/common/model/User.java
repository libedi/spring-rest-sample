package kr.co.poscoict.card.common.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 사용자 정보
 * @author Sangjun, Park
 *
 */
public class User implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3559420712697202465L;
	
	private String epId;
	private String empId;
	private String empcd;
	private String hname;
	private String email;
	private String jikck;
	private String jikgb;
	private String dptcd;
	private String dptnm;
	private String preDptcd;
	private String preDptnm;
	private String superempcd;
	private String superHname;
	private String superEmail;
	private String costCenter;
	private String costCenterName;
	private Integer userId;
	
	public String getEpId() {
		return epId;
	}
	public void setEpId(String epId) {
		this.epId = epId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpcd() {
		return empcd;
	}
	public void setEmpcd(String empcd) {
		this.empcd = empcd;
	}
	public String getHname() {
		return hname;
	}
	public void setHname(String hname) {
		this.hname = hname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJikck() {
		return jikck;
	}
	public void setJikck(String jikck) {
		this.jikck = jikck;
	}
	public String getJikgb() {
		return jikgb;
	}
	public void setJikgb(String jikgb) {
		this.jikgb = jikgb;
	}
	public String getDptcd() {
		return dptcd;
	}
	public void setDptcd(String dptcd) {
		this.dptcd = dptcd;
	}
	public String getDptnm() {
		return dptnm;
	}
	public void setDptnm(String dptnm) {
		this.dptnm = dptnm;
	}
	public String getPreDptcd() {
		return preDptcd;
	}
	public void setPreDptcd(String preDptcd) {
		this.preDptcd = preDptcd;
	}
	public String getPreDptnm() {
		return preDptnm;
	}
	public void setPreDptnm(String preDptnm) {
		this.preDptnm = preDptnm;
	}
	public String getSuperempcd() {
		return superempcd;
	}
	public void setSuperempcd(String superempcd) {
		this.superempcd = superempcd;
	}
	public String getSuperHname() {
		return superHname;
	}
	public void setSuperHname(String superHname) {
		this.superHname = superHname;
	}
	public String getSuperEmail() {
		return superEmail;
	}
	public void setSuperEmail(String superEmail) {
		this.superEmail = superEmail;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getCostCenterName() {
		return costCenterName;
	}
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ADMIN"));
		return authorities;
	}
	@Override
	public String getPassword() {
		return "";
	}
	@Override
	public String getUsername() {
		return this.empcd;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
