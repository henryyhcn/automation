import util.LoadExcel;


public class TryLoad
{
	public static void main(String[] args)
	{
		LoadExcel le=new LoadExcel("D://testdata//aaa.xlsx");
		System.out.println(le.fetchTestData(1,2).size());
		//System.out.println("当前程序所在目录：" + System.getProperty("user.dir")); // 当前程序所在目录
		//String filePath=System.getProperty("user.dir")+"\\src\\main\\resources\\"+"";
	}	
}
