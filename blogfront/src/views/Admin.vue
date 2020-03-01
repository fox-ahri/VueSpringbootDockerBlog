<template>
  <div id="admin" class="admin">
    <el-container>
      <el-header>
        <el-menu
          router
          :default-active="activeIndex"
          class="el-menu"
          mode="horizontal"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#66ccff"
        >
          <el-menu-item index="/index">首页</el-menu-item>
          <el-menu-item index="/admin/category">分类</el-menu-item>
          <el-menu-item index="/admin/tag">标签</el-menu-item>
          <el-menu-item index="/admin/article">文章</el-menu-item>
          <el-menu-item index="/admin/write">新建</el-menu-item>
          <el-menu-item index="/admin/about">关于</el-menu-item>
          <el-menu-item index="/admin/me">我的信息</el-menu-item>
        </el-menu>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'admin',
  data() {
    return {
      activeIndex: '/admin/category'
    }
  },
  mounted() {
    if (!localStorage.getItem('token')) {
      this.$router.push('/auth/login')
      return
    }
    this.activeIndex = this.$route.path
  }
}
</script>

<style lang="scss" scoped>
#admin {
  width: 100%;
  height: 100%;
  .el-header {
    padding: 0;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
  }
  .el-main {
    margin-top: 100px;
  }
}
</style>
