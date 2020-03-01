<template>
  <div id="auth" class="auth">
    <el-card class="el-card">
      <el-form
        :model="user"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="用户名" prop="name">
          <el-input v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="name">
          <el-input v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'auth',
  data() {
    return {
      user: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    }
  },
  methods: {
    submitForm(formName) {
      let self = this
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.axios
            .post('/api/admin/user/', self.user)
            .then(response => {
              if (response.data.code === 200) {
                localStorage.setItem('token', response.data.msg)
                localStorage.setItem('user', JSON.stringify(response.data.data))
                self.$router.push({ name: 'admin' })
              } else {
                self.$message({
                  showClose: true,
                  message: response.data.msg,
                  type: 'error'
                })
              }
            })
            .catch(error => {
              self.$message({
                showClose: true,
                message: error,
                type: 'error'
              })
            })
        } else {
          self.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
#auth {
  width: 100%;
  height: 100%;
  .el-card {
    width: 800px;
    margin: 200px auto;
  }
}
</style>
