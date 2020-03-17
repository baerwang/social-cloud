<template>
  <div></div>
</template>

<script>
import { getUrlParam } from '@/utils/param'
import weixin from '@/api/weixin'
import { setUser } from '@/utils/auth'
export default {
  mounted() {
    let code = getUrlParam('code')
    if (code !== null) {//如果是微信登陆
      //根据code获取access_token
      weixin.getAccessToken(code).then(res => {
        let access_token = res.data.access_token
        let openid = res.data.openid
        weixin.getUserinfo(access_token, openid).then(res => {
          //提取用户昵称和头像
          let nickname = res.data.nickname
          let headimgurl = res.data.headimgurl
          setUser(access_token, nickname, headimgurl)
          location.href = '/'
        })
      })
    }
  }
}
</script>