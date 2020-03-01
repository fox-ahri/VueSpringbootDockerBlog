<template>
  <div id="file" class="file" v-loading="loading">
    <el-timeline v-for="i in data" :key="i[0]">
      <el-timeline-item :timestamp="i[0]" placement="top">
        <el-card>
          <p
            class="link"
            v-for="j in i[1]"
            :key="j.id"
            @click="$router.push({name: 'read', query: {id: j.id}})"
          >{{ j.title }}</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
    <div class="btn">
      <button class="load" @click="getMoreArticle">加载</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'file',
  data() {
    return {
      loading: false,
      articles: [],
      pageable: {
        pageNumber: 0,
        pageSize: 6
      },
      data: []
    }
  },
  methods: {
    time(time) {
      let d = new Date(time)
      return d.getFullYear() + ' / ' + (d.getMonth() + 1) + ' / ' + d.getDate()
    },
    serilaze() {
      let data = []
      let date = ''
      let tmp = []
      this.articles.forEach(article => {
        if (this.time(article.updateDate) == date) {
          tmp.push(article)
        } else {
          if (date == '') {
            tmp.push(article)
          } else {
            data.push([date, tmp])
            tmp = []
            tmp.push(article)
          }
          date = this.time(article.updateDate)
        }
      })
      if (tmp.length > 0) data.push([date, tmp])
      this.data = data
      return data
    },
    getMoreArticle() {
      this.pageable.pageNumber++
      this.getArticle()
    },
    getArticle() {
      this.loading = true
      let self = this
      this.axios
        .get(
          '/api/index/article/?page=' +
            self.pageable.pageNumber +
            '&size=' +
            self.pageable.pageSize
        )
        .then(response => {
          if (response.data.data.content.length > 0) {
            response.data.data.content.forEach(article => {
              self.articles.push(article)
            })
            self.serilaze()
          } else
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
#file {
  width: 100%;
  height: 100%;
  .link {
    margin: 5px 0;
    cursor: pointer;
    &:hover {
      text-decoration: underline;
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
</style>
