package cardapp.com.android.housingsociety.bean;

import java.io.Serializable;

/**
 * 生活必备Bean
 *
 * @author CardApp@ZuoQing
 *
 */
public class Necessity implements Serializable{
	private static final long serialVersionUID = 1L;
	Long NecessityId; // 对应ID
	String NecessityChiName; // 中文名
	String NecessityEngName; // 英文名
	Long NecessityClassId; // 一级ID
	String NecessityImage; // 图片路径
	String NecessityPhone; // 电话号码
	String Location; // 坐标
	String ImageList; // 图片数组
	Integer NecessityClassType; // 1:三级2:二级列表3:二级详细
	Integer Type; // 1:港铁2：巴士3：的士4：图书馆5：街市6：邮局

	public Long getNecessityId() {
		return NecessityId;
	}

	public void setNecessityId(Long necessityId) {
		NecessityId = necessityId;
	}

	public String getNecessityChiName() {
		return NecessityChiName;
	}

	public void setNecessityChiName(String necessityChiName) {
		NecessityChiName = necessityChiName;
	}

	public String getNecessityEngName() {
		return NecessityEngName;
	}

	public void setNecessityEngName(String necessityEngName) {
		NecessityEngName = necessityEngName;
	}

	public Long getNecessityClassId() {
		return NecessityClassId;
	}

	public void setNecessityClassId(Long necessityClassId) {
		NecessityClassId = necessityClassId;
	}

	public String getNecessityImage() {
		return NecessityImage;
	}

	public void setNecessityImage(String necessityImage) {
		NecessityImage = necessityImage;
	}

	public String getNecessityPhone() {
		return NecessityPhone;
	}

	public void setNecessityPhone(String necessityPhone) {
		NecessityPhone = necessityPhone;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getImageList() {
		return ImageList;
	}

	public void setImageList(String imageList) {
		ImageList = imageList;
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
