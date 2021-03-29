import request from '@/utils/request'

// 查询文件信息列表
export function listToolswarehouse(query) {
  return request({
    url: '/system/toolswarehouse/list',
    method: 'get',
    params: query
  })
}

// 查询文件信息详细
export function getToolswarehouse(fileId) {
  return request({
    url: '/system/toolswarehouse/' + fileId,
    method: 'get'
  })
}

// 新增文件信息
export function addToolswarehouse(data) {
  return request({
    url: '/system/toolswarehouse',
    method: 'post',
    data: data
  })
}

// 修改文件信息
export function updateToolswarehouse(data) {
  return request({
    url: '/system/toolswarehouse',
    method: 'put',
    data: data
  })
}

// 删除文件信息
export function delToolswarehouse(fileId) {
  return request({
    url: '/system/toolswarehouse/' + fileId,
    method: 'delete'
  })
}

// 导出文件信息
export function exportToolswarehouse(query) {
  return request({
    url: '/system/toolswarehouse/export',
    method: 'get',
    params: query
  })
}