package com.ruoyi.summarize.controller;

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
import com.ruoyi.summarize.domain.WorkflowSummarize;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.summarize.service.IWorkflowSummarizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 请求Controller
 *
 * @author danny
 * @date 2020-10-28
 */
@RestController
@RequestMapping("/workflow/summarize")
public class WorkflowSummarizeController extends BaseController {
    @Autowired
    private IWorkflowSummarizeService workflowSummarizeService;

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询总结列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:summarize:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkflowSummarize workflowSummarize) {
        startPage();
        workflowSummarize.setCreateBy(SecurityUtils.getUsername());
        List<WorkflowSummarize> list = workflowSummarizeService.selectWorkflowSummarizeAndTaskNameList(workflowSummarize);
        return getDataTable(list);
    }
    /**
     * 查询总结列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:summarize:list')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(WorkflowSummarize workflowSummarize) {
        startPage();
        List<WorkflowSummarize> list = workflowSummarizeService.selectWorkflowSummarizeList(workflowSummarize);
        return getDataTable(list);
    }

    /**
     * 导出总结列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:summarize:export')")
    @Log(title = "请求", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WorkflowSummarize workflowSummarize) {
        List<WorkflowSummarize> list = workflowSummarizeService.selectWorkflowSummarizeList(workflowSummarize);
        ExcelUtil<WorkflowSummarize> util = new ExcelUtil<WorkflowSummarize>(WorkflowSummarize.class);
        return util.exportExcel(list, "summarize");
    }

    /**
     * 获取总结详细信息
     */
    @PreAuthorize("@ss.hasPermi('workflow:summarize:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(workflowSummarizeService.selectWorkflowSummarizeById(id));
    }


    /**
     * 新增总结
     */
    @PreAuthorize("@ss.hasPermi('workflow:summarize:add')")
    @Log(title = "适配总结", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkflowSummarize workflowSummarize) {
        return toAjax(workflowSummarizeService.insertWorkflowSummarize(workflowSummarize));
    }

    /**
     * 修改总结
     */
    @PreAuthorize("@ss.hasPermi('workflow:summarize:edit')")
    @Log(title = "适配总结", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkflowSummarize workflowSummarize) {
        return toAjax(workflowSummarizeService.insertWorkflowSummarize(workflowSummarize));
    }

    /**
     * 删除总结
     */
    @PreAuthorize("@ss.hasPermi('workflow:summarize:remove')")
    @Log(title = "适配总结", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(workflowSummarizeService.deleteWorkflowSummarizeByIds(ids));
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    //@PostMapping("/upload")
    @ResponseBody
    public AjaxResult uploadFile(MultipartFile file, WorkflowSummarize workflowSummarize) throws Exception
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

//          WorkflowSummarize workflowSummarize = new WorkflowSummarize();
            workflowSummarize.setRealName(fileRealName);
            workflowSummarize.setShowName(showName);
            workflowSummarize.setFilePath(path);
            workflowSummarizeService.insertWorkflowSummarize(workflowSummarize);

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

            WorkflowSummarize workflowSummarize = new WorkflowSummarize();
            workflowSummarize.setRealName(downloadName);
            List<WorkflowSummarize> fileList = workflowSummarizeService.selectWorkflowSummarizeList(workflowSummarize);
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

