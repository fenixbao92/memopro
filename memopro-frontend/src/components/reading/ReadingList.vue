<template>
  <div>
    <el-container>
      <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
        <div style="display: inline">
          <el-input
            placeholder="通过姓名进行搜索,按回车开始..."
            clearable
            @change="searchNameChange"
            style="width: 300px;margin: 0px;padding: 0px;"
            size="mini"
            :disabled="advanceSearchViewVisible"
            @keyup.enter.native="searchContacts"
            prefix-icon="el-icon-search"
            v-model="searchName">
          </el-input>
          <el-button type="primary" size="mini" style="margin-left: 5px" icon="el-icon-search"
                     @click="searchContacts">
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
                     @click="showAddContactView">
            添加联系人
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
                  <el-button icon="el-icon-search" type="primary" size="mini" @click="searchContacts">搜索
                  </el-button>
                </el-col>
              </el-row>
            </div>
          </transition>
          <el-table
            :data="contacts"
            v-loading="tableLoading"
            border
            stripe
            size="mini"
            style="width: 100%">
            <el-table-column
              prop="name"
              align="left"
              label="姓名"
            >
            </el-table-column>
            <el-table-column
              prop="birthday"

              align="left"
              label="生日">
            </el-table-column>
            <el-table-column
              align="left"
              prop="phone"
              label="手机"
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
                <el-button @click="showEditContactView(scope.row)"
                           style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini">编辑
                </el-button>
                <el-button type="danger" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini"
                           @click="deleteContact(scope.row)">删除
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
    <el-form :model="contact" :rules="rules" ref="addContactForm" style="margin: 0px;padding: 0px;">
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
                <el-form-item label="姓名:" prop="name">
                  <el-input prefix-icon="el-icon-edit" v-model="contact.name" size="mini"
                            style="width: 150px"
                            placeholder="请输入姓名"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="生日:" prop="birthday">
                  <el-input prefix-icon="el-icon-edit" v-model="contact.birthday" size="mini"
                            style="width: 150px"
                            placeholder="请输入生日"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="5">
              <div>
                <el-form-item label="电话:" prop="phone">
                  <el-input prefix-icon="el-icon-edit" v-model="contact.phone" size="mini"
                            style="width: 150px"
                            placeholder="请输入电话"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <div>
                <el-form-item label="标签:" prop="tag">
                  <el-select v-model="contact.tag" style="width: 200px" size="mini" placeholder="标签">
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
                  <el-input prefix-icon="el-icon-edit" v-model="contact.remark" size="mini"
                            style="width: 150px"
                            placeholder="请输入备注"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <span slot="footer" class="dialog-footer">
    <el-button size="mini" @click="cancelEdit">取 消</el-button>
    <el-button size="mini" type="primary" @click="addContact('addContactForm')">确 定</el-button>
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
        contacts: [],
        tags: ["家人", "兄弟", "姐妹", "同学", "朋友", "同事"],
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
        contact: {
          name: '',
          birthday: '',
          phone: '',
          tag: '',
          remark: '',
          updateTime: '',
        },
        rules: {
          name: [{required: true, message: '必填:姓名', trigger: 'blur'}],
          birthday: [{required: false, message: '选填:生日', trigger: 'blur'}],
          phone: [{required: false, message: '选填:手机', trigger: 'blur'}],
          tag: [{required: true, message: '必填:分类', trigger: 'blur'}],
          remark: [{required: false, message: '选填:备注', trigger: 'blur'}],
        }
      };
    },
    mounted: function () {
      this.initData();
      this.loadContacts();
    },
    methods: {
      initData() {


      },
      loadContacts() {
        var _this = this;
        this.tableLoading = true;
        this.getRequest("/contact/page/" + (this.currentPage - 1) * 10 + "/10" + "?name=" + this.searchName + "&tag=" + this.searchTag).then(resp => {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;

            console.log(JSON.stringify(data));
            _this.contacts = data.contacts;
            _this.totalCount = data.count;
          }
        })
      },
      searchContacts() {
        this.loadContacts();
      },

      // add contact
      cancelEdit() {
        this.dialogVisible = false;
        this.emptyContactData();
      },
      showAddContactView() {
        this.dialogTitle = "添加联系人";
        this.dialogVisible = true;
      },
      addContact(formName) {
        var _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.dialogTitle == '编辑联系人') {
              //更新
              this.tableLoading = true;
              this.postRequest("/contact/update", _this.contact).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyContactData();
                  _this.loadContacts();
                }
              })
            } else {
              //添加
              console.log(JSON.stringify(_this.contact));
              this.tableLoading = true;
              this.postRequest("/contact/add", _this.contact).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyContactData();
                  _this.loadContacts();
                }
              });
            }
          } else {
            return false;
          }
        });
      },
      showEditContactView(row) {
        console.log(row)
        this.dialogTitle = "编辑联系人";
        this.contact = row;
        this.contact.name = row.name;
        this.contact.birthday = row.birthday;
        this.contact.phone = row.phone;
        this.contact.tag = row.tag;
        // this.contact.birthday = this.formatDate(row.birthday);
        // delete this.contact.updateTime;
        this.dialogVisible = true;
      },
      deleteContact(row) {
        this.$confirm('此操作将永久删除联系人[' + row.name + '], 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.doDelete({"contactIds": row.contactId});
        }).catch(() => {
        });
      },
      doDelete(ids) {
        this.tableLoading = true;
        var _this = this;
        this.postRequest("/contact/delete", ids).then(resp => {
          _this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.loadContacts();
          }
        })
      },
      emptyContactData() {
        this.contact = {
          name: '',
          birthday: '',
          phone: '',
          tag: '',
          remark: '',
          updateTime: '',
        }
      },
      ////search
      searchNameChange(val) {
        if (val == '') {
          this.loadContacts();
        }
      },
      showAdvanceSearchView() {
        this.advanceSearchViewVisible = !this.advanceSearchViewVisible;
        this.searchName = '';
        if (!this.advanceSearchViewVisible) {
          this.emptyContactData();
          this.loadContacts();
        }
      },
      cancelSearch(){
        this.advanceSearchViewVisible = false;
        this.emptyContactData();
        this.searchTag = '';
        this.loadContacts();
      },
      ////page
      currentChange(currentChange) {
        this.currentPage = currentChange;
        this.loadContacts();
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
