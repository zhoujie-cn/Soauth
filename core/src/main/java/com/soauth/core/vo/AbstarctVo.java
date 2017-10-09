package com.soauth.core.vo;

import com.soauth.core.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhoujie on 2017/9/25.
 */
public abstract class AbstarctVo implements Serializable {
    private static final long serialVersionUID = -4211754273453632694L;

    public  Date createTime= DateUtils.date();

    public Date createTime(){
        return createTime;
    }

    public  <T extends AbstarctVo> T createTime(Date date){
       createTime=date;
       return (T)this;
    }
}
