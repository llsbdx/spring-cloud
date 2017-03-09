package io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.net.URI;

/**
 * MapFile的写入类似SequenceFile写入，首先新建一个Write实例，然后调用append方法将内容顺序写入。如果不按顺序写入将抛出异常。
 * MapFIle是排序后的SequenceFile,它由两部分组成，分别是data和index。index作为文件的索引，主要记录每个Record的Key值，以及
 * 该Record在文件中的偏位置。在MapFile被访问的时间，索引文件会被加载到内存，通过索引映射关系可迅速定位到指定Recod所在的
 * 位置。因些相对SequenceFile有检索效率更高效，缺点是会消耗一部分内存来存储index数据。
 */
public class MapFileWrite
{
    private static final String[] data = {
            "asasafdfgdfgg",
            "sdfffgsdfgsdf",
            "4sds65df54f5sf",
            "sdfs245dgsd54fsd",
            "hsd4fh65fg65",
            "fdgethouof"
    };

    public static void main(String[] args) throws IOException
    {
        String uri = args[0];
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        IntWritable key = new IntWritable();
        Text value = new Text();
        MapFile.Writer writer = null;
        try
        {
            writer = new MapFile.Writer(conf, fs, uri, key.getClass(), value.getClass());

            for (int i = 0; i < 1023; i++)
            {
                key.set(i);
                value.set(data[1024 % data.length]);
                writer.append(key, value);
            }
        } finally
        {
            IOUtils.closeStream(writer);
        }
    }
}
