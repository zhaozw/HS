package cardapp.com.android.housingsociety.bean;

import java.io.Serializable;

/**
 * 屋苑联系信息Bean
 *
 * @author CardApp@ZuoQing
 *
 */
public class EstateInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	String ChiTitle;	// 中文标题
	String EngTitle;	// 英文标题
	String ChiPhone;	// 电话
	String EngPhone;	// 电话
	Long Type;	// 0:电话 1:传真 2: 电邮 3:网址 4:无内容
	
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
	public String getChiPhone() {
		return ChiPhone;
	}
	public void setChiPhone(String chiPhone) {
		ChiPhone = chiPhone;
	}
	public String getEngPhone() {
		return EngPhone;
	}
	public void setEngPhone(String engPhone) {
		EngPhone = engPhone;
	}
	public Long getType() {
		return Type;
	}
	public void setType(Long type) {
		Type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
