package com.lloyds.data.job.configuration.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.lloyds.data.job.configuration.AccessInfo;
import com.lloyds.data.job.configuration.ConfigProperties;
import com.lloyds.data.job.configuration.DestinationInfo;
import com.lloyds.data.job.configuration.Secret;
import com.lloyds.data.job.configuration.SourceInfo;
import com.lloyds.data.job.configuration.helper.DataConfigHelper;

@Controller
@RequestMapping("datajobconfig")
public class DataJobConfigController {

	@Autowired
	private ConfigProperties configProperties;

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	DataConfigHelper dataConfigHelper;

	@PostMapping("v1/api/accessdata")
	public ResponseEntity<?> postJobConfigurationAccessData(@RequestBody AccessInfo accessInfo)
			throws IOException {
		
		Secret secret = accessInfo.getConfiguration().getSecret();

		if (secret.getVault_url().isEmpty() || accessInfo.getJobName().isEmpty()
				|| secret.getAccess_key().isEmpty()) {
			throw new NullPointerException(" JobName, vault_url and secret_key must have value");
		}

		String jobName = accessInfo.getJobName();

		String fileName = dataConfigHelper.getFileName(jobName, configProperties.getAccessDataSuffix());

		System.out.println("fileName " + fileName);

		Resource resource = resourceLoader.getResource("file:" + configProperties.getPath() + fileName);

		if (!resource.exists()) {
			ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
			objectMapper.writeValue(new File(configProperties.getPath() + fileName), accessInfo);

			return ResponseEntity.status(HttpStatus.CREATED).body(" Created access data job file " + fileName);

		} else {
			return ResponseEntity.status(HttpStatus.FOUND).body(fileName + "file  already exist in the given path");
		}

	}

	@PostMapping("v1/api/destinationDetail")
	public ResponseEntity<?> postDestinationDetails(@RequestBody DestinationInfo destinationInfo) throws IOException {

		// Stream<String> stream =
		// Stream.of(configProperties.getSupportedFileFormats().split(","));

		// stream.filter(S-> S.equals(destination.getFormat())).findAny().orElse(null);
			

		String jobName = destinationInfo.getJobName();

		String fileName = dataConfigHelper.getFileName(jobName, configProperties.getDestinationInfoSuffix());

		System.out.println("fileName " + fileName);

		Resource resource = resourceLoader.getResource("file:" + configProperties.getPath() + fileName);

		if (!resource.exists()) {
			ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
			objectMapper.writeValue(new File(configProperties.getPath() + fileName), destinationInfo);

			return ResponseEntity.status(HttpStatus.CREATED).body(" Created destination details job file " + fileName);

		} else {
			return ResponseEntity.status(HttpStatus.FOUND).body(fileName + "file  already exist in the given path");
		}

	}
	
	
	@PostMapping("v1/api/jobconfigurations")
	public ResponseEntity<?> postJobConfigurationInfo(@RequestBody SourceInfo sourceInfo) throws IOException {

	    String jobName = sourceInfo.getJobName();    
	    

		String fileName = dataConfigHelper.getFileName(jobName, configProperties.getJobConfigInfoSuffix());

		System.out.println("fileName " + fileName);

		Resource resource = resourceLoader.getResource("file:" + configProperties.getPath() + fileName);

		if (!resource.exists()) {
			ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
			objectMapper.writeValue(new File(configProperties.getPath() + fileName), sourceInfo);

			return ResponseEntity.status(HttpStatus.CREATED).body(" Created job configuration file " + fileName);

		} else {
			return ResponseEntity.status(HttpStatus.FOUND).body(fileName + "file  already exist in the given path");
		}

	}

}
