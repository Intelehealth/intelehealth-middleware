package com.emrmiddleware.dmo;


import org.apache.ibatis.annotations.Select;


public interface MindmapDMO {
    @Select("select name FROM mindmap_server.dic_publish WHERE id = (select max(id) FROM mindmap_server.dic_publish)")
    public String getConfigFileName();
}
