package cardapp.com.android.housingsociety.bean;

import java.io.Serializable;

/**
 * 生活必备类型
 *
 * @author CardApp@ZuoQing
 *
 */
public class NecessityClass implements Serializable {
	private static final long serialVersionUID = 1L;
	Long NecessityClassId; // 对应ID
	String NecessityClassChiName; // 中文名称, 巴士/小巴",
	String NecessityClassEngName; // 英文名称 Bus/Minibus",
	String NecessityClassImage; // 图片路径
								// http://hsadmin.cardapp.com.hk/uploads/NecessityFile/c0555516-1372-4e27-a656-22a6cc399916.png",
	Integer NecessityClassType; // 1:三级2:二级列表3:二级详细
	Integer Type; // 1:港铁2：巴士3：的士4：图书馆5：街市6：邮局

	public Long getNecessityClassId() {
		return NecessityClassId;
	}

	public void setNecessityClassId(Long necessityClassId) {
		NecessityClassId = necessityClassId;
	}

	public String getNecessityClassChiName() {
		return NecessityClassChiName;
	}

	public void setNecessityClassChiName(String necessityClassChiName) {
		NecessityClassChiName = necessityClassChiName;
	}

	public String getNecessityClassEngName() {
		return NecessityClassEngName;
	}

	public void setNecessityClassEngName(String necessityClassEngName) {
		NecessityClassEngName = necessityClassEngName;
	}

	public String getNecessityClassImage() {
		return NecessityClassImage;
	}

	public void setNecessityClassImage(String necessityClassImage) {
		NecessityClassImage = necessityClassImage;
	}

	public Integer getNecessityClassType() {
		return NecessityClassType;
	}

	public void setNecessityClassType(Integer necessityClassType) {
		NecessityClassType = necessityClassType;
	}

	public Integer getType() {
		return Type;
	}

	public void setType(Integer type) {
		Type = type;
	}
}
