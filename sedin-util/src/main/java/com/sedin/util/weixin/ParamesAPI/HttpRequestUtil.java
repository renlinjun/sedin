package com.sedin.util.weixin.ParamesAPI;

import java.io.*;
import java.net.*;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

public class HttpRequestUtil {


	/**
	 * 发起https请求并获取结果
	 *
	 * @param request
	 *            请求地址
	 * @param RequestMethod
	 *            请求方式（GET、POST）
	 * @param output
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String request, String RequestMethod, String output) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 建立连接
			URL url = new URL(request);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod(RequestMethod);
			if (output != null) {
				//conn.setRequestProperty("Content-Type", "plain/text; charset=UTF-8");
				connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
				OutputStream out = connection.getOutputStream();
				out.write(output.getBytes("UTF-8"));
				out.flush();
				out.close();
			}
			// 流处理
			InputStream input = connection.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(input, "UTF-8");
			BufferedReader reader = new BufferedReader(inputReader);
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			// 关闭连接、释放资源
			reader.close();
			inputReader.close();
			input.close();
			input = null;
			connection.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}


	/**
	 * 发起https请求并获取结果
	 * @param request 请求地址
	 * @param filePath 提交的文件路径
	 * @param fileType type
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequestFile(String request, String filePath , String fileType) {
		JSONObject jsonObject = null;
		// 创建SSLContext对象，并使用我们指定的信任管理器初始化
		TrustManager[] tm = { new MyX509TrustManager() };
		//安全套接字的上下文
		SSLContext sslContext;
		StringBuffer buffer = new StringBuffer();

		// 输入流来读取URL的响应
		BufferedReader reader = null;
		String result = null;
		String fileName = "default.jpg";
		File file = null;
		InputStream  input = null;
		try {
			if(filePath.startsWith("http")){//远程
				URL Fileurl = new URL(filePath);
				HttpURLConnection conn = (HttpURLConnection) Fileurl.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5 * 1000);
				input = conn.getInputStream();// 通过输入流获取图片数据
				fileName = filePath.substring(filePath.lastIndexOf("/")+1);
			}else{
				file = new File(filePath);//本地
				fileName = file.getName();
				input = new DataInputStream(new FileInputStream(file));
			}
			sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			// 建立连接
			URL url = new URL(request);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false); // post方式不能使用缓存
			// 设置请求头信息
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Charset", "UTF-8");
			// 设置边界
			String boundary = "-----------------------------"+System.currentTimeMillis();
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary);
			StringBuffer sbuff=new StringBuffer();
			sbuff.append("--").append(boundary).append("\r\n");
			sbuff.append("Content-Disposition: form-data;name=\"type\" \r\n\r\n");
			sbuff.append(fileType);
			sbuff.append("\r\n--").append(boundary).append("\r\n");
			//在上传视频素材时需要POST另一个表单，id为description
			if("video/mp4".equals(fileType)){
				sbuff.append("Content-Disposition: form-data;name=\"description\"\r\n\r\n");
				sbuff.append("{\"title\":\"hello title\", \"introduction\":\"hello introduction\"}");
				sbuff.append("\r\n--").append(boundary).append("\r\n");
			}
			sbuff.append("Content-Disposition: form-data;name=\"media\";filename=\"" + fileName + "\" \r\n");
			sbuff.append("Content-Type:application/octet-stream\r\n\r\n");
			System.out.println(sbuff.toString());

			byte[] head = sbuff.toString().getBytes("utf-8");
			// 获得输出流
			OutputStream output = new DataOutputStream(connection.getOutputStream());
            // 输出表头
			output.write(head);
            // 文件正文部分
			// 把文件已流文件的方式 推入到url中
			byte[] data = new byte[1024];
			int len =0;
			while((len=input.read(data))>-1){
				output.write(data, 0, len);
			}
			input.close();
			// 结尾部分
			byte[] foot = ("\r\n--" + boundary + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
			output.write(foot);
			output.flush();
			output.close();

			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}finally {

		}
		return jsonObject;
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest2(String requestUrl, String requestMethod, String outputStr) {

		System.err.println(requestMethod+"\toutputStr="+outputStr);

		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				httpUrlConn.connect();
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
			//System.out.println("jsonObject="+jsonObject);
		} catch (ConnectException ce) {
			ce.printStackTrace();
			System.out.println("网络链接失败！");
		}catch (UnknownHostException uhe) {
			uhe.printStackTrace();
			System.out.println("微信API无法访问....！");
			//httpRequest(requestUrl, requestMethod, outputStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * 发起https请求并获取字节数组结果
	 * @param requestUrl
	 * @param requestMethod
	 * @param data
	 * @return
	 */
	public static byte[] httpRequest_byte(String requestUrl, String requestMethod, byte[] data) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			if (requestMethod == EnumMethod.GET.name() && data != null && data.length > 0) {
				if (requestUrl.indexOf('?') > 0) {
					requestUrl += '&';
				} else {
					requestUrl += '?';
				}
				requestUrl += new String(data);
			}
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			if (httpUrlConn instanceof HttpsURLConnection) {
				// 创建SSLContext对象，并使用我们指定的信任管理器初始化
				TrustManager[] tm = { new MyX509TrustManager() };
				SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
				sslContext.init(null, tm, new SecureRandom());
				// 从上述SSLContext对象中得到SSLSocketFactory对象
				SSLSocketFactory ssf = sslContext.getSocketFactory();
				((HttpsURLConnection) httpUrlConn).setSSLSocketFactory(ssf);
			}
			boolean truePost = requestMethod == EnumMethod.POST.name() && data != null && data.length > 0;
			httpUrlConn.setDoOutput(truePost);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if (requestMethod == EnumMethod.GET.name()) {
				httpUrlConn.connect();
			} else if (truePost) {
				// 提交数据
				OutputStream outputStream = httpUrlConn.getOutputStream();
				outputStream.write(data);
				outputStream.close();
			}

			// 读取返回数据
			InputStream inputStream = httpUrlConn.getInputStream();
			byte[] buf = new byte[1024 * 1024];
			int len;
			while ((len = inputStream.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			// 释放资源
			out.close();
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}
		return out.toByteArray();
	}


	/**
	 * 获取文件流
	 * @param requestUrl
	 * @return
	 */
	public static Object getFileUploadStream(String requestUrl) {
		HttpURLConnection conn = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			URL url = new URL(requestUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.connect();
			InputStream input = conn.getInputStream();
			System.out.println(conn.getResponseMessage());
			System.out.print(conn.getContentType());
			if("text/plain".equals(conn.getContentType())){
				StringBuffer buffer = new StringBuffer();
				// 将返回的输入流转换成字符串
				InputStreamReader inputStreamReader = new InputStreamReader(input, "utf-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String str = null;
				while ((str = bufferedReader.readLine()) != null) {
					buffer.append(str);
				}
				bufferedReader.close();
				JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
				return  jsonObject;
			}else{
				byte[] buf = new byte[1024 * 1024];
				int len;
				while ((len = input.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				return out.toByteArray();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			// 释放资源
			try {
				out.close();
				conn.disconnect();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return  null;
	}
}
