package com.jobsys.work.service.impl;

import java.util.List;

import com.jobsys.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jobsys.work.mapper.CompanyMapper;
import com.jobsys.work.domain.Company;
import com.jobsys.work.service.ICompanyService;

/**
 * 公司Service业务层处理
 *
 * @author PengFei Deng
 * @date 2022-03-20
 */
@Service
public class CompanyServiceImpl implements ICompanyService {
    @Autowired
    private CompanyMapper companyMapper;

    /**
     * 查询公司
     *
     * @param comId 公司主键
     * @return 公司
     */
    @Override
    public Company selectCompanyByComId(Long comId) {
        return companyMapper.selectCompanyByComId(comId);
    }

    /**
     * 查询公司列表
     *
     * @param company 公司
     * @return 公司
     */
    @Override
    public List<Company> selectCompanyList(Company company) {
        return companyMapper.selectCompanyList(company);
    }

    @Override
    public List<Company> listByType(String type) {
        return companyMapper.listByType(type);
    }

    /**
     * 新增公司
     *
     * @param company 公司
     * @return 结果
     */
    @Override
    public int insertCompany(Company company) {
        company.setCreateTime(DateUtils.getNowDate());
        return companyMapper.insertCompany(company);
    }

    /**
     * 修改公司
     *
     * @param company 公司
     * @return 结果
     */
    @Override
    public int updateCompany(Company company) {
        company.setUpdateTime(DateUtils.getNowDate());
        System.out.println(company);
        return companyMapper.updateCompany(company);
    }

    /**
     * 批量删除公司
     *
     * @param comIds 需要删除的公司主键
     * @return 结果
     */
    @Override
    public int deleteCompanyByComIds(Long[] comIds) {
        return companyMapper.deleteCompanyByComIds(comIds);
    }

    /**
     * 删除公司信息
     *
     * @param comId 公司主键
     * @return 结果
     */
    @Override
    public int deleteCompanyByComId(Long comId) {
        return companyMapper.deleteCompanyByComId(comId);
    }
}
