package com.ruoyi.develop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.develop.mapper.SysFileInfoMapper;
import com.ruoyi.develop.domain.SysFileInfo;
import com.ruoyi.develop.service.ISysFileInfoService;

/**
 * 文件信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-12-29
 */
@Service
public class SysFileInfoServiceImpl implements ISysFileInfoService 
{
    @Autowired
    private SysFileInfoMapper sysFileInfoMapper;

    /**
     * 查询文件信息
     * 
     * @param fileId 文件信息ID
     * @return 文件信息
     */
    @Override
    public SysFileInfo selectSysFileInfoById(Long fileId)
    {
        return sysFileInfoMapper.selectSysFileInfoById(fileId);
    }

    /**
     * 查询文件信息列表
     * 
     * @param sysFileInfo 文件信息
     * @return 文件信息
     */
    @Override
    public List<SysFileInfo> selectSysFileInfoList(SysFileInfo sysFileInfo)
    {
        return sysFileInfoMapper.selectSysFileInfoList(sysFileInfo);
    }

    /**
     * 新增文件信息
     * 
     * @param sysFileInfo 文件信息
     * @return 结果
     */
    @Override
    public int insertSysFileInfo(SysFileInfo sysFileInfo)
    {
        return sysFileInfoMapper.insertSysFileInfo(sysFileInfo);
    }

    /**
     * 修改文件信息
     * 
     * @param sysFileInfo 文件信息
     * @return 结果
     */
    @Override
    public int updateSysFileInfo(SysFileInfo sysFileInfo)
    {
        return sysFileInfoMapper.updateSysFileInfo(sysFileInfo);
    }

    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的文件信息ID
     * @return 结果
     */
    @Override
    public int deleteSysFileInfoByIds(Long[] fileIds)
    {
        return sysFileInfoMapper.deleteSysFileInfoByIds(fileIds);
    }

    /**
     * 删除文件信息信息
     * 
     * @param fileId 文件信息ID
     * @return 结果
     */
    @Override
    public int deleteSysFileInfoById(Long fileId)
    {
        return sysFileInfoMapper.deleteSysFileInfoById(fileId);
    }
}
