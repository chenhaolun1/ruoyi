package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysFileStandardMapper;
import com.ruoyi.system.domain.SysFileStandard;
import com.ruoyi.system.service.ISysFileStandardService;

/**
 * 文件信息Service业务层处理
 * 
 * @author xiefei
 * @date 2020-12-29
 */
@Service
public class SysFileStandardServiceImpl implements ISysFileStandardService 
{
    @Autowired
    private SysFileStandardMapper sysFileStandardMapper;

    /**
     * 查询文件信息
     * 
     * @param fileId 文件信息ID
     * @return 文件信息
     */
    @Override
    public SysFileStandard selectSysFileStandardById(Long fileId)
    {
        return sysFileStandardMapper.selectSysFileStandardById(fileId);
    }

    /**
     * 查询文件信息列表
     * 
     * @param sysFileStandard 文件信息
     * @return 文件信息
     */
    @Override
    public List<SysFileStandard> selectSysFileStandardList(SysFileStandard sysFileStandard)
    {
        return sysFileStandardMapper.selectSysFileStandardList(sysFileStandard);
    }

    /**
     * 新增文件信息
     * 
     * @param sysFileStandard 文件信息
     * @return 结果
     */
    @Override
    public int insertSysFileStandard(SysFileStandard sysFileStandard)
    {
        return sysFileStandardMapper.insertSysFileStandard(sysFileStandard);
    }

    /**
     * 修改文件信息
     * 
     * @param sysFileStandard 文件信息
     * @return 结果
     */
    @Override
    public int updateSysFileStandard(SysFileStandard sysFileStandard)
    {
        return sysFileStandardMapper.updateSysFileStandard(sysFileStandard);
    }

    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的文件信息ID
     * @return 结果
     */
    @Override
    public int deleteSysFileStandardByIds(Long[] fileIds)
    {
        return sysFileStandardMapper.deleteSysFileStandardByIds(fileIds);
    }

    /**
     * 删除文件信息信息
     * 
     * @param fileId 文件信息ID
     * @return 结果
     */
    @Override
    public int deleteSysFileStandardById(Long fileId)
    {
        return sysFileStandardMapper.deleteSysFileStandardById(fileId);
    }
}
