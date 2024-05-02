package com.lloyds.data.job.configuration;

import org.springframework.stereotype.Component;

import jakarta.annotation.Nonnull;

@Component
public class Secret {
	
	@Nonnull
	private String vaultURL;
	
	private String accessKey;
	
	private String secretKey;

	public String getVaultURL() {
		return vaultURL;
	}

	public void setVaultURL(String vaultURL) {
		this.vaultURL = vaultURL;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	
	
	
	

}
