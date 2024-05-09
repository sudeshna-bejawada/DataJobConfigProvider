package com.lloyds.data.job.configuration;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;


@Data
public class JobConfiguration {	
	
	private String schema_keyspace;	
	private List<Table> tables;
	

}
