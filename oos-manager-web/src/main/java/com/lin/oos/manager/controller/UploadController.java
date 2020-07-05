package com.lin.oos.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lin.oos.service.UploadService;
import com.lin.oos.vo.UploadResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class UploadController {

    @Reference
    private UploadService uploadService;

    @PostMapping(value = "/image/upload.do")
    @ResponseBody
    public UploadResult upload(@RequestParam(value = "file") MultipartFile uploadFile)  {
        /*System.out.println("-文件上传-" + uploadFile);*/
        UploadResult upload = new UploadResult();

        try {
            InputStream inputStream = uploadFile.getInputStream();


            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buff = new byte[4096];
            int n = 0;
            while (-1 != (n = inputStream.read(buff))) {
                output.write(buff, 0, n);
            }
            byte[] bytes = output.toByteArray();


            upload = uploadService.upload(uploadFile.getOriginalFilename(), bytes);
        } catch (IOException e) {
            e.printStackTrace();
            upload.setError(1);
            upload.setMessage("上传失败");
        }




        return upload;

    }
}
