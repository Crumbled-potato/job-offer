<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" v-if="isLogin"
               @toggleClick="toggleSideBar"/>
    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" v-if="!topNav"/>
    <top-nav id="topmenu-container" class="topmenu-container" v-if="topNav"/>

    <div class="right-menu" v-if="isLogin">
      <template v-if="device!=='mobile' || isLogin">
        <!--        <search id="header-search" class="right-menu-item" />-->

        <!--       <el-tooltip content="源码地址" effect="dark" placement="bottom">
                  <ruo-yi-git id="ruoyi-git" class="right-menu-item hover-effect" />
                </el-tooltip>-->

        <!--      <el-tooltip content="文档地址" effect="dark" placement="bottom">
                  <ruo-yi-doc id="ruoyi-doc" class="right-menu-item hover-effect" />
                </el-tooltip>-->

        <!--        <screenfull id="screenfull" class="right-menu-item hover-effect" />-->

        <el-tooltip content="消息中心" style="margin-top: -20px;margin-right: 20px">
          <el-badge :value="isReadSum" :max="99" class="item">
            <el-button size="mini" @click="handlerMsg"><i class="el-icon-s-comment"></i></el-button>
          </el-badge>
        </el-tooltip>

      </template>

      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">


        <div class="avatar-wrapper">
          <img :src="avatar" class="user-avatar">
          <i class="el-icon-caret-bottom"/>
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/user/profile">
            <el-dropdown-item>个人中心</el-dropdown-item>
          </router-link>
          <!--          <el-dropdown-item @click.native="setting = true">
                      <span>布局设置</span>
                    </el-dropdown-item>-->
          <el-dropdown-item divided @click.native="logout">
            <span>退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>


    <div class="right-menu" v-else>
      <div style=" margin-right: 30px">
        <router-link to="/login">
          <el-button> 登录</el-button>
        </router-link>
      </div>
    </div>

    <el-dialog :visible.sync="msgOpen" width="1000px" append-to-body>
      <div slot="title">
        <h4> 系统通知
          <span style="margin-left: 15px;color: grey;font-size: 12px">
           <i class="el-icon-circle-check" style="color: #1ab394"></i> 已读 {{ total - isReadSum }} 条 |
           <i class="el-icon-info" style="color: #00afff"></i> 未读: {{ this.isReadSum }} 条
          </span>
        </h4>
      </div>
      <div class="el-dialog-div">
        <el-table v-loading="loading" :data="msgList" border>
          <div slot="empty">
            <el-empty description="暂无系统通知！"
                      image="http://127.0.0.1:9300/statics/401.gif"></el-empty>
          </div>

          <el-table-column label="${comment}" align="center" prop="msgId" v-if="false"/>
          <el-table-column label="${comment}" align="center" prop="userId" v-if="false"/>
          <el-table-column label="系统通知内容" align="center" prop="msgContent" show-overflow-tooltip/>
          <el-table-column label="时间" align="center" prop="createTime" width="160"/>
          <el-table-column label="状态" align="center" prop="isRead" width="100">
            <template slot-scope="scope">
              <div v-if="scope.row.isRead == 1">
                <i class="el-icon-circle-check" style="color: #1ab394"></i> 已读
              </div>
              <div v-if="scope.row.isRead == 2">
                <i class="el-icon-info" style="color: #00afff"></i> 未读
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="180">
            <template slot-scope="scope">
              <el-button size="mini" type="text" style="color: #1ab394" icon="el-icon-view" @click='readMsg(scope.row)'>
                详情
              </el-button>
              <el-button size="mini" type="text" style="color: red" icon="el-icon-delete"
                         @click="handleDelete(scope.row)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum"
                    :limit.sync="queryParams.pageSize"
                    @pagination="getMsgList"/>
      </div>
    </el-dialog>


    <el-dialog :visible.sync="detailOpen" width="500px" append-to-body>
      <div slot="title">
        <span style="color: grey">时间：{{ msg.createTime }}</span>
      </div>
      <div>
        <p style="text-indent: 2em;line-height: 24px;font-size: 13px">
          {{ msg.msgContent }}
        </p>
      </div>
    </el-dialog>

  </div>


</template>

<script>
import {mapGetters} from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import TopNav from '@/components/TopNav'
import Hamburger from '@/components/Hamburger'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import Search from '@/components/HeaderSearch'
import RuoYiGit from '@/components/RuoYi/Git'
import RuoYiDoc from '@/components/RuoYi/Doc'
import {delMsg, getUnRead, listMsg, updateMsg} from "@/api/apply/msg";

export default {
  components: {
    Breadcrumb,
    TopNav,
    Hamburger,
    Screenfull,
    SizeSelect,
    Search,
    RuoYiGit,
    RuoYiDoc
  },
  data() {
    return {
      loading: true,
      msgOpen: false,
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      msgList: [],
      isReadSum: 0,
      msg: {
        createTime: null,
        msgContent: null,
      },
      detailOpen: false,

    }


  },


  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device'
    ]),
    setting: {
      get() {
        return this.$store.state.settings.showSettings
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'showSettings',
          value: val
        })
      }
    },
    isLogin() {
      return this.$store.state.user.token !== undefined;
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav
      }
    }
  },
  created() {
    if (this.$store.state.user.token !== undefined) {
      this.getMsgList();
    }
  },
  methods: {
    handlerMsg() {
      this.msgOpen = true;
      this.getMsgList();
    },


    readMsg(row) {
      this.detailOpen = true;
      this.msg.createTime = row.createTime;
      this.msg.msgContent = row.msgContent;
      if (row.isRead == 2) {
        let params = {
          msgId: row.msgId,
          isRead: 1,
        }
        updateMsg(params).then(res => {
          if (res.code == 200) {
            this.getMsgList()
          }
        })

      }
    },


    getUnReadSum() {
      getUnRead().then(res => {
        this.isReadSum = res.data;
      })

    },
    getMsgList() {
      listMsg(this.queryParams).then(res => {
        this.msgList = res.rows;
        this.total = res.total;
        this.loading = false;
        this.getUnReadSum();
      })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const msgIds = row.msgId || this.ids;
      this.$modal.confirm('是否此的通知？').then(function() {
        return delMsg(msgIds);
      }).then(() => {
        this.getMsgList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    }
    ,
    async logout() {
      this.$confirm('确定注销并退出系统吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('LogOut').then(() => {
          location.href = '/index';
        })
      }).catch(() => {
      });
    }
  }
}
</script>

<style lang="scss" scoped>
.no-su:hover {
  cursor: default;
}


.el-dialog-div {
  height: 60vh;
  overflow: auto;
  padding: 0 20px 0 0;
}

.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .topmenu-container {
    position: absolute;
    left: 50px;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
