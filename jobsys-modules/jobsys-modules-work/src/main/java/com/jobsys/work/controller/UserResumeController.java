package com.jobsys.work.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.jobsys.common.core.utils.poi.ExcelUtil;
import com.jobsys.common.core.web.controller.BaseController;
import com.jobsys.common.core.web.domain.AjaxResult;
import com.jobsys.common.core.web.page.TableDataInfo;
import com.jobsys.common.log.enums.BusinessType;
import com.jobsys.common.security.annotation.RequiresPermissions;
import com.jobsys.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobsys.work.domain.UserResume;
import com.jobsys.work.service.IUserResumeService;


/**
 * resumeController
 *
 * @author PengFei Deng
 * @date 2022-04-25
 */
@RestController
@RequestMapping("/resume")
public class UserResumeController extends BaseController {
    @Autowired
    private IUserResumeService userResumeService;

    /**
     * 查询resume列表
     */

    @GetMapping("/list")
    public TableDataInfo list(UserResume userResume) {
        startPage();
        List<UserResume> list = userResumeService.selectUserResumeList(userResume);
        return getDataTable(list);
    }


    /**
     * @return com.jobsys.common.core.web.domain.AjaxResult
     * @author PengFei Deng
     * @date 2022/4/25 18:36
     */
    @GetMapping("/listById")
    public AjaxResult getListByUserId() {
        UserResume userResume = new UserResume();
        userResume.setUserId(SecurityUtils.getUserId());
        List<UserResume> list = userResumeService.selectUserResumeList(userResume);
        return AjaxResult.success(list);
    }


    /**
     * 导出resume列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserResume userResume) {
        List<UserResume> list = userResumeService.selectUserResumeList(userResume);
        ExcelUtil<UserResume> util = new ExcelUtil<UserResume>(UserResume.class);
        util.exportExcel(response, list, "resume数据");
    }

    /**
     * 获取resume详细信息
     */
    @GetMapping(value = "/{resumeId}")
    public AjaxResult getInfo(@PathVariable("resumeId") Integer resumeId) {
        return AjaxResult.success(userResumeService.selectUserResumeByResumeId(resumeId));
    }

    /**
     * 新增resume
     */
    @PostMapping
    public AjaxResult add(@RequestBody UserResume userResume) {
        userResume.setUserId(SecurityUtils.getUserId());
        userResume.setCreateBy(SecurityUtils.getUsername());
        return toAjax(userResumeService.insertUserResume(userResume));
    }

    /**
     * 修改resume
     */
    @PutMapping
    public AjaxResult edit(@RequestBody UserResume userResume) {
        userResume.setUserId(SecurityUtils.getUserId());
        return toAjax(userResumeService.updateUserResume(userResume));
    }

    /**
     * 删除resume
     */
    @DeleteMapping("/{resumeIds}")
    public AjaxResult remove(@PathVariable Integer[] resumeIds) {
        return toAjax(userResumeService.deleteUserResumeByResumeIds(resumeIds));
    }
}
