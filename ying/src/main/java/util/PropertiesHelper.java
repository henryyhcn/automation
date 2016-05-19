package util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper
{
	// 根据Key读取Value
	public static String GetValueByKey(String filePath, String key)
	{
		Properties pps = new Properties();
		try
		{
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			pps.load(in);
			String value = pps.getProperty(key);
			System.out.println(key + " = " + value);
			return value;

		} 
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static void SetValueByKey(String filePath, String key,String value)
	{
		Properties pps = new Properties();
		try
		{
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			pps.load(in);
			pps.setProperty(key, value);
			//String value = pps.getProperty(key);
			//System.out.println(key + " = " + value);
			FileOutputStream fos = new FileOutputStream(filePath);
			pps.store(fos, "Copyright ying");   
			fos.close();//关闭流   
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
