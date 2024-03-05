<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公告标题" prop="noticeTitle">
        <el-input
          v-model="queryParams.noticeTitle"
          placeholder="请输入公告标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布人" prop="createBy">
        <el-input
          v-model="queryParams.createBy"
          placeholder="请输入发布人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="noticeType">
        <el-select v-model="queryParams.noticeType" placeholder="请选择公告类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_notice_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-divider><i class="el-icon-chat-dot-square"></i></el-divider>

    <el-row :gutter="20">
      <el-col :span="6" v-for="notice in noticeList">
        <el-card class="box-card" shadow="hover" style="margin: 10px 0">
          <template #header>
            <div class="card-header" @click="getNoticeById(notice)">
              <span v-if=" notice.noticeTitle.length < 25 ">
                <el-tooltip effect="dark" :content="notice.noticeTitle">
                  <el-button style="border: 0">{{ notice.noticeTitle }}</el-button>
                </el-tooltip>
              </span>
              <span v-else>
                <el-tooltip effect="dark" :content="notice.noticeTitle">
                   <el-button style="border: 0">{{ notice.noticeTitle.slice(0, 23) }}...</el-button>
                </el-tooltip>
              </span>
              <dict-tag :options="dict.type.sys_notice_type" :value="notice.noticeType" style="text-align: right"/>
              <span style="color: gray;font-size: 12px;"> {{ notice.createBy }} </span>
              <span style="color: gray;font-size: 12px;margin-left: 20px"> {{ notice.createTime }} </span>
            </div>
          </template>
          <div @click="getNoticeById(notice)" style="color: gray;font-size: 14px;text-indent: 2em">
            <span v-if=" notice.noticeContent.length < 30 ">{{ notice.noticeContent }}</span>
            <span v-else>{{ notice.noticeContent.slice(0, 25) }}...</span>
          </div>
        </el-card>
      </el-col>
    </el-row>


    <!-- 添加或修改公告对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body destroy-on-close
               :close-on-press-escape="true">

      <el-row>
        <h2 style="padding: 0">{{ selectNotice.noticeTitle }}
          <dict-tag :options="dict.type.sys_notice_type" :value="selectNotice.noticeType"
                    style="text-align: right;margin-right: 20px;float: right"/>
        </h2>
      </el-row>
      <el-row>
        <span style="color: gray;font-size: 12px;"> 发布人：{{ selectNotice.createBy }} </span>
        <span style="color: gray;font-size: 12px;margin-left: 20px">发布时时间： {{ selectNotice.createTime }} </span>
      </el-row>


      <editor style="margin-top: 20px" v-model="selectNotice.noticeContent" :min-height="200" :readOnly="true"
              :toolbar="[]"/>

      <div slot="footer" class="dialog-footer">
        <el-button @click="open=false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listNotice} from "@/api/system/notice";

export default {
  name: "inform",
  dicts: ['sys_notice_status', 'sys_notice_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 公告表格数据
      noticeList: [],
      // 弹出层标题
      title: "互联网招聘求职平台系统公告详情",
      // 是否显示弹出层
      open: false,
      selectNotice: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        noticeTitle: undefined,
        createBy: undefined,
        status: 0
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        noticeTitle: [
          {required: true, message: "公告标题不能为空", trigger: "blur"}
        ],
        noticeType: [
          {required: true, message: "公告类型不能为空", trigger: "change"}
        ]
      }

    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询公告列表 */
    getList() {
      this.loading = true;
      listNotice(this.queryParams).then(response => {
        this.noticeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 表单重置
    reset() {
      this.form = {
        noticeId: undefined,
        noticeTitle: undefined,
        noticeType: undefined,
        noticeContent: undefined,
        status: "0"
      };
      this.resetForm("form");
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

    /** 选择查看某一条通知公告 */
    getNoticeById(notice) {
      this.selectNotice = notice;
      this.form = notice;
      this.open = true;
    }
  }
};
</script>

<style scoped>
.box-card:hover {
  color: red;
  border: solid 1px #ffba00;
  cursor: pointer;
  background-color: #C7C9D0;

}
</style>
