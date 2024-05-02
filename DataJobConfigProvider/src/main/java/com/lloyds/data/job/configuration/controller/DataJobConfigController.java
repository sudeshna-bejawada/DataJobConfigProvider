package com.lloyds.data.job.configuration.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.lloyds.data.job.configuration.ConfigProperties;
import com.lloyds.data.job.configuration.Configuration;
import com.lloyds.data.job.configuration.Secret;

@Controller
@RequestMapping("datajobconfig")
public class DataJobConfigController {
	
	@Autowired
	private  ConfigProperties configProperties;

	@Autowired
	Configuration configuration;

	@Autowired
	ResourceLoader resourceLoader;
	
	 

	@PostMapping("api/accessdata/{jobName}")
	public ResponseEntity<?> PostJobConfigurationAccessdata(@PathVariable String jobName,
			@RequestParam String jobDescription, @RequestParam String vaultURL, @RequestParam String accessKey,
			@RequestParam String secretKey) throws IOException {
		
		
		if(vaultURL.isEmpty() ||secretKey.isEmpty()) {
			 throw new NullPointerException(" Both VaultURL and SecretKey should have some value");
		}
		String fileName = jobName + configProperties.getFileExtension();

		System.out.println("fileName " + fileName);

		Resource resource = resourceLoader.getResource("file:" + configProperties.getPath() + fileName);

		if (!resource.exists()) {
			configuration.setJobname(jobName);
			configuration.setJobDescription(jobDescription);

			Secret secret = configuration.getSecret();
			secret.setVaultURL(vaultURL);
			secret.setAccessKey(accessKey);
			secret.setSecretKey(secretKey);

			
				ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
				objectMapper.writeValue(new File(configProperties.getPath() + fileName), configuration);

				return ResponseEntity.status(HttpStatus.CREATED).body(" Created data job file " + jobName );
			

		} else {
			return ResponseEntity.status(HttpStatus.FOUND).body(jobName + "file  already exist in the given path" );
		}

	}

}
