
import request from '@/utils/request'

// 查询适配实施列表
export function listSummarize(query) {
  return request({
    url: '/workflow/summarize/list',
    method: 'get',
    params: query
  })
}
// 查询适配实施列表
export function listSummarizeAll(query) {
  return request({
    url: '/workflow/summarize/listAll',
    method: 'get',
    params: query
  })
}

// 查询适配实施详细
export function getSummarize(id) {
  return request({
    url: '/workflow/summarize/' + id,
    method: 'get'
  })
}


// 新增适配实施
export function addSummarize(data) {
  return request({
    url: '/workflow/summarize',
    method: 'post',
    data: data
  })
}

// 修改适配实施
export function updateSummarize(data) {
  return request({
    url: '/workflow/summarize',
    method: 'put',
    data: data
  })
}

// 删除适配实施
export function delSummarize(id) {
  return request({
    url: '/workflow/summarize/' + id,
    method: 'delete'
  })
}

// 导出适配实施
export function exportSummarize(query) {
  return request({
    url: '/workflow/summarize/export',
    method: 'get',
    params: query
  })
}
