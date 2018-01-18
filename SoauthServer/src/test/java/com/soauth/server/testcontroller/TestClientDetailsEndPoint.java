package com.soauth.server.testcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soauth.core.utils.SpringBeanUtils;
import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.server.service.ClientDetailsService;
import com.soauth.server.web.authncontroller.ClientsEndPoint;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Set;

/**
 * client crud
 */


public class TestClientDetailsEndPoint extends AbstractControllerTestNG {


    @Autowired
    ClientsEndPoint clientsEndPoint;


    public String returnClientDetails(String GrantType) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        ClientDetails clientDetails = new ClientDetails();
        Set<String> grant=clientDetails.getGrantTypes();
        grant.add(GrantType);
        clientDetails.setClientId("testclient1");
        clientDetails.setClientSecret("test_secret1");
        clientDetails.setClientName("Webapp");
        clientDetails.setRedirectUri("redirectUri");
        clientDetails.setClientUri("testClientUri");
        clientDetails.setGrantTypes(grant);
        clientDetails.setLoginUri("loginuri");
        clientDetails.setScopeString("openid, refresh_toen, id_token, access_token");
        clientDetails.setAccessTokenValidity(1);
        clientDetails.setRefreshTokenValidity(1);
        clientDetails.setDescription("desc");
        clientDetails.setTokenEndpointmethod(ClientDetails.Authentication.SECRET_BASIC);
        String json =mapper.writeValueAsString(clientDetails);
    return  json;

    }


    @Test
    public  void test() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .post("/clients")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(returnClientDetails(GrantType.AUTHORIZATION_CODE.toString())));
    }

    @Override
    protected Object getController() {
        return clientsEndPoint;
    }
}
