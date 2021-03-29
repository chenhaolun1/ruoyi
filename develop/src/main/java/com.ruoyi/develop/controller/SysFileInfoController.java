package com.ruoyi.develop.controller;

import java.util.List;

import com.ruoyi.develop.domain.SysFileInfo;
import com.ruoyi.develop.service.ISysFileInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文件信息Controller
 * 
 * @author ruoyi
 * @date 2020-12-29
 */
@RestController
@RequestMapping("/system/info")
public class SysFileInfoController extends BaseController
{
    @Autowired
    private ISysFileInfoService sysFileInfoService;

    /**
     * 查询文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(com.ruoyi.develop.domain.SysFileInfo sysFileInfo)
    {
        startPage();
        List<com.ruoyi.develop.domain.SysFileInfo> list = sysFileInfoService.selectSysFileInfoList(sysFileInfo);
        return getDataTable(list);
    }

    /**
     * 导出文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:export')")
    @Log(title = "文件信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(com.ruoyi.develop.domain.SysFileInfo sysFileInfo)
    {
        List<com.ruoyi.develop.domain.SysFileInfo> list = sysFileInfoService.selectSysFileInfoList(sysFileInfo);
        ExcelUtil<com.ruoyi.develop.domain.SysFileInfo> util = new ExcelUtil<com.ruoyi.develop.domain.SysFileInfo>(com.ruoyi.develop.domain.SysFileInfo.class);
        return util.exportExcel(list, "info");
    }

    /**
     * 获取文件信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") Long fileId)
    {
        return AjaxResult.success(sysFileInfoService.selectSysFileInfoById(fileId));
    }

    /**
     * 新增文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "文件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody com.ruoyi.develop.domain.SysFileInfo sysFileInfo)
    {
        return toAjax(sysFileInfoService.insertSysFileInfo(sysFileInfo));
    }

    /**
     * 修改文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "文件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysFileInfo sysFileInfo)
    {
        return toAjax(sysFileInfoService.updateSysFileInfo(sysFileInfo));
    }

    /**
     * 删除文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "文件信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable Long[] fileIds)
    {
        return toAjax(sysFileInfoService.deleteSysFileInfoByIds(fileIds));
    }
}
