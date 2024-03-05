<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="工作名称" prop="jobName">
        <el-input
          v-model="queryParams.jobName"
          placeholder="请输入工作名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>


    <el-table v-loading="loading" :data="reportList" border>
      <el-table-column label="工作名称" align="center" prop="jobName">
        <template slot-scope="scope">
          <div class="link-hover" style="color: #00afff" @click="gotoJob(scope.row)"> {{ scope.row.jobName }}</div>
        </template>
      </el-table-column>
      <el-table-column label="举报人姓名" align="center" prop="userName"/>
      <el-table-column label="联系方式" align="center" prop="phone"/>
      <el-table-column label="描述" align="center" prop="userDescribe"/>
      <el-table-column label="举报类型" align="center" prop="type"/>
      <el-table-column label="具体原因" align="center" prop="reason" show-overflow-tooltip/>
      <el-table-column label="处理结果" align="center" prop="reason" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-button v-if="scope.row.remark == null" size="mini" type="warning"><i class="el-icon-info"> 未处理</i>
          </el-button>
          <el-button v-if="scope.row.remark == 1" size="mini" type="success"><i class="el-icon-success"></i> 成立
          </el-button>
          <el-button v-if="scope.row.remark == 2" size="mini" type="danger"><i class="el-icon-error"> </i> 不成立
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="300" class-name="small-padding fixed-width">
        <template slot-scope="scope">

          <div v-if="scope.row.remark == null">
            <el-button size="small" type="success" plain icon="el-icon-success" @click="handelCheck(scope.row,1)">
              成立
            </el-button>
            <el-button size="small" type="warning" plain icon="el-icon-error" @click="handelCheck(scope.row,2)">
              不成立
            </el-button>
            <el-button size="small" type="danger" plain icon="el-icon-delete" @click="handleDelete(scope.row)">
              删除
            </el-button>
          </div>
          <div v-else>
            <el-button size="small" type="info" plain icon="el-icon-success">举报已处理</el-button>
            <el-button size="small" type="danger" plain icon="el-icon-delete" @click="handleDelete(scope.row)"> 删除
            </el-button>
          </div>
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

  </div>
</template>

<script>
import {listReport, getReport, delReport, addReport, updateReport} from "@/api/apply/report";
import {getNowDate} from "@/utils/ruoyi";
import {addMsg} from "@/api/apply/msg";
import {getApplyJob} from "@/api/company/applyJob";

export default {
  name: "Report",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // report表格数据
      reportList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {

    gotoJob(row) {
      this.$router.push({path: '/job', query: {jobId: row.jobId}});
    },


    handelCheck(row, state) {
      let param = {
        reportId: row.reportId,
        remark: state,
        jobId: row.jobId
      }
      updateReport(param).then(res => {
        this.$modal.msgSuccess("处理成功");
        this.getList();
      })
    },



    /** 查询report列表 */
    getList() {
      this.loading = true;
      listReport(this.queryParams).then(response => {
        this.reportList = response.rows;
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
      this.form = {
        reportId: null,
        jobId: null,
        jobName: null,
        name: null,
        describe: null,
        phone: null,
        type: null,
        reason: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.reportId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加report";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const reportId = row.reportId || this.ids
      getReport(reportId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改report";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.reportId != null) {
            updateReport(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReport(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const reportIds = row.reportId || this.ids;
      this.$modal.confirm('是否确认删除report编号为"' + reportIds + '"的数据项？').then(function () {
        return delReport(reportIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('work/report/export', {
        ...this.queryParams
      }, `report_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>


<style scoped>
.link-hover:hover {
  cursor: pointer;
}

</style>
