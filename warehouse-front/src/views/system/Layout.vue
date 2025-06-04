<template>
  <div class="system-layout">
    <el-container>
      <el-aside width="200px">
        <el-menu
          :router="true"
          :default-active="$route.path"
          class="system-menu"
        >
          <el-menu-item index="/system/categories">
            <el-icon><Folder /></el-icon>
            <span>分类管理</span>
          </el-menu-item>
          <el-menu-item index="/system/units">
            <el-icon><ScaleToOriginal /></el-icon>
            <span>单位管理</span>
          </el-menu-item>
          <el-menu-item index="/system/statuses">
            <el-icon><List /></el-icon>
            <span>状态管理</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { Folder, ScaleToOriginal, List } from '@element-plus/icons-vue'
</script>

<style scoped>
.system-layout {
  height: 100%;
}

.el-container {
  height: 100%;
}

.el-aside {
  background-color: #f5f7fa;
  border-right: solid 1px #e6e6e6;
}

.system-menu {
  border-right: none;
}

.el-main {
  padding: 0;
  background-color: #fff;
}
</style> 