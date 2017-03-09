package hdfs;


import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;

import java.io.InputStream;
import java.net.URL;

/**
 * 通过URLStreamHandler读取Hadoop文件
 */
public class HDFSURLReader
{
    static {
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
    }

    public static void main(String[] args)
    {
        InputStream inputStream=null;
        try
        {
            inputStream=new URL(args[0]).openStream();
            IOUtils.copyBytes(inputStream,System.out,1023,false);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
