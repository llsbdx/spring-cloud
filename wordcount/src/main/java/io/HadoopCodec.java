package io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 压缩算法比较
 */
public class HadoopCodec
{
    private static Map<String, String> map = new HashMap<>();

    static
    {
        map.put("gz", "org.apache.hadoop.io.compress.GzipCodec");
        map.put("bz2", "org.apache.hadoop.io.compress.BZip2Codec");
        map.put("deflate", "org.apache.hadoop.io.compress.DefaultCodec");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        String inputFile = args[0];
        String outFolder = args[1];
        System.out.println(inputFile + ">>" + outFolder);

        Configuration conf = new Configuration();
        System.out.println("被压缩的文件名为：" + inputFile);
        for (String key : map.keySet())
        {
            long time = copyAndZipFile(conf, inputFile, outFolder, map.get(key), key);
            System.out.println("使用" + key + "压缩，时间为：" + time + "毫秒！");
        }

    }

    private static long copyAndZipFile(Configuration conf, String inputFile, String outFolder, String codecClass, String suffixName) throws IOException, ClassNotFoundException
    {
        long startTime = System.currentTimeMillis();
        InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
        String baseName = inputFile.substring(0, inputFile.indexOf("."));
        String outputFile = outFolder + baseName + "." + suffixName;
        FileSystem fs = FileSystem.get(URI.create(outputFile), conf);
        //创建一个编码解码器
        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(Class.forName(codecClass), conf);
        OutputStream out = codec.createOutputStream(fs.create(new Path(outputFile)));

        try
        {
            IOUtils.copyBytes(in, out, 4096, false);
        } finally
        {
            IOUtils.closeStream(in);
            IOUtils.closeStream(out);
        }
        return System.currentTimeMillis() - startTime;
    }
}
