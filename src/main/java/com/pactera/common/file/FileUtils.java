package com.pactera.common.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.pactera.common.constants.Constants;
import com.pactera.common.utils.UIHelper;

/**
 * 文件处理工具类.
 * 
 * <pre>
 * <b>依赖jar包：</b>
 * javax.servlet-api-3.0.1.jar
 * spring-web-4.1.3.RELEASE.jar
 * spring-mock-2.0.8.jar
 * </pre>
 * 
 * @author wangchaojie 2018年1月10日
 */
public class FileUtils {

	public static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

//	/**
//	 * 上传文件
//	 * 
//	 * @param request
//	 *            请求对象
//	 * @param file
//	 *            控制层获取到的文件
//	 * @param uploadSubPath
//	 *            文件的路径
//	 * @return 上传后的文件信息map
//	 * 
//	 */
//	public static void uploadFile(MultipartFile file, File destFile) {
//			// 上传
//			file.transferTo(destFile);
//	}

	/**
	 * 上传文件
	 * 
	 * @param request       请求对象
	 * @param file          控制层获取到的文件
	 * @param uploadSubPath 文件的路径
	 * @return 上传后的文件信息map
	 * 
	 */
	public static Map<String, Object> uploadFile(HttpServletRequest request, MultipartFile file, String uploadSubPath) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (file.isEmpty()) {
				jsonMap.put("success", false);
				jsonMap.put("errorCode", "fileEmpty");
				jsonMap.put("error", "上传文件为空");
				return jsonMap;
			}

			// 获取文件名
			String fileName = file.getOriginalFilename();

			// 获取文件后缀
			String fileSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			// 获取当前当前配置保存物理路径
			if (StrUtils.isEmpty(uploadSubPath)) {
				uploadSubPath = "/default";
			}

			Constants.loadConfig();

