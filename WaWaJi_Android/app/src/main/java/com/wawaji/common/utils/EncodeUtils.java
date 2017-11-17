package com.wawaji.common.utils;

import android.text.TextUtils;
import android.util.Base64;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密算法
 * 
 * @since 2016.06.30
 * @author admin
 */
@SuppressWarnings("UnusedParameters")
public class EncodeUtils {

	/**
	 * MD5加密
	 */
	public static class MD5 {

		private static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

		public MD5() {
		}

		public static String hexdigest(String string) {
			String s = null;

			try {
				s = hexdigest(string.getBytes());
			} catch (Exception var3) {
				var3.printStackTrace();
			}

			return s;
		}

		public static String hexdigest(byte[] bytes) {
			String s = null;

			try {
				MessageDigest e = MessageDigest.getInstance("MD5");
				e.update(bytes);
				byte[] tmp = e.digest();
				char[] str = new char[32];
				int k = 0;

				for(int i = 0; i < 16; ++i) {
					byte byte0 = tmp[i];
					str[k++] = hexDigits[byte0 >>> 4 & 15];
					str[k++] = hexDigits[byte0 & 15];
				}

				s = new String(str);
			} catch (Exception var8) {
				var8.printStackTrace();
			}

			return s;
		}

		public static void main(String[] args) {
			System.out.println(hexdigest("c"));
		}

	}

	/**
	 * BASE64算法
	 */
	public static class BASE64 {

		/**
		 * 加密
		 */
		public static byte[] encode(byte[] plain) {
			return Base64.encode(plain, Base64.DEFAULT);
		}

		/**
		 * 加密
		 */
		public static String encodeToString(byte[] plain) {
			return Base64.encodeToString(plain, Base64.DEFAULT);
		}

		/**
		 * 解密
		 */
		public static byte[] decode(String text) {
			return Base64.decode(text, Base64.DEFAULT);
		}

		/**
		 * 解密
		 */
		public static byte[] decode(byte[] text) {
			return Base64.decode(text, Base64.DEFAULT);
		}
	}

	/**
	 * SHA签名
	 */
	public static class SHA {

		public static byte[] encrypt(byte[] data) throws Exception {
			MessageDigest sha = MessageDigest.getInstance(Algorithm.SHA.getType());
			sha.update(data);
			return sha.digest();
		}

	}

	/**
	 * MAC秘钥
	 */
	public static class MAC {

		/**
		 * 初始化HMAC密钥
		 *
		 * @param algorithm 算法，可为空。默认为：Algorithm.Hmac_MD5
		 * @return str
		 * @throws Exception 异常
		 */
		public static String initMacKey(Algorithm algorithm) throws Exception {
			if (algorithm == null) algorithm = Algorithm.Hmac_MD5;
			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm.getType());
			SecretKey secretKey = keyGenerator.generateKey();

			return BASE64.encodeToString(secretKey.getEncoded());
		}

		/**
		 * HMAC加密
		 *
		 * @param plain     明文
		 * @param key       key
		 * @param algorithm 算法，可为空。默认为：Algorithm.Hmac_MD5
		 * @return byte
		 * @throws Exception 异常
		 */
		public static byte[] encrypt(byte[] plain, String key, Algorithm algorithm) throws Exception {
			if (algorithm == null) algorithm = Algorithm.Hmac_MD5;
			SecretKey secretKey = new SecretKeySpec(BASE64.decode(key), algorithm.getType());
			Mac mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);

