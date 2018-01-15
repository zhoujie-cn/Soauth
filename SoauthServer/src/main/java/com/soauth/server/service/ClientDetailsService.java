package com.soauth.server.service;

import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.server.model.ClientInfoPage;

import java.util.List;

/**
 *
 * @author zhoujie
 * @date 2017/12/15
 */
public interface ClientDetailsService {


    /**
     *  保存新建的Client
     * @param clientDetails
     * @return
     */
 boolean saveClientDetails(ClientDetails clientDetails);

    /**
     * 查询所有的客户端.
     * @param clientInfoPage
     * @return
     */
 List<ClientInfoPage> getAllClientDetails(ClientInfoPage clientInfoPage);

}
