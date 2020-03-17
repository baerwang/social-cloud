import axios from 'axios'
import { getUser } from '@/utils/auth'

//  'http://127.0.0.1:9001' // 基础
//  'http://127.0.0.1:9002' // 职位
//  'http://127.0.0.1:9003' // 回答
//  'http://127.0.0.1:9004' // 文章
//  'http://127.0.0.1:9005' // 活动
//  'http://127.0.0.1:9006' // 吐槽
//  'http://127.0.0.1:9007' // 添加文章
//  'http://127.0.0.1:9008' // 用户
//  'http://127.0.0.1:9009' // 发短信
//  'http://127.0.0.1:9010' // 交友

// //创建axios实例
// const service = axios.create({
//   baseURL: 'http://192.168.1.106:7300/mock/5e46cced5eabc210c9f73932', // api的base_url
//   timeout: 100000, // 请求超时时间
//   headers: { 'Authorization': `Bearer ${getUser().token}` } // 每次请求将token添加到header里
// })


/** 基础*/
export function request1(config) {
  const instance = axios.create({
    baseURL: "http://127.0.0.1:9001",
    timeout: 100000,
    headers: { 'Authorization': `Bearer ${getUser().token}` } // 每次请求将token添加到header里
  })
  return instance(config)
}

/** 职位*/
export function request2(config) {
  const instance = axios.create({
    baseURL: "http://127.0.0.1:9002",
    timeout: 100000,
    headers: { 'Authorization': `Bearer ${getUser().token}` } // 每次请求将token添加到header里
  })
  return instance(config)
}

/** 回答*/
export function request3(config) {
  const instance = axios.create({
    baseURL: "http://127.0.0.1:9003",
    timeout: 100000,
    headers: { 'Authorization': `Bearer ${getUser().token}` } // 每次请求将token添加到header里
  })
  return instance(config)
}

/** 文章*/
export function request4(config) {
  const instance = axios.create({
    baseURL: "http://127.0.0.1:9004",
    timeout: 100000,
    headers: { 'Authorization': `Bearer ${getUser().token}` } // 每次请求将token添加到header里
  })
  return instance(config)
}

/** 活动*/
export function request5(config) {
  const instance = axios.create({
    baseURL: "http://127.0.0.1:9005",
    timeout: 100000,
    headers: { 'Authorization': `Bearer ${getUser().token}` } // 每次请求将token添加到header里
  })
  return instance(config)
}

/** 吐槽*/
export function request6(config) {
  const instance = axios.create({
    baseURL: "http://127.0.0.1:9006",
    timeout: 100000,
    headers: { 'Authorization': `Bearer ${getUser().token}` } // 每次请求将token添加到header里
  })
  return instance(config)
}

/** 添加文章*/
export function request7(config) {
  const instance = axios.create({
    baseURL: "http://127.0.0.1:9007",
    timeout: 100000,
    headers: { 'Authorization': `Bearer ${getUser().token}` } // 每次请求将token添加到header里
  })
  return instance(config)
}

/**用户 */
export function request8(config) {
  const instance = axios.create({
    baseURL: "http://127.0.0.1:9008",
    timeout: 100000,
    headers: { 'Authorization': `Bearer ${getUser().token}` } // 每次请求将token添加到header里
  })
  return instance(config)
}

/**发短信 */
export function request9(config) {
  const instance = axios.create({
    baseURL: "http://127.0.0.1:9009",
    timeout: 100000,
    headers: { 'Authorization': `Bearer ${getUser().token}` } // 每次请求将token添加到header里
  })
  return instance(config)
}

/**交友 */
export function request10(config) {
  const instance = axios.create({
    baseURL: "http://127.0.0.1:9010",
    timeout: 100000,
    headers: { 'Authorization': `Bearer ${getUser().token}` } // 每次请求将token添加到header里
  })
  return instance(config)
}