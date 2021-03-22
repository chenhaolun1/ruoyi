package com.ruoyi.firstcheck.controller;


import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.firstcheck.domain.WorkflowFirstcheck;
import com.ruoyi.firstcheck.service.IWorkflowFirstcheckService;
import com.ruoyi.framework.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * 实施Controller
 *
 * @author danny
 * @date 2020-10-28
 */
@RestController
@RequestMapping("/workflow/firstcheck")
public class WorkflowFirstcheckController extends BaseController {
    @Autowired
    private IWorkflowFirstcheckService workflowFirstcheckService;

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询实施列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:firstcheck:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkflowFirstcheck workflowFirstcheck) {
        startPage();
        workflowFirstcheck.setCreateBy(SecurityUtils.getUsername());
        List<WorkflowFirstcheck> list = workflowFirstcheckService.selectWorkflowFirstcheckAndTaskNameList(workflowFirstcheck);
        return getDataTable(list);
    }
    /**
     * 查询实施列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:firstcheck:list')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(WorkflowFirstcheck workflowFirstcheck) {
        startPage();
        List<WorkflowFirstcheck> list = workflowFirstcheckService.selectWorkflowFirstcheckList(workflowFirstcheck);
        return getDataTable(list);
    }

    /**
     * 导出实施列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:firstcheck:export')")
    @Log(title = "请假", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WorkflowFirstcheck workflowFirstcheck) {
        List<WorkflowFirstcheck> list = workflowFirstcheckService.selectWorkflowFirstcheckList(workflowFirstcheck);
        ExcelUtil<WorkflowFirstcheck> util = new ExcelUtil<WorkflowFirstcheck>(WorkflowFirstcheck.class);
        return util.exportExcel(list, "firstcheck");
    }

    /**
     * 获取实施详细信息
     */
    @PreAuthorize("@ss.hasPermi('workflow:firstcheck:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(workflowFirstcheckService.selectWorkflowFirstcheckById(id));
    }


    /**
     * 新增实施
     */
    @PreAuthorize("@ss.hasPermi('workflow:firstcheck:add')")
    @Log(title = "适配实施", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkflowFirstcheck workflowFirstcheck) {
        return toAjax(workflowFirstcheckService.insertWorkflowFirstcheck(workflowFirstcheck));
    }

    /**
     * 修改实施
     */
    @PreAuthorize("@ss.hasPermi('workflow:firstcheck:edit')")
    @Log(title = "适配实施", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkflowFirstcheck workflowFirstcheck) {
        return toAjax(workflowFirstcheckService.insertWorkflowFirstcheck(workflowFirstcheck));
    }

    /**
     * 删除实施
     */
    @PreAuthorize("@ss.hasPermi('workflow:firstcheck:remove')")
    @Log(title = "适配实施", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(workflowFirstcheckService.deleteWorkflowFirstcheckByIds(ids));
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    //@PostMapping("/upload")
    @ResponseBody
    public AjaxResult uploadFile(MultipartFile file, WorkflowFirstcheck workflowFirstcheck) throws Exception
    {

        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            //保存文件原有名称
            String showName = file.getOriginalFilename();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);

            String fileRealName = StringUtils.substringAfterLast(fileName, "/");
            String path = fileName.substring(0, fileName.lastIndexOf("/"));

            workflowFirstcheck.setRealName(fileRealName);
            workflowFirstcheck.setShowName(showName);
            workflowFirstcheck.setFilePath(path);
            workflowFirstcheckService.insertWorkflowFirstcheck(workflowFirstcheck);

            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        try
        {
            if (!FileUtils.checkAllowDownload(resource))
            {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            //String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            String downloadPath = localPath + "/"+resource;
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");

            WorkflowFirstcheck workflowFirstcheck = new WorkflowFirstcheck();
            workflowFirstcheck.setRealName(downloadName);
            List<WorkflowFirstcheck> fileList = workflowFirstcheckService.selectWorkflowFirstcheckList(workflowFirstcheck);
            if( fileList == null || fileList.size() == 0) {
                return;
            }
            String showName =  fileList.get(0).getShowName();

//            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//            FileUtils.setAttachmentResponseHeader(response, downloadName);
//            FileUtils.writeBytes(downloadPath+"/"+resource, response.getOutputStream());
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, showName));
            FileUtils.writeBytes(downloadPath, response.getOutputStream());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
