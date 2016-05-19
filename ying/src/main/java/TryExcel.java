import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class TryExcel
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		FileInputStream is=null;
		try
		{
			is = new FileInputStream("D://testdata//aaa.xlsx");
		} catch (FileNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			XSSFWorkbook xssfWorkbook=new XSSFWorkbook(is);
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0); 
			int rowNum=xssfSheet.getLastRowNum();
			for(int i=1;i<rowNum+1;i++)
			{
				XSSFRow row=xssfSheet.getRow(i);
				//int columNum=row.getLastCellNum();
				int columNum=row.getPhysicalNumberOfCells();
				System.out.println(String.format("%s row has %s column",i,columNum));
			}
			//System.out.println(rowNum);
			
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
