(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-ced30946"],{"0050":function(e,s,a){"use strict";a.r(s);var t=function(){var e=this,s=e.$createElement,a=e._self._c||s;return a("div",{staticClass:"me",attrs:{id:"me"}},[a("el-card",{staticClass:"box-card"},[a("el-form",{ref:"form",attrs:{model:e.user,"label-width":"80px"}},[a("el-form-item",{attrs:{label:"用户名"}},[a("el-input",{model:{value:e.user.username,callback:function(s){e.$set(e.user,"username",s)},expression:"user.username"}})],1),a("el-form-item",{attrs:{label:"旧密码"}},[a("el-input",{model:{value:e.user.password,callback:function(s){e.$set(e.user,"password",s)},expression:"user.password"}})],1),a("el-form-item",{attrs:{label:"新密码"}},[a("el-input",{model:{value:e.user.pass1,callback:function(s){e.$set(e.user,"pass1",s)},expression:"user.pass1"}})],1),a("el-form-item",{attrs:{label:"确认密码"}},[a("el-input",{model:{value:e.user.pass2,callback:function(s){e.$set(e.user,"pass2",s)},expression:"user.pass2"}})],1),a("el-form-item",{attrs:{label:"昵称"}},[a("el-input",{model:{value:e.user.nickname,callback:function(s){e.$set(e.user,"nickname",s)},expression:"user.nickname"}})],1),a("el-form-item",{attrs:{label:"头像"}},[a("el-input",{model:{value:e.user.avatar,callback:function(s){e.$set(e.user,"avatar",s)},expression:"user.avatar"}})],1),a("el-form-item",{attrs:{label:"邮箱"}},[a("el-input",{model:{value:e.user.email,callback:function(s){e.$set(e.user,"email",s)},expression:"user.email"}})],1),a("el-form-item",{attrs:{label:"电话"}},[a("el-input",{model:{value:e.user.phone,callback:function(s){e.$set(e.user,"phone",s)},expression:"user.phone"}})],1),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:e.onSubmit}},[e._v("保 存")])],1)],1)],1)],1)},r=[],l={name:"me",data:function(){return{user:{}}},methods:{onSubmit:function(){if(this.user.pass1===this.user.pass2){var e=this;this.axios.put("/api/admin/user/",e.user).then((function(s){200===s.data.code?(e.$message({showClose:!0,message:s.data.msg,type:"success"}),localStorage.clear(),e.$router.push({name:"auth"})):e.$message({showClose:!0,message:s.data.msg,type:"error"})})).catch((function(s){e.$message({showClose:!0,message:s,type:"error"})}))}else this.$message({showClose:!0,message:"密码不一致",type:"error"})}},mounted:function(){localStorage.getItem("user")&&(this.user=JSON.parse(localStorage.getItem("user")))}},u=l,o=(a("e6b8"),a("2877")),n=Object(o["a"])(u,t,r,!1,null,"fec859ce",null);s["default"]=n.exports},a79d3:function(e,s,a){},e6b8:function(e,s,a){"use strict";var t=a("a79d3"),r=a.n(t);r.a}}]);
//# sourceMappingURL=chunk-ced30946.988825ab.js.map