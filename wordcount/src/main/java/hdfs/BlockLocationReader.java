package hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

/**
 * BlockLocation块在HDFS集群的位置
 */
public class BlockLocationReader
{
    public static void main(String[] args) throws IOException
    {
        Configuration conf=new Configuration();
        FileSystem fs=FileSystem.get(conf);
        Path fpath=new Path(args[0]);
        FileStatus ft=fs.getFileStatus(fpath);

        BlockLocation[] blockLocations=fs.getFileBlockLocations(ft,0,ft.getLen());

        for (BlockLocation blockLocation:blockLocations)
        {
            String[] hosts=blockLocation.getHosts();
            System.out.println("blockLocation:"+hosts[0]);
        }
    }
}
