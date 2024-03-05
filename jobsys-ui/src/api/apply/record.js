import request from '@/utils/request'

// 查询record列表
export function listRecord(query) {
  return request({
    url: '/work/record/list',
    method: 'get',
    params: query
  })
}

// 查询record详细
export function getRecord(recordId) {
  return request({
    url: '/work/record/' + recordId,
    method: 'get'
  })
}

// 新增record
export function addRecord(data) {
  return request({
    url: '/work/record',
    method: 'post',
    data: data
  })
}


export function applyAddRecord(data) {
  return request({
    url: '/work/record/applyAdd',
    method: 'post',
    data: data
  })
}




export function applyAddByJob(data) {
  return request({
    url: '/work/record/applyAddByJob',
    method: 'post',
    data: data
  })
}
// 修改record
export function updateRecord(data) {
  return request({
    url: '/work/record',
    method: 'put',
    data: data
  })
}

// 删除record
export function delRecord(recordId) {
  return request({
    url: '/work/record/' + recordId,
    method: 'delete'
  })
}
