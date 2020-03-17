import Vue from 'vue'
import { request8 } from '@/utils/request'

const group_name = 'user'

export default {
  methods: {
    //在此处定义方法
    sendsms(mobile) {
      return request8({
        url: `/${group_name}/sendsms/${mobile}`,
        method: 'post'
      })
    },
    register(user, code) {
      return request8({
        url: `/${group_name}/register/${code}`,
        method: 'post',
        data: user
      })
    },
    login(mobile, password) {
      return request8({
        url: `/${group_name}/login`,
        method: 'post',
        data: {
          mobile, password
        }
      })
    }
  }
}