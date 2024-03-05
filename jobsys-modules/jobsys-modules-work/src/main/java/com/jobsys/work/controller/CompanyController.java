package com.jobsys.work.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.jobsys.common.core.utils.poi.ExcelUtil;
import com.jobsys.common.core.web.controller.BaseController;
import com.jobsys.common.core.web.domain.AjaxResult;
import com.jobsys.common.core.web.page.TableDataInfo;
import com.jobsys.common.log.annotation.Log;
import com.jobsys.common.log.enums.BusinessType;
import com.jobsys.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobsys.work.domain.Company;
import com.jobsys.work.service.ICompanyService;


/**
 * 公司Controller
 *
 * @author PengFei Deng
 * @date 2022-03-20
 */
@RestController
@RequestMapping("/company")
public class CompanyController extends BaseController {
    @Autowired
    private ICompanyService companyService;

    /**
     * 查询公司列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Company company) {
        startPage();
        List<Company> list = companyService.selectCompanyList(company);
        return getDataTable(list);
    }


    @GetMapping("/listByType")
    public AjaxResult listByType(String type) {
        List<Company> list = companyService.listByType(type);
        return AjaxResult.success(list);
    }


    /**
     * 导出公司列表
     */
    //  @RequiresPermissions("company:company:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Company company) {
        List<Company> list = companyService.selectCompanyList(company);
        ExcelUtil<Company> util = new ExcelUtil<Company>(Company.class);
        util.exportExcel(response, list, "公司数据");
    }

    /**
     * 获取公司详细信息
     */
    // @RequiresPermissions("company:company:query")
    @GetMapping(value = "/{comId}")
    public AjaxResult getInfo(@PathVariable("comId") Long comId) {
        return AjaxResult.success(companyService.selectCompanyByComId(comId));
    }

    /**
     * 新增公司
     */
    // @RequiresPermissions("company:company:add")
    @PostMapping
    public AjaxResult add(@RequestBody Company company) {
        return toAjax(companyService.insertCompany(company));
    }

    /**
     * 修改公司
     */
    //@RequiresPermissions("company:company:edit")
    @PutMapping
    public AjaxResult edit(@RequestBody Company company) {
        return toAjax(companyService.updateCompany(company));
    }

    /**
     * 删除公司
     */
    //@RequiresPermissions("company:company:remove")
    @DeleteMapping("/{comIds}")
    public AjaxResult remove(@PathVariable Long[] comIds) {
        return toAjax(companyService.deleteCompanyByComIds(comIds));
    }
}
