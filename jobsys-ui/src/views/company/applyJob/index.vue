<template xmlns:el-col="http://www.w3.org/1999/html">
  <div class="app-container">

    <!--工商信息 与公司信息    -->
    <el-row :gutter="10">
      <el-col :span="12">
        <el-collapse accordion>
          <el-collapse-item>
            <template slot="title">
              <h3 style="color: #1ab394"><i class="el-icon-view"></i>点击查看{{ comInfo.comName }}的详细信息</h3>
            </template>
            <el-card shadow="hover">
              <el-row>
                <el-col :span="6">
                  <el-image style="width: 100px; height: 100px"
                            :src="comInfo.comImg"
                            fit="contain"
                  >
                    <div slot="error" class="image-slot">
                      <i class="el-icon-picture-outline"></i>
                    </div>
                  </el-image>
                </el-col>
                <el-col :span="18">
                  <el-row>
                    <el-col :span="12">
                      <el-row>公司名称：{{ comInfo.comName }}</el-row>
                      <el-row>融资状态：
                        <dict-tag :options="dict.type.is_market" :value="comInfo.isMarket"/>
                      </el-row>
                      <el-row>联系电话：{{ comInfo.comTel }}</el-row>
                    </el-col>
                    <el-col :span="12">
                      <el-row>所属行业：
                        <dict-tag :options="dict.type.com_type" :value="comInfo.comType"/>
                      </el-row>
                      <el-row>公司规模：
                        <dict-tag :options="dict.type.com_scale" :value="comInfo.comScale"/>
                      </el-row>
                    </el-col>
                  </el-row>
                  <el-row>公司地址：{{ comInfo.address }}</el-row>
                </el-col>
              </el-row>

              <el-row>
                <h4>公司简介</h4>
                <editor :value="comInfo.comInfo" :min-height="191" :readOnly="true"
                        :toolbar="[[]]"/>
              </el-row>

            </el-card>
          </el-collapse-item>
        </el-collapse>
      </el-col>

      <el-col :span="12">
        <el-collapse accordion>
          <el-collapse-item>
            <template slot="title">
              <h3 style="color: #1ab394"><i class="el-icon-s-check"></i>点击查看{{ comInfo.comName }}公司的工商信息</h3>
            </template>
            <el-card shadow="hover">
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
                    <dict-tag :options="dict.type.bu_type" :value="businessInfo.comState"/>
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
            </el-card>
            <el-card shadow="hover">
              <el-steps :active="businessInfo.checkState" finish-status="success" align-center>
                <el-step title="提交信息"/>
                <el-step title="工作人员审核"/>
                <el-step title="审核通过"/>
              </el-steps>
            </el-card>
          </el-collapse-item>
        </el-collapse>
      </el-col>

    </el-row>


    <!-- 搜索栏   -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="80px"
             style="margin-top: 20px">

      <el-form-item label="职位状态" prop="jobState">
        <el-select v-model="queryParams.jobState" placeholder="请选择状态" clearable size="small">
          <el-option v-for="dict in dict.type.apply_job_state" :key="dict.value" :label="dict.label"
                     :value="dict.value"/>
        </el-select>
      </el-form-item>

      <el-form-item label="职位名称" prop="jobName">
        <el-input v-model="queryParams.jobName" placeholder="请输入名称" clearable size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>

      <el-form-item label="学历要求" prop="education">
        <el-select v-model="queryParams.education" placeholder="请选择学历要求" clearable size="small">
          <el-option v-for="dict in dict.type.education" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>

      <el-form-item label="工作经验" prop="expYear">
        <el-select v-model="form.expYear" placeholder="请工作年限要求">
          <el-option v-for="num in 50"
                     :key="num"
                     :label="num + '年'"
                     :value="num"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 可操作按钮   -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-circle-plus" @click="handleAdd"
                   v-hasPermi="['company:applyJob:add']">发布职位
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" @click="handleExport"
                   v-hasPermi="['company:applyJob:export']">导出
        </el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <router-link :to="{path:'/company/company'}">-->
