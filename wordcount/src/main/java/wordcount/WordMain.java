package wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

/**
 * WordMain说明：
 * 1、Configuration类：读取Hadoop配置文件，如core-site.xml、mapred-site.xml、hdfs-site.xml等。
 *    也可以使用set方法进行重新设置，如conf.set("fs.default.name","hdfs://xxxxx:9000")。注意,
 *    set方法设置的值会替代配置文件里面配置的值。
 * 2、Job类：表示一个MapRedice任务。Job的构造方法有两个参数，第一个参数为Confiruration，第二个参
 *    数为Job的名称(等同于任务的名称)。
 */
public class WordMain
{
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException
    {
        Configuration conf=new Configuration();
        String[] otherArgs=new GenericOptionsParser(conf,args).getRemainingArgs();

        if(otherArgs.length!=2)
        {
            System.err.println("Usage:wordcount <in> <out>");
            System.exit(2);
        }

        Job job=new Job(conf,"word cound");

        job.setJarByClass(WordMain.class);
        job.setMapperClass(WordMapper.class);
        job.setCombinerClass(WordReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job,new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job,new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true)?0:1);
    }
}
