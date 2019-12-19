
package com.jacken.wqttsbmodel.baseentity;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 分页对象
 *
 * @author pocketcoder
 */
public class PageModel<T> implements Serializable {

    private static final long serialVersionUID = 2386907222204760125L;

    private int currentPage = 1;

    private int pageSize = 20;

    private long totalNumber;

    private long totalPage;

    private Object totalPrice;

    private List<T> data;

    private Map<String, Object> extraData;


    public PageModel() {
    }

    public PageModel(List<T> data, int currentPage, int pageSize, int totalNumber) {
        this.data = data;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalNumber = totalNumber;
        this.totalPage = (this.totalNumber % this.pageSize == 0 ? this.totalNumber / this.pageSize
                : this.totalNumber / this.pageSize + 1);
    }

    public PageModel(int currentPage) {
        this.currentPage = currentPage;
    }

    public PageModel(int currentPage, int totalNumber, int totalPage, int pageSize) {
        this.currentPage = currentPage;
        this.totalNumber = totalNumber;
        this.totalPage = totalPage;
        this.pageSize = pageSize;
    }

    public PageModel(int currentPage, int totalNumber, int totalPage) {
        this.currentPage = currentPage;
        this.totalNumber = totalNumber;
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(long totalNumber) {
        this.totalNumber = totalNumber;
        this.totalPage = (this.totalNumber % this.pageSize == 0 ? this.totalNumber / this.pageSize
                : this.totalNumber / this.pageSize + 1);
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Object getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Object totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Map<String, Object> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, Object> extraData) {
        this.extraData = extraData;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


    public static PageModel buildByIPage(IPage page) {
        PageModel pageModel = new PageModel();
        if (page == null) {
            return pageModel;
        }
        pageModel.setData(page.getRecords());
        pageModel.setCurrentPage((int) page.getCurrent());
        pageModel.setPageSize((int) page.getSize());
        pageModel.setTotalNumber(page.getTotal());
        return pageModel;
    }

    public static PageModel buildByIPageWithExtraData(IPage page, Map<String, Object> extraData) {
        PageModel pageModel = buildByIPage(page);
        if (!CollectionUtils.isEmpty(extraData)) {
            pageModel.setExtraData(extraData);
        }
        return pageModel;
    }

    public static <T> PageModel pageTool(List<T> result, Integer currentPage, Integer pageSize) {
        //总条数
        Integer totalNum = 0;
        // 总页数
        Integer pageTotal = 0;
        //设置查询总条数
        totalNum = result.size();
        //根据查询结果和分页结束获取总页数
        if (totalNum <= pageSize) {
            pageTotal = 1;
        } else if (totalNum % pageSize != 0) {
            pageTotal = (totalNum / pageSize) + 1;
        } else {
            pageTotal = totalNum / pageSize;
        }
        PageModel pageModel = new PageModel();
        if (result != null && result.size() > 0) {
            if (currentPage * pageSize >= result.size()) {
                pageModel.setData(result.subList((currentPage - 1) * pageSize, result.size()));
            } else {
                pageModel.setData(result.subList((currentPage - 1) * pageSize, currentPage * pageSize));
            }
        }
        pageModel.setCurrentPage(currentPage);
        pageModel.setPageSize(pageSize);
        pageModel.setTotalNumber(totalNum);
        pageModel.setTotalPage(pageTotal);
        return pageModel;
    }
}
