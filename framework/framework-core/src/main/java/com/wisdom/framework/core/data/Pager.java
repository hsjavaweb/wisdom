package com.wisdom.framework.core.data;



import java.io.Serializable;
import java.util.*;


public class Pager<T> implements Serializable{
    private static final long serialVersionUID = 905659333844841228L;
    /**
     * 总记录数
     * Number of records in the data set, not accounting for filtering
     */
    private long totalSize;

    /**
     * 数据
     * The data to display on this page
     */
    private List<T> data ;

    /**
     * 分页查询结果对象构造方法
     */
    public Pager() {}

    /**
     * 分页查询结果对象构造方法
     *
     * @param totalSize 总记录数
     * @param data 分页参数
     */
    public Pager(long totalSize,List data) {
        this.totalSize = totalSize;
        this.data = data;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public List<T> getData() {
        return data;
    }
}
