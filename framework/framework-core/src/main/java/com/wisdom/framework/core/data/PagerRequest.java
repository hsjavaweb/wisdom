package com.wisdom.framework.core.data;


import com.wisdom.framework.core.annotation.ValidateField;
import com.wisdom.framework.core.enums.ValidateType;

import java.util.Map;
import java.util.Set;

/**
 * @author hyberbin on 2016/11/5.
 */
public class PagerRequest {
    @ValidateField(note = "开始页码",type = ValidateType.INTEGER)
    private Integer startPage;
    @ValidateField(note = "每页条数",type = ValidateType.INTEGER)
    private Integer pageLength;
    private Map data;

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getPageLength() {
        return pageLength;
    }

    public void setPageLength(Integer pageLength) {
        this.pageLength = pageLength;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        if(data!=null){
            Set set = data.keySet();
            for(Object key:set){
                Object o = data.get(key);
                if(o ==null|| o.toString().trim().isEmpty()){
                    data.remove(key);
                }
            }
        }
        this.data = data;
    }
}
