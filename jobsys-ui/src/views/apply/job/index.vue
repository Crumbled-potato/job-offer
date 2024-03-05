<template>


  <div style="padding:10px">
    <!-- 顶部   -->

    <el-row :gutter="10" class="el_row" style="background-color: #575F71;padding-top: 20px">


      <!--    工作基本情况  -->
      <el-row>
        <el-col :span="8" :offset="4">
          <span class="job_title white">{{ jobDetail.jobName }}</span>
          <span class="job_title red">{{ jobDetail.jobSalary }}</span>
          <el-button type="warning" v-if="jobDetail.jobState === 1" style="margin-left: 30px" disabled> 招 聘 中
          </el-button>
          <el-button type="danger" v-if="jobDetail.jobState === 2" style="margin-left: 30px" disabled
                     class="mosHover2">停止招聘
          </el-button>
          <el-button type="danger" v-if="jobDetail.jobState === 3" style="margin-left: 30px" disabled
                     class="mosHover2">职位信息异常
          </el-button>
        </el-col>

        <!--    工作福利待遇    -->
        <el-col :span="8">
          <div v-if="treatment.length<5">
            <el-button v-for="item in treatment"
                       style="margin-top: 10px;background-color:#575F71;border: #13CE66 solid 1px"
                       size="small" type="success" round plain>{{ item }}
            </el-button>
          </div>
          <div v-else>
            <el-button
              style="margin-top: 10px;background-color:#575F71;border: #13CE66 solid 1px"
              size="small" type="success" round plain v-for="(item,index) in treatment" v-if="index<5">{{ item }}
            </el-button>
            <el-tooltip>
              <template #content>
              <span v-for="(item,index) in treatment">
                <el-button
                  style="margin: 5px;background-color:#575F71;border: #13CE66 solid 1px"
                  size="small" type="success" round plain>{{ item }}
                </el-button>
                <span v-if="((index + 1) % 5) ===0"><br></span>
              </span>
              </template>
              <el-button
                style="margin-top: 10px;background-color:#575F71;border: #13CE66 solid 1px"
                size="small" type="success" round plain>...
              </el-button>
            </el-tooltip>
          </div>
        </el-col>
      </el-row>

      <!--   求职 沟通   -->
      <el-row>

        <!--   沟通     -->
        <el-col :span="8" :offset="4">
          <div style="margin-left: 10px;padding: 10px 0;margin-top: 10px">
            <el-button type="success" v-if="userIsCollect" plain style="font-size: 16px;border: #13CE66 solid 1px"
                       @click="collect()">
              <i class="el-icon-star-off"></i> 我感兴趣
            </el-button>
            <el-button type="info" v-else plain style="font-size: 16px;border: #13CE66 solid 1px" @click="collect()">
              <i class="el-icon-star-off"></i> 取消收藏
            </el-button>
            <el-button type="success" style="font-size: 16px" @click="talkThisJob()">
              <i class="el-icon-chat-line-square"></i> 立即投递
            </el-button>
            <el-button type="danger" style="font-size: 16px" @click="complaintWindow = true">
              <i class="el-icon-warning-outline"></i> 我要举报
            </el-button>
          </div>
        </el-col>

        <!--  公司基本信息      -->
        <el-col :span="8">
          <div class="gray job_need" style="padding: 10px 0">
            <div>
              <span>
                <i class="el-icon-office-building"></i>
                <span v-if="company.comName.length < 11" class="mosHover2">
                {{ company.comName }}
                </span>
                <el-tooltip>
                   <template #content><span>{{ company.comName }}</span></template>
                   <span v-if="company.comName.length > 10" class="mosHover2">
                     {{ company.comName.slice(0, 10) + '...' }}
                   </span>
                </el-tooltip>
              </span>
              <span style="margin-left: 10px" class="mosHover2">  <dict-tag :options="dict.type.com_type"
                                                                            :value="company.comType"/></span> |
              <span style="margin-left: 10px" class="mosHover2"> <dict-tag :options="dict.type.is_market"
                                                                           :value="company.isMarket"/></span> |
              <span style="margin-left: 10px" class="mosHover2">  <dict-tag :options="dict.type.com_scale"
                                                                            :value="company.comScale"/></span> |
              <span style="margin-left: 10px" class="mosHover2">
                 <i class="el-icon-phone-outline"></i> {{ company.comTel }}
              </span>
            </div>
            <div style="margin-top: 5px">
              <span class="mosHover2">
                <i class="el-icon-location-information"></i> {{ jobDetail.jobSite }}
              </span>
              <span style="margin-left: 10px" class="mosHover2">
                 <i class="el-icon-s-cooperation"></i> {{ jobDetail.expYear }}
              </span>
              <span style="margin-left: 10px" class="mosHover2">
                 <i class="el-icon-collection"></i>  <dict-tag :options="dict.type.education"
                                                               :value="jobDetail.education"/>
              </span>
              <span style="margin-left: 10px" class="mosHover2">
                 <i class="el-icon-time"></i> {{ company.workTime }}
              </span>
            </div>
          </div>
        </el-col>

      </el-row>
      <!--  求职 沟通  结束    -->

    </el-row>


    <!--  头部部分结束    -->

    <!-- 工作职责详情   -->
    <el-row :gutter="10" class="el_row" style="margin-top: 20px">
      <el-col :span="12" :offset="4">
        <el-card shadow="hover">
          <!--头部结束-->
          <!--   职位描述       -->
          <el-row>
            <el-divider><h3 style="color: #1ab394"><i class="el-icon-s-cooperation"></i> 职位详情</h3></el-divider>
            <h4 style="color: #1ab394"><i class="el-icon-s-promotion"></i> 职位详情</h4>
            <div>
              <editor style="margin-top: 20px" v-model="jobDetail.jobDetail" :readOnly="true" :toolbar="[]"/>
            </div>
          </el-row>
          <el-row>
            <h4 style="color: #1ab394"><i class="el-icon-finished"></i> 任职要求</h4>
            <div>
              <editor style="margin-top: 20px" v-model="jobDetail.jobReq" :readOnly="true" :toolbar="[]"/>
            </div>
          </el-row>
          <el-row>
            <h4 style="color: #1ab394"><i class="el-icon-office-building"></i> 公司介绍</h4>
            <div>
              <editor v-model="company.comInfo" :readOnly="true" :toolbar="[]"/>
            </div>
          </el-row>
          <!--  工商信息        -->
          <el-row>
            <h4 style="color: #1ab394"><i class="el-icon-s-check"></i> 公司工商信息</h4>
            <el-collapse>
              <el-collapse-item :title=company.comName>
                <table style="border:1px solid #DCDFE6;padding: 15px " cellpadding="0" cellspacing="0">
                  <tr>
                    <td class="table-left">法定代表人:</td>
                    <td class="table-right">{{ businessInfo.legal }}</td>
                    <td class="table-left">注册资本：</td>
                    <td class="table-right">{{ businessInfo.capital }}万</td>
                  </tr>
                  <tr>
                    <td class="table-left">成立日期：</td>
                    <td class="table-right">{{ businessInfo.setUpTime }}</td>
                    <td class="table-left">企业类型：</td>
                    <td class="table-right">
                      <dict-tag :options="dict.type.business_com_type" :value="businessInfo.comType "/>
                    </td>
                  </tr>
                  <tr>
                    <td class="table-left">经营状态：</td>
                    <td class="table-right">
                      <span v-if="businessInfo.comState == 1 || businessInfo.comState == '1'">开业</span>
                      <span v-else-if="businessInfo.comState == 2 || businessInfo.comState == '2'">存续</span>
                      <span v-else-if="businessInfo.comState == 3 || businessInfo.comState == '3'">停业</span>
                      <span v-else>其他</span>
                    </td>
                    <td class="table-left">统一社会信用代码：</td>
                    <td class="table-right">{{ businessInfo.comCode }}</td>
                  </tr>
                  <tr>
                    <td class="table-left">注册地址：</td>
                    <td class="table-right" colspan="3">{{ businessInfo.address }}</td>
                  </tr>
                  <tr>
                    <td class="table-left">经营范围：</td>
                    <td class="table-right" colspan="3">
                      {{ businessInfo.comScope }}
                    </td>
                  </tr>
                </table>
              </el-collapse-item>
            </el-collapse>
          </el-row>
        </el-card>
      </el-col>

      <!-- 公司介绍     -->
      <el-col :span="4">
        <el-card shadow="never">
          <el-divider><h3 style="color: #1ab394">公司信息</h3></el-divider>

          <div style="padding: 5px 0" class="mosHover2">
            <el-col :span="6">
              <img alt="公司照片" src="@/assets/404_images/404.png" width="50">
            </el-col>
            <span>
                {{ company.comName }}
             </span>
          </div>

          <div class="company-item mosHover2">
            <i class="el-icon-location-information"></i> 详细地址： {{ jobDetail.jobSite }}
          </div>

          <div class="company-item mosHover2">
            <span style="font-size: 12px"> {{ company.address }}</span>
          </div>

          <div class="company-item mosHover2">
            <i class="el-icon-phone-outline"></i> 联系电话： {{ company.comTel }}
          </div>

          <div class="company-item mosHover2">
            <i class="el-icon-time"></i> 工作时间： {{ company.workTime }}
          </div>

          <div class="company-item mosHover2">
            <i class="el-icon-s-custom"></i> 人员规模：
            <dict-tag :options="dict.type.com_scale" :value="company.comScale"/>
          </div>
          <div class="company-item mosHover2">
            <i class="el-icon-menu"></i> 公司类型：
            <dict-tag :options="dict.type.com_type" :value="company.comType"/>
          </div>
          <el-divider><h3 style="color: #1ab394">相似职位</h3></el-divider>
          <el-row>
            <div v-for="job in similarWorkList" class="mosHover" @click="gotoSimilarJob(job)">
              <!--职位消息 -->
              <div>
                <span>{{ job.jobName }}</span>
                <span class="job_salary">{{ job.jobSalary }}</span>
              </div>
              <!--公司消息-->
              <div class="text item gray job_need  " style="padding-bottom: 10px;margin-top: 10px">
                <el-col :span="16" style="font-size: 14px">
                  {{ job.jobSite }} | {{ job.company.comName }}
                </el-col>
                <el-col :span="8">
                  <img alt="公司照片" src="@/assets/404_images/404.png" width="50">
                </el-col>
              </div>
              <el-divider/>
            </div>
          </el-row>
        </el-card>
      </el-col>
    </el-row>

    <!-- 选择举报类型   -->
    <el-dialog
      title="我要举报这份工作"
      :visible.sync="complaintWindow"
      width="50%">
      <div class="el-dialog-div">
        <el-steps :active="0" simple finish-status="success">
          <el-step title="选择举报类型" icon="el-icon-warning"/>
          <el-step title="填写举报信息" icon="el-icon-edit-outline"/>
          <el-step title="提交举报信息" icon="el-icon-finished"/>
        </el-steps>
        <el-card shadow="never">
          <div v-for="type in complaintType" @click="chooseComReason(type)">
            <el-card shadow="hover" style="margin: 10px" class="mosHover">
              <h3>{{ type.type }}</h3>
              <span style="font-size: 14px;text-indent: 2em">{{ type.description }}</span>
            </el-card>
          </div>
        </el-card>
      </div>
      <span slot="footer" class="dialog-footer"><el-button @click=" complaintWindow = false">取 消</el-button></span>
    </el-dialog>

    <!--  举报详情填写   -->
    <el-dialog title="我要举报这份工作" :visible.sync="complaintWindow2" width="1000px">
      <div class="el-dialog-div">
        <el-steps :active="1" simple finish-status="success">
          <el-step title="选择举报类型" icon="el-icon-warning"/>
          <el-step title="填写举报信息" icon="el-icon-edit-outline"/>
          <el-step title="提交举报信息" icon="el-icon-finished"/>
        </el-steps>

        <el-form :model="complaintForm" label-width="120px" style="margin-top: 20px">
          <el-form-item label="举报工作：">
            <el-input v-model="complaintForm.jobName" name="jobName" readonly disabled/>
          </el-form-item>
          <el-form-item label="举报类型：">
            <el-input v-model="complaintForm.type" name="type" readonly disabled/>
          </el-form-item>
          <el-form-item label="具体原因" v-if="complaintReason.length > 0">
            <el-checkbox-group v-model="complaintForm.reasonArray">
              <el-checkbox v-for="re in complaintReason" :label=re name="reason"/>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="补充说明：">
            <el-input v-model="complaintForm.userDescribe" placeholder="请进一步描述举报原因!" name="describe" type="textarea"/>
          </el-form-item>
          <el-form-item label="您的姓名：">
            <el-input v-model="complaintForm.userName" placeholder="留下您的姓名，方便工作人员必要时与您联系!" name="name"/>
          </el-form-item>
          <el-form-item label="您的联系方式：">
            <el-input v-model="complaintForm.phone" placeholder="留下您的联系方式，方便工作人员必要时与您联系!" name="phone"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSubmitFor()">提交</el-button>
            <el-button @click="backChooseType()">返回</el-button>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer"><el-button @click="complaintWindow2 = false">关闭</el-button></span>
    </el-dialog>

    <!--  提交举报信息结果  -->
    <el-dialog title="我要举报这份工作" :visible.sync="complaintWindow3" width="1000px">
      <div class="el-dialog-div" style="height: 38vh">
        <el-steps :active="2" simple finish-status="success">
          <el-step title="选择举报类型" icon="el-icon-warning"/>
          <el-step title="填写举报信息" icon="el-icon-edit-outline"/>
          <el-step title="提交举报信息" icon="el-icon-finished"/>
        </el-steps>

        <el-result v-if="complaintResult" icon="success" title="提交成功！" subTitle="感谢您为净化招聘环境的努力，工作人员将进一步核实">
          <template slot="extra">
            <el-button type="primary" size="medium" @click="complaintWindow3 = false">确认</el-button>
          </template>
        </el-result>

        <el-result v-if="!complaintResult" icon="error" title="提交失败！" subTitle="提交失败，请重新提交或与工作人员联系">
          <template slot="extra">
            <el-button type="primary" size="medium" @click="backEditForm()">返回</el-button>
          </template>
        </el-result>
      </div>
    </el-dialog>

    <!--  求职简历    -->
    <el-dialog title="我的简历" :visible.sync="resumeOpen" width="1200px">
      <div class="el-dialog-div" style="height: 38vh">
        <el-card v-for="resume in resumeList" shadow="hover" style="margin-top: 15px">
          <el-col :span="4">
            <h3>姓名：{{ resume.userName }}</h3>
          </el-col>
          <el-col :span="6">
            <h3>意向行业：
              <dict-tag :options="dict.type.com_type" :value="resume.applyType"/>
            </h3>
          </el-col>
          <el-col :span="6">
            <h3>意向岗位：{{ resume.applyJob }}</h3>
          </el-col>
          <el-col :span="2">
            <h3>性别：
              <dict-tag :options="dict.type.sys_user_sex" :value="resume.userSex"/>
            </h3>
          </el-col>
          <el-col :span="2">
            <h3>
              学 历：
              <dict-tag :options="dict.type.education" :value="resume.education"/>
            </h3>

          </el-col>
          <el-col :span="4">
            <h3 style="text-align: center;">
              <el-button size="mini" plain type="success" @click="commitResume(resume)">投递此份简历</el-button>
            </h3>
          </el-col>

        </el-card>

      </div>
    </el-dialog>


  </div>
