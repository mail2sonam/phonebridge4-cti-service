package com.eupraxia.telephony.constants;

public interface WebStreamProperties {
	static final String TokenUrl = "http://34.134.149.85:8080/auth/realms/phonebridge-cti/protocol/openid-connect/token";
	static final String WebStreamUrl = "http://104.154.188.48:8081/stream/accid/topicname/";
	static final String PostDataUrl = "http://104.154.188.48:8081/stream";
	static final String Content_Type="application/x-www-form-urlencoded";
	static final String grant_type="client_credentials";
	static final String reqScope="openid";
	static final String client_id="phonebridge-cti-client";
	static final String client_secret="pV2TSCP3uU1E9COUDJ8d0FsKSmGCabiR";
	
	
	
	
}
