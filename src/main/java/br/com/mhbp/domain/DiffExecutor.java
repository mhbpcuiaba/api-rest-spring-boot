package br.com.mhbp.domain;

import java.util.ArrayList;
import java.util.List;

import br.com.mhbp.enums.DiffResultEnum;
import br.com.mhbp.enums.MissingOperatorEnum;
import br.com.mhbp.exception.MissingOperatorException;
import br.com.mhbp.util.Base64Util;

public abstract class DiffExecutor {

	public static DiffResponse execute(DiffContainer df) {
		
		if (df == null) {
			throw new MissingOperatorException(MissingOperatorEnum.MISSING_BOTH_OPERATOR.value());
		}
		
		if (df.getLeft() == null) {
			throw new MissingOperatorException(MissingOperatorEnum.MISSING_LEFT_OPERATOR.value());
		}
		
		if (df.getRight() == null) {
			throw new MissingOperatorException(MissingOperatorEnum.MISSING_RIGHT_OPERATOR.value());
		}
		
		String leftDecoded = Base64Util.decodeString(df.getLeft());
		String rightDecoded = Base64Util.decodeString(df.getRight());
		
		if (leftDecoded.equals(rightDecoded)) {
			return buildResponseEqual();
		} else if (leftDecoded.length() != rightDecoded.length()) {
			return buildResponseNotEqualSize();
		} else {
			List<Integer> list = new ArrayList<Integer>();
			
			for (int i = 0; i < leftDecoded.length(); i++) {
			
				if (leftDecoded.charAt(i) != rightDecoded.charAt(i)) {
					list.add(i);
				}
			}
			
			return buildResponseDifferences(list);
		}
	}
	
	private static DiffResponse buildResponseEqual() {
		return new DiffResponse(DiffResultEnum.EQUAL.value());
	}
	
	private static DiffResponse buildResponseNotEqualSize() {
		return new DiffResponse(DiffResultEnum.NOT_EQUAL_SIZE.value());
	}
	
	private static DiffResponse buildResponseDifferences(List<Integer> positionsWithDifferences) {
		return new DiffResponse(DiffResultEnum.DIFFERENCES.value(), positionsWithDifferences);
	}
}
