package br.com.mhbp.common;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

	private T data;
	private int status;
	
	public ApiResponse(){}
	
	public ApiResponse(int status) {
		this.status = status;
	}
	
	public ApiResponse(int status, T data) {
		this.status = status;
		this.data = data;
	}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public static <T> ApiResponse<T> buildSuccess() {
		return new ApiResponse<T>(HttpStatus.OK.value());
	}
	
	public static <T> ApiResponse<T> buildSuccess(T data) {
		return new ApiResponse<T>(HttpStatus.OK.value(), data);
	}
	
}
