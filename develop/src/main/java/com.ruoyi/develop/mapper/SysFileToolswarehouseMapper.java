package com.ruoyi.develop.mapper;

import com.ruoyi.develop.domain.SysFileToolswarehouse;

import java.util.List;

/**
 * 文件信息Mapper接口
 * 
 * @author xiefei
 * @date 2020-12-29
 */
public interface SysFileToolswarehouseMapper
{
    /**
     * 查询文件信息
     * 
     * @param fileId 文件信息ID
     * @return 文件信息
     */
    public SysFileToolswarehouse selectSysFileToolswarehouseById(Long fileId);

    /**
     * 查询文件信息列表
     * 
     * @param sysFileToolswarehouse 文件信息
     * @return 文件信息集合
     */
    public List<SysFileToolswarehouse> selectSysFileToolswarehouseList(SysFileToolswarehouse sysFileToolswarehouse);

    /**
     * 新增文件信息
     * 
     * @param sysFileToolswarehouse 文件信息
     * @return 结果
     */
    public int insertSysFileToolswarehouse(SysFileToolswarehouse sysFileToolswarehouse);

    /**
     * 修改文件信息
     * 
     * @param sysFileToolswarehouse 文件信息
     * @return 结果
     */
    public int updateSysFileToolswarehouse(SysFileToolswarehouse sysFileToolswarehouse);

    /**
     * 删除文件信息
     * 
     * @param fileId 文件信息ID
     * @return 结果
     */
    public int deleteSysFileToolswarehouseById(Long fileId);

    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysFileToolswarehouseByIds(Long[] fileIds);
}
