<template>
  <footer class="layout-footer">
    <div class="layout-footer-content">
      <div class="footer-left">
        <span>&copy; 2025 nf-cachebrowser. Incremental Steps Software Solutions</span>
      </div>
      <div class="footer-right">
        <span>V1.0.0</span>
        <Divider layout="vertical" />
        <a href="#" class="footer-link">Política de Privacidad</a>
        <Divider layout="vertical" />
        <a href="#" class="footer-link">Términos de Uso</a>
      </div>
    </div>
  </footer>
</template>

<style scoped>
.layout-footer {
  background: var(--surface-card);
  border-top: 1px solid var(--surface-border);
  padding: 1rem 1.5rem;
  margin-top: auto;
}

.layout-footer-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
}

.footer-left {
  color: var(--text-color-secondary);
  font-size: 0.875rem;
}

.footer-right {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: var(--text-color-secondary);
  font-size: 0.875rem;
}

.footer-link {
  color: var(--primary-color);
  text-decoration: none;
  transition: color 0.3s;
}

.footer-link:hover {
  color: var(--primary-color-text);
  text-decoration: underline;
}
</style>