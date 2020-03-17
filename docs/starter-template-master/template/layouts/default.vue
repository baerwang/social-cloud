<template>
  <div>
    <header>
      <div class="activity-head">
        <div class="wrapper">
          <div class="sui-navbar">
            <div class="navbar-inner">
              <a href="index-logined.html" class="sui-brand">
                <img src="~/assets/img/asset-logo-black.png" alt="社交" />
              </a>
              <ul class="sui-nav">
                <router-link to="/" tag="li" active-class="active" exact>
                  <a>头条</a>
                </router-link>
                <router-link to="/qa" tag="li" active-class="active">
                  <a>问答</a>
                </router-link>
                <router-link to="/gathering" tag="li" active-class="active">
                  <a>活动</a>
                </router-link>
                <router-link to="/friends" tag="li" active-class="active">
                  <a>交友</a>
                </router-link>
                <router-link to="/spit" tag="li" active-class="active">
                  <a>吐槽</a>
                </router-link>
                <router-link to="/recruit" tag="li" active-class="active">
                  <a>招聘</a>
                </router-link>
              </ul>
              <form class="sui-form sui-form pull-left">
                <input type="text" placeholder="输入关键词..." />
                <span class="btn-search fa fa-search"></span>
              </form>
              <div class="sui-nav pull-right info" v-if="user.name===undefined">
                <li>
                  <router-link to="/login">登陆</router-link>
                </li>
              </div>
              <div class="sui-nav pull-right info" v-if="user.name!==undefined">
                <li>
                  <a href="/manager" class="notice">{{user.name}}</a>
                </li>
                <li>
                  <a @click="logout" class="notice">退出登录</a>
                </li>
                <li>
                  <a href="/manager" class="homego">
                    <img :src="user.avatar" :alt="user.name" width="50px" />
                  </a>
                </li>
              </div>
            </div>
          </div>
        </div>
      </div>
    </header>
    <nuxt />
    <footer>
      <!--底部版权-->
      <div class="footer">
        <div class="wrapper">
          <div class="footer-bottom">
            <div class="link">
              <dl>
                <dt>网站相关</dt>
                <dd>关于我们</dd>
                <dd>服务条款</dd>
                <dd>帮助中心</dd>
                <dd>编辑器语法</dd>
              </dl>
              <dl>
                <dt>常用链接</dt>
                <dd>Scoial-Cloud播客</dd>
                <dd>Scoial-Cloud论坛</dd>
              </dl>
              <dl>
                <dt>联系我们</dt>
                <dd>联系我们</dd>
                <dd>加入我们</dd>
                <dd>建议反馈</dd>
              </dl>
              <dl>
                <dt>关注我们</dt>
                <dd>微博</dd>
                <dd>twitter</dd>
              </dl>

              <div class="xuke">
                <h3>内容许可</h3>
                <p>除特别说明外，用户内容均采用知识共享署名-非商业性使用-禁止演绎4.0 国际许可协议 (CC BY-NC-ND 4.0) 进行许可</p>
                <p>本站由 baerwang 提供更新服务</p>
              </div>
            </div>

            <p class="Copyright">Copyright © 2020 Scoial-Cloud社区 当前版本 0.0.1</p>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>
<script>
import '~/assets/plugins/normalize-css/normalize.css'
import '~/assets/plugins/yui/cssgrids-min.css'
import '~/assets/plugins/sui/sui.min.css'
import '~/assets/plugins/sui/sui-append.min.css'
import '~/assets/plugins/font-awesome/css/font-awesome.min.css'
import '~/assets/css/widget-base.css'
import '~/assets/css/widget-head-foot.css'
import "~/assets/css/page-sj-activity-index.css"

import { getUser, removeUser } from '@/utils/auth'
export default {
  data() {
    return {
      user: {}
    }
  },
  methods: {
    logout() {
      //  清除用户登录信息
      removeUser()
      location.href = '/'
    }
  },
  created() {
    this.user = getUser()
  }
}
</script>