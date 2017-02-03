package org.utils;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {
	private String errno;
	private String errdesc;
	private T data;
	private long timestamp;

	public ApiResponse() {
	}

	public ApiResponse(T data, String code, String msg) {
		this.data = data;
		this.errno = code;
		this.errdesc = msg;
	}

	public ApiResponse<T> success() {
		return success(null);
	}

	public ApiResponse<T> success(T data) {
		this.errno = ApiResponseCode.success.code;
		this.errdesc = ApiResponseCode.success.msg;
		this.data = data;
		this.timestamp = (System.currentTimeMillis() / 1000L);
		return this;
	}

	public ApiResponse<T> error() {
		return error(ApiResponseCode.system_error);
	}

	public ApiResponse<T> error(String code, String message) {
		this.errno = code;
		this.errdesc = message;
		this.timestamp = (System.currentTimeMillis() / 1000L);
		return this;
	}

	public ApiResponse<T> error(String code, String message, T t) {
		this.errno = code;
		this.errdesc = message;
		this.data = t;
		this.timestamp = (System.currentTimeMillis() / 1000L);
		return this;
	}

	public ApiResponse<T> error(ApiResponseCode errCode) {
		return error(errCode.code, errCode.msg);
	}

	public ApiResponse<T> error(ApiResponseCode errCode, T t) {
		return error(errCode.code, errCode.msg, t);
	}

	public boolean apiSuccess() {
		return ApiResponseCode.success.code.equals(this.errno);
	}

	public boolean apiError() {
		return !ApiResponseCode.success.code.equals(this.errno);
	}

	public String getErrno() {
		return this.errno;
	}

	public String getErrdesc() {
		return this.errdesc;
	}

	public T getData() {
		return this.data;
	}

	public long getTimestamp() {
		return this.timestamp;
	}

	public ApiResponse<T> setErrno(String code) {
		this.errno = code;
		return this;
	}

	public ApiResponse<T> setErrdesc(String msg) {
		this.errdesc = msg;
		return this;
	}

	public ApiResponse<T> setData(T data) {
		this.data = data;
		return this;
	}

	public static boolean apiSuccess(ApiResponseCode apiCode) {
		return ApiResponseCode.success.code.equals(apiCode.code);
	}
}
