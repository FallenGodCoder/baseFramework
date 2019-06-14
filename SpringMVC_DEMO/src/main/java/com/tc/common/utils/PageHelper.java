/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tc.common.utils;

/**
 * 分页设置
 */
public class PageHelper {
    /**
     * 其实页数
     */
    private int page;
    /**
     * 页个数大小
     */
    private int size = DEFAULT_PAGE_SIZE;
    /*
     * 总数
     */
    private int total;
    final static int DEFAULT_PAGE_SIZE = 0;

    public final  static PageHelper getDefault(){
        return  new PageHelper(1,10);
    }

    public final  static PageHelper getDefault(PageHelper ...pageHelpers){
        if(pageHelpers==null){
            return getDefault();
        }
        for(PageHelper pageHelper:pageHelpers){
              if(pageHelper!=null){
                  return  pageHelper;
              }
        }
        return  getDefault();
    }
    public final  static PageHelper get(PageHelper ...pageHelpers){
        if(pageHelpers==null){
            return null;
        }
        for(PageHelper pageHelper:pageHelpers){
            if(pageHelper!=null){
                return  pageHelper;
            }
        }
        return null;
    }

    public PageHelper() {
    }

    public PageHelper(int size) {
        this.size = size;
    }

    public PageHelper(int page, int size) {
        this.page = page;
        this.size = size;
    }

    /**
     * 返回物理开始行
     *
     * @return
     */
    public int getStart() {
        if (page > 0) {
            return (page - 1) * size;
        } else {
            return 0;
        }

    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setRows(int rows){
        this.setSize(rows);
    }
    public int getRows(){
        return this.getSize();
    }
}
