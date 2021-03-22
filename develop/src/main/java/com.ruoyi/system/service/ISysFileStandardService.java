package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysFileStandard;

/**
 * 文件信息Service接口
 * 
 * @author xiefei
 * @date 2020-12-29
 */
public interface ISysFileStandardService 
{
    /**
     * 查询文件信息
     * 
     * @param fileId 文件信息ID
     * @return 文件信息
     */
    public SysFileStandard selectSysFileStandardById(Long fileId);

    /**
     * 查询文件信息列表
     * 
     * @param sysFileStandard 文件信息
     * @return 文件信息集合
     */
    public List<SysFileStandard> selectSysFileStandardList(SysFileStandard sysFileStandard);

    /**
     * 新增文件信息
     * 
     * @param sysFileStandard 文件信息
     * @return 结果
     */
    public int insertSysFileStandard(SysFileStandard sysFileStandard);

    /**
     * 修改文件信息
     * 
     * @param sysFileStandard 文件信息
     * @return 结果
     */
    public int updateSysFileStandard(SysFileStandard sysFileStandard);

    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的文件信息ID
     * @return 结果
     */
    public int deleteSysFileStandardByIds(Long[] fileIds);

    /**
     * 删除文件信息信息
     * 
     * @param fileId 文件信息ID
     * @return 结果
     */
    public int deleteSysFileStandardById(Long fileId);
}
