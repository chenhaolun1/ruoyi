package com.ruoyi.develop.service.impl;

import com.ruoyi.develop.domain.SysFileProductwarehouse;
import com.ruoyi.develop.mapper.SysFileProductwarehouseMapper;
import com.ruoyi.develop.service.ISysFileProductwarehouseService;
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
public class SysFileProductwarehouseServiceImpl implements ISysFileProductwarehouseService
{
    @Autowired
    private SysFileProductwarehouseMapper sysFileProductwarehouseMapper;

    /**
     * 查询文件信息
     * 
     * @param fileId 文件信息ID
     * @return 文件信息
     */
    @Override
    public SysFileProductwarehouse selectSysFileProductwarehouseById(Long fileId)
    {
        return sysFileProductwarehouseMapper.selectSysFileProductwarehouseById(fileId);
    }

    /**
     * 查询文件信息列表
     * 
     * @param sysFileProductwarehouse 文件信息
     * @return 文件信息
     */
    @Override
    public List<SysFileProductwarehouse> selectSysFileProductwarehouseList(SysFileProductwarehouse sysFileProductwarehouse)
    {
        return sysFileProductwarehouseMapper.selectSysFileProductwarehouseList(sysFileProductwarehouse);
    }

    /**
     * 新增文件信息
     * 
     * @param sysFileProductwarehouse 文件信息
     * @return 结果
     */
    @Override
    public int insertSysFileProductwarehouse(SysFileProductwarehouse sysFileProductwarehouse)
    {
        return sysFileProductwarehouseMapper.insertSysFileProductwarehouse(sysFileProductwarehouse);
    }

    /**
     * 修改文件信息
     * 
     * @param sysFileProductwarehouse 文件信息
     * @return 结果
     */
    @Override
    public int updateSysFileProductwarehouse(SysFileProductwarehouse sysFileProductwarehouse)
    {
        return sysFileProductwarehouseMapper.updateSysFileProductwarehouse(sysFileProductwarehouse);
    }

    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的文件信息ID
     * @return 结果
     */
    @Override
    public int deleteSysFileProductwarehouseByIds(Long[] fileIds)
    {
        return sysFileProductwarehouseMapper.deleteSysFileProductwarehouseByIds(fileIds);
    }

    /**
     * 删除文件信息信息
     * 
     * @param fileId 文件信息ID
     * @return 结果
     */
    @Override
    public int deleteSysFileProductwarehouseById(Long fileId)
    {
        return sysFileProductwarehouseMapper.deleteSysFileProductwarehouseById(fileId);
    }
}
