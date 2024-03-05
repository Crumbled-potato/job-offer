<template>
  <div>

    <el-row :gutter="10" class="elRow">
      <el-col :span="16" :offset="4">
        <el-card style="padding-bottom: 15px">
          <el-input style="border: 0" v-model="search.jobName" placeholder="输入工作职位名称等" size="large">
            <template #append>
              <el-button @click="gotoSelectJob()" class="linkHover select_top"
                         style="background-color: #E7FAF0;color: #1ab394;"> 搜索
              </el-button>
            </template>
          </el-input>
          <div v-if="dict.type.com_type.length < 9">
            <el-button-group v-for="dict in dict.type.com_type">
              <el-button plain class=" border_none linkHover" @click="gotoSelectJob(dict.value)">{{
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
                               @click="gotoSelectJob(dict.value)">{{ dict.label }}</el-button>
                    <br v-if="((index+1) %  8) === 0">
                    </span>
                </div>
              </template>
              <el-button plain class="gray border_none">查看更多>></el-button>
            </el-tooltip>


          </div>
        </el-card>
      </el-col>
    </el-row>


    <el-row :gutter="10" >
      <el-col :span="4" :offset="10">
        <el-divider><h4>搜索详情</h4></el-divider>
      </el-col>
      <el-col :span="16" :offset="4">
        <el-card style="padding-bottom: 25px">

          <!--    头部分类    -->


          <!--每一个job-->
          <div v-if="jobDetails.length>0" v-for="job in jobDetails">
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


  </div>
</template>

<script>
import {listApplyJobOrderBy} from "@/api/company/applyJob";

export default {
  name: "searchJob",
  dicts: ['com_type', 'com_scale', 'is_market', 'education'],
  data() {
    return {
      jobDetails: [],
      search: {jobName: '', jobType: ''}, /*搜索框条件*/
    }
  }, created() {
    this.search.jobName = this.$route.query.jobName;
    this.search.jobType = this.$route.query.jobType;
    this.getJobList()
  },
  methods: {
    getJobList() {
      listApplyJobOrderBy(this.search).then(
        res => {
          this.jobDetails = res.data;
        })

    },
    gotoSelectJob(type) {
      if (type == undefined) {
        this.search.jobType = null;
      } else {
        this.search.jobType = type;
      }
      this.getJobList();
    },


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

  }
}
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
