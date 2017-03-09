package hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;

/**
 * 获取文件或目录信息
 */
public class ListAllFile
{
    public static void main(String[] args) throws IOException
    {
        String url = args[0];
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(url), conf);

        Path[] paths = new Path[args.length];

        for (int i = 0; i < paths.length; i++)
        {
            paths[i] = new Path(args[i]);
        }
        FileStatus[] statuses = fs.listStatus(paths);
        Path[] listedPaths = FileUtil.stat2Paths(statuses);
        for (Path path : listedPaths)
        {
            System.out.println(path);
        }
    }
}
