package com.ego.commons.pojo;

/**
 * java代码和jsp交互公共类
 * @author Bernard
 *
 */
public class EgoResult {
	private int status;
	//交互信息
	private Object data;
	//传递消息
	private String msg;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
