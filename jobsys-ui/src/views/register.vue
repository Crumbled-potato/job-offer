<template>
  <div class="register">
    <el-card shadow="always">
      <template #header>
        <div class="card-header">
          <p style="color:darkgray">快速注册互联网招聘平台，找工作，找人才，更方便！</p>
        </div>
      </template>
      <div style="width: 700px;padding: 0">
        <el-row>
          <el-col :span="8">
            <div style="padding: 10px;margin: 38px 10px 20px 10px">
              <el-row :gutter="20">
                <el-col :span="6" style="color: #D1D4DB">
                  <i class="el-icon-s-comment" style="font-size: 40px"></i>
                </el-col>
                <el-col :span="16">
                  <span style="color: #8D92A1">找工作</span><br>
                  <span style="color: #C3C7D0;font-size: 12px">我要和老板直接谈</span>
                </el-col>
              </el-row>
            </div>

            <div style="padding: 10px;margin: 21px 10px">
              <el-row :gutter="20">
                <el-col :span="6" style="color: #D1D4DB">
                  <i class="el-icon-s-custom" style="font-size: 40px"></i>
                </el-col>
                <el-col :span="16">
                  <span style="color: #8D92A1">找人才</span><br>
                  <span style="color: #C3C7D0;font-size: 12px">各大行业大牛聚集</span>
                </el-col>
              </el-row>
            </div>

            <div style="padding: 10px;margin: 21px 10px">
              <el-row :gutter="20">
                <el-col :span="6" style="color: #D1D4DB">
                  <i class="el-icon-s-cooperation" style="font-size: 40px"></i>
                </el-col>
                <el-col :span="16">
                  <span style="color: #8D92A1">任性选</span><br>
                  <span style="color: #C3C7D0;font-size: 12px">各大行业职位任你选</span>
                </el-col>
              </el-row>
            </div>

            <div style="padding: 10px;margin: 21px 10px">
              <el-row :gutter="20">
                <el-col :span="6" style="color: #D1D4DB">
                  <i class="el-icon-s-claim" style="font-size: 40px"></i>
                </el-col>
                <el-col :span="16">
                  <span style="color: #8D92A1">人才匹配度高</span><br>
                  <span style="color: #C3C7D0;font-size: 12px">获取更精准的牛人</span>
                </el-col>
              </el-row>
            </div>

          </el-col>
          <el-col :span="16" style="border-left: solid 1px #e2e2e2;padding-left: 30px">
            <el-tabs>
              <!--  求职者注册            -->
              <el-tab-pane label="我要找工作">
                <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="register-form">
                  <el-form-item prop="username">
                    <el-input v-model="registerForm.username" type="text" auto-complete="off" placeholder="账号">
                      <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
                    </el-input>
                  </el-form-item>
                  <el-form-item prop="password">
                    <el-input
                      v-model="registerForm.password"
                      type="password"
                      auto-complete="off"
                      placeholder="密码"
                      @keyup.enter.native="handleRegister"
                    >
                      <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
                    </el-input>
                  </el-form-item>
                  <el-form-item prop="confirmPassword">
                    <el-input
                      v-model="registerForm.confirmPassword"
                      type="password"
                      auto-complete="off"
                      placeholder="确认密码"
                      @keyup.enter.native="handleRegister"
                    >
                      <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
                    </el-input>
                  </el-form-item>
                  <el-form-item prop="code" v-if="captchaOnOff">
                    <el-input
                      v-model="registerForm.code"
                      auto-complete="off"
                      placeholder="验证码"
                      style="width: 63%"
                      @keyup.enter.native="handleRegister"
                    >
                      <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>
                    </el-input>
                    <div class="register-code">
                      <img :src="codeUrl" @click="getCode" class="register-code-img"/>
                    </div>
                  </el-form-item>
                  <el-form-item style="width:100%;">
                    <el-button
                      :loading="loading"
                      size="medium"
                      type="primary"
                      style="width:100%;"
                      @click.native.prevent="handleRegister(true)"
                    >
                      <span v-if="!loading">注 册</span>
                      <span v-else>注 册 中...</span>
                    </el-button>
                    <div style="float: right;">
                      <router-link class="link-type" :to="'/login'">使用已有账户登录</router-link>
                    </div>
                  </el-form-item>
                </el-form>
              </el-tab-pane>


              <!--   招聘者注册  -->
              <el-tab-pane label="我要找人才">
                <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="register-form">

                  <el-form-item prop="username">
                    <el-input v-model="registerForm.username" type="text" auto-complete="off" placeholder="账号">
                      <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
                    </el-input>
                  </el-form-item>

                  <el-form-item prop="password">
                    <el-input
                      v-model="registerForm.password"
                      type="password"
                      auto-complete="off"
                      placeholder="密码"
                      @keyup.enter.native="handleRegister"
                    >
                      <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
                    </el-input>
                  </el-form-item>
                  <el-form-item prop="confirmPassword">
                    <el-input
                      v-model="registerForm.confirmPassword"
                      type="password"
                      auto-complete="off"
                      placeholder="确认密码"
                      @keyup.enter.native="handleRegister"
                    >
                      <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
                    </el-input>
                  </el-form-item>
                  <el-form-item prop="code" v-if="captchaOnOff">
                    <el-input
                      v-model="registerForm.code"
                      auto-complete="off"
                      placeholder="验证码"
                      style="width: 63%"
                      @keyup.enter.native="handleRegister"
                    >
                      <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>
                    </el-input>
                    <div class="register-code">
                      <img :src="codeUrl" @click="getCode" class="register-code-img"/>
                    </div>
                  </el-form-item>
                  <el-form-item style="width:100%;">
                    <el-button
                      :loading="loading"
                      size="medium"
                      type="primary"
                      style="width:100%;"
                      @click.native.prevent="handleRegister(false)"
                    >
                      <span v-if="!loading">注 册</span>
                      <span v-else>注 册 中...</span>
                    </el-button>
                    <div style="float: right;">
                      <router-link class="link-type" :to="'/login'">使用已有账户登录</router-link>
                    </div>
                  </el-form-item>
                </el-form>
              </el-tab-pane>
            </el-tabs>
          </el-col>
        </el-row>
      </div>
    </el-card>


    <!--  底部  -->
    <div class="el-register-footer">
      <span>Copyright © 2018-2022 All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
