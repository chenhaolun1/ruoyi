<template>
  <div>
    <div>
    <h2>适配总结人：{{form.createName}}</h2>
    <el-form  label-width="80px">
      <el-form-item label="适配总结类型" >
       <el-input v-model="form.type"/>
      </el-form-item>
      <el-form-item label="总结文件">
        <el-input v-model="form.title"/>
      </el-form-item>
      <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="handleDownload(form.realName,form.filePath)"   
          >下载</el-button
          >
    
      <el-form-item label="总结简述" >
        <el-input v-model="form.reason" />
      </el-form-item>
      <el-form-item label="开始时间">
        <el-input v-model="form.summarizeStartTime"/>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-input v-model="form.summarizeEndTime"/>
      </el-form-item>
    </el-form>
    </div>
    <div  v-for="(historyData, index) in fromData"
          :key="index" >
      <h2>{{historyData.taskNodeName}}</h2>
      <h3>审批人:{{historyData.createName}}</h3>
      <h3>审批时间:{{historyData.createdDate}}</h3>
      <el-form v-for="(fistoryFormData, indexH) in historyData.formHistoryDataDTO" :key="indexH" label-width="80px">
        <el-form-item :label=fistoryFormData.title >
          <el-input v-model="fistoryFormData.value"/>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import {listSummarize, getSummarize, delSummarize, addSummarize, updateSummarize, exportSummarize} from '@/api/workflow/summarize'
  import { historyFromData } from '@/api/activiti/historyFormdata';
  import { getToken } from "@/utils/auth";

  export default {
    name: "summarizeHistoryForm",
    props: {
      businessKey: {
        type: String
      }
    },
    data(){
      return{
        // 表单参数
        form: {},
        fromData:[],
      }
    },
    created() {
      // debugger
      this.getSummarize()
      this.historyFromData()
    },
    methods:{
      getSummarize() {
        getSummarize(this.businessKey).then(response => {
          this.form = response.data
        })
      },
      historyFromData() {
        historyFromData(this.businessKey).then(response => {
          this.fromData = response.data
        })
      },
      /** 查询文件信息列表 */
    getList() {
      this.loading = true;
      listInfo(this.queryParams).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        fileId: null,
        showName: null,
        filePath: null,
        realName: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.fileId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加文件信息";
      this.upload.fileList = [];
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const fileId = row.fileId || this.ids
      getInfo(fileId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改文件信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.fileId != null) {
            updateInfo(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const fileIds = row.fileId || this.ids;
      this.$confirm('是否确认删除文件信息编号为"' + fileIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delInfo(fileIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有文件信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportInfo(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    },
      handleDownload(realName,filePath) {
  
        var url = filePath;
        var name = realName;
          console.log("start to download file");
          console.log("realName is:"+name);
          console.log("URL is:"+ url);
      var suffix = url.substring(9, url.length);
      console.log("suffix："+suffix)
      var suff = url.substring(url.lastIndexOf("."), url.length);
      console.log("suff："+suff)
      const a = document.createElement("a");
      a.setAttribute("download", name)
      console.log("download："+name)

      a.setAttribute("target", "_blank")
      a.setAttribute("href","http://localhost:80"+process.env.VUE_APP_BASE_API+ "/workflow/summarize/common/download/resource?resource="+suffix+"/"+name)
      a.click()
    }
    }
  }
</script>

<style scoped>

</style>