<!--          <el-button plain icon="el-icon-back">返回-->
<!--          </el-button>-->
<!--        </router-link>-->
<!--      </el-col>-->


      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!--列表-->
    <el-table v-loading="loading" :data="applyJobList" @selection-change="handleSelectionChange" border>
      <div slot="empty">
        <el-empty description="暂无任何职位信息"/>
      </div>
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="主键" align="center" prop="jobId" v-if="false"/>
      <el-table-column label="名称" align="center" prop="jobName" width="300"/>
      <el-table-column label="工作类型" align="center" prop="jobType" width="150">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.com_type" :value="scope.row.jobType"/>
        </template>
      </el-table-column>
      <el-table-column label="工资" align="center" prop="jobSalary" width="120"/>
      <el-table-column label="任职要求" align="center" prop="jobReq" width="120">
        <template slot-scope="scope">
          <el-tooltip effect="light" content="Bottom center">
            <template #content>
              <div style="width: 300px">
                <editor :value="scope.row.jobReq" :min-height="200" :readOnly="true"
                        :toolbar="[[]]"/>
              </div>
            </template>
            <span style="font-size: 12px;color: #1ab394">要求详情</span>
          </el-tooltip>

        </template>
      </el-table-column>

      <el-table-column label="工作详情" align="center" prop="jobDetail" width="120">
        <template slot-scope="scope">
          <el-tooltip effect="light" content="Bottom center">

            <template #content>
              <div style="width: 300px">
                <editor :value="scope.row.jobDetail" :min-height="200" :readOnly="true"
                        :toolbar="[]"/>
              </div>
            </template>
            <span style="font-size: 12px;color: #1ab394">工作详情</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="学历要求" align="center" prop="education" width="120">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.education" :value="scope.row.education"/>
        </template>
      </el-table-column>
      <el-table-column label="工作经验" align="center" prop="expYear" width="120"/>
      <el-table-column label="工作城市" align="center" prop="jobSite" width="200"/>

      <el-table-column label="职位状态" align="center" prop="jobState" width="120">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.apply_job_state" :value="scope.row.jobState"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="creatTime" width="180"/>
      <el-table-column label="创建人账户" align="center" prop="creatBy" width="150"/>
      <el-table-column label="招聘处理" align="center" width="250" fixed="right">
        <template slot-scope="scope">
          <el-row v-if="scope.row.jobState === 3 ">
            <span style="color: grey;font-size: 12px"> 职位信息异常</span>
          </el-row>
          <el-row v-else>
            <el-button size="small" type="success" plain icon="el-icon-zoom-in" @click="findApply(scope.row)">
              寻找人才
            </el-button>
            <el-button size="small" type="warning" plain icon="el-icon-s-custom" @click="getRecord(scope.row)">
              投递记录
            </el-button>
          </el-row>
        </template>
      </el-table-column>
      <el-table-column label="职位操作" align="center" width="200" fixed="right">
        <template slot-scope="scope">
          <el-row v-if="scope.row.jobState === 3 ">
            <span style="color: grey;font-size: 12px"> 职位信息异常</span>
          </el-row>
          <el-row v-else>
            <el-button size="small" type="primary" plain icon="el-icon-edit" @click="handleUpdate(scope.row)"
                       v-hasPermi="['company:applyJob:edit']">修改
            </el-button>
            <el-button v-if="scope.row.jobState === 1" size="small" type="info" plain icon="el-icon-remove"
                       @click="handleDelete(scope.row,2,true)" v-hasPermi="['company:applyJob:remove']">停招
            </el-button>
            <el-button v-if="scope.row.jobState === 2" size="small" type="success" plain icon="el-icon-remove"
                       @click="handleDelete(scope.row,1,false)" v-hasPermi="['company:applyJob:remove']">继招
            </el-button>
          </el-row>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页插件   -->
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改applyJob对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
      <div class="el-dialog-div">
        <div style="padding-bottom: 15px">
          <el-steps :active="addStep" simple>
            <el-step title="填写基本信息" icon="el-icon-edit"></el-step>
            <el-step title="填写任职要求" icon="el-icon-suitcase"></el-step>
            <el-step title="填写工作详情" icon="el-icon-s-order"></el-step>
            <el-step title="核对招聘职位" icon="el-icon-success"></el-step>
          </el-steps>
        </div>


        <el-form ref="form" :model="form" :rules="rules" label-width="120px">

          <div v-if="addStep==0" style="padding-top: 50px">
            <el-form-item label="工作名称" prop="jobName">
              <el-input v-model="form.jobName" placeholder="请填写职位名称"/>
            </el-form-item>

            <el-form-item label="职位类型" prop="jobType">
              <el-select v-model="form.jobType" placeholder="请选择职位类型">
                <el-option
                  v-for="dict in dict.type.com_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="薪资范围" prop="lowSalary">
              <el-input-number prop="lowSalary" v-model="form.lowSalary" :precision="1" size="small" :step="0.5"
                               :min="0"
                               :max="200"/>
              <span style="margin: 0 10px;font-size: 14px;">~</span>
              <el-input-number prop="heightSalary" v-model="form.heightSalary" size="small" :precision="1" :step="0.5"
                               :min="0"
                               :max="200"/>
              <span style="margin: 0 10px;font-size: 14px;color: red">K</span>
            </el-form-item>

            <el-form-item label="学历要求" prop="education">
              <el-select v-model="form.education" placeholder="请选择学历要求">
                <el-option
                  v-for="dict in dict.type.education"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="工作经验" prop="expYear">
              <el-select v-model="form.expYear" placeholder="请工作年限要求">
                <el-option v-for="num in 50"
                           :key="num"
                           :label="num + '年'"
                           :value="num"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="工作城市">
              <el-select v-model="form.province" @change="choseProvince" placeholder="省级地区"
                         style="width: 152px; margin-right: 5px">
                <el-option v-for="(item, $index) in province" :key="$index" :label="item.value" :value="item.id">
                </el-option>
              </el-select>
              <el-select style="width: 190px; margin-right: 5px" v-model="form.property" @change="choseCity"
                         placeholder="市级地区">
                <el-option v-for="(item, $index) in city" :key="$index" :label="item.value"
                           :value="item.id"></el-option>
              </el-select>
              <el-select style="width: 170px" v-model="bname" @change="choseBlock" placeholder="区级地区">
                <el-option v-for="(item, $index) in block" :key="$index" :label="item.value" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </div>

          <div v-if="addStep==1" style="padding-top: 50px">
            <el-form-item label="任职要求：">
              <editor v-model="form.jobReq" :min-height="300"/>
            </el-form-item>
          </div>

          <div v-if="addStep==2" style="padding-top: 50px">
            <el-form-item label="工作详情：">
              <editor v-model="form.jobDetail" :min-height="300"/>
            </el-form-item>
          </div>
        </el-form>

        <div v-if="addStep == 3">
          <el-card>
            <el-card>
              <el-row style="padding: 10px">
                <el-col :span="4" class="detail-left">职位名称:</el-col>
                <el-col :span="10" class="detail-right">{{ form.jobName }}</el-col>
                <el-col :span="4" class="detail-left">工作地点:</el-col>
                <el-col :span="6" class="detail-right">{{ pname }}{{ cname }}{{ bname }}</el-col>
              </el-row>
              <el-row style="padding: 10px">
                <el-col :span="4" class="detail-left">薪资范围:</el-col>
                <el-col :span="4" class="detail-right">{{ form.lowSalary }} - {{ form.heightSalary }} <span
                  style="color: red">K</span></el-col>
                <el-col :span="4" class="detail-left">学历要求:</el-col>
                <el-col :span="4" class="detail-right">
                  <dict-tag :options="dict.type.education" :value="form.education"/>
                </el-col>
                <el-col :span="4" class="detail-left">工作经验:</el-col>
                <el-col :span="4" class="detail-right">{{ form.expYear }}年</el-col>
              </el-row>
            </el-card>

            <el-card>
              <editor style="margin-top: 20px" v-model="form.jobReq" :min-height="200" :readOnly="true"
                      :toolbar="[]"/>
            </el-card>
            <el-card>
              <editor style="margin-top: 20px" v-model="form.jobDetail" :min-height="200" :readOnly="true"
                      :toolbar="[]"/>
            </el-card>
          </el-card>
        </div>

      </div>


      <div slot="footer" class="dialog-footer">
        <div v-if="addStep == 3">
          <el-button type="primary" plain @click="submitForm" icon="el-icon-check">确 定</el-button>
          <el-button @click="addStep--" icon="el-icon-back">返 回</el-button>
        </div>
        <div v-else>
          <el-button type="info" plain @click="addJobBackStep" v-if="addStep==0" disabled icon="el-icon-back">上一步
          </el-button>
          <el-button type="info" plain @click="addJobBackStep" v-else icon="el-icon-back">上一步</el-button>
          <el-button type="success" plain @click="addJobNextStep" icon="el-icon-right">下一步</el-button>
        </div>

      </div>
    </el-dialog>


    <!-- 寻找人   -->
    <el-dialog :title="findTitle" :visible.sync="findOpen" width="1450px" append-to-body>
      <div class="el-dialog-div">
        <div style="padding-bottom: 15px">
          <el-card shadow="never" style="padding-bottom: 15px">
            <el-col :span="24" v-for="resume in resumeList">
              <el-card shadow="hover" style="margin-top: 10px">
                <el-row>
                  <el-col :span="3">
                    <el-image style="width: 150px; height: 150px" :src="resume.userImg"/>
                    <el-button type="success" size="mini" style="width: 150px;" @click="addRecord(resume)">邀请投递简历
                    </el-button>
                  </el-col>
                  <el-col :span="4">
                    <p>姓名：{{ resume.userName }}</p>
                    <p>性别：
                      <dict-tag :options="dict.type.sys_user_sex" :value="resume.userSex"/>
                    </p>
                    <p>学历：
                      <dict-tag :options="dict.type.education" :value="resume.education"/>
                    </p>
                    <p>意向行业：
                      <dict-tag :options="dict.type.com_type" :value="resume.applyType"/>
                    </p>
                    <p>意向岗位：{{ resume.applyJob }}</p>
                  </el-col>
                  <el-col :span="17">
                    <editor v-model="resume.resumeDetail" :min-height="150" :readOnly="true"
                            :toolbar="[]"/>
                  </el-col>
                </el-row>
              </el-card>
            </el-col>
          </el-card>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <pagination v-show="findTotal>0" :total="findTotal" :page.sync="findParam.pageNum"
                    :limit.sync="findParam.pageSize"
                    @pagination="getResumeList"/>
      </div>
    </el-dialog>

    <!--投递记录    -->
    <el-dialog :title="findTitle" :visible.sync="recordOpen" width="1550px" append-to-body>
      <div class="el-dialog-div">
        <el-table v-loading="loading" :data="recordList" border>
          <el-table-column label="时间" align="center" prop="createTime" width="180" show-overflow-tooltip/>
          <el-table-column label="记录类型" align="center" prop="type" width="120">
            <template slot-scope="scope">
              <div v-if="scope.row.type == 1">
                <span> <i style="color: #2b2f3a;" class="el-icon-medal-1"></i>主动投递</span>
              </div>
              <div v-if="scope.row.type == 2">
                <span><i style="color: #1ab394" class="el-icon-medal"></i> 邀约投递</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="简历" align="center" prop="resumeId" width="180">
            <template slot-scope="scope">
              <span class="link-type" @click="getResume(scope.row)"><i style="color: #00afff;" class="el-icon-view"></i> 查看简历
                <span v-if="scope.row.isAgree == 1">(全部内容)</span>
                <span v-else>(部分内容)</span>
              </span>
            </template>
          </el-table-column>
          <el-table-column label="简历是否通过" align="center" prop="isReq" width="120">
            <template slot-scope="scope">
              <div v-if="scope.row.isReq == 1">
                <i class="el-icon-success" style="color: #1ab394"></i> 是
              </div>
              <div v-if="scope.row.isReq == 2">
                <i class="el-icon-error" style="color:red;"></i> 否
              </div>
              <div v-if="scope.row.isReq == null">
                <i class="el-icon-info" style="color: #00afff"></i> 未处理
              </div>
            </template>
          </el-table-column>
          <el-table-column label="面试地点" align="center" prop="applyAddress" show-overflow-tooltip>
            <template slot-scope="scope">
              <div v-if="scope.row.applyAddress == null">暂无信息</div>
              <div v-else>{{ scope.row.applyAddress }}</div>
            </template>
          </el-table-column>
          <el-table-column label="面试时间" align="center" prop="applyTime" width="160" show-overflow-tooltip>
            <template slot-scope="scope">
              <div v-if="scope.row.applyAddress == null">暂无信息</div>
              <div v-else>{{ scope.row.applyTime }}</div>
            </template>
          </el-table-column>
          <el-table-column label="联系人" align="center" prop="applyTime" width="100" show-overflow-tooltip>
            <template slot-scope="scope">
              <div v-if="scope.row.applyAddress == null">暂无信息</div>
              <div v-else>{{ scope.row.createBy }}</div>
            </template>
          </el-table-column>
          <el-table-column label="联系电话" align="center" prop="applyTime" width="130" show-overflow-tooltip>
            <template slot-scope="scope">
              <div v-if="scope.row.applyAddress == null">暂无信息</div>
              <div v-else>{{ scope.row.updateBy }}</div>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="200">
            <template slot-scope="scope">
              <div v-if="scope.row.isReq == 2">
                <el-button type="danger" size="mini" disabled><i class="el-icon-error"></i> 简历不符合要求</el-button>
              </div>
              <div v-else>
                <div v-if="scope.row.isReq == null">
                  <el-button type="success" size="mini" @click="isReq(scope.row,1)"><i class="el-icon-success"></i> 符合
                  </el-button>
                  <el-button type="danger" size="mini" @click="isReq(scope.row,2)"><i class="el-icon-error"></i> 不符合
                  </el-button>
                </div>

                <div v-if="scope.row.isReq == 1 && scope.row.applyAddress == null">
                  <el-button type="primary" size="mini" @click="applyAdd(scope.row)">
                    <i class="el-icon-s-custom"></i>邀请面试
                  </el-button>
                </div>
                <div v-if="scope.row.isReq == 1 && scope.row.applyAddress != null">
                  <el-button type="warning" size="mini"><i class="el-icon-success"></i> 处理完毕</el-button>
                </div>


              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <pagination v-show="recordTotal>0" :total="recordTotal" :page.sync="recordParam.pageNum"
                    :limit.sync="recordParam.pageSize"
                    @pagination="getRecordList"/>
      </div>
    </el-dialog>


    <!-- 查看简历详情   -->
    <el-dialog title="简历详情" :visible.sync="resumeOpen" width="1000px" append-to-body>
      <div class="el-dialog-div">
        <div v-if="resumeIsAgree">
          <el-card>
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
            </el-row>
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
                <p>出生日期：<span>{{ resume.userBirth }}</span></p>
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
          </el-card>
        </div>
        <div v-else>
          <h3>该求职者还未同意您查看简历全部信息哦，您只能查看部分信息！</h3>
          <el-card shadow="hover" style="margin-top: 10px">
            <el-row>
              <el-col :span="6" offset="1">
                <el-image style="width: 150px; height: 150px" :src="resume.userImg"/>
              </el-col>
              <el-col :span="12">
                <p>姓名：{{ resume.userName }}</p>
                <p>性别：
                  <dict-tag :options="dict.type.sys_user_sex" :value="resume.userSex"/>
                </p>
                <p>学历：
                  <dict-tag :options="dict.type.education" :value="resume.education"/>
                </p>
                <p>意向行业：
                  <dict-tag :options="dict.type.com_type" :value="resume.applyType"/>
                </p>
                <p>意向岗位：{{ resume.applyJob }}</p>
              </el-col>
              <el-col :span="24">
                <editor v-model="resume.resumeDetail" :min-height="150" :readOnly="true"
                        :toolbar="[]"/>
              </el-col>
            </el-row>
          </el-card>
        </div>
      </div>
    </el-dialog>


    <el-dialog title="面试邀请" :visible.sync="applyOpen" width="500px" append-to-body>
      <div>
        <el-row>

          <h3><i class="el-icon-location"></i>面试地点：</h3>
          <el-input v-model="applyForm.applyAddress" placeholder="请输入面试地点"></el-input>
        </el-row>
        <el-row>
          <h3><i class="el-icon-watch"></i> 面试时间：</h3>
          <el-date-picker v-model="applyForm.applyTime" value-format="yyyy-MM-dd HH:mm:ss" type="datetime"
                          placeholder="选择日期时间">
          </el-date-picker>
        </el-row>
        <el-row>
          <h3><i class="el-icon-user-solid"></i>联系人：</h3>
          <el-input v-model="applyForm.createBy" placeholder="请输入联系人名称"></el-input>
        </el-row>
        <el-row>
          <h3><i class="el-icon-phone"></i>联系电话：</h3>
          <el-input v-model="applyForm.updateBy" placeholder="请输入联系电话"></el-input>
        </el-row>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addApply"> 发出邀请</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import {addApplyJob, changeState, getApplyJob, listApplyJob, updateApplyJob} from "@/api/company/applyJob";
