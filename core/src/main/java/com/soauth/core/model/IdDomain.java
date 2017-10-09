package com.soauth.core.model;

import com.soauth.core.utils.GidGeneratorUtils;
import com.soauth.core.vo.AbstarctVo;

import java.io.Serializable;

/**
 * Created by zhoujie on 2017/9/25.
 *
 */
public abstract class IdDomain extends AbstarctVo{

    private static final long serialVersionUID = 3081728884257451223L;


    protected int id;


    /**
     * 数据逻辑删除的 标记
     */
    // logic delete label
    protected boolean archived = false;

    /**
     * 数据业务ID
     */
    // business id
    protected String guid = GidGeneratorUtils.generate();


    public IdDomain() {
    }

    public int id() {
        return id;
    }

    @SuppressWarnings("unchecked")
    public <T extends IdDomain> T id(int id) {
        this.id = id;
        return (T) this;
    }



    public boolean archived() {
        return archived;
    }

    @SuppressWarnings("unchecked")
    public <T extends IdDomain> T archived(boolean archived) {
        this.archived = archived;
        return (T) this;
    }

    public String guid() {
        return guid;
    }

    @SuppressWarnings("unchecked")
    public <T extends IdDomain> T guid(String guid) {
        this.guid = guid;
        return (T) this;
    }
}
