<template>
  <div>
    <div class="tab-nav">
      <div class="wrapper">
        <ul class="fl sui-nav nav-tabs navbar-dark">
          <router-link active-class="active" excat to="/qa/label/0" tag="li">
            <a>首页</a>
          </router-link>
          <router-link
            tag="li"
            :to="'/qa/label/'+item.id"
            active-class="active"
            v-for="(item,index) in labelList"
            :key="index"
          >
            <a>{{item.labelname}}</a>
          </router-link>
        </ul>
        <span class="fr more">
          <nuxt-link to="/label">更多</nuxt-link>
        </span>
        <div class="clearfix"></div>
      </div>
    </div>
    <nuxt-child />
  </div>
</template>
<script>
import labelApi from '@/api/label'
export default {
  asyncData({ params, error }) {
    return labelApi.toplist().then((res) => {
      return { labelList: res.data.data }
    })
  }
}
</script>