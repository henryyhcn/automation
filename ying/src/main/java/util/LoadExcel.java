package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import model.TestDataExcel;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;


/*
 * 在DataProvider中使用，用来生成TestDataExcel类型的数据。
 */

public class LoadExcel
{
	//excel的路径
	private String filePath;
	private String autoFilePath=System.getProperty("user.dir")
			+ "\\src\\main\\resources\\" + "autoNumber.properties";
	//构造函数
	public LoadExcel(String filePath)
	{
		this.filePath=filePath;
	}
	
	/*
	 * params:  iBegin就是Excel中你需要获取的数据的第一行，iEnd就是Excel中你需要获取的数据的最后一行
	 */
	public ArrayList<TestDataExcel> fetchTestData(int iBegin,int iEnd)
	{
		ArrayList<TestDataExcel> tds=new ArrayList<TestDataExcel>();
		//加载Excel文件
		FileInputStream is=null;
		try
		{
			is = new FileInputStream(filePath);
		} 
		catch (FileNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//将每一条Excel记录转成TestDataExcel
		try
		{
			XSSFWorkbook xssfWorkbook=new XSSFWorkbook(is);
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0); 
			/*int rowNum=xssfSheet.getLastRowNum();
			for(int i=1;i<rowNum+1;i++)
			{
				XSSFRow row=xssfSheet.getRow(i);
				//int columNum=row.getLastCellNum();
				int columNum=row.getPhysicalNumberOfCells();
				System.out.println(String.format("%s row has %s column",i,columNum));
			}
			System.out.println(rowNum);*/	
			for(int i=iBegin;i<=iEnd;i++)
			{
				TestDataExcel tde=new TestDataExcel();
				XSSFRow row=xssfSheet.getRow(1);
				//设置tde.title
				String title=row.getCell(1).getStringCellValue();
				tde.setTitle(title);
				
				//设置tde.url
				String url=row.getCell(3).getStringCellValue();
				tde.setUrl(url);
				System.out.println("url is "+row.getCell(3).getStringCellValue());
				
				//设置tde.method
				tde.setMethod(row.getCell(4).getStringCellValue());
				System.out.println("url is "+row.getCell(4).getStringCellValue());
				
				//设置tde.params
				//第6列，由于数组起始位0，所以用5，getCell(5),得到Params列
				String strParams=row.getCell(5).getStringCellValue();
				//转换成HashMap
				HashMap<String,String> params=new HashMap<String,String>();
				JSONObject jo=new JSONObject(strParams);
				Set<String> keys=jo.keySet();
				for(String tempKey:keys)
				{
					System.out.println("key is "+tempKey);
					String tempValue=jo.getString(tempKey);
					System.out.println("value is "+tempValue);
					params.put(tempKey, tempValue);		
				}
				tde.setParams(params);
				System.out.println("params is "+row.getCell(5).getStringCellValue());
				tds.add(tde);
			}						
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tds;
	}
	
	
}
