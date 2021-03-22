
import request from '@/utils/request'

// 查询适配实施列表
export function listEnforce(query) {
  return request({
    url: '/workflow/enforce/list',
    method: 'get',
    params: query
  })
}
// 查询适配实施列表
export function listEnforceAll(query) {
  return request({
    url: '/workflow/enforce/listAll',
    method: 'get',
    params: query
  })
}

// 查询适配实施详细
export function getEnforce(id) {
  return request({
    url: '/workflow/enforce/' + id,
    method: 'get'
  })
}


// 新增适配实施
export function addEnforce(data) {
  return request({
    url: '/workflow/enforce',
    method: 'post',
    data: data
  })
}

// 修改适配实施
export function updateEnforce(data) {
  return request({
    url: '/workflow/enforce',
    method: 'put',
    data: data
  })
}

// 删除适配实施
export function delEnforce(id) {
  return request({
    url: '/workflow/enforce/' + id,
    method: 'delete'
  })
}

// 导出适配实施
export function exportEnforce(query) {
  return request({
    url: '/workflow/enforce/export',
    method: 'get',
    params: query
  })
}
