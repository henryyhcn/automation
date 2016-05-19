package interfaceTestCase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import redis.clients.jedis.Jedis;
import util.AESCoder;
import util.JsonHelper;
import util.PropertiesHelper;
import util.TestDataInterface;

public class BingQiongUserCenter
{
	// private String baseURL="http://192.168.130.246:9000/";
	//uc.domestore.cn
	//private String baseURL = "http://uc.domestore.cn/";
	private String baseURL = "http://192.168.130.246:9000/";
	private String redisURL = "192.168.130.40";
	private String mobile = "18500000035";
	private int redisPort = 6379;
	private String verCode = null;
	private String originalToken = null;
	private String smsToken = null;

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
		String propFilePath = System.getProperty("user.dir")
				+ "\\src\\main\\resources\\" + "autoNumber.properties";
		String tempValue = PropertiesHelper.GetValueByKey(propFilePath,
				"BingQiongUserCenter_Mobile");
		mobile = tempValue;
		Long lmobile = Long.parseLong(mobile) + 1;
		String modifiedmobile = Long.toString(lmobile);
		PropertiesHelper.SetValueByKey(propFilePath,
				"BingQiongUserCenter_Mobile", modifiedmobile);
	}

	@Test
	// 获得注册的短信验证码
	public void getCode()
	{
		String url = baseURL + "mobile/getCode";
		String method = "post";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("mobile", mobile);
		params.put("bizType", "1");
		params.put("clientId", "1");
		params.put("buId", "BQ001");
		TestDataInterface td = new TestDataInterface(url, method, params);
		JsonHelper jsonHelper = new JsonHelper();
		jsonHelper.jsoupPost(td);

		try
		{
			Thread.sleep(2000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Jedis jedis = new Jedis(redisURL, redisPort);
		String verCodeKey = String.format(
				"usercenter_sms_1_register_%s_regverifyCode", mobile);
		System.out.println(verCodeKey);
		verCode = jedis.get(verCodeKey);
		System.out.println(jedis.get(verCodeKey));
		try
		{
			FileOutputStream fos = new FileOutputStream(
					"d:\\usercenterLog.txt", true);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(System.getProperty("line.separator"));
			osw.write("above is getCode");
			osw.write(System.getProperty("line.separator"));
			osw.write("----------------------------------------------------------------------------------------------");
			osw.flush();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Test
	// 用户注册
	public void registerConfirm()
	{
		String url = baseURL + "register/registerConfirm";
		System.out.println("URL is " + url);
		String method = "post";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("passport", mobile);
		System.out.println("mobile is " + mobile);
		
		params.put("password", "3A1A029100B5DD3AD4863E038F7B4651");
		params.put("mobileCode", verCode);
		System.out.println("verCode is " + verCode);
		params.put("clientId", "1");
		params.put("buId", "BQ001");
		TestDataInterface td = new TestDataInterface(url, method, params);
		JsonHelper jsonHelper = new JsonHelper();
		jsonHelper.jsoupPost(td);
		try
		{
			FileOutputStream fos = new FileOutputStream(
					"d:\\usercenterLog.txt", true);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(System.getProperty("line.separator"));
			osw.write("above is registerConfirm");
			osw.write(System.getProperty("line.separator"));
			osw.write("----------------------------------------------------------------------------------------------");
			osw.flush();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Test
	// 用户登录
	public void login()
	{
		try
		{
			Thread.sleep(3000);
		} catch (InterruptedException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String url = baseURL + "login/login";
		String method = "post";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("passport", mobile);
		params.put("password", "3A1A029100B5DD3AD4863E038F7B4651");
		params.put("clientId", "1");
		params.put("buId", "BQ001");
		TestDataInterface td = new TestDataInterface(url, method, params);
		JsonHelper jsonHelper = new JsonHelper();
		String body = jsonHelper.jsoupPost(td);
		JSONObject jo = new JSONObject(body);
		originalToken = ((JSONObject) jo.get("data")).getString("accessToken");
		// System.out.println("accesstoken is "+originalToken);
		try
		{
			FileOutputStream fos = new FileOutputStream(
					"d:\\usercenterLog.txt", true);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(System.getProperty("line.separator"));
			osw.write("above is login");
			osw.write(System.getProperty("line.separator"));
			osw.write("----------------------------------------------------------------------------------------------");
			osw.flush();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Test
	// 获取用户信息
	public void getByToken()
	{
		String url = baseURL + "user/getByToken";
		String method = "post";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("accessToken", AESCoder.token(originalToken));
		params.put("clientId", "1");
		params.put("buId", "BQ001");
		TestDataInterface td = new TestDataInterface(url, method, params);
		JsonHelper jsonHelper = new JsonHelper();
		jsonHelper.jsoupPost(td);
		try
		{
			FileOutputStream fos = new FileOutputStream(
					"d:\\usercenterLog.txt", true);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(System.getProperty("line.separator"));
			osw.write("above is getByToken");
			osw.write(System.getProperty("line.separator"));
			osw.write("----------------------------------------------------------------------------------------------");
			osw.flush();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Test
	// 获得重设密码的 短信验证码
	public void getResetCode()
	{
		String url = baseURL + "mobile/getCode";
		String method = "post";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("mobile", mobile);
		params.put("bizType", "3");
		params.put("clientId", "1");
		params.put("buId", "BQ001");
		TestDataInterface td = new TestDataInterface(url, method, params);
		JsonHelper jsonHelper = new JsonHelper();
		jsonHelper.jsoupPost(td);

		try
		{
			Thread.sleep(2000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Jedis jedis = new Jedis(redisURL, redisPort);
		String verCodeKey = String.format(
				"usercenter_sms_1_resetPassword_%s_regverifyCode", mobile);
		verCode = jedis.get(verCodeKey);
		System.out.println("resetCode is " + jedis.get(verCodeKey));
		try
		{
			FileOutputStream fos = new FileOutputStream(
					"d:\\usercenterLog.txt", true);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(System.getProperty("line.separator"));
			osw.write("above is getResetCode");
			osw.write(System.getProperty("line.separator"));
			osw.write("----------------------------------------------------------------------------------------------");
			osw.flush();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Test
	// 验证手机验证码
	public void verifyCode()
	{
		String url = baseURL + "mobile/verifyCode";
		System.out.println("URL is " + url);
		String method = "post";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("mobile", mobile);
		params.put("bizType", "3");
		params.put("clientId", "1");
		params.put("buId", "BQ001");
		params.put("mobileCode", verCode);
		TestDataInterface td = new TestDataInterface(url, method, params);
		JsonHelper jsonHelper = new JsonHelper();
		String body = jsonHelper.jsoupPost(td);
		JSONObject jo = new JSONObject(body);
		smsToken = ((JSONObject) jo.get("data")).getString("smsToken");
		try
		{
			FileOutputStream fos = new FileOutputStream(
					"d:\\usercenterLog.txt", true);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(System.getProperty("line.separator"));
			osw.write("above is verifyCode");
			osw.write(System.getProperty("line.separator"));
			osw.write("----------------------------------------------------------------------------------------------");
			osw.flush();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Test(enabled = true)
	// dependsOnMethods = {"getResetCode"}
	// 重置密码
	public void resetPassword()
	{
		String url = baseURL + "user/resetPassword";
		System.out.println(url);
		String method = "post";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("mobile", mobile);
		System.out.println(mobile);
		params.put("password", "160A87D8D98F37AA6F71DDC4BF387A0C");
		System.out.println("160A87D8D98F37AA6F71DDC4BF387A0C");
		params.put("smsToken", smsToken);
		System.out.println(smsToken);
		params.put("clientId", "1");
		params.put("buId", "BQ001");
		TestDataInterface td = new TestDataInterface(url, method, params);
		JsonHelper jsonHelper = new JsonHelper();
		jsonHelper.jsoupPost(td);
		try
		{
			FileOutputStream fos = new FileOutputStream(
					"d:\\usercenterLog.txt", true);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(System.getProperty("line.separator"));
			osw.write("above is resetPassword");
			osw.write(System.getProperty("line.separator"));
			osw.write("----------------------------------------------------------------------------------------------");
			osw.flush();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Test(enabled = true)
	// 修改密码
	public void modifyPassword()
	{
		String url = baseURL + "user/modifyPassword";
		String method = "post";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("passport", mobile);
		params.put("passwordNew", "95CC485FDF46E1A1793CDBDB533983C0");// password:654321
		params.put("passwordOld", "160A87D8D98F37AA6F71DDC4BF387A0C");// password:234567
		params.put("clientId", "1");
		params.put("buId", "BQ001");
		TestDataInterface td = new TestDataInterface(url, method, params);
		JsonHelper jsonHelper = new JsonHelper();
		jsonHelper.jsoupPost(td);
	
	}


}
