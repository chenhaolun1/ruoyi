
import request from '@/utils/request'

// 查询适配实施列表
export function listFirstcheck(query) {
  return request({
    url: '/workflow/firstcheck/list',
    method: 'get',
    params: query
  })
}
// 查询适配实施列表
export function listFirstcheckAll(query) {
  return request({
    url: '/workflow/firstcheck/listAll',
    method: 'get',
    params: query
  })
}

// 查询适配实施详细
export function getFirstcheck(id) {
  return request({
    url: '/workflow/firstcheck/' + id,
    method: 'get'
  })
}


// 新增适配实施
export function addFirstcheck(data) {
  return request({
    url: '/workflow/firstcheck',
    method: 'post',
    data: data
  })
}

// 修改适配实施
export function updateFirstcheck(data) {
  return request({
    url: '/workflow/firstcheck',
    method: 'put',
    data: data
  })
}

// 删除适配实施
export function delFirstcheck(id) {
  return request({
    url: '/workflow/firstcheck/' + id,
    method: 'delete'
  })
}

// 导出适配实施
export function exportFirstcheck(query) {
  return request({
    url: '/workflow/firstcheck/export',
    method: 'get',
    params: query
  })
}
