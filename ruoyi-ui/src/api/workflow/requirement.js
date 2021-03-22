import request from '@/utils/request'

// 查询适配需求列表
export function listRequirement(query) {
  return request({
    url: '/workflow/requirement/list',
    method: 'get',
    params: query
  })
}
// 查询适配需求列表
export function listRequirementAll(query) {
  return request({
    url: '/workflow/requirement/listAll',
    method: 'get',
    params: query
  })
}

// 查询适配需求详细
export function getRequirement(id) {
  return request({
    url: '/workflow/requirement/' + id,
    method: 'get'
  })
}


// 新增适配需求
export function addRequirement(data) {
  return request({
    url: '/workflow/requirement',
    method: 'post',
    data: data
  })
}

// 修改适配需求
export function updateRequirement(data) {
  return request({
    url: '/workflow/requirement',
    method: 'put',
    data: data
  })
}

// 删除适配需求
export function delRequirement(id) {
  return request({
    url: '/workflow/requirement/' + id,
    method: 'delete'
  })
}

// 导出适配需求
export function exportRequirement(query) {
  return request({
    url: '/workflow/requirement/export',
    method: 'get',
    params: query
  })
}
