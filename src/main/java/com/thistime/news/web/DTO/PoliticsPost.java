package com.thistime.news.web.DTO;

public class PoliticsPost {
	
	public int no;
	public String date;
	public String ModifyDate;
	public String title;
	public int part;
	public String article;
	public int good;
	public int bad;

	public PoliticsPost(int no, String date, String modifyDate, String title, int part, String article, int good,
			int bad) {
		super();
		this.no = no;
		this.date = date;
		ModifyDate = modifyDate;
		this.title = title;
		this.part = part;
		this.article = article;
		this.good = good;
		this.bad = bad;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getModifyDate() {
		return ModifyDate;
	}
	public void setModifyDate(String modifyDate) {
		ModifyDate = modifyDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPart() {
		return part;
	}
	public void setPart(int part) {
		this.part = part;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public int getGood() {
		return good;
	}
	public void setGood(int good) {
		this.good = good;
	}
	public int getBad() {
		return bad;
	}
	public void setBad(int bad) {
		this.bad = bad;
	}

	@Override
	public String toString() {
		return "PoliticsPost [no=" + no + ", date=" + date + ", ModifyDate=" + ModifyDate + ", title=" + title
				+ ", part=" + part + ", article=" + article + ", good=" + good + ", bad=" + bad + ", toString()="
				+ super.toString() + "]";
	}
	
}
