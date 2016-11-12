package com.example.saurabhgoyal.cameraapplication.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Utility {
	
	public String listToQueryList(ArrayList<String> anyList){
		try{
		String result=null;
		int counter=0;
		for(String s:anyList){
			if(counter++==0)
				result="'"+s+"'";
			else
				result+=","+"'"+s+"'";
		}
		return result;
		}catch(Exception ex){
			Utility.write("ErrorLog", "Encountered error in utilities list to query list." + ex.getMessage());
	    	return null;
		}
	}
	public static String[] listToStringArray(List<String> anyList){
		try{
		String[] result=new String[anyList.size()];
		int counter=0;
		for(String s:anyList){
				result[counter++]=s;
		}
		return result;
		}catch(Exception ex){
			Utility.write("ErrorLog", "Encountered error in utilities list to string array list." + ex.getMessage());
	    	return null;
		}
		
	}
	public static Boolean write(String fname, String fcontent){
	      try {
	        String fpath = "/sdcard/"+fname+".txt";
	        File file = new File(fpath);
	        // If file does not exists, then create it
	        if (!file.exists()) {
	          file.createNewFile();
	        }
	        FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
	        
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.append(fcontent+"\n");
	        
	        bw.close();
	        Log.d("Success", "Sucess");
	        return true;
	      } catch (IOException e) {
	    	  Utility.write("ErrorLog", "Encountered error in utilities writing fname,fcontent " + e.getMessage());
	        return false;
	      }
	   }
	public static void saveToFile(Context context,String className,Object object) {
	    try {
	        FileOutputStream fileOutputStream = context.openFileOutput(className, Context.MODE_PRIVATE);
	        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
	        objectOutputStream.writeObject(object);
	        objectOutputStream.close();
	        fileOutputStream.close();
	    } catch (IOException e) {
	    	Utility.write("ErrorLog", "Encountered error in utilities save to file." + e.getMessage());
	    }
	}
	public static Object readFromFile(Context context,String className) {
	    Object object = null;
	    try {
	        FileInputStream fileInputStream = context.openFileInput(className);
	        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	        object= objectInputStream.readObject();
	        objectInputStream.close();
	        fileInputStream.close();
	    } catch (IOException e) {
	    	Utility.write("ErrorLog", "Encountered error in utilities reading from file." + e.getMessage());
	    	return null;
	    }
	    catch (ClassNotFoundException e) {
	    	Utility.write("ErrorLog", "Encountered error in utilities reading from file." + e.getMessage());
	    	return null;
	    }
	    return object;
	}
	public static void writeToSharedPref(Context context,String key,String value){
		 SharedPreferences data=context.getSharedPreferences("cashSavvy", Context.MODE_PRIVATE);
	        SharedPreferences.Editor edit=data.edit();
	        edit.putString(key, value);
	        edit.apply();//apply faster asyhccronous edit.commit() is slower and shynchronous

	}
	public static String readFromSharedPref(Context context,String key,String defaultValue){
		try{
		 SharedPreferences somedata=context.getSharedPreferences("cashSavvy", Context.MODE_PRIVATE);
	        String data=somedata.getString(key, defaultValue);
	        return data;
		}catch(Exception ex){
			Utility.write("ErrorLog", "Encountered error in utilities reading from shared Prefrences." + ex.getMessage());
	    	return null;
		}
	}
	public static ArrayList<String> read(String fname, Boolean checkReference){
		BufferedReader br = null;
		ArrayList<String> files=new ArrayList<String>();
		 StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new FileReader("/sdcard/"+fname+".txt"));
	        String line = null;
		
				line = br.readLine();
				

	        while (line != null) {
	            sb.append(line);
	            if(checkReference){
	            File file=new File("/sdcard/"+line+".txt");
	            if(!file.exists() || files.contains(line)){
		            line = br.readLine();
		            continue;
	            }
	            }
	            	files.add(line);
	            line = br.readLine();
	        }
	        br.close();
	    } catch (IOException e) {
	    	Utility.write("ErrorLog", "Encountered error in utilities reading file." + e.getMessage());
	    	return null;
	    	
		} 
	    
	    finally {
	    }
	    
	return files;   
	}
	public static void deleteFile(String fname){
		try{
		File file=new File("/sdcard/"+fname+".txt");
		file.delete();
		}catch(Exception ex){
			Utility.write("ErrorLog", "Encountered error in utilities deleting file." + ex.getMessage());
		}
	}
}
