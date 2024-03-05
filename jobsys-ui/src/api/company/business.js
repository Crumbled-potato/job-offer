import request from '@/utils/request'

// 查询business列表
export function listBusiness(query) {
  return request({
    url: '/work/business/list',
    method: 'get',
    params: query
  })
}

// 查询business详细
export function getBusiness(businessId) {
  return request({
    url: '/work/business/' + businessId,
    method: 'get'
  })
}

// 新增business
export function addBusiness(data) {
  return request({
    url: '/work/business',
    method: 'post',
    data: data
  })
}

// 修改business
export function updateBusiness(data) {
  return request({
    url: '/work/business',
    method: 'put',
    data: data
  })
}

// 删除business
export function delBusiness(businessId) {
  return request({
    url: '/work/business/' + businessId,
    method: 'delete'
  })
}
