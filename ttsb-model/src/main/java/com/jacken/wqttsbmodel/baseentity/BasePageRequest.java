package com.jacken.wqttsbmodel.baseentity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class BasePageRequest {


    /**
     * 当前页码
     */
    @TableField(exist = false)
    private Integer currentPage = 1;
    /**
     * 每页大小
     */
    @TableField(exist = false)
    private Integer pageSize = 20;

    /**
     * 当前坐标
     */
    @TableField(exist = false)
    private Integer start;

    public Integer getStart() {
        return (this.currentPage - 1) * this.pageSize;
    }

}
