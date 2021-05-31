package com.dw.demo.util;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * HTTP工具类
 * 
 * @author xhGuo
 */
public class HttpUtil {

	/**
	 * 忽视证书HostName
	 */
	private static HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
		public boolean verify(String s, SSLSession sslsession) {
			return true;
		}
	};

	/**
	 * Ignore Certification
	 */
	private static TrustManager ignoreCertificationTrustManger = new X509TrustManager() {
		private X509Certificate[] certificates;

		public void checkClientTrusted(X509Certificate certificates[], String authType) throws CertificateException {
			if (this.certificates == null) {
				this.certificates = certificates;
			}

		}

		public void checkServerTrusted(X509Certificate[] ax509certificate, String s) throws CertificateException {
			if (this.certificates == null) {
				this.certificates = ax509certificate;
			}
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};

	/**
	 * 获取UrlConn
	 */
	public static URLConnection geneUrlConn(String urlstr) throws Exception {
		URL url = new URL(urlstr);
		if (urlstr.startsWith("https://")) {
			HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
			HttpsURLConnection httpsConnection = (HttpsURLConnection) url.openConnection();

			// Prepare SSL Context
			TrustManager[] tm = { ignoreCertificationTrustManger };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			httpsConnection.setSSLSocketFactory(ssf);
			return httpsConnection;
		} else {
			return url.openConnection();
		}
	}

	/**
	 * HTTP GET
	 * 
	 * @param url
	 *            访问的地址
	 * @param param
	 *            请求参数
	 * @return 返回值
	 */
	public static String sendGet(String url, String param) {
		if (url == null || url.equals(""))
			throw new NullPointerException("url");
		String result = "";
		BufferedReader in = null;
		try {
			if (param != null && !param.equals(""))
				param = param.toString().replace("\"", "%22").replace("{", "%7b").replace("}", "%7d");
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"gb2312"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}

		return result;
	}

	/**
	 * HTTP GET
	 * 
	 * @param url
	 *            访问的地址
	 * @return 返回值
	 */
	public static String sendGet(String url) {
		return sendGet(url, null);
	}

	/**
	 * HTTP POST
	 * 
	 * @param url
	 *            访问的地址
	 * @param param
	 *            请求参数
	 * @return 返回值
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {

			// URL realUrl = new URL(url);
			URLConnection conn = geneUrlConn(url);
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0;Windows NT 5.1;SV1)");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(60000);
			conn.setReadTimeout(60000); 
			out = new PrintWriter(conn.getOutputStream());
			out.print(param);
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			throw new 	RuntimeException(e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}

		return result;
	}

	/**
	 * HTTP POST
	 * 
	 * @param url
	 *            访问的地址
	 * @return 返回值
	 */
	public static String sendPost(String url) {
		return sendPost(url, null);
	}

	public static void main(String[] args) throws Exception {
		String url = "http://127.0.0.1:8090/ceshi";
		// String image= "D://100000_20180413140559.jpg";
		String image = "D://Image_00001.jpg";
		String key = "aaa";
		String value = "bbb";
		System.out.println("content-length1: " + new File(image).length());
		String result = uploadFileWithHeader3(url, key, value, "", image);
		System.out.println(result);
	}

	/**
	 * 与delphi的server对接，body中只放stream 解决了delphi不认识分隔符导致图片保存后无法打开的错误
	 * 
	 * @param urlPath
	 * @param pich
	 * @param scanType
	 *            识别类型
	 * @param fileType
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static String uploadFileWithHeader3(String urlPath, String pich, String scanType, String fileType,
			String filePath) throws IOException {
		String urlStr = urlPath + "?" + "&pich=" + pich + "&scantype=" + scanType + "&filetype=" + fileType
				+ "&filename=" + new File(filePath).getName();

		String towHyphens = "--"; // 定义连接字符串
		String boundary = "******"; // 定义分界线字符串
		String end = "\r\n"; // 定义结束换行字符串
		OutputStream outputStream = null;
		DataOutputStream dataOutputStream = null;
		FileInputStream fileInputStream = null;
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		InputStream inputStream = null;
		String result = null;
		try {
			// 创建URL对象
			URL url = new URL(urlStr);
			// 获取连接对象
			URLConnection urlConnection = url.openConnection();
			// 设置允许输入流输入数据到本机
			urlConnection.setDoOutput(true);
			// 设置允许输出流输出数据到服务器
			urlConnection.setDoInput(true);
			// 设置不使用缓存
			urlConnection.setUseCaches(false);
			// 设置请求参数中的内容类型为multipart/form-data,设置请求内容的分割线为******
			urlConnection.setRequestProperty("Content-Type", "application/octet-stream;boundary=" + boundary);
			// 从连接对象中获取输出流
			outputStream = urlConnection.getOutputStream();
			// 实例化数据输出流对象，将输出流传入
			dataOutputStream = new DataOutputStream(outputStream);

			// 遍历文件路径的长度,将路径数组下所有路径的文件都写到输出流中
			// for (int i = 0; i < filePaths.length; i++) {
			// 取出文件路径
			// 获取文件名称
			// String fileName = filePath.substring(filePath.lastIndexOf("/") +
			// 1);
			// 向数据输出流中写出分割符
			// dataOutputStream.writeBytes(towHyphens + boundary + end);
			// 向数据输出流中写出文件参数名与文件名
			// dataOutputStream.writeBytes("Content-Disposition:form-data;name=file;filename="
			// + fileName + end);
			// 向数据输出流中写出结束标志
			// dataOutputStream.writeBytes(end);

			// 实例化文件输入流对象，将文件路径传入，用于将磁盘上的文件读入到内存中
			fileInputStream = new FileInputStream(filePath);
			// 定义缓冲区大小
			int bufferSize = 1024;
			// 定义字节数组对象，用来读取缓冲区数据
			byte[] buffer = new byte[bufferSize];
			// 定义一个整形变量，用来存放当前读取到的文件长度
			int length;
			// 循环从文件输出流中读取1024字节的数据，将每次读取的长度赋值给length变量，直到文件读取完毕，值为-1结束循环
			while ((length = fileInputStream.read(buffer)) != -1) {
				// 向数据输出流中写出数据
				dataOutputStream.write(buffer, 0, length);
			}
			// 每写出完成一个完整的文件流后，需要向数据输出流中写出结束标志符
			// dataOutputStream.writeBytes(end);
			// 关闭文件输入流
			// fileInputStream.close();

			// }
			// 向数据输出流中写出分隔符
			// dataOutputStream.writeBytes(towHyphens + boundary + towHyphens +
			// end);
			// 刷新数据输出流
			dataOutputStream.flush();

			// 从连接对象中获取字节输入流
			inputStream = urlConnection.getInputStream();
			// 实例化字符输入流对象，将字节流包装成字符流
			inputStreamReader = new InputStreamReader(inputStream);
			// 创建一个输入缓冲区对象，将要输入的字符流对象传入
			bufferedReader = new BufferedReader(inputStreamReader);

			// 创建一个字符串对象，用来接收每次从输入缓冲区中读入的字符串
			String line;
			// 创建一个可变字符串对象，用来装载缓冲区对象的最终数据，使用字符串追加的方式，将响应的所有数据都保存在该对象中
			StringBuilder stringBuilder = new StringBuilder();
			// 使用循环逐行读取缓冲区的数据，每次循环读入一行字符串数据赋值给line字符串变量，直到读取的行为空时标识内容读取结束循环
			while ((line = bufferedReader.readLine()) != null) {
				// 将缓冲区读取到的数据追加到可变字符对象中
				stringBuilder.append(line);
			}

			// 依次关闭打开的输入流
			// bufferedReader.close();
			// inputStreamReader.close();
			// inputStream.close();

			// 依次关闭打开的输出流
			// dataOutputStream.close();
			// outputStream.close();

			// 返回服务器响应的数据
			result = stringBuilder.toString();

		} catch (IOException e) {
			throw e;
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (outputStream != null) {
				outputStream.close();
			}

			if (dataOutputStream != null) {
				dataOutputStream.close();
			}

			if (fileInputStream != null) {
				fileInputStream.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}
		}
		return result;

	}

}
