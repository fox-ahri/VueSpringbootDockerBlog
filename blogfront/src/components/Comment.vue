<template>
  <div id="comment" class="comment">
    <div class="reply" v-if="top">
      <div class="btn">
        <div class="i">
          <el-button type="text" @click="show = true">回复</el-button>
        </div>
        <div class="i">
          <el-button type="text" v-show="show" @click="show = false">取消</el-button>
        </div>
        <div class="i">
          <el-button type="text" v-show="show" @click="fatherMethod()">确定</el-button>
        </div>
      </div>
      <div class="text" v-show="show">
        <div class="info">
          <el-input size="mini" placeholder="昵称" v-model="nickname"></el-input>
          <el-input size="mini" placeholder="邮箱" v-model="email"></el-input>
          <el-input size="mini" placeholder="Https://" v-model="website"></el-input>
        </div>
        <el-input
          type="textarea"
          placeholder="请输入内容"
          v-model="text"
          maxlength="120"
          show-word-limit
        ></el-input>
      </div>
    </div>
    <div class="item" v-for="i in list" :key="i.id">
      <div class="userinfo">
        <div class="avatar">
          <img :src="i.avatar" :alt="i.avatar" />
        </div>
      </div>
      <div class="commentinfo">
        <div class="markdown-body content" v-html="render(i.content)"></div>
        <div class="user">
          <div class="group1">
            <div class="i">
              <i class="el-icon-user"></i>
              {{ i.nickname }}
            </div>
            <div class="i">
              <i class="el-icon-s-promotion"></i>
              {{ i.email }}
            </div>
            <div class="i">
              <i class="el-icon-s-home"></i>
              {{ i.website }}
            </div>
          </div>
          <div class="group2">
            <div class="i">
              <i class="el-icon-time"></i>
              {{ time(i.date) }}
            </div>
            <div class="i">
              <el-button type="text" @click="i.reply = !i.reply">回复</el-button>
            </div>
            <div class="i">
              <el-button type="text" v-show="i.reply" @click="i.reply = !i.reply">取消</el-button>
            </div>
            <div class="i">
              <el-button type="text" v-show="i.reply" @click="commentSubmit(i)">确定</el-button>
            </div>
          </div>
        </div>
        <div class="reply" v-show="i.reply">
          <div class="info">
            <el-input size="mini" placeholder="请输入昵称" v-model="nickname"></el-input>
            <el-input size="mini" placeholder="邮箱" v-model="email"></el-input>
            <el-input size="mini" placeholder="Https://" v-model="website"></el-input>
          </div>
          <el-input
            type="textarea"
            placeholder="请输入内容"
            v-model="i.new"
            maxlength="120"
            show-word-limit
          ></el-input>
        </div>
        <comment-tree :list="i.children" :top="false" :article="article"></comment-tree>
      </div>
    </div>
  </div>
</template>

<script>
import '../assets/github-markdown.min.css'
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
  name: 'commentTree',
  props: {
    list: Array,
    top: Boolean,
    article: String
  },
  data() {
    return {
      show: false,
      text: '',
      nickname: '',
      email: '',
      website: ''
    }
  },
  methods: {
    time(time) {
      let d = new Date(time)
      return (
        d.getMonth() +
        1 +
        '-' +
        d.getDate() +
        ' ' +
        d.getHours() +
        ':' +
        d.getMinutes()
      )
    },
    render(val) {
      return md.render(val)
    },
    fatherMethod() {
      this.$parent.commentSubmit(
        this.text,
        this.nickname,
        this.email,
        this.website,
        this.article
      )
      this.show = false
    },
    commentSubmit(val) {
      if (!localStorage.getItem('token')) {
        this.dialogLogin = true
        return
      }
      let self = this
      this.axios
        .post('/api/index/comment/', {
          content: val.new,
          nickname: self.nickname,
          email: self.email,
          website: self.website,
          pid: val.id,
          articleId: self.article
        })
        .then(response => {
          console.log(response)
          if (response.data.code === 200) {
            self.$message({
              showClose: true,
              message: response.data.msg,
              type: 'success'
            })
            val.children.unshift({
              content: val.new,
              date: '2020-02-20 15:32:41',
              nickname: self.nickname,
              email: self.email,
              website: self.website,
              avatar:
                'http://ahriknow.oss-cn-beijing.aliyuncs.com/default_avatar.png',
              reply: false,
              children: []
            })
            val.new = ''
            val.reply = false
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
    }
  }
}
</script>

<style lang="scss" scoped>
#comment {
  width: 100%;
  height: 100%;
  .item {
    border: solid #ccc 1px;
    display: flex;
    margin: 20px;
    .userinfo {
      width: 60px;
      border: solid #ccc 1px;
      .avatar {
        width: 80%;
        margin: 10%;
        img {
          width: 100%;
        }
      }
    }
    .commentinfo {
      flex: 1;
      .content {
        margin: 20px;
      }
      .user {
        border-top: dashed #ccc 1px;
        margin: 0 20px;
        .group1,
        .group2 {
          display: flex;
          justify-content: flex-start;
          align-items: center;
          .i {
            margin-right: 20px;
          }
        }
      }
      .reply {
        margin: 0 20px;
        .info {
          display: flex;
        }
      }
    }
  }
  .reply {
    margin: 20px;
    .btn {
      display: flex;
      justify-content: flex-end;
      .i {
        margin-left: 20px;
      }
    }
    .text {
      .info {
        display: flex;
      }
    }
  }
}
</style>
