package com.jobsys.work.service;

import java.util.List;

import com.jobsys.work.domain.Business;

/**
 * businessService接口
 *
 * @author PengFei Deng
 * @date 2022-04-04
 */
public interface IBusinessService {
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
     * 批量删除business
     *
     * @param businessIds 需要删除的business主键集合
     * @return 结果
     */
    int deleteBusinessByBusinessIds(Long[] businessIds);

    /**
     * 删除business信息
     *
     * @param businessId business主键
     * @return 结果
     */
    int deleteBusinessByBusinessId(Long businessId);
}
