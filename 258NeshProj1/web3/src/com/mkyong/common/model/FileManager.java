package com.mkyong.common.model;

public class FileManager 
{

	private String fieldName;
    private String fieldFileName;
    private String fieldFileString;
    // + getters, setters
    
    public FileManager()
    {
    	
    }
    //<Information>Accessors</Information>
    public String getFieldName()
    {
    	return fieldName;
    }

    public String getFieldFileName()
    {
    	return fieldFileName;
    }
    public String getFieldFileString()
    {
    	return fieldFileString;
    }
    //<Information>Mutators</Information>
    public void setFieldName(String fieldName)
    {
    	this.fieldName = fieldName;
    }


    public void setFieldFileName(String fieldFileName)
    {
    	this.fieldFileName = fieldFileName ;
    } 
    
    public void setFieldFileString(String fieldFileString)
    {
    	this.fieldFileString = fieldFileString ;
    } 
}
