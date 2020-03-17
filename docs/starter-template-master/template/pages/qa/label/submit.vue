<template>
  <div class="wrapper">
    <div class="qa-submit-form">
      <form action class="sui-form">
        <div class="input-title">
          <input
            v-model="pojo.title"
            type="text"
            placeholder="标题：一句话说清问题，用问号结尾"
            class="input-xfat input-xxlarge title"
          />
        </div>
        <div class="tags-area">
          <div class="input-tags">
            <input
              v-model="pojo.labels"
              type="text"
              placeholder="标签，如:php可使用逗号，来分隔"
              class="input-xfat input-xxlarge"
              id="tags"
            />
          </div>
        </div>
        <div class="editor">
          <div
            class="quill-editor"
            @change="onEditorChange($event)"
            :content="pojo.content"
            v-quill:myQuillEditor="editorOption"
          ></div>
        </div>
        <div class="submit">
          <span>
            <a class="sui-btn btn-release" @click="save">发布问题</a>
          </span>
        </div>
        <div class="clearfix"></div>
      </form>
    </div>
  </div>
</template>

<script>
import "~/assets/css/page-sj-qa-submit.css";
import problemApi from "@/api/problem";

export default {
  data() {
    return {
      editorOption: {
        modules: {
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'],
            ['blockquote', 'code-block']
          ]
        }
      },
      pojo: { content: '' },      // 问答实体类
    };
  },
  methods: {
    onEditorChange({ editor, html, text }) {
      this.pojo.content = html;
    },
    save() {
      // 判断
      if (this.pojo.title === undefined || this.pojo.title.trim() === "") {
        this.$message.warning('必须填写内容哦～')
        return
      }
      if (this.pojo.content.trim() === "") {
        this.$message.warning('必须填写内容哦～')
        return
      }
      problemApi.save(this.pojo).then(res => {
        this.$message({
          showClose: true,
          message: res.data.message,
          type: (res.data.flag ? 'success' : "error"),
        })
        if (res.data.flag) {
          // 添加成功跳转
          this.$router.push("/qa");
        }
      });
    },
  },
}
</script>


<style>
.quill-editor {
  min-height: 200px;
  max-height: 400px;
  overflow-y: auto;
}
</style>
