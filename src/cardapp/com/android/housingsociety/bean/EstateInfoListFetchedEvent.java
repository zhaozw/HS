package cardapp.com.android.housingsociety.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 保存NecessityClass集合数据
 *
 * @author CardApp@ZuoQing
 *
 */
public class EstateInfoListFetchedEvent implements Serializable{
	private static final long serialVersionUID = 1L;
	List<EstateInfo> list;
	
	public EstateInfoListFetchedEvent() {
	}
	
	public EstateInfoListFetchedEvent(List<EstateInfo> list) {
		this.list = list;
	}

	public List<EstateInfo> getList() {
		return list;
	}

	public void setList(List<EstateInfo> list) {
		this.list = list;
	}
}
