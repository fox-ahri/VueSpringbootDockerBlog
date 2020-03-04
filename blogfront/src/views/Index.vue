<template>
  <div id="index" class="index">
    <div class="container">
      <menu class="menu">
        <div class="btn nocopy">
          <div class="logo">AHRIKNOW</div>
          <i class="el-icon-s-operation" @click="drawer = !drawer"></i>
        </div>
        <el-drawer size="400" :visible.sync="drawer" :modal="false" direction="ltr">
          <ul class="nocopy" @click="drawer = false">
            <li>
              <router-link to="/index/home">
                <i class="el-icon-s-home">首页</i>
                <span></span>
              </router-link>
            </li>
            <li>
              <router-link to="/index/cate">
                <i class="el-icon-s-grid">所有分类</i>
                <el-tag size="mini" type="info">{{ category_count }}</el-tag>
              </router-link>
            </li>
            <li>
              <router-link to="/index/tag">
                <i class="el-icon-s-ticket">所有标签</i>
                <el-tag size="mini" type="info">{{ tag_count }}</el-tag>
              </router-link>
            </li>
              <li>
                <router-link to="/index/file">
                  <i class="el-icon-s-finance">文章归档</i>
                  <el-tag size="mini" type="info">{{ article_count }}</el-tag>
                </router-link>
              </li>
            <li>
              <router-link to="/index/search">
                <i class="el-icon-search">搜索</i>
                <span></span>
              </router-link>
            </li>
            <li>
              <router-link to="/index/about">
                <i class="el-icon-info">我</i>
                <span></span>
              </router-link>
            </li>
          </ul>
        </el-drawer>
      </menu>
      <aside class="aside">
        <div class="menu-bar">
          <div class="profile">
            <el-image src="http://ahriknow.oss-cn-beijing.aliyuncs.com/avatar.jpg" lazy fit="cover">
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
              <div slot="placeholder" class="image-slot">
                加载中
                <span class="dot">...</span>
              </div>
            </el-image>
            <div class="info">
              <span>AHRIKNOW</span>
            </div>
          </div>
          <div class="menu-list nocopy">
            <ul>
              <li>
                <router-link to="/index/home">
                  <i class="el-icon-s-home">首页</i>
                  <span></span>
                </router-link>
              </li>
              <li>
                <router-link to="/index/cate">
                  <i class="el-icon-s-grid">所有分类</i>
                  <el-tag size="mini" type="info">{{ category_count }}</el-tag>
                </router-link>
              </li>
              <li>
                <router-link to="/index/tag">
                  <i class="el-icon-s-ticket">所有标签</i>
                  <el-tag size="mini" type="info">{{ tag_count }}</el-tag>
                </router-link>
              </li>
              <li>
                <router-link to="/index/file">
                  <i class="el-icon-s-finance">文章归档</i>
                  <el-tag size="mini" type="info">{{ article_count }}</el-tag>
                </router-link>
              </li>
              <li>
                <router-link to="/index/search">
                  <i class="el-icon-search">搜索</i>
                  <span></span>
                </router-link>
              </li>
              <li>
                <router-link to="/index/about">
                  <i class="el-icon-info">我</i>
                  <span></span>
                </router-link>
              </li>
              <li>
                <router-link to="/admin/article">
                  <i class="el-icon-setting">后台</i>
                  <span></span>
                </router-link>
              </li>
            </ul>
          </div>
        </div>
      </aside>
      <section class="section">
        <div class="content">
          <router-view />
          <hr style="height:1px;border:none;border-top:1px dashed #ccc;margin-top: 30px" />
          <div class="record">© 2020 狸知 www.ahriknow.com</div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
