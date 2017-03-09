package io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RawLocalFileSystem;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * HDFS提供了两种数据校验方式：数据块检测程序以及校验和，以些来保证数据的完整情，且这两种校验方法在节点上同时工作。
 */
public class WriteToLocal
{
    /**
     * LocalFileSystem是客户端校验类，使用其写文件时，文件系统会自动创建一个.crc文件。在读取文件时会根据此文件进行校验。
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, URISyntaxException
    {
        Configuration conf=new Configuration();

        LocalFileSystem fs=new LocalFileSystem(new RawLocalFileSystem());
        fs.initialize(new URI(args[0]),conf);
        OutputStream out=fs.create(new Path(args[0]));
        for (int i=0;i<5120;i++)
        {
            out.write(97);
        }
        out.close();
        Path file=new Path(args[0]);
        System.out.println(file.getName());
        fs.close();

    }
}
