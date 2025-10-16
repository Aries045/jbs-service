import request from '@/utils/request'

// 查询角色列表
export function listStudent(query) {
    return request({
      url: '/system/student/list',
      method: 'get',
      params: query
    })
  }

  // 新增角色
export function addStudent(data) {
    return request({
      url: '/system/student',
      method: 'post',
      data: data
    })
  }
