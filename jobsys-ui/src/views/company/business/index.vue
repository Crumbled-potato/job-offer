<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="法人" prop="legal">
        <el-input v-model="queryParams.legal" placeholder="请输入法人" clearable size="small"/>
      </el-form-item>
      <el-form-item label="成立时间" prop="setUpTime">
        <el-date-picker clearable size="small"
                        v-model="queryParams.setUpTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择成立时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="企业类型" prop="comType">
        <el-select v-model="queryParams.comType" placeholder="请选择企业类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.business_com_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>


      <el-form-item label="审核阶段" prop="checkState">
        <el-select v-model="queryParams.checkState" placeholder="请选择审核阶段" clearable size="small">
          <el-option
            v-for="dict in dict.type.check"
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

    <el-table v-loading="loading" :data="businessList" border>
      <div slot="empty">
        <el-empty description="暂无需要审核的公司工商信息！"
                  image="http://127.0.0.1:9300/statics/401.gif"></el-empty>
      </div>
      <el-table-column label="主键" align="center" prop="businessId" v-if="false"/>
      <el-table-column label="法人" align="center" prop="legal" width="130" show-overflow-tooltip/>
      <el-table-column label="注册资本" align="center" width="130" prop="capital" show-overflow-tooltip>
        <template slot-scope="scope">
          <span>{{ scope.row.capital }} 万</span>
        </template>
      </el-table-column>
      <el-table-column label="成立时间" align="center" prop="setUpTime" width="150" show-overflow-tooltip>
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.setUpTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="企业类型" align="center" prop="comType" width="150" show-overflow-tooltip>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.business_com_type" :value="scope.row.comType"/>
        </template>
      </el-table-column>
      <el-table-column label="经营状态" align="center" prop="comState" width="120" show-overflow-tooltip>
        <template slot-scope="scope">
          <dict-tag :options="dict.type.bu_type" :value="scope.row.comState"/>
        </template>
      </el-table-column>
      <el-table-column label="注册地址" align="center" prop="address" show-overflow-tooltip/>
      <el-table-column label="统一社会信用代码" align="center" prop="comCode" width="180" show-overflow-tooltip/>
      <el-table-column label="公司经营范围" align="center" prop="comScope" show-overflow-tooltip/>
      <el-table-column label="审核阶段" align="center" prop="checkState" show-overflow-tooltip>
        <template slot-scope="scope">
          <div v-if="scope.row.checkState ==1 "><i style="color: #2b2f3a" class="el-icon-info"></i> 已提交</div>
          <div v-if="scope.row.checkState ==2 "><i style="color: #00afff" class="el-icon-more"></i> 审核中</div>
          <div v-if="scope.row.checkState ==4 "><i style="color: #1ab394" class="el-icon-success"></i> 已通过</div>
          <div v-if="scope.row.checkState ==3 "><i style="color: red" class="el-icon-error"></i> 未通过</div>
        </template>
      </el-table-column>
      <el-table-column label="公司Id" align="center" prop="comId">
        <template slot-scope="scope">
          <div @click="getComInfo(scope.row)"><i style="color: #00afff" class="el-icon-view"></i> 查看公司信息</div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="220">
        <template slot-scope="scope">
          <div v-if="scope.row.checkState ==4">
            <i style="color: #1ab394" class="el-icon-success"></i> 审核完毕
          </div>
          <div v-else>
            <el-button size="mini" type="success" icon="el-icon-circle-check" @click="changeState(scope.row,4)"> 通过
            </el-button>
            <el-button size="mini" type="danger" icon="el-icon-circle-close" @click="changeState(scope.row,3)"> 不通过
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

    <!-- 添加或修改business对话框 -->
    <el-dialog title="公司详情" :visible.sync="open" width="800px" append-to-body>
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

    </el-dialog>
  </div>
</template>

<script>
import {listBusiness, getBusiness, delBusiness, addBusiness, updateBusiness} from "@/api/company/business";
import {getCompany} from "@/api/company/company";
import {getNowDate} from "@/utils/ruoyi";
import {addMsg} from "@/api/apply/msg";

export default {
  name: "Business",
  dicts: ['business_com_type', 'is_market', 'com_type', 'com_scale', 'bu_type', 'check'],
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
      // business表格数据
      businessList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      comInfo: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        legal: null,
        capital: null,
        setUpTime: null,
        comType: null,
        comState: null,
        address: null,
        comCode: null,
        comScope: null,
        checkState: null,
        createDate: null,
        updateDate: null,
        comId: null
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
    changeState(row, state) {
      let param = {
        businessId: row.businessId,
        checkState: state
      }
      updateBusiness(param).then(res => {
        if (res.code == 200) {
          this.$modal.msgSuccess("审核成功");
          this.addBuMsg(row, state)
          this.getList()
        }
      })

    },


    addBuMsg(row, state) {
      getCompany(row.comId).then(res => {
        if (res.code == 200) {
          let comInfo = res.data;
          console.log(comInfo)
          let msg = state == 3 ? "您提交的 " + comInfo.comName + " 的工商信息，已经通过工作人员的审核，您现在可以发布该公司的招聘信息了！" : "您提交的 " + comInfo.comName + " 的工商信息，未能通过审核！";
          let param = {
            userId: comInfo.userId,
            msgContent: msg,
            isRead: 2,
          }
          addMsg(param);
        }
      })
    },


    getComInfo(row) {
      this.open = true
      getCompany(row.comId).then(res => {
        this.comInfo = res.data;
      })
    },


    /** 查询business列表 */
    getList() {
      this.loading = true;
      listBusiness(this.queryParams).then(response => {
        this.businessList = response.rows;
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
        businessId: null,
        legal: null,
        capital: null,
        setUpTime: null,
        comType: null,
        comState: null,
        address: null,
        comCode: null,
        comScope: null,
        checkState: null,
        createDate: null,
        createBy: null,
        updateDate: null,
        updateBy: null,
        comId: null
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
      this.ids = selection.map(item => item.businessId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加business";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const businessId = row.businessId || this.ids
      getBusiness(businessId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改business";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.businessId != null) {
            updateBusiness(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBusiness(this.form).then(response => {
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
      const businessIds = row.businessId || this.ids;
      this.$modal.confirm('是否确认删除business编号为"' + businessIds + '"的数据项？').then(function () {
        return delBusiness(businessIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/business/export', {
        ...this.queryParams
      }, `business_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
