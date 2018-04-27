package kr.co.poscoict.push.send.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 패밀리 PUSH
 * @author Sangjun, Park
 *
 */
@JsonInclude(Include.NON_NULL)
public class FamilyPush {
	private final String appIdIphone;
	private final String appIdAndroid;
	private String mailAddress;
	private MsgType arpvMsgType;
	private String aprvzMessage;
	private int aprvCount;
	private boolean appSound;
	private PushCustomMsg customMsg;
	
	public FamilyPush(String appIdIphone, String appIdAndroid) {
		this.appIdIphone = appIdIphone;
		this.appIdAndroid = appIdAndroid;
	}
	
	public String getAppIdIphone() {
		return appIdIphone;
	}
	public String getAppIdAndroid() {
		return appIdAndroid;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public MsgType getArpvMsgType() {
		return arpvMsgType;
	}
	public void setArpvMsgType(MsgType arpvMsgType) {
		this.arpvMsgType = arpvMsgType;
	}
	public String getAprvzMessage() {
		return aprvzMessage;
	}
	public void setAprvzMessage(String aprvzMessage) {
		this.aprvzMessage = aprvzMessage;
	}
	public int getAprvCount() {
		return aprvCount;
	}
	public void setAprvCount(int aprvCount) {
		this.aprvCount = aprvCount;
	}
	public boolean isAppSound() {
		return appSound;
	}
	public void setAppSound(boolean appSound) {
		this.appSound = appSound;
	}
	@JsonGetter("customMsg")
	public String getCustomMsg() {
		try {
			return new ObjectMapper().writeValueAsString(customMsg);
		} catch (JsonProcessingException e) {
			return "";
		}
	}
	public void setCustomMsg(PushCustomMsg customMsg) {
		this.customMsg = customMsg;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (appIdIphone != null)
			builder.append("appIdIphone=").append(appIdIphone).append("&");
		if (appIdAndroid != null)
			builder.append("appIdAndroid=").append(appIdAndroid).append("&");
		if (mailAddress != null)
			builder.append("mailAddress=").append(mailAddress).append("&");
		if (arpvMsgType != null)
			builder.append("arpvMsgType=").append(arpvMsgType).append("&");
		if (aprvzMessage != null)
			builder.append("aprvzMessage=").append(aprvzMessage).append("&");
		builder.append("aprvCount=").append(aprvCount).append("&appSound=").append(appSound).append("&");
		if (customMsg != null)
			builder.append("customMsg=").append(getCustomMsg());
		return builder.toString();
	}
}
