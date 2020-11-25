package com.board.paging;

public class PaginationInfo {
	
	private Criteria criteria; // 페이징 계산에 필요한 파라미터들이 담긴 클래스.
	private int totalRecordCount; // 전체 데이터 개수.
	private int totalPageCount; // 전체 페이지 개수.
	private int firstPage; // 페이지 리스트의 첫 번째 페이지 번호.
	private int lastPage; // 페이지 리스트이 마지막 페이지 번호.
	private int firstRecordIndex; // SQL 조건절에 사용되는 첫 RNUM.
	private int lastRecordIndex; // SQL 조건절에 사용되는 마지막 RNUM.
	private boolean hasPreviousPage; // 이전 페이지 존재 여부.
	private boolean hasNextPage; // 다음 페이지 존재 여부.
	
	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getFirstRecordIndex() {
		return firstRecordIndex;
	}

	public void setFirstRecordIndex(int firstRecordIndex) {
		this.firstRecordIndex = firstRecordIndex;
	}

	public int getLastRecordIndex() {
		return lastRecordIndex;
	}

	public void setLastRecordIndex(int lastRecordIndex) {
		this.lastRecordIndex = lastRecordIndex;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	// 생성자 : 잘못된 값이 들어왔을 때, 각각의 if 조건을 통해 기본값을 지정.
	public PaginationInfo(Criteria criteria) {
		if (criteria.getCurrentPageNo() < 1) {
			criteria.setCurrentPageNo(1);
		}
		if (criteria.getRecordsPerPage() < 1 || criteria.getRecordsPerPage() > 100) {
			criteria.setRecordsPerPage(10);
		}
		if (criteria.getPageSize() < 5 || criteria.getPageSize() > 20) {
			criteria.setPageSize(10);
		}
		this.criteria = criteria; // 사용자의 요청 파라미터 정보를 가진 criteria를 PaginationInfo 클래스의 criteria에 저장.
	}
	
	// 파라미터로 넘어온 전체 데이터 개수를 PaginationInfo 클래스의 전체 데이터 개수에 저장
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalPageCount = totalRecordCount;
		if(totalRecordCount > 0) {
			calculation(); // 전체 데이터 개수가 1개 이상이면 페이지 번호를 계산하는 calculation 메서드를 실행.
		}
	}
	
	private void calculation() {
		// 전체 페이지 수. (현재 페이지 번호가 전체 페이지 수보다 크면 현재 페이지 번호에 전체 페이지 수를 저장) 
		// ((전체 데이터 개수 - 1) / 페이지당 출력할 데이터 개수) + 1을 하면 전체 페이지 개수를 구할 수 있다.
		totalPageCount = ((totalRecordCount - 1) / criteria.getRecordsPerPage()) + 1;
		if (criteria.getCurrentPageNo() > totalPageCount) {
			criteria.setCurrentPageNo(totalPageCount);
		}
		
		// 페이지 리스트의 첫 페이지 번호.
		// ((현재 페이지 번호 - 1) / 화면 하단의 페이지 개수) * 화면 하단의 페이지 개수 + 1을 하면 가장 좌측의 페이지 번호를 구할 수 있다.
		firstPage = ((criteria.getCurrentPageNo() - 1) / criteria.getPageSize()) * criteria.getPageSize() + 1;
		
		// 페이지 리스트의 마지막 페이지 번호. (마지막 페이지가 전체 페이지 수보다 크면 마지막 페이지에 전체 페이지 수를 저장.
		// (첫 페이지 번호 + 화면 하단의 페이지 개수) - 1을 하면 마지막 페이지 번호를 구할 수 있습니다.
		lastPage = firstPage + criteria.getPageSize() - 1;
		if (lastPage > totalPageCount) {
			lastPage = totalPageCount;
		}
		
		// SQL 조건절에 사용되는 첫 RNUM.
		// Criteria 클래스의 getStartPage 메서드를 대신해서 LIMIT 구문의 첫 번째 값에 들어갈 데이터.
		firstRecordIndex = (criteria.getCurrentPageNo() - 1) * criteria.getRecordsPerPage();
		
		// SQL의 조건절에 사용되는 마지막 RNUM.
		lastRecordIndex = criteria.getCurrentPageNo() * criteria.getRecordsPerPage();
		
		// 이전 페이지 존재 여부.
		hasPreviousPage = firstPage != 1;
		
		// 다음 페이지 존재 여부.
		// (마지막 페이지 번호 * 페이지당 출력할 데이터의 개수)가 전체 데이터 개수보다 크거나 같으면 false, 작으면 true.
		hasNextPage = (lastPage * criteria.getRecordsPerPage()) < totalRecordCount;
	}
}
