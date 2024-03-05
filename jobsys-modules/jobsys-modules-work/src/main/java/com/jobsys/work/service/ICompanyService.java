package com.jobsys.work.service;

import java.util.List;
import com.jobsys.work.domain.Company;

/**
 * 公司Service接口
 *
 * @author PengFei Deng
 * @date 2022-03-20
 */
public interface ICompanyService
{
    /**
     * 查询公司
     *
     * @param comId 公司主键
     * @return 公司
     */
    Company selectCompanyByComId(Long comId);

    /**
     * 查询公司列表
     *
     * @param company 公司
     * @return 公司集合
     */
    List<Company> selectCompanyList(Company company);


    List<Company> listByType(String type);

    /**
     * 新增公司
     *
     * @param company 公司
     * @return 结果
     */
    int insertCompany(Company company);

    /**
     * 修改公司
     *
     * @param company 公司
     * @return 结果
     */
    int updateCompany(Company company);

    /**
     * 批量删除公司
     *
     * @param comIds 需要删除的公司主键集合
     * @return 结果
     */
    int deleteCompanyByComIds(Long[] comIds);

    /**
     * 删除公司信息
     *
     * @param comId 公司主键
     * @return 结果
     */
    int deleteCompanyByComId(Long comId);
}
