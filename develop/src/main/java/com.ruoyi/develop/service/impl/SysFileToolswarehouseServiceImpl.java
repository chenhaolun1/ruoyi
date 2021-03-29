package com.ruoyi.develop.service.impl;

import com.ruoyi.develop.domain.SysFileToolswarehouse;
import com.ruoyi.develop.mapper.SysFileToolswarehouseMapper;
import com.ruoyi.develop.service.ISysFileToolswarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件信息Service业务层处理
 * 
 * @author xiefei
 * @date 2020-12-29
 */
@Service
public class SysFileToolswarehouseServiceImpl implements ISysFileToolswarehouseService
{
    @Autowired
    private SysFileToolswarehouseMapper sysFileToolswarehouseMapper;

    /**
     * 查询文件信息
     * 
     * @param fileId 文件信息ID
     * @return 文件信息
     */
    @Override
    public SysFileToolswarehouse selectSysFileToolswarehouseById(Long fileId)
    {
        return sysFileToolswarehouseMapper.selectSysFileToolswarehouseById(fileId);
    }

    /**
     * 查询文件信息列表
     * 
     * @param sysFileToolswarehouse 文件信息
     * @return 文件信息
     */
    @Override
    public List<SysFileToolswarehouse> selectSysFileToolswarehouseList(SysFileToolswarehouse sysFileToolswarehouse)
    {
        return sysFileToolswarehouseMapper.selectSysFileToolswarehouseList(sysFileToolswarehouse);
    }

    /**
     * 新增文件信息
     * 
     * @param sysFileToolswarehouse 文件信息
     * @return 结果
     */
    @Override
    public int insertSysFileToolswarehouse(SysFileToolswarehouse sysFileToolswarehouse)
    {
        return sysFileToolswarehouseMapper.insertSysFileToolswarehouse(sysFileToolswarehouse);
    }

    /**
     * 修改文件信息
     * 
     * @param sysFileToolswarehouse 文件信息
     * @return 结果
     */
    @Override
    public int updateSysFileToolswarehouse(SysFileToolswarehouse sysFileToolswarehouse)
    {
        return sysFileToolswarehouseMapper.updateSysFileToolswarehouse(sysFileToolswarehouse);
    }

    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的文件信息ID
     * @return 结果
     */
    @Override
    public int deleteSysFileToolswarehouseByIds(Long[] fileIds)
    {
        return sysFileToolswarehouseMapper.deleteSysFileToolswarehouseByIds(fileIds);
    }

    /**
     * 删除文件信息信息
     * 
     * @param fileId 文件信息ID
     * @return 结果
     */
    @Override
    public int deleteSysFileToolswarehouseById(Long fileId)
    {
        return sysFileToolswarehouseMapper.deleteSysFileToolswarehouseById(fileId);
    }
}
