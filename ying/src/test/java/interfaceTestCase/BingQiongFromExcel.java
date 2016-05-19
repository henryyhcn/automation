package interfaceTestCase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.TestDataExcel;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import util.JsonHelper;
import util.LoadExcel;

public class BingQiongFromExcel
{
	private String filePath="D://testdata//aaa.xlsx";
	private String baseURL="http://192.168.130.246:9000/";
	
	@DataProvider(name = "test1")
	public Object[][] createData1()
	{
		LoadExcel le=new LoadExcel(filePath);
		ArrayList<TestDataExcel> tds=new ArrayList<TestDataExcel>();
		
		int iEnd=2;
		tds=le.fetchTestData(1, iEnd);
		Object[][] ob=new Object[iEnd][0];
		for(int i=0;i<iEnd;i++)
		{
			ob[i]=new Object[]{tds.get(i)};
		}
		return ob;
		/*return new Object[][] { { "Cedric", new Integer(36) }, // [0][0] [0][1]
				{ "Anne", new Integer(37) }, // [1][0] [1][1]
		};*/
	}
	
	@BeforeClass
	public void beforeClass()
	{
		File file = new File("D:\\usercenterLog.txt");
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
		{
			file.delete();
		}
	}
	
	
	@Test(dataProvider = "test1")
	public void test001(TestDataExcel td)
	{
		td.setUrl(baseURL+td.getUrl());
		JsonHelper jsonHelper = new JsonHelper();
		jsonHelper.jsoupPost(td);
		//实例化LoadExcel,这个类目的为了导出每个testcase所需要的数据
		/*LoadExcel le=new LoadExcel(filePath);
		TestDataExcel tde=new TestDataExcel();
		tde=le.fetchTestData(1,2);
		tde.setUrl(baseURL+tde.getUrl());
		JsonHelper jsonHelper = new JsonHelper();
		jsonHelper.jsoupPost(tde);*/
		
	}
}
