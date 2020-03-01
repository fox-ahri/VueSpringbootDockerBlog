<template>
  <div
    id="write"
    class="write"
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)"
  >
    <div class="editor">
      <mavon-editor
        v-model="article.content"
        :tabSize="4"
        codeStyle="atom-one-dark"
        ref="md"
        @change="change"
        style="min-height: 600px"
      />
    </div>
    <div class="info">
      <el-form ref="form_article" :model="article" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="article.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item label="描 述" prop="describe">
          <el-input
            type="textarea"
            maxlength="120"
            show-word-limit
            rows="4"
            v-model="article.describe"
          ></el-input>
        </el-form-item>
        <el-form-item label="封面">
          <el-input v-model="article.image" placeholder="封面"></el-input>
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="article.categoryId" placeholder="请选择分类" style="width: 100%">
            <el-option
              v-for="category in categories"
              :key="category.id"
              :label="category.name"
              :value="category.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="标签">
          <el-select v-model="article.tagIds" multiple placeholder="请选择标签" style="width: 100%">
            <el-option v-for="tag in tags" :key="tag.id" :label="tag.name" :value="tag.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="类型">
          <div style="display: flex;justify-content: flex-start">
            <el-select v-model="article.type" placeholder="请选择类型">
              <el-option label="原创" value="原创"></el-option>
              <el-option label="翻译" value="翻译"></el-option>
              <el-option label="转载" value="转载"></el-option>
            </el-select>
            <div style="width: 100%">
              <el-input v-show="article.type != '原创'" placeholder="引用链接" v-model="article.link"></el-input>
            </div>
          </div>
        </el-form-item>
        <el-form-item label="其他">
          <div style="display: flex;justify-content: space-between;">
            <div>
              <!-- <el-checkbox v-model="article.published" label="发布" border></el-checkbox> -->
              <el-checkbox v-model="article.commented" label="评论" border></el-checkbox>
              <!-- <el-checkbox v-model="article.appreciation" label="赞赏" border></el-checkbox> -->
              <el-checkbox v-model="article.top" label="置顶" border></el-checkbox>
            </div>
            <el-button type="primary" @click="handlerSubmit">提交</el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
export default {
  name: 'write',
  components: {
    mavonEditor
  },
  data() {
    return {
      loading: false,
      article: {
        titlt: '',
        describe: '',
        image: 'http://ahriknow.oss-cn-beijing.aliyuncs.com/1.jpg',
        content: '# Hello MavonEditor!',
        categoryId: '',
        tagIds: [],
        type: '原创',
        link: '',
        removed: false,
        // published: true,
        commented: true,
        // appreciation: true,
        top: true
      },
      tags: [],
      categories: []
    }
  },
  methods: {
    change(value, render) {
      this.article.content = value
    },
    handlerSubmit() {
      if (this.$route.query.hasOwnProperty('edit')) {
        this.loading = true
        let self = this
        this.axios
          .put('/api/admin/article/' + self.$route.query.edit, self.article)
          .then(response => {
            if (response.data.code === 200) {
              self.$router.push('/admin/article')
              self.$message({
                showClose: true,
                message: response.data.msg,
                type: 'success'
              })
            } else {
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
      } else {
        this.loading = true
        let self = this
        this.axios
          .post('/api/admin/article/', self.article)
          .then(response => {
            if (response.data.code === 200) {
              self.$router.push('/admin/article')
              self.$message({
                showClose: true,
                message: response.data.msg,
                type: 'success'
              })
            } else {
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
    getTag() {
      this.loading = true
      let self = this
      this.axios
        .get('/api/admin/tag/')
        .then(response => {
          if (response.data.code === 200) {
            self.tags = response.data.data
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
    this.getTag()
    this.getCategory()
    if (this.$route.query.hasOwnProperty('edit')) {
      this.loading = true
      let self = this
      this.axios
        .get('/api/admin/article/' + self.$route.query.edit)
        .then(response => {
          self.article = response.data.data
          console.log(response.data.data)
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
}
</script>

<style lang="scss" scoped>
#write {
  width: 1100px;
  height: 100%;
  margin: 0 auto;
  .info {
    padding: 30px 0 300px 0;
  }
}
</style>
