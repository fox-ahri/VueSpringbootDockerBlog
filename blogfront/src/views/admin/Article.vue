<template>
  <div
    id="article"
    class="article"
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)"
  >
    <div class="area">
      <div class="btn">
        <div class="item">
          <el-switch v-model="hideImage" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
        </div>
        <div class="item">
          <el-select
            v-model="currentCategory"
            placeholder="请选择分类"
            size="mini"
            @change="changeCategory"
          >
            <el-option label="全部分类" :value="0"></el-option>
            <el-option
              v-for="category in categories"
              :key="category.id"
              :label="category.name"
              :value="category.id"
            ></el-option>
          </el-select>
        </div>
        <div class="item">
          <el-pagination
            background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[10, 20, 50, 100]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="count"
          ></el-pagination>
        </div>
        <div class="item">
          <el-button size="mini" type="primary" @click="$router.push('/admin/write')">添加文章</el-button>
        </div>
      </div>
      <el-table
        border
        :data="articles.filter(article => !search || article.name.toLowerCase().includes(search.toLowerCase()))"
        style="width: 100%"
      >
        <el-table-column type="index"></el-table-column>
        <el-table-column label="标题" prop="title"></el-table-column>
        <el-table-column label="描述" prop="describe"></el-table-column>
        <el-table-column label="封面" width="180" align="center">
          <template slot-scope="scope">
            <span v-if="hideImage">{{ scope.row.image }}</span>
            <el-image
              v-else
              style="width: 100px; height: 100px"
              :src="scope.row.image"
              :fit="'contain'"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column label="类型" prop="type"></el-table-column>
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
        <el-table-column width="210" align="center">
          <template slot="header" slot-scope="scope">
            <el-input v-model="search" size="mini" placeholder="输入关键字搜索" />
          </template>
          <template slot-scope="scope">
            <el-button size="mini" @click="handleComment(scope.row)">评论</el-button>
            <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: 'article-page',
  data() {
    return {
      loading: false,
      articles: [],
      search: '',
      currentPage: 1,
      pageSize: 20,
      count: 0,
      categories: [],
      currentCategory: 0,
      hideImage: true
    }
  },
  methods: {
    handleSizeChange(val) {
      this.getArticle()
    },
    handleCurrentChange() {
      this.getArticle()
    },
    changeCategory() {
      this.getArticle()
    },
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
    handleComment(val) {
      this.$router.push({
        name: 'admin-articlecomment',
        query: { id: val.id }
      })
    },
    handleEdit(val) {
      this.$router.push({ name: 'admin-write', query: { edit: val.id } })
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
            .delete('/api/admin/article/' + val.id)
            .then(response => {
              if (response.data.code === 200) {
                self.$message({
                  showClose: true,
                  message: response.data.msg,
                  type: 'success'
                })
                self.articles = self.articles.filter(
                  article => article.id != val.id
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
    getArticle() {
      this.loading = true
      let self = this
      this.axios
        .get('/api/admin/article/')
        .then(response => {
          if (response.data.code === 200) {
            self.articles = response.data.data
            this.loading = false
          } else {
            self.$router.push({ name: 'auth' })
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
    },
    getCategory() {
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
    this.getArticle()
    this.getCategory()
  }
}
</script>

<style lang="scss" scoped>
#article {
  width: 100%;
  height: 100%;
  .area {
    width: 1400px;
    margin: 0 auto;
    .btn {
      display: flex;
      justify-content: flex-end;
      padding-bottom: 20px;
      align-items: center;
      .item {
        margin-left: 20px;
      }
    }
  }
}
</style>
