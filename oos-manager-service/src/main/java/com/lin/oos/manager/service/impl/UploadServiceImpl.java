package com.lin.oos.manager.service.impl;

import com.lin.oos.service.UploadService;
import com.lin.oos.util.FtpUtils;
import com.lin.oos.util.IDUtils;
import com.lin.oos.vo.UploadResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
@com.alibaba.dubbo.config.annotation.Service
public class UploadServiceImpl implements UploadService {


    @Value("${ftp.hostname}")
    private String hostname;
    @Value("${ftp.port}")
    private int port;
    @Value("${ftp.username}")
    private String username;
    @Value("${ftp.password}")
    private String password;
    @Value("${ftp.basePath}")
    private String basePath;
    @Value("${pic.url}")
    private String picBaseUrl;


    @Override
    public UploadResult upload(String originalFilename,byte[] bytes) {


        InputStream inputStream = new ByteArrayInputStream(bytes);

        //第一步：构建返回对象
        UploadResult result=new UploadResult();
        //第二步：上传处理
        //需求：文件路径为/ego/images/年份/月份/日/
        Date date=new Date();
        final String year = new SimpleDateFormat("yyyy").format(date);
        final  String month=new SimpleDateFormat("MM").format(date);
        final  String day=new SimpleDateFormat("dd").format(date);

        String filePath="/oos/images/"+year+"/"+month+"/"+day;
        //原图片名
        //String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
        /*System.out.println(extName);*/

        String fileName= IDUtils.genImageName()+extName;

        //InputStream in=file.getInputStream();
        boolean upload = FtpUtils.upload(hostname, port, username, password, basePath, filePath, fileName, inputStream);
        if (upload){
            result.setError(0);
            String url=picBaseUrl+"/"+year+"/"+month+"/"+day+"/"+fileName;
            result.setUrl(url);
        }else {
            result.setError(1);
            result.setMessage("上传失败");
        }

        return result;



    }
}
