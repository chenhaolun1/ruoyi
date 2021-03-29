<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
<!--      <el-form-item label="文件类别" prop="realName">-->
<!--        <el-input-->
<!--          v-model="queryParams.realName"-->
<!--          placeholder="请输入文件类别"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="文件名称" prop="showName">
        <el-input
          v-model="queryParams.showName"
          placeholder="请输入文件名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件路径" prop="filePath">
        <el-input
          v-model="queryParams.filePath"
          placeholder="请输入文件路径"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件类别" prop="categoriesName">
        <el-input
          v-model="queryParams.categoriesName"
          placeholder="请输入文件类别"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:toolswarehouse:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:toolswarehouse:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:toolswarehouse:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:toolswarehouse:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="toolswarehouseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="文件类别" align="center" prop="categoriesName" />
<!--      <el-table-column label="文件id" align="center" prop="fileId" />-->
<!--      <el-table-column label="文件id" align="center" prop="realName" />-->
      <el-table-column label="文件名称" align="center" prop="showName" />
      <el-table-column label="文件路径" align="center" prop="filePath" />

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:toolswarehouse:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:toolswarehouse:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="handleDownload(scope.row)"
          >下载</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改文件信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="选取文件" label-width="5rem">
        <el-upload
          ref="upload"
          :limit="5"
          accept=".doc, .pdf"
          :action="upload.url"
          :headers="upload.headers"
          :file-list="upload.fileList"
          :data="form"
          :on-progress="handleFileUploadProgress"
          :on-success="handleFileSuccess"
          :auto-upload="false"
        >
          <el-button slot="trigger" size="mini" type="primary"
          >选取文件</el-button
          >
        </el-upload>
        </el-form-item>

        <el-form-item label="文件类别" prop="categoriesName">
          <el-input
            v-model="form.categoriesName"
            placeholder="请输入文件类别"
          />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listToolswarehouse, getToolswarehouse, delToolswarehouse, addToolswarehouse, updateToolswarehouse, exportToolswarehouse } from "@/api/system/toolswarehouse";
import { getToken } from "@/utils/auth";

export default {
  name: "Toolswarehouse",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 文件信息表格数据
      toolswarehouseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        realName: null,
        showName: null,
        filePath: null,
        categoriesName: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      // 上传参数
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/toolswarehouse/common/upload",
        // 上传的文件列表
        fileList: [],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询文件信息列表 */
    getList() {
      this.loading = true;
      listToolswarehouse(this.queryParams).then(response => {
        this.toolswarehouseList = response.rows;
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
        realName: null,
        showName: null,
        filePath: null,
        categoriesName: null
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
      getToolswarehouse(fileId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改文件信息";
      });
      this.upload.fileList = [
        { name: this.form.fileName, url: this.form.filePath },
      ];
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // if (this.form.fileId != null) {
          //   updateToolswarehouse(this.form).then((response) => {
          //     this.msgSuccess("修改成功");
          //     this.open = false;
          //     this.getList();
          //   });
          // } else {
          //   addToolswarehouse(this.form).then((response) => {
          //     this.msgSuccess("新增成功");
          //     this.open = false;
          //     this.getList();
          //   });
          // }
          this.submitUpload();
          this.open = false;
          this.getList();
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
          return delToolswarehouse(fileIds);
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
          return exportToolswarehouse(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    },
    // 文件提交处理
    submitUpload() {
      this.$refs.upload.submit();
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.isUploading = false;
      this.form.filePath = response.url;
      this.msgSuccess(response.msg);
      this.getList();
    },
    handleDownload(row) {
      var name = row.realName;
      var url = row.filePath;
      var suffix = url.substring(9, url.length);
      console.log("suffix：" + suffix);
      var suff = url.substring(url.lastIndexOf("."), url.length);
      console.log("suff：" + suff);
      const a = document.createElement("a");
      a.setAttribute("download", name);
      console.log("download：" + name);

      a.setAttribute("target", "_blank");
      a.setAttribute(
        "href",
        "http://localhost:80" +
        process.env.VUE_APP_BASE_API +
        "/system/toolswarehouse/common/download/resource?resource=" +
        suffix +
        "/" +
        name
      );

      a.click();}
  }
};
</script>