</template>

<script>

import {getApplyJob} from "@/api/company/applyJob";
import {listBusiness} from "@/api/company/business";
import {applyAddRecord} from "@/api/apply/record";
import {listResumeById} from "@/api/apply/resume";
import {addReport} from "@/api/apply/report";
import {collectCollect, listCollect} from "@/api/apply/collect";
import {addMsg} from "@/api/apply/msg";
import {getNowDate} from "@/utils/ruoyi";

export default {
  name: "index",
  dicts: ['com_type', 'com_scale', 'is_market', 'education', 'business_com_type', 'sys_user_sex', 'education'],
  data() {
    return {
      jobId: '',
      complaintWindow: false,
      complaintWindow2: false,
      complaintWindow3: false,
      jobDetail: {},
      company: {comName: ''},
      treatment: ['五险一金', '定期体检', '年终奖', '股票期权', '带薪年假', '员工旅游', '节日福利'],

      //公司工商信息
      businessInfo: {},

      //相似职位
      similarWorkList: [
        {
          'jobId': 1,
          'jobName': 'Java工程师',
          'jobSalary': '20-28K',
          'jobSite': '南昌',
          company: {
            'comId': 1,
            'comImg': '@/assets/404_images/404.png',
            'comName': '公司名称',
          }
        },
        {
          'jobId': 2,
          'jobName': 'Java工程师',
          'jobSalary': '20-28K',
          'jobSite': '南昌',
          company: {
            'comId': 1,
            'comImg': '@/assets/404_images/404.png',
            'comName': '公司名称',
          }
        },
        {
          'jobId': 3,
          'jobName': 'Java工程师',
          'jobSalary': '20-28K',
          'jobSite': '南昌',
          company: {
            'comId': 1,
            'comImg': '@/assets/404_images/404.png',
            'comName': '公司名称',
          }
        },
        {
          'jobId': 4,
          'jobName': 'Java工程师',
          'jobSalary': '20-28K',
          'jobSite': '南昌',
          company: {
            'comId': 1,
            'comImg': '@/assets/404_images/404.png',
            'comName': '公司名称',
          }
        },
        {
          'jobId': 5,
          'jobName': 'Java工程师',
          'jobSalary': '20-28K',
          'jobSite': '南昌',
          company: {
            'comId': 1,
            'comImg': '@/assets/404_images/404.png',
            'comName': '公司名称',
          }
        },
      ],

      //举报原因
      complaintType: [
        {'id': 1, 'type': '散播违法/敏感言论', 'description': '招聘者发布的信息包含违法、政治敏感内容', 'reason': []},
        {'id': 2, 'type': '人身攻击', 'description': '招聘者存在辱骂、骚扰等语言或肢体上的不当行为', 'reason': ['语言攻击', '骚扰', '人身伤害', '歧视']},
        {'id': 3, 'type': '色情骚扰', 'description': '招聘者发布的信息包含色情低俗内容或存在性骚扰行为', 'reason': ['色情信息或图片', '性骚扰', '色情低俗职位']},
        {
          'id': 4,
          'type': '职位虚假',
          'description': '招聘者发布的职位信息与实际沟通职位不符，例如:薪资、工作内容、工作地点不符',
          'reason': ['工作地点不符', '工作内容不符', '薪资待遇不符', '职位已停招']
        },
        {'id': 5, 'type': '招聘者身份虚假', 'description': '招聘者不是其认证公司的员工', 'reason': ['面试公司与认证公司不符', '中介介绍']},
        {
          'id': 6,
          'type': '违法/欺诈行为',
          'description': '招聘者存在引诱求职者从事不法活动或欺诈求职者，例如:网络诈骗、套取简历',
          'reason': ['网络诈骗', '传销', '骗取隐私信息', '引诱参与不法活动']
        },
        {
          'id': 7,
          'type': '收取求职者费用',
          'description': '招聘者以各种名义或变相收取求职者费用，例如:中介费、培训费',
          'reason': ['以招聘的名义招生', '收取中介费', '收取培训费', '收取押金', '收取服装、装备费用']
        },
        {'id': 8, 'type': '发布广告和招商信息', 'description': '招聘者变相发布广告或寻求投资、合作', 'reason': ['发布广告', '发布招商信息']},
        {'id': 9, 'type': '其它违规行为', 'description': '招聘者或公司存在以上列举类型之外的违规行为', 'reason': []},

      ],

      //举报表单
      complaintForm: {
        jobId: '',
        jobName: '',
        typeId: '',
        type: '',
        reasonArray: [],
        reason: "",
        userDescribe: '',
        userName: '',
        phone: '',
      },


      resumeOpen: false,

      resumeList: [],   // resume表格数据

      complaintReason: [],

      //提交结果
      complaintResult: false,

      userIsCollect: null,


    }
  },
  methods: {

    //查询工作详情
    getJobDetail(jobId) {
      getApplyJob(jobId).then(
        res => {
          this.jobDetail = res.data;
          this.company = res.data.company
          //查询公司工商信息
          this.getBusinessInfo(this.company.comId)

        })


    },

    //获取公司工商信息
    getBusinessInfo(comId) {
      listBusiness({comId: comId}).then(response => {
        if (response == null || response.rows.length != 1) {
          return;
        }
        this.businessInfo = response.rows[0];
        this.businessInfo.checkState = Number(this.businessInfo.checkState);
      })
    },

    //提交表单
    handleSubmitFor() {
      this.complaintWindow2 = false;
      this.complaintWindow3 = true;
      for (let j = 0; j < this.complaintForm.reasonArray.length; j++) {
        this.complaintForm.reason += (this.complaintForm.reasonArray[j] + " ");
      }
      console.log(this.complaintForm);
      addReport(this.complaintForm).then(
        res => {
          this.complaintResult = res.code == 200;
        })
    },

    //相似工作
    gotoSimilarJob(item) {
      this.$message({
        message: '相似jobId:' + item.jobId,
        type: 'success'
      });
    },

    //收藏
    collect() {
      let param = {
        jobId: this.jobDetail.jobId,
        comId: this.company.comId,
        jobName: this.jobDetail.jobName,
        comName: this.company.comName,
      }

      collectCollect(param).then(
        res => {
          this.$message({
            message: res.msg,
            type: 'success'
          });
          if (res.code == 200) {
            this.userIsCollect = !this.userIsCollect;
          }
        }
      )
    },


    //投递窗口 查询简历
    talkThisJob() {
      this.resumeOpen = true;
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      listResumeById().then(response => {
        this.resumeList = response.data;
        loading.close();
      });

    },

    //提交投递记录
    commitResume(resume) {
      let params = {
        jobId: this.jobId,
        jobName: this.jobDetail.jobName,
        applyId: this.$store.state.user.userId,
        takeId: this.jobDetail.userId,
        type: '1',
        comId: this.jobDetail.company.comId,
        comName: this.jobDetail.company.comName,
        resumeId: resume.resumeId,
      }

      applyAddRecord(params).then(
        res => {
          if (res.code == 200) {
            let param = {
              userId: this.jobDetail.userId,
              msgContent: "有求职者在" + getNowDate() + "向" + this.company.comName + "的" + this.jobDetail.jobName + "投递了他的简历，快去处理吧！",
              isRead: 2,
            }
            addMsg(param);
          }
          this.$modal.msgSuccess(res.msg);
        }
      )

    },


    //分享
    shareThisJob() {
      this.$message({
        message: 'shareJobId:' + this.jobDetail.jobId,
        type: 'success'
      });
    },


    //返回举报原因
    backChooseType() {
      this.complaintWindow = true;
      this.complaintForm = {
        jobId: '',
        jobName: '',
        typeId: '',
        type: '',
        reason: [],
        describe: '',
        name: '',
        phone: '',
      };
      this.complaintWindow2 = false;
    },

    backEditForm() {
      this.complaintWindow2 = true;
      this.complaintWindow3 = false;
    },

    //选择举报原因
    chooseComReason(type) {
      this.complaintWindow = false;
      this.complaintForm.jobId = this.jobDetail.jobId;
      this.complaintForm.jobName = this.jobDetail.jobName;
      this.complaintForm.typeId = type.id;
      this.complaintForm.type = type.type;
      this.complaintReason = type.reason;
      this.complaintWindow2 = true;
    },


    isCollect() {
      let param = {
        jobId: this.jobId,
        userId: this.$store.state.user.userId,
      }
      listCollect(param).then(
        res => {
          if (res.total == 0 || res.rows[0].userName == "2") {
            this.userIsCollect = true;
          }
        })
    }

  },
  created() {
    let jobId = this.$route.query.jobId;
    this.jobId = jobId;
    this.getJobDetail(jobId);
    this.isCollect();
  }


}
</script>

<style scoped>


.el-dialog-div {
  height: 60vh;
  overflow: auto;
}


.job_salary {
  float: right;
  color: #FD7240
}

.company-item {
  padding: 18px 0;
  font-size: 14px
}

.table-left {
  color: gray;
  font-size: 12px;
  width: 15%;
  text-align: right;
}

.table-right {
  color: black;
  font-size: 12px;
}

.job_title {
  line-height: 40px;
  margin-left: 30px;
  font-size: 24px;
  font-weight: bold;
}

.white {
  color: white;
}

.mosHover:hover {
  cursor: pointer;
  color: #1ab394;
}


.mosHover2:hover {
  cursor: Default;
  color: #1ab394;
}


.job_need {
  font-size: 16px;
  margin: 5px 0 0 10px
}

.gray {
  color: #C7C9D0;
}

.red {
  color: #FD7240
}

.el_row {
  padding: 10px;
}
</style>
