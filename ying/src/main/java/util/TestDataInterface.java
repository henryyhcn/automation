package util;

import java.util.HashMap;

public class TestDataInterface
{
	//构造函数
	public TestDataInterface(String url,String method,HashMap<String,String> params)
	{
		this.url=url;
		this.method=method;
		this.params=params;
	}
	//默认构造函数
	public TestDataInterface()
	{
		
	}
	
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
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public HashMap<String, String> getParams()
	{
		return params;
	}
	public void setParams(HashMap<String, String> params)
	{
		this.params = params;
	}
	private String url;
	private HashMap<String,String> params;

}
