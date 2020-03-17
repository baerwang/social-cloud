import request from '@/utils/request'
import { getUser } from '@/utils/auth'
const group_name = 'base'
const api_name = 'label'

export default {
  toplist() {
    return request({
      url: `/${group_name}/${api_name}/toplist`,
      method: 'get'
    })
  },
  search(page, size) {
    return request({
      url: `/${group_name}/${api_name}/search/${page}/${size}`,
      method: "post",
    });
  }
}