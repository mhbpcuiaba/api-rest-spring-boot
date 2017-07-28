package br.com.mhbp.controller;

import static br.com.mhbp.common.ApiResponse.buildSuccess;
import static br.com.mhbp.domain.DiffExecutor.execute;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.mhbp.common.ApiResponse;
import br.com.mhbp.domain.DiffContainer;
import br.com.mhbp.domain.DiffResponse;
import br.com.mhbp.enums.MissingOperatorEnum;
import br.com.mhbp.exception.MissingOperatorException;

@RestController
public class DiffController {

	private static Map<String, DiffContainer> map = new HashMap<String, DiffContainer>();

	
	@PostMapping("/v1/diff/{id}/left")
	public @ResponseBody ApiResponse<Void> diffLeft(@PathVariable String id, @RequestBody Data data) {
		getDiffContainer(id).setLeft(data.getValue());
		return buildSuccess();
	}

	@PostMapping("/v1/diff/{id}/right")
	public @ResponseBody ApiResponse<Void> diffRight(@PathVariable String id, @RequestBody Data data) {
		getDiffContainer(id).setRight(data.getValue());
		return buildSuccess();
	}

	@PostMapping("/v1/diff/{id}")
	public @ResponseBody ApiResponse<DiffResponse> diff(@PathVariable String id) {

		if (!map.containsKey(id)) {
			throw new MissingOperatorException(MissingOperatorEnum.MISSING_BOTH_OPERATOR.value());
		}

		return buildSuccess(execute(map.get(id)));
	}
	
	private DiffContainer getDiffContainer(String id) {
		DiffContainer dc;
		if (map.containsKey(id)) {
			dc = map.get(id);
		} else {
			dc = new DiffContainer();
			map.put(id, dc);
		}

		return dc;
	}

}
