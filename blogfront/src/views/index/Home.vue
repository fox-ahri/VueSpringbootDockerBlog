<template>
  <div
    id="home"
    class="home"
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(255, 255, 255, 0.6)"
  >
    <el-card v-for="i in articles" :key="i.id" style="z-index: 1">
      <div class="item">
        <div class="image" v-show="i.image != ''">
          <img :src="i.image" alt="image" />
        </div>
        <div class="container">
          <div class="content">
            <div class="title" @click="readArticle(i.id)">
              <el-tag v-if="i.top" type="danger" size="mini" effect="dark">置顶</el-tag>
              <el-tag v-if="i.type == '原创'" size="mini">原创</el-tag>
              <el-tooltip v-else placement="top">
                <div slot="content">{{ i.link }}</div>
                <el-tag :type="i.type == '翻译' ? 'danger' : 'warning'" size="mini">{{ i.type }}</el-tag>
              </el-tooltip>
              {{ i.title }}
            </div>
            <div class="describe">{{ i.describe }}</div>
          </div>
          <div class="info">
            <div class="short">
              <div class="user">
                <i class="el-icon-user">AHRI</i>
              </div>
              <div class="msg">
                <i class="el-icon-chat-square">{{ i.comments.length }}</i>
              </div>
            </div>
            <div class="time">
              <i class="el-icon-time">{{ time(i.updateDate) }}</i>
            </div>
          </div>
        </div>
      </div>
    </el-card>
    <div class="btn" v-show="!loading">
      <button class="load" @click="getMoreArticle">加载</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'home',
  data() {
    return {
      loading: false,
      articles: [],
      pageable: {
        pageNumber: 0,
        pageSize: 2
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
    readArticle(id) {
      this.$router.push({ name: 'read', query: { id: id } })
    },
    getMoreArticle() {
      this.pageable.pageNumber++
      this.getArticle()
    },
    getArticle() {
      this.loading = true
      let self = this
      this.axios
        .get('/api/index/article/?page=' + self.pageable.pageNumber)
        .then(response => {
          if (response.data.data.content.length > 0)
            response.data.data.content.forEach(article => {
              self.articles.push(article)
            })
          else
            self.$message({
              showClose: true,
              message: '没有更多了',
              type: 'warning'
            })
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
    this.getArticle()
  }
}
</script>

<style lang="scss" scoped>
#home {
  width: 100%;
  height: 100%;
  transition: 1s;
  .el-card {
    margin: 5px 10px;
    .item {
      display: flex;
      .image {
        width: 240px;
        img {
          width: 100%;
          height: 100%;
        }
      }
      .container {
        flex: 1;
        height: 135px;
        padding: 0 10px 0 20px;
        .content {
          height: 95px;
          overflow: hidden;
          .title {
            font-size: 18px;
            font-weight: 700;
            cursor: pointer;
            overflow: hidden;
            &:hover {
              text-decoration: underline;
            }
          }
          .describe {
            margin-top: 5px;
            overflow: hidden;
            font-size: 14px;
            padding: 4px 10px;
            color: #666;
            border-top: dashed #ccc 1px;
          }
        }
        .info {
          height: 40px;
          border-top: dashed #ccc 1px;
          line-height: 40px;
          display: flex;
          .short {
            display: flex;
          }
          .user,
          .time,
          .msg {
            margin-right: 25px;
            color: #555;
            cursor: pointer;
            i {
              &:hover {
                text-decoration: underline;
              }
            }
          }
        }
      }
    }
  }
  .btn {
    height: 120px;
    line-height: 120px;
    text-align: center;
    .load {
      height: 40px;
      width: 120px;
      border: solid #000 2px;
      background: none;
      transition: 0.3s;
      color: #000;
      font-size: 18px;
      cursor: pointer;
      &:hover {
        background: #000;
        color: #fff;
      }
    }
  }
}

@media screen and (max-width: 1000px) {
  #home {
    .el-card {
      .item {
        flex-direction: column;
        height: auto;
        max-width: 500px;
        margin: 0 auto;
        .image {
          width: 100%;
          height: 200px;
        }
        .container {
          margin-top: 10px;
          .content {
            height: auto;
          }
          .info {
            height: 60px;
            line-height: 30px;
            flex-direction: column;
          }
        }
      }
    }
  }
}
</style>
