package br.com.mhbp.controller;

import org.junit.Test;

import br.com.mhbp.domain.DiffContainer;
import br.com.mhbp.domain.DiffExecutor;
import br.com.mhbp.domain.DiffResponse;
import br.com.mhbp.enums.DiffResultEnum;
import br.com.mhbp.enums.MissingOperatorEnum;
import br.com.mhbp.exception.MissingOperatorException;
import br.com.mhbp.util.Base64Util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class DiffExecutorTest {

	@Test
	public void shouldThrowMissingBothOperatorException() {
		try {
			DiffExecutor.execute(null);
		} catch (MissingOperatorException e) {
			assertThat(e.getMessage(), is(MissingOperatorEnum.MISSING_BOTH_OPERATOR.value()));
		}
	}

	@Test
	public void shouldThrowMissingRightOperatorException() {
		try {
			DiffContainer dc = new DiffContainer();
			dc.setLeft("asd");
			DiffExecutor.execute(dc);
		} catch (MissingOperatorException e) {
			assertThat(e.getMessage(), is(MissingOperatorEnum.MISSING_RIGHT_OPERATOR.value()));
		}
	}

	@Test
	public void shouldThrowMissingLeftOperatorException() {
		try {
			DiffContainer dc = new DiffContainer();
			dc.setRight("asd");
			DiffExecutor.execute(dc);
		} catch (MissingOperatorException e) {
			assertThat(e.getMessage(), is(MissingOperatorEnum.MISSING_LEFT_OPERATOR.value()));
		}
	}

	@Test
	public void shouldGetResponseEqual() {
			DiffContainer dc = new DiffContainer();
			dc.setRight("asd");
			dc.setLeft("asd");
			DiffResponse dr = DiffExecutor.execute(dc);
			assertThat(dr.getResultDiff(), is(DiffResultEnum.EQUAL.value()));
	}
	
	@Test
	public void shouldGetResponseNotEqualSize() {
			DiffContainer dc = new DiffContainer();
			dc.setRight("asdqwe");
			dc.setLeft("asd");
			DiffResponse dr = DiffExecutor.execute(dc);
			assertThat(dr.getResultDiff(), is(DiffResultEnum.NOT_EQUAL_SIZE.value()));
	}
	
	@Test
	public void shouldGetResponseDifferences() {
			DiffContainer dc = new DiffContainer();
			dc.setRight(Base64Util.encodeString("abce"));
			dc.setLeft( Base64Util.encodeString("abcd"));
			DiffResponse dr = DiffExecutor.execute(dc);
			assertThat(dr.getResultDiff(), is(DiffResultEnum.DIFFERENCES.value()));
			assertThat(dr.getPositionWithDifferences(), contains(3));
	}
}
