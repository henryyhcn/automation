package util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESCoder
{
	private static final String KEY_ALGORITHM = "AES";
	
	/**
	 * 
	 *  鍑芥暟鍚嶇О : decrypt
	 *  鍔熻兘鎻忚堪 :  
	 *  鍙傛暟鍙婅繑鍥炲�艰鏄庯細
	 *  	@param content
	 *  	@param password
	 *  	@return
	 *
	 *  淇敼璁板綍锛�
	 *  	鏃ユ湡 锛�2016骞�4鏈�25鏃� 涓嬪崍12:24:40	淇敼浜猴細  niuzan
	 *  	鎻忚堪	锛�
	 *
	 */
	public byte[] decrypt(byte[] content, String password) {  
        try {  
             KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
             kgen.init(128, new SecureRandom(password.getBytes()));  
             SecretKey secretKey = kgen.generateKey();
             byte[] enCodeFormat = secretKey.getEncoded();  
             SecretKeySpec key = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);              
             Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);// 鍒涘缓瀵嗙爜鍣�   
	         cipher.init(Cipher.DECRYPT_MODE, key);// 鍒濆鍖� 
	         byte[] result = cipher.doFinal(content);  
	         return result; // 鍔犲瘑  
        } catch (Exception e) 
        {
        	e.printStackTrace();
			 return null;
        } 
	}  

	/**
	 * 
	 *  鍑芥暟鍚嶇О : encrypt
	 *  鍔熻兘鎻忚堪 :  
	 *  鍙傛暟鍙婅繑鍥炲�艰鏄庯細
	 *  	@param content
	 *  	@param password
	 *  	@return
	 *
	 *  淇敼璁板綍锛�
	 *  	鏃ユ湡 锛�2016骞�4鏈�25鏃� 涓嬪崍12:24:49	淇敼浜猴細  niuzan
	 *  	鎻忚堪	锛�
	 *
	 */
	public static byte[] encrypt(String content, String password) 
	{
        try {             
                KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);  
                kgen.init(128, new SecureRandom(password.getBytes()));  
                SecretKey secretKey = kgen.generateKey();  
                byte[] enCodeFormat = secretKey.getEncoded();  
                SecretKeySpec key = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);  
                Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);// 鍒涘缓瀵嗙爜鍣�   
                byte[] byteContent = content.getBytes("utf-8");  
                cipher.init(Cipher.ENCRYPT_MODE, key);// 鍒濆鍖�   
                byte[] result = cipher.doFinal(byteContent);  
                return result; // 鍔犲瘑   
        } catch (Exception e) 
        {
        	e.printStackTrace();
			 return null;
        } 
	}
	
	/**
	 * 
	 *  鍑芥暟鍚嶇О : parseByte2HexStr
	 *  鍔熻兘鎻忚堪 :  
	 *  鍙傛暟鍙婅繑鍥炲�艰鏄庯細
	 *  	@param buf
	 *  	@return
	 *
	 *  淇敼璁板綍锛�
	 *  	鏃ユ湡 锛�2016骞�4鏈�25鏃� 涓嬪崍12:25:04	淇敼浜猴細  niuzan
	 *  	鎻忚堪	锛�
	 *
	 */
	public static String parseByte2HexStr(byte buf[])
	{
		try {
			StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < buf.length; i++) 
	        {
	            String hex = Integer.toHexString(buf[i] & 0xFF);  
	            if (hex.length() == 1)
	            {
	                    hex = '0' + hex;
	            }
	            sb.append(hex.toUpperCase());
	        }
	        return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			 return null;
		}
	}
	
	/**
	 * 
	 *  鍑芥暟鍚嶇О : parseHexStr2Byte
	 *  鍔熻兘鎻忚堪 :  
	 *  鍙傛暟鍙婅繑鍥炲�艰鏄庯細
	 *  	@param hexStr
	 *  	@return
	 *
	 *  淇敼璁板綍锛�
	 *  	鏃ユ湡 锛�2016骞�4鏈�25鏃� 涓嬪崍12:19:45	淇敼浜猴細  niuzan
	 *  	鎻忚堪	锛�
	 *
	 */
	public byte[] parseHexStr2Byte(String hexStr)
	{
		try {
			if (hexStr.length() < 1)
                return null;
	        byte[] result = new byte[hexStr.length()/2];
	        for (int i = 0;i< hexStr.length()/2; i++) 
	        {
	            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
	            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
	            result[i] = (byte) (high * 16 + low);  
	        }
	        return result;
			
		} catch (Exception e)
		{
			 e.printStackTrace();
			 return null;
		}
        
	}
	
	/**
	 * 
	 *  鍑芥暟鍚嶇О : getEncryptResult
	 *  鍔熻兘鎻忚堪 :  
	 *  鍙傛暟鍙婅繑鍥炲�艰鏄庯細
	 *  	@param content
	 *  	@param passKey
	 *  	@return
	 *
	 *  淇敼璁板綍锛�
	 *  	鏃ユ湡 锛�2016骞�4鏈�25鏃� 涓嬪崍12:24:19	淇敼浜猴細  niuzan
	 *  	鎻忚堪	锛�
	 *
	 */
	public String getEncryptResult(String content, String passKey)
	{
		return parseByte2HexStr(encrypt(content, passKey));
	}
	
	/**
	 * 
	 *  鍑芥暟鍚嶇О : getDecryptResult
	 *  鍔熻兘鎻忚堪 :  
	 *  鍙傛暟鍙婅繑鍥炲�艰鏄庯細
	 *  	@param content
	 *  	@param passKey
	 *  	@return
	 *
	 *  淇敼璁板綍锛�
	 *  	鏃ユ湡 锛�2016骞�4鏈�25鏃� 涓嬪崍12:24:26	淇敼浜猴細  niuzan
	 *  	鎻忚堪	锛�
	 *
	 */
	public String getDecryptResult(String content, String passKey)
	{
		return new String(decrypt(parseHexStr2Byte(content), passKey));
	}
	
	public static String token(String originalToken)
	{
		String password = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQhIvT60RAT0FJ8tqlV9wN1i8hE37h3IXOxxBwAxkqonFgHw8ucGKT3w8ApGhEgvdRegkHM8/y8MB3l/Q1YOdQMq5DNV5/nHSuYZOTyPE7YlgRj6Xve9KgsRdQT76JIPfCyJd2qCZzsVxsUENZppYPZHAITiOvd8zK9M4cagtkJwIDAQAB";  
		
		byte[] encryptResult1 = encrypt(originalToken, password);  
		String encryptResultStr1 = parseByte2HexStr(encryptResult1);  		
		return encryptResultStr1;
	}
	

}
