import request from '@/utils/request'

// 查询公司列表
export function listCompany(query, type) {
  return request({
    url: '/work/company/list' + "?type=" + type,
    method: 'get',
    params: query
  })
}


export function listCompanyByType(query) {
  return request({
    url: '/work/company/listByType',
    method: 'get',
    params: query
  })
}

// 查询公司详细
export function getCompany(comId) {
  return request({
    url: '/work/company/' + comId,
    method: 'get'
  })
}

// 新增公司
export function addCompany(data) {
  return request({
    url: '/work/company',
    method: 'post',
    data: data
  })
}

// 修改公司
export function updateCompany(data) {
  return request({
    url: '/work/company',
    method: 'put',
    data: data
  })
}

// 删除公司
export function delCompany(comId) {
  return request({
    url: '/work/company/' + comId,
    method: 'delete'
  })
}
