package com.lloyds.data.job.configuration.helper;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lloyds.data.job.configuration.AccessInfo;
import com.lloyds.data.job.configuration.ConfigProperties;
import com.lloyds.data.job.configuration.Secret;
import com.lloyds.data.job.configuration.exception.BadRequestFormatException;

@Component
public class DataConfigHelper {

	@Autowired
	private ConfigProperties configProperties;
	private static Logger logger = LoggerFactory.getLogger(DataConfigHelper.class);

	public String getFileName(String jobName, String suffix) {
		return jobName + "_" + suffix + configProperties.getFileExtension();
	}

	public void validateBucket(String bucket) throws BadRequestFormatException {
		if (bucket == null || bucket.isEmpty()) {
			throw new NullPointerException("bucket must have value");
		} else if (bucket.length()> 4 && !bucket.substring(0,4).equals("GCP:")) {
			throw new BadRequestFormatException("bucket name must start with 'GCP:'");
		} else if (bucket.length()<= 4) {
			throw new BadRequestFormatException("bucket name must have valid value");
		}

	}

	public void validateFileFormats(String format) throws BadRequestFormatException {
		if (format == null || format.isEmpty()) {
			throw new NullPointerException("format must have value");
		}
		List<String> supportedFormats = Arrays.asList(configProperties.getSupportedFileFormats().split(","));
		logger.info("supported formats are: " + supportedFormats);
		if (!supportedFormats.contains(format)) {
			throw new BadRequestFormatException("input file format " + format + " is not supported");
		}

	}

	public void validateAccessInfo(AccessInfo accessInfo) {

		Secret secret = accessInfo.getConfiguration().getSecret();
		if (secret.getVault_url() == null || secret.getVault_url().isEmpty()) {
			throw new NullPointerException("vault_url must have value");
		}

		if (secret.getAccess_key() == null || secret.getAccess_key().isEmpty()) {
			throw new NullPointerException("access key must have value");
		}

	}

	public void validateJobName(String jobName) {
		if (jobName == null || jobName.isEmpty()) {
			throw new NullPointerException("jobName must have value");
		}

	}

}
