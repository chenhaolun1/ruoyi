package com.ruoyi.system.controller;

import java.util.List;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.ServerConfig;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysFileStandard;
import com.ruoyi.system.service.ISysFileStandardService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件信息Controller
 * 
 * @author xiefei
 * @date 2020-12-29
 */
@RestController
@RequestMapping("/system/standard")
public class SysFileStandardController extends BaseController
{
    @Autowired
    private ISysFileStandardService sysFileStandardService;

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:standard:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysFileStandard sysFileStandard)
    {
        startPage();
        List<SysFileStandard> list = sysFileStandardService.selectSysFileStandardList(sysFileStandard);
        return getDataTable(list);
    }

    /**
     * 导出文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:standard:export')")
    @Log(title = "文件信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysFileStandard sysFileStandard)
    {
        List<SysFileStandard> list = sysFileStandardService.selectSysFileStandardList(sysFileStandard);
        ExcelUtil<SysFileStandard> util = new ExcelUtil<SysFileStandard>(SysFileStandard.class);
        return util.exportExcel(list, "standard");
    }

    /**
     * 获取文件信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:standard:query')")
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") Long fileId)
    {
        return AjaxResult.success(sysFileStandardService.selectSysFileStandardById(fileId));
    }

    /**
     * 新增文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:standard:add')")
    @Log(title = "文件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysFileStandard sysFileStandard)
    {
        return toAjax(sysFileStandardService.insertSysFileStandard(sysFileStandard));
    }

    /**
     * 修改文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:standard:edit')")
    @Log(title = "文件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysFileStandard sysFileStandard)
    {
        return toAjax(sysFileStandardService.updateSysFileStandard(sysFileStandard));
    }

    /**
     * 删除文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:standard:remove')")
    @Log(title = "文件信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable Long[] fileIds)
    {
        return toAjax(sysFileStandardService.deleteSysFileStandardByIds(fileIds));
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    //@PostMapping("/upload")
    @ResponseBody
    public AjaxResult uploadFile(MultipartFile file, @RequestParam String categoriesName) throws Exception
    {
        if (StringUtils.isBlank(categoriesName)) {
            return AjaxResult.error("请填写文件标准！");
        }
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

            SysFileStandard sysFileStandard = new SysFileStandard();
            sysFileStandard.setRealName(fileRealName);
            sysFileStandard.setShowName(showName);
            sysFileStandard.setFilePath(path);
            sysFileStandard.setCategoriesName(categoriesName);
            sysFileStandardService.insertSysFileStandard(sysFileStandard);

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

            SysFileStandard sysFileStandard = new SysFileStandard();
            sysFileStandard.setRealName(downloadName);
            List<SysFileStandard> fileList = sysFileStandardService.selectSysFileStandardList(sysFileStandard);
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
            //log.error("下载文件失败", e);
        }
    }


}
