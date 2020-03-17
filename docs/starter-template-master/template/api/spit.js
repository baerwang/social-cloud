import request from '@/utils/request'
const group_name = 'spit'
const api_name = 'spit'

export default {
  // 查询
  search(page, size, searchMap) {
    return request({
      url: `/${group_name}/${api_name}/search/${page}/${size}`,
      method: 'post',
      data: searchMap
    })
  },
  // 按id查询
  findById(id) {
    return request({
      url: `/${group_name}/${api_name}/${id}`,
      method: 'get'
    })
  },
  // 查询id评论
  commentlist(id) {
    return request({
      url: `/${group_name}/${api_name}/commentlist/${id}`,
      method: 'get'
    })
  },
  // 点赞
  thumbup(id) {
    return request({
      url: `/${group_name}/${api_name}/thumbup/${id}`,
      method: 'put'
    })
  },
  //增加提交吐槽
  save(pojo) {
    return request({
      url: `/${group_name}/${api_name}`,
      method: 'post',
      data: pojo
    })
  }

}