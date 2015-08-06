package com.cwa;


import com.cwa.message.ISendMessage;

public class SendMessageService {
	public static ISendMessage send;

	public void setSend(ISendMessage send) {
		SendMessageService.send = send;
	}

	

}
