package wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * reduce方法的主要功能就是获取map方法的Key-Value结果，相同的Key发送到同一个reduce里面处理，然后迭代
 * Key把Value相加，结果写到HDFS系统里。
 */
public class WordReducer extends Reducer<Text, IntWritable, Text, IntWritable>
{
    private Logger log=Logger.getLogger(WordReducer.class.getName());
    private IntWritable result = new IntWritable();


    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
    {
        int sum = 0;

        for (IntWritable val:values)
        {
            sum += val.get();
        }

        result.set(sum);
        log.info(key.toString()+"==>"+result);
        context.write(key, result);
    }

}
