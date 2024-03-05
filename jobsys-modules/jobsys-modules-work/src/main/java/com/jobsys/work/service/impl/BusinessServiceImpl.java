package com.jobsys.work.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jobsys.work.mapper.BusinessMapper;
import com.jobsys.work.domain.Business;
import com.jobsys.work.service.IBusinessService;

/**
 * businessService业务层处理
 *
 * @author PengFei Deng
 * @date 2022-04-04
 */
@Service
public class BusinessServiceImpl implements IBusinessService {
    @Autowired
    private BusinessMapper businessMapper;

    /**
     * 查询business
     *
     * @param businessId business主键
     * @return business
     */
    @Override
    public Business selectBusinessByBusinessId(Long businessId) {
        return businessMapper.selectBusinessByBusinessId(businessId);
    }

    /**
     * 查询business列表
     *
     * @param business business
     * @return business
     */
    @Override
    public List<Business> selectBusinessList(Business business) {
        return businessMapper.selectBusinessList(business);
    }

    /**
     * 新增business
     *
     * @param business business
     * @return 结果
     */
    @Override
    public int insertBusiness(Business business) {
        return businessMapper.insertBusiness(business);
    }

    /**
     * 修改business
     *
     * @param business business
     * @return 结果
     */
    @Override
    public int updateBusiness(Business business) {
        return businessMapper.updateBusiness(business);
    }

    /**
     * 批量删除business
     *
     * @param businessIds 需要删除的business主键
     * @return 结果
     */
    @Override
    public int deleteBusinessByBusinessIds(Long[] businessIds) {
        return businessMapper.deleteBusinessByBusinessIds(businessIds);
    }

    /**
     * 删除business信息
     *
     * @param businessId business主键
     * @return 结果
     */
    @Override
    public int deleteBusinessByBusinessId(Long businessId) {
        return businessMapper.deleteBusinessByBusinessId(businessId);
    }
}
