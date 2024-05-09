package com.lloyds.data.job.configuration.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lloyds.data.job.configuration.ConfigProperties;

@Component
public class DataConfigHelper {

	@Autowired
	private ConfigProperties configProperties;

	public String getFileName(String jobName, String suffix) {

		return jobName + "_" + suffix + configProperties.getFileExtension();
	}

}
