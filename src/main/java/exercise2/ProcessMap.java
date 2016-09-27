package exercise2.service;

import java.util.*;
import org.apache.commons.lang3.text.StrBuilder;
import exercise2.model.*;

public class ProcessMap{
	private DataMap keyValueList;	
	
	public ProcessMap(){
		keyValueList = new DataMap();
	}	
	
	public DataMap getDataMapList(){
		return keyValueList;	
	}	
	
	public void addRow(String key, String value, String index){
		keyValueList.addDataMap(key, value);
		keyValueList.addTableFormat(index, key);
	}

	public void editKey(String index, String newKey){
		String prevKey = keyValueList.getTableFormatMap().get(index);
		String tempValue = keyValueList.getDataMap().get(prevKey);
		
		keyValueList.editFormatValue(index, newKey);
		keyValueList.removeDataMap(prevKey);
		keyValueList.addDataMap(newKey, tempValue);
	}

	public void editValue(String index, String newValue){
		keyValueList.editDataMapValue(index, newValue);	
	}
	
	public boolean checkKey(String key){
		return keyValueList.getDataMap().containsKey(key);	
	}

	public boolean checkIndex(String index){
		return keyValueList.getTableFormatMap().containsKey(index);	
	}

	public void originalCopy(){
		keyValueList.setOriginalDataMap(keyValueList.getDataMap());
		keyValueList.setOriginalFormatMap(keyValueList.getTableFormatMap());
	}

	protected void resetMap(){
		keyValueList.setDataMap(keyValueList.getOriginalDataMap());
		keyValueList.setTableFormatMap(keyValueList.getOriginalFormatMap());
	}

	public LinkedHashMap<String,String> getDataMap(){
		return keyValueList.getDataMap();
	}

	public LinkedHashMap<String,String> getTableFormatMap(){
		return keyValueList.getTableFormatMap();
	}

	protected LinkedHashMap<String,String> getOriginalDataMap(){
		return keyValueList.getOriginalDataMap();
	}

	protected LinkedHashMap<String,String> getOriginalFormatMap(){
		return keyValueList.getOriginalFormatMap();
	}
	
	public String search(String searchString){
		StrBuilder strBuilder = new StrBuilder();
		int keyOccurence = 0, valueOccurence = 0;
		
		for (Map.Entry<String, String> entry : keyValueList.getTableFormatMap().entrySet() ){
		    String key = entry.getValue(), value = keyValueList.getDataMap().get(entry.getValue());
			keyOccurence = 0;
			valueOccurence = 0;

			for(int i=0;i<key.length();i++){
				if(i+searchString.length()<=key.length())
				   {
			 		 if(key.substring(i,i+searchString.length()).equals(searchString))
					{
						keyOccurence++;
					}
				   }	
			}

			for(int i=0;i<value.length();i++){
				if(i+searchString.length()<=value.length())
				   {
			 		 if(value.substring(i,i+searchString.length()).equals(searchString))
					{
						valueOccurence++;
					}
				   }	
			}
		   strBuilder.appendln("["+entry.getKey()+"]: key = "+keyOccurence+" occurences, value = "+valueOccurence+" occurences");
		}
		return strBuilder.toString();	
	}
    
    public String printMap(){
        StrBuilder strBuilder = new StrBuilder();
        LinkedHashMap<String, String> dataMap = keyValueList.getDataMap();
        String previousKey = "X";

		for (Map.Entry<String, String> entry : keyValueList.getTableFormatMap().entrySet()){
			if(previousKey.equals("X") || previousKey.equals(entry.getKey().substring(0,1))){
				strBuilder.append("("+entry.getValue()+","+dataMap.get(entry.getValue())+")");
			}
			else{
				strBuilder.append("\n("+entry.getValue()+","+dataMap.get(entry.getValue())+")");
			}	
			previousKey=entry.getKey().substring(0,1);
		}
        return strBuilder.toString();
    }
    
	public void sort(){
		LinkedHashMap<String,String> dataMap = keyValueList.getDataMap();
		LinkedHashMap<String,String> formatMap = keyValueList.getTableFormatMap();
		int keyCtr=0;

		TreeMap<String, String> sortedMap = new TreeMap<String, String>();		
		for (Map.Entry<String, String> entry : dataMap.entrySet() ){
			sortedMap.put(entry.getKey()+""+entry.getValue(), entry.getKey());			
		}
		
		HashMap<Integer, String> dummyFormatMap = new HashMap<Integer, String>();
		for (Map.Entry<String, String> entry : sortedMap.entrySet() ){
			dummyFormatMap.put(keyCtr++, entry.getValue());			
		}
		
		keyCtr=0;
		for (Map.Entry<String, String> entry : formatMap.entrySet() ){			
			entry.setValue(dummyFormatMap.get(keyCtr));
			keyValueList.editFormatValue(entry.getKey(), dummyFormatMap.get(keyCtr++));	
		}
	}	
}
