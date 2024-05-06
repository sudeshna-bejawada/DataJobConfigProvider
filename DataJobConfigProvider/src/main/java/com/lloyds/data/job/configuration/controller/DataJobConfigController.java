package com.lloyds.data.job.configuration.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.lloyds.data.job.configuration.ConfigProperties;
import com.lloyds.data.job.configuration.Configuration;
import com.lloyds.data.job.configuration.DestinationInfo;
import com.lloyds.data.job.configuration.Secret;
import com.lloyds.data.job.configuration.exception.FileUnSupportedException;
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

	@PostMapping("api/accessdata")
	public ResponseEntity<?> postJobConfigurationAccessData(@RequestBody Configuration configuration)
			throws IOException {

		if (configuration.getSecret().getVaultURL().isEmpty() || configuration.getJobName().isEmpty()
				|| configuration.getSecret().getAccessKey().isEmpty()) {
			throw new NullPointerException(" JobName, VaultURL and SecretKey must have value");
		}

		String jobName = configuration.getJobName();

		String fileName = dataConfigHelper.getFileName(jobName, configProperties.getAccessDataSuffix());

		System.out.println("fileName " + fileName);

		Resource resource = resourceLoader.getResource("file:" + configProperties.getPath() + fileName);

		if (!resource.exists()) {
			ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
			objectMapper.writeValue(new File(configProperties.getPath() + fileName), configuration);

			return ResponseEntity.status(HttpStatus.CREATED).body(" Created access data job file " + fileName);

		} else {
			return ResponseEntity.status(HttpStatus.FOUND).body(jobName + "file  already exist in the given path");
		}

	}

	@PostMapping("api/destinationDetail")
	public ResponseEntity<?> postDestinationDetails(@RequestBody DestinationInfo destination) throws IOException {

		// Stream<String> stream =
		// Stream.of(configProperties.getSupportedFileFormats().split(","));

		// stream.filter(S-> S.equals(destination.getFormat())).findAny().orElse(null);

		String jobName = destination.getJobName();

		String fileName = dataConfigHelper.getFileName(jobName, configProperties.getDestinationInfoSuffix());

		System.out.println("fileName " + fileName);

		Resource resource = resourceLoader.getResource("file:" + configProperties.getPath() + fileName);

		if (!resource.exists()) {
			ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
			objectMapper.writeValue(new File(configProperties.getPath() + fileName), destination);

			return ResponseEntity.status(HttpStatus.CREATED).body(" Created destination details job file " + fileName);

		} else {
			return ResponseEntity.status(HttpStatus.FOUND).body(jobName + "file  already exist in the given path");
		}

	}

}
