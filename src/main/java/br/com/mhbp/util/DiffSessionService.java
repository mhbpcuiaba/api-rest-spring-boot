package br.com.mhbp.util;

import org.springframework.stereotype.Service;

@Service
public class DiffSessionService {

	private DiffSession diffSession = new DiffSession();
	
	public String getLeft() {
		return diffSession.getLeft();
	}
	
	public String getRight() {
		return diffSession.getRight();
	}
	
	public void setLeft(String left) {
		diffSession.setLeft(left);
	}
	
	public void setRight(String right) {
		diffSession.setRight(right);
	}
}
