package com.ego.manage.service.imp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ego.commons.utils.FtpUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.manage.service.PicService;

@Service
public class PicServiceImp implements PicService {

	@Value("${ftpclient.host}")
	private String host;
	@Value("${ftpclient.port}")
	private int port;
	@Value("${ftpclient.username}")
	private String username;
	@Value("${ftpclient.password}")
	private String password;
	@Value("${ftpclient.basepath}")
	private String basepath;
	@Value("${ftpclient.filepath}")
	private String filepath;

	@Override
	public Map<String, Object> upload(MultipartFile uploadFile) throws IOException {
		Map<String, Object> map = new HashMap<>();
		// 获取文件名
		String genFilename = IDUtils.genImageName()
				+ uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf("."));
		boolean result = FtpUtil.uploadFile(host, port, username, password, basepath, filepath, genFilename,
				uploadFile.getInputStream());
		if (result) {
			// 上传成功
			map.put("error", 0);
			map.put("url", "http://" + host + "/" + genFilename);
		} else {
			// 上传失败
			map.put("error", 1);
			map.put("message", "图片上传时出现错误");
		}

		return map;
	}
}
