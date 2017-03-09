package wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Logger;

/**
 * 实现Mapper抽象类里的map方法，map方法主要就是把字符串解析成key-value的形式
 * (如Key=hello,Value=1),发给Reduce端来统计
 */
public class WordMapper extends Mapper<Object,Text,Text,IntWritable>
{
    private Logger log=Logger.getLogger(WordMapper.class.getName());

    private final static IntWritable one=new IntWritable(1);
    private Text word=new Text();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException
    {
        log.info(key.toString()+"===>"+value);
        StringTokenizer itr=new StringTokenizer(value.toString());
        while (itr.hasMoreTokens()){
            word.set(itr.nextToken());
            context.write(word,one);
        }
    }

}
