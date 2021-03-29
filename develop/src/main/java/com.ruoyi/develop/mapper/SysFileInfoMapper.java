package com.ruoyi.develop.mapper;

import com.ruoyi.develop.domain.SysFileInfo;

import java.util.List;

/**
 * 文件信息Mapper接口
 * 
 * @author ruoyi
 * @date 2020-12-29
 */
public interface SysFileInfoMapper 
{
    /**
     * 查询文件信息
     * 
     * @param fileId 文件信息ID
     * @return 文件信息
     */
    public com.ruoyi.develop.domain.SysFileInfo selectSysFileInfoById(Long fileId);

    /**
     * 查询文件信息列表
     * 
     * @param sysFileInfo 文件信息
     * @return 文件信息集合
     */
    public List<com.ruoyi.develop.domain.SysFileInfo> selectSysFileInfoList(com.ruoyi.develop.domain.SysFileInfo sysFileInfo);

    /**
     * 新增文件信息
     * 
     * @param sysFileInfo 文件信息
     * @return 结果
     */
    public int insertSysFileInfo(com.ruoyi.develop.domain.SysFileInfo sysFileInfo);

    /**
     * 修改文件信息
     * 
     * @param sysFileInfo 文件信息
     * @return 结果
     */
    public int updateSysFileInfo(SysFileInfo sysFileInfo);

    /**
     * 删除文件信息
     * 
     * @param fileId 文件信息ID
     * @return 结果
     */
    public int deleteSysFileInfoById(Long fileId);

    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysFileInfoByIds(Long[] fileIds);
}
