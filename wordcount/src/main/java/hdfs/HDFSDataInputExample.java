package hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.net.URI;

/**
 * FSDataInputStream类的主要作用就是DataInputStream包装一个输入流，并且使用BUfferedInputStream实现对输入的缓冲，
 * 该类继承了DataInputStream父类，实现了CLoseable、Seekable两个接口。
 */
public class HDFSDataInputExample
{
//    static
//    {
//        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
//    }

    public static void main(String[] args) throws IOException
    {
        HDFSDataInputExample example = new HDFSDataInputExample();
        String cmd = args[0].trim();
        String localPath = args[1].trim();
        String hdfsPath = args[2].trim();
        System.out.println(args[0]+"=="+args[1]+"=="+args[2]);

        if (cmd.equals("create"))
        {
            example.createFile(localPath, hdfsPath);
        } else if (cmd.equals("get"))
        {
            Boolean print = Boolean.valueOf(args[3]);
            example.getFile(localPath, hdfsPath, print);
        }
    }

    public void createFile(String localPath, String hdfsPath)
    {
        InputStream inputStream = null;
        try
        {
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
            FSDataOutputStream out = fs.create(new Path(hdfsPath));
            inputStream = new BufferedInputStream(new FileInputStream(new File(localPath)));
            IOUtils.copyBytes(inputStream, out, 4096, false);
            System.out.println("create file in hdfs:" + hdfsPath);
            out.sync();
            out.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            IOUtils.closeStream(inputStream);
        }
    }

    public void getFile(String localPath, String hdfsPath, Boolean print)
    {
        Configuration conf = new Configuration();

        FSDataInputStream in = null;
        OutputStream out = null;
        try
        {
            FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
            in = fs.open(new Path(hdfsPath));
            out = new BufferedOutputStream(new FileOutputStream(new File(localPath)));
            IOUtils.copyBytes(in, out, 4096, false);
            System.out.println("get file form hdfs to local:" + hdfsPath + "," + localPath);
            if (print)
            {
                in.seek(0);
                IOUtils.copyBytes(in, System.out, 4096, !print);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            IOUtils.closeStream(out);
        }
    }
}
