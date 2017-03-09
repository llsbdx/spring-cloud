package hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.*;
import java.net.URI;

/**
 * FSDataOutPutStream类充许我们指定是否强制覆盖已有文件、文件副本数量、写入文件时的缓冲大小、文件块大小以及文件许可。
 */
public class FileCopyWithProgress
{
    public static void main(String[] args) throws IOException
    {
        String localpath = args[0].trim();
        String hdfspath = args[1].trim();
        System.out.println(localpath+"==="+hdfspath);
        InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(localpath)));
        try
        {
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(hdfspath), conf);
            OutputStream out = fs.create(new Path(hdfspath), new Progressable()
            {
                @Override
                public void progress()
                {
                    System.out.print(".");
                }
            });
            IOUtils.copyBytes(inputStream, out, 4096, false);
        } finally
        {
            IOUtils.closeStream(inputStream);
        }

    }
}
