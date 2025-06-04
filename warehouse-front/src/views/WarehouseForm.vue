<template>
  <div class="warehouse-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <h2>仓库管理</h2>
          <el-button type="primary" @click="showAddDialog">添加仓库</el-button>
        </div>
      </template>
    <el-table v-loading="loading" :data="warehouses" style="width: 100%" border>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="仓库名称" />
      <el-table-column prop="location" label="位置" />
      <el-table-column prop="capacity" label="容量" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-radio-group v-model="dataSourceType" @change="handleDataSourceChange">
      <el-radio-button label="DATABASE">数据库</el-radio-button>
      <el-radio-button label="EXCEL">Excel</el-radio-button>
    </el-radio-group>
    <!-- 添加/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑仓库' : '添加仓库'" width="30%">
      <el-form :model="form" label-width="80px">
        <el-form-item label="仓库名称">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="位置">
          <el-input v-model="form.location" />
        </el-form-item>
        <el-form-item label="容量">
          <el-input-number v-model="form.capacity" :min="1" />
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
import { ElMessage, ElMessageBox } from 'element-plus'
import { useWarehouseStore } from '@/stores/warehouse'
import { getWarehouses } from '@/api/warehouse'

const warehouseStore = useWarehouseStore()
const warehouses = computed(() => warehouseStore.warehouses)

const dialogVisible = ref(false)
const isEdit = ref(false)
const loading = ref(false)
const dataSourceType = ref('DATABASE')

const form = ref({
  id: null,
  name: '',
  location: '',
  capacity: 100,
})

const showAddDialog = () => {
  isEdit.value = false
  form.value = { id: null, name: '', location: '', capacity: 100 }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  form.value = { ...row }
  dialogVisible.value = true
}

const handleDelete = (id) => {
  ElMessageBox.confirm('确定要删除这个仓库吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(() => {
      warehouseStore.deleteWarehouse(id)
      ElMessage.success('删除成功')
    })
    .catch(() => {
      // 用户取消
    })
}

const submitForm = () => {
  if (isEdit.value) {
    warehouseStore.updateWarehouse(form.value)
    ElMessage.success('更新成功')
  } else {
    warehouseStore.addWarehouse(form.value)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
}

// 从数据库获取数据
const fetchWarehouses = async () => {
  loading.value = true
  try {
    const response = await getWarehouses({ source: dataSourceType.value })
    warehouseStore.setWarehouses(response.data)
  } catch (error) {
    ElMessage.error('获取仓库列表失败')
    console.error('Error fetching warehouses:', error)
  } finally {
    loading.value = false
  }
}

const handleDataSourceChange = () => {
  fetchWarehouses()
}

onMounted(() => {
  fetchWarehouses()
})
</script>

<style scoped>
.warehouse-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
