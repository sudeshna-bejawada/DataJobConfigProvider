package com.lloyds.data.job.configuration;

import lombok.Data;

@Data
public class Secret {	
	
	
	private String vaultURL;
	

	private String accessKey;
	
	private String secretKey;

	}
