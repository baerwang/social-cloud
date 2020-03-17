<template>
  <div class="wrapper qa-content">
    <div class="qa-title">
      <div class="fl title">
        <h2>{{item.title}}</h2>
        <p>
          <span class="author">{{ item.nickname }}</span>
          <span>{{ item.createtime }}提问 · {{ item.updatetime }}更新</span>
        </p>
      </div>
      <div class="fr share">
        <h4>分享到：</h4>
        <div
          class="social-share"
          data-sites="weibo,wechat,qq"
          data-url="https://baerwang.baklib.com/"
          :data-title="item.name"
        ></div>
      </div>
      <div class="clearfix"></div>
    </div>
    <div class="fl left-list">
      <div class="problem-detail">
        <div class="title-intro">
          <div class="sidebar">
            <button
              type="button"
              class="like"
              data-placement="right"
              data-toggle="tooltip"
              title="问题对人有帮助，内容完整，我也想知道答案"
            >
              <i class="fa fa-caret-up" aria-hidden="true"></i>
            </button>
            <span class="count">{{item.thumbup}}</span>
            <button
              type="button"
              class="hate"
              data-placement="right"
              data-toggle="tooltip"
              title="问题没有实际价值，缺少关键内容，没有改进余地"
            >
              <i class="fa fa-caret-down" aria-hidden="true"></i>
            </button>
          </div>
          <div class="title">
            <div class="detail" v-html="item.content"></div>
            <div class="clearfix"></div>
            <div class="operate">
              <span class="time">{{ item.createtime }}提问</span>
              <span class="comment" @click="dialogVisible=true; this.content=''">评论</span>
              <!-- 只有自己能够编辑 -->
              <span class="edit" v-if="item.userid === user.id">编辑</span>
              <span class="jubao">举报</span>
            </div>
            <div class="comment-box">
              <form class="comment-form">
                <textarea row="1" placeholder="使用评论询问更多信息或提出修改意见，请不要在评论里回答问题"></textarea>
                <button type="submit" class="sui-btn submit-comment">提交评论</button>
              </form>
              <div class="tips">
                <p>
                  评论支持部分 Markdown 语法：**bold**_italic_[link](http://example.com)> 引用`code`- 列表。 同时，被你 @
                  的用户也会收到通知
                </p>
              </div>
            </div>
          </div>
        </div>
        <div class="answer-intro">
          <h4 class="answer-num">1个回答</h4>

          <div class="sidebar">
            <button
              type="button"
              class="like"
              data-placement="right"
              data-toggle="tooltip"
              title="问题对人有帮助，内容完整，我也想知道答案"
            >
              <i class="fa fa-caret-up" aria-hidden="true"></i>
            </button>
            <span class="count">0</span>
            <button
              type="button"
              class="hate"
              data-placement="right"
              data-toggle="tooltip"
              title="问题没有实际价值，缺少关键内容，没有改进余地"
            >
              <i class="fa fa-caret-down" aria-hidden="true"></i>
            </button>
          </div>
          <div class="title">
            <p>{{ item.solve }}</p>
            <div class="operate">
              <div class="tool pull-left">
                <span class="time">{{ item.replytime }}回答</span>
                <span class="comment">评论</span>
                <span class="edit">编辑</span>
                <span class="jubao">举报</span>
              </div>

              <div class="myanswer pull-right">
                <img src="~/assets/img/widget-photo.jpg" alt />
                <a href="#">我的回答</a>
              </div>
              <div class="clearfix"></div>
            </div>
            <div class="comment-box">
              <form class="comment-form">
                <textarea row="1" placeholder="使用评论询问更多信息或提出修改意见，请不要在评论里回答问题"></textarea>
                <button type="submit" class="sui-btn submit-comment">提交评论</button>
              </form>
              <div class="tips">
                <p>
                  评论支持部分 Markdown 语法：**bold**_italic_[link](http://example.com)> 引用`code`- 列表。 同时，被你 @
                  的用户也会收到通知
                </p>
              </div>
            </div>
          </div>
          <div class="clearfix"></div>
        </div>
        <div class="edit-answer">
          <h4>撰写答案</h4>
          <div class="edit-tip">
            <h4>你正在撰写答案</h4>
            <p>如果你是要对问题或其他回答进行点评或询问，请使用“评论”功能。</p>
          </div>
        </div>
      </div>
    </div>
    <div class="fl right-tag">
      <div class="similar-problem">
        <h3 class="title">相似问题</h3>
        <ul class="problem-list">
          <li class="list-item">
            <p class="list-title">求一份浏览器中html css javascript jquery ajax的渲染顺序与原理！！</p>
            <p class="list-info">
              <a href>1 回答</a> | 已解决
            </p>
          </li>
          <li class="list-item">
            <p class="list-title">求一份浏览器中html css javascript jquery ajax的渲染顺序与原理！！</p>
            <p class="list-info">
              <a href>1 回答</a> | 已解决
            </p>
          </li>
        </ul>
      </div>
    </div>
    <div class="clearfix"></div>
  </div>
</template>

<script>
import '~/assets/css/page-sj-qa-detail.css'
import problemApi from '@/api/problem'
import { getUser } from "@/utils/auth"
export default {
  asyncData({ params }) {
    return problemApi.findById(params.id).then(res => {
      return {
        item: res.data.data,
        user: getUser()
      }
    })
  },
  head: {
    script: [
      { src: 'https://cdn.bootcss.com/social-share.js/1.0.16/js/social-share.min.js' }
    ],
    link: [
      { rel: 'stylesheet', href: 'https://cdn.bootcss.com/social-share.js/1.0.16/css/share.min.css' }
    ]
  }
}
</script>