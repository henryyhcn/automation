import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.dome.sdkserver.util.RSACoder;

import redis.clients.jedis.Jedis;


public class Try
{
	
	public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC52p/pRoZ4SGynOMKWlZjc8DBwMykuyV9r0v7TW/4cnJNXMM5lqe1zNA9nl6rCsSFAcF/3fzXf5UrMFXZXM990RGA3Mr6xIn5NPqc98n2plkH4FQEIQLd7z5Bit6xkJwApAHXToT3FA/KQiuXCB2ma2yCAchEzaEjleuDzqq1ftQIDAQAB";
	public static void main(String[] args)
	{
		/*Jedis redis = new Jedis("192.168.130.40", 6379);// 连接redis
		// redis.auth("redis");//验证密码
		Set keys = redis.keys("*");// 列出所有的key，查找特定的key如：redis.keys("foo")
		Iterator t1 = keys.iterator();
		while (t1.hasNext())
		{
			Object obj1 = t1.next();
			System.out.println(obj1);
		}
		String str=redis.get("usercenter_sms_A0001082_1_18930723259");
		System.out.println(str);*/
		
		byte data1[]=null;
		try
		{
			data1 = RSACoder.encryptByPublicKey(("654321").getBytes(), publicKey);
		} catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try
		{
			String str=RSACoder.encryptBASE64(data1);
			System.out.println(str);
		} catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		Connection conn = null;
		try
		{
			conn = Jsoup.connect("http://192.168.130.246:9999/bqsdklogin/getAuthCode");
			conn.data("passport","18507777772");
			conn.data("password","3A1A029100B5DD3AD4863E038F7B4651");
			conn.data("mobileCode","344933");
			conn.data("clientId","1");
			conn.data("buId","BQ001");
			String body = conn.ignoreContentType(true).execute().body();
			System.out.println(body);
		}
		catch(Exception e)
		{}
	}
}
