<template>
  <div
    id="category"
    class="category"
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)"
  >
    <div class="area">
      <div class="btn">
        <el-button size="mini" type="primary" @click="form.edit = false;dialogCategory = true">添 加</el-button>
      </div>
      <el-table
        border
        :data="categories.filter(category => !search || category.name.toLowerCase().includes(search.toLowerCase()))"
        style="width: 100%"
      >
        <el-table-column type="index"></el-table-column>
        <el-table-column label="分类名" prop="name"></el-table-column>
        <el-table-column label="描述" prop="describe"></el-table-column>
        <el-table-column label="创建日期" width="180" align="center">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ time(scope.row.createDate) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="更新日期" width="180" align="center">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ time(scope.row.updateDate) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="用户" width="140" align="center">
          <template slot-scope="scope">
            <i class="el-icon-user"></i>
            <span style="margin-left: 10px">{{ scope.row.user.nickname }}</span>
          </template>
        </el-table-column>
        <el-table-column label="文章数" prop="count" width="80" align="center"></el-table-column>
        <el-table-column align="center" width="143">
          <template slot="header" slot-scope="scope">
            <el-input v-model="search" size="mini" placeholder="输入关键字搜索" />
          </template>
          <template slot-scope="scope">
            <el-button size="mini" @click="openEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog title="分类" :visible.sync="dialogCategory" width="60%" :before-close="closeDialog">
      <el-form :model="form" ref="form" label-width="100px">
        <el-form-item label="分类名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="描 述" prop="describe">
          <el-input
            type="textarea"
            maxlength="120"
            show-word-limit
            rows="4"
            v-model="form.describe"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogCategory = false">取 消</el-button>
        <el-button type="primary" @click="handlerSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'category',
  data() {
    return {
      loading: false,
      categories: [],
      search: '',
      dialogCategory: false,
      form: {
        id: '',
        name: '',
        describe: ''
      },
      edit: false
    }
  },
  methods: {
    time(time) {
      let d = new Date(time)
      return (
        d.getFullYear() +
        '-' +
        (d.getMonth() + 1) +
        '-' +
        d.getDate() +
        ' ' +
        d.getHours() +
        ':' +
        d.getMinutes() +
        ':' +
        d.getSeconds()
      )
    },
    closeDialog() {
      this.dialogCategory = false
      this.form = {
        id: '',
        name: '',
        describe: ''
      }
      this.edit = false
    },
    handlerSubmit() {
      if (this.edit) {
        this.loading = true
        let self = this
        this.axios
          .put('/api/admin/category/', self.form)
          .then(response => {
            if (response.data.code === 200) {
              self.$message({
                showClose: true,
                message: response.data.msg,
                type: 'success'
              })
              self.dialogCategory = false
            } else {
              self.$message({
                showClose: true,
                message: response.data.msg,
                type: 'error'
              })
              self.getData()
            }
            this.loading = false
          })
          .catch(error => {
            self.$message({
              showClose: true,
              message: error,
              type: 'error'
            })
            this.loading = false
          })
      } else {
        this.loading = true
        let self = this
        this.axios
          .post('/api/admin/category/', self.form)
          .then(response => {
            if (response.data.code === 200) {
              self.$message({
                showClose: true,
                message: response.data.msg,
                type: 'success'
              })
              self.getData()
              self.dialogCategory = false
              self.form = {
                id: '',
                name: '',
                describe: ''
              }
            } else {
              self.$router.push({ name: 'auth' })
              self.$message({
                showClose: true,
                message: response.data.msg,
                type: 'error'
              })
            }
            this.loading = false
          })
          .catch(error => {
            self.$message({
              showClose: true,
              message: error,
              type: 'error'
            })
            this.loading = false
          })
      }
    },
    handleDelete(val) {
      this.loading = true
      this.$confirm('此操作将永久删除该分类, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let self = this
          this.axios
            .delete('/api/admin/category/' + val.id)
            .then(response => {
              if (response.data.code === 200) {
                self.$message({
                  showClose: true,
                  message: response.data.msg,
                  type: 'success'
                })
                self.categories = self.categories.filter(
                  category => category.id != val.id
                )
              } else {
                self.$router.push({ name: 'auth' })
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
          this.loading = false
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
          this.loading = false
        })
    },
    openEdit(val) {
      this.form = val
      this.edit = true
      this.dialogCategory = true
    },
    getData() {
      this.loading = true
      let self = this
      this.axios
        .get('/api/admin/category/')
        .then(response => {
          if (response.data.code === 200) {
            self.categories = response.data.data
            this.loading = false
          } else {
            self.$router.push({ name: 'auth' })
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
          this.loading = false
        })
    }
  },
  mounted() {
    this.getData()
  }
}
</script>

<style lang="scss" scoped>
#category {
  width: 100%;
  height: 100%;
  .area {
    width: 1300px;
    margin: 0 auto;
    .btn {
      text-align: right;
      padding-bottom: 20px;
    }
  }
}
</style>
