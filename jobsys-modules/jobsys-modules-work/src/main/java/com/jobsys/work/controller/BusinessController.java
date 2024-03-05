package com.jobsys.work.controller;

import java.util.List;
import java.io.IOException;
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
import com.jobsys.work.domain.Business;
import com.jobsys.work.service.IBusinessService;

/**
 * businessController
 *
 * @author PengFei Deng
 * @date 2022-04-04
 */
@RestController
@RequestMapping("/business")
public class BusinessController extends BaseController {
    @Autowired
    private IBusinessService businessService;

    /**
     * 查询business列表
     */
  //  @RequiresPermissions("business:business:list")
    @GetMapping("/list")
    public TableDataInfo list(Business business) {
        startPage();
        List<Business> list = businessService.selectBusinessList(business);
        return getDataTable(list);
    }

    /**
     * 导出business列表
     */
   // @RequiresPermissions("business:business:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Business business) {
        List<Business> list = businessService.selectBusinessList(business);
        ExcelUtil<Business> util = new ExcelUtil<Business>(Business.class);
        util.exportExcel(response, list, "business数据");
    }

    /**
     * 获取business详细信息
     */
   // @RequiresPermissions("business:business:query")
    @GetMapping(value = "/{businessId}")
    public AjaxResult getInfo(@PathVariable("businessId") Long businessId) {
        return AjaxResult.success(businessService.selectBusinessByBusinessId(businessId));
    }

    /**
     * 新增business
     */
  //  @RequiresPermissions("business:business:add")
    @PostMapping
    public AjaxResult add(@RequestBody Business business) {
        return toAjax(businessService.insertBusiness(business));
    }

    /**
     * 修改business
     */
   // @RequiresPermissions("business:business:edit")
    @PutMapping
    public AjaxResult edit(@RequestBody Business business) {
        return toAjax(businessService.updateBusiness(business));
    }

    /**
     * 删除business
     */
    @DeleteMapping("/{businessIds}")
    public AjaxResult remove(@PathVariable Long[] businessIds) {
        return toAjax(businessService.deleteBusinessByBusinessIds(businessIds));
    }
}
