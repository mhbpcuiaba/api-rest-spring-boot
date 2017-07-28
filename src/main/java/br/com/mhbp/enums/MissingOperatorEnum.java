package br.com.mhbp.enums;

public enum MissingOperatorEnum {

	MISSING_LEFT_OPERATOR("MISSING_LEFT_OPERATOR"), 
	MISSING_RIGHT_OPERATOR("MISSING_RIGHT_OPERATOR"),
	MISSING_BOTH_OPERATOR("MISSING_BOTH_OPERATOR");

	private String value;

	MissingOperatorEnum(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
