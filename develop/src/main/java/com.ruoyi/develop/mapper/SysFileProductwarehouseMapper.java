package com.ruoyi.develop.mapper;

import com.ruoyi.develop.domain.SysFileProductwarehouse;

import java.util.List;

/**
 * 文件信息Mapper接口
 * 
 * @author xiefei
 * @date 2020-12-29
 */
public interface SysFileProductwarehouseMapper
{
    /**
     * 查询文件信息
     * 
     * @param fileId 文件信息ID
     * @return 文件信息
     */
    public SysFileProductwarehouse selectSysFileProductwarehouseById(Long fileId);

    /**
     * 查询文件信息列表
     * 
     * @param sysFileProductwarehouse 文件信息
     * @return 文件信息集合
     */
    public List<SysFileProductwarehouse> selectSysFileProductwarehouseList(SysFileProductwarehouse sysFileProductwarehouse);

    /**
     * 新增文件信息
     * 
     * @param sysFileProductwarehouse 文件信息
     * @return 结果
     */
    public int insertSysFileProductwarehouse(SysFileProductwarehouse sysFileProductwarehouse);

    /**
     * 修改文件信息
     * 
     * @param sysFileProductwarehouse 文件信息
     * @return 结果
     */
    public int updateSysFileProductwarehouse(SysFileProductwarehouse sysFileProductwarehouse);

    /**
     * 删除文件信息
     * 
     * @param fileId 文件信息ID
     * @return 结果
     */
    public int deleteSysFileProductwarehouseById(Long fileId);

    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysFileProductwarehouseByIds(Long[] fileIds);
}
