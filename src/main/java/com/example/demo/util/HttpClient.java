package com.example.demo.util;

import java.io.BufferedReader;  
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *  @author hadoop
 */
public class HttpClient {


	private static Logger log = LoggerFactory.getLogger(HttpClient.class);

	/**
	 *     发送http post 请求
	 * @param url     请求地址 
	 * @param params  请求参数
	 * @param paramtype  参数形式 1：key-value形式 其他 json形式
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static  String post(String url, Map<String, Object> params, String paramtype)  throws Exception{

		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(400000).setConnectTimeout(400000)
				.setSocketTimeout(400000).setExpectContinueEnabled(false).build();

		HttpPost httppost = new HttpPost(url);

		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();// 设置进去
		if (params != null) {
			if (paramtype != null && paramtype.equals("1")) {
				// key value 形式
				List<NameValuePair> formParams = new ArrayList<>();
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
				}
				UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
				httppost.setEntity(uefEntity);
			} else {
				StringEntity rsqEntity;
				rsqEntity = new StringEntity(JSONObject.fromObject(params).toString(), "utf-8");
				rsqEntity.setContentEncoding("UTF-8");
				rsqEntity.setContentType("application/json");
				httppost.setEntity(rsqEntity);
			}
		}
		HttpResponse rsp = null;

		Long startTime = System.currentTimeMillis();

		log.info("=======================开始发送http请求:" + startTime);

		rsp = httpClient.execute(httppost);

		log.info(url + ":" + rsp.getStatusLine().getStatusCode());
		StringBuffer result = new StringBuffer();
		if (rsp != null) {
			HttpEntity entity = rsp.getEntity();
			InputStream in = entity.getContent();

			String temp;
			BufferedReader data = new BufferedReader(new InputStreamReader(in, "utf-8"));

			while ((temp = data.readLine()) != null) {
				result.append(temp);
			}
			log.info("=======================http 请求结束-------------- , 用时：" + (System.currentTimeMillis() - startTime) / 1000.0 + "秒");

		}
		httpClient.close();
		return result.toString();
	}


	public static String xml(String callURL, String xmlData) throws ClientProtocolException, IOException {
		
		RequestConfig config = RequestConfig.custom()
				.setConnectionRequestTimeout(400000)
				.setConnectTimeout(400000)
				.setSocketTimeout(400000)
				.setExpectContinueEnabled(false)
				.build();

		HttpPost httppost = new HttpPost(callURL);
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();// 设置进去

		StringEntity entity = new StringEntity(xmlData, "UTF-8");

		httppost.setHeader("User-Agent", "Mozilla/5.0");

		httppost.setEntity(entity);
		CloseableHttpResponse response = null;
		response = httpClient.execute(httppost);

		HttpEntity rspEntity = response.getEntity();
		InputStream in = rspEntity.getContent();

		log.info( callURL + ":" + response.getStatusLine().getStatusCode());

		String temp;
		BufferedReader data = new BufferedReader(new InputStreamReader(in, "utf-8"));
		StringBuffer result = new StringBuffer();
		while ((temp = data.readLine()) != null) {
			result.append(temp);
		}
		response.close();
		httpClient.close();
		return result.toString();
	}
}
