import request from './request'

// 获取仓库列表
export function getWarehouses(params) {
  return request({
    url: '/api/warehouses',
    method: 'get',
    params
  })
}

// 添加仓库
export function addWarehouse(data) {
  return request({
    url: '/api/warehouses',
    method: 'post',
    data
  })
}

// 更新仓库
export function updateWarehouse(data) {
  return request({
    url: `/api/warehouses/${data.id}`,
    method: 'put',
    data
  })
}

// 删除仓库
export function deleteWarehouse(id) {
  return request({
    url: `/api/warehouses/${id}`,
    method: 'delete'
  })
}

// API 实例
export const api = {
  getData: () => getWarehouses()
} 