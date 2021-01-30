package com.linkin.service;

import com.linkin.model.SMSDTO;

public interface SMSService {

	void sendSMS(SMSDTO smsDto);
}
