<script setup lang="ts">
import {onMounted} from "vue";
import TasksTable from "@/components/TasksTable.vue";
import {useTasksStore} from "@/stores/tasks.ts";
import router from "@/router";

const refId = defineProps({
  id: String
})
const store = useTasksStore();

const onTaskSelected = (data: any) => {
  store.setSelected(data);
  router.push({name:'task', params:{id:data?.session, taskId:data?.id}});
}

const refreshTasks = ()=>{
  store.load(refId?.id || '');
}

const goBack = ()=>{
  store.setSelected(null);
  router.push({name:'home'});
}

onMounted(()=>{
  refreshTasks()
})
</script>

<template>
  <div class="tasks-view">

    <div class="view-header">
      <div class="header-content">
        <div class="title-section">
          <h1 class="view-title">
            Tasks
            <Button
                icon="pi pi-refresh"
                class="p-button-outlined"
                @click="refreshTasks"
            />
            <Button
                icon="pi pi-list-check"
                class="p-button-outlined"
                @click="goBack"
            />
          </h1>
        </div>
      </div>
    </div>

    <div class="view-content">
      <div class="content-container">
        <TasksTable
            :id="id||''"
            :tasks="store?.tasks"
            @task-selected="onTaskSelected"
            @refresh-tasks="refreshTasks"
        />
      </div>
    </div>
  </div>

</template>
