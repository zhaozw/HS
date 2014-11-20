package cardapp.com.android.housingsociety.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 保存NoticeItem集合数据
 *
 * @author CardApp@ZuoQing
 *
 */
public class NoticeItemFetchedEvent implements Serializable{
	private static final long serialVersionUID = 1L;
	List<NoticeItem> list;
	
	public NoticeItemFetchedEvent() {
	}
	
	public NoticeItemFetchedEvent(List<NoticeItem> list) {
		this.list = list;
	}

	public List<NoticeItem> getList() {
		return list;
	}

	public void setList(List<NoticeItem> list) {
		this.list = list;
	}
}
