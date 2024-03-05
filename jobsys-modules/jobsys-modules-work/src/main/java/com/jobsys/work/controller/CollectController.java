package com.jobsys.work.controller;

import com.jobsys.common.core.utils.poi.ExcelUtil;
import com.jobsys.common.core.web.controller.BaseController;
import com.jobsys.common.core.web.domain.AjaxResult;
import com.jobsys.common.core.web.page.TableDataInfo;
import com.jobsys.work.domain.Collect;
import com.jobsys.work.service.ICollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * collectController
 *
 * @author PengFei Deng
 * @date 2022-04-29
 */
@RestController
@RequestMapping("/collect")
public class CollectController extends BaseController {
    @Autowired
    private ICollectService collectService;

    /**
     * 查询collect列表
     */

    @GetMapping("/list")
    public TableDataInfo list(Collect collect) {
        startPage();
        List<Collect> list = collectService.selectCollectList(collect);
        return getDataTable(list);
    }


    @GetMapping("collect")
    public AjaxResult collect(Collect collect) {
        return collectService.collect(collect);
    }


    /**
     * 导出collect列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Collect collect) {
        List<Collect> list = collectService.selectCollectList(collect);
        ExcelUtil<Collect> util = new ExcelUtil<Collect>(Collect.class);
        util.exportExcel(response, list, "collect数据");
    }

    /**
     * 获取collect详细信息
     */

    @GetMapping(value = "/{colletId}")
    public AjaxResult getInfo(@PathVariable("colletId") Integer colletId) {
        return AjaxResult.success(collectService.selectCollectByColletId(colletId));
    }

    /**
     * 新增collect
     */

    @PostMapping
    public AjaxResult add(@RequestBody Collect collect) {
        return toAjax(collectService.insertCollect(collect));
    }

    /**
     * 修改collect
     */

    @PutMapping
    public AjaxResult edit(@RequestBody Collect collect) {
        return toAjax(collectService.updateCollect(collect));
    }

    /**
     * 删除collect
     */

    @DeleteMapping("/{colletIds}")
    public AjaxResult remove(@PathVariable Integer[] colletIds) {
        return toAjax(collectService.deleteCollectByColletIds(colletIds));
    }
}
