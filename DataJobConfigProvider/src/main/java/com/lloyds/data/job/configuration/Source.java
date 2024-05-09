package com.lloyds.data.job.configuration;

import java.util.List;

import lombok.Data;

@Data
public class Source {
	
	private String source_url;	
	private List<JobConfiguration> job_Configurations;
	

}
