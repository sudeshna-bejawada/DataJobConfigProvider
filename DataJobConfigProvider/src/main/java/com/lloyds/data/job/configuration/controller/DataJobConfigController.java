package com.lloyds.data.job.configuration.controller;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.lloyds.data.job.configuration.ConfigProperties;
import com.lloyds.data.job.configuration.dto.DestinationInfo;
import com.lloyds.data.job.configuration.dto.SourceInfo;
import com.lloyds.data.job.configuration.dto.AccessInfo;
import com.lloyds.data.job.configuration.exception.BadRequestFormatException;
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

	private static Logger logger = LoggerFactory.getLogger(DataJobConfigController.class);

	@PostMapping("v1/api/accessdata")
	public ResponseEntity<?> postJobConfigurationAccessData(@RequestBody AccessInfo accessInfo) throws IOException {

		String jobName = accessInfo.getJobName();
		dataConfigHelper.validateJobName(jobName);
		dataConfigHelper.validateAccessInfo(accessInfo);

		String fileName = dataConfigHelper.getFileName(jobName, configProperties.getAccessDataSuffix());
		String path = configProperties.getPath();
		logger.info("fileName " + fileName);

		Resource resource = resourceLoader.getResource("file:" + path + fileName);

		if (!resource.exists()) {
			ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
			objectMapper.writeValue(new File(path + fileName), accessInfo);
			return ResponseEntity.status(HttpStatus.CREATED).body(" Created access data job file " + fileName+ " in the Location " + path);

		} else {
			return ResponseEntity.status(HttpStatus.FOUND).body(fileName + "file  already exist in the given Location " + path);
		}

	}

	@PostMapping("v1/api/destinationDetail")
	public ResponseEntity<?> postDestinationDetails(@RequestBody DestinationInfo destinationInfo)
			throws IOException, BadRequestFormatException {

		String jobName = destinationInfo.getJobName();

		dataConfigHelper.validateJobName(jobName);
		dataConfigHelper.validateBucket(destinationInfo.getDestination().getBucket());
		dataConfigHelper.validateFileFormats(destinationInfo.getDestination().getFormat());

		String fileName = dataConfigHelper.getFileName(jobName, configProperties.getDestinationInfoSuffix());
		String path = configProperties.getPath();
		logger.info("fileName " + fileName);

		Resource resource = resourceLoader.getResource("file:" + path + fileName);

		if (!resource.exists()) {
			ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
			objectMapper.writeValue(new File(path + fileName), destinationInfo);
			return ResponseEntity.status(HttpStatus.CREATED).body(" Created destination details job file " + fileName + " in the Location " + path);

		} else {
			return ResponseEntity.status(HttpStatus.FOUND).body(fileName + "file  already exist in the given Location " + path);
		}

	}

	@PostMapping("v1/api/jobconfigurations")
	public ResponseEntity<?> postJobConfigurationInfo(@RequestBody SourceInfo sourceInfo) throws IOException {

		String jobName = sourceInfo.getJobName();
		dataConfigHelper.validateJobName(jobName);

		String fileName = dataConfigHelper.getFileName(jobName, configProperties.getJobConfigInfoSuffix());
		String path = configProperties.getPath();
		logger.info("fileName " + fileName);

		Resource resource = resourceLoader.getResource("file:" + path + fileName);

		if (!resource.exists()) {
			ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
			objectMapper.writeValue(new File( path + fileName), sourceInfo);
			return ResponseEntity.status(HttpStatus.CREATED).body(" Created job configuration file " + fileName + " in the Location " + path);

		} else {
			return ResponseEntity.status(HttpStatus.FOUND).body(fileName + "file  already exist in the given Location " + path);
		}

	}

}
