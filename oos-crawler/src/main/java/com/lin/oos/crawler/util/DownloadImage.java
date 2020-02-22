package com.lin.oos.crawler.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadImage {


    public static byte[] downloadImg(String urlString)  {


        try {
            URL url = new URL(urlString);// 构造URL
            URLConnection con = url.openConnection();// 打开连接
            InputStream inputStream = con.getInputStream();// 输入流
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buff = new byte[4096];
            int n = 0;
            while (-1 != (n = inputStream.read(buff))) {
                output.write(buff, 0, n);
            }
            byte[] bytes = output.toByteArray();
            return bytes;


        } catch (IOException e) {
            e.printStackTrace();

        }

        return null;







    }


}
