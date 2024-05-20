package com.lloyds.data.job.configuration.dto;

import java.util.List;

import lombok.Data;


@Data
public class JobConfiguration {	
	
	private String schema_keyspace;	
	private List<Table> tables;
	

}
