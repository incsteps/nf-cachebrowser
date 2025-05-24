<script setup lang="ts">
import {onMounted} from "vue";
import router from "@/router";
import {useExecutionsStore} from "@/stores/executions.ts";
import ExecutionsTable from "@/components/ExecutionsTable.vue";

const store = useExecutionsStore();

const onExecutionSelected = (data:any)=>{
  router.push({name:'tasks', params:{id:data?.session}});
}

const refreshExecutions = ()=>{
  store.load();
}
onMounted(()=>{
  refreshExecutions();
})

</script>

<template>

  <div class="executions-view">

    <div class="view-header">
      <div class="header-content">
        <div class="title-section">
          <h1 class="view-title">
            Executions
            <Button
                icon="pi pi-refresh"
                class="p-button-outlined"
                @click="refreshExecutions"
            />
          </h1>
        </div>
      </div>
    </div>

    <div class="view-content">
      <div class="content-container">
        <ExecutionsTable
            :executions="store?.executions"
            @refreshExecutions="refreshExecutions"
            @execution-selected="onExecutionSelected"
        />
      </div>
    </div>
  </div>

</template>
