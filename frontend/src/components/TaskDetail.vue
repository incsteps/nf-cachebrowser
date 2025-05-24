<script setup lang="ts">

defineProps<{
  task: any | null
}>();

</script>

<template>
    <Panel>
        <DataTable :value="task?.trace" tableStyle="min-width: 50rem" scrollable scrollHeight="400px">
          <Column header="Key">
            <template #body="slotProps">
              {{ slotProps.index }}
            </template>
          </Column>
          <Column header="Value">
            <template #body="slotProps">
              {{ slotProps.data }}
            </template>
          </Column>
        </DataTable>
    </Panel>
</template>
