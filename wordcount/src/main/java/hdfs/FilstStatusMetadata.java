package hdfs;

import org.apache.commons.net.ntp.TimeStamp;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;

/**
 * Hadoop中的FileStatus类可以用来查看HDFS中文件或目录的元信息，任意文件或者目录都可以得到对应的FileStatus。
 * 它封装了文件系统中文件和目录的元数据信息，包括文件的长度、块大小、备份数、修改时间、所有者以及权限等信息。
 */
public class FilstStatusMetadata
{
    public static void main(String[] args) throws IOException
    {
        Configuration conf = new Configuration();
        //conf.set("hadoop.job.ugi","");
        String uri = args[0];
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        //1.获取文件元信息
        FileStatus ft = fs.getFileStatus(new Path(uri));
        if (!ft.isDir())
        {
            System.out.println("this is file !");
            System.out.println("file path:" + ft.getPath());
            System.out.println("file length:" + ft.getLen());
            System.out.println("file modify:" + new TimeStamp(ft.getModificationTime()).toDateString());
            System.out.println("file replication:" + ft.getReplication());
            System.out.println("file block:" + ft.getBlockSize());
            System.out.println("file group:" + ft.getGroup());
            System.out.println("file permission:" + ft.getPermission());
            System.out.println();
        }

        //2.获取目录的元信息
        if (ft.isDir())
        {
            System.out.println("this is dir !");
            System.out.println("dir path:" + ft.getPath());
            System.out.println("dir length:" + ft.getLen());
            System.out.println("dir modify:" + new TimeStamp(ft.getModificationTime()).toDateString());
            System.out.println("dir access:" + new TimeStamp(ft.getAccessTime()).toDateString());
            System.out.println("dir replication:" + ft.getReplication());
            System.out.println("dir block:" + ft.getBlockSize());
            System.out.println("dir group:" + ft.getGroup());
            System.out.println("dir permission:" + ft.getPermission());
            System.out.println("dir content file or sub-dir");
            for (FileStatus sft : fs.listStatus(new Path(uri)))
            {
                System.out.println(sft.getPath());
            }
        }
    }
}
