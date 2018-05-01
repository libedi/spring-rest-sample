package com.skplanet.impay.ccs.framework.security.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.skplanet.impay.ccs.common.util.CustomStringUtils;
import com.skplanet.impay.ccs.common.model.MenuModel;
import com.skplanet.impay.ccs.common.model.CntcInfo;

public class CustomUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1372446262197924905L;
	
	private String userId; 	//사용자 ID : TN_USER.USER_ID 
    private String password;
    private String userSeq;
    private String userNm;       //사용자이름
    private String sno;          //사용자주민번호 앞자리
    private int cntcSeq = 0;     //연락처정보 테이블 PK
    private String admTypCd;     //관리자유형코드
    private String itgrAdjAuthYn;//통합정산권한여부
    private int pwdErrCnt=0;
    private String useYn;        //사용여부 (isEnabled)
    private String ip;
    private String mstYn;
    
    private String hdnUsername;  //**로 보여지는 계정
    private String hdnIp;        //**로 보여지는 IP

	
	private List<RoleModel> authorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    
    private String sysId;
    private String loginId;
    private String clntIp;
    
    private CntcInfo cntcInfo; 	//연락처 정보 
    private List<MenuModel> menu1st;
	private List<MenuModel> menu2nd;
	private List<MenuModel> menu3rd;
	private List<MenuModel> menuList;
	private List<String> menuIdList;
    
    public String getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
	}

	public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }
    
    public int getCntcSeq() {
        return cntcSeq;
    }
    
    public void setCntcSeq(int cntcSeq) {
        this.cntcSeq = cntcSeq;
    }

    public String getAdmTypCd() {
        return admTypCd;
    }
    
    public void setAdmTypCd(String admTypCd) {
        this.admTypCd = admTypCd;
    }

    public String getItgrAdjAuthYn() {
        return itgrAdjAuthYn;
    }
    
    public void setItgrAdjAuthYn(String itgrAdjAuthYn) {
        this.itgrAdjAuthYn = itgrAdjAuthYn;
    }
    
    public int getPwdErrCnt() {
        return pwdErrCnt;
    }
    
    public void setPwdErrCnt(int pwdErrCnt) {
        this.pwdErrCnt = pwdErrCnt;
    }
    
    public String getIp() {
        return ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
        this.hdnIp = CustomStringUtils.replaceIpForAsterik(this.ip);
    }
    
    public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getMstYn() {
		return mstYn;
	}

	public void setMstYn(String mstYn) {
		this.mstYn = mstYn;
	}

	public String getHdnUsername() {
        return hdnUsername;
    }
    
    public void setHdnUsername(String hdnUsername) {
        this.hdnUsername = hdnUsername;
    }

    public String getHdnIp() {
        return hdnIp;
    }
    
    public void setHdnIp(String hdnIp) {
        this.hdnIp = hdnIp;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
        this.hdnUsername = CustomStringUtils.replaceUsernameForAsterik(this.userId);
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public String getPassword() {
        return password;
    }
    public String getUserId() {
        return userId;
    }
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }
    @Override
    public boolean isAccountNonLocked() {
        if(this.pwdErrCnt < 5) {
            this.accountNonLocked = true;
        } else {
            this.accountNonLocked = false;
        }
        return this.accountNonLocked;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }
    @Override
    public boolean isEnabled() {
        if(this.useYn.equals("Y")) {
            this.enabled = true;
        } else {
            this.enabled = false;
        }
        return this.enabled;
    }

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getClntIp() {
		return clntIp;
	}

	public void setClntIp(String clntIp) {
		this.clntIp = clntIp;
	}
	@Override
	public String toString() {
		return "CustomUserDetails [userId=" + userId + ", password=" + password + ", userSeq=" + userSeq + ", userNm="
				+ userNm + ", sno=" + sno + ", cntcSeq=" + cntcSeq + ", admTypCd=" + admTypCd + ", itgrAdjAuthYn="
				+ itgrAdjAuthYn + ", pwdErrCnt=" + pwdErrCnt + ", useYn=" + useYn + ", ip=" + ip + ", mstYn=" + mstYn
				+ ", hdnUsername=" + hdnUsername + ", hdnIp=" + hdnIp + ", authorities=" + authorities
				+ ", accountNonExpired=" + accountNonExpired + ", accountNonLocked=" + accountNonLocked
				+ ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled + ", sysId=" + sysId
				+ ", loginId=" + loginId + ", clntIp=" + clntIp + "]";
	}

	public CntcInfo getCntcInfo() {
		return cntcInfo;
	}

	public void setCntcInfo(CntcInfo cntcInfo) {
		this.cntcInfo = cntcInfo;
	}

	public List<MenuModel> getMenu1st() {
		return menu1st;
	}

	public void setMenu1st(List<MenuModel> menu1st) {
		this.menu1st = menu1st;
	}

	public List<MenuModel> getMenu2nd() {
		return menu2nd;
	}

	public void setMenu2nd(List<MenuModel> menu2nd) {
		this.menu2nd = menu2nd;
	}

	public List<MenuModel> getMenu3rd() {
		return menu3rd;
	}

	public void setMenu3rd(List<MenuModel> menu3rd) {
		this.menu3rd = menu3rd;
	}

	public List<MenuModel> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<MenuModel> menuList) {
		this.menuList = menuList;
	}

	public List<String> getMenuIdList() {
		return menuIdList;
	}

	public void setMenuIdList(List<String> menuIdList) {
		this.menuIdList = menuIdList;
	}
	
	
}