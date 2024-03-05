<template>
  <div class="login">
    <el-card shadow="always">
      <template #header>
        <div class="card-header">
          <p style="color:#8D92A1">快速登录互联网招聘平台，找工作，找人才，更方便！</p>
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
            <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
              <h3 class="title">互联网招聘求职平台</h3>
              <el-form-item prop="username">
                <el-input
                  v-model="loginForm.username"
                  type="text"
                  auto-complete="off"
                  placeholder="账号"
                >
                  <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
                </el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  v-model="loginForm.password"
                  type="password"
                  auto-complete="off"
                  placeholder="密码"
                  @keyup.enter.native="handleLogin"
                >
                  <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
                </el-input>
              </el-form-item>
              <el-form-item prop="code" v-if="captchaOnOff">
                <el-input
                  v-model="loginForm.code"
                  auto-complete="off"
                  placeholder="验证码"
                  style="width: 63%"
                  @keyup.enter.native="handleLogin"
                >
                  <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>
                </el-input>
                <div class="login-code">
                  <img :src="codeUrl" @click="getCode" class="login-code-img"/>
                </div>
              </el-form-item>
              <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
              <el-form-item style="width:100%;">
                <el-button
                  :loading="loading"
                  size="medium"
                  type="primary"
                  style="width:100%;"
                  @click.native.prevent="handleLogin"
                >
                  <span v-if="!loading">登 录</span>
                  <span v-else>登 录 中...</span>
                </el-button>
                <div style="float: right;" v-if="register">
                  <router-link class="link-type" :to="'/register'">立即注册</router-link>
                </div>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </div>
    </el-card>


    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2022  JOBSYS All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
import {getCodeImg} from "@/api/login";
import Cookies from "js-cookie";
import {encrypt, decrypt} from '@/utils/jsencrypt'

export default {
  name: "Login",
  data() {
    return {
      codeUrl: "",
      loginForm: {
        username: "superAdmin",
        password: "admin123",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          {required: true, trigger: "blur", message: "请输入您的账号"}
        ],
        password: [
          {required: true, trigger: "blur", message: "请输入您的密码"}
        ],
        code: [{required: true, trigger: "change", message: "请输入验证码"}]
      },
      loading: false,
      // 验证码开关
      captchaOnOff: true,
      // 注册开关
      register: true,
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaOnOff = res.captchaOnOff === undefined ? true : res.captchaOnOff;
        if (this.captchaOnOff) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, {expires: 30});
            Cookies.set("password", encrypt(this.loginForm.password), {expires: 30});
            Cookies.set('rememberMe', this.loginForm.rememberMe, {expires: 30});
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {


            this.$router.push({path: this.redirect || "/"}).catch(() => {
            });
          }).catch(() => {
            this.loading = false;
            if (this.captchaOnOff) {
              this.getCode();
            }
          });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
  background-repeat: no-repeat;
}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
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

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.login-code {
  width: 33%;
  height: 38px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.el-login-footer {
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

.login-code-img {
  height: 38px;
}
</style>
