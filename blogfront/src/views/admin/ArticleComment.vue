<template>
  <div id="article-comment" class="article-comment">
    <div class="area">
      <el-table
        border
        :data="comments.filter(comment => !search || comment.content.toLowerCase().includes(search.toLowerCase()))"
        style="width: 100%"
      >
        <el-table-column type="index"></el-table-column>
        <el-table-column label="内容" prop="content"></el-table-column>
        <el-table-column label="用户" width="120" align="center">
          <template slot-scope="scope">
            <i class="el-icon-user"></i>
            <span style="margin-left: 10px">{{ scope.row.user_nickname }}</span>
          </template>
        </el-table-column>
        <el-table-column label="时间" width="180" align="center">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ time(scope.row.date) }}</span>
          </template>
        </el-table-column>
        <el-table-column width="140" align="center">
          <template slot="header" slot-scope="scope">
            <el-input v-model="search" size="mini" placeholder="输入关键字搜索" />
          </template>
          <template slot-scope="scope">
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: 'article-comment',
  data() {
    return {
      articles: {},
      comments: [],
      search: ''
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
    handleDelete(val) {
      this.$confirm('此操作将永久删除该分类, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let self = this
          this.axios
            .delete('/api/admin/comment/' + val.id)
            .then(response => {
              if (response.data.code === 200) {
                self.$message({
                  showClose: true,
                  message: response.data.msg,
                  type: 'success'
                })
                self.comments = self.comments.filter(
                  comment => comment.id != val.id
                )
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
          this.loading = false
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
          this.loading = false
        })
    }
  },
  mounted() {
    if (this.$route.query.hasOwnProperty('id')) {
      let self = this
      this.axios
        .get('/api/admin/comment/' + self.$route.query.id)
        .then(response => {
          self.comments = response.data.data
        })
        .catch(error => {
          self.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        })
    }
  }
}
</script>

<style lang="scss" scoped>
#article-comment {
  width: 100%;
  height: 100%;
  .area {
    width: 1000px;
    margin: 0 auto;
  }
}
</style>
