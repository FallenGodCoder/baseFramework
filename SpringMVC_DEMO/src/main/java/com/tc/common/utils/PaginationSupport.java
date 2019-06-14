package com.tc.common.utils;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页支持.
 */
public class PaginationSupport<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final PaginationSupport EMPTY_PAGINATION_SUPPORT =
            new EmptyPaginationSupport();


    public final static int PAGESIZE = 30;

    private int indexCountOnShow = 7;//����������

    private int pageSize = PAGESIZE;

    private List<T> items;

    private int totalCount;

    private int[] indexes = new int[0];

    private int nextIndex;
    private int previousIndex;
    private int pageCount;
    private int currentIndex;
    private int endIndex;
//    private int startIndexOnShow;
//    private int endIndexOnShow;

    /**
     * @return Returns the endIndex.
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * @param endIndex The endIndex to set.
     */
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    /**
     * @return Returns the currentIndex.
     */
    public int getCurrentIndex() {
        return currentIndex;
    }


    /**
     * @param currentIndex The currentIndex to set.
     */
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public PaginationSupport(List<T> items, PageHelper helper) {
        setItems(items);
        if(helper!=null) {
            setPageSize(helper.getSize());
            setTotalCount(helper.getTotal());
            setCurrentIndex(helper.getPage());
        }else{
            setPageSize(0);
            setTotalCount(0);
            setCurrentIndex(0);
        }
        init();
    }

    public PaginationSupport(List<T> items, int totalCount) {
        this(items, totalCount, PAGESIZE, 0);
    }


    public PaginationSupport(List<T> items, int totalCount, int pageSize, int page) {
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.items = items;
        setCurrentIndex(page);
        init();
    }

    public PaginationSupport(List<T> items, int totalCount, int pageSize, int page, int indexCountOnShow) {
        setPageSize(pageSize);
        setTotalCount(totalCount);
        setItems(items);
        setCurrentIndex(page);
        this.setIndexCountOnShow(indexCountOnShow);

        init();
    }

    /**
     * @return Returns the pageCount.
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * @param pageCount The pageCount to set.
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    private void init() {

        if (totalCount > 0) {
            pageCount = totalCount / pageSize;
            if (totalCount % pageSize > 0)
                pageCount++;
        } else {
            pageCount = 0;
        }

        endIndex = pageCount;
        nextIndex = currentIndex >= endIndex ? endIndex : currentIndex + 1;
        previousIndex = currentIndex > 1 ? currentIndex - 1 : 1;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int[] getIndexes() {
        return indexes;
    }

    public void setIndexes(int[] indexes) {
        this.indexes = indexes;
    }

    /**
     * @return Returns the nextIndex.
     */
    public int getNextIndex() {
        return nextIndex;
    }

    /**
     * @param nextIndex The nextIndex to set.
     */
    public void setNextIndex(int nextIndex) {
        this.nextIndex = nextIndex;
    }

    /**
     * @return Returns the previousIndex.
     */
    public int getPreviousIndex() {
        return previousIndex;
    }

    /**
     * @param previousIndex The previousIndex to set.
     */
    public void setPreviousIndex(int previousIndex) {
        this.previousIndex = previousIndex;
    }

    public int getIndexCountOnShow() {
        return indexCountOnShow;
    }

    public void setIndexCountOnShow(int indexCountOnShow) {
        this.indexCountOnShow = indexCountOnShow;
    }

    public int getFirst() {
        int result = (currentIndex - 1) * pageSize;
        return (result < 0 ? 0 : result);
    }

    public int getStartIndexOnShow() {
        if (currentIndex < (indexCountOnShow / 2 + 1)) return 1;
        else {
            if (currentIndex > endIndex - (indexCountOnShow / 2 - 1))
                return (endIndex - (indexCountOnShow - 1) > 0) ? endIndex - (indexCountOnShow - 1) : 1;
            else return currentIndex - indexCountOnShow / 2;
        }
    }

    public int getEndIndexOnShow() {
        if (currentIndex < (indexCountOnShow / 2 + 1)) {
            if (endIndex > indexCountOnShow) return indexCountOnShow;
            else return endIndex;
        } else {
            if (currentIndex >= endIndex - (indexCountOnShow / 2 - 1)) return endIndex;
            else return currentIndex + indexCountOnShow / 2;
        }
    }

//    public List getList() {
//        return items;
//    }

    public int getTotal() {
        return totalCount;
    }

//    public List getRows() {
//        return items;
//    }

    public int getPageNumber() {
        int num = pageSize==0?0:totalCount / pageSize;
        int f = pageSize==0?0:totalCount % pageSize;
        return f > 0 ? num + 1 : num;
    }

    private static final class EmptyPaginationSupport extends PaginationSupport {

        public EmptyPaginationSupport() {
            super(Collections.EMPTY_LIST, 0);
        }

        @Override
        public void setItems(List items) {
            throw new UnsupportedOperationException();
        }
    }

    public String toString() {
        return new StringBuilder(4000).append("{start:").append("num:").append(pageSize).append(",").append("totalCount:").append(totalCount).append(",").append("pageCount:").append(pageCount).append(",")
                .append("list:").append(items.toString()).append("}").toString();
    }
}