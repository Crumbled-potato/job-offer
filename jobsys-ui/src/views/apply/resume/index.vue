<template>
  <div class="app-container">
    <el-row :gutter="10">
      <el-col :span="12">


        <el-card shadow="hover" style="height: 830px;overflow: auto">
          <h4 style="color: #1ab394"><i class="el-icon-s-promotion"></i> 我的求职历程记录
            <el-button size="mini">
              <i class="el-icon-circle-plus"></i> 找工作
            </el-button>
          </h4>


          <el-form :model="recordQueryParams" :inline="true" v-show="showSearch" label-width="68px">

            <el-form-item label="工作名称" prop="jobName">
              <el-input v-model="recordQueryParams.jobName" placeholder="请输入工作名称" clearable size="small"/>
            </el-form-item>
            <el-form-item label="公司名称" prop="comName">
              <el-input v-model="recordQueryParams.comName" placeholder="请输入公司名称" clearable size="small"/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery" plain>搜索</el-button>
            </el-form-item>
          </el-form>

          <el-row :gutter="10">
            <el-col v-for="record in recordList" style="margin-top: 10px;" :span="12">
              <el-card style="padding-bottom: 10px" shadow="hover">
                <div style="padding: 10px">
                  <el-button v-if="record.type == 1" size="mini" type="warning">主动投递</el-button>
                  <el-button v-if="record.type == 2" size="mini" type="success">邀约投递</el-button>
                  <span style="margin-left: 30px;color: grey;font-size: 12px">{{ record.createTime }}</span>
                </div>
                <div style="padding: 0 10px">
                  职位名称：{{ record.jobName }}
                  <el-button style="float: right;border: 0;color: #00afff" size="mini" plain @click="gotoJob(record)">
                    职位详情
                  </el-button>
                </div>
                <div style="padding: 0 10px">
                  公司名称：{{ record.comName }}
                  <!--                  <el-button style="float: right;border: 0;color: #00afff" size="mini" plain>公司详情</el-button>-->
                </div>
                <div style="padding: 10px">
                  <el-button style="float: right;width: 100%" size="mini" type="success" plain
                             @click="getRecord(record)">查看求职进度
                  </el-button>
                </div>

              </el-card>

            </el-col>

          </el-row>

          <pagination
            :total="recordTotal"
            :page.sync="recordQueryParams.pageNum"
            :limit.sync="recordQueryParams.pageSize"
            @pagination="getRecordList"
          />

        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card shadow="hover" style="height: 830px;overflow: auto">
          <h4 style="color: #1ab394"><i class="el-icon-s-check"></i> 我的所有简历
            <el-button size="mini" @click="handleAdd" type="success" plain>
              <i class="el-icon-circle-plus"></i> 添 加
            </el-button>
          </h4>
          <el-card shadow="hover" v-if="resumeList.length == 0" style="margin-top: 10px">
            <el-empty description="暂无您的简历信息，快去添加吧！"
                      image="http://127.0.0.1:9300/statics/401.gif"></el-empty>
          </el-card>
          <el-card shadow="hover" v-if="resumeList.length > 0" v-for="(resume,index) in resumeList"
                   style="margin-top: 10px">
            <el-collapse>
              <el-row>
                <el-col :span="4">
                  <h2 style="text-align: center">{{ resume.userName }}</h2>
                </el-col>
                <el-col :span="14">
                  <el-row>
                    <el-row>
                      <h3> 意向行业：
                        <dict-tag :options="dict.type.com_type" :value="resume.applyType"/>
                        | <span style="font-size: 16px">意向岗位：{{ resume.applyJob }}</span></h3>
                    </el-row>
                  </el-row>
                </el-col>
                <el-col :span="6" style="text-align: center">
                  <el-button size="mini" style="margin-top: 10px" plain type="warning" @click="handleUpdate(resume)">
                    <i class="el-icon-edit-outline"></i> 编 辑
                  </el-button>
                  <el-button size="mini" style="margin-top: 10px" plain type="danger" @click="handleDelete(resume)">
                    <i class="el-icon-delete-solid"></i> 删 除
                  </el-button>
                </el-col>
              </el-row>
              <el-collapse-item>
                <template slot="title">
                  <span style="color: #1ab394">点击查看我的第 {{ index + 1 }} 份简历详情 <i class="el-icon-view"></i> </span>
                </template>
                <el-divider><i class="el-icon-s-grid"></i> 基本信息</el-divider>
                <el-row>
                  <el-col :span="9">
                    <p>姓 名：{{ resume.userName }}</p>
                    <p>城 市：{{ resume.userCity }}</p>
                    <p>电 话：{{ resume.userPhone }}</p>
                    <p>邮 箱：{{ resume.userEmail }}</p>
                  </el-col>
                  <el-col :span="9">
                    <p>性 别：
                      <dict-tag :options="dict.type.sys_user_sex" :value="resume.userSex"/>
                    </p>
                    <p>学 历：
                      <dict-tag :options="dict.type.education" :value="resume.education"/>
                    </p>
                    <!--                <p>专 业：计算机科学与技术</p>-->
                    <p>出生日期：<span>{{ parseTime(resume.userBirth, '{y}-{m}-{d}') }}</span></p>
                  </el-col>
                  <el-col :span="6" style="text-align: center">
                    <el-image
                      style="width: 150px; height: 150px"
                      :src="resume.userImg"
                    ></el-image>

                  </el-col>
                </el-row>
                <el-row v-if=" resume.major != '' && resume.major != null ">
                  <el-divider><i class="el-icon-shopping-bag-1"></i> 专业技能</el-divider>
                  <editor style="margin-top: 20px" v-model="resume.major" :min-height="200" :readOnly="true"
                          :toolbar="[]"/>
                </el-row>
                <el-row v-if="  resume.eduExp != '' && resume.eduExp != null">
                  <el-divider><i class="el-icon-s-grid"></i> 教育经历</el-divider>
                  <editor style="margin-top: 20px" v-model="resume.eduExp" :min-height="200" :readOnly="true"
                          :toolbar="[]"/>
                </el-row>
                <el-row v-if=" resume.resumeDetail != '' &&  resume.resumeDetail != null">
                  <el-divider><i class="el-icon-folder-add"></i> 公开部分</el-divider>
                  <editor style="margin-top: 20px" v-model="resume.resumeDetail" :min-height="200" :readOnly="true"
                          :toolbar="[]"/>
                </el-row>
                <el-row style="margin-top: 10px" v-if="resume.resumeFile != null ">
                  <el-divider><i class="el-icon-folder-opened"></i> 简历附件</el-divider>
                  简历附件：
                  <span style="color: royalblue" v-if="resume.resumeFile != null || resume.resumeFile != ''">
                    {{
                      resume.resumeFile != null ? resume.resumeFile.split("/")[resume.resumeFile.split("/").length - 1] : ''
                    }}</span>
                  <a :href="resume.resumeFile" style="margin-left: 15px">
                    <el-button size="mini" type="primary">下载</el-button>
                  </a>
                </el-row>
              </el-collapse-item>
            </el-collapse>
          </el-card>
        </el-card>
      </el-col>
    </el-row>


    <!-- 添加或修改简历对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1150px" append-to-body>
      <el-steps :active="active" finish-status="success">
        <el-step title="填写基本信息"></el-step>
        <el-step title="填写专业技能"></el-step>
        <el-step title="填写教育经历"></el-step>
        <el-step title="填写简历补充"></el-step>
        <el-step title="预览"></el-step>
      </el-steps>

      <div style="height: 55vh;overflow: auto;">
        <span><i class="el-icon-info"></i>说明：您的姓名、性别、学历、意向行业、意向职位、电子照片及公开部分将会在人才市场公开，招聘者可以直接查看此部分信息！</span>
        <el-form ref="form" :model="form" :rules="rules" label-width="90px" style="margin-top: 10px">
          <el-row v-if="active == 0">
            <!--    意向岗位        -->
            <el-row>
              <el-col :span="6">
                <el-form-item label="意向行业：" prop="applyType">
                  <el-select v-model="form.applyType" placeholder="请选择意向行业">
                    <el-option
                      v-for="dict in dict.type.com_type"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="意向职位：" prop="applyJob">
                  <el-input v-model="form.applyJob" placeholder="请输入意向职位"/>
                </el-form-item>
              </el-col>
            </el-row>
            <!--   基本信息         -->
            <el-row>

              <el-col :span="6">
                <el-form-item label="姓 名：" prop="userName">
                  <el-input v-model="form.userName" placeholder="请输入姓名"/>
                </el-form-item>
                <el-form-item label="城 市：" prop="userCity">
                  <el-input v-model="form.userCity" placeholder="请输入城市"/>
                </el-form-item>
                <el-form-item label="联系电话：" prop="userPhone">
                  <el-input v-model="form.userPhone" placeholder="请输入手机"/>
                </el-form-item>
                <el-form-item label="电子邮箱：" prop="userEmail">
                  <el-input v-model="form.userEmail" placeholder="请输入邮箱"/>
                </el-form-item>
              </el-col>

              <el-col :span="6">
                <el-form-item label="性别：" prop="userSex">
                  <el-select v-model="form.userSex" placeholder="请选择性别">
                    <el-option
                      v-for="dict in dict.type.sys_user_sex"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="学历：" prop="education">
                  <el-select v-model="form.education" placeholder="请选择学历">
                    <el-option
                      v-for="dict in dict.type.education"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="出生日期：" prop="userBirth">
                  <el-date-picker clearable size="small"
                                  v-model="form.userBirth"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择出生日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="电子照片:">
                  <image-upload v-model="form.userImg"/>
                </el-form-item>
              </el-col>
            </el-row>
            <!--    简历附件        -->
            <el-row>
              <el-form-item label="简历附件">
                <file-upload v-model="form.resumeFile"/>
              </el-form-item>
            </el-row>
          </el-row>


          <el-row v-if="active == 1">
            <el-form-item label="请填写专业技能">
              <editor v-model="form.major" :min-height="320"/>
            </el-form-item>
          </el-row>

          <el-row v-if="active == 2">
            <el-form-item label="教育经历">
              <editor v-model="form.eduExp" :min-height="320"/>
            </el-form-item>
          </el-row>
          <el-row v-if="active == 3">
            <el-form-item label="公开部分">
              <editor v-model="form.resumeDetail" :min-height="320"/>
            </el-form-item>
          </el-row>
        </el-form>

        <el-row v-if="active == 4">
          <el-row>
            <el-col :span="6">
              <h2 style="text-align: center">{{ form.userName }}</h2>
            </el-col>
            <el-col :span="16">
              <el-row>
                <h3> 意向行业：
                  <dict-tag :options="dict.type.com_type" :value="form.applyType"/>
                  | <span style="font-size: 16px">意向岗位：{{ form.applyJob }}</span></h3>
              </el-row>
            </el-col>
          </el-row>
          <el-divider><i class="el-icon-s-grid"></i> 基本信息</el-divider>
          <el-row>
            <el-col :span="9">
              <p>姓 名：{{ form.userName }}</p>
              <p>城 市：{{ form.userCity }}</p>
              <p>电 话：{{ form.userPhone }}</p>
              <p>邮 箱：{{ form.userEmail }}</p>
            </el-col>
            <el-col :span="9">
              <p>性 别：
                <dict-tag :options="dict.type.sys_user_sex" :value="form.userSex"/>
              </p>
              <p>学 历：
                <dict-tag :options="dict.type.education" :value="form.education"/>
              </p>
              <!--                <p>专 业：计算机科学与技术</p>-->
              <p>出生日期：<span>{{ parseTime(form.userBirth, '{y}-{m}-{d}') }}</span></p>
            </el-col>
            <el-col :span="6" style="text-align: center">
              <el-image
                style="width: 150px; height: 150px"
                :src="form.userImg"
              ></el-image>
            </el-col>
          </el-row>
          <el-row v-if=" form.major != '<p><br></p>' && form.major != '' && form.major != null">
            <el-divider><i class="el-icon-shopping-bag-1"></i> 专业技能</el-divider>
            <editor v-model="form.major" style="margin-top: 20px" :min-height="200" :readOnly="true" :toolbar="[]"/>
          </el-row>
          <el-row v-if="form.eduExp != '<p><br></p>' && form.eduExp != '' && form.eduExp != null">
            <el-divider><i class="el-icon-s-grid"></i> 教育经历</el-divider>
            <editor v-model="form.eduExp" style="margin-top: 20px" :min-height="200" :readOnly="true" :toolbar="[]"/>
          </el-row>
          <el-row v-if="form.resumeDetail != '<p><br></p>' && form.resumeDetail != '' && form.resumeDetail != null">
            <el-divider><i class="el-icon-folder-add"></i> 公开部分</el-divider>
            <editor v-model="form.resumeDetail" style="margin-top: 20px" :min-height="200" :readOnly="true"
                    :toolbar="[]"/>
          </el-row>
        </el-row>
      </div>
      <div slot="footer" class="dialog-footer">
        <div v-if="active==4">
          <el-button @click="formBack"><i class="el-icon-back"></i> 上一步</el-button>
          <el-button type="primary" @click="submitForm"><i class="el-icon-check"></i> 提 交</el-button>
          <el-button @click="cancel"><i class="el-icon-close"></i> 关 闭</el-button>
        </div>
        <div v-else>
          <el-button v-if="active>0" @click="formBack"><i class="el-icon-back"></i> 上一步</el-button>
          <el-button v-if="active==0" @click="formBack" disabled><i class="el-icon-back"></i> 上一步</el-button>
          <el-button type="primary" @click="formNext"><i class="el-icon-right"></i> 下一步</el-button>
        </div>
      </div>
    </el-dialog>


    <!-- 求职记录    -->
    <el-dialog title="求职进度" :visible.sync="recordOpen" width="1200px" append-to-body>
      <div style="height: 64vh;overflow: auto;padding: 10px">
        <el-card shadow="hover">
          <el-row :gutter="10">
            <el-col :span="4">
              <el-image style="width: 110px; height: 75px" :src="company.comImg"></el-image>
            </el-col>
            <!--职位消息 -->
            <el-col :span="5">
              <h3> {{ job.jobName }} </h3>
              <h3> {{ company.comName }} </h3>
            </el-col>
            <el-col :span="12">
              <h3 class="gray ">{{ job.jobSite }} | {{ job.expYear }} |
                <dict-tag :options="dict.type.education" :value="job.education"/>
              </h3>
              <h3 class="gray ">{{ job.jobSite }} | {{ job.expYear }} |
                <dict-tag :options="dict.type.com_type" :value="company.comType"/>
                |
                <dict-tag :options="dict.type.is_market" :value="company.isMarket"/>
              </h3>
            </el-col>
            <el-col :span="3">
              <h3 style="color: red"> {{ job.jobSalary }} </h3>
            </el-col>
          </el-row>

        </el-card>

        <el-card shadow="hover" style="margin-top: 10px;">
          <div style="height: 400px;">
            <el-steps direction="vertical" :active="6">
              <el-step title="投递/邀约" style="color: black">
                <div slot="description" style="color: black" v-if="record.type == 1">
                  <h3> {{ record.createTime }} 您投递了 {{ this.record.comName }} 公司的 {{ this.record.jobName }} 岗位</h3>
                </div>
                <div slot="description" style="color: black" v-if="record.type == 2 ">
                  <h3 v-if="record.resumeId == null">
                    {{ this.record.comName }} 公司的 {{ this.record.jobName }} 职位负责人在 {{ record.createTime }} 邀请您投递该职位 岗位
                    <el-button size="mini" type="danger" @click="resumeOpen = true">
                      投递简历
                    </el-button>
                  </h3>
                  <h3 v-else>
                    {{ this.record.comName }} 公司的 {{ this.record.jobName }} 职位负责人在{{ record.createTime }}
                    查看了您下面这份简历，很感兴趣，是否同意查看全部信息？
                    <span v-if="record.isAgree == 1" style="color: #1ab394"><i class="el-icon-success"></i> 您已同意！</span>
                    <span v-if="record.isAgree == 2">
                      <el-button size="mini" type="success" @click="isAgree(record,1)"><i class="el-icon-success"></i> 是 </el-button>
                      <el-button size="mini" type="danger" @click="isAgree(record,3)"><i
                        class="el-icon-error"></i> 否 </el-button>
                    </span>
                    <span v-if="record.isAgree == 3" style="color: red"><i class="el-icon-error"></i> 您已拒绝！</span>

                  </h3>
                </div>
              </el-step>
              <el-step title="投递简历">
                <div slot="description" style="color: black" v-if="record.resumeId != null">
                  <el-card shadow="hover">
                    <el-col :span="4">
                      <h3>姓名：{{ recordResume.userName }}</h3>
                    </el-col>
                    <el-col :span="7">
                      <h3>意向行业：
                        <dict-tag :options="dict.type.com_type" :value="recordResume.applyType"/>
                      </h3>
                    </el-col>
                    <el-col :span="7">
                      <h3>意向岗位：{{ recordResume.applyJob }}</h3>
                    </el-col>
                    <el-col :span="3">
                      <h3>性别：
                        <dict-tag :options="dict.type.sys_user_sex" :value="recordResume.userSex"/>
                      </h3>
                    </el-col>
                    <el-col :span="3">
                      <h3>
                        学 历：
                        <dict-tag :options="dict.type.education" :value="recordResume.education"/>
                      </h3>
                    </el-col>
                  </el-card>
                </div>
                <div slot="description" style="color: black" v-else>
                  <h3 style="color: grey">您未投递简历</h3>
                </div>
              </el-step>

              <el-step title="简历是否符合要求">
                <div slot="description">
                  <div v-if="record.isReq == null">
                    <h3 style="color: grey">
                      <i class="el-icon-more"></i>
                      暂无信息
                    </h3>
                  </div>
                  <div v-if="record.isReq == 1">
                    <h3 style="color: #1ab394">
                      <i class="el-icon-circle-check"></i>
                      恭喜你！您的简历符合该职位的要求，请留意改职位发起的面试邀请。
                    </h3>
                  </div>
                  <div v-if="record.isReq == 2">
                    <h3 style="color: red">
                      <i class="el-icon-circle-close"></i>
                      很遗憾！您的简历符合该不职位的要求哦，不要灰心，您可以继续去寻找类似的职位。
                    </h3>
                  </div>
                </div>
              </el-step>
              <el-step title="面试邀请">
                <div slot="description" style="color: black" v-if="record.applyAddress != null">
                  <h3> {{ this.record.comName }} 公司的 {{ this.record.jobName }} 职位负责人在
                    {{ this.record.updateTime }} 向您发起了面试邀请</h3>
                </div>
                <div slot="description" v-else>
                  <h3 style="color: grey">暂无信息</h3>
                </div>
              </el-step>
              <el-step title="面试时间、地点">
                <div slot="description" style="color: black" v-if="record.applyAddress != null">
                  <el-row gutter="10">

                    <h3>
                      <i class="el-icon-map-location"></i> 面试地点：{{ record.applyAddress }}
                      <i style="margin-left: 15px" class="el-icon-watch"></i> 面试时间：{{ record.applyTime }}
                      <i style="margin-left: 15px" class="el-icon-user-solid"></i> 联系人：{{ record.createBy }}
                      <i style="margin-left: 15px" class="el-icon-phone"></i> 联系电话：{{ record.updateBy }}
                    </h3>
                  </el-row>

                </div>
                <div slot="description" style="color: black" v-else>
                  <h3 style="color: grey">暂无信息</h3>
                </div>
              </el-step>
            </el-steps>
          </div>
        </el-card>
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
import {getResume, delResume, addResume, updateResume, listResumeById} from "@/api/apply/resume";
import {getRecord, listRecord, updateRecord} from "@/api/apply/record";
import {getApplyJob} from "@/api/company/applyJob";
import {getNowDate} from "@/utils/ruoyi";
import {addMsg} from "@/api/apply/msg";

