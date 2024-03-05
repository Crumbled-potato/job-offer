package com.jobsys.work.mapper;

import java.util.List;

import com.jobsys.work.domain.Business;

/**
 * businessMapper接口
 *
 * @author PengFei Deng
 * @date 2022-04-04
 */
public interface BusinessMapper {
    /**
     * 查询business
     *
     * @param businessId business主键
     * @return business
     */
    Business selectBusinessByBusinessId(Long businessId);

    /**
     * 查询business列表
     *
     * @param business business
     * @return business集合
     */
    List<Business> selectBusinessList(Business business);

    /**
     * 新增business
     *
     * @param business business
     * @return 结果
     */
    int insertBusiness(Business business);

    /**
     * 修改business
     *
     * @param business business
     * @return 结果
     */
    int updateBusiness(Business business);

    /**
     * 删除business
     *
     * @param businessId business主键
     * @return 结果
     */
    int deleteBusinessByBusinessId(Long businessId);

    /**
     * 批量删除business
     *
     * @param businessIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteBusinessByBusinessIds(Long[] businessIds);
}
