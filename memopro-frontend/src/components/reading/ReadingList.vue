<template>
  <div>
    <el-container>
      <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
        <div style="display: inline">
          <el-input
            placeholder="书名"
            clearable
            style="width: 300px;margin: 0px;padding: 0px;"
            size="mini"
            prefix-icon="el-icon-search"
            v-model="searchName">
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
            添加书
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
              min-width=35
            >
            </el-table-column>
            <el-table-column
              prop="name"
              align="left"
              label="书名"
              min-width=35>
            </el-table-column>
            <el-table-column
              align="left"
              prop="url"
              type="image"
              label="封面图"
              min-width=25
            >
              <template slot-scope="scope">
                <el-popover
                  placement="right"
                  title=""
                  trigger="hover">
                  <img :src="scope.row.url" min-width="140" height="300"/>
                  <img slot="reference" :src="scope.row.url" :alt="scope.row.url"
                       style="max-height: 80px;max-width: 130px">
                </el-popover>
              </template>
            </el-table-column>
            <el-table-column
              align="left"
              prop="status"
              label="状态"
              min-width=25
            >
            </el-table-column>
            <el-table-column
              align="left"
              prop="thoughts"
              label="心得"
              min-width=25
            >
            </el-table-column>
            <el-table-column
              align="left"
              prop="remark"
              label="备注"
              min-width=25
            >
            </el-table-column>
            <el-table-column
              align="left"
              label="修改时间"
              min-width=50>
              <template slot-scope="scope">{{ scope.row.updateTime | formatDate}}</template>
            </el-table-column>
            <el-table-column
              align="left"
              label="操作"
              min-width=35
            >
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
                <el-form-item label="书名:" prop="name">
                  <el-input prefix-icon="el-icon-edit" v-model="model.name" size="mini"
                            style="width:400%"
                            placeholder="请输入书名"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="4">
              <div>
                <el-form-item label="编号:" prop="index">
                  <el-input prefix-icon="el-icon-edit" v-model="model.index" size="mini"
                            style="width:70%"
                            placeholder="请输入编号"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="4">
              <div>
                <el-form-item label="标签:" prop="tag">
                  <el-select v-model="model.tag" style="width: 70%" size="mini" placeholder="标签">
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
            <el-col :span="4">
              <div>
                <el-form-item label="状态:" prop="status">
                  <el-select v-model="model.status" style="width: 70%" size="mini" placeholder="状态">
                    <el-option
                      v-for="item in status"
                      :key="item"
                      :label="item"
                      :value="item">
                    </el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="封面图:">
                <el-upload
                  class="avatar-uploader"
                  action="/book/photoUpload/"
                  :show-file-list="false"
                  :on-progress="handleProgress"
                  :on-success="handleSuccess"
                  :before-upload="beforeUpload">
                  <img v-if="model.url" :src="model.url" class="avatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                  <el-progress v-show="model.pictureFlagStart" type="circle" :percentage="model.uploadPercent" style="position: absolute;top:10%;left:15%;" ></el-progress>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="心得:" prop="thoughts">
                  <el-input prefix-icon="el-icon-edit" v-model="model.thoughts" size="mini"
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
                <el-form-item label="备注:" prop="remark">
                  <el-input prefix-icon="el-icon-edit" v-model="model.remark" size="mini"
                            style="width:400%"
                            type="textarea"
                            :rows="1"
                            placeholder="请输入内容"></el-input>
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
        status: ["规划中", "阅读中", "已读完"],
        tags: ["人文-人物传记", "人文-小说", "人文-生活", "技术-前端", "技术-后端", "其它"],
        totalCount: -1,
        currentPage: 1,
        searchName: '',
        searchStatus: '',
        searchTag: '',
        searchCategory: '每日待办',
        tableLoading: false,
        dialogTitle: '',
        dialogVisible: false,
        model: this.getEmptyModel(),
        rules: {
          index: [{required: true, message: '必填:编号', trigger: 'blur'}],
          name: [{required: true, message: '必填:书名', trigger: 'blur'}],
          tag: [{required: true, message: '必填:分类', trigger: 'blur'}],
        },

      };
    },
    mounted: function () {
      this.initModelData();
      this.loadModels();
    },
    methods: {
      getTimeStr(str) {
        let date = new Date();
        if (str === '明天') {
          date = nextDate(date, 1);
        } else if (str === '后天') {
          date = nextDate(date, 2);
        }
        return this.formatDayTime(date);
      },
      initModelData() {
      },
      getEmptyModel() {
        return {
          imageUrl: '',
          index: '',
          name: '',
          tag: '',
          url: '',
          status: '规划中',
          thoughts: '',
          remark: '',
          updateTime: '',
          pictureFlagStart: false,
          uploadPercent:0,
        }
      },
      loadModels() {
        var _this = this;
        this.tableLoading = true;
        this.getRequest("/book/page/" + (this.currentPage - 1) * 10 + "/10" + "?status=" + this.searchStatus + "&tag=" + this.searchTag + "&name=" + this.searchName).then(resp => {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;

            console.log(JSON.stringify(data));
            _this.models = data.books;
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
        this.dialogTitle = "添加书";
        this.dialogVisible = true;
      },
      addModel(formName) {
        var _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.dialogTitle === '编辑书') {
              //更新
              this.tableLoading = true;
              this.postRequest("/book/update", _this.model).then(resp => {
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
              this.postRequest("/book/add", _this.model).then(resp => {
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
        this.dialogTitle = '编辑书';
        this.model = row;
        // this.contact.birthday = this.formatDate(row.birthday);
        // delete this.contact.updateTime;
        this.dialogVisible = true;
      },
      deleteModel(row) {
        this.$confirm('此操作将永久删除此书[' + row.name + '], 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.doDelete({"bookIds": row.bookId});
        }).catch(() => {
        });
      },
      doDelete(ids) {
        this.tableLoading = true;
        var _this = this;
        this.postRequest("/book/delete", ids).then(resp => {
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
      ////page
      currentChange(currentChange) {
        this.currentPage = currentChange;
        this.loadModels();
      },

      ////file upload
      handleSuccess(res, file) {
        // this.imageUrl = URL.createObjectURL(file.raw);
        this.model.url = res.obj;
        this.model.pictureFlagStart=false;
      },
      beforeUpload(file) {
        // const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 5;
        // if (!isJPG) {
        //   this.$message.error('上传头像图片只能是 JPG 格式!');
        // }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 5MB!');
        }
        this.model.pictureFlagStart=true;
        return isLt2M;
      },
      handleProgress(event, file, fileList){
        this.model.uploadPercent = file.percentage.toFixed(0);
      }
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

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }

</style>
