package exercise2;

import java.util.*;

public class DataMap{
	private static LinkedHashMap<String,String> dataMap;
	private static LinkedHashMap<String, String> tableFormatMap;
	private static LinkedHashMap<String, String> originalDataMap;	
	private static LinkedHashMap<String, String> originalFormatMap;

	DataMap(){
		 dataMap = new LinkedHashMap<String,String>();
		 tableFormatMap = new LinkedHashMap<String,String>();
		 originalDataMap = new LinkedHashMap<String,String>();
		 originalFormatMap = new LinkedHashMap<String,String>();
	}
	
	protected void setDataMap(Map<String,String> newMap){
		dataMap.clear();			
		dataMap.putAll(newMap);
	}

	protected void setTableFormatMap(Map<String,String> newMap){
		tableFormatMap.clear();
		tableFormatMap.putAll(newMap);
	}

	protected void setOriginalDataMap(Map<String,String> newMap){
		originalDataMap.clear();
		originalDataMap.putAll(newMap);
	}

	protected void setOriginalFormatMap(Map<String,String> newMap){
		originalFormatMap.clear();
		originalFormatMap.putAll(newMap);
	}

	protected LinkedHashMap<String,String> getDataMap(){
		return dataMap;
	}

	protected LinkedHashMap<String,String> getTableFormatMap(){
		return tableFormatMap;
	}

	protected LinkedHashMap<String,String> getOriginalDataMap(){
		return originalDataMap;
	}

	protected LinkedHashMap<String,String> getOriginalFormatMap(){
		return originalFormatMap;
	}

	protected void addDataMap(String key, String value){
		dataMap.put(key, value);	
	}
	
	protected void addTableFormat(String key, String value){
		tableFormatMap.put(key, value);	
	}

	protected void removeDataMap(String key){
		dataMap.remove(key);	
	}

	protected void editDataMapValue(String index, String newValue){
		dataMap.put(tableFormatMap.get(index), newValue);	
	}

	protected void editFormatValue(String key, String value){
		tableFormatMap.put(key, value);	
	}
	
}