export default {
  name: "Resume",
  dicts: ['com_type', 'sys_user_sex', 'education', 'com_scale', 'is_market',],
  data() {
    return {

      loading: false,// 遮罩层
      ids: [],  // 选中数组
      single: true, // 非单个禁用
      multiple: true,  // 非多个禁用
      showSearch: true,  // 显示搜索条件
      activeName: 'first',

      resumeList: [],   // resume表格数据
      title: "", // 弹出层标题
      active: 0,
      open: false,  // 是否显示弹出层
      // 表单参数
      form: {
        resumeId: null,
        userName: null,
        userId: null,
        userPhone: null,
        resumeDetail: null,
        userEmail: null,
        userCity: null,
        education: null,
        createBy: null,
        createTime: null,
        updateTime: null,
        updateBy: null,
        remark: null,
        userSex: null,
        userBirth: null,
        applyJob: null,
        applyType: null,
        resumeFile: null,
        major: null,
        eduExp: null,
        userImg: null
      },

      recordList: [],
      recordTotal: 0,   // 总条数
      recordQueryParams: {
        pageNum: 1,
        pageSize: 10,
        applyId: this.$store.state.user.userId,
        comName: null,

      },
      resumeOpen: false,

      record: {},
      recordOpen: false,
      recordResume: {},

      job: {},
      company: {},

      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getRecordList();
    this.getListById();
  },
  methods: {
    //是否同意
    isAgree(record, state) {
      let param = {recordId: record.recordId, isAgree: state}
      updateRecord(param).then(res => {
        if (res.code == 200) {
          this.record.isAgree = state;
          this.addUserMsg(record, state)
        }
      })
    },

    addUserMsg(record, state) {
      getApplyJob(record.jobId).then(res => {
        if (res.code == 200) {
          let job = res.data;
          let msg;
          if (state == 1) {
            msg = "求职者在" + getNowDate() + "同意了您在" + record.createTime + "向其发出的查看简历请求！关联的职位信息：" + job.company.comName + "的" + job.jobName + '职位，您现在可以查看其简历详情了！';
          }
          if (state == 3) {
            msg = "求职者在" + getNowDate() + "拒绝了您在" + record.createTime + "向其发出的查看简历请求！关联的职位信息：" + job.company.comName + "的" + job.jobName + '职位。';
          }


          let param = {
            userId: this.job.userId,
            msgContent: msg,
            isRead: 2,
          }
          addMsg(param);
        }
      })

    },


    //提交投递记录
    commitResume(resume) {
      let params = {
        recordId: this.record.recordId,
        resumeId: resume.resumeId,
      }
      updateRecord(params).then(
        res => {
          this.$modal.msgSuccess(res.msg);
          getRecord(this.record.recordId).then(res => {
            this.record = res.data;
            if (this.record.resumeId != null) {
              getResume(this.record.resumeId).then(res => {
                this.recordResume = res.data;
                this.resumeOpen = false;
              })
            }
          })
          loading.close();
        }
      )

    },

    //查看投递记录
    getRecord(record) {
      this.recordOpen = true;
      getRecord(record.recordId).then(res => {
        this.record = res.data;
        if (this.record.resumeId != null) {
          getResume(this.record.resumeId).then(res => {
            this.recordResume = res.data;
          })
        }
      })
      getApplyJob(record.jobId).then(
        res => {
          this.job = res.data;
          this.company = res.data.company;
        }
      )

    },

    gotoJob(record) {
      this.$router.push({path: '/job', query: {jobId: record.jobId}});
    },

    handleQuery() {
      this.recordQueryParams.pageNum = 1;
      this.getRecordList();
    },

    getRecordList() {
      this.loading = true;
      listRecord(this.recordQueryParams).then(response => {
        this.recordList = response.rows;
        this.recordTotal = response.total;
        this.loading = false;
      });
    },

    formBack() {
      this.active--;
      if (this.active < 0) {
        this.active = 0;
      }
    },

    formNext() {
      this.active++;
      if (this.active > 4) {
        this.active = 4;
      }
    },


    //** 查询resume列表 */
    getListById() {
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        resumeId: null,
        userName: null,
        userId: null,
        userPhone: null,
        resumeDetail: null,
        userEmail: null,
        userCity: null,
        education: null,
        createBy: null,
        createTime: null,
        updateTime: null,
        updateBy: null,
        remark: null,
        userSex: null,
        userBirth: null,
        applyJob: null,
        applyType: null,
        resumeFile: null,
        major: null,
        eduExp: null,
        userImg: null
      };
      this.resetForm("form");
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    //** 新增按钮操作
    handleAdd() {
      if (this.resumeList.length >= 4) {
        this.$modal.msgWarning("每个人最多添加四份简历哦！");
        return;
      }

      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      this.reset();
      this.open = true;
      this.title = "添加我的简历";
      loading.close();
    },
    /** 修改按钮操作 */
    handleUpdate(resume) {
      this.active = 0;
      this.reset();
      const resumeId = resume.resumeId;
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      getResume(resumeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改简历";
        loading.close();
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        const loading = this.$loading({
          lock: true,
          text: 'Loading',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        if (valid) {
          if (this.form.resumeId != null) {
            updateResume(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getListById();
            });
          } else {
            addResume(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getListById();
            });
          }
        }
        loading.close()
      });
    },

    /** 删除按钮操作 */
    handleDelete(resume) {
      const resumeIds = resume.resumeId;
      this.$modal.confirm('是否确认删除此份简历的数据项？').then(function () {
        return delResume(resumeIds);
      }).then(() => {
        this.getListById();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },

  }
};
</script>


<style scoped>


.elRow {
  padding: 10px 0 0 0;
  margin-top: 10px;
}

.input-with-select .el-input-group__prepend {
  background-color: #1ab394;
}


.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

.jobHover:hover {
  cursor: pointer;
  color: #1ab394;
}

.linkHover:hover {
  /* background-color: #E7FAF0;*/
}

.select_top {
  width: 130px;
  background-color: #E7FAF0;
  color: #1ab394;
}

.top_hot_job {
  font-size: 14px;
  margin-left: 20px
}

.gray {
  color: gray;
}

.top_hot_job_item {
  color: #1ab394;
}

.item:hover {
  cursor: pointer;
}

.border_none {
  border: 0;
}

.job_need {
  font-size: 14px;
  margin: 5px 0 0 5px
}

.job_salary {
  float: right;
  color: #FD7240
}

.margin_top_10 {
  margin-top: 10px;
}

.margin_left_10 {
  margin-left: 10px;
}

.color_1ab394 {
  color: #1ab394;
}


</style>
