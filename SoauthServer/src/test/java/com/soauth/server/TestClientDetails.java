package com.soauth.server;

import com.soauth.core.utils.SpringBeanUtils;
import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.server.service.ClientDetailsService;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Set;

/**
 * client crud
 */

@Test
@ContextConfiguration(locations = "classpath:content.xml" , initializers = {TestApplicationContextInitializer.class})
public class TestClientDetails extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    ClientDetailsService clientDetailsService;

    public ClientDetails returnClientDetails(String GrantType){


        ClientDetails clientDetails = new ClientDetails();
        Set<String> grant=clientDetails.getGrantTypes();
        grant.add(GrantType);
        clientDetails.setClientId("testclient1");
        clientDetails.setClientSecret("test_secret1");
        clientDetails.setClientName("Webapp");
        clientDetails.setRedirectUri("redirectUri");
        clientDetails.setGrantTypes(grant);
    return  clientDetails;

    }

    @BeforeTransaction
    public void before() throws Exception {
        SpringBeanUtils.initialize(applicationContext);
    }

    @Test
   public  void saveClientTest(){
        ClientDetails clientDetails=returnClientDetails(GrantType.AUTHORIZATION_CODE.toString());
        System.out.println(Arrays.toString(new Set[]{clientDetails.getGrantTypes()}));
        clientDetails.setGrantType(Arrays.toString(new Set[]{clientDetails.getGrantTypes()}));

     //clientDetailsService.saveClientDetails(clientDetails);

   }
}
