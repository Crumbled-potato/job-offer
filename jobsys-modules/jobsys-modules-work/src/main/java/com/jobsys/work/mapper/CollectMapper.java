package com.jobsys.work.mapper;

import java.util.List;

import com.jobsys.work.domain.Collect;
import org.apache.ibatis.annotations.Param;

/**
 * collectMapper接口
 *
 * @author PengFei Deng
 * @date 2022-04-29
 */
public interface CollectMapper {
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
     * 删除collect
     *
     * @param colletId collect主键
     * @return 结果
     */
    int deleteCollectByColletId(Integer colletId);

    /**
     * 批量删除collect
     *
     * @param colletIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteCollectByColletIds(Integer[] colletIds);


    int collect(@Param("userName") String userName, @Param("colletId") int colletId);
}
