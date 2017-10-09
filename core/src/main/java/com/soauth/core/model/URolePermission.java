package com.soauth.core.model;

import lombok.Data;

/**
 *  zhoujie 
 * roles -> permission
 */
@Data
public class URolePermission extends  IdDomain{

    private static final long serialVersionUID = -7118528815493054382L;
    private Long rid;
    private Long pid;


}