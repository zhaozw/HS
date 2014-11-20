package cardapp.com.android.housingsociety.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 保存NecessityClass集合数据
 *
 * @author CardApp@ZuoQing
 *
 */
public class NecessityClassListFetchedEvent implements Serializable{
	private static final long serialVersionUID = 1L;
	List<NecessityClass> list;
	
	public NecessityClassListFetchedEvent() {
	}
	
	public NecessityClassListFetchedEvent(List<NecessityClass> list) {
		this.list = list;
	}

	public List<NecessityClass> getList() {
		return list;
	}

	public void setList(List<NecessityClass> list) {
		this.list = list;
	}
}
