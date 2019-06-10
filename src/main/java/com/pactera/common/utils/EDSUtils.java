/**
 * 项目名：ams
 * 包名：com.pactera.common.utils
 * 文件名：EDSUtils.java
 * 版本信息：1.0.0
 * 日期：2019年5月12日-下午8:38:52
 * Copyright (c) 2019 Pactera 版权所有
 */
 
package com.pactera.common.utils;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @ClassName：EDSUtils
 * @Description：EDSUtils
 * @author pactera 
 * @date 2019年5月12日 下午8:38:52 
 * @version 1.0.0 
 */
public class EDSUtils {
	  /**
     *  EDS的加密解密代码
     */
    public static final byte[] DES_KEY = { 21, 1, -110, 82, -32, -85, -128, -65 };
      @SuppressWarnings("restriction")
      
      /**
       * 加密
       * @Title: encryptBasedDes 
       * @Description: encryptBasedDes
       * @param data
       * @return String
       * @author pactera
       * @date 2019年5月28日 下午1:52:06
       */
      public static String encryptBasedDes(String data) {
          String encryptedData = null;
          try {
              // DES算法要求有一个可信任的随机数源
              SecureRandom sr = new SecureRandom();
              DESKeySpec deskey = new DESKeySpec(DES_KEY);
              // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
              SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
              SecretKey key = keyFactory.generateSecret(deskey);
              // 加密对象
              Cipher cipher = Cipher.getInstance("DES");
              cipher.init(Cipher.ENCRYPT_MODE, key, sr);
              // 加密，并把字节数组编码成字符串
              encryptedData = new sun.misc.BASE64Encoder().encode(cipher.doFinal(data.getBytes()));
          } catch (Exception e) {
              throw new RuntimeException("加密错误，错误信息：", e);
          }
          return encryptedData;
      }
      /**
       * 解密
       * @Title: decryptBasedDes 
       * @Description: decryptBasedDes
       * @param cryptData
       * @return String
       * @author pactera
       * @date 2019年5月28日 下午1:51:26
       */
      @SuppressWarnings("restriction")
      public static String decryptBasedDes(String cryptData) {
          String decryptedData = null;
          try {
              // DES算法要求有一个可信任的随机数源
              SecureRandom sr = new SecureRandom();
              DESKeySpec deskey = new DESKeySpec(DES_KEY);
              // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
              SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
              SecretKey key = keyFactory.generateSecret(deskey);
              // 解密对象
              Cipher cipher = Cipher.getInstance("DES");
              cipher.init(Cipher.DECRYPT_MODE, key, sr);
              // 把字符串进行解码，解码为为字节数组，并解密
              decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(cryptData)));
          } catch (Exception e) {
              throw new RuntimeException("解密错误，错误信息：", e);
          }
          return decryptedData;
      }
}
