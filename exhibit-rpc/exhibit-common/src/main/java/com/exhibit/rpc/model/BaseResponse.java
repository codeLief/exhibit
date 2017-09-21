package com.exhibit.rpc.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.exhibit.rpc.util.ConstantsUtil;

import lombok.Data;

@Data
public class BaseResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int code;
	
	private String message;

	public BaseResponse(){
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean resultCheck(){
		
		return ConstantsUtil.CODE_200 == this.getCode();
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
