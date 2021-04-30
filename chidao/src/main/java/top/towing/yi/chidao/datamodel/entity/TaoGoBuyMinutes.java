package top.towing.yi.chidao.datamodel.entity;

public class TaoGoBuyMinutes extends TaoMinutes {
	private int goBuyCount;
	
	public TaoGoBuyMinutes(String timestamp) {
		this.setTimestamp(timestamp);
	}
	
	public void countGoBuyMessage() {
		this.goBuyCount++;
	}

	public int getGoBuyCount() {
		return goBuyCount;
	}

	public void setGoBuyCount(int goBuyCount) {
		this.goBuyCount = goBuyCount;
	}
}
