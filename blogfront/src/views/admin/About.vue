<template>
  <div id="about" class="about">
    <div class="editor">
      <mavon-editor
        v-model="about"
        :tabSize="4"
        codeStyle="atom-one-dark"
        ref="md"
        @change="change"
        style="min-height: 600px"
      />
    </div>
    <div class="btn">
      <el-button type="primary" @click="handlerSubmit">提交</el-button>
    </div>
  </div>
</template>

<script>
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
export default {
  name: 'about',
  components: {
    mavonEditor
  },
  data() {
    return {
      about: ''
    }
  },
  methods: {
    handlerSubmit() {
      let self = this
      this.axios
        .post('/api/admin/setting/about/', {
          id: 'about',
          content: self.about
        })
        .then(response => {
          if (response.data.code === 200) {
            self.$message({
              showClose: true,
              message: response.data.msg,
              type: 'success'
            })
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
            message: response.data.msg,
            type: 'error'
          })
        })
    },
    change(val, html) {
      this.about = val
    }
  },
  mounted() {
    let self = this
    this.axios
      .get('/api/admin/setting/about/')
      .then(response => {
        self.about = response.data.data.content
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
</script>

<style lang="scss" scoped>
#about {
  width: 1100px;
  height: 100%;
  margin: 0 auto;
  .btn {
    text-align: right;
    margin-top: 20px;
  }
}
</style>
