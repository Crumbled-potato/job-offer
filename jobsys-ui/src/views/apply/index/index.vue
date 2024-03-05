<template>
  <div>
    <!-- 求职首页 -->
    <el-row :gutter="10" class="elRow">
      <el-col :span="16" :offset="4">
        <el-card style="padding-bottom: 15px">
          <el-input style="border: 0" v-model="search.name" placeholder="输入工作职位、公司名称等" size="large">
            <template #prepend>
              <el-select v-model="search.type" placeholder="所有类型" filterable class="linkHover select_top">
                <el-option v-for="item in jobAllType" :key=item.name :label=item.name :value=item.id></el-option>
              </el-select>
            </template>
            <template #append>
              <el-button @click="gotoSelectJob()" class="linkHover select_top"
                         style="background-color: #E7FAF0;color: #1ab394;"> 搜索
              </el-button>
            </template>
          </el-input>
          <el-col :span="14" style="margin-top: 10px">
            <span class="top_hot_job gray">热门职位:</span>
            <span v-for="item in hostJob" class="top_hot_job_item top_hot_job jobHover">{{ item }}</span>
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
            <el-carousel-item v-for="item in 4" :key="item">
              <h3>{{ item }}</h3>
            </el-carousel-item>
          </el-carousel>
        </template>
      </el-col>


      <!-- 部分职位   -->
      <el-col :span="6">
        <el-card style="height: 400px">
          <div style="height: 50px" v-for="o in 7" :key="o">
            类型 职位 职位 职位 职位
          </div>
        </el-card>
      </el-col>
    </el-row>


    <!--热招职位-->
    <el-row :gutter="10" class="elRow">
      <el-col :span="4" :offset="10">
        <el-divider><h2>热招职位</h2></el-divider>
      </el-col>
      <el-col :span="16" :offset="4">
        <el-card style="padding-bottom: 25px">

          <!--    头部分类    -->
          <div style="padding: 5px">
            <div style="border: solid 1px #E6EBF5;padding: 5px;border-radius: 5px;">
              <el-button-group v-for="type in hostType">
                <el-button plain class=" border_none linkHover" @click="handleSelectType(type)">{{ type }}</el-button>
              </el-button-group>
              <el-button plain class="gray border_none">查看更多>></el-button>
            </div>
          </div>

          <!--每一个job-->
          <div v-for="job in jobDetails">
            <el-col :span="8" class="margin_top_10">
              <el-card shadow="hover" class="linkHover">

                <!--职位消息 -->
                <div slot="header" class="clearfix jobHover  ">
                  <span @click="goJobDetail(job)">{{ job.jobName }}</span>
                  <span class="job_salary">{{ job.jobSalary }}</span>
                  <div class="gray job_need">{{ job.jobSite }} | {{ job.expYear }} | {{ job.education }}</div>
                </div>

                <!--公司消息-->
                <div class="text item gray job_need jobHover " @click="goCompanyDetail(job.company)">
                  <img alt="公司照片" src="@/assets/404_images/404.png" width="50">
                  {{ job.company.comName }} | {{ job.company.comType }} | {{ job.company.isMarket }}
                </div>

              </el-card>
            </el-col>
          </div>
        </el-card>
      </el-col>
    </el-row>


    <!--热门公司-->
    <el-row :gutter="10" class="elRow">
      <el-col :span="4" :offset="10">
        <el-divider><h2>热门企业</h2></el-divider>
      </el-col>
      <el-col :span="16" :offset="4">

        <el-card style="padding-bottom: 25px">
          <div v-for="(company,index) in companyList">
            <el-col :span="8" class="margin_top_10">
              <el-card shadow="hover" class="linkHover">

                <div slot="header" class="clearfix">
                  <el-col :span="6">
                    <img alt="公司照片" src="@/assets/404_images/404.png" width="90">
                  </el-col>
                  <h3 style="padding: 3px;margin: 0" class="jobHover" @click="goCompanyDetail(company)">
                    {{ company.comName }}</h3>
                  <div class="gray job_need">
                    {{ company.comType }} |
                    {{ company.comName }} |
                    {{ company.isMarket }}
                  </div>
                </div>

                <!-- 工作                 -->
                <div class="text item jobHover" v-for="(job,index) in company.jobList">
                  <span @click="goJobDetail(job)">{{ job.jobName }}
                    <i class="el-icon-s-comment color_1ab394 margin_left_10 "></i>
                  </span>
                  <span class="job_salary">{{ job.jobSalary }}</span>
                  <div class="job_need">
                    <el-button size="mini" style="border: 0;" type="info" plain>{{ job.jobSite }}</el-button>
                    <el-button size="mini" style="border: 0;" type="info" plain>{{ job.expYear }}</el-button>
                    <el-button size="mini" style="border: 0;" type="info" plain>{{ job.education }}</el-button>
                  </div>
                  <el-divider v-if="index !== (company.jobList.length - 1)"></el-divider>
                </div>
              </el-card>
            </el-col>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card style="padding-bottom: 25px"></el-card>


  </div>
