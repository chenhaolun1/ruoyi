import request from '@/utils/request'

// 查询文件信息列表
export function listProductwarehouse(query) {
  return request({
    url: '/system/productwarehouse/list',
    method: 'get',
    params: query
  })
}

// 查询文件信息详细
export function getProductwarehouse(fileId) {
  return request({
    url: '/system/productwarehouse/' + fileId,
    method: 'get'
  })
}

// 新增文件信息
export function addProductwarehouse(data) {
  return request({
    url: '/system/productwarehouse',
    method: 'post',
    data: data
  })
}

// 修改文件信息
export function updateProductwarehouse(data) {
  return request({
    url: '/system/productwarehouse',
    method: 'put',
    data: data
  })
}

// 删除文件信息
export function delProductwarehouse(fileId) {
  return request({
    url: '/system/productwarehouse/' + fileId,
    method: 'delete'
  })
}

// 导出文件信息
export function exportProductwarehouse(query) {
  return request({
    url: '/system/productwarehouse/export',
    method: 'get',
    params: query
  })
}