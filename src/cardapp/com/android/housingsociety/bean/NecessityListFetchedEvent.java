package cardapp.com.android.housingsociety.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 保存Necessity集合数据
 *
 * @author CardApp@ZuoQing
 *
 */
public class NecessityListFetchedEvent implements Serializable{
	private static final long serialVersionUID = 1L;
	List<Necessity> list;
	
	public NecessityListFetchedEvent() {
	}
	
	public NecessityListFetchedEvent(List<Necessity> list) {
		this.list = list;
	}

	public List<Necessity> getList() {
		return list;
	}

	public void setList(List<Necessity> list) {
		this.list = list;
	}
}
