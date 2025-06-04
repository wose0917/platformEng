import request from '@/utils/request'

// 分类管理
export const getCategories = (type) => {
  return request({
    url: '/api/base-data/categories',
    method: 'get',
    params: { type }
  })
}

export const createCategory = (data) => {
  return request({
    url: '/api/base-data/categories',
    method: 'post',
    data
  })
}

export const updateCategory = (id, data) => {
  return request({
    url: `/api/base-data/categories/${id}`,
    method: 'put',
    data
  })
}

export const deleteCategory = (id) => {
  return request({
    url: `/api/base-data/categories/${id}`,
    method: 'delete'
  })
}

// 单位管理
export const getUnits = (type) => {
  return request({
    url: '/api/base-data/units',
    method: 'get',
    params: { type }
  })
}

export const createUnit = (data) => {
  return request({
    url: '/api/base-data/units',
    method: 'post',
    data
  })
}

export const updateUnit = (id, data) => {
  return request({
    url: `/api/base-data/units/${id}`,
    method: 'put',
    data
  })
}

export const deleteUnit = (id) => {
  return request({
    url: `/api/base-data/units/${id}`,
    method: 'delete'
  })
}

// 状态管理
export const getStatus = (type) => {
  return request({
    url: '/api/base-data/status',
    method: 'get',
    params: { type }
  })
}

export const createStatus = (data) => {
  return request({
    url: '/api/base-data/status',
    method: 'post',
    data
  })
}

export const updateStatus = (id, data) => {
  return request({
    url: `/api/base-data/status/${id}`,
    method: 'put',
    data
  })
}

export const deleteStatus = (id) => {
  return request({
    url: `/api/base-data/status/${id}`,
    method: 'delete'
  })
} 