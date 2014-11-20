package cardapp.com.android.housingsociety.bean;

import java.io.Serializable;

/**
 * 通告类型Bean
 *
 * @author CardApp@ZuoQing
 *
 */
public class NoticeClass implements Serializable {
	private static final long serialVersionUID = 1L;
	String EstateId;// 屋苑ID
	Long NoticeClassId;// 对应ID
	String NoticeClassChiName;// 中文名称
	String NoticeClassEngName;// 英文名称
	Long NoticeCounts;// 对应通告数目

	public String getEstateId() {
		return EstateId;
	}

	public void setEstateId(String estateId) {
		EstateId = estateId;
	}

	public Long getNoticeClassId() {
		return NoticeClassId;
	}

	public void setNoticeClassId(Long noticeClassId) {
		NoticeClassId = noticeClassId;
	}

	public String getNoticeClassChiName() {
		return NoticeClassChiName;
	}

	public void setNoticeClassChiName(String noticeClassChiName) {
		NoticeClassChiName = noticeClassChiName;
	}

	public String getNoticeClassEngName() {
		return NoticeClassEngName;
	}

	public void setNoticeClassEngName(String noticeClassEngName) {
		NoticeClassEngName = noticeClassEngName;
	}

	public Long getNoticeCounts() {
		return NoticeCounts;
	}

	public void setNoticeCounts(Long noticeCounts) {
		NoticeCounts = noticeCounts;
	}

}
