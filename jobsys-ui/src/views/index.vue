<template>
  <div>
    <!-- 求职首页 -->
    <el-row :gutter="10" class="elRow">
      <el-col :span="16" :offset="4">
        <el-card style="padding-bottom: 15px">
          <el-input style="border: 0" v-model="search.name" placeholder="输入工作职位、公司名称等" size="large">
            <template #prepend>
              <!--              <el-select v-model="search.type" placeholder="所有类型" filterable class="linkHover select_top">-->
              <!--                <el-option v-for="item in jobAllType" :key=item.name :label=item.name :value=item.id></el-option>-->
              <!--              </el-select>-->
            </template>
            <template #append>
              <el-button @click="gotoSelectJob()" class="linkHover select_top"
                         style="background-color: #E7FAF0;color: #1ab394;"> 搜索
              </el-button>
            </template>
          </el-input>
          <el-col :span="24" style="margin-top: 10px">
            <div v-if="dict.type.com_type.length < 9">
              <el-button-group v-for="dict in dict.type.com_type">
                <el-button plain class=" border_none linkHover" @click="gotoSelectJob(dict.label)">{{
                    dict.label
                  }}
                </el-button>
              </el-button-group>
              <el-button plain class="gray border_none">查看更多>></el-button>
            </div>

            <div v-else>
              <el-button-group v-for="(dict,index) in dict.type.com_type">


                <el-button v-if="index < 10" plain class=" border_none linkHover" @click="gotoSelectJob(dict.value)">
                  {{
                    dict.label
                  }}
                </el-button>
              </el-button-group>

              <el-tooltip effect="light" content="Bottom center">
                <template #content>
                  <div>
                    <span v-for="(dict,index) in dict.type.com_type">
                    <el-button plain class=" border_none linkHover"
                               style="padding: 5px 5px;margin: 5px 5px"
                               @click="getCompanyList(dict.value)">{{ dict.label }}</el-button>
                    <br v-if="((index+1) %  8) === 0">
                    </span>
                  </div>
                </template>
                <el-button plain class="gray border_none">查看更多>></el-button>
              </el-tooltip>


            </div>
          </el-col>
        </el-card>
      </el-col>

    </el-row>

    <!--上部分 广告和部门职位-->
    <el-row :gutter="10" class="elRow">

      <!-- 轮播图  -->
      <el-col :span="10" :offset="4">
        <template>
          <el-carousel :interval="3000" arrow="hover" height="400px">
            <img alt="公司照片" src="@/assets/images/login-background.jpg" width="100%" height="99%">
          </el-carousel>
        </template>
      </el-col>


      <!-- 部分职位   -->
      <el-col :span="6">
        <el-card style="height: 400px">
          <div style="height: 50px" v-for="o in 7" :key="o">
            <span v-for="item in hostJob" class="top_hot_job_item top_hot_job jobHover">{{ item }}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!--最新职位-->
    <el-row :gutter="10" class="elRow">
      <el-col :span="4" :offset="10">
        <el-divider><h3>最新职位信息</h3></el-divider>
      </el-col>
      <el-col :span="16" :offset="4">
        <el-card style="padding-bottom: 25px">
          <!--    头部分类    -->
          <div style="border: solid 1px #E6EBF5;padding: 5px;border-radius: 5px;"
               v-if="dict.type.com_type.length < 9">
            <el-button-group v-for="dict in dict.type.com_type">
              <el-button plain class=" border_none linkHover" @click="getJobList(dict.label)">{{
                  dict.label
                }}
              </el-button>
            </el-button-group>
            <el-button plain class="gray border_none">查看更多>></el-button>
          </div>
          <div style="border: solid 1px #E6EBF5;padding: 5px;border-radius: 5px;" v-else>
            <el-button-group v-for="(dict,index) in dict.type.com_type">


              <el-button v-if="index < 10" plain class=" border_none linkHover" @click="getJobList(dict.value)">
                {{
                  dict.label
                }}
              </el-button>
            </el-button-group>

            <el-tooltip effect="light" content="Bottom center">
              <template #content>
                <div>
                    <span v-for="(dict,index) in dict.type.com_type">
                    <el-button plain class=" border_none linkHover"
                               style="padding: 5px 5px;margin: 5px 5px"
                               @click="getCompanyList(dict.value)">{{ dict.label }}</el-button>
                    <br v-if="((index+1) %  8) === 0">
                    </span>
                </div>
              </template>
              <el-button plain class="gray border_none">查看更多>></el-button>
            </el-tooltip>


          </div>

          <!--每一个job-->
          <div v-for="job in jobDetails" v-if="jobDetails.length>0">
            <el-col :span="8" class="margin_top_10">
              <el-card shadow="hover" class="linkHover">

                <!--职位消息 -->
                <div slot="header" class="clearfix jobHover  ">
                  <span @click="goJobDetail(job)">{{ job.jobName }}</span>
                  <span class="job_salary">{{ job.jobSalary }}</span>
                  <div class="gray job_need">{{ job.jobSite }} | {{ job.expYear }} |
                    <dict-tag :options="dict.type.education" :value="job.education"/>
                  </div>
                </div>

                <!--公司消息-->
                <div class="text item gray job_need jobHover " @click="goCompanyDetail(job.company)">
                  <el-image
                    style="width: 35px; height: 35px"
                    :src="job.company.comImg"
                  ></el-image>
                  {{ job.company.comName }} |
                  <dict-tag :options="dict.type.com_type" :value="job.company.comType"/>
                  |
                  <dict-tag :options="dict.type.is_market" :value="job.company.isMarket"/>
                </div>

              </el-card>
            </el-col>
          </div>
          <div v-if="jobDetails.length==0">
            <el-empty description="小苏努力查询了，暂无相关职位信息！"
                      image="http://127.0.0.1:9300/statics/401.gif"></el-empty>
          </div>
        </el-card>
      </el-col>
    </el-row>


    <!--热门公司-->
    <el-row :gutter="10" class="elRow">
      <el-col :span="4" :offset="10">
        <el-divider><h3>最新企业信息</h3></el-divider>
      </el-col>
      <el-col :span="16" :offset="4">
        <el-card style="padding-bottom: 25px">
          <div style="border: solid 1px #E6EBF5;padding: 5px;border-radius: 5px;"
               v-if="dict.type.com_type.length < 9">
            <el-button-group v-for="dict in dict.type.com_type">
              <el-button plain class=" border_none linkHover" @click="getCompanyList(dict.label)">{{
                  dict.label
                }}
              </el-button>
            </el-button-group>
            <el-button plain class="gray border_none">查看更多>></el-button>
          </div>
          <div style="border: solid 1px #E6EBF5;padding: 5px;border-radius: 5px;" v-else>
            <el-button-group v-for="(dict,index) in dict.type.com_type">


              <el-button v-if="index < 10" plain class=" border_none linkHover" @click="getCompanyList(dict.value)">
                {{
                  dict.label
                }}
              </el-button>
            </el-button-group>

            <el-tooltip effect="light" content="Bottom center">
              <template #content>
                <div>
                    <span v-for="(dict,index) in dict.type.com_type">
                    <el-button plain class=" border_none linkHover"
                               style="padding: 5px 5px;margin: 5px 5px"
                               @click="getCompanyList(dict.value)">{{ dict.label }}</el-button>
                    <br v-if="((index+1) %  8) === 0">
                    </span>
                </div>
              </template>
              <el-button plain class="gray border_none">查看更多>></el-button>
            </el-tooltip>


          </div>
          <div v-if="companyList.length>0" v-for="(company,index) in companyList">
            <el-col :span="8" class="margin_top_10">
              <el-card shadow="hover" class="linkHover">
                <div slot="header" class="clearfix">
                  <el-col :span="6">
                    <el-image
                      style="width: 55px; height: 55px"
                      :src="company.comImg"
                    ></el-image>
                  </el-col>
                  <h3 style="padding: 3px;margin: 0" class="jobHover" @click="goCompanyDetail(company)">
                    {{ company.comName }}</h3>
                  <div class="gray job_need">
                    <dict-tag :options="dict.type.com_type" :value="company.comType"/>
                    |
                    <dict-tag :options="dict.type.com_scale" :value="company.comScale"/>
                    |
                    <dict-tag :options="dict.type.is_market" :value="company.isMarket"/>
                  </div>
                </div>

                <!-- 工作                 -->
                <div class="text item jobHover" v-if="company.applyJobList.length>0"
                     v-for="(job,index) in company.applyJobList">
                  <span @click="goJobDetail(job)">{{ job.jobName }}
                    <!--                    <i class="el-icon-s-comment color_1ab394 margin_left_10 "></i>-->
                  </span>
                  <span class="job_salary">{{ job.jobSalary }}</span>
                  <div class="job_need">
                    <el-button size="mini" style="border: 0;" type="info" plain>{{ job.jobSite }}</el-button>
                    <el-button size="mini" style="border: 0;" type="info" plain>{{ job.expYear }}</el-button>
                    <el-button size="mini" style="border: 0;" type="info" plain>
                      <dict-tag :options="dict.type.education" :value="job.education"/>
                    </el-button>
                  </div>
                  <el-divider v-if="index !== (company.applyJobList.length - 1)"></el-divider>
                </div>
                <div v-if="company.applyJobList.length==0">
                  <el-empty description="小苏努力查询了，该公司暂无职位信息！"
                            image="http://127.0.0.1:9300/statics/401.gif"></el-empty>
                </div>
              </el-card>
            </el-col>
          </div>
          <div v-if="companyList.length==0">
            <el-empty description="小苏努力查询了，暂无公司信息！"
                      image="http://127.0.0.1:9300/statics/401.gif"></el-empty>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card style="padding-bottom: 25px"></el-card>


  </div>
