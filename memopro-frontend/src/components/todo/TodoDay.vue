<template>
  <div>
    <el-container>
      <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
        <div style="display: inline">
          <el-input
            placeholder="日期，例如20190715"
            clearable
            style="width: 300px;margin: 0px;padding: 0px;"
            size="mini"
            prefix-icon="el-icon-search"
            v-model="searchTime">
          </el-input>
          <el-select v-model="searchStatus" style="width: 130px" size="mini"
                     placeholder="状态">
            <el-option
              v-for="item in status"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
          <el-select v-model="searchTag" style="width: 130px" size="mini"
                     placeholder="标签">
            <el-option
              v-for="item in tags"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
          <el-button type="primary" size="mini" style="margin-left: 5px" icon="el-icon-search"
                     @click="searchModels">
            搜索
          </el-button>
        </div>
        <div style="margin-left: 5px;margin-right: 20px;display: inline">
          <el-button type="primary" size="mini" icon="el-icon-plus"
                     @click="showAddModelView">
            添加待办
          </el-button>
        </div>
      </el-header>
      <el-main style="padding-left: 0px;padding-top: 0px">
        <div>
          <el-table
            :data="models"
            v-loading="tableLoading"
            border
            stripe
            size="mini"
            style="width: 100%">
            <el-table-column
              prop="index"
              align="left"
              label="序号"
            >
            </el-table-column>
            <el-table-column
              prop="name"
              align="left"
              label="内容">
            </el-table-column>
            <el-table-column
              align="left"
              prop="tag"
              label="标签"
            >
            </el-table-column>
            <el-table-column
              align="left"
              label="状态"
            >
              <template slot-scope="scope">
                {{ scope.row.status }}
                <el-button type="primary" icon="el-icon-edit" circle
                           @click="startDo(scope.row)"
                           style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini">
                </el-button>
                <el-button type="success" icon="el-icon-check" circle
                           style="padding: 3px 4px 3px 4px;margin: 2px" size="mini"
                           @click="finishDo(scope.row)">
                </el-button>
              </template>
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
                <el-form-item label="内容:" prop="name">
                  <el-input prefix-icon="el-icon-edit" v-model="model.name" size="mini"
                            style="width:400%"
                            type="textarea"
                            :rows="2"
                            placeholder="请输入内容"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="时间:" prop="time">
                  <el-select v-model="model.time" style="width: 200px" size="mini" placeholder="时间">
                    <el-option
                      v-for="item in times"
                      :key="item"
                      :label="item"
                      :value="item">
                    </el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
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
  import {nextDate} from "element-ui/lib/utils/date-util";
  export default {
    data() {
      return {
        models: [],
        status: ["未开始", "进行中", "已完成"],
        tags: ["工作", "学习", "实践", "阅读", "其它"],
        times: ["今天","明天","后天"],
        totalCount: -1,
        currentPage: 1,
        searchTime: this.formatDayTime(new Date()),
        searchStatus: '',
        searchTag: '',
        searchCategory: '每日待办',
        tableLoading: false,
        dialogTitle: '',
        dialogVisible: false,
        model: this.getEmptyModel(),
        rules: {
          name: [{required: true, message: '必填:待办内容', trigger: 'blur'}],
          time: [{required: true, message: '必填:时间', trigger: 'blur'}],
          tag: [{required: true, message: '必填:分类', trigger: 'blur'}],
        },

      };
    },
    mounted: function () {
      this.initModelData();
      this.loadModels();
    },
    methods: {
      getTimeStr(str){
        let date = new Date();
        if(str==='明天'){
          date = nextDate(date,1);
        }else if(str==='后天'){
          date = nextDate(date,2);
        }
        return this.formatDayTime(date);
      },

      initModelData() {
      },
      getEmptyModel() {
        return {
          name: '',
          index : '',
          category: '',
          time: '今天',
          status: '',
          tag: '',
          parentId: '',
          updateTime: '',
        }
      },
      loadModels() {
        var _this = this;
        this.tableLoading = true;
        this.getRequest("/todo/page/" + (this.currentPage - 1) * 10 + "/10" + "?status=" + this.searchStatus + "&tag=" + this.searchTag + "&time=" + this.searchTime + "&category=" + this.searchCategory).then(resp => {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;

            console.log(JSON.stringify(data));
            _this.models = data.todos;
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
        this.dialogTitle = "添加待办";
        this.dialogVisible = true;
      },
      addModel(formName) {
        var _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.dialogTitle === '编辑待办') {
              //更新
              this.tableLoading = true;
              _this.model.parentId = '';
              this.postRequest("/todo/update", _this.model).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyModelData();
                  _this.loadModels();
                }
              })
            } else {
              //添加
              console.log(JSON.stringify(_this.model));
              this.tableLoading = true;
              _this.model.status = "未开始";
              _this.model.category = "每日待办";
              _this.model.time = this.getTimeStr(_this.model.time);
              this.postRequest("/todo/add", _this.model).then(resp => {
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
      startDo(row){
        var _this = this;
        var val = {"todoId":row.todoId,status:"进行中"};
        this.postRequest("/todo/update", val).then(resp => {
          _this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.loadModels();
          }
        })
      },
      finishDo(row){
        var _this = this;
        var val = {"todoId":row.todoId,status:"已完成"};
        this.postRequest("/todo/update", val).then(resp => {
          _this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.loadModels();
          }
        })
      },
      showEditModelView(row) {
        console.log(row)
        this.dialogTitle = '编辑待办';
        this.model = row;
        // this.contact.birthday = this.formatDate(row.birthday);
        // delete this.contact.updateTime;
        this.dialogVisible = true;
      },
      deleteModel(row) {
        this.$confirm('此操作将永久删除待办[' + row.name + '], 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.doDelete({"todoIds": row.todoId});
        }).catch(() => {
        });
      },
      doDelete(ids) {
        this.tableLoading = true;
        var _this = this;
        this.postRequest("/todo/delete", ids).then(resp => {
          _this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.loadModels();
          }
        })
      },
      emptyModelData() {
        this.model = this.getEmptyModel()
      },
      emptySearchData() {
        this.searchTime = '';
        this.searchStatus = '';
        this.searchTag = '';
      },
      cancelSearch() {
        this.emptyModelData();
        this.emptySearchData();
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
