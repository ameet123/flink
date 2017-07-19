import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ameet Chaubal on 3/17/2017.
 * sample flink app
 */
public class FlinkSampleApp {
    private static final int PORT = 9013;
    private static final String SAMPLE_FILE = "C:\\Users\\ameet" +
            ".chaubal\\Documents\\source\\flink\\src\\main\\resources\\drinks.arff";

    public static void main(String[] args) throws Exception {
        // get execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // ****** DISABLE OUTPUT to syslog if you want
//        env.getConfig().disableSysoutLogging();


//        DataStreamSource<String> textStream =fromElements(env);
        DataStreamSource<String> textStream = env.readTextFile(SAMPLE_FILE);

        SingleOutputStreamOperator<Object> lowerCase = textStream.flatMap(new FlatMapFunction<String, Object>() {
            public void flatMap(String value, Collector<Object> out) throws Exception {
                for (String w : value.split("\\s")) {
                    out.collect(w.toUpperCase());
                }
            }
        });

        lowerCase.print().setParallelism(1);
        env.execute("**** File to UPPER *************");
    }

    private static DataStreamSource<String> fromElements(StreamExecutionEnvironment env) {
        List<String> elements = new ArrayList<>();
        elements.add("apple");
        return env.fromCollection(elements);
    }
}
