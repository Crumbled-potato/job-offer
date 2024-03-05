package com.jobsys.auth.form;

import lombok.Data;

/**
 * 用户登录对象
 *
 * @author PengFei Deng
 */
@Data
public class LoginBody {

    //用户名
    private String username;

    //用户密码
    private String password;

    //用户角色Id
    private String roleId;

    private Long comId;

}
