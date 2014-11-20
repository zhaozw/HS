package cardapp.com.android.housingsociety.bean;

import java.io.Serializable;

/**
 * 通告列表条目Bean
 *
 * @author CardApp@ZuoQing
 *
 */
public class NoticeItem implements Serializable{
	private static final long serialVersionUID = 1L;
	String ChiTitle;	// 中文名称
	String EngTitle;	// 英文名称
	String EmergencyNoticeDeadline;	// 紧急通告到期日
	Boolean IsEmergencyNotice;	// 是否紧急通告
	Boolean IsImportantNotice;	// 是否重要通告
	Boolean IsNewNotice;	// 是否新通告
	String NewNoticeDeadline;	// 新通告到期日
	Long NoticeClassid;	// 对应分类ID
	Long NoticeId;	// 对应ID
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
	public String getEmergencyNoticeDeadline() {
		return EmergencyNoticeDeadline;
	}
	public void setEmergencyNoticeDeadline(String emergencyNoticeDeadline) {
		EmergencyNoticeDeadline = emergencyNoticeDeadline;
	}
	public Boolean getIsEmergencyNotice() {
		return IsEmergencyNotice;
	}
	public void setIsEmergencyNotice(Boolean isEmergencyNotice) {
		IsEmergencyNotice = isEmergencyNotice;
	}
	public Boolean getIsImportantNotice() {
		return IsImportantNotice;
	}
	public void setIsImportantNotice(Boolean isImportantNotice) {
		IsImportantNotice = isImportantNotice;
	}
	public Boolean getIsNewNotice() {
		return IsNewNotice;
	}
	public void setIsNewNotice(Boolean isNewNotice) {
		IsNewNotice = isNewNotice;
	}
	public String getNewNoticeDeadline() {
		return NewNoticeDeadline;
	}
	public void setNewNoticeDeadline(String newNoticeDeadline) {
		NewNoticeDeadline = newNoticeDeadline;
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
	
}
