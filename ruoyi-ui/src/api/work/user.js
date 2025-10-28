import request from '@/utils/request'

// 查询dm员工管理列表
export function listUser(query) {
  return request({
    url: '/work/user/list',
    method: 'get',
    params: query
  })
}

// 查询dm员工管理详细
export function getUser(id) {
  return request({
    url: '/work/user/get/' + id,
    method: 'get'
  })
}

// 新增dm员工管理
export function addUser(data) {
  return request({
    url: '/work/user/save',
    method: 'post',
    data: data
  })
}

// 修改dm员工管理
export function updateUser(data) {
  return request({
    url: '/work/user/update',
    method: 'put',
    data: data
  })
}

// 删除dm员工管理
export function delUser(id) {
  return request({
    url: '/work/user/delete/' + id,
    method: 'delete'
  })
}


// 员工是否离职
export function isResign(data) {
  return request({
    url: '/work/user/isResign',
    method: 'post',
    data: data
  })
}

