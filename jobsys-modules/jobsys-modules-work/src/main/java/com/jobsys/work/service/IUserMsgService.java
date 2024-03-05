package com.jobsys.work.service;

import java.util.List;

import com.jobsys.common.core.web.domain.AjaxResult;
import com.jobsys.work.domain.UserMsg;

/**
 * userMsgService接口
 *
 * @author PengFei Deng
 * @date 2022-05-02
 */
public interface IUserMsgService {


    AjaxResult getUnReadSum(Long userId);

    /**
     * 查询userMsg
     *
     * @param msgId userMsg主键
     * @return userMsg
     */
    UserMsg selectUserMsgByMsgId(Integer msgId);

    /**
     * 查询userMsg列表
     *
     * @param userMsg userMsg
     * @return userMsg集合
     */
    List<UserMsg> selectUserMsgList(UserMsg userMsg);

    /**
     * 新增userMsg
     *
     * @param userMsg userMsg
     * @return 结果
     */
    int insertUserMsg(UserMsg userMsg);

    /**
     * 修改userMsg
     *
     * @param userMsg userMsg
     * @return 结果
     */
    int updateUserMsg(UserMsg userMsg);

    /**
     * 批量删除userMsg
     *
     * @param msgIds 需要删除的userMsg主键集合
     * @return 结果
     */
    int deleteUserMsgByMsgIds(Integer[] msgIds);

    /**
     * 删除userMsg信息
     *
     * @param msgId userMsg主键
     * @return 结果
     */
    int deleteUserMsgByMsgId(Integer msgId);
}
