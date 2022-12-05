package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "MemoDto : 댓글정보", description = "댓글의 정보를 나타낸다.")
public class MemoDto {
	
	@ApiModelProperty(value = "댓글 번호")
	private int memono;
	@ApiModelProperty(value = "회원 아이디")
	private String userid;
	@ApiModelProperty(value = "댓글 내용")
	private String comment;
	@ApiModelProperty(value = "댓글 생성일")
	private String memotime;
	@ApiModelProperty(value = "글번호")
	private int articleno;
	
	public int getMemono() {
		return memono;
	}
	public void setMemono(int memono) {
		this.memono = memono;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getMemotime() {
		return memotime;
	}
	public void setMemotime(String memotime) {
		this.memotime = memotime;
	}
	public int getArticleno() {
		return articleno;
	}
	public void setArticleno(int articleno) {
		this.articleno = articleno;
	}
	
	@Override
	public String toString() {
		return "MemoDto [memono=" + memono + ", userid=" + userid + ", comment=" + comment + ", memotime=" + memotime
				+ ", articleno=" + articleno + "]";
	}
	
}
