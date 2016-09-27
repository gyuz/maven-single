package exercise2;

import java.io.*;
import java.util.*;
import org.apache.commons.lang3.text.StrBuilder;

public class ProcessFile{
	private FileData file;
	private ProcessMap mapOperations;	
	private static int row = 0;

	ProcessFile(String filePath, ProcessMap mapOperations){
		this.file = new FileData(filePath);
		this.mapOperations = mapOperations;
	}
	
	protected int getRow(){
		return row;	
	}
	
	protected void setRow(int row){
		this.row = row;
	}
	
	protected boolean readFile(){
		String lineText = "", key = "", value = "";
		int column = 0;		
		boolean rowComplete = false, isKey = false;	

			try{
				FileInputStream inFile = new FileInputStream(file.getFilePath());
				BufferedReader buffer = new BufferedReader(new InputStreamReader(inFile));
				
				while((lineText = buffer.readLine()) != null) {
					column = 0;
					isKey = true;
					for(int i=0; i<lineText.length();i++) {
						if (lineText.charAt(i) == '(') {
							if (lineText.charAt(i+1) == '(' && lineText.charAt(i+2) == '('){
								if(isKey) {
									key = key + lineText.charAt(i);
								} else {
									value = value + lineText.charAt(i);
								}	
								i+=2;
							} else { 
								rowComplete = false;
							}
						} else if (lineText.charAt(i) == ')') {
							if ( i+2 < lineText.length() && lineText.charAt(i+1) == ')' && lineText.charAt(i+2) == ')'){
								if(isKey) {
									key = key + lineText.charAt(i);
								} else {
									value = value + lineText.charAt(i);
								}	
								i+=2;
							} else { 
								mapOperations.addRow(key, value, row+""+column);
								key = "";
								value = "";
								column++;
								isKey = true;
							}	
						} else if (lineText.charAt(i) == ',') {
							if (lineText.charAt(i+1) == ',' && lineText.charAt(i+2) == ','){
								if(isKey) {
									key = key + lineText.charAt(i);
								   } else {
									value = value + lineText.charAt(i);
								   }	
									i+=2;
								} else { 
									isKey = false;
								}								
						} else {
						   if(isKey) {
							key = key + lineText.charAt(i);
						   } else {
							value = value + lineText.charAt(i);
						   }		
						}								
					}
					row++;
				}
				
				row--;
				inFile.close();
				buffer.close();

				mapOperations.originalCopy();
			}	
			catch(IOException ie){
				System.out.println("Error Reading file.");
				return false;
			}	
			return true;
	}
	
	protected void writeOriginalData(){
		writeToFile(mapOperations.getOriginalFormatMap(), mapOperations.getOriginalDataMap());
		mapOperations.resetMap();	
	}
	
	protected void rewriteData(){
		writeToFile(mapOperations.getTableFormatMap(), mapOperations.getDataMap());	
	}	

	protected void writeToFile(Map<String, String> tableFormatMap, Map<String, String> dataMap){
		try{		
			FileWriter outFile = new FileWriter(file.getFilePath());
			BufferedWriter writer = new BufferedWriter(outFile);
			StrBuilder strBuilder = new StrBuilder();
		
			String previousKey = "X";
			for (Map.Entry<String, String> entry : tableFormatMap.entrySet()){
				if(previousKey.equals("X") || previousKey.equals(entry.getKey().substring(0,1))){
					strBuilder.append("("+getNewString(entry.getValue())+","+getNewString(dataMap.get(entry.getValue()))+")");
				}
				else{
					strBuilder.appendNewLine();
					strBuilder.append("("+getNewString(entry.getValue())+","+getNewString(dataMap.get(entry.getValue()))+")");
				}	
				previousKey=entry.getKey().substring(0,1);
			}
			
			writer.write(strBuilder.toString());
			writer.close();
		}
		catch(IOException ie){
			System.out.println("Error writing to file");
		}	
		
	}

	protected String getNewString(String data){
		String newData = "";
		for(int i=0; i<data.length(); i++){
			if(data.charAt(i) == '(' || data.charAt(i) == ')' || data.charAt(i) == ','){
				for(int j=0; j<3; j++){
					newData += data.charAt(i);				
				}
			} else {
				newData += data.charAt(i);			
			}		
		}
		return newData;
	}	

}
