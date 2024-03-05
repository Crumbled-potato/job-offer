import request from '@/utils/request'

// 查询userMsg列表
export function listMsg(query) {
  return request({
    url: '/work/msg/list',
    method: 'get',
    params: query
  })
}


export function getUnRead() {
  return request({
    url: '/work/msg/getUnReadSum',
    method: 'get',
  })
}

// 查询userMsg详细
export function getMsg(msgId) {
  return request({
    url: '/work/msg/' + msgId,
    method: 'get'
  })
}

// 新增userMsg
export function addMsg(data) {
  return request({
    url: '/work/msg',
    method: 'post',
    data: data
  })
}

// 修改userMsg
export function updateMsg(data) {
  return request({
    url: '/work/msg',
    method: 'put',
    data: data
  })
}

// 删除userMsg
export function delMsg(msgId) {
  return request({
    url: '/work/msg/' + msgId,
    method: 'delete'
  })
}
