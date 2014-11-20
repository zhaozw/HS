package cardapp.com.android.housingsociety.bean;

import java.io.Serializable;

/**
 * 通告详细Bean
 *
 * @author CardApp@ZuoQing
 *
 */
public class Notice implements Serializable {
	private static final long serialVersionUID = 1L;
	String ChiTitle;		// 中文名称
	String EngTitle;		// 英文名称
	Long NoticeClassid;		// 对应分类ID
	Long NoticeId;		// 对应ID
	String ContentFormat;		// 格式
	String MobileChiContent;		// 中文文件地址
	String MobileEngContent;		// 英文文件地址
	Long Integral;		// 浏览可获取积分
	
	public Long getIntegral() {
		return Integral;
	}
	public void setIntegral(Long integral) {
		Integral = integral;
	}
	public String getChiTitle() {
		return ChiTitle;
	}
	public void setChiTitle(String chiTitle) {
		ChiTitle = chiTitle;
	}
	public String getEngTitle() {
		return EngTitle;
	}
	public void setEngTitle(String engTitle) {
		EngTitle = engTitle;
	}
	public Long getNoticeClassid() {
		return NoticeClassid;
	}
	public void setNoticeClassid(Long noticeClassid) {
		NoticeClassid = noticeClassid;
	}
	public Long getNoticeId() {
		return NoticeId;
	}
	public void setNoticeId(Long noticeId) {
		NoticeId = noticeId;
	}
	public String getContentFormat() {
		return ContentFormat;
	}
	public void setContentFormat(String contentFormat) {
		ContentFormat = contentFormat;
	}
	public String getMobileChiContent() {
		return MobileChiContent;
	}
	public void setMobileChiContent(String mobileChiContent) {
		MobileChiContent = mobileChiContent;
	}
	public String getMobileEngContent() {
		return MobileEngContent;
	}
	public void setMobileEngContent(String mobileEngContent) {
		MobileEngContent = mobileEngContent;
	}

}
