<template>
  <div>
    <el-container>
      <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
        <div style="display: inline">
          <el-input
            placeholder="通过站点名进行搜索,按回车开始..."
            clearable
            @change="searchNameChange"
            style="width: 300px;margin: 0px;padding: 0px;"
            size="mini"
            :disabled="advanceSearchViewVisible"
            @keyup.enter.native="searchModels"
            prefix-icon="el-icon-search"
            v-model="searchName">
          </el-input>
          <el-button type="primary" size="mini" style="margin-left: 5px" icon="el-icon-search"
                     @click="searchModels">
            搜索
          </el-button>
          <el-button slot="reference" type="primary" size="mini" style="margin-left: 5px"
                     @click="showAdvanceSearchView"><i
            class="fa fa-lg"
            v-bind:class="[advanceSearchViewVisible ? faangledoubleup:faangledoubledown]"
            style="margin-right: 5px"></i>高级搜索
          </el-button>
        </div>
        <div style="margin-left: 5px;margin-right: 20px;display: inline">
          <el-button type="primary" size="mini" icon="el-icon-plus"
                     @click="showAddModelView">
            添加站点
          </el-button>
        </div>
      </el-header>
      <el-main style="padding-left: 0px;padding-top: 0px">
        <div>
          <transition name="slide-fade">
            <div style="margin-bottom: 10px;border: 1px;border-radius: 5px;border-style: solid;padding: 5px 0px 5px 0px;box-sizing:border-box;border-color: #20a0ff"
                 v-show="advanceSearchViewVisible">
              <el-row>
                <el-col :span="3">
                  标签:
                  <el-select v-model="searchTag" style="width: 130px" size="mini"
                             placeholder="标签">
                    <el-option
                      v-for="item in tags"
                      :key="item"
                      :label="item"
                      :value="item">
                    </el-option>
                  </el-select>
                </el-col>
              </el-row>
              <el-row style="margin-top: 10px">
                <el-col :span="5" :offset="3" style="text-align: left">
                  <el-button size="mini" @click="cancelSearch">取消</el-button>
                  <el-button icon="el-icon-search" type="primary" size="mini" @click="searchModels">搜索
                  </el-button>
                </el-col>
              </el-row>
            </div>
          </transition>
          <el-table
            :data="models"
            v-loading="tableLoading"
            border
            stripe
            size="mini"
            style="width: 100%">
            <el-table-column
              prop="name"
              align="left"
              label="名称"
            >
            </el-table-column>
            <el-table-column
              prop="url"
              align="left"
              label="url">
            </el-table-column>
            <el-table-column
              align="left"
              prop="account"
              label="账号"
            >
            </el-table-column>
            <el-table-column
              align="left"
              prop="password"
              label="密码"
            >
            </el-table-column>
            <el-table-column
              align="left"
              prop="tag"
              label="标签"
            >
            </el-table-column>
            <el-table-column
              prop="remark"
              align="left"
              label="备注"
              width="50%">
            </el-table-column>
            <el-table-column
              align="left"
              label="修改时间">
              <template slot-scope="scope">{{ scope.row.updateTime | formatDate}}</template>
            </el-table-column>
            <el-table-column
              align="left"
              label="操作"
              width="120%">
              <template slot-scope="scope">
                <el-button @click="showEditModelView(scope.row)"
                           style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini">编辑
                </el-button>
                <el-button type="danger" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini"
                           @click="deleteModel(scope.row)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="display: flex;justify-content: space-between;margin: 2px">
            <el-pagination
              background
              :page-size="10"
              :current-page="currentPage"
              @current-change="currentChange"
              layout="prev, pager, next"
              :total="totalCount">
            </el-pagination>
          </div>
        </div>
      </el-main>
    </el-container>
    <el-form :model="model" :rules="rules" ref="addModelForm" style="margin: 0px;padding: 0px;">
      <div style="text-align: left">
        <el-dialog
          :title="dialogTitle"
          style="padding: 0px;"
          :close-on-click-modal="false"
          :visible.sync="dialogVisible"
          width="77%">
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="名称:" prop="name">
                  <el-input prefix-icon="el-icon-edit" v-model="model.name" size="mini"
                            style="width: 150px"
                            placeholder="请输入名称"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-form-item label="url:" prop="url">
                  <el-input prefix-icon="el-icon-edit" v-model="model.url" size="mini"
                            style="width: 150px"
                            placeholder="请输入url"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="账号:" prop="account">
                  <el-input prefix-icon="el-icon-edit" v-model="model.account" size="mini"
                            style="width: 150px"
                            placeholder="请输入账号"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="密码:" prop="password">
                  <el-input prefix-icon="el-icon-edit" v-model="model.password" size="mini"
                            style="width: 150px"
                            placeholder="请输入密码"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="标签:" prop="tag">
                  <el-select v-model="model.tag" style="width: 200px" size="mini" placeholder="标签">
                    <el-option
                      v-for="item in tags"
                      :key="item"
                      :label="item"
                      :value="item">
                    </el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="备注:" prop="remark">
                  <el-input prefix-icon="el-icon-edit" v-model="model.remark" size="mini"
                            style="width: 150px"
                            placeholder="请输入备注"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <span slot="footer" class="dialog-footer">
    <el-button size="mini" @click="cancelEdit">取 消</el-button>
    <el-button size="mini" type="primary" @click="addModel('addModelForm')">确 定</el-button>
  </span>
        </el-dialog>
      </div>
    </el-form>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        models: [],
        tags: ["微信", "qq", "邮箱", "工作", "生活"],
        totalCount: -1,
        currentPage: 1,
        searchName: '',
        searchTag: '',
        advanceSearchViewVisible: false,
        tableLoading: false,
        faangledoubleup: 'fa-angle-double-up',
        faangledoubledown: 'fa-angle-double-down',

        dialogTitle: '',
        dialogVisible: false,
        model: {
          name: '',
          url: '',
          account: '',
          password: '',
          tag:'',
          remark: '',
          updateTime: '',
        },
        rules: {
          name: [{required: true, message: '必填:站点名', trigger: 'blur'}],
          url: [{required: false, message: '选填:url', trigger: 'blur'}],
          account: [{required: false, message: '选填:账号', trigger: 'blur'}],
          password: [{required: false, message: '选填:密码', trigger: 'blur'}],
          tag: [{required: true, message: '必填:分类', trigger: 'blur'}],
          remark: [{required: false, message: '选填:备注', trigger: 'blur'}],
        }
      };
    },
    mounted: function () {
      this.initModelData();
      this.loadModels();
    },
    methods: {
      initModelData() {


      },
      loadModels() {
        var _this = this;
        this.tableLoading = true;
        this.getRequest("/site/page/" + (this.currentPage - 1) * 10 + "/10" + "?name=" + this.searchName + "&tag=" + this.searchTag).then(resp => {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;

            console.log(JSON.stringify(data));
            _this.models = data.sites;
            _this.totalCount = data.count;
          }
        })
      },
      searchModels() {
        this.loadModels();
      },

      // add contact
      cancelEdit() {
        this.dialogVisible = false;
        this.emptyModelData();
      },
      showAddModelView() {
        this.dialogTitle = "添加站点";
        this.dialogVisible = true;
      },
      addModel(formName) {
        var _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.dialogTitle == '编辑站点') {
              //更新
              this.tableLoading = true;
              this.postRequest("/site/update", _this.model).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyModelData( );
                  _this.loadModels();
                }
              })
            } else {
              //添加
              console.log(JSON.stringify(_this.model));
              this.tableLoading = true;
              this.postRequest("/site/add", _this.model).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyModelData();
                  _this.loadModels();
                }
              });
            }
          } else {
            return false;
          }
        });
      },
      showEditModelView(row) {
        console.log(row)
        this.dialogTitle = "编辑站点";
        this.model = row;
        this.model.name = row.name;
        this.model.url = row.url;
        this.model.account = row.account;
        this.model.password = row.password;
        this.model.tag = row.tag;
        this.model.remark = row.remark;
        // this.contact.birthday = this.formatDate(row.birthday);
        // delete this.contact.updateTime;
        this.dialogVisible = true;
      },
      deleteModel(row) {
        this.$confirm('此操作将永久删除站点[' + row.name + '], 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.doDelete({"siteIds": row.siteId});
        }).catch(() => {
        });
      },
      doDelete(ids) {
        this.tableLoading = true;
        var _this = this;
        this.postRequest("/site/delete", ids).then(resp => {
          _this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.loadModels();
          }
        })
      },
      emptyModelData() {
        this.model = {
          name: '',
          url: '',
          account: '',
          password: '',
          tag: '',
          remark: '',
          updateTime: '',
        }
      },
      ////search
      searchNameChange(val) {
        if (val == '') {
          this.loadModels();
        }
      },
      showAdvanceSearchView() {
        this.advanceSearchViewVisible = !this.advanceSearchViewVisible;
        this.searchName = '';
        if (!this.advanceSearchViewVisible) {
          this.emptyModelData();
          this.loadModels();
        }
      },
      cancelSearch(){
        this.advanceSearchViewVisible = false;
        this.emptyModelData();
        this.searchTag = '';
        this.loadModels();
      },
      ////page
      currentChange(currentChange) {
        this.currentPage = currentChange;
        this.loadModels();
      },
    }
  };
</script>
<style>
  .el-dialog__body {
    padding-top: 0px;
    padding-bottom: 0px;
  }

  .slide-fade-enter-active {
    transition: all .8s ease;
  }

  .slide-fade-leave-active {
    transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
  }

  .slide-fade-enter, .slide-fade-leave-to {
    transform: translateX(10px);
    opacity: 0;
  }
</style>
