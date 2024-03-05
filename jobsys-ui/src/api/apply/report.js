import request from '@/utils/request'

// 查询report列表
export function listReport(query) {
  return request({
    url: '/work/report/list',
    method: 'get',
    params: query
  })
}

// 查询report详细
export function getReport(reportId) {
  return request({
    url: '/work/report/' + reportId,
    method: 'get'
  })
}

// 新增report
export function addReport(data) {
  return request({
    url: '/work/report',
    method: 'post',
    data: data
  })
}

// 修改report
export function updateReport(data) {
  return request({
    url: '/work/report',
    method: 'put',
    data: data
  })
}

// 删除report
export function delReport(reportId) {
  return request({
    url: '/work/report/' + reportId,
    method: 'delete'
  })
}
