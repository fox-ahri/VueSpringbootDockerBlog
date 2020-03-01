<template>
  <div
    id="label"
    class="label"
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(255, 255, 255, 0.6)"
  >
    <el-tag v-for="(i, index) in tags" :type="getType(index)" :key="i.id">
      <span class="tag" @click="search(i.id)">{{ i.name }}</span>
    </el-tag>
  </div>
</template>

<script>
export default {
  name: 'label-page',
  data() {
    return {
      tags: [],
      loading: false
    }
  },
  methods: {
    getType(index) {
      return [
        'primary',
        'success',
        'info',
        'warning',
        'danger',
        'primary',
        'success',
        'info',
        'warning',
        'danger'
      ][index % 10]
    },
    search(id) {
      this.$router.push({
        name: 'search',
        query: {
          tag: id
        }
      })
    }
  },
  mounted() {
    this.loading = true
    let self = this
    this.axios
      .get('/api/index/tag/')
      .then(response => {
        if (response.data.code === 200) {
          self.tags = response.data.data
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
#label {
  width: 100%;
  height: 100%;
  .el-tag {
    margin: 15px;
    .tag {
      cursor: pointer;
    }
  }
}
</style>
