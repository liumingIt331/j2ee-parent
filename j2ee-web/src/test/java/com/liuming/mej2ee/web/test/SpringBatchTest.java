package com.liuming.mej2ee.web.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.liuming.mej2ee.data.model.Product;
import com.liuming.mej2ee.web.base.BaseSpringTest;
import org.junit.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 用于测试spring批处理框架的job执行
 * </p>
 * Created with IntelliJ IDEA.
 * User: liuming
 * Date: 2017/9/11
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 * Description:
 */

public class SpringBatchTest extends BaseSpringTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job simpleFileImportJob;

    @Test
    public void testJob() {

        JobParameter parameter = new JobParameter("file/sample.csv");
        Map<String, JobParameter> parameterMap = new LinkedHashMap<>();
        parameterMap.put("inputFile", parameter);
        try {
            JobExecution result = jobLauncher.run(simpleFileImportJob, new JobParameters(parameterMap));
            System.out.println(result.toString());
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();

        map.put("id", 1001);
        map.put("name", null);
        map.put("description", "123");
        map.put("quantity", 2);
        map.put("test", null);

        String json = JSON.toJSONString(map);
        System.out.println(json);
//        Product product = JSON.parseObject(json, Product.class);
        Product product = JSON.parseObject(json, Product.class, Feature.InitStringFieldAsEmpty);
        System.out.println(JSON.toJSONString(product));
    }
}