<template>
  <div class="wrapper loginsign">
    <div class="item signup">
      <div class="form">
        <h3 class="loginsign-title">注册新账号</h3>
        <form class="sui-form">
          <div class="control-group">
            <label for="inputname" class="control-label">昵称</label>
            <div class="controls">
              <input
                type="text"
                id="inputname"
                placeholder="真实姓名或常用昵称"
                class="input-xlarge"
                v-model="pojo.nickname"
              />
            </div>
          </div>
          <div class="different">
            <div class="radio-content">
              <div id="a1" class="phone">
                <div class="control-group number">
                  <input
                    type="text"
                    placeholder="仅支持大陆手机号"
                    class="input-xlarge"
                    v-model="pojo.mobile"
                    data-rules="required|mobile"
                  />
                </div>
                <div class="control-group code">
                  <div class="input-append">
                    <input
                      id="appendedInputButton"
                      type="text"
                      placeholder="短信验证"
                      class="span2 input-large msg-input"
                      v-model="code"
                    />
                    <button type="button" class="sui-btn msg-btn" @click="sendsms">获取验证码</button>
                  </div>
                </div>
                <div class="control-group">
                  <label for="inputpassword" class="control-label">密码</label>
                  <div class="controls">
                    <input
                      type="text"
                      id="inputpassword"
                      placeholder="请输入6-16位密码"
                      class="input-xlarge"
                      v-model="pojo.password"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="control-group btn-signup">
            <label class="control-label"></label>
            <div class="controls">
              <label>
                <input type="checkbox" v-model="agree" />
                <span class="type-text" style="font-size:12px;">同意协议并接受《服务条款》</span>
              </label>
              <button type="button" class="sui-btn btn-danger btn-yes" @click="register">注 册</button>
            </div>
          </div>
        </form>
      </div>
    </div>
    <div class="item">
      <div class="form">
        <h3 class="loginsign-title">用户登录</h3>
        <form class="sui-form login-form">
          <div class="control-group">
            <label for="inputname" class="control-label">手机号：</label>
            <div class="controls">
              <input
                type="text"
                id="inputname"
                placeholder="11位手机号"
                class="input-xlarge"
                data-rules="required"
                v-model="mobile"
              />
            </div>
          </div>
          <div class="control-group">
            <label for="inputpassword" class="control-label">密码：</label>
            <div class="controls">
              <input
                type="text"
                id="inputpassword"
                placeholder="输入登录密码"
                class="input-xlarge"
                v-model="password"
              />
            </div>
          </div>
          <div class="controls">
            <label>
              <input type="checkbox" name="remember-me" />
              <span class="type-text" style="font-size:12px;">记住登录状态</span>
            </label>
            <button type="button" class="sui-btn btn-danger btn-yes" @click="login">登 录</button>
          </div>
          <div id="weixin"></div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import '~/assets/css/page-sj-person-loginsign.css'

import userApi from '@/api/user'
import { setUser } from '@/utils/auth'

export default {
  data() {
    return {
      pojo: {
      },
      code: '',
      agree: false,
      mobile: '',
      password: ''
    }
  },
   
  methods: {
    sendsms() {
      // 手机验证
      if (!/^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\d{8}$/g.test(this.pojo.mobile)) {
        this.$message.error('手机填写有误,请重新输入')
        return
      }
      userApi.$options.methods.sendsms(this.pojo.mobile).then(res => {
        this.$message({
          message: res.data.message,
          type: (res.data.flag ? 'success' : 'error')
        })
      })
    },
    register() {
      let checkForm = this.checkForm();
      if (!checkForm.flag) {
        this.$message.error(checkForm.errMsg)
        return
      }
      userApi.register(this.pojo, this.code).then(res => {
        if (res.data.flag) {
          this.$message.success(res.data.message),
            this.pojo = {},
            this.code = ''
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    checkForm() {
      let errMsg = '';
      // 判断昵称
      if (!this.pojo.nickname) {
        errMsg = '昵称不能为空';
        return {
          flag: false,
          errMsg,
        }
      }
      if (!/^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\d{8}$/g.test(this.pojo.mobile)) {
        errMsg = '手机填写有误';
        return {
          flag: false,
          errMsg,
        }
      }
      // 判断密码
      if (!this.pojo.password || this.pojo.password.length <= 6 || this.pojo.password.length >= 16) {
        errMsg = '密码长度必须在6-16之内';
        return {
          flag: false,
          errMsg,
        }
      }
      // 是否同意了服务条款
      if (!this.agree) {
        errMsg = '请勾选服务条款';
        return {
          flag: false,
          errMsg,
        }
      }
      return {
        flag: true,
        errMsg,
      }
    },
    login() {
      if (!/^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\d{8}$/g.test(this.mobile)) {
        this.$message.error('手机填写有误,请重新输入')
        return
      }
      userApi.methods.login(this.mobile, this.password).then(res => {
        if (res.data.flag) {
          // 保存用户信息
          setUser(res.data.data.token, res.data.data.name, res.data.data.avatar)
          location.href = '/'
        } else {
          this.$message.error(res.data.message)
          this.mobile = ''
          this.password = ''
        }
      })
    }
  },
  mounte() {
    var obj = new WxLogin({
      id: "weixin",
      appid: "xx",
      scope: "xx",
      redirect_uri: "http://note.java.itcast.cn/weixinlogin"
    });
  },
  head: {
    script: [
      { src: 'http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js' }
    ]
  }
}
</script>