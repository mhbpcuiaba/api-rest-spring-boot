package br.com.mhbp.enums;

public enum DiffResultEnum {

	EQUAL("EQUAL"), NOT_EQUAL_SIZE("NOT_EQUAL_SIZE"), DIFFERENCES("DIFFERENCES");

	private String value;

	DiffResultEnum(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
