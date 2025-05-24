<template>
  <main class="layout-main">
    <div class="layout-content">
      <router-view />
    </div>
  </main>
</template>

<script setup lang="ts">
</script>

<style scoped>
.layout-main {
  flex: 1;
  min-height: 90vh;
  background: var(--surface-ground);
}

.layout-content {
  padding: 1.5rem;
  height: 100%;
}
</style>