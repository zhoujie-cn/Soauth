import com.soauth.api.client.model.ServerConfig;

import com.soauth.api.client.openid.connect.service.ConnectServerConfigService;
import com.soauth.api.client.openid.connect.service.impl.StaticConnectServerConfigServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 */
@Test
@ContextConfiguration(locations ={"classpath*: ../test/context.xml"} )
public class TestStaticServerConfig extends AbstractTestNGSpringContextTests {

   @Autowired( )
    public  ConnectServerConfigService applicationContext;
    @Test
    public void connect(){
        ServerConfig config= applicationContext.loadServerConfig("http://localhost:8000/SoauthServer");
        System.out.println(config.getJwksUri());
       //ApplicationContext app= new ClassPathXmlApplicationContext("classpath*:../test/context.xml");
     //  applicationContext.getBean("staticConnectServer");
     // StaticConnectServerConfigServiceimpl  staticConnect= (StaticConnectServerConfigServiceimpl) applicationContext.getBean(StaticConnectServerConfigServiceimpl.class,"staticConnectServer");

      //  ServerConfig config = staticConnect.loadServerConfig("http://localhost:8000/SoauthServer");
        //System.out.println(config.getJwksUri());
    }
}