</template>

import {useRouter} from 'vue-router'

<script>
import {listCompanyByType} from "@/api/company/company";
import {listApplyJobOrderBy} from "@/api/company/applyJob";

export default {
  name: "index",
  dicts: ['com_type', 'com_scale', 'is_market', 'education'],
  data() {
    return {
      tabPosition: 'left',
      //头部搜索框
      search: {name: '', type: '全部分类'}, /*搜索框条件*/
      //热门职业
      hostType: ['IT.互联网', '教育', '金融', '房地产', '教育培训', '娱乐传媒', '医疗健康', '法律', '采购'],
      hostJob: ['Java', 'python', '销售', 'web前端', '产品经理', 'UI设计', '产品运营', 'Android', '行政'],
      //热门职业每个工作
      jobDetails: [],
      //所有工作类型

      companyList: []


    }
  }, created() {
    this.getCompanyList(1);
    this.getJobList(1)
  },
  methods: {

    getJobList(type) {
      listApplyJobOrderBy({jobType: type}).then(
        res => {
          this.jobDetails = res.data;
        })

    },


    getCompanyList(type) {
      listCompanyByType({type: type}).then(
        response => {
          this.companyList = response.data;
        })


    },

    //选择不同Id
    handClickComType(item) {

    },


    //选择不同job
    handleSelectType(type) {
      this.getJobList();
    },

    //选择不同jobId
    goJobDetail(item) {
      this.$router.push({path: '/job', query: {jobId: item.jobId}});
    },

    //选择不同jobId
    goCompanyDetail(item) {
      this.$message({
        message: 'company:' + item.comId,
        type: 'success'
      });
    },


    //搜索职位公司
    gotoSelectJob(type) {
      this.$router.push({path: '/searchJob', query: {jobName: this.search.name, jobType: type}});
    },


  }


}
</script>

<style scoped>


.elRow {
  margin-top: 3px;
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


<!--
<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="24">
        <blockquote class="text-warning" style="font-size: 14px">
          互联网招聘平台

        </blockquote>

        <hr/>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>互联网招聘平台</h2>
        <p>
          互联网招聘平台
        </p>
        <p>
          <b>当前版本:</b> <span>v{{ version }}</span>
        </p>

      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>技术选型</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <h4>后端技术</h4>
            <ul>
              <li>SpringBoot</li>
              <li>SpringCloud</li>
              <li>Nacos</li>
              <li>Sentinel</li>
              <li>Seata</li>
              <li>Minio</li>
              <li>...</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>前端技术</h4>
            <ul>
              <li>Vue</li>
              <li>Vuex</li>
              <li>Element-ui</li>
              <li>Axios</li>
              <li>Echarts</li>
              <li>Quill</li>
              <li>...</li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-divider/>
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">

        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">

        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">

        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import store from "@/store";

export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.3.0",
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
  },
  created() {
  /*  store.get*/
    console.log( store);
  }
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

-->
