import request from '@/utils/request'
const group_name = 'reply'

export default {
  findById(id) {
    return request({
      url: `/${group_name}/${id}`,
      method: 'get'
    })
  }
}