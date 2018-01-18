package com.soauth.server.testcontroller;

import com.soauth.core.utils.SpringBeanUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *  接口公共测试类
 */
@Test
@ContextHierarchy({
        @ContextConfiguration(name = "parent", locations = "classpath:content.xml"),
        @ContextConfiguration(name = "child", locations = "classpath:springmvc.xml")
})
@WebAppConfiguration("src/main/java/com/soauth/server")
public abstract class AbstractControllerTestNG extends AbstractTransactionalTestNGSpringContextTests {

    protected MockMvc mockMvc;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        this.mockMvc= MockMvcBuilders.standaloneSetup(getController()).build();
    }

    @BeforeTransaction
    public void before() {
        SpringBeanUtils.initialize(applicationContext);
    }

    /**
     *  子类返回一个接口测试实例
     * @return
     */
    protected abstract Object getController();

}
