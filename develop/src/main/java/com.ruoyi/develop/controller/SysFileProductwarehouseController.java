package com.ruoyi.develop.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.develop.domain.SysFileProductwarehouse;
import com.ruoyi.develop.service.ISysFileProductwarehouseService;
import com.ruoyi.framework.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件信息Controller
 * 
 * @author xiefei
 * @date 2020-12-29
 */
@RestController
@RequestMapping("/system/productwarehouse")
public class SysFileProductwarehouseController extends BaseController
{
    @Autowired
    private ISysFileProductwarehouseService sysFileProductwarehouseService;

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:productwarehouse:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysFileProductwarehouse sysFileProductwarehouse)
    {
        startPage();
        List<SysFileProductwarehouse> list = sysFileProductwarehouseService.selectSysFileProductwarehouseList(sysFileProductwarehouse);
        return getDataTable(list);
    }

    /**
     * 导出文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:productwarehouse:export')")
    @Log(title = "文件信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysFileProductwarehouse sysFileProductwarehouse)
    {
        List<SysFileProductwarehouse> list = sysFileProductwarehouseService.selectSysFileProductwarehouseList(sysFileProductwarehouse);
        ExcelUtil<SysFileProductwarehouse> util = new ExcelUtil<SysFileProductwarehouse>(SysFileProductwarehouse.class);
        return util.exportExcel(list, "productwarehouse");
    }

    /**
     * 获取文件信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:productwarehouse:query')")
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") Long fileId)
    {
        return AjaxResult.success(sysFileProductwarehouseService.selectSysFileProductwarehouseById(fileId));
    }

    /**
     * 新增文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:productwarehouse:add')")
    @Log(title = "文件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysFileProductwarehouse sysFileProductwarehouse)
    {
        return toAjax(sysFileProductwarehouseService.insertSysFileProductwarehouse(sysFileProductwarehouse));
    }

    /**
     * 修改文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:productwarehouse:edit')")
    @Log(title = "文件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysFileProductwarehouse sysFileProductwarehouse)
    {
        return toAjax(sysFileProductwarehouseService.updateSysFileProductwarehouse(sysFileProductwarehouse));
    }

    /**
     * 删除文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:Productwarehouse:remove')")
    @Log(title = "文件信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable Long[] fileIds)
    {
        return toAjax(sysFileProductwarehouseService.deleteSysFileProductwarehouseByIds(fileIds));
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

            SysFileProductwarehouse sysFileProductwarehouse = new SysFileProductwarehouse();
            sysFileProductwarehouse.setRealName(fileRealName);
            sysFileProductwarehouse.setShowName(showName);
            sysFileProductwarehouse.setFilePath(path);
            sysFileProductwarehouse.setCategoriesName(categoriesName);
            sysFileProductwarehouseService.insertSysFileProductwarehouse(sysFileProductwarehouse);

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

            SysFileProductwarehouse sysFileProductwarehouse = new SysFileProductwarehouse();
            sysFileProductwarehouse.setRealName(downloadName);
            List<SysFileProductwarehouse> fileList = sysFileProductwarehouseService.selectSysFileProductwarehouseList(sysFileProductwarehouse);
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
