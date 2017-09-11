package com.liuming.mej2ee.web.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 用于加载spring配置文件，创建测试环境
 *
 * Created with IntelliJ IDEA.
 * User: liuming
 * Date: 2017/9/11
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 * Description:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:application-context.xml")
public class BaseSpringTest {
}