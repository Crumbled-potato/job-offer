package com.jobsys.work.service.impl;

import java.util.List;

import com.jobsys.common.core.utils.DateUtils;
import com.jobsys.common.core.web.domain.AjaxResult;
import com.jobsys.common.security.utils.SecurityUtils;
import com.jobsys.work.mapper.ApplyJobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jobsys.work.mapper.CollectMapper;
import com.jobsys.work.domain.Collect;
import com.jobsys.work.service.ICollectService;

/**
 * collectService业务层处理
 *
 * @author PengFei Deng
 * @date 2022-04-29
 */
@Service
public class CollectServiceImpl implements ICollectService {
    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private ApplyJobMapper applyJobMapper;

    @Override
    public AjaxResult collect(Collect collect) {
        Collect select = new Collect();
        select.setJobId(collect.getJobId());
        select.setUserId(collect.getUserId());
        List<Collect> collects = collectMapper.selectCollectList(collect);
        if (collects.size() == 0) {
            collect.setCreateBy(SecurityUtils.getUsername());
            collect.setUserId(SecurityUtils.getUserId());
            collect.setUserName("1");
            collect.setCreateTime(DateUtils.getNowDate());
            int res = collectMapper.insertCollect(collect);
            applyJobMapper.changeLikeSum(1, collect.getJobId());
            return res == 1 ? AjaxResult.success("收藏成功！") : AjaxResult.success("收藏失败！");
        }
        Collect update = new Collect();
        update.setColletId(collects.get(0).getColletId());
        String msg;
        if (collects.get(0).getUserName() != null && "1".equalsIgnoreCase(collects.get(0).getUserName())) {
            update.setUserName("2");
            applyJobMapper.changeLikeSum(-1, collect.getJobId());
            msg = "取消收藏成功！";
        } else {
            update.setUserName("1");
            applyJobMapper.changeLikeSum(1, collect.getJobId());
            msg = "收藏成功";
        }
        int res = collectMapper.collect(update.getUserName(), update.getColletId());
        return res == 1 ? AjaxResult.success(msg) : AjaxResult.success("操作失败");
    }

    /**
     * 查询collect
     *
     * @param colletId collect主键
     * @return collect
     */
    @Override
    public Collect selectCollectByColletId(Integer colletId) {
        return collectMapper.selectCollectByColletId(colletId);
    }

    /**
     * 查询collect列表
     *
     * @param collect collect
     * @return collect
     */
    @Override
    public List<Collect> selectCollectList(Collect collect) {
        return collectMapper.selectCollectList(collect);
    }

    /**
     * 新增collect
     *
     * @param collect collect
     * @return 结果
     */
    @Override
    public int insertCollect(Collect collect) {
        collect.setCreateTime(DateUtils.getNowDate());
        return collectMapper.insertCollect(collect);
    }

    /**
     * 修改collect
     *
     * @param collect collect
     * @return 结果
     */
    @Override
    public int updateCollect(Collect collect) {
        collect.setUpdateTime(DateUtils.getNowDate());
        return collectMapper.updateCollect(collect);
    }

    /**
     * 批量删除collect
     *
     * @param colletIds 需要删除的collect主键
     * @return 结果
     */
    @Override
    public int deleteCollectByColletIds(Integer[] colletIds) {
        return collectMapper.deleteCollectByColletIds(colletIds);
    }

    /**
     * 删除collect信息
     *
     * @param colletId collect主键
     * @return 结果
     */
    @Override
    public int deleteCollectByColletId(Integer colletId) {
        return collectMapper.deleteCollectByColletId(colletId);
    }
}
