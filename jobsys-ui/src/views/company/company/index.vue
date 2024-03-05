<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">

      <el-form-item label="公司名称" prop="comName">
        <el-input v-model="queryParams.comName" placeholder="请输入公司名称" clearable size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>

      <el-form-item label="公司行业" prop="comType">
        <el-select v-model="queryParams.comType" placeholder="请选择公司行业" clearable size="small">
          <el-option v-for="dict in dict.type.com_type" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>

      <el-form-item label="公司融资状态" prop="isMarket">
        <el-select v-model="queryParams.isMarket" placeholder="请选择公司融资状态" clearable size="small">
          <el-option v-for="dict in dict.type.is_market" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>

      <el-form-item label="公司人数规模" prop="comScale">
        <el-select v-model="queryParams.comScale" placeholder="请选择公司人数规模" clearable size="small">
          <el-option v-for="dict in dict.type.com_scale" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" @click="handleAdd"
                   v-hasPermi="['company:company:add']">新增公司
        </el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" @click="handleExport"
                   v-hasPermi="['company:company:export']">导出
        </el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>

    </el-row>


    <el-table v-loading="loading" :data="companyList" @selection-change="handleSelectionChange" border>
      <div slot="empty">
        <el-empty description="暂无任何文章信息"/>
      </div>

      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="主键" align="center" prop="comId" v-if="false"/>
      <el-table-column label="名称" align="center" prop="comName" width="350" show-overflow-tooltip/>
      <el-table-column label="LOGO" align="center" prop="comImg" width="120">
        <template slot-scope="scope">
          <image-preview :src="scope.row.comImg" :width="50" :height="50"/>
        </template>
      </el-table-column>

      <el-table-column label="公司简介" align="center" prop="comInfo" width="150">
        <template slot-scope="scope">
          <el-tooltip effect="light" content="Bottom center">
            <template #content>
              <div style="width: 300px">
                <editor :value="scope.row.comInfo" :min-height="200" :readOnly="true"
                        :toolbar="[[]]"/>
              </div>
            </template>
            <span style="font-size: 12px;color: #1ab394">简介详情</span>
          </el-tooltip>
        </template>
      </el-table-column>

      <el-table-column label="所属于行业" align="center" prop="comType" width="150">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.com_type" :value="scope.row.comType"/>
        </template>
      </el-table-column>
      <el-table-column label="融资状态" align="center" prop="isMarket" width="130">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.is_market" :value="scope.row.isMarket"/>
        </template>
      </el-table-column>
      <el-table-column label="联系电话" align="center" prop="comTel" width="190" show-overflow-tooltip/>
      <el-table-column label="公司规模人数" align="center" prop="comScale" width="150">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.com_scale" :value="scope.row.comScale"/>
        </template>
      </el-table-column>

      <el-table-column label="公司地址" align="center" width="330" show-overflow-tooltip>
        <template slot-scope="scope">
          <span>{{ scope.row.province }}{{ scope.row.property }}{{ scope.row.district }}{{ scope.row.address }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="550" fixed="right" class-name="small-padding fixed-width">


        <!--fixed="right"-->
        <template slot-scope="scope">


          <el-button size="small" type="warning" plain icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['company:company:edit']">修改
          </el-button>
          <el-button size="small" type="danger" plain icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['company:company:remove']">删除
          </el-button>
          <el-button size="small" type="info" plain icon="el-icon-document" @click="selectBusinessInfo(scope.row)">
            工商信息
          </el-button>

          <el-button size="small" type="success" plain icon="el-icon-s-custom" @click="gotoApplyDetail(scope.row)">
            招聘详情
          </el-button>
          <el-button size="small" type="success" plain icon="el-icon-s-custom" @click="addUser(scope.row)">增加招聘负责人
          </el-button>


        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />


    <!-- 添加或修改公司对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="50%" append-to-body>
      <div class="el-dialog-div">

        <el-form ref="form" :model="form" :rules="rules" label-width="120px">
          <el-form-item label="公司名称" prop="comName">
            <el-input v-model="form.comName" placeholder="请输入公司名称"/>
          </el-form-item>
          <el-form-item label="公司类型" prop="comType">
            <el-select v-model="form.comType" placeholder="请选择公司类型">
              <el-option
                v-for="dict in dict.type.com_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否上市" prop="isMarket">
            <el-select v-model="form.isMarket" placeholder="请选择是否上市">
              <el-option
                v-for="dict in dict.type.is_market"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="公司联系电话：" prop="comTel">
            <el-input v-model="form.comTel" placeholder="请输入公司类型电话"/>
          </el-form-item>
          <el-form-item label="公司人数规模：" prop="comScale">
            <el-select v-model="form.comScale" placeholder="请选择公司规模人数">
              <el-option
                v-for="dict in dict.type.com_scale"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="公司工作时间：">


            <el-time-select
              v-model="form.startTime"

              class="mr-4"
              placeholder="工作上班时间"
              start="06:00"
              step="00:15"
              end="13:00"
            />
            <span style="margin: 0 15px;font-size: 14px"> - </span>
            <el-time-select
              v-model="form.endTime"

              placeholder="工作下班时间"
              start="12:00"
              step="00:15"
              end="22:30"
            />
          </el-form-item>

          <el-form-item label="公司处于地区" prop="property">
            <el-select v-model="form.province" @change="choseProvince" placeholder="省级地区"
                       style="width: 152px; margin-right: 5px">
              <el-option v-for="(item, $index) in province" :key="$index" :label="item.value" :value="item.id">
              </el-option>
            </el-select>
            <el-select style="width: 190px; margin-right: 5px" v-model="form.property" @change="choseCity"
                       placeholder="市级地区">
              <el-option v-for="(item, $index) in city" :key="$index" :label="item.value" :value="item.id"></el-option>
            </el-select>
            <el-select style="width: 170px" v-model="form.district" @change="choseBlock" placeholder="区级地区">
              <el-option v-for="(item, $index) in block" :key="$index" :label="item.value" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="公司详细地址" prop="address">
            <el-input v-model="form.address" placeholder="请输入公司详细地址"/>
          </el-form-item>

          <el-form-item label="公司图片">
            <image-upload v-model="form.comImg"/>
          </el-form-item>
          <el-form-item label="公司简介">
            <editor v-model="form.comInfo" :min-height="192"/>
          </el-form-item>

        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


    <!-- 工商信息   -->
    <el-drawer :visible.sync="showBusiness" :title="businessTitle">
      <div class="demo-drawer__content">
        <div v-if="businessInfo != null && businessInfo.legal != '' ">


          <el-card>
            <table style="border:1px solid #DCDFE6;padding: 15px " cellpadding="0" cellspacing="0">
              <tr>
                <td class="table-left">法定代表人:</td>
                <td class="table-right">{{ businessInfo.legal }}</td>
              </tr>
              <tr>
                <td class="table-left">注册资本：</td>
                <td class="table-right">{{ businessInfo.capital }}万</td>
              </tr>
              <tr>
                <td class="table-left">成立日期：</td>
                <td class="table-right">{{ businessInfo.setUpTime }}</td>
              </tr>
              <tr>
                <td class="table-left">企业类型：</td>
                <td class="table-right">
                  <dict-tag :options="dict.type.business_com_type" :value="businessInfo.comType "/>
                </td>
              </tr>
              <tr>
                <td class="table-left">经营状态：</td>
                <td class="table-right">
                  <dict-tag :options="dict.type.bu_type" :value="businessInfo.comState"/>
                </td>
              </tr>
              <tr>
                <td class="table-left">注册地址：</td>
                <td class="table-right">{{ businessInfo.address }}</td>
              </tr>
              <tr>
                <td class="table-left">统一社会信用代码：</td>
                <td class="table-right">{{ businessInfo.comCode }}</td>
              </tr>
              <tr>
                <td class="table-left">经营范围：</td>
                <td class="table-right">
                  {{ businessInfo.comScope }}
                </td>
              </tr>
            </table>
          </el-card>

          <el-card>
            <el-steps :active="businessInfo.checkState" finish-status="success" align-center>
              <el-step title="提交信息"/>
              <el-step title="工作人员审核"/>
              <el-step>
                <div slot="title">
                  <span v-if="businessInfo.checkState == 4">审核通过</span>
                  <span v-else-if="businessInfo.checkState == 3">审核不通过</span>
                  <span v-else>审核中</span>
                </div>
              </el-step>
            </el-steps>
          </el-card>

          <div class="demo-drawer__footer" style="text-align:center;padding: 50px 20px">
            <el-button @click="showBusiness = false">关闭</el-button>
            <el-button type="primary" :loading="loading" @click="updateBusiness()">修改</el-button>
          </div>
        </div>

        <div v-else>
          <el-empty description="暂无此公司相关工商信息"></el-empty>

          <div class="demo-drawer__footer" style="text-align:center;padding: 50px 20px">
            <el-button @click="showBusiness = false">关闭</el-button>
            <el-button type="primary" :loading="loading" @click="addBusiness()">添加</el-button>
          </div>
        </div>


      </div>


    </el-drawer>


    <!-- 添加或修改business对话框 -->
    <el-dialog :title="addBusinessTitle" :visible.sync="addBusinessWindow" width="50%" append-to-body>
      <el-form ref="businessForm" :model="businessForm" :rules="businessFormRule" label-width="150px">

        <el-form-item label="法人代表" prop="legal">
          <el-input v-model="businessForm.legal" placeholder="请输入法人代表姓名"/>
        </el-form-item>

        <el-form-item label="注册资本" prop="capital">
          <el-input-number v-model="businessForm.capital" :precision="4" :step="10" :min="0"></el-input-number>
          <span style="font-size: 14px;color: red;margin-left: 5px">万元</span>
        </el-form-item>

        <el-form-item label="成立时间" prop="setUpTime">
          <el-date-picker clearable size="small"
                          v-model="businessForm.setUpTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择成立时间">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="企业类型" prop="comType">
          <el-select v-model="businessForm.comType" placeholder="请选择企业类型">
            <el-option
              v-for="dict in dict.type.business_com_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="经营状态" prop="comState">
          <el-select v-model="form.comState" placeholder="请选择经营状态">
            <el-option
              v-for="dict in dict.type.bu_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="注册地址" prop="address">
          <el-input v-model="businessForm.address" placeholder="请输入注册地址"/>
        </el-form-item>

        <el-form-item label="统一社会信用代码" prop="comCode">
          <el-input v-model="businessForm.comCode" placeholder="请输入统一社会信用代码"/>
        </el-form-item>

        <el-form-item label="公司经营范围" prop="comScope">
          <el-input v-model="businessForm.comScope" type="textarea" placeholder="请输入内容"/>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="commitBusinessForm">确 定</el-button>
        <el-button @click="addBusinessWindow=false">关 闭</el-button>
      </div>

    </el-dialog>


    <!-- 分配招聘负责人   -->
    <el-dialog :title="addTitle" :visible.sync="addUserOpen" width="500px" append-to-body>
      <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="register-form">

        <el-form-item prop="username">
          <el-input v-model="registerForm.username" type="text" auto-complete="off" placeholder="账号">
            <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
          </el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input v-model="registerForm.password" type="password" auto-complete="off" placeholder="密码">
            <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
          </el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input v-model="registerForm.confirmPassword" type="password" auto-complete="off" placeholder="确认密码">
            <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
          </el-input>
        </el-form-item>
        <el-form-item prop="code" v-if="true">
          <el-row>
            <el-col :span="12">
              <el-input
                v-model="registerForm.code"
                auto-complete="off"
                placeholder="验证码"
                style="width: 98%"
                @keyup.enter.native="handleRegister"
              >
                <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>
              </el-input>
            </el-col>
            <el-col :span="12">
              <div class="register-code">
                <img :src="codeUrl" @click="getCode" class="register-code-img"/>
              </div>
            </el-col>
          </el-row>


        </el-form-item>
        <el-form-item style="width:100%;">
          <el-button :loading="loading" size="medium" type="primary" style="width:100%;"
                     @click.native.prevent="handleRegister">
            <span v-if="!loading">添 加</span>
            <span v-else>添 加 中...</span>
          </el-button>
        </el-form-item>
      </el-form>

    </el-dialog>
  </div>
</template>

<script>
import {listCompany, getCompany, delCompany, addCompany, updateCompany} from "@/api/company/company";
import ChineseDistricts from "@/plugins/cityList"
import {listBusiness, addBusiness, updateBusiness} from "@/api/company/business";
import {getCodeImg, register} from "@/api/login";

export default {
  name: "Company",
  dicts: ['com_type', 'is_market', 'com_scale', 'business_com_type', 'bu_type'],

  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.registerForm.password !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      //地级市联动
      ChineseDistricts: ChineseDistricts,
      province: [],
      shi1: [],
      qu1: [],
      city: [],
      block: [],
      pname: "", //省的名字
      cname: "", //市的名字
      bname: "", //区的名字

      options: [{value: '1', label: '开业'}, {value: '2', label: '存续'}, {value: '3', label: '停业'}],
      loading: true,  // 遮罩层
      ids: [],  // 选中数组
      single: true,  // 非单个禁用
      multiple: true, // 非多个禁用
      showSearch: true, // 显示搜索条件

      comId: null, // 存储单个公司
      addUserOpen: false,
      addTitle: "",
      codeUrl: "",
      registerForm: {
        username: "",
        password: "",
        confirmPassword: "",
        code: "",
        uuid: "",
        comId: "",
        roleId: 103
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
      captchaOnOff: true,

      showBusiness: false, // 工商信息标志显示
      businessTitle: null, //工商信息抽屉标题
      businessWindow: false, //修改提交公司信息
      addBusinessWindow: false, // 修改添加工商信息
      businessInfo: {}, //公司工商信息
      addBusinessTitle: null, // 公司工商弹窗信息
      businessForm: {},
      isAdd: false,
      businessFormRule: {
        legal: [
          {required: true, message: "请输入公司法人代表", trigger: "blur"}
        ],
        capital: [
          {required: true, message: "请输入公司法注册资本，单位：万元", trigger: "blur"}
        ],
        setUpTime: [
          {required: true, message: "请填写公司注册时间", trigger: "blur"}
        ],
        comType: [
          {required: true, message: "请选择公司所属于类型", trigger: "blur"}
        ],
        comState: [
          {required: true, message: "请选择公司状态", trigger: "blur"}
        ],
        address: [
          {required: true, message: "请填写公司注册地址", trigger: "blur"}
        ],
        comCode: [
          {required: true, message: "请填写公司统一信用代码", trigger: "blur"}
        ],
        comScope: [
          {required: true, message: "请填写公司经营范围", trigger: "blur"}
        ],
      },


      total: 0,  // 总条数
      companyList: [],  // 公司表格数据
      title: "",  // 弹出层标题
      open: false,  // 是否显示弹出层
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        comId: null,
        comName: null,
        comImg: null,
        comInfo: null,
        comType: null,
        isMarket: null,
        comTel: null,
        comScale: null,
        workTime: null,
        province: null,
        property: null,
        district: null,
        address: null,
        userId: this.$store.state.user.userId,
      },
      // 表单参数
      form: {
        comId: null,
        comName: null,
        comImg: null,
        comInfo: null,
        comType: null,
        isMarket: null,
        comTel: null,
        comScale: null,
        workTime: null,
        startTime: null,
        endTime: null,
        province: null,
        property: null,
        district: null,
        address: null,
        userId: this.$store.state.user.userId,
        applyJobList: [],
      },
      // 表单校验
      rules: {
        comName: [
          {required: true, message: "请填写公司名称", trigger: "blur"}
        ],
        comImg: [
          {required: true, message: "请上传公司图片", trigger: "blur"}
        ],
        comInfo: [
          {required: true, message: "请填写公司简介", trigger: "blur"}
        ],
        comType: [
          {required: true, message: "请选择公司所属于行业类型", trigger: "change"}
        ],
        isMarket: [
          {required: true, message: "请选择公司融资阶段", trigger: "change"}
        ],
        comTel: [
          {required: true, message: "请填写公司联系电话", trigger: "blur"}
        ],
        comScale: [
          {required: true, message: "请选择公司规模", trigger: "change"}
        ],
        workTime: [
          {required: true, message: "请选择公司工作时间", trigger: "blur"}
        ],
        province: [
          {required: true, message: "请选择公司所处于省份", trigger: "blur"}
        ],
        property: [
          {required: true, message: "请选择公司所处于地级市", trigger: "blur"}
        ],
        address: [
          {required: true, message: "请填写公司详细地址", trigger: "blur"}
        ],
      },

    };
  },
  created() {
    let userId = this.$store.state.user.userId;
    if (userId == null || userId === '') {
      this.$confirm('获取参数缺失，请重新登录！', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('LogOut').then(() => {
          location.href = '/login';
        })
      }).catch(() => {
      });
    }
    this.getList();
    this.getCityData();
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
    addUser(row) {
      this.getCode();
      this.addUserOpen = true;
      this.registerForm.comId = row.comId;
      this.addTitle = "为 " + row.comName + " 增加招聘负责人"
    },
    handleRegister() {
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          this.loading = true;
          register(this.registerForm).then(res => {

            const username = this.registerForm.username;
            this.$alert("<font color='red'>恭喜你，分配的账号 " + username + " 分配成功！</font>", '系统提示', {
              dangerouslyUseHTMLString: true,
              type: 'success'
            }).then(() => {
              this.loading = false;
              this.addUserOpen = false;
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
    },

    //添加工商信息
    addBusiness() {
      this.isAdd = true;
      this.addBusinessWindow = true;
      this.addBusinessTitle = '添加工商信息';
      this.restBusinessForm();
    },
    //修改工商信息
    updateBusiness() {
      this.isAdd = false;
      this.addBusinessWindow = true;
      this.addBusinessTitle = '修改工商信息';
      this.businessForm = this.businessInfo;
    },

    //提交公司工商信息信息表格
    commitBusinessForm() { //businessForm
      let isAdd = this.isAdd;
      this.$refs["businessForm"].validate(valid => {
        if (valid) {
          this.businessForm.checkState = 1;
          if (isAdd) {
            this.businessForm.comId = this.comId;
            addBusiness(this.businessForm).then(response => {
              this.businessInfo = this.businessForm;
              this.$modal.msgSuccess("添加成功");
              this.addBusinessWindow = false;
            })
          } else {
            this.businessForm.checkState = 2;
            updateBusiness(this.businessForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.addBusinessWindow = false;
            }).catch(function (e) {
              console.log(e)
            })
          }
        }
      });
    },


    restBusinessForm() {
      this.businessForm = {
        legal: null, // 法人
        capital: null, // 注册资本
        setUpTime: null,// 创建时间
        comType: null,// 公司类型
        comState: null,// 公司状态
        address: null,//注册地址
        comCode: null,// 公司代码
        comScope: null,//公司经营范围
      };
    },

    //查询工商信息
    selectBusinessInfo(row) {
      this.comId = row.comId;
      this.showBusiness = true;
      this.businessTitle = "公司：" + row.comName + " 的工商信息如下";
      this.businessInfo = {legal: ''};
      listBusiness({comId: this.comId}).then(response => {
        if (response == null || response.rows.length == 0) {
          return;
        }
        this.businessInfo = response.rows[0];
        this.businessInfo.checkState = Number(this.businessInfo.checkState);
      })
    },

    /** 查询公司列表 */
    getList() {
      this.loading = true;
      listCompany(this.queryParams).then(response => {
        this.companyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },

    // 公司信息表单重置
    reset() {
      this.form = {
        comId: null,
        comName: null,
        comImg: null,
        comInfo: null,
        comType: null,
        isMarket: null,
        comTel: null,
        comScale: null,
        workTime: null,
        startTime: null,
        endTime: null,
        province: null,
        property: null,
        district: null,
        address: null,
        isDelete: null,
        userId: this.$store.state.user.userId,
        applyJobList: [],
      };
      this.resetForm("form");
    },

    //** 搜索按钮操作 、
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    //** 重置按钮操作
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.comId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },

    //** 新增按钮操作
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加公司";
    },

    // 修改按钮操作
    handleUpdate(row) {
      this.reset();
      this.open = true;
      this.title = "修改公司";
      let workTime = row.workTime.split("-")
      this.form.comId = row.comId;
      this.form.comName = row.comName;
      this.form.comImg = row.comImg;
      this.form.comInfo = row.comInfo;
      this.form.comType = row.comType;
      this.form.isMarket = row.isMarket;
      this.form.comTel = row.comTel;
      this.form.comScale = row.comScale;
      this.form.startTime = workTime[0];
      this.form.endTime = workTime[1];
      this.form.province = row.province;
      this.form.property = row.property;
      this.form.district = row.district;
      this.form.address = row.address;
      this.form.userId = this.$store.state.user.userId;
      this.form.applyJobList = [];
      this.choseProvince(row.province);
      this.choseCity(row.property)
      this.choseBlock(row.district);
      /* const comId = row.comId || this.ids
       getCompany(comId).then(response => {
         this.form = response.data;
         this.open = true;
         this.title = "修改公司";
       });*/
    },

    // 提交按钮
    submitForm() {
      this.form.workTime = this.form.startTime + "-" + this.form.endTime;
      this.form.property = this.cname;
      this.form.province = this.pname;
      this.form.district = this.bname;
      this.form.applyJobList = [];
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.comId != null) {
            console.log(this.form);
            updateCompany(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCompany(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    //跳转到莫一个招聘公司详情
    gotoApplyDetail(row) {
      this.$router.push({path: '/company/applyJob', query: {comId: row.comId}});
    },

    //** 删除按钮操作 */
    handleDelete(row) {
      const comIds = row.comId || this.ids;
      this.$modal.confirm('是否确认删除公司编号为"' + comIds + '"的数据项？').then(function () {
        return delCompany(comIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },

    //** 导出按钮操作 */
    handleExport() {
      this.download('company/company/export', {
        ...this.queryParams
      }, `company_${new Date().getTime()}.xlsx`)
    },

    // 加载china地点数据，三级
    getCityData: function () {
      let that = this;
      that.ChineseDistricts.forEach(function (item, index) {
        //省级数据
        that.province.push({
          id: item.code,
          value: item.name,
          children: item.children,
        });
      });
    },

    // 选省
    choseProvince: function (e) {
      let that = this;
      that.city = [];
      that.block = [];
      that.cname = "";
      that.bname = "";
      for (let index2 in that.province) {
        if (e === that.province[index2].id) {
          that.shi1 = that.province[index2].children;
          that.pname = that.province[index2].value;
          that.shi1.forEach(function (citem, cindex) {
            that.city.push({
              id: citem.code,
              value: citem.name,
              children: citem.children,
            });
          });
        }
      }
      console.log(that.pname);
    },

    // 选市
    choseCity: function (e) {
      let that = this;
      that.block = [];
      that.bname = "";
      for (let index3 in that.city) {
        if (e === that.city[index3].id) {
          that.qu1 = that.city[index3].children;
          that.cname = that.city[index3].value;
          that.E = that.qu1[0].id;
          that.qu1.forEach(function (bitem, bindex) {
            that.block.push({
              id: bitem.code,
              value: bitem.name,
              children: [],
            });
          });
        }
      }
      console.log(that.cname);
    },

    // 选区
    choseBlock: function (e) {
      this.bname = e;
      console.log(this.bname);
    },

  }
};
</script>

<style scoped>
.el-dialog-div {
  height: 60vh;
  overflow: auto;
  padding: 10px;
}

.table-left {
  padding: 5px 10px;
  color: gray;
  font-size: 14px;
  width: 35%;
  text-align: right;
}

.table-right {
  padding: 5px 10px;
  color: black;
  font-size: 14px;
}

.register-code-img {
  height: 38px;
}


</style>
