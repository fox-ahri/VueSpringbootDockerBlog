<template>
  <div id="me" class="me">
    <el-card class="box-card">
      <el-form ref="form" :model="user" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item label="旧密码">
          <el-input v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="user.pass1"></el-input>
        </el-form-item>
        <el-form-item label="确认密码">
          <el-input v-model="user.pass2"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="user.nickname"></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <el-input v-model="user.avatar"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="user.email"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="user.phone"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">保 存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'me',
  data() {
    return {
      user: {}
    }
  },
  methods: {
    onSubmit() {
      if (this.user.pass1 !== this.user.pass2) {
        this.$message({
          showClose: true,
          message: '密码不一致',
          type: 'error'
        })
        return
      }
      let self = this
      this.axios
        .put('/api/admin/user/', self.user)
        .then(response => {
          if (response.data.code === 200) {
            self.$message({
              showClose: true,
              message: response.data.msg,
              type: 'success'
            })
            localStorage.clear()
            self.$router.push({ name: 'auth' })
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
    }
  },
  mounted() {
    if (localStorage.getItem('user')) {
      this.user = JSON.parse(localStorage.getItem('user'))
    }
  }
}
</script>

<style lang="scss" scoped>
#me {
  width: 100%;
  height: 100%;
  .el-card {
    margin: 60px auto;
    width: 900px;
  }
}
</style>
