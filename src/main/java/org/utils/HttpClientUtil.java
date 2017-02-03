package org.utils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

/**
 * TODO
 *
 * @author Aaron
 * @date 2016年7月13日
 */
public class HttpClientUtil {

	public static String requestGet(String url) throws IOException {
		CloseableHttpClient httpclient = null;
		String respContent = null;
		try {
			httpclient = createSSLInsecureClient();
			HttpGet httpGet = new HttpGet(url);
			RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
			httpGet.setConfig(config);
			// HttpEntity
			CloseableHttpResponse response = httpclient.execute(httpGet);
			HttpEntity respEntity = response.getEntity();
			respContent = EntityUtils.toString(respEntity, "UTF-8");
		} catch (Exception e) {
			throw new IOException(e);
		} finally {
			if (httpclient != null) {
				httpclient.close();
			}
		}
		return respContent;
	}

	public static String requestPost(String url, Map<String, Object> paraMap)
			throws ClientProtocolException, IOException {
		if (paraMap == null) {
			paraMap = new HashMap<String, Object>();
		}
		CloseableHttpClient httpclient = null;
		String respContent = null;
		try {
			httpclient = createSSLInsecureClient();
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> entry : paraMap.entrySet()) {
				String key = entry.getKey();
				String value = String.valueOf(entry.getValue());
				nvps.add(new BasicNameValuePair(key, value));
			}
			HttpPost httpPost = new HttpPost(url);
			RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
			httpPost.setConfig(config);
			// HttpEntity
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httpPost);
			HttpEntity respEntity = response.getEntity();
			respContent = EntityUtils.toString(respEntity, "UTF-8");
		} catch (Exception e) {
			throw new IOException(e);
		} finally {
			if (httpclient != null) {
				httpclient.close();
			}
		}
		return respContent;
	}

	public static String requestPost(String url, String body) throws IOException {
		CloseableHttpClient httpclient = null;
		String respContent = null;
		try {
			httpclient = createSSLInsecureClient();
			HttpPost httpPost = new HttpPost(url);
			RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
			httpPost.setConfig(config);
			// HttpEntity
			httpPost.setEntity(new StringEntity(body));
			CloseableHttpResponse response = httpclient.execute(httpPost);
			HttpEntity respEntity = response.getEntity();
			respContent = EntityUtils.toString(respEntity, "UTF-8");
		} catch (Exception e) {
			throw new IOException(e);
		} finally {
			if (httpclient != null) {
				httpclient.close();
			}
		}
		return respContent;
	}

	public static CloseableHttpClient createSSLInsecureClient()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			// 信任所有
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}

			@Override
			public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType)
					throws java.security.cert.CertificateException {
				// TODO Auto-generated method stub
				return true;
			}
		}).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
		return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}

	public static void main(String[] args) throws IOException {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("uri", "/xinwo/calendar/list");
		System.out.println(requestPost("https://boxapi.mingque.yoloho.com/api/box/login/sendCode", paraMap));
	}

}
