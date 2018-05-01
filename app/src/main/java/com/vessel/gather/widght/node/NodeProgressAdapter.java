package com.vessel.gather.widght.node;

import java.util.List;

/**
 * @author vesselzhang
 * @date 2018/3/19
 */

public interface NodeProgressAdapter{

    /**
     * 返回集合大小
     *
     * @return
     */
    int getCount();

    /**
     * 适配数据的集合
     *
     * @return
     */
    List<LogisticsData> getData();

}