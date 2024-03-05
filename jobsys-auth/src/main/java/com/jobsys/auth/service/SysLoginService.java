package com.jobsys.auth.service;

import com.jobsys.common.core.constant.Constants;
import com.jobsys.common.core.constant.SecurityConstants;
import com.jobsys.common.core.constant.UserConstants;
import com.jobsys.common.core.domain.R;
import com.jobsys.common.core.enums.UserStatus;
import com.jobsys.common.core.exception.ServiceException;
import com.jobsys.common.core.utils.ServletUtils;
import com.jobsys.common.core.utils.StringUtils;
import com.jobsys.common.core.utils.ip.IpUtils;
import com.jobsys.common.security.utils.SecurityUtils;
import com.jobsys.system.api.RemoteLogService;
import com.jobsys.system.api.RemoteUserService;
import com.jobsys.system.api.domain.SysLogininfor;
import com.jobsys.system.api.domain.SysUser;
import com.jobsys.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 登录校验方法
 *
 * @author PengFei Deng
 */
@Component
public class SysLoginService {
    @Autowired
    private RemoteLogService remoteLogService;

    @Autowired
    private RemoteUserService remoteUserService;

    /**
     * 用户登录方法
     *
     * @param username 用户名
     * @param password 密码
     * @return com.jobsys.system.api.model.LoginUser
     * @author PengFei Deng
     * @date 2022/2/28 15:27
     */
    public LoginUser login(String username, String password) {

        // 未填写用户名和密码
        if (StringUtils.isEmpty(username)) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "未填写用户名");
            throw new ServiceException("未填写用户名");
        }

        if (StringUtils.isEmpty(password)) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "未填写登录密码");
            throw new ServiceException("未填写登录密码");
        }

        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            String msg = "密码的长度在" + UserConstants.PASSWORD_MIN_LENGTH + "到" + UserConstants.PASSWORD_MAX_LENGTH + ",请按规定填写！";
            recordLogininfor(username, Constants.LOGIN_FAIL, msg);
            throw new ServiceException("用户密码不在指定范围");
        }

        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            String msg = "用户名的长度在" + UserConstants.USERNAME_MIN_LENGTH + "到" + UserConstants.USERNAME_MAX_LENGTH + ",请按规定填写！";
            recordLogininfor(username, Constants.LOGIN_FAIL, msg);
            throw new ServiceException("用户名不在指定范围");
        }

        // 查询用户信息
        R<LoginUser> userResult = remoteUserService.getUserInfo(username, SecurityConstants.INNER);

        //内部微服务之间通信失败
        if (R.FAIL == userResult.getCode()) {
            throw new ServiceException(userResult.getMsg());
        }

        //用户名不存在，提示注册
        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "用户名不存在，请注册！");
            throw new ServiceException("登录用户：" + username + " 不存在");
        }

        LoginUser userInfo = userResult.getData();
        SysUser user = userResult.getData().getSysUser();
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "对不起，您的账号已被删除");
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }

        //判断用户是否账户是否停用
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "您的用户已停用，请联系管理员！");
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }

        //账户正常使用，判断密码是否正确
        if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "用户密码错误");
            throw new ServiceException("用户不存在/密码错误");
        }

        recordLogininfor(username, Constants.LOGIN_SUCCESS, "登录成功");
        return userInfo;
    }

    public void logout(String loginName) {
        recordLogininfor(loginName, Constants.LOGOUT, "退出成功");
    }

    /**
     * 用户注册功能
     *
     * @param username 用户名
     * @param password 用户密码
     * @param roleId   角色分类Id
     * @author PengFei Deng
     * @date 2022/2/28 15:26
     */
    public void register(String username, String password, String roleId, Long comId) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            throw new ServiceException("用户名和密码都是必须填写项");
        }
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            throw new ServiceException("账户长度必须在8到20个字符之间");
        }
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            throw new ServiceException("密码长度必须在8到20个字符之间");
        }

        // 注册用户信息
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setNickName(username);
        Long[] roleIds = new Long[1];
        roleIds[0] = Long.valueOf(roleId);
        sysUser.setRoleIds(roleIds);
        sysUser.setPassword(SecurityUtils.encryptPassword(password));
        sysUser.setComId(comId);

        R<?> registerResult = remoteUserService.registerUserInfo(sysUser, SecurityConstants.INNER);

        if (R.FAIL == registerResult.getCode()) {
            throw new ServiceException(registerResult.getMsg());
        }
        recordLogininfor(username, Constants.REGISTER, "注册成功");
    }

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     */
    public void recordLogininfor(String username, String status, String message) {
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setUserName(username);
        logininfor.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));
        logininfor.setMsg(message);
        // 日志状态
        if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            logininfor.setStatus("0");
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            logininfor.setStatus("1");
        }
        remoteLogService.saveLogininfor(logininfor, SecurityConstants.INNER);
    }
}
