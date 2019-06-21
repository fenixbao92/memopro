<template>
  <div>
    <el-form :rules="rules" :model="loginForm" class="login-container" label-position="left"
             label-width="0px" v-loading="loading">
      <h3 class="login_title">MemoPro</h3>
      <el-form-item prop="account">
        <el-input type="text" v-model="loginForm.account"
                  auto-complete="off" placeholder="账号"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" v-model="loginForm.password"
                  auto-complete="off" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item style="width: 100%">
        <el-button type="primary" style="width: 100%" @click="submitClick">登录</el-button>
      </el-form-item>

      <el-container direction="horizontal" type="flex" justify="space-around">
          <el-button icon="el-icon-user-solid" style="flex: 1" @click="tour">游客</el-button>
          <el-button icon="el-icon-s-custom" style="flex: 1" @click="showRegister">注册</el-button>
          <el-button icon="el-icon-search" style="flex: 1" width="120" @click="goGitHub">GitHub</el-button>
      </el-container>
    </el-form>
    <el-dialog title="注册" :visible.sync="dialogFormVisible" @close="dialogFormVisible = false,loading=false">
      <el-form :model="registerForm" :rules="rulesRegister" ref="registerFormRef">
        <el-form-item label="账号" :label-width="formLabelWidth" prop="account">
          <el-input prefix-icon="el-icon-edit" v-model="registerForm.account" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
          <el-input type="password" prefix-icon="el-icon-edit" v-model="registerForm.password"
                    autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false,loading=false">取 消
        </el-button>
        <el-button type="primary" @click="register('registerFormRef')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        rules: {
          account: [{required: true, message: '请输入用户名', trigger: 'blur'}],
          password: [{required: true, message: '请输入密码', trigger: 'blur'}]
        },
        loginForm: {
          account: '',
          password: '',
        },
        loading: false,
        dialogFormVisible: false,
        registerForm: {
          account: '',
          password: '',
        },
        formLabelWidth: '120px',
        rulesRegister: {
          account: [{required: true, message: '必填:账号', trigger: 'blur'}],
          password: [{required: true, message: '必填:密码', trigger: 'blur'}],
        }

      }
    },
    methods: {
      submitClick: function () {
        var _this = this;
        this.loading = true;
        this.postRequest('/login', {
          username: this.loginForm.account,
          password: this.loginForm.password
        }).then(resp => {
          _this.loading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.$store.commit('login', data);
            var path = _this.$route.query.redirect;
            _this.$router
              .replace({path: path == '/' || path == undefined ? '/home' : path});
          }
        });
      },
      tour(){
        var _this = this;
        this.loading = true;
        this.postRequest('/login', {
          username: "tourist",
          password: "tourist"
        }).then(resp => {
          _this.loading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.$store.commit('login', data);
            var path = _this.$route.query.redirect;
            _this.$router
              .replace({path: path == '/' || path == undefined ? '/home' : path});
          }
        });
      },
      register(formName) {
        var _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.loading = true;
            this.postRequest('/config/register', {
              account: _this.registerForm.account,
              password: _this.registerForm.password
            }).then(resp => {
              _this.loading = false;
              _this.dialogFormVisible = false;
              if (resp && resp.status == 200) {
                console.log("register success!");
              }
            });
          } else {
            return false;
          }
        });
      },
      showRegister() {
        this.dialogFormVisible = true;
        this.loading = true;
      },
      goGitHub() {
        window.location.href = "https://github.com/fenixbao92/memopro";
      }
    }
  }
</script>
<style>
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }

  .login_remember {
    margin: 0px 0px 35px 0px;
    text-align: left;
  }
</style>

