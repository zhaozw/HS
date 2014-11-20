package cardapp.com.android.housingsociety.bean;

/**
 * 菜单的每个条目
 *
 * @author CardApp@ZuoQing
 *
 */
public class MyMenuItem {
	String title;		// 标题
	int image;			// 图片，可以空
	String action;		// 点击动作
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
}
