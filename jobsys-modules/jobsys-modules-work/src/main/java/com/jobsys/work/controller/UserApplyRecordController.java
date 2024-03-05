package com.jobsys.work.controller;


import com.jobsys.common.core.utils.DateUtils;
import com.jobsys.common.core.utils.poi.ExcelUtil;
import com.jobsys.common.core.web.controller.BaseController;
import com.jobsys.common.core.web.domain.AjaxResult;
import com.jobsys.common.core.web.page.TableDataInfo;
import com.jobsys.work.domain.UserApplyRecord;
import com.jobsys.work.service.IUserApplyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * recordController
 *
 * @author PengFei Deng
 * @date 2022-04-26
 */
@RestController
@RequestMapping("/record")
public class UserApplyRecordController extends BaseController {
    @Autowired
    private IUserApplyRecordService userApplyRecordService;

    /**
     * 查询record列表
     */

    @GetMapping("/list")
    public TableDataInfo list(UserApplyRecord userApplyRecord) {
        startPage();
        List<UserApplyRecord> list = userApplyRecordService.selectUserApplyRecordList(userApplyRecord);
        return getDataTable(list);
    }

    /**
     * 导出record列表
     */

    @PostMapping("/export")
    public void export(HttpServletResponse response, UserApplyRecord userApplyRecord) {
        List<UserApplyRecord> list = userApplyRecordService.selectUserApplyRecordList(userApplyRecord);
        ExcelUtil<UserApplyRecord> util = new ExcelUtil<UserApplyRecord>(UserApplyRecord.class);
        util.exportExcel(response, list, "record数据");
    }

    /**
     * 获取record详细信息
     */

    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Integer recordId) {
        return AjaxResult.success(userApplyRecordService.selectUserApplyRecordByRecordId(recordId));
    }

    /**
     * 新增record
     */

    @PostMapping
    public AjaxResult add(@RequestBody UserApplyRecord userApplyRecord) {
        userApplyRecord.setCreateTime(DateUtils.getNowDate());
        return toAjax(userApplyRecordService.insertUserApplyRecord(userApplyRecord));
    }


    @PostMapping("/applyAdd")
    public AjaxResult applyAdd(@RequestBody UserApplyRecord userApplyRecord) {
        UserApplyRecord select = new UserApplyRecord();
        select.setApplyId(userApplyRecord.getApplyId());
        select.setJobId(userApplyRecord.getJobId());
        List<UserApplyRecord> list = userApplyRecordService.selectUserApplyRecordList(select);
        if (list.size() > 0) {
            return AjaxResult.error("操作失败，您已经投递过了此职位！");
        }
        userApplyRecord.setCreateTime(DateUtils.getNowDate());
        return toAjax(userApplyRecordService.insertUserApplyRecord(userApplyRecord));
    }


    @PostMapping("/applyAddByJob")
    public AjaxResult applyAddByJob(@RequestBody UserApplyRecord userApplyRecord) {
        UserApplyRecord select = new UserApplyRecord();
        select.setResumeId(userApplyRecord.getResumeId());
        select.setJobId(userApplyRecord.getJobId());
        List<UserApplyRecord> list = userApplyRecordService.selectUserApplyRecordList(select);
        if (list.size() > 0) {
            return AjaxResult.success("操作失败，此份简历已经投递了此职位！");
        }
        userApplyRecord.setCreateTime(DateUtils.getNowDate());
        return toAjax(userApplyRecordService.insertUserApplyRecord(userApplyRecord));
    }


    /**
     * 修改record
     */

    @PutMapping
    public AjaxResult edit(@RequestBody UserApplyRecord userApplyRecord) {
        return toAjax(userApplyRecordService.updateUserApplyRecord(userApplyRecord));
    }

    /**
     * 删除record
     */
    @DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Integer[] recordIds) {
        return toAjax(userApplyRecordService.deleteUserApplyRecordByRecordIds(recordIds));
    }
}
