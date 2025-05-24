<template>
  <header class="layout-topbar">
    <div class="layout-topbar-logo">
      <div class="logo-image">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 200 150">
          <!-- Barra 1 (inferior) -->
          <rect x="20" y="110" width="100" height="20" fill="#3498db"/>

          <!-- Barra 2 -->
          <rect x="40" y="85" width="100" height="20" fill="#2980b9"/>

          <!-- Barra 3 -->
          <rect x="60" y="60" width="100" height="20" fill="#1f6da8"/>

          <!-- Barra 4 (superior) -->
          <rect x="80" y="35" width="100" height="20" fill="#165a8e"/>
        </svg>
      </div>
      <span class="logo-text">nf-cachebrowser</span>
    </div>

    <div class="layout-topbar-menu">
      <Menubar :model="menuItems" class="layout-menubar">
        <template #start>

        </template>
        <template #end>
          <div class="layout-topbar-actions">
            <Button type="button" class="topbar-theme-button" @click="toggleDarkMode" text rounded>
              <i :class="['pi ', 'pi ', { 'pi-moon': isDarkMode, 'pi-sun': !isDarkMode }]" />
            </Button>
          </div>
        </template>
      </Menubar>
    </div>
  </header>
</template>

<script setup lang="ts">
import { useLayout } from "../composables/useLayout.ts";

const { isDarkMode, toggleDarkMode } = useLayout();
import { ref } from "vue";
import router from "@/router";

const menuItems = ref([
  {
    label: 'Home',
    icon: 'pi pi-home',
    command: () => {
      router.push({name:'home'});
    }
  },
  {
    label: 'Executions',
    icon: 'pi pi-play-circle',
    command: () => {
      router.push({name:'home'});
    }
  },
  {
    label: 'About',
    icon: 'pi pi-about',
    command: () => {
      router.push({name:'about'});
    }
  },
])
</script>

<style scoped>
.layout-topbar {
  background: var(--surface-card);
  border-bottom: 1px solid var(--surface-border);
  padding: 0.5rem 1rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 60px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  position: sticky;
  top: 0;
  z-index: 1000;
}

.layout-topbar-logo {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.logo-image {
  height: 32px;
  width: 32px;
  object-fit: contain;
}

.logo-text {
  font-size: 1.25rem;
  font-weight: 600;
  color: var(--primary-color);
}

.layout-topbar-menu {
  flex: 1;
  margin-left: 2rem;
}

.layout-menubar {
  border: none;
  background: transparent;
  padding: 0;
}

.layout-topbar-actions {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}
</style>