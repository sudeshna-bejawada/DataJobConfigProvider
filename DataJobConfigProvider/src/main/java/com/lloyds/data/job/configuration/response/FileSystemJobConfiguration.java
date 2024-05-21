package com.lloyds.data.job.configuration.response;

import java.util.List;

import com.lloyds.data.job.configuration.dto.Column;

import lombok.Data;

@Data
public class FileSystemJobConfiguration  {
	
	private List<Column> columns;

}
