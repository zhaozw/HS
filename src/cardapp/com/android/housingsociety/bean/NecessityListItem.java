package cardapp.com.android.housingsociety.bean;

import java.io.Serializable;

/**
 * 生活必备项列表条目(三级列表)
 *
 * @author CardApp@ZuoQing
 *
 */
public class NecessityListItem implements Serializable{
	private static final long serialVersionUID = 1L;
	Long NecessityListId;	// 对应ID
	Long NecessityId;	// 二级ID
	String FirstLayerChiLeft;	// 一层中文左
	String FirstLayerEngLeft;	// 一层英文左
	String FirstLayerChiRight;	// 一层中文右
	String FirstLayerEngRight;	// 一层英文右
	String Price;	// 价格
	String StepOneChiUp;	// 
	String StepOneEngUp;	// 
	String StepOneChiDown;	// 
	String StepOneEngDown;	// 
	String StepTwoChiUp;	// 
	String StepTwoEngUp;	// 
	String StepTwoChiDown;	// 
	String StepTwoEngDown;	// 
	String StepThreeChiUp;	// 
	String StepThreeEngUp;	// 
	String StepThreeChiDown;	// 
	String StepThreeEngDown;	// 
	Boolean IsBigBus;	// 是否大巴
	
	public NecessityListItem() {
		
	}
	
	public Long getNecessityListId() {
		return NecessityListId;
	}
	public void setNecessityListId(Long necessityListId) {
		NecessityListId = necessityListId;
	}
	public Long getNecessityId() {
		return NecessityId;
	}
	public void setNecessityId(Long necessityId) {
		NecessityId = necessityId;
	}
	public String getFirstLayerChiLeft() {
		return FirstLayerChiLeft;
	}
	public void setFirstLayerChiLeft(String firstLayerChiLeft) {
		FirstLayerChiLeft = firstLayerChiLeft;
	}
	public String getFirstLayerEngLeft() {
		return FirstLayerEngLeft;
	}
	public void setFirstLayerEngLeft(String firstLayerEngLeft) {
		FirstLayerEngLeft = firstLayerEngLeft;
	}
	public String getFirstLayerChiRight() {
		return FirstLayerChiRight;
	}
	public void setFirstLayerChiRight(String firstLayerChiRight) {
		FirstLayerChiRight = firstLayerChiRight;
	}
	public String getFirstLayerEngRight() {
		return FirstLayerEngRight;
	}
	public void setFirstLayerEngRight(String firstLayerEngRight) {
		FirstLayerEngRight = firstLayerEngRight;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}
	public String getStepOneChiUp() {
		return StepOneChiUp;
	}
	public void setStepOneChiUp(String stepOneChiUp) {
		StepOneChiUp = stepOneChiUp;
	}
	public String getStepOneEngUp() {
		return StepOneEngUp;
	}
	public void setStepOneEngUp(String stepOneEngUp) {
		StepOneEngUp = stepOneEngUp;
	}
	public String getStepOneChiDown() {
		return StepOneChiDown;
	}
	public void setStepOneChiDown(String stepOneChiDown) {
		StepOneChiDown = stepOneChiDown;
	}
	public String getStepOneEngDown() {
		return StepOneEngDown;
	}
	public void setStepOneEngDown(String stepOneEngDown) {
		StepOneEngDown = stepOneEngDown;
	}
	public String getStepTwoChiUp() {
		return StepTwoChiUp;
	}
	public void setStepTwoChiUp(String stepTwoChiUp) {
		StepTwoChiUp = stepTwoChiUp;
	}
	public String getStepTwoEngUp() {
		return StepTwoEngUp;
	}
	public void setStepTwoEngUp(String stepTwoEngUp) {
		StepTwoEngUp = stepTwoEngUp;
	}
	public String getStepTwoChiDown() {
		return StepTwoChiDown;
	}
	public void setStepTwoChiDown(String stepTwoChiDown) {
		StepTwoChiDown = stepTwoChiDown;
	}
	public String getStepTwoEngDown() {
		return StepTwoEngDown;
	}
	public void setStepTwoEngDown(String stepTwoEngDown) {
		StepTwoEngDown = stepTwoEngDown;
	}
	public String getStepThreeChiUp() {
		return StepThreeChiUp;
	}
	public void setStepThreeChiUp(String stepThreeChiUp) {
		StepThreeChiUp = stepThreeChiUp;
	}
	public String getStepThreeEngUp() {
		return StepThreeEngUp;
	}
	public void setStepThreeEngUp(String stepThreeEngUp) {
		StepThreeEngUp = stepThreeEngUp;
	}
	public String getStepThreeChiDown() {
		return StepThreeChiDown;
	}
	public void setStepThreeChiDown(String stepThreeChiDown) {
		StepThreeChiDown = stepThreeChiDown;
	}
	public String getStepThreeEngDown() {
		return StepThreeEngDown;
	}
	public void setStepThreeEngDown(String stepThreeEngDown) {
		StepThreeEngDown = stepThreeEngDown;
	}
	public Boolean getIsBigBus() {
		return IsBigBus;
	}
	public void setIsBigBus(Boolean isBigBus) {
		IsBigBus = isBigBus;
	}

}
