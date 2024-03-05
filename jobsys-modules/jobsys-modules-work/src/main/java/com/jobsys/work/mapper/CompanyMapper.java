package com.jobsys.work.mapper;

import java.util.List;

import com.jobsys.work.domain.Company;
import org.apache.ibatis.annotations.Param;

/**
 * 公司Mapper接口
 *
 * @author PengFei Deng
 * @date 2022-03-20
 */
public interface CompanyMapper {
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



    List<Company> listByType(@Param("type") String type);

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
     * 删除公司
     *
     * @param comId 公司主键
     * @return 结果
     */
    int deleteCompanyByComId(Long comId);

    /**
     * 批量删除公司
     *
     * @param comIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteCompanyByComIds(Long[] comIds);
}
