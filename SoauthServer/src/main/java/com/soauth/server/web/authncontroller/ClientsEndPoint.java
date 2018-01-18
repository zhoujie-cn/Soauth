package com.soauth.server.web.authncontroller;

import com.github.pagehelper.PageHelper;
import com.soauth.core.utils.DataTablePageUtil;
import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.server.model.ClientInfoPage;
import com.soauth.server.service.ClientDetailsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Set;
import java.util.UUID;

/**
 *客户端注册.
 * @author zhoujie
 * @date 2017/12/13
 */
@Controller
@RequestMapping("clients")
public class ClientsEndPoint {

    @Autowired
    ClientDetailsService  clientService;

    @RequestMapping(method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
    public String addClient(@RequestBody ClientDetails clientInfo){

        //如果没有指定客户端ID 则强制生成一个客户端ID
        if(!StringUtils.isNotEmpty(clientInfo.getClientId())){
           String generateid= UUID.randomUUID().toString();
           clientInfo.setClientId(generateid);
        }

        //如果没有指定客户端秘钥, 则生成一个秘钥
        if(!StringUtils.isNotEmpty(clientInfo.getClientSecret())){
            String clientSecret=new BigInteger(500,new SecureRandom()).toString(18);
            clientInfo.setClientSecret(clientSecret);
        }

        if(StringUtils.isNotEmpty(clientInfo.getTokenEndpointmethod().toString())){
            clientInfo.setTokenEndpointmethod(ClientDetails.Authentication.SECRET_BASIC);
        }

        clientService.saveClientDetails(clientInfo);

        return null;
    }

    @RequestMapping(method = RequestMethod.GET, produces =MediaType.APPLICATION_JSON_VALUE)
    public String getAllClient(HttpServletRequest request, @RequestBody() ClientInfoPage clientInfoPage){

        DataTablePageUtil<ClientInfoPage>   clientPage= new DataTablePageUtil<ClientInfoPage>(request,clientInfoPage.getStartIndex(),clientInfoPage.getPageSize());

        PageHelper.startPage(clientPage.getPage_num(),clientPage.getPage_size());

        clientService.getAllClientDetails(clientInfoPage);

        return null;
    }


}
