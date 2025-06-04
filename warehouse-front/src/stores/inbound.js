import { defineStore } from 'pinia'

export const useInboundStore = defineStore('inbound', {
  state: () => ({
    records: [
      {
        id: 1,
        productName: '商品A',
        quantity: 100,
        warehouse: '主仓库',
        handler: '张三',
        approvalStatus: '已批准',
        inboundDate: '2023-05-15',
        approvalDate: '2023-05-15',
        notes: '常规补货'
      },
      {
        id: 2,
        productName: '商品B',
        quantity: 50,
        warehouse: '备用仓库',
        handler: '李四',
        approvalStatus: '待批准',
        inboundDate: '2023-05-16',
        approvalDate: '',
        notes: '新商品入库'
      },
      {
        id: 2,
        productName: '商品B',
        quantity: 50,
        warehouse: '备用仓库',
        handler: '李四',
        approvalStatus: '待批准',
        inboundDate: '2023-05-16',
        approvalDate: '',
        notes: '生鲜冷冻入库'
      },
      {
        id: 2,
        productName: '商品B',
        quantity: 50,
        warehouse: '备用仓库',
        handler: '李四',
        approvalStatus: '待批准',
        inboundDate: '2025-05-16',
        approvalDate: '',
        notes: '新商品入库'
      },
      {
        id: 2,
        productName: '商品B',
        quantity: 50,
        warehouse: '备用仓库',
        handler: '李四',
        approvalStatus: '已驳回',
        inboundDate: '2025-05-16',
        approvalDate: '',
        notes: '样品质检不合格'
      }
    ],
    nextId: 3
  }),
  actions: {
    addRecord(record) {
      this.records.push({ ...record, id: this.nextId++ })
    },
    updateRecord(updatedRecord) {
      const index = this.records.findIndex(r => r.id === updatedRecord.id)
      if (index !== -1) {
        this.records[index] = updatedRecord
      }
    },
    deleteRecord(id) {
      this.records = this.records.filter(r => r.id !== id)
    },
    approveRecord(id) {
      const record = this.records.find(r => r.id === id)
      if (record) {
        record.approvalStatus = '已批准'
        record.approvalDate = new Date().toISOString().split('T')[0]
      }
    }
  }
})