			return mac.doFinal(plain);
		}
	}

	/**
	 * DES 秘钥
	 */
	public static class DES {

		/**
		 * 转换秘钥
		 *
		 * @param key 值
		 * @return Key
		 * @throws Exception 异常
		 */
		private static Key toKey(byte[] key) throws Exception {
			DESKeySpec dks = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm.DES.getType());
			return keyFactory.generateSecret(dks);
		}

		/**
		 * 解密
		 *
		 * @param plain 字节
		 * @param key   值
		 * @return byte
		 * @throws Exception 异常
		 */
		public static byte[] decrypt(byte[] plain, String key) throws Exception {
			Key k = toKey(BASE64.decode(key));

			Cipher cipher = Cipher.getInstance(Algorithm.DES.getType());
			cipher.init(Cipher.DECRYPT_MODE, k);

			return cipher.doFinal(plain);
		}

		/**
		 * 加密
		 *
		 * @param data 数据源
		 * @param key  值
		 * @return byte
		 * @throws Exception 异常
		 */
		public static byte[] encrypt(byte[] data, String key) throws Exception {
			Key k = toKey(BASE64.decode(key));
			Cipher cipher = Cipher.getInstance(Algorithm.DES.getType());
			cipher.init(Cipher.ENCRYPT_MODE, k);

			return cipher.doFinal(data);
		}

		/**
		 * 生成密钥
		 *
		 * @return str
		 * @throws Exception 异常
		 */
		public static String initKey() throws Exception {
			return initKey(null);
		}

		/**
		 * 生成密钥
		 *
		 * @param seed 目标位置
		 * @return str
		 * @throws Exception 异常
		 */
		public static String initKey(String seed) throws Exception {
			SecureRandom secureRandom;
			if (seed != null) {
				secureRandom = new SecureRandom(BASE64.decode(seed));
			} else {
				secureRandom = new SecureRandom();
			}

			KeyGenerator kg = KeyGenerator.getInstance(Algorithm.DES.getType());
			kg.init(secureRandom);

			SecretKey secretKey = kg.generateKey();

			return BASE64.encodeToString(secretKey.getEncoded());
		}
	}

	/**
	 * RSA秘钥
	 */
	public static class RSA {

		public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

		private static final String PUBLIC_KEY = "RSAPublicKey";
		private static final String PRIVATE_KEY = "RSAPrivateKey";


		/**
		 * 用私钥对信息生成数字签名
		 *
		 * @param data       加密数据
		 * @param privateKey 私钥
		 * @return str
		 * @throws Exception 异常
		 */
		public static String sign(byte[] data, String privateKey) throws Exception {
			byte[] keyBytes = BASE64.decode(privateKey);        // 解密由base64编码的私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);   // 构造PKCS8EncodedKeySpec对象
			KeyFactory keyFactory = KeyFactory.getInstance(Algorithm.RSA.getType());    // KEY_ALGORITHM 指定的加密算法
			PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);        // 取私钥匙对象
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);   // 用私钥对信息生成数字签名
			signature.initSign(priKey);
			signature.update(data);

			return BASE64.encodeToString(signature.sign());
		}

		/**
		 * 校验数字签名
		 *
		 * @param data      加密数据
		 * @param publicKey 公钥
		 * @param sign      数字签名
		 * @return boolean
		 * @throws Exception 异常
		 */
		public static boolean verify(byte[] data, String publicKey, String sign)
				throws Exception {

			byte[] keyBytes = BASE64.decode(publicKey); // 解密由base64编码的公钥
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  // 构造X509EncodedKeySpec对象
			KeyFactory keyFactory = KeyFactory.getInstance(Algorithm.RSA.getType());  // KEY_ALGORITHM 指定的加密算法
			PublicKey pubKey = keyFactory.generatePublic(keySpec);   // 取公钥对象

			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initVerify(pubKey);
			signature.update(data);

			return signature.verify(BASE64.decode(sign));
		}

		/**
		 * 用私钥解密
		 *
		 * @param data 字节流
		 * @param key  值
		 * @return byte
		 * @throws Exception 异常
		 */
		public static byte[] decryptByPrivateKey(byte[] data, String key)
				throws Exception {
			byte[] keyBytes = BASE64.decode(key);   // 对密钥解密

			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);   // 取得私钥
			KeyFactory keyFactory = KeyFactory.getInstance(Algorithm.RSA.getType());
			Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

			// 对数据解密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			return cipher.doFinal(data);
		}

		/**
		 * 用公钥解密
		 *
		 * @param data 字节流
		 * @param key  值
		 * @return byte
		 * @throws Exception 异常
		 */
		public static byte[] decryptByPublicKey(byte[] data, String key)
				throws Exception {
			byte[] keyBytes = BASE64.decode(key);       // 对密钥解密

			// 取得公钥
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(Algorithm.RSA.getType());
			Key publicKey = keyFactory.generatePublic(x509KeySpec);

			// 对数据解密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicKey);

			return cipher.doFinal(data);
		}

		/**
		 * 用公钥加密
		 *
		 * @param data 字节流
		 * @param key  值
		 * @return byte
		 * @throws Exception 异常
		 */
		public static byte[] encryptByPublicKey(byte[] data, String key)
				throws Exception {
			byte[] keyBytes = BASE64.decode(key);   // 对公钥解密

			// 取得公钥
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(Algorithm.RSA.getType());
			Key publicKey = keyFactory.generatePublic(x509KeySpec);

			// 对数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			return cipher.doFinal(data);
		}

		/**
		 * 用私钥加密
		 *
		 * @param data 字节流
		 * @param key  值
		 * @return byte
		 * @throws Exception 异常
		 */
		public static byte[] encryptByPrivateKey(byte[] data, String key)
				throws Exception {

			byte[] keyBytes = BASE64.decode(key);   // 对密钥解密

			// 取得私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(Algorithm.RSA.getType());
			Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

			// 对数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);

			return cipher.doFinal(data);
		}

		/**
		 * 取得私钥
		 *
		 * @param keyMap map值
		 * @return str
		 * @throws Exception 异常
		 */
		public static String getPrivateKey(Map<String, Object> keyMap)
				throws Exception {
			Key key = (Key) keyMap.get(PRIVATE_KEY);

			return BASE64.encodeToString(key.getEncoded());
		}

		/**
		 * 取得公钥
		 *
		 * @param keyMap map值
		 * @return str
		 * @throws Exception 异常
		 */
		public static String getPublicKey(Map<String, Object> keyMap)
				throws Exception {
			Key key = (Key) keyMap.get(PUBLIC_KEY);

			return BASE64.encodeToString(key.getEncoded());
		}

		/**
		 * 初始化密钥
		 *
		 * @return map
		 * @throws Exception 异常
		 */
		public static Map<String, Object> initKey() throws Exception {
			KeyPairGenerator keyPairGen = KeyPairGenerator
					.getInstance(Algorithm.RSA.getType());
			keyPairGen.initialize(1024);

			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();    // 公钥
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();     // 私钥
			Map<String, Object> keyMap = new HashMap<>(2);

			keyMap.put(PUBLIC_KEY, publicKey);
			keyMap.put(PRIVATE_KEY, privateKey);
			return keyMap;
		}

	}

	/**
	 * 自定义加密算法
	 */
	public static class MINE {

		private static final char[] gsdAlphabet = { 'Z', 'C', 'U', 'd', 'R', 'A', 'a',
				'Q', 'O', 'c', 'F', 'H', 'I', 'D', 'g', 'y', 'V', 'x', 'z', 'B',
				'S', 'k', 'Y', 't', 'G', 'b', 'h', 'T', 'v', 'l', 'u', 'e', 'J',
				'r', 'E', 'i', 'W', 'p', 'K', 'o', 'f', 'L', 'q', 'j', 'w', 's',
				'N', 'P', 'm', 'M', 'n', 'X' };

		/**
		 * 加密
		 *
		 * @param key
		 *            密钥
		 * @param str
		 *            需要加密的字符串
		 * @return boolean
		 */
		public static String encode(String key, String str) {
			if (TextUtils.isEmpty(key) || TextUtils.isEmpty(str)) {
				return null;
			}
			int id = 0;
			int l = key.length();
			int len = str.length();
			String res = null;
			try {
				char[] result = new char[len];
				for (int k = 0; k < str.length(); k++) {
					int a = (str.charAt(k) - '0') + (key.charAt(id) - 1 - '0');
					result[k] = gsdAlphabet[a];
					id++;
					if (id >= l) {
						id = 0;
					}
				}
				res = new String(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return res;
		}

	}

	@SuppressWarnings("WeakerAccess")
	public enum Algorithm {

		SHA("SHA"),
		MD5("MD5"),
		Hmac_MD5("HmacMD5"),
		DES("DES"),
		RSA("RSA"),
		MINE("MINE");

		private String type;

		Algorithm(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}

}