			// 创建相对路径
			String serverRealPath = FileUtils.getRealPath(request);
			String dirRelativePath = uploadSubPath + "/" + sdf.format(new Date());
			String dirRealPath = serverRealPath + dirRelativePath;
			// 创建文件夹
			File uploadDir = new File(dirRealPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			// 文件重命名
			String fileEnd = System.currentTimeMillis() + "";

			String uploadFileName = fileEnd + fileSuffix;

			// 创建文件
			File uploadFile = new File(dirRealPath + "/" + uploadFileName);
			// 上传
			file.transferTo(uploadFile);

			String relativeUrl = dirRelativePath + "/" + uploadFileName;// 文件的相对路径
			String domain = getAccessDomain(request);
			String accessUrl = domain + relativeUrl;

			jsonMap.put("success", true);
			jsonMap.put("url", relativeUrl);
			jsonMap.put("accessUrl", accessUrl);
			jsonMap.put("realName", fileName);
			jsonMap.put("realPath", uploadFile.getPath());
			jsonMap.put("fileName", uploadFileName);
			jsonMap.put("file", uploadFile);
		} catch (Exception e) {
			jsonMap.put("successData", false);
			jsonMap.put("error", e.getMessage());
			e.printStackTrace();
			logger.error("上传文件异常", e);
		}
		return jsonMap;
	}

	/**
	 * 
	 * 检查上传文件大小是否超限.
	 * 
	 * <pre>
	 * FileUtils.isFileExceedSize(null,12) = true;
	 * FileUtils.isFileExceedSize(File,0()) = false;
	 * </pre>
	 * 
	 * @param file      文件对象
	 * @param limitSize 限制文件大小(单位:MB)
	 * @return true 超限； false 没有超限
	 *
	 */
	public static boolean isFileExceedSize(File file, long limitSize) {
		boolean isValidSize = true;
		if (file == null) {
			return isValidSize;
		}
		long fileSize = file.length();
		if (limitSize > 0) {
			if (fileSize > 0 && fileSize <= (limitSize * 1024 * 1024)) {
				isValidSize = false;
			}
		} else {
			isValidSize = false;
		}
		return isValidSize;
	}

	/**
	 * 下载文件
	 * 
	 * @param file     
	 * @throws FileNotFoundException,IOException
	 */
	public static void downloadFile(File file, HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, IOException {
		if (!file.exists()) {
			throw new FileNotFoundException();
		}

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		String fileName = file.getName();
		response.setContentType("application/octet-stream");
		boolean isMSIE = isMSBrowser(request);
		if (isMSIE) {
			// IE浏览器的乱码问题解决
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} else {
			// 万能乱码问题解决
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		}
		response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");

		try {
			bis = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}

		}
	}

	private static String[] IEBrowserSignals = { "MSIE", "Trident", "Edge" };

	public static boolean isMSBrowser(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		for (String signal : IEBrowserSignals) {
			if (userAgent.contains(signal)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 从网络上下载文件到本地.</br>
	 * PS：url请求超时设置3秒.
	 * 
	 * @param srcUrl        网络文件url
	 * @param localFileName 存储到本地的文件名
	 * @param localPath     存储到本地的目录
	 * @throws IOException
	 * 
	 */
	public static void downloadFile(String srcUrl, String localFileName, String localPath) throws IOException {
		InputStream inputStream = null;

		FileOutputStream fos = null;

		try {
			URL u = new URL(srcUrl);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			// 设置超时间为3秒
			conn.setConnectTimeout(3 * 1000);
			// 防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

			// 得到输入流
			inputStream = conn.getInputStream();
			// 获取自己数组
			byte[] getData = readInputStream(inputStream);

			// 文件保存位置
			File saveDir = new File(localPath);
			if (!saveDir.exists()) {
				saveDir.mkdir();
			}
			File file = new File(saveDir + "/" + localFileName);
			fos = new FileOutputStream(file);
			fos.write(getData);

		} catch (IOException e) {
			e.printStackTrace();
			logger.error("下载文件异常", e);
		} finally {
			if (fos != null) {
				fos.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	/**
	 * 从输入流中获取字节数组
	 * 
	 * @param inputStream 输入流
	 * @return byte[]
	 * @throws IOException
	 */
	private static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}

	/**
	 * 文件拷贝
	 * 
	 * @param srcFile  源文件
	 * @param destFile 拷贝后的文件
	 */
	public static void copyFile(File srcFile, File destFile) {
		createDir(destFile);
		int length = 1024;
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			FileChannel inC = in.getChannel();
			FileChannel outC = out.getChannel();
			ByteBuffer b = null;
			while (true) {
				if (inC.position() == inC.size()) {
					inC.close();
					outC.close();
					break;
				}
				if ((inC.size() - inC.position()) < length) {
					length = (int) (inC.size() - inC.position());
				}
				b = ByteBuffer.allocateDirect(length);
				inC.read(b);
				b.flip();
				outC.write(b);
				outC.force(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void createDir(File file) {
		file = file.getParentFile();
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 获取上传物理路径
	 * 
	 * @return
	 */
	public static String getRealPath(HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("");// 获得项目的物理路径
		String mode = Constants.UPLOAD_MODE;
		if ("nas".equals(mode)) {
			if (StrUtils.isNotEmpty(Constants.UPLOAD_NAS_DIR)) {
				realPath = Constants.UPLOAD_NAS_DIR;
			}
		}
		return realPath;
	}

	/**
	 * 获取文件访问域名
	 * 
	 * @return
	 */
	public static String getAccessDomain(HttpServletRequest request) {
		String accessDomain = UIHelper.getWebRoot(request);
		String mode = Constants.UPLOAD_MODE;
		if ("nas".equals(mode)) {
			String domain = UIHelper.getWebDomain(request);
			accessDomain = domain + Constants.UPLOAD_NAS_ACCESS;
		}
		return accessDomain;
	}
}
