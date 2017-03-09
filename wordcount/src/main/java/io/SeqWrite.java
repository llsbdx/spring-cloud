package io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.net.URI;

/**
 * HDFS提供了两种类型的容器，分别是SequenceFile和MapFile
 * SequenceFile的存储类似于LogFile,不同的是LogFile记录的是纯文本数据，而SequenceFile记录是可以序列化的字符数组。
 * 它主要由一个Header后跟多条record组成。
 * 其中Header主要包含Key ClassName、Value ClassName存储压缩算法、用户自定义数据等信息。此外还包函一些同频标识，
 * 用于快速定位到记录的边界。
 * SequenceFile支持两格式的数据压缩，分别是Record compression和Block compression.
 * Record compression：是对每条记录的Value进行压缩
 * Block compression：是将一连串的Record组织到一起，统一压缩成一个Block.
 */
public class SeqWrite
{
    private static final String[] data = {
            "a,s,d,f,g,s,g,", "h,d,s,f,j,r,", "o,p,u,j,g,h,", "3,4,5,6,7,8,"
    };

    public static void main(String[] args) throws IOException
    {
        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(URI.create(args[0]), conf);

        Path path = new Path("test.seq");
        SequenceFile.Writer writer = null;
        IntWritable key = new IntWritable();
        Text value = new Text();
        try
        {
            writer = SequenceFile.createWriter(fs, conf, path, key.getClass(), value.getClass());

            for (int i = 0; i < 10000; i++)
            {
                key.set(i);
                value.set(data[i % data.length]);
                writer.append(key, value);
            }
        } finally
        {
            IOUtils.closeStream(writer);
        }

    }
}
