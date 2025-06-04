import axios from 'axios'
import { ElMessage } from 'element-plus'

import request from '@/utils/request'

export function getLessonPageAPI(params) {
  return request({
    url: '/lesson/page',
    method: 'get',
    params: params,
  })
}

//导入课程信息
export function importExcel(params) {
  return request({
    url: '/lesson/excel/try-import',
    method: 'post',
    params: params,
    // responseType: 'blob',
  })
}

// 导入校验成功的数据
export function importSuccess(id) {
  return request({
    url: `/lesson/excel/import/${id}`,
    method: 'post',
  })
}

// 导出校验失败的数据
export function exportFail(id) {
  return request({
    url: `/lesson/excel/export/fail/${id}`,
    method: 'get',
    responseType: 'blob'
  })
}


// 下载排课模板
export function downloadModel() {
  return request({
    url: '/lesson/excel/export-template',
    method: 'get',
    responseType: 'blob'
  })
}

// 导出排课数据
export function exportExcel(params) {
  return request({
    url: '/lesson/excel/export',
    method: 'get',
    params: params,
    responseType: 'blob',
  })
}

export function addLessonAPI(lesson) {
  return request({
    url: '/lesson/add',
    method: 'post',
    data: lesson,
  })
}

export function deleteLessonAPI(ids) {
  return request({
    url: '/lesson',
    method: 'delete',
    data: ids,
  })
}

export function updateLessonAPI(lesson) {
  return request({
    url: `/lesson/${lesson.id}`,
    method: 'put',
    data: lesson
  })
}
// 获取教师列表
export function getTeacherListAPI() {
  return request({
    url: '/teacher/getTeacherInfo',
    method: 'get'
  })
}

// 教师分页
export function getTeacherListByPage(params) {
  return request({
    url: '/api/teacherQuery/getTeacherByPage',
    method: 'post',
    params
  })
}

// 复制上学期排课
export function copyLastSemesterSchedule() {
  return request({
    url: '/lesson/copyLastSemester',
    method: 'post'
  })
}



// 创建 axios 实例
const service = axios.create({
  baseURL: 'http://localhost:8080', // API 的基础URL
  timeout: 5000 // 请求超时时间
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 在发送请求之前做些什么
    return config
  },
  error => {
    // 对请求错误做些什么
    console.error('Request error:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    // 直接返回响应数据
    return response
  },
  error => {
    console.error('Response error:', error)
    const message = error.response?.data?.message || error.message || '请求失败'
    ElMessage({
      message: message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service 