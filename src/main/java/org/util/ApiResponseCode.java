package org.util;

public enum ApiResponseCode {
	success("0", "ok"), request_parameter_error("1", "请求参数错误"), access_deny("2", "拒绝方法"), system_error("3",
			"系统错误,请稍后重试"), no_data("4", "没有有效数据"), operation_fail("5", "操作失败"), request_timeout("7", "请求超时");

	public String code = null;
	public String msg = null;

	private ApiResponseCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
