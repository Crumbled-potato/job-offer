package com.jobsys.work.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.jobsys.common.core.utils.poi.ExcelUtil;
import com.jobsys.common.core.web.controller.BaseController;
import com.jobsys.common.core.web.domain.AjaxResult;
import com.jobsys.common.core.web.page.TableDataInfo;
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

import com.jobsys.work.domain.UserMsg;
import com.jobsys.work.service.IUserMsgService;


/**
 * userMsgController
 *
 * @author PengFei Deng
 * @date 2022-05-02
 */
@RestController
@RequestMapping("/msg")
public class UserMsgController extends BaseController {
    @Autowired
    private IUserMsgService userMsgService;


    @GetMapping("/getUnReadSum")
    public AjaxResult getUnReadSum() {
        Long userId = SecurityUtils.getUserId();
        return userMsgService.getUnReadSum(userId);
    }


    /**
     * 查询userMsg列表
     */

    @GetMapping("/list")
    public TableDataInfo list(UserMsg userMsg) {
        startPage();
        Long userId = SecurityUtils.getUserId();
        userMsg.setUserId(userId);
        List<UserMsg> list = userMsgService.selectUserMsgList(userMsg);
        return getDataTable(list);
    }

    /**
     * 导出userMsg列表
     */


    @PostMapping("/export")
    public void export(HttpServletResponse response, UserMsg userMsg) {
        List<UserMsg> list = userMsgService.selectUserMsgList(userMsg);
        ExcelUtil<UserMsg> util = new ExcelUtil<UserMsg>(UserMsg.class);
        util.exportExcel(response, list, "userMsg数据");
    }

    /**
     * 获取userMsg详细信息
     */

    @GetMapping(value = "/{msgId}")
    public AjaxResult getInfo(@PathVariable("msgId") Integer msgId) {
        return AjaxResult.success(userMsgService.selectUserMsgByMsgId(msgId));
    }

    /**
     * 新增userMsg
     */


    @PostMapping
    public AjaxResult add(@RequestBody UserMsg userMsg) {
        return toAjax(userMsgService.insertUserMsg(userMsg));
    }

    /**
     * 修改userMsg
     */


    @PutMapping
    public AjaxResult edit(@RequestBody UserMsg userMsg) {
        return toAjax(userMsgService.updateUserMsg(userMsg));
    }

    /**
     * 删除userMsg
     */


    @DeleteMapping("/{msgIds}")
    public AjaxResult remove(@PathVariable Integer[] msgIds) {
        return toAjax(userMsgService.deleteUserMsgByMsgIds(msgIds));
    }
}
