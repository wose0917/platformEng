import { defineStore } from 'pinia'
import { getWarehouses, addWarehouse, updateWarehouse, deleteWarehouse } from '@/api/warehouse'

export const useWarehouseStore = defineStore('warehouse', {
  state: () => ({
    warehouses: [],
    loading: false,
    error: null
  }),
  actions: {
    async fetchWarehouses(params) {
      this.loading = true
      try {
        const response = await getWarehouses(params)
        if (response && response.data) {
          this.warehouses = response.data
          this.error = null
        } else {
          throw new Error('Invalid response from server')
        }
      } catch (error) {
        this.error = error.message
        throw error
      } finally {
        this.loading = false
      }
    },
    setWarehouses(warehouses) {
      this.warehouses = warehouses
    },
    async addWarehouse(warehouse) {
      try {
        const response = await addWarehouse(warehouse)
        if (response && response.data) {
          this.warehouses.push(response.data)
          return response.data
        } else {
          throw new Error('Invalid response from server')
        }
      } catch (error) {
        this.error = error.message
        throw error
      }
    },
    async updateWarehouse(warehouse) {
      try {
        const response = await updateWarehouse(warehouse)
        if (response && response.data) {
          const index = this.warehouses.findIndex(w => w.id === warehouse.id)
          if (index !== -1) {
            this.warehouses[index] = response.data
          }
          return response.data
        } else {
          throw new Error('Invalid response from server')
        }
      } catch (error) {
        this.error = error.message
        throw error
      }
    },
    async deleteWarehouse(id) {
      try {
        await deleteWarehouse(id)
        this.warehouses = this.warehouses.filter(w => w.id !== id)
      } catch (error) {
        this.error = error.message
        throw error
      }
    }
  }
})
