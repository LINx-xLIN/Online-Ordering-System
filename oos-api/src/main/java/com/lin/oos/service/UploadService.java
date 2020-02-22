package com.lin.oos.service;

import com.lin.oos.vo.UploadResult;

import java.io.InputStream;

public interface UploadService {



    UploadResult upload(String originalFilename, byte[] bytes);
}
