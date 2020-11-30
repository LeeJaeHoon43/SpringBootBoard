package com.board.domain;

public class AttachDTO extends CommentDTO{
	private Long idx; // 파일 번호.
	private Long boardIdx; // 게시글 번호.
	private String originalName; // 원본 파일명.
	private String saveName; // 저장 파일명.
	private long size; // 파일크기.
	
	public Long getIdx() {
		return idx;
	}
	public void setIdx(Long idx) {
		this.idx = idx;
	}
	public Long getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(Long boardIdx) {
		this.boardIdx = boardIdx;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
}
