import request from '@/utils/request'

// 查询文件信息列表
export function listStandard(query) {
  return request({
    url: '/system/standard/list',
    method: 'get',
    params: query
  })
}

// 查询文件信息详细
export function getStandard(fileId) {
  return request({
    url: '/system/standard/' + fileId,
    method: 'get'
  })
}

// 新增文件信息
export function addStandard(data) {
  return request({
    url: '/system/standard',
    method: 'post',
    data: data
  })
}

// 修改文件信息
export function updateStandard(data) {
  return request({
    url: '/system/standard',
    method: 'put',
    data: data
  })
}

// 删除文件信息
export function delStandard(fileId) {
  return request({
    url: '/system/standard/' + fileId,
    method: 'delete'
  })
}

// 导出文件信息
export function exportStandard(query) {
  return request({
    url: '/system/standard/export',
    method: 'get',
    params: query
  })
}