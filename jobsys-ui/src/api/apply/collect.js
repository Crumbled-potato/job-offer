import request from '@/utils/request'

// 查询collect列表
export function listCollect(query) {
  return request({
    url: '/work/collect/list',
    method: 'get',
    params: query
  })
}

// 查询collect详细
export function getCollect(colletId) {
  return request({
    url: '/work/collect/' + colletId,
    method: 'get'
  })
}

// 新增collect
export function addCollect(data) {
  return request({
    url: '/work/collect',
    method: 'post',
    data: data
  })
}


export function collectCollect(data) {
  return request({
    url: '/work/collect/collect',
    method: 'get',
    params: data,
  })
}

// 修改collect
export function updateCollect(data) {
  return request({
    url: '/work/collect',
    method: 'put',
    data: data
  })
}

// 删除collect
export function delCollect(colletId) {
  return request({
    url: '/work/collect/' + colletId,
    method: 'delete'
  })
}
