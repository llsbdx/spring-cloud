package io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.SequenceFile;

import java.net.URI;

/**
 * Created by mysteel on 2017/3/8.
 */
public class SeqToMapFile
{
    private static final String[] data = {
            "a,s,d,f,g,s,g,", "h,d,s,f,j,r,", "o,p,u,j,g,h,", "3,4,5,6,7,8,"
    };

    public static void main(String[] args) throws Exception
    {
        String uri = args[0];
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        Path path = new Path(uri);
        Path mapData = new Path(path, MapFile.DATA_FILE_NAME);

        SequenceFile.Reader reader = new SequenceFile.Reader(fs, mapData, conf);
        Class keyClass = reader.getKeyClass();
        Class valueClass = reader.getValueClass();

        reader.close();
        long entries = MapFile.fix(fs, path, keyClass, valueClass, false, conf);
        System.out.printf("create MapFile form squenceFile about %d entires!", entries);
    }
}
