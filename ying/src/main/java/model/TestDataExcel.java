package model;

import java.util.HashMap;
import java.util.Set;

import org.json.JSONObject;

public class TestDataExcel
{
	//对应Excel的Title列
	private String title;
	
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}

	//对应Excel的method列
	private String method;
	public String getMethod()
	{
		return method;
	}
	public void setMethod(String method)
	{
		if(method.toLowerCase().equals("post")||method.toLowerCase().equals("get"))
		{
			this.method = method;
		}
		else
		{
			this.method="post";
		}
		
	}
	
	//对应Excel的URL列
	private String url;
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	//转换后的params列，HashMap格式
	private HashMap<String,String> params=null;
	public HashMap<String, String> getParams()
	{
		return params;
	}
	public void setParams(HashMap<String, String> params)
	{
		this.params = params;
	}
	
	
	//从Excel中导入的params列地值，String格式，需要转成Json格式最后转成HashMap
	private String strParams;
	public String getStrParams()
	{
		return strParams;
	}
	public void setStrParams(String strParams)
	{
		this.strParams = strParams;
	}
	/*public void convertToHashMap(String strParams)
	{
		JSONObject jo=new JSONObject(strParams);
		Set<String> keys=jo.keySet();
		for(String tempKey:keys)
		{
			System.out.println("key is "+tempKey);
			String tempValue=jo.getString(tempKey);
			System.out.println("value is "+tempValue);
			this.params.put(tempKey, tempValue);		
		}
	}*/
	
	
	//构造函数，把Excel中地Parmas列转换成了HashMap格式。String转成Json再转成HashMap
	/*public TestDataExcel(String strParams)
	{
		JSONObject jo=new JSONObject(strParams);
		Set<String> keys=jo.keySet();
		for(String tempKey:keys)
		{
			this.params.put(tempKey, jo.getString(tempKey));		
		}
	}*/
	
	
	

}
