import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import WarehouseForm from '../views/WarehouseForm.vue'
import InboundRecord from '../views/InboundRecord.vue'

const routes = [
  {
    path: '/',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('@/views/Dashboard.vue')
  },
  {
    path: '/system',
    name: 'System',
    component: () => import('@/views/system/Layout.vue'),
    children: [
      {
        path: 'categories',
        name: 'CategoryManagement',
        component: () => import('@/views/system/CategoryManagement.vue'),
        meta: { title: '分类管理' }
      },
      {
        path: 'units',
        name: 'UnitManagement',
        component: () => import('@/views/system/UnitManagement.vue'),
        meta: { title: '单位管理' }
      },
      {
        path: 'statuses',
        name: 'StatusManagement',
        component: () => import('@/views/system/StatusManagement.vue'),
        meta: { title: '状态管理' }
      }
    ]
  },
  {
    path: '/warehouse',
    name: 'warehouse',
    component: WarehouseForm
  },
  {
    path: '/inbound',
    name: 'inbound',
    component: InboundRecord
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
