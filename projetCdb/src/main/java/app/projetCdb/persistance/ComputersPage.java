package app.projetCdb.persistance;

import org.springframework.stereotype.Service;

@Service
public class ComputersPage implements IPageSelect {
	
	private int nbPages;
	private int maxResult;
	private int currentPage; 
	private int offset;
	
	
	@Override
	public int getCursor() {
		return (currentPage==1?0:currentPage*offset);
	}


	public ComputersPage(int currentPage, int offset) {
		this.currentPage = currentPage;
		this.offset = offset;
	}

	@Override
	public int getNbPages() {
		return maxResult/offset;
	}
	
	@Override
	public int getCurrentPage() {
		return currentPage;
	}

	@Override
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public void setNbPages(int nbPages) {
		this.nbPages = nbPages;
	}

	@Override
	public void next() {
		currentPage+=offset;
	}

	@Override
	public void previews() {
		currentPage=(currentPage-offset>=0)?(currentPage-=offset):0;
	}

	@Override
	public void get(int page) {
		currentPage=0;
		
	}
	
	@Override
	public int getMaxResult() {
		return maxResult;
	}
	@Override
	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}


	
	
}
