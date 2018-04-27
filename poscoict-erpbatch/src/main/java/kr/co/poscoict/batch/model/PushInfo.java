package kr.co.poscoict.batch.model;

/**
 * 푸시정보 Entity
 * @author Sangjun, Park
 *
 */
public class PushInfo {
	private String empcd;
	private int preCnt;
	private int nowCnt;
	private String pushYn;
	private String pushSTime;
	private String pushETime;
	private String pushWeek;
	
	public String getEmpcd() {
		return empcd;
	}
	public void setEmpcd(String empcd) {
		this.empcd = empcd;
	}
	public int getPreCnt() {
		return preCnt;
	}
	public void setPreCnt(int preCnt) {
		this.preCnt = preCnt;
	}
	public int getNowCnt() {
		return nowCnt;
	}
	public void setNowCnt(int nowCnt) {
		this.nowCnt = nowCnt;
	}
	public String getPushYn() {
		return pushYn;
	}
	public void setPushYn(String pushYn) {
		this.pushYn = pushYn;
	}
	public String getPushSTime() {
		return pushSTime;
	}
	public void setPushSTime(String pushSTime) {
		this.pushSTime = pushSTime;
	}
	public String getPushETime() {
		return pushETime;
	}
	public void setPushETime(String pushETime) {
		this.pushETime = pushETime;
	}
	public String getPushWeek() {
		return pushWeek;
	}
	public void setPushWeek(String pushWeek) {
		this.pushWeek = pushWeek;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PushInfo [");
		if (empcd != null)
			builder.append("empcd=").append(empcd).append(", ");
		builder.append("preCnt=").append(preCnt).append(", nowCnt=").append(nowCnt).append(", ");
		if (pushYn != null)
			builder.append("pushYn=").append(pushYn).append(", ");
		if (pushSTime != null)
			builder.append("pushSTime=").append(pushSTime).append(", ");
		if (pushETime != null)
			builder.append("pushETime=").append(pushETime).append(", ");
		if (pushWeek != null)
			builder.append("pushWeek=").append(pushWeek);
		builder.append("]");
		return builder.toString();
	}
}
