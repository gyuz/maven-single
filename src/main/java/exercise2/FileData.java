package exercise2;

import java.io.*;

public class FileData{
	private static File filePath;
	
	FileData(String filePath){
		this.filePath = new File(filePath);
	}
	
	protected void setFilePath(String filePath){
		this.filePath = new File(filePath);	
	}
	
	protected File getFilePath(){
		return filePath;	
	}
}


