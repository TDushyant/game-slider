package com.learnon.games.tiling.slider;

public class HighScoreBean implements Comparable<HighScoreBean> {
	String gameType;
	String name;
	String time;
	public HighScoreBean(String gameType, String name, String timeElapsed) {
		if (name.trim().length() == 0) {
			this.name = "Unknown";
		}
		else {
			this.name = name;
		}
		this.gameType = gameType;
		this.time = timeElapsed;
	}
	public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(gameType).append("|").append(name).append("|").append(time);
        return sb.toString();
    }
	
	@Override
	public int compareTo(HighScoreBean anotherBean) {
		// TODO Auto-generated method stub
        return this.getTime().compareTo(anotherBean.getTime());
	}
	public CharSequence getScoreRecord() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\t\t\t\t\t\t").append(time);
        return sb.toString();
	}
}
