<script setup lang="ts">

defineProps<{
  executions: any[]
}>();

const emit = defineEmits(['executionSelected']);

const selectRow = (data:any)=>{
  emit('executionSelected',data);
}
</script>

<template>
    <Panel>
        <DataTable :value="executions" stripedRows
                   dataKey="session"
                   scrollable scrollHeight="30em" :virtualScrollerOptions="{ itemSize: 44 }"
                   tableStyle="min-width: 50rem;">
          <Column class="w-24 !text-end">
            <template #body="{ data }">
              <Button icon="pi pi-search" @click="selectRow(data)" severity="secondary" rounded></Button>
            </template>
          </Column>
          <Column field="session" header="Session" sortable></Column>
          <Column field="name" header="Name" sortable></Column>
          <Column field="date" header="Date" sortable></Column>
          <Column field="status" header="Status" sortable></Column>
          <Column field="tasks.length" header="Tasks"></Column>
          <Column field="cmd" header="Cmd"></Column>
        </DataTable>
    </Panel>
</template>
