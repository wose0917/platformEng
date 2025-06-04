<template>
  <div class="home-container">
    <el-card class="welcome-card">
      <template #header>
        <div class="card-header">
          <h2>欢迎使用仓库管理系统</h2>
        </div>
      </template>
      <div class="welcome-content">
        <p>这是一个简单的仓库管理系统，您可以管理您的仓库信息。</p>
        <el-button type="primary" @click="goToWarehouseForm">开始管理仓库</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goToWarehouseForm = () => {
  router.push('/warehouse')
}
</script>

<style scoped>
.home-container {
  padding: 20px;
}

.welcome-card {
  max-width: 800px;
  margin: 0 auto;
  text-align: center;
}

.card-header {
  text-align: center;
}

.welcome-content {
  padding: 20px 0;
}

.welcome-content p {
  margin-bottom: 20px;
  font-size: 16px;
  color: #666;
}
</style>