import ChineseDistricts from "@/plugins/cityList";
import {getCompany} from "@/api/company/company";
import {listBusiness} from "@/api/company/business";
import {getResume, listResume} from "@/api/apply/resume";
import {applyAddByJob, listRecord, updateRecord} from "@/api/apply/record";
import {getNowDate} from "@/utils/ruoyi";
import {addMsg} from "@/api/apply/msg";

export default {
  name: "ApplyJob",
  dicts: ['apply_job_state', 'education', 'business_com_type', 'com_type', 'is_market', 'com_scale', 'sys_user_sex','bu_type'],
  data() {
    return {
      //地市选择框
      ChineseDistricts: ChineseDistricts,
      province: [],
      shi1: [],
      qu1: [],
      city: [],
      block: [],
      pname: "", //省的名字
      cname: "", //市的名字
      bname: "", //区的名字

      //页面数据
      comId: this.$route.query.comId || this.$store.state.user.comId,
      loading: true,// 遮罩层
      ids: [],  // 选中数组
      single: true,    // 非单个禁用
      multiple: true, // 非多个禁用
      showSearch: true, // 显示搜索条件
      total: 0,   // 总条数
      applyJobList: [],  // applyJob表格数据
      title: "",    // 弹出层标题
      open: false,  // 是否显示弹出层
      addStep: 0,  // 表单填写步骤
      comInfo: {}, // 公司信息
      businessInfo: {}, // 公司工商信息

      findOpen: false,
      findTotal: 0,
      findParam: {
        pageNum: 1,
        pageSize: 10,
        applyType: null,
      },
      resumeList: [],
      findTitle: "",


      resumeOpen: false,
      resumeIsAgree: false,
      resume: {major: null, eduExp: null, resumeDetail: null, resumeFile: null},


      applyOpen: false,
      applyForm: {
        recordId: null,
        applyAddress: null,
        applyTime: null,
        updateBy: null,
        createBy: null,
      },


      recordOpen: false,
      recordList: [],
      recordTotal: 0,
      recordParam: {
        pageNum: 1,
        pageSize: 10,
        jobId: null,
        jobName: null,
      },

      record: {},


      recordForm: {
        jobId: null,
        jobName: null,
        applyId: null,
        takeId: null,
        type: 2,
        isAgree: 2,
        comId: null,
        comName: null,
        resumeId: null,
      },

      //  职位查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobState: null,
        jobName: null,
        education: null,
        expYear: null,
        jobSite: null,
        comId: this.$route.query.comId || this.$store.state.user.comId,
      },
      // 职位表单参数
      form: {
        jobId: null,
        jobState: null,
        jobName: null,
        jobSalary: null,
        jobReq: null,
        jobDetail: null,
        education: null,
        expYear: null,
        jobSite: null,
        jobType: null,
        lowSalary: 3,
        heightSalary: 5,
        comId: this.$route.query.comId || this.$store.state.user.comId,
      },
      // 职位表单校验
      rules: {
        jobState: [
          {required: true, message: "状态不能为空", trigger: "change"}
        ],
        jobName: [
          {required: true, message: "名称不能为空", trigger: "blur"}
        ],
        jobSalary: [
          {required: true, message: "工资不能为空", trigger: "blur"}
        ],
        jobReq: [
          {required: true, message: "任职要求不能为空", trigger: "blur"}
        ],
        jobDetail: [
          {required: true, message: "工作详情不能为空", trigger: "blur"}
        ],
        education: [
          {required: true, message: "学历要求不能为空", trigger: "change"}
        ],
        jobType: [
          {required: true, message: "工作类型不能为空", trigger: "change"}
        ],
        expYear: [
          {required: true, message: "工作经验不能为空", trigger: "blur"}
        ],
        jobSite: [
          {required: true, message: "工作城市不能为空", trigger: "blur"}
        ],
        lowSalary: [
          {required: true, message: "请填写工作薪水范围", trigger: "blur"}
        ],
        heightSalary: [
          {required: true, message: "请填写工作薪水范围", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    let comId = this.$route.query.comId || this.$store.state.user.comId;
    if (comId == null || comId === '') {
      this.$confirm('获取参数缺失', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      }).then(() => {
        location.href = '/company/company';
      }).catch(() => {
      });
    }
    this.getCityData();
    this.getList();
    this.getComInfo();
    this.getBusinessInfo();
  },
  methods: {
    applyAdd(row) {
      this.applyOpen = true;
      this.applyForm.recordId = row.recordId;
      this.applyForm.applyTime = null;
      this.applyForm.applyAddress = null;
      this.applyForm.updateBy = null;
      this.applyForm.ceateBy = null;
      this.record = row;
    },

    addApply() {
      if (this.applyForm.applyAddress == null) {
        this.$modal.msgError("面试地点不能为空！");
        return
      }
      if (this.applyForm.applyTime == null) {
        this.$modal.msgError("面试时间不能为空！");
        return
      }
      if (this.applyForm.createBy == null) {
        this.$modal.msgError("联系人名称不能为空！");
        return
      }
      if (this.applyForm.updateBy == null) {
        this.$modal.msgError("联系电话不能为空！");
        return
      }
      updateRecord(this.applyForm).then(res => {
        this.$modal.msgSuccess("操作成功！");
        this.applyOpen = false;
        if (res.code == 200) {
          this.addApplyMsg();
        }
        this.getRecordList();
      })
    },


    addApplyMsg() {
      getApplyJob(this.record.jobId).then(res => {
        if (res.code == 200) {
          let job = res.data;
          let param = {
            userId: this.record.applyId,
            msgContent: job.company.comName + "的" + job.jobName + '职位的招聘招聘负责人,在' + getNowDate() + "向您发起了面试邀请！面试地点：" + this.applyForm.applyAddress + "  面试时间：" + this.applyForm.applyTime + "  联系人：" + this.applyForm.createBy + "  联系电话：" + this.applyForm.updateBy + " 请您留意面试信息！祝您通过面试！",
            isRead: 2,
          }
          this.addCommitMsg(param)
        }
      })
    },


    isReq(row, isReq) {
      let param = {
        recordId: row.recordId,
        isReq: isReq,
      }
      updateRecord(param).then(res => {
        if (res.code == 200) {
          this.addUserMsg(row, isReq);
        }
        this.$modal.msgSuccess("操作成功！");
        this.getRecordList();
      })

    },


    addUserMsg(record, state) {
      getApplyJob(record.jobId).then(res => {
        if (res.code == 200) {
          let job = res.data;
          let msg;
          if (state == 1) {
            msg = "恭喜您，在" + getNowDate() + "经过招聘方的简历筛选之后，您的符合通过了" + job.company.comName + "的" + job.jobName + '职位的招聘要求。后续招聘方将会向您发起面试邀请，确定面试时间和地点，请您留意系统通知！';
          }
          if (state == 2) {
            msg = "很遗憾，在" + getNowDate() + "经过招聘方的简历筛选之后，您的简历未能通过了" + job.company.comName + "的" + job.jobName + '职位的招聘要求。不要灰心，您可以继续寻找相关的职位，加油！';
          }
          let param = {
            userId: record.applyId,
            msgContent: msg,
            isRead: 2,
          }
          this.addCommitMsg(param)
        }
      })
    },


    addCommitMsg(param) {
      addMsg(param);
    },


    getResume(row) {
      this.resumeOpen = true;
      this.resumeIsAgree = row.isAgree == 1;
      getResume(row.resumeId).then(res => {
        this.resume = res.data;
      })

    },


    getRecord(row) {
      this.recordOpen = true;
      this.recordParam.pageNum = 1;
      this.recordParam.pageSize = 10;
      this.recordParam.jobId = row.jobId;
      this.findTitle = "处理职位简历投递记录，招聘职位名称：" + row.jobName;
      this.getRecordList();

    },


    getRecordList() {
      listRecord(this.recordParam).then(res => {
        this.recordList = res.rows;
        this.recordTotal = res.total;

      })

    },


    addRecord(resume) {
      this.recordForm.resumeId = resume.resumeId;
      this.recordForm.applyId = resume.userId;
      applyAddByJob(this.recordForm).then(res => {
        this.$modal.msgSuccess(res.msg);
      })
    },


    //寻找人才
    findApply(row) {
      this.findOpen = true;
      this.findParam.applyType = row.jobType;
      this.findParam.pageNum = 1;
      this.findParam.pageSize = 10;
      this.getResumeList();
      this.findTitle = "寻找与职位相关的人才，招聘职位名称：" + row.jobName;
      this.recordForm.jobId = row.jobId;
      this.recordForm.jobName = row.jobName;
      this.recordForm.comId = this.comInfo.comId;
      this.recordForm.comName = this.comInfo.comName;
      this.recordForm.takeId = row.userId;
    },
    getResumeList() {
      listResume(this.findParam).then(
        res => {
          this.findTotal = res.total;
          this.resumeList = res.rows;
        }
      )
    },

    //获取公司信息
    getComInfo() {
      getCompany(this.comId).then(
        response => {
          this.comInfo = response.data;
        })

    },

    //获取公司工商信息
    getBusinessInfo() {
      listBusiness({comId: this.comId}).then(response => {
        if (response == null || response.rows.length == 0) {
          return;
        }
        this.businessInfo = response.rows[0];
        this.businessInfo.checkState = Number(this.businessInfo.checkState);
      })

    },

    //增加工作下一步
    addJobNextStep() {
      let addStep = this.addStep;
      if (addStep == 0) {
        if (this.form.jobName == null || this.form.jobName === '') {
          this.$modal.msgError("招聘职位名称不能为空！");
          return;
        }
        if (this.form.lowSalary == null || this.form.lowSalary === '' || this.form.heightSalary == null || this.form.heightSalary === '') {
          this.$modal.msgError("招聘职位薪水不能为空！");
          return;
        }
        if (this.form.lowSalary > this.form.heightSalary) {
          this.$modal.msgError("薪资范围设置有误，请核对！");
          return;
        }
        if (this.form.expYear == null || this.form.expYear === '') {
          this.$modal.msgError("招聘职位工作经验不能为空！");
          return;
        }
        if (this.form.education == null || this.form.education === '') {
          this.$modal.msgError("招聘职位学历要求不能为空！");
          return;
        }
        if (this.form.education == null || this.form.education === '') {
          this.$modal.msgError("招聘职位学历要求不能为空！");
          return;
        }
        this.form.jobSite = this.pname + this.cname + this.bname;
        if (this.form.jobSite === undefined || this.form.jobSite === '') {
          this.$modal.msgError("请选择工作地点！");
          return;
        }
        this.addStep++;
      } else if (addStep == 1) {
        if (this.form.jobReq === null || this.form.jobReq === '') {
          this.$modal.msgError("请选择填写任职要求！");
          return;
        }
        this.addStep++;
      } else if (addStep == 2) {
        if (this.form.jobDetail === null || this.form.jobDetail === '') {
          this.$modal.msgError("请选择填写工作详情！");
          return;
        }
        this.addStep++;
      }
      this.addStep = this.addStep > 3 ? 3 : this.addStep;
    },

    //上一步
    addJobBackStep() {
      this.addStep--;
      this.addStep = this.addStep < 0 ? 0 : this.addStep;
    },

    //** 查询applyJob列表
    getList() {
      this.loading = true;
      listApplyJob(this.queryParams).then(response => {
        this.applyJobList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },

    // 表单重置
    reset() {
      this.pname = '';
      this.cname = '';
      this.bname = '';
      this.form = {
        jobId: null,
        jobState: null,
        jobName: null,
        jobSalary: null,
        jobReq: null,
        jobDetail: null,
        education: null,
        expYear: null,
        jobSite: null,
        jobType: null,
        lowSalary: 3,
        heightSalary: 5,
      };
      this.addStep = 0;
      //  this.resetForm("form");
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.jobId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },

    /** 新增按钮操作 */
    handleAdd() {
      if (this.businessInfo.checkState < 3){
        this.$modal.msgError("公司工商信息为审核通过，无法发布职位信息！");
        return
      }

      this.reset();
      this.open = true;
      this.title = "添加新的职位";
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      let salary = row.jobSalary.split("-");
      this.form.lowSalary = salary[0];
      this.form.heightSalary = salary[1].slice(0, salary[1].length - 1);
      this.title = "修改" + row.jobName + '的信息';
      this.addStep = 0;
      this.form.comId = row.comId;
      this.form.company = row.company;
      this.form.education = row.education;
      this.form.expYear = row.expYear;
      this.form.jobDetail = row.jobDetail;
      this.form.jobId = row.jobId;
      this.form.jobName = row.jobName;
      this.form.jobReq = row.jobReq;
      this.form.jobSite = row.jobSite;
      this.form.jobState = row.jobState;
      this.form.remark = row.remark;
      this.form.userId = row.userId;
      this.form.jobType = row.jobType;
      this.open = true;
    },

    /** 提交按钮 */
    submitForm() {
      if (this.form.jobId == null) {
        this.form.jobSite = this.pname + this.cname + this.bname;
        this.form.expYear = this.form.expYear + '年';
      }
      if (this.form.jobSalary === undefined || this.form.jobSalary === '') {
        this.$modal.msgError("薪资范围不能为空！");
        return;
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.comId = this.$route.query.comId;
          this.form.jobSalary = this.form.lowSalary + '-' + this.form.heightSalary + "K";
          if (this.form.jobId != null) {
            updateApplyJob(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addApplyJob(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    /** 删除按钮操作 */
    handleDelete(row, state, isStop) {
      const jobIds = row.jobId || this.ids;
      if (row.jobState === 3) {
        this.$modal.msgError("职位信息异常，请联系工作人员！");
        return;
      }
      let msg = isStop ? "是否停止该职位的招聘？" : "是否继续改职位的招聘？"
      this.$modal.confirm(msg).then(function () {
        if (isStop) {
          if (row.jobState === 2) {
            this.$modal.msgWarning("该职位已是停止招聘状态！");
            return;
          }
        } else {
          if (row.jobState === 1) {
            this.$modal.msgWarning("该职位已是招聘状态！");
            return;
          }
        }
        return changeState(jobIds, state);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("操作成功！");
      }).catch(() => {
      });
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('company/applyJob/export', {
        ...this.queryParams
      }, `applyJob_${new Date().getTime()}.xlsx`)
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
    },
    // 选区
    choseBlock: function (e) {
      this.bname = e;
    },


  }
};
</script>


<style scoped>

.no-su:hover {
  cursor: default;
}


.el-dialog-div {
  height: 60vh;
  overflow: auto;
  padding: 0 20px 0 0;
}

.detail-left {
  text-align: right;
  font-size: 16px;
  color: black;
  font-weight: bold;
}


.detail-right {
  text-align: left;
  font-size: 16px;
  color: grey;
  padding: 0 10px;
}

.table-left {
  padding: 5px 10px;
  color: gray;
  font-size: 14px;
  width: 20%;
  text-align: right;
}


.table-right {
  width: 30%;
  padding: 5px 10px;
  color: black;
  font-size: 14px;
}


.table-left1 {
  padding: 5px 10px;
  color: gray;
  font-size: 14px;
  width: 12%;
  text-align: right;
}

.table-right1 {
  width: 22%;
  padding: 5px 10px;
  color: black;
  font-size: 14px;
}


</style>
