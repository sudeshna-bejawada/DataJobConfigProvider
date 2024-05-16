package com.lloyds.data.job.configuration;

import java.util.List;

import lombok.Data;

@Data
public class Source {
	
	private String source_url;
	private Boolean is_streaming;
	private List<JobConfiguration> job_Configurations;
	

}
