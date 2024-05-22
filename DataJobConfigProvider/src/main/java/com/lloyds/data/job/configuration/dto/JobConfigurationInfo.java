package com.lloyds.data.job.configuration.dto;

import java.util.List;

import com.lloyds.data.job.configuration.response.FileSystemJobConfiguration;
import com.lloyds.data.job.configuration.response.DataBaseJobConfigurations;

import lombok.Data;

@Data
public class JobConfigurationInfo {
	
	
	private String jobName;
	private String jobDescription;
	private SourceType source_type;
	private String source_url;
	private String schema;
	private String Path;
	private String file_type;
	private List<FileSystemJobConfiguration>  fileSystemJobConfigurations;
	private List<DataBaseJobConfigurations>  dataBaseJobConfigurations;
	
	
	
	

}
