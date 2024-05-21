package com.lloyds.data.job.configuration.dto;

public enum SourceType {

	DATABASE("database"), FILE_SYSTEM("file System"), IMAGE("image"), VIDEO("video");

	SourceType(String sourceType) {
		this.setSourceType(sourceType);
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	private String sourceType;

}