import {getCodeImg, register} from "@/api/login";

export default {
  name: "Register",
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.registerForm.password !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      codeUrl: "",
      registerForm: {
        username: "",
        password: "",
        confirmPassword: "",
        code: "",
        uuid: "",
        roleId: ""
      },
      registerRules: {
        username: [
          {required: true, trigger: "blur", message: "请输入您的账号"},
          {min: 2, max: 20, message: '用户账号长度必须介于 2 和 20 之间', trigger: 'blur'}
        ],
        password: [
          {required: true, trigger: "blur", message: "请输入您的密码"},
          {min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, trigger: "blur", message: "请再次输入您的密码"},
          {required: true, validator: equalToPassword, trigger: "blur"}
        ],
        code: [{required: true, trigger: "change", message: "请输入验证码"}]
      },
      loading: false,
      captchaOnOff: true
    };
  },
  created() {
    this.getCode();
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaOnOff = res.captchaOnOff === undefined ? true : res.captchaOnOff;
        if (this.captchaOnOff) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.registerForm.uuid = res.uuid;
        }
      });
    },
    handleRegister(isApply) {
      if (isApply) {
        this.registerForm.roleId = "100"
      } else {
        this.registerForm.roleId = "101";
      }

      this.$refs.registerForm.validate(valid => {
        if (valid) {
          this.loading = true;
          register(this.registerForm).then(res => {
            const username = this.registerForm.username;
            this.$alert("<font color='red'>恭喜你，您的账号 " + username + " 注册成功！</font>", '系统提示', {
              dangerouslyUseHTMLString: true,
              type: 'success'
            }).then(() => {
              this.$router.push("/login");
            }).catch(() => {
            });
          }).catch(() => {
            this.loading = false;
            if (this.captchaOnOff) {
              this.getCode();
            }
          })
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.register {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.register-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;

  .el-input {
    height: 38px;

    input {
      height: 38px;
    }
  }

  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}

.register-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.register-code {
  width: 33%;
  height: 38px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.el-register-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}

.register-code-img {
  height: 38px;
}
</style>
