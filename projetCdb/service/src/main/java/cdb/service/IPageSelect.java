package cdb.service;

public interface IPageSelect {
	int getNbPages() ;
	int getCurrentPage();
	int getOffset() ;
	void next();
	void get(int page);
	void previews();
	void setMaxResult(int maxResult);
	int getMaxResult();
	void setCurrentPage(int currentPage);
	int getCursor();
}
