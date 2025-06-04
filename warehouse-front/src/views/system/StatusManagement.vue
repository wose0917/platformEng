<template>
  <div class="status-management">
    <div class="header">
      <h2>状态管理</h2>
      <el-button type="primary" @click="handleAdd">新增状态</el-button>
    </div>

    <el-table :data="statuses" v-loading="loading" border>
      <el-table-column prop="code" label="编码" width="120" />
      <el-table-column prop="name" label="名称" width="150" />
      <el-table-column prop="description" label="描述" />
      <el-table-column prop="sortOrder" label="排序" width="80" />
      <el-table-column prop="enabled" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.enabled ? 'success' : 'danger'">
            {{ row.enabled ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="500px"
      @close="resetForm"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="编码" prop="code">
          <el-input v-model="form.code" placeholder="请输入编码" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            placeholder="请输入描述"
          />
        </el-form-item>
        <el-form-item label="排序" prop="sortOrder">
          <el-input-number v-model="form.sortOrder" :min="0" />
        </el-form-item>
        <el-form-item label="状态" prop="enabled">
          <el-switch v-model="form.enabled" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getStatus, createStatus, updateStatus, deleteStatus } from '@/api/baseData'

const loading = ref(false)
const statuses = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref(null)

const form = ref({
  code: '',
  name: '',
  description: '',
  sortOrder: 0,
  enabled: true
})

const rules = {
  code: [
    { required: true, message: '请输入编码', trigger: 'blur' },
    { max: 50, message: '编码长度不能超过50个字符', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入名称', trigger: 'blur' },
    { max: 100, message: '名称长度不能超过100个字符', trigger: 'blur' }
  ],
  description: [
    { max: 500, message: '描述长度不能超过500个字符', trigger: 'blur' }
  ]
}

// 加载状态列表
const loadStatuses = async () => {
  loading.value = true
  try {
    const response = await getStatus()
    statuses.value = response.data
  } catch (error) {
    ElMessage.error('加载状态列表失败：' + error.message)
  } finally {
    loading.value = false
  }
}

// 新增状态
const handleAdd = () => {
  dialogTitle.value = '新增状态'
  dialogVisible.value = true
}

// 编辑状态
const handleEdit = (row) => {
  dialogTitle.value = '编辑状态'
  form.value = { ...row }
  dialogVisible.value = true
}

// 删除状态
const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该状态吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    try {
      await deleteStatus(row.id)
      ElMessage.success('删除成功')
      loadStatuses()
    } catch (error) {
      ElMessage.error('删除失败：' + error.message)
    }
  })
}

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (form.value.id) {
          await updateStatus(form.value.id, form.value)
          ElMessage.success('更新成功')
        } else {
          await createStatus(form.value)
          ElMessage.success('创建成功')
        }
        dialogVisible.value = false
        loadStatuses()
      } catch (error) {
        ElMessage.error('操作失败：' + error.message)
      }
    }
  })
}

// 重置表单
const resetForm = () => {
  if (formRef.value) {
    formRef.value.resetFields()
  }
  form.value = {
    code: '',
    name: '',
    description: '',
    sortOrder: 0,
    enabled: true
  }
}

onMounted(() => {
  loadStatuses()
})
</script>

<style scoped>
.status-management {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header h2 {
  margin: 0;
}
</style> 