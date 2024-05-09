package com.lloyds.data.job.configuration;

import lombok.Data;

@Data
public class Destination {
	private String format;
	private String bucket;
	private String path;
	private String name;
	private String prefix;

}
