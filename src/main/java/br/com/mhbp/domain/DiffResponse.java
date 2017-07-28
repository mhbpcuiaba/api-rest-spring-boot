package br.com.mhbp.domain;

import java.util.List;

public class DiffResponse {

	private String resultDiff;
	private List<Integer> positionWithDifferences;

	public DiffResponse() {
	}

	public DiffResponse(String resultDiff) {
		this.resultDiff = resultDiff;
	}

	public DiffResponse(String resultDiff, List<Integer> positionWithDifferences) {
		this.resultDiff = resultDiff;
		this.positionWithDifferences = positionWithDifferences;
	}

	public String getResultDiff() {
		return resultDiff;
	}

	public void setResultDiff(String resultDiff) {
		this.resultDiff = resultDiff;
	}

	public List<Integer> getPositionWithDifferences() {
		return positionWithDifferences;
	}

	public void setPositionWithDifferences(List<Integer> positionWithDifferences) {
		this.positionWithDifferences = positionWithDifferences;
	}
}
