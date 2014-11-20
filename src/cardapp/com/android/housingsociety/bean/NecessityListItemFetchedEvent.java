package cardapp.com.android.housingsociety.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 保存NecessityListItem集合数据
 *
 * @author CardApp@ZuoQing
 *
 */
public class NecessityListItemFetchedEvent implements Serializable{
	private static final long serialVersionUID = 1L;
	List<NecessityListItem> list;
	
	public NecessityListItemFetchedEvent() {
	}
	
	public NecessityListItemFetchedEvent(List<NecessityListItem> list) {
		this.list = list;
	}

	public List<NecessityListItem> getList() {
		return list;
	}

	public void setList(List<NecessityListItem> list) {
		this.list = list;
	}
}
