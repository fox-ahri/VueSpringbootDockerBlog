<template>
  <div
    id="category"
    class="category"
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(255, 255, 255, 0.6)"
  >
    <el-card class="box-card" v-for="i in categories" :key="i.id">
      <span class="category" @click="search(i.id)">{{ i.name }}</span>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'category',
  data() {
    return {
      categories: [],
      loading: false
    }
  },
  methods: {
    search(id) {
      this.$router.push({
        name: 'search',
        query: {
          category: id
        }
      })
    }
  },
  mounted() {
    this.loading = true
    let self = this
    this.axios
      .get('/api/index/category/')
      .then(response => {
        if (response.data.code === 200) {
          self.categories = response.data.data
        } else {
          self.$message({
            showClose: true,
            message: response.data.msg,
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
}
</script>

<style lang="scss" scoped>
#category {
  width: 100%;
  height: 100%;
  .el-card {
    margin: 15px;
    .category {
      cursor: pointer;
      display: inline-block;
      width: 100%;
      height: 100%;
    }
    &:hover .category {
      text-decoration: underline;
    }
  }
}
</style>
