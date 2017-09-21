package com.exhibit.rpc.model;

import java.util.List;

import lombok.Data;

@Data
public class RegisterServiceResponse extends BaseResponse{

	private static final long serialVersionUID = 1L;
	
	public RegisterServiceResponse(int code){
		super.setCode(code);
	}
	public RegisterServiceResponse(){
	}
	private List<String> lists;
}
