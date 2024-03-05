import request from '@/utils/request'
import * as url from "url";

// 查询applyJob列表
export function listApplyJob(query) {
  return request({
    url: '/work/applyJob/list',
    method: 'get',
    params: query
  })
}


export function listApplyJobOrderBy(query) {
  return request({
    url: '/work/applyJob/listOrderBy',
    method: 'get',
    params: query
  })
}


// 查询applyJob详细
export function getApplyJob(jobId) {
  return request({
    url: '/work/applyJob/' + jobId,
    method: 'get'
  })
}

// 新增applyJob
export function addApplyJob(data) {
  return request({
    url: '/work/applyJob',
    method: 'post',
    data: data
  })
}

// 修改applyJob
export function updateApplyJob(data) {
  return request({
    url: '/work/applyJob',
    method: 'put',
    data: data
  })
}


export function changeState(jobId, state) {
  return request({
    url: '/work/applyJob/changeState/' + jobId + '?state=' + state,
    method: 'get',
  })
}


// 删除applyJob
export function delApplyJob(jobId) {
  return request({
    url: '/work/applyJob/' + jobId,
    method: 'delete'
  })
}
