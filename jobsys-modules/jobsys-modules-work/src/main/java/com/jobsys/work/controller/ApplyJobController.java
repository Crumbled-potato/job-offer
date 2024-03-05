package com.jobsys.work.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.jobsys.common.core.constant.SecurityConstants;
import com.jobsys.common.core.domain.R;
import com.jobsys.common.core.utils.poi.ExcelUtil;
import com.jobsys.common.core.web.controller.BaseController;
import com.jobsys.common.core.web.domain.AjaxResult;
import com.jobsys.common.core.web.page.TableDataInfo;
import com.jobsys.common.log.annotation.Log;
import com.jobsys.common.log.enums.BusinessType;
import com.jobsys.common.security.annotation.RequiresPermissions;
import com.jobsys.common.security.utils.SecurityUtils;
import com.jobsys.system.api.RemoteUserService;
import com.jobsys.system.api.domain.SysUser;
import com.jobsys.system.api.model.LoginUser;
import com.jobsys.work.domain.Business;
import com.jobsys.work.service.IBusinessService;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobsys.work.domain.ApplyJob;
import com.jobsys.work.service.IApplyJobService;


/**
 * applyJobController
 *
 * @author PengFei Deng
 * @date 2022-03-21
 */
@RestController
@RequestMapping("/applyJob")
public class ApplyJobController extends BaseController {
    @Autowired
    private IApplyJobService applyJobService;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private IBusinessService businessService;


    /**
     * 查询applyJob列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ApplyJob applyJob) {
        startPage();
        List<ApplyJob> list = applyJobService.selectApplyJobList(applyJob);
        return getDataTable(list);
    }


    @GetMapping("/listOrderBy")
    public AjaxResult listOrderBy(ApplyJob applyJob) {
        List<ApplyJob> list = applyJobService.listOrderBy(applyJob);
        return AjaxResult.success(list);
    }


    /**
     * 导出applyJob列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, ApplyJob applyJob) {
        List<ApplyJob> list = applyJobService.selectApplyJobList(applyJob);
        ExcelUtil<ApplyJob> util = new ExcelUtil<ApplyJob>(ApplyJob.class);
        util.exportExcel(response, list, "applyJob数据");
    }

    /**
     * 获取applyJob详细信息
     */
    // @RequiresPermissions("company:applyJob:query")
    @GetMapping(value = "/{jobId}")
    public AjaxResult getInfo(@PathVariable("jobId") Long jobId) {
        return AjaxResult.success(applyJobService.selectApplyJobByJobId(jobId));
    }

    /**
     * 新增applyJob
     */
    @PostMapping
    public AjaxResult add(@RequestBody ApplyJob applyJob) {
        //校验薪水
        if (applyJob.getHeightSalary() < applyJob.getLowSalary()) {
            return AjaxResult.error("最高薪水:" + applyJob.getHeightSalary() + " 低于最低薪水：" + applyJob.getLowSalary());
        }
        //组装薪资薪水 工作城市 工作状态审核中 创建人相关信息
        applyJob.setJobSalary(applyJob.getLowSalary() + "-" + applyJob.getHeightSalary() + "K");
        //组装工作城市
        applyJob.setJobState(1);
        SysUser sysUser = remoteUserService.getUserInfo(SecurityUtils.getUsername(), SecurityConstants.INNER).getData().getSysUser();
        applyJob.setUserId(sysUser.getUserId());
        applyJob.setCreatBy(SecurityUtils.getUsername());
        if (applyJob.getComId() == null || "".equalsIgnoreCase(applyJob.getComId().toString())) {
            return AjaxResult.error("参数缺失");
        }
        Business business = new Business();
        business.setComId(applyJob.getComId());
        List<Business> businesses = businessService.selectBusinessList(business);
        if (businesses == null || businesses.size() == 0) {
            return AjaxResult.error("此公司无工商信息，无法发布职位");
        }
        if (!"3".equalsIgnoreCase(businesses.get(0).getCheckState())) {
            return AjaxResult.error("此公司无工商信息审核未通过，无法发布职位");
        }
        return toAjax(applyJobService.insertApplyJob(applyJob));
    }

    /**
     * 修改applyJob
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ApplyJob applyJob) {
        //校验薪水
        if (applyJob.getHeightSalary() < applyJob.getLowSalary()) {
            return AjaxResult.error("最高薪水:" + applyJob.getHeightSalary() + " 低于最低薪水：" + applyJob.getLowSalary());
        }
        //组装薪资薪水 工作城市 工作状态审核中 创建人相关信息
        applyJob.setJobSalary(applyJob.getLowSalary() + "-" + applyJob.getHeightSalary() + "K");
        return toAjax(applyJobService.updateApplyJob(applyJob));
    }

    /**
     * 删除applyJob
     */
    @DeleteMapping("/{jobIds}")
    public AjaxResult remove(@PathVariable Long[] jobIds) {
        return toAjax(applyJobService.deleteApplyJobByJobIds(jobIds));
    }


    @GetMapping(value = "changeState/{jobId}")
    public AjaxResult changeState(@PathVariable Long jobId, String state) {
        System.out.println("state:" + state);

        return toAjax(applyJobService.changeState(jobId, state));
    }


}
