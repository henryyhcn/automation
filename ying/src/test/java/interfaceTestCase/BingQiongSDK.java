package interfaceTestCase;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import redis.clients.jedis.Jedis;

import com.dome.sdkserver.util.RSACoder;

import util.AESCoder;
import util.JsonHelper;
import util.PropertiesHelper;
import util.TestDataInterface;

public class BingQiongSDK
{
	private String baseURL = "http://192.168.130.246:9999/";
	private String redisURL = "192.168.130.40";
	private int redisPort = 6379;
	private String mobile = "18500000035";
	private String authCode=null;
	private String accessToken=null;
	private String registerCode=null;
	private String resetCode=null;
	private String smsToken=null;
	private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC52p/pRoZ4SGynOMKWlZjc8DBwMykuyV9r0v7TW/4cnJNXMM5lqe1zNA9nl6rCsSFAcF/3fzXf5UrMFXZXM990RGA3Mr6xIn5NPqc98n2plkH4FQEIQLd7z5Bit6xkJwApAHXToT3FA/KQiuXCB2ma2yCAchEzaEjleuDzqq1ftQIDAQAB";
	
	@BeforeClass
	public void beforeClass()
	{
		File file = new File("D:\\SDK_Log.txt");
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
	//获取授权码
	public void getAuthCode()
	{
		String url = baseURL + "bqsdklogin/getAuthCode";
		String method = "post";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("appCode", "A0001082");
		TestDataInterface td = new TestDataInterface(url, method, params);
		JsonHelper jsonHelper = new JsonHelper();
		String body=jsonHelper.jsoupPost2(td);
		JSONObject jo=new JSONObject(body);
		authCode=((JSONObject)jo.get("data")).getString("authCode");
		System.out.println("authCode is "+authCode);
	}
	
	@Test
	//获取登录token    
	public void getLoginToken()
	{
		String url = baseURL + "bqsdklogin/getLoginToken";
		String method = "post";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("appCode", "A0001082");
		params.put("loginName", mobile);
		String pwd=null;
		byte data1[]=null;
		try
		{
			data1 = RSACoder.encryptByPublicKey(("123456"+authCode).getBytes(), publicKey);
		} catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try
		{
			pwd=RSACoder.encryptBASE64(data1);
			System.out.println(pwd);
		} catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//pwd:123456
		params.put("password",pwd);
		TestDataInterface td = new TestDataInterface(url, method, params);
		JsonHelper jsonHelper = new JsonHelper();
		String body=jsonHelper.jsoupPost2(td);
		JSONObject jo=new JSONObject(body);
		accessToken=((JSONObject)jo.get("data")).getString("accessToken");
		System.out.println("accessToken is "+accessToken);
	}
	
	@Test
	//获取用户信息
	public void getUserInfoByToken()
	{
		String url = baseURL + "bqsdklogin/getUserInfoByToken";
		String method = "post";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("appCode", "A0001082");
		params.put("loginName", mobile);
		params.put("key", "18500000074");
		//accessToken
		params.put("authToken", accessToken);
		TestDataInterface td = new TestDataInterface(url, method, params);
		JsonHelper jsonHelper = new JsonHelper();
		String body=jsonHelper.jsoupPost2(td);

	}
	
	@Test
	//获得注册码
	public void getSmsCode()
	{
		String url = baseURL + "bqsdkuser/getSmsCode";
		String method = "post";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("appCode", "A0001082");
		params.put("mobile", mobile);
		params.put("bizType", "1");
		TestDataInterface td = new TestDataInterface(url, method, params);
		JsonHelper jsonHelper = new JsonHelper();
		String body=jsonHelper.jsoupPost2(td);
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
				"usercenter_sms_A0001082_register_%s_regverifyCode", mobile);
		System.out.println(verCodeKey);
		registerCode = jedis.get(verCodeKey);
		System.out.println("registerCode is "+jedis.get(verCodeKey));
	}
	
	@Test
	//注册      bqsdkuser/registerUser
	public void registerUser()
	{
		String url = baseURL + "bqsdkuser/registerUser";
		String method = "post";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("appCode", "A0001082");
		params.put("loginName", mobile);
		//pwd 123456
		String pwd="mVbiC0nhHhc+LtJVXNngQm8yPL47nn2uz2zupAk5skBvHYYNqkcX+KIDU2nQ0uaSTm+3rkuQ2dkAKSQctiJHZjHJwMswQqFa/aIFTWZkDgzBChFibABUUzVgNOdMBWTHBg7Y1r/vZqLX7g9XU33hjS4TZna4NXnL5CBQpcern00=";
		params.put("password", pwd);
		params.put("key", "1sdafsdf");
		params.put("registerCode", registerCode);
		TestDataInterface td = new TestDataInterface(url, method, params);
		JsonHelper jsonHelper = new JsonHelper();
		jsonHelper.jsoupPost2(td);
	}
	
	
	@Test
	//获得找回密码注册码
	public void getPwdSmsCode()
	{
		String url = baseURL + "bqsdkuser/getSmsCode";
		String method = "post";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("appCode", "A0001082");
		params.put("mobile", mobile);
		params.put("bizType", "3");
		TestDataInterface td = new TestDataInterface(url, method, params);
		JsonHelper jsonHelper = new JsonHelper();
		String body=jsonHelper.jsoupPost2(td);
		//usercenter_sms_A0001082_resetPassword_18500000083_regverifyCode
		Jedis jedis = new Jedis(redisURL, redisPort);
		String verCodeKey = String.format(
				"usercenter_sms_A0001082_resetPassword_%s_regverifyCode", mobile);
		System.out.println(verCodeKey);
		resetCode = jedis.get(verCodeKey);
		System.out.println("registerCode is "+jedis.get(verCodeKey));
	}
	
	@Test
	//验证短信码
	public void verifySmsCode()
	{
		String url = baseURL + "bqsdkuser/verifySmsCode";
		String method = "post";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("appCode", "A0001082");
		params.put("mobile", mobile);
		params.put("bizType", "3");
		params.put("smsCode",resetCode);
		TestDataInterface td = new TestDataInterface(url, method, params);
		JsonHelper jsonHelper = new JsonHelper();
		String body=jsonHelper.jsoupPost2(td);
		JSONObject jo=new JSONObject(body);
		smsToken=((JSONObject)jo.get("data")).getString("smsToken");
		System.out.println("smsToken is "+smsToken);
	}
	
	
	@Test
	//找回密码
	public void resetPassword()
	{
		String url = baseURL + "bqsdkuser/resetPassword";
		String method = "post";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("appCode", "A0001082");
		params.put("loginName", mobile);
		//password:654321
		String pwd="O/3v9qnHVCgaO57ZFIoS+8n1zbYFo+EYD9WuX53eRmckL7QbjdYhNtom9Cgp/XLFU+G6UWbQRNzloClMxvvWMxdXnFWsJ8118RsT2H09eK1BD9f8IXC+F93krEQ8MkH8Em4UGa8wr5Mpmy3F9iVGsrtjN8FTPSKq6iq4bF+Oihg=";
		params.put("password", pwd);
		params.put("smsCode", resetCode);
		params.put("smsToken",smsToken);
		TestDataInterface td = new TestDataInterface(url, method, params);
		JsonHelper jsonHelper = new JsonHelper();
		String body=jsonHelper.jsoupPost2(td);
	}
	
}