</template>

import {useRouter} from 'vue-router'

<script>
export default {
  name: "index",
  data() {
    return {
      tabPosition: 'left',
      //头部搜索框
      search: {name: '', type: '全部分类'}, /*搜索框条件*/
      //热门职业
      hostType: ['IT.互联网', '教育', '金融', '房地产', '教育培训', '娱乐传媒', '医疗健康', '法律', '采购'],
      hostJob: ['Java', 'python', '销售', 'web前端', '产品经理', 'UI设计', '产品运营', 'Android', '行政'],
      //热门职业每个工作
      jobDetails: [
        {
          'jobId': 1,
          'jobName': 'Java工程师',
          'jobSalary': '20-28K',
          'jobSite': '南昌',
          'expYear': '2年',
          'education': '本科',
          company: {
            'comId': 1,
            'comImg': '@/assets/404_images/404.png',
            'comName': '公司名称',
            'comType': '企业类型',
            'isMarket': '是否上市'
          }
        },
        {
          'jobId': 2,
          'jobName': 'Java工程师',
          'jobSalary': '20-28K',
          'jobSite': '南昌',
          'expYear': '2年',
          'education': '本科',
          company: {
            'comId': 2,
            'comImg': '@/assets/404_images/404.png',
            'comName': '公司名称',
            'comType': '企业类型',
            'isMarket': '是否上市'
          }
        },
        {
          'jobId': 3,
          'jobName': 'Java工程师',
          'jobSalary': '20-28K',
          'jobSite': '南昌',
          'expYear': '2年',
          'education': '本科',
          company: {
            'comId': 3,
            'comImg': '@/assets/404_images/404.png',
            'comName': '公司名称',
            'comType': '企业类型',
            'isMarket': '是否上市'
          }
        },
        {
          'jobId': 4,
          'jobName': 'Java工程师',
          'jobSalary': '20-28K',
          'jobSite': '南昌',
          'expYear': '2年',
          'education': '本科',
          company: {
            'comId': 4,
            'comImg': '@/assets/404_images/404.png',
            'comName': '公司名称',
            'comType': '企业类型',
            'isMarket': '是否上市'
          }
        },
        {
          'jobId': 5,
          'jobName': 'Java工程师',
          'jobSalary': '20-28K',
          'jobSite': '南昌',
          'expYear': '2年',
          'education': '本科',
          company: {
            'comId': 5,
            'comImg': '@/assets/404_images/404.png',
            'comName': '公司名称',
            'comType': '企业类型',
            'isMarket': '是否上市'
          }
        },
        {
          'jobId': 6,
          'jobName': 'Java工程师',
          'jobSalary': '20-28K',
          'jobSite': '南昌',
          'expYear': '2年',
          'education': '本科',
          company: {
            'comId': 6,
            'comImg': '@/assets/404_images/404.png',
            'comName': '公司名称',
            'comType': '企业类型',
            'isMarket': '是否上市'
          }
        },
        {
          'jobId': 7,
          'jobName': 'Java工程师',
          'jobSalary': '20-28K',
          'jobSite': '南昌',
          'expYear': '2年',
          'education': '本科',
          company: {
            'comId': 7,
            'comImg': '@/assets/404_images/404.png',
            'comName': '公司名称',
            'comType': '企业类型',
            'isMarket': '是否上市'
          }
        },
        {
          'jobId': 8,
          'jobName': 'Java工程师',
          'jobSalary': '20-28K',
          'jobSite': '南昌',
          'expYear': '2年',
          'education': '本科',
          company: {
            'comId': 18,
            'comImg': '@/assets/404_images/404.png',
            'comName': '公司名称',
            'comType': '企业类型',
            'isMarket': '是否上市'
          }
        },
        {
          'jobId': 9,
          'jobName': 'Java工程师',
          'jobSalary': '20-28K',
          'jobSite': '南昌',
          'expYear': '2年',
          'education': '本科',
          company: {
            'comId': 19,
            'comImg': '@/assets/404_images/404.png',
            'comName': '公司名称',
            'comType': '企业类型',
            'isMarket': '是否上市'
          }
        },
      ],
      //所有工作类型
      jobAllType: [
        {id: 1, name: 'IT.互联网'},
        {id: 2, name: '金融'},
        {id: 3, name: '房地产'},
        {id: 4, name: '教育培训'},
        {id: 5, name: '娱乐传媒'},
        {id: 6, name: '医疗健康'},
        {id: 7, name: '法律'},
        {id: 8, name: '采购'},
      ],

      companyList: [
        {
          'comId': 1,
          'comImg': '@/assets/404_images/404.png',
          'comName': '公司名称',
          'comType': '企业类型',
          'isMarket': '是否上市',
          jobList: [{
            'jobId': 1,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 2,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 3,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }]
        },
        {
          'comId': 2,
          'comImg': '@/assets/404_images/404.png',
          'comName': '公司名称',
          'comType': '企业类型',
          'isMarket': '是否上市',
          jobList: [{
            'jobId': 4,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 5,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 6,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }]
        },
        {
          'comId': 3,
          'comImg': '@/assets/404_images/404.png',
          'comName': '公司名称',
          'comType': '企业类型',
          'isMarket': '是否上市',
          jobList: [{
            'jobId': 7,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 8,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 9,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }]
        },
        {
          'comId': 4,
          'comImg': '@/assets/404_images/404.png',
          'comName': '公司名称',
          'comType': '企业类型',
          'isMarket': '是否上市',
          jobList: [{
            'jobId': 7,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 8,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 9,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }]
        },
        {
          'comId': 5,
          'comImg': '@/assets/404_images/404.png',
          'comName': '公司名称',
          'comType': '企业类型',
          'isMarket': '是否上市',
          jobList: [{
            'jobId': 7,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 8,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 9,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }]
        },
        {
          'comId': 6,
          'comImg': '@/assets/404_images/404.png',
          'comName': '公司名称',
          'comType': '企业类型',
          'isMarket': '是否上市',
          jobList: [{
            'jobId': 7,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 8,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 9,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }]
        },
        {
          'comId': 7,
          'comImg': '@/assets/404_images/404.png',
          'comName': '公司名称',
          'comType': '企业类型',
          'isMarket': '是否上市',
          jobList: [{
            'jobId': 7,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 8,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 9,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }]
        },
        {
          'comId': 8,
          'comImg': '@/assets/404_images/404.png',
          'comName': '公司名称',
          'comType': '企业类型',
          'isMarket': '是否上市',
          jobList: [{
            'jobId': 7,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 8,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 9,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }]
        },
        {
          'comId': 9,
          'comImg': '@/assets/404_images/404.png',
          'comName': '公司名称',
          'comType': '企业类型',
          'isMarket': '是否上市',
          jobList: [{
            'jobId': 7,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 8,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }, {
            'jobId': 9,
            'jobName': 'Java工程师',
            'jobSalary': '20-28K',
            'jobSite': '南昌',
            'expYear': '2年',
            'education': '本科',
          }]
        },
      ]


    }
  },
  methods: {

    //选择不同job
    handleSelectType(type) {
      this.$message({
        message: 'type:' + type,
        type: 'success'
      });
    },

    //选择不同jobId
    goJobDetail(item) {
      this.$router.push({path: 'job', query: {jobId: item.jobId}});
    },

    //选择不同jobId
    goCompanyDetail(item) {
      this.$message({
        message: 'company:' + item.comId,
        type: 'success'
      });
    },


    //搜索职位公司
    gotoSelectJob() {
      this.$message({
        message: '类型：' + this.search.type + '名称：' + this.search.name,
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
