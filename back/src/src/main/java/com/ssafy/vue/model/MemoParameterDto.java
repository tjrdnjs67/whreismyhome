package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "MemoParameterDto : 댓글 파라미터 정보", description = "댓글을 얻기위한 부가적인 파라미터정보.")
public class MemoParameterDto {
	
	@ApiModelProperty(value = "현재 게시글의 번호")
	private int articleno;
	@ApiModelProperty(value = "현재 페이지 번호")
	private int pg;
	@ApiModelProperty(value = "페이지당 글갯수")
	private int spp;
	@ApiModelProperty(value = "페이지의 시작 글번호")
	private int start;

	public MemoParameterDto() {
		pg = 1;
		spp = 20;
	}
	
	
	public int getArticleno() {
		return articleno;
	}



	public void setArticleno(int articleno) {
		this.articleno = articleno;
	}



	public int getPg() {
		return pg;
	}

	public void setPg(int pg) {
		pg = pg == 0 ? 1 : pg;
		this.pg = pg;
	}
	
	public int getSpp() {
		return spp;
	}

	public void setSpp(int spp) {
		this.spp = spp;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	@Override
	public String toString() {
		return "MemoParameterDto [pg=" + pg + ", spp=" + spp + ", start=" + start + "]";
	}

}
