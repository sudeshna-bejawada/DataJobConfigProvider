package com.lloyds.data.job.configuration.dto;

import lombok.Data;

@Data
public class DestinationInfo {
	
	private String jobName;
	private String jobDesc;
	
	private Destination destination;
	

}
