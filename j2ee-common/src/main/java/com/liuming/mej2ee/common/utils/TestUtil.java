package com.liuming.mej2ee.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: liuming
 * Date: 2017/9/8
 * Time: 10:40
 * To change this template use File | Settings | File Templates.
 * Description:
 */

public class TestUtil {

    public static final Map<Long, String> MAP = new LinkedHashMap<>();

    static {
        MAP.put(3L, "level3");
        MAP.put(2L, "level2");
        MAP.put(1L, "level1");
        MAP.put(0L, "root");
    }

    public static void main(String[] args) {
//        String name = "";
//        concatString1(2L, name);
//        System.out.println(name);
//        System.out.println(concatString(3L, ""));

//        HashSet<Object> set = new HashSet<>();
//        set.add("java");
//        System.out.println(set.contains(null));

        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Thread.sleep(3000);
                System.out.println("线程执行完成");
                return true;
            }
        };

        test09(callable);
    }

    public static void test09(Callable<Boolean> callable) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        // 采用Future
        Future<Boolean> future = executorService.submit(callable);

        try {
            if (future.get(4, TimeUnit.SECONDS)) {
                System.out.println("任务执行成功");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("执行超时");
            future.cancel(true);
        }

        System.out.println("测试下进度");

    }

    public static String concatString(Long id, String name) {
        name = StringUtils.isBlank(name) ? MAP.get(id) : MAP.get(id) + "." + name;

        Long pId = id - 1;
        if (pId != 0) {
            name = concatString(id - 1, name);
        }

        return name;
    }

    public static void concatString1(Long id, String name) {
        name = StringUtils.isBlank(name) ? MAP.get(id) : MAP.get(id) + "." + name;

        Long pId = id - 1;
        if (pId != 0) {
            concatString1(id - 1, name);
        }
    }
}


