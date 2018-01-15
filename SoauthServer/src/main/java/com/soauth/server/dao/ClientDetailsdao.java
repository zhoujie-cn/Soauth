package com.soauth.server.dao;

import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.server.model.ClientInfoPage;

import java.util.List;

/**
 *
 * @author zhoujie
 * @date 2017/12/15
 */
public interface ClientDetailsdao {

    /**
     *  保存新建的Client
     * @param clientDetails
     * @return
     */
    int  saveClientDetails(ClientDetails clientDetails);

    /**
     * 查询所有的客户端.
     * @param clientInfoPage
     * @return
     */
    List<ClientInfoPage> getAllClientDetails(ClientInfoPage clientInfoPage);

}