export default {
  name: 'index',
  data() {
    return {
      drawer: false,
      dialogLogin: false,
      article_count: 0,
      category_count: 0,
      tag_count: 0
    }
  },
  methods: {
    closeDialog() {
      this.form = {
        username: '',
        password: ''
      }
      this.dialogLogin = false
    },
    handlerLogin() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          let self = this
          this.axios
            .post('/api/verification/', self.form)
            .then(response => {
              if (response.data.code === 200) {
                localStorage.setItem(
                  'user',
                  JSON.stringify(response.data.data.user)
                )
                localStorage.setItem('token', response.data.data.token)
                self.$message({
                  showClose: true,
                  message: response.data.msg,
                  type: 'success'
                })
                self.dialogLogin = false
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
          return false
        }
      })
    }
  },
  mounted() {
    window.onresize = () => {
      this.drawer = false
    }

    this.loading = true
    let self = this
    this.axios
      .get('/api/index/count/', {
        params: {
          count: true
        }
      })
      .then(response => {
        self.article_count = response.data.data[0]
        self.category_count = response.data.data[1]
        self.tag_count = response.data.data[2]
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
#index {
  width: 100%;
  height: 100%;
  .container {
    width: 100%;
    height: 100%;
    overflow: hidden;
    display: flex;
    .menu {
      position: fixed;
      top: -80px;
      left: 0;
      right: 0;
      height: 80px;
      background: #fff;
      transition: 0.4s;
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      .btn {
        height: 80px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 30px;
        .logo {
          font-size: 32px;
          font-weight: 700;
        }
        i {
          font-size: 30px;
          font-weight: 700;
          cursor: pointer;
          transition: 0.4s;
          border-radius: 4px;
          &:hover {
            background: #ddd;
            color: #666;
          }
          &:active {
            background: #ccc;
          }
        }
      }
      .el-drawer {
        ul {
          margin: 100px 30px;
          width: 300px;
          li {
            text-align: left;
            height: 32px;
            list-style-type: none;
            font-size: 20px;
            border-bottom: solid #ccc 1px;
            margin-top: 10px;
            transition: 0.3s;
            cursor: pointer;
            padding: 5px;
            border-radius: 4px;
            &:hover {
              transform: scale(1px);
              font-size: 19px;
              background: #eee;
            }
            &:active {
              background: #ddd;
            }
            a {
              display: inline-block;
              width: 100%;
              height: 100%;
              text-decoration: none;
              color: #2c3e50;
              display: flex;
              justify-content: space-between;
            }
          }
        }
      }
    }
    .aside {
      width: 35%;
      overflow: hidden;
      transition: 0.5s;
      .menu-bar {
        width: 300px;
        height: 100%;
        float: right;
        .profile {
          margin: 30px 20px;
          border-bottom: solid #555 2px;
          .info {
            padding-top: 50px;
            text-align: center;
            font-size: 32px;
            font-weight: 700;
          }
        }
        .menu-list {
          text-align: right;
          margin: 0 40px;
          ul {
            li {
              text-align: left;
              height: 32px;
              list-style-type: none;
              font-size: 20px;
              border-bottom: solid #ccc 1px;
              margin-top: 10px;
              transition: 0.3s;
              cursor: pointer;
              padding: 5px;
              border-radius: 4px;
              &:hover {
                transform: scale(1px);
                font-size: 19px;
                background: #eee;
              }
              &:active {
                background: #ddd;
              }
              a {
                display: inline-block;
                width: 100%;
                height: 100%;
                text-decoration: none;
                color: #2c3e50;
                display: flex;
                justify-content: space-between;
              }
            }
          }
        }
      }
    }
    .section {
      width: 65%;
      transition: 0.5s;
      overflow: auto;
      background: #ccc;
      &::-webkit-scrollbar {
        /*滚动条整体样式*/
        width: 6px;
        height: 1px;
      }
      &::-webkit-scrollbar-thumb {
        /*滚动条里面小方块*/
        border-radius: 6px;
        box-shadow: inset 0 0 3px rgba(0, 0, 0, 0.2);
        background: #949494;
      }
      &::-webkit-scrollbar-track {
        /*滚动条里面轨道*/
        box-shadow: inset 0 0 3px rgba(0, 0, 0, 0.2);
        border-radius: 6px;
        background: #ededed;
      }
      .content {
        padding: 10px 50px;
        border-radius: 10px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        width: 900px;
        background: #fff;
        margin: 60px;
        .record {
          height: 80px;
          line-height: 80px;
          text-align: center;
          font-size: 20px;
          font-weight: 600;
        }
      }
    }
  }
}
@media screen and (max-width: 1540px) {
  #index {
    .container {
      .menu {
        top: 0;
      }
      .aside {
        width: 0;
      }
      .section {
        width: 100%;
        .content {
          margin: 100px auto;
        }
      }
    }
  }
}
@media screen and (max-width: 1000px) {
  #index {
    .container {
      .section {
        padding: 10px;
        width: 100%;
        .content {
          width: 100%;
          margin: 100px 0;
        }
      }
    }
  }
}
</style>
