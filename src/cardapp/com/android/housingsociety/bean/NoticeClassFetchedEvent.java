package cardapp.com.android.housingsociety.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 保存NoticeClass集合数据
 *
 * @author CardApp@ZuoQing
 *
 */
public class NoticeClassFetchedEvent implements Serializable{
	private static final long serialVersionUID = 1L;
	List<NoticeClass> list;
	
	public NoticeClassFetchedEvent() {
	}
	
	public NoticeClassFetchedEvent(List<NoticeClass> list) {
		this.list = list;
	}

	public List<NoticeClass> getList() {
		return list;
	}

	public void setList(List<NoticeClass> list) {
		this.list = list;
	}
}
