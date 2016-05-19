package util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Set;

import model.TestDataExcel;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsonHelper
{
	private Document document;

	public void jsoupPost(HashMap<String, String> params)
	{
		Connection conn = null;
		try
		{
			conn = Jsoup
					.connect("http://www.cnblogs.com/wade-xu/p/4236295.html");
			if (params != null)
			{
				int length = params.size();
				Set<String> keys = params.keySet();
				for (String str : keys)
				{
					System.out.print("key is " + str);
					System.out.println("     value is " + params.get(str));
				}
			}
			// conn.data("loginname","test1");
			// conn.data("loginpwd","test1");
			Document doc = conn.post();
			System.out.println(doc);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String jsoupPost(TestDataInterface td)
	{
		Connection conn = null;
		String body=null;
		try
		{
			conn = Jsoup.connect(td.getUrl());
			if (td.getParams() != null)
			{
				HashMap<String, String> params;
				params = td.getParams();
				int length = params.size();
				Set<String> keys = params.keySet();
				for (String str : keys)
				{
					conn.data(str, params.get(str));
				}
			}
			body = conn.ignoreContentType(true).execute().body();
			System.out.println(body);
		    try { 
		        FileOutputStream fos = new FileOutputStream("d:\\usercenterLog.txt",true); 
		        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8"); 
		        osw.write(System.getProperty("line.separator"));
		        osw.write(body); 
		        osw.write(System.getProperty("line.separator"));
		        osw.write("----------------------------------------------------------------------------------------------");
		        osw.flush(); 
		    } 
		    catch (Exception e) 
		    { 
		        e.printStackTrace(); 
		    }
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return body;
	}

	public String jsoupPost2(TestDataInterface td)
	{
		Connection conn = null;
		String body=null;
		try
		{
			conn = Jsoup.connect(td.getUrl());
			if (td.getParams() != null)
			{
				HashMap<String, String> params;
				params = td.getParams();
				int length = params.size();
				Set<String> keys = params.keySet();
				for (String str : keys)
				{
					conn.data(str, params.get(str));
				}
			}
			conn.header("Content-Type", "application/x-www-form-urlencoded");
			Document doc=conn.post();
			
			//body = conn.ignoreContentType(true).execute().body();
			body=doc.body().text();
			System.out.println(body);
		    try { 
		        FileOutputStream fos = new FileOutputStream("d:\\usercenterLog.txt",true); 
		        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8"); 
		        osw.write(System.getProperty("line.separator"));
		        osw.write(body); 
		        osw.write(System.getProperty("line.separator"));
		        osw.write("----------------------------------------------------------------------------------------------");
		        osw.flush(); 
		    } 
		    catch (Exception e) 
		    { 
		        e.printStackTrace(); 
		    }
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return body;
	}
	
	public String jsoupPost(TestDataExcel tde)
	{
		Connection conn = null;
		String body=null;
		try
		{
			conn = Jsoup.connect(tde.getUrl());
			if (tde.getParams() != null)
			{
				HashMap<String, String> params;
				params = tde.getParams();
				int length = params.size();
				Set<String> keys = params.keySet();
				for (String str : keys)
				{
					conn.data(str, params.get(str));
				}
			}
			body = conn.ignoreContentType(true).execute().body();
			System.out.println(body);
		    try { 
		        FileOutputStream fos = new FileOutputStream("d:\\usercenterLog.txt",true); 
		        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8"); 
		        osw.write(System.getProperty("line.separator"));
		        osw.write(body); 
		        osw.write(System.getProperty("line.separator"));
		        osw.write("----------------------------------------------------------------------------------------------");
		        osw.flush(); 
		    } 
		    catch (Exception e) 
		    { 
		        e.printStackTrace(); 
		    }
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return body;
	}
}
