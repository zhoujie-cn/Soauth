package com.soauth.core.model;
import java.util.List;
import com.google.common.collect.Lists;
import net.sf.json.JSONObject;
import lombok.Data;


/**
 *  
 * @author zhoujie
 * 负责组装树形数据
 *
 */

@Data
public class SidebarTree {
      private String  url;
      private String urlname;
      private String id;
      private String parent;
      private String cssname;
      private String urltype;
      private JSONObject attributes = new JSONObject();
    /**
     * 存放子节点
     */
    private List<SidebarTree> children= Lists.newArrayList();
    




      
}
