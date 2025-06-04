<template>
  <div class="inbound-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <h2>入库备案</h2>
          <el-button type="primary" @click="showAddDialog">新增入库记录</el-button>
        </div>
      </template>

      <el-table :data="records" style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="productName" label="商品名称" />
        <el-table-column prop="quantity" label="数量" width="100" />
        <el-table-column prop="warehouse" label="入库仓库" />
        <el-table-column prop="handler" label="经手人" width="120" />
        <el-table-column prop="inboundDate" label="入库日期" width="120" />
        <el-table-column prop="approvalStatus" label="审批状态" width="120">
          <template #default="scope">
            <el-tag :type="scope.row.approvalStatus === '已批准' ? 'success' : 'warning'">
              {{ scope.row.approvalStatus }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="approvalDate" label="审批日期" width="120" />
        <el-table-column label="操作" width="220">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button
              size="small"
              type="success"
              @click="handleApprove(scope.row.id)"
              v-if="scope.row.approvalStatus === '待批准'"
            >
              批准
            </el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.row.id)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 添加/编辑对话框 -->
      <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑入库记录' : '添加入库记录'" width="50%">
        <el-form :model="form" label-width="100px">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="商品名称" required>
                <el-input v-model="form.productName" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="数量" required>
                <el-input-number v-model="form.quantity" :min="1" style="width: 100%" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="入库仓库" required>
                <el-select v-model="form.warehouse" style="width: 100%">
                  <el-option
                    v-for="warehouse in warehouses"
                    :key="warehouse.id"
                    :label="warehouse.name"
                    :value="warehouse.name"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="经手人" required>
                <el-input v-model="form.handler" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="入库日期" required>
                <el-date-picker
                  v-model="form.inboundDate"
                  type="date"
                  placeholder="选择日期"
                  style="width: 100%"
                  format="YYYY-MM-DD"
                  value-format="YYYY-MM-DD"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="审批状态" required>
                <el-select v-model="form.approvalStatus" style="width: 100%">
                  <el-option label="待批准" value="待批准" />
                  <el-option label="已批准" value="已批准" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item label="备注">
            <el-input v-model="form.notes" type="textarea" :rows="3" />
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确认</el-button>
        </template>
      </el-dialog>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useInboundStore } from '@/stores/inbound'
import { useWarehouseStore } from '@/stores/warehouse'
import { ElMessage, ElMessageBox } from 'element-plus'

const inboundStore = useInboundStore()
const warehouseStore = useWarehouseStore()

const records = computed(() => inboundStore.records)
const warehouses = computed(() => warehouseStore.warehouses)

const dialogVisible = ref(false)
const isEdit = ref(false)
const form = ref({
  id: null,
  productName: '',
  quantity: 1,
  warehouse: '',
  handler: '',
  approvalStatus: '待批准',
  inboundDate: new Date().toISOString().split('T')[0],
  approvalDate: '',
  notes: ''
})

onMounted(() => {
  // 设置默认仓库
  if (warehouses.value.length > 0) {
    form.value.warehouse = warehouses.value[0].name
  }
})

const showAddDialog = () => {
  isEdit.value = false
  form.value = {
    id: null,
    productName: '',
    quantity: 1,
    warehouse: warehouses.value.length > 0 ? warehouses.value[0].name : '',
    handler: '',
    approvalStatus: '待批准',
    inboundDate: new Date().toISOString().split('T')[0],
    approvalDate: '',
    notes: ''
  }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  form.value = { ...row }
  dialogVisible.value = true
}

const handleDelete = (id) => {
  ElMessageBox.confirm('确定要删除这条入库记录吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    inboundStore.deleteRecord(id)
    ElMessage.success('删除成功')
  }).catch(() => {
    // 用户取消
  })
}

const handleApprove = (id) => {
  ElMessageBox.confirm('确定要批准这条入库记录吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    inboundStore.approveRecord(id)
    ElMessage.success('批准成功')
  }).catch(() => {
    // 用户取消
  })
}

const submitForm = () => {
  if (isEdit.value) {
    inboundStore.updateRecord(form.value)
    ElMessage.success('更新成功')
  } else {
    inboundStore.addRecord(form.value)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
}
</script>

<style scoped>
.inbound-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
