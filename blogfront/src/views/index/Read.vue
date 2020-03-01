<template>
  <div
    id="read"
    class="read"
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(255, 255, 255, 0.6)"
  >
    <div class="markdown-body" v-html="html"></div>
    <hr style="height:1px;border:none;border-top:1px dashed #ccc;margin-top: 30px" />
    <div class="comment" v-if="article.commented">
      <comment-tree :list="comments" :top="true" :article="$route.query.id"></comment-tree>
    </div>
  </div>
</template>

<script>
import commentTree from '../../components/Comment'
import '../../assets/github-markdown.min.css'
import 'highlight.js/styles/atom-one-dark.css'
const hljs = require('highlight.js')
const md = require('markdown-it')({
  html: false, // 在源码中启用 HTML 标签
  xhtmlOut: false, // 使用 '/' 来闭合单标签 （比如 <br />）。
  // 这个选项只对完全的 CommonMark 模式兼容。
  breaks: false, // 转换段落里的 '\n' 到 <br>。
  langPrefix: 'language-', // 给围栏代码块的 CSS 语言前缀。对于额外的高亮代码非常有用。
  linkify: false, // 将类似 URL 的文本自动转换为链接。

  // 启用一些语言中立的替换 + 引号美化
  typographer: false,

  // 当 typographer 启用时，成倍的 + 单引号替换对。
  // 或者智能(smartquotes)引号等，可以是 String 或 Array。
  //
  // 比方说，你可以支持 '«»„“' 给俄罗斯人使用， '„“‚‘'  给德国人使用。
  // 还有 ['«\xA0', '\xA0»', '‹\xA0', '\xA0›'] 给法国人使用（包括 nbsp）。
  quotes: '“”‘’',

  // 高亮函数，会返回转义的 HTML。
  // 如果源字符串未更改，且应该进行外部的转义，或许返回 ''
  // 如果结果以 <pre ... 开头，内部包装器则会跳过。
  highlight: function(str, lang) {
    if (lang && hljs.getLanguage(lang)) {
      try {
        return (
          '<pre class="hljs" style="font-size: 16px"><code>' +
          hljs.highlight(lang, str, true).value +
          '</code></pre>'
        )
      } catch (__) {}
    }

    return (
      '<pre class="hljs"><code>' + md.utils.escapeHtml(str) + '</code></pre>'
    )
  }
})
export default {
  name: 'read',
  components: {
    commentTree
  },
  data() {
    return {
      article: {
        content: ''
      },
      comments: [],
      content:
        '# Markdown It\n```python\nclass Test:\n    def hello():\n```\n- 123\n- 456\n    - qqq\n' +
        `\n|column1|column2|column3|\n|-|-|-|\n|content1|content2|content3|\n` +
        `![Img](http://ahricdn.oss-cn-beijing.aliyuncs.com/images/pic.jpg)`,
      html: '',
      loading: false
    }
  },
  methods: {
    dataToTree(list, pid = null) {
      return list
        .filter(item => {
          item.reply = false
          return item.pid === pid
        })
        .map(item => ({
          ...item,
          children: this.dataToTree(list, item.id)
        }))
    },
    commentSubmit(val, nickname, email, website, article) {
      this.loading = true
      let self = this
      this.axios
        .post('/api/index/comment/', {
          content: val,
          nickname: nickname,
          email: email,
          website: website,
          articleId: article
        })
        .then(response => {
          if (response.data.code === 200) {
            self.$message({
              showClose: true,
              message: response.data.msg,
              type: 'success'
            })
            self.comments.unshift(response.data.data)
          } else {
            self.$message({
              showClose: true,
              message: response.data.msg,
              type: 'error'
            })
          }
          this.loading = true
        })
        .catch(error => {
          self.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
          this.loading = true
        })
    }
  },
  mounted() {
    if (this.$route.query.hasOwnProperty('id')) {
      this.loading = true
      let self = this
      this.axios
        .get('/api/index/article/' + self.$route.query.id)
        .then(response => {
          self.article = response.data.data
          self.comments = self.dataToTree(response.data.data.comments)
          self.html = md.render(response.data.data.content)
          self.loading = false
        })
        .catch(error => {
          self.$message({
            showClose: true,
            message: response.data.msg,
            type: 'error'
          })
          self.loading = false
        })
    } else {
      self.$message({
        showClose: true,
        message: '文章不存在',
        type: 'error'
      })
    }
  }
}
</script>

<style lang="scss" scoped>
#read {
  width: 100%;
  height: 100%;
}
</style>
