package com.util;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

public class ComUtil {
	
	private static Gson gson = new Gson();
	
	public static <T> T fromJson(String str, Class<T> c) {
		return gson.fromJson(str, c);
	}
	
	public static <T> String toJson(T t) {
		return gson.toJson(t);
	}

    @SuppressWarnings({ "unchecked" })
    public static <T> T fromXML(String content,Class<T> c) throws InstantiationException, IllegalAccessException {
 	   XStream xstream = new XStream();
 	   xstream.alias("xml", c);
 	   T t = (T)xstream.fromXML(content);
 	   return t;
    }
    
    public static <T> String toXML(T t,Class<T> c) {
 	   XStream xstream = new XStream(); 
 	   xstream.alias("xml", c);
 	   String content = xstream.toXML(t);
 	   content = content.replaceAll("&lt;","<");
 	   content = content.replaceAll("&gt;",">");
 	   return content;
    }

    /**
     * 将字节数组转换为十六进制字符串
     * 
     * @param byteArray
     * @return
     */
    public static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     * @param mByte
     * @return
     */
    public static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
    public static void sort(String a[]) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[j].compareTo(a[i]) < 0) {
                    String temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

	public static String fromCDATA(String msgType) {
		if (msgType==null || msgType.length()==0)
			return null;
		if (msgType.length()>=11 && msgType.contains("<![CDATA[")) {
			msgType = msgType.substring(0, msgType.length()-3);
			msgType = msgType.substring(9,msgType.length());
		}
		return msgType;
	}

	public static String toCDATA(String msgType) {
		if (msgType==null)
			return msgType;
		return "<![CDATA["+msgType+"]]>";
	}
	
	public static httpsRsp httpsGetSend(String url) throws HttpException, IOException {
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        int code = httpClient.executeMethod(getMethod);
        String desc = getMethod.getResponseBodyAsString();
        return new httpsRsp(code,desc);
	}
	
	@SuppressWarnings("deprecation")
	public static httpsRsp httpsPostSend(String url,String param) throws HttpException, IOException {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8"); 
        postMethod.setRequestBody(param);
        int code = httpClient.executeMethod(postMethod);
        String desc = postMethod.getResponseBodyAsString();
        return new httpsRsp(code,desc);
	}
	
	public static int getCurrentTime() {
		long l = new Date().getTime();
		return (int)(l/1000);
	}
}
