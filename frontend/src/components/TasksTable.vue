<script setup lang="ts">

defineProps<{
  id: String,
  tasks: any[]
}>();

const emits = defineEmits(['taskSelected']);

const selectRow = (data:any)=>{
  emits('taskSelected',data);
}

</script>

<template>
    <Panel>
      <DataTable :value="tasks" data-key="id"
                 scrollable scrollHeight="30em" :virtualScrollerOptions="{ itemSize: 44 }"
                 tableStyle="min-width: 50rem;">
        <Column class="w-24 !text-end">
          <template #body="{ data }">
            <Button icon="pi pi-search" @click="selectRow(data)" severity="secondary" rounded></Button>
          </template>
        </Column>
        <Column field="refCount" header="Index" sortable></Column>
        <Column field="name" header="Name" sortable></Column>
        <Column field="status" header="Status" sortable></Column>
        <Column field="trace.hash" header="Hash"></Column>
        <Column field="trace.duration" header="Duration"></Column>
      </DataTable>
    </Panel>
</template>
