package com.jobsys.work.service;

import java.util.List;

import com.jobsys.common.core.web.domain.AjaxResult;
import com.jobsys.work.domain.Collect;

/**
 * collectService接口
 *
 * @author PengFei Deng
 * @date 2022-04-29
 */
public interface ICollectService {

    AjaxResult collect(Collect collect);

    /**
     * 查询collect
     *
     * @param colletId collect主键
     * @return collect
     */
    Collect selectCollectByColletId(Integer colletId);

    /**
     * 查询collect列表
     *
     * @param collect collect
     * @return collect集合
     */
    List<Collect> selectCollectList(Collect collect);

    /**
     * 新增collect
     *
     * @param collect collect
     * @return 结果
     */
    int insertCollect(Collect collect);

    /**
     * 修改collect
     *
     * @param collect collect
     * @return 结果
     */
    int updateCollect(Collect collect);

    /**
     * 批量删除collect
     *
     * @param colletIds 需要删除的collect主键集合
     * @return 结果
     */
    int deleteCollectByColletIds(Integer[] colletIds);

    /**
     * 删除collect信息
     *
     * @param colletId collect主键
     * @return 结果
     */
    int deleteCollectByColletId(Integer colletId);
}
