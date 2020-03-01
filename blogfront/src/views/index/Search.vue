<template>
  <div
    id="search"
    class="search"
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(255, 255, 255, 0.6)"
  >
    <div class="btn">
      <el-input placeholder="请输入标题" v-model="filter.title">
        <template slot="prepend">请输入标题</template>
        <el-button slot="append" icon="el-icon-search" @click="search">搜 索</el-button>
      </el-input>
    </div>
    <el-card class="box-card" v-for="i in articles" :key="i.id">
      <span class="category" @click="search(i.id)">
        <span class="item">{{ i.title }}</span>
        <span class="item">
          <i class="el-icon-user">{{ i.user.nickname }}</i>
        </span>
        <span class="item">
          <i class="el-icon-time">{{ time(i.updateDate) }}</i>
        </span>
      </span>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'search',
  data() {
    return {
      categories: [],
      tags: [],
      articles: [],
      title: '',
      loading: false,
      filter: {
        title: '',
        category: '',
        tag: ''
      }
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
    search() {
      this.loading = true
      let self = this
      this.axios
        .post('/api/index/article', self.filter)
        .then(response => {
          if (response.data.code === 200) {
            self.articles = response.data.data
          } else {
            self.$message({
              showClose: true,
              message: response.data.ms,
              type: 'error'
            })
          }
          self.loading = false
        })
        .catch(error => {
          self.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
          self.loading = false
        })
    }
  },
  mounted() {
    if (this.$route.query.hasOwnProperty('category')) {
      this.filter.category = this.$route.query.category
      this.search()
    }
    if (this.$route.query.hasOwnProperty('tag')) {
      this.filter.tag = this.$route.query.tag
      this.search()
    }
  }
}
</script>

<style lang="scss" scoped>
#search {
  width: 100%;
  height: 100%;
  .btn {
    display: flex;
    margin: 40px 0;
  }
  .el-card {
    margin: 15px;
    .category {
      cursor: pointer;
      display: inline-block;
      width: 100%;
      height: 100%;
      .item {
        margin: 0 20px;
      }
    }
    &:hover .category .item {
      text-decoration: underline;
    }
    &:hover .category .item i {
      text-decoration: underline;
    }
  }
}
</style>
