package com.sq.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.log4j.Logger;

import com.sq.log.result.LogResultInfo;
import com.sq.shell.ShellDao;
import com.sq.shell.ShellFactory;


public class URLHandler {

	private static Logger log = ShellFactory.logger;
	/**
	 * 通过get方式请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String sendGet(String url) throws Exception {
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		GetMethod getMethod = new GetMethod(url);
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			log.info("get请求结果(" + url + "):" + statusCode);
			// 读取内容
			byte[] responseBody = getMethod.getResponseBody();
			// 处理内容
			return new String(responseBody);
		} catch (Exception e) {
			throw e;
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
	}
	/**
	 * 通过Post方式	请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static String sendPost(String url, Map<String, String> param,int timeout) throws Exception {
		if(param == null){
			param = new HashMap<String, String>();
		}
//		param.put("authorize.username", (String)ServerInfo.getServerInfo("gm_username"));
//		param.put("authorize.signed.password", StringUtil.hash((String)ServerInfo.getServerInfo("gm_password") + (String)ServerInfo.getServerInfo("localIp") + "dfiG92B&32+1j^Xd9u543TY*32cF9@i34"));
		param.put("gm_username", "gmplatform");
		param.put("gm_password","gmplatformqazxsw12");	
		param.put("authorize.username", "gmplatform");
		param.put("authorize.password","gmplatformqazxsw12");	
		 HttpClient client = new HttpClient();
         PostMethod post = new PostMethod(url);
         try{
        	 if(param != null){
	        	 Iterator it = param.entrySet().iterator();
	        	 List<NameValuePair> paramList = new ArrayList<NameValuePair>();
	        	 while(it.hasNext()){
	        		 Map.Entry<String, String> entry = ( Map.Entry<String, String>)it.next();
	        		 NameValuePair nvp = new NameValuePair(entry.getKey(), entry.getValue());
	        		 
	        		 paramList.add(nvp);
	        	 }
	        	 
	        	 NameValuePair[] array = new NameValuePair[paramList.size()];
	        	 for(int i = 0; i < paramList.size(); i ++){
	        		 array[i] = paramList.get(i);
	        	 }
		         post.setRequestBody(array);
		         post.setQueryString(array);
        	 }
        	
        	 client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout*1000);
        	 client.getHttpConnectionManager().getParams().setSoTimeout(timeout*1000);
	         int status = client.executeMethod(post);
	        
	         String result = EncodingUtil.getString(post.getResponseBody(), "UTF-8");
	         return result;
         }catch(Exception e){
//        	 e.printStackTrace();
        	 log.error(e);
        	 throw e;
         }finally{
        	 post.releaseConnection();
         }
	}

	@SuppressWarnings("unchecked")
	public static void  getConnectionByPost(String url, Map<String, String> param,int timeout,LogResultInfo info) throws Exception {
		if(param == null){
			param = new HashMap<String, String>();
		}
		String name = param.get("cmd");
		param.put("gm_username", "gmplatform");
		param.put("gm_password","gmplatformqazxsw12");	
		param.put("authorize.username", "gmplatform");
		param.put("authorize.password","gmplatformqazxsw12");	
		 HttpClient client = new HttpClient();
         PostMethod post = new PostMethod(url);
         long start = System.currentTimeMillis();
         try{
        	 if(param != null){
	        	 Iterator it = param.entrySet().iterator();
	        	 List<NameValuePair> paramList = new ArrayList<NameValuePair>();
	        	 while(it.hasNext()){
	        		 Map.Entry<String, String> entry = ( Map.Entry<String, String>)it.next();
	        		 NameValuePair nvp = new NameValuePair(entry.getKey(), entry.getValue());
	        		 
	        		 paramList.add(nvp);
	        	 }
	        	 
	        	 NameValuePair[] array = new NameValuePair[paramList.size()];
	        	 for(int i = 0; i < paramList.size(); i ++){
	        		 array[i] = paramList.get(i);
	        	 }
		         post.setRequestBody(array);
		         post.setQueryString(array);
        	 }
        	
        	 client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout*1000);
        	 client.getHttpConnectionManager().getParams().setSoTimeout(timeout*1000);
	         client.executeMethod(post);
	         InputStream is =post.getResponseBodyAsStream();
	         BufferedReader	 in = new BufferedReader(new InputStreamReader(is,"UTF-8"));
	            String line;
	            int cou = 0 ;
	            while ((line = in.readLine()) != null) {
	            	if(info.isCancel()){
	            		String n = ShellDao.getFileShowName(name);
	            		info.setScheduleMsg(n, "强制取消成功");
	            		break;
	            	}
	               info.getDatas().put(line);
	               info.setSize(1);
	               info.setSize(name, 1);
	               System.out.println(line);
	               if(++cou >=100){
	            	   break;
	               }
	            }
				log.debug("[URLHandler][getConnectionByPost][size:" + info.getSize()	+ "][info:"+info+"][url:" + url + "][param:"+param+"][耗时："+ (System.currentTimeMillis() - start) + "]");
         }catch(Exception e){
        	 e.printStackTrace();
        	 info.setMsg(name+"超时");
        	 log.error(e);
        	 throw e;
         }finally{
        	 post.releaseConnection();
         }
	}
	
	public static byte[] webPost(URL url, byte[] content, Map headers,	int connectTimeout, int readTimeout) throws Exception {
		HttpURLConnection urlconnection = null;
		try {
			urlconnection = (HttpURLConnection) url.openConnection();

			String jdkV = System.getProperty("java.vm.version");
			if (jdkV != null) {
				Class clazz = urlconnection.getClass();
				try {
					Method method = clazz.getMethod("setConnectTimeout",
							new Class[] { Integer.TYPE });
					method.invoke(urlconnection, new Object[] { new Integer(
							connectTimeout) });
					method = clazz.getMethod("setReadTimeout",
							new Class[] { Integer.TYPE });
					method.invoke(urlconnection, new Object[] { new Integer(
							readTimeout) });
				} catch (Exception ec) {
					ec.printStackTrace();
				}
			} else {
				System.setProperty("sun.net.client.defaultConnectTimeout",
						new StringBuilder().append("").append(connectTimeout)
								.toString());
				System.setProperty("sun.net.client.defaultReadTimeout",
						new StringBuilder().append("").append(readTimeout)
								.toString());
			}

			Iterator it = headers.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry me = (Map.Entry) it.next();
				String key = (String) me.getKey();
				String value = (String) me.getValue();
				if ((key != null) && (value != null)) {
					urlconnection.setRequestProperty(key, value);
				}
			}
			urlconnection.setRequestProperty("Request-method", "post");
			urlconnection.setRequestProperty("Content-length", Integer
					.toString(content.length));

			urlconnection.setDoInput(true);
			urlconnection.setDoOutput(true);

			OutputStream outputstream = urlconnection.getOutputStream();
			outputstream.write(content);
			outputstream.flush();

			String contentType = urlconnection.getContentType();
			String encoding = null;
			if ((contentType != null)
					&& (contentType.toLowerCase().indexOf("charset") > 0)) {
				int k = contentType.toLowerCase().indexOf("charset");
				if (contentType.length() > k + 7) {
					String sss = contentType.substring(k + 7).trim();
					k = sss.indexOf("=");
					if ((k >= 0) && (sss.length() > k + 1)) {
						encoding = sss.substring(k + 1).trim();
						if (encoding.indexOf(";") > 0) {
							encoding = encoding.substring(0,
									encoding.indexOf(";")).trim();
						}
					}
				}
			}

			headers.clear();

			int k = 0;
			String feildValue = null;
			while ((feildValue = urlconnection.getHeaderField(k)) != null) {
				String key = urlconnection.getHeaderFieldKey(k);
				k++;
				if (key != null) {
					headers.put(key, feildValue);
				}
			}
			headers.put("Response-Code", new Integer(urlconnection
					.getResponseCode()));
			headers.put("Response-Message", urlconnection.getResponseMessage());

			InputStream bis = urlconnection.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] bytes = new byte[1024];
			int n = 0;

			int count = 0;
			while ((n = bis.read(bytes)) > 0) {
				out.write(bytes, 0, n);
			}
			bis.close();
			bytes = out.toByteArray();
			out.close();
			outputstream.close();

			if (encoding == null) {
				encoding = "UTF-8";
			}
			headers.put("Encoding", encoding);

			byte[] arrayOfByte1 = bytes;
			return arrayOfByte1;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (urlconnection != null)
				urlconnection.disconnect();
		}
	}

}
