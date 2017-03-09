package hdfs;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * 通过Hadoop文件系统读取文件
 */
public class FileSystemReader
{
    public static void main(String[] args) throws IOException
    {
        Configuration conf=new Configuration();
        if(args.length!=1)
        {
            System.err.println("参数错误！");
            System.exit(1);
        }
        String url=args[0];
        /**
         * FileSystem是Hadoop中文件系统的抽象父类，所以首先需要做的就是检索其具体使用的文件系统，比如HDFS。
         * 获取实例之后，可以通过调用它的OPEN方法来获得输入流。
         */
        FileSystem fs=FileSystem.get(URI.create(url),conf);
        InputStream inputStream=null;
        try
        {
            inputStream=fs.open(new Path(url));
            IOUtils.copyBytes(inputStream,System.out,4096,false);
        }finally
        {
            IOUtils.closeStream(inputStream);
        }
    }
}
