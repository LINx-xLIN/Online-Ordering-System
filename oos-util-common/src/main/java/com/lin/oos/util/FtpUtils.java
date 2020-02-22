package com.lin.oos.util;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;

public class FtpUtils {
    /**
     * 实现文件的上传，服务端，如果没有文件夹，需要先创建文件夹在上传到该文件夹。已知 basePath文件夹是必定存在的。filePath文件夹是不确定是否存在的。
     * @param hostname 主机名
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @param basePath 基础路径
     * @param filePath 文件路径
     * @param remoteFileName 远程文件路径
     * @param in 文件流
     * @return 返回是否成功
     */
    public static boolean upload(String hostname, int port, String username, String password, String basePath,
                                 String filePath, String remoteFileName, InputStream in) {
        //思路：只要有远程连接的功能，必定需要建立连接获得操作对象。
        //第一步：获得客户端连接（以及操作对象）
        FTPClient client = new FTPClient();
        client.setDataTimeout(60000);
        client.setConnectTimeout(60000);
        try {
            client.connect(hostname, port);
            //注意事项：注意连接ftp的条件：ftp的访问路径，用户名、密码

            boolean login = client.login(username, password);
            if (login) {
                //第二步：获得一个文件（从本地读取一个文件）


                //注意事项：发送之前需要设置一个发送参数
                //指定使用二进制码发送
                client.setFileType(FTPClient.BINARY_FILE_TYPE);
                //指定发送的模式为被动模式
                client.enterLocalPassiveMode();
                //指定发送的位置
                String path = basePath + filePath;
                boolean directoryFlag = client.changeWorkingDirectory(path);
                //思路：判断当前配置路径是否存在，如果存在直接删除文件。如果不存在，就一级一级的将文件夹创建好在上传。
                if (directoryFlag == false) {
                    String tempPath = basePath;
                    String[] split = filePath.split("/");
                    for (int i = 0; i < split.length; i++) {
                        if (split[i] != null && !"".equals(split[i])) {
                            tempPath = tempPath + "/" + split[i];
                            //判断当前路径是否存在
                            boolean tempPathFlag = client.changeWorkingDirectory(tempPath);
                            if (!tempPathFlag) {
                                //判断如果不存在，创建新的额路径
                                client.makeDirectory(tempPath);
                            }
                        }
                    }
                    //重新设置路径
                    client.changeWorkingDirectory(path);
                }
                //第三步：发送读取到的文件到ftp服务器对应的路径
                client.storeFile(remoteFileName, in);
                return true;
            }

            client.logout();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //第四步：关闭
            if(client.isConnected()) {
                try {
                    client.disconnect();
                } catch(IOException ioe) {
                    // do nothing
                }
            }
        }
        return false;
    }
}
