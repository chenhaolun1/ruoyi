package com.ruoyi.enforce.controller;


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
import com.ruoyi.enforce.domain.WorkflowEnforce;
import com.ruoyi.enforce.service.IWorkflowEnforceService;
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
@RequestMapping("/workflow/enforce")
public class WorkflowEnforceController extends BaseController {
    @Autowired
    private IWorkflowEnforceService workflowEnforceService;

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询实施列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:enforce:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkflowEnforce workflowEnforce) {
        startPage();
        workflowEnforce.setCreateBy(SecurityUtils.getUsername());
        List<WorkflowEnforce> list = workflowEnforceService.selectWorkflowEnforceAndTaskNameList(workflowEnforce);
        return getDataTable(list);
    }
    /**
     * 查询实施列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:enforce:list')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(WorkflowEnforce workflowEnforce) {
        startPage();
        List<WorkflowEnforce> list = workflowEnforceService.selectWorkflowEnforceList(workflowEnforce);
        return getDataTable(list);
    }

    /**
     * 导出实施列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:enforce:export')")
    @Log(title = "请假", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WorkflowEnforce workflowEnforce) {
        List<WorkflowEnforce> list = workflowEnforceService.selectWorkflowEnforceList(workflowEnforce);
        ExcelUtil<WorkflowEnforce> util = new ExcelUtil<WorkflowEnforce>(WorkflowEnforce.class);
        return util.exportExcel(list, "enforce");
    }

    /**
     * 获取实施详细信息
     */
    @PreAuthorize("@ss.hasPermi('workflow:enforce:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(workflowEnforceService.selectWorkflowEnforceById(id));
    }


    /**
     * 新增实施
     */
    @PreAuthorize("@ss.hasPermi('workflow:enforce:add')")
    @Log(title = "适配实施", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkflowEnforce workflowEnforce) {
        return toAjax(workflowEnforceService.insertWorkflowEnforce(workflowEnforce));
    }

    /**
     * 修改实施
     */
    @PreAuthorize("@ss.hasPermi('workflow:enforce:edit')")
    @Log(title = "适配实施", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkflowEnforce workflowEnforce) {
        return toAjax(workflowEnforceService.insertWorkflowEnforce(workflowEnforce));
    }

    /**
     * 删除实施
     */
    @PreAuthorize("@ss.hasPermi('workflow:enforce:remove')")
    @Log(title = "适配实施", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(workflowEnforceService.deleteWorkflowEnforceByIds(ids));
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    //@PostMapping("/upload")
    @ResponseBody
    public AjaxResult uploadFile(MultipartFile file, WorkflowEnforce workflowEnforce) throws Exception
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

            workflowEnforce.setRealName(fileRealName);
            workflowEnforce.setShowName(showName);
            workflowEnforce.setFilePath(path);
            workflowEnforceService.insertWorkflowEnforce(workflowEnforce);

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

            WorkflowEnforce workflowEnforce = new WorkflowEnforce();
            workflowEnforce.setRealName(downloadName);
            List<WorkflowEnforce> fileList = workflowEnforceService.selectWorkflowEnforceList(workflowEnforce);
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
