<script setup lang="ts">
import router from "@/router";
import TaskDetail from "@/components/TaskDetail.vue";
import {useTasksStore} from "@/stores/tasks.ts";

const refIds=defineProps({
  id: String,
  taskId: String
})
const store = useTasksStore();

const getTaskName = (task : any)=>{
  return task?.name||'';
}

const goBack = ()=>{
  store.setSelected(null);
  router.push({name:'tasks', params:{id:refIds.id}});
}

</script>

<template>

  <div class="task-view">

    <div class="view-header">
      <div class="header-content">
        <div class="title-section">
          <h1 class="view-title">
            Task {{getTaskName(store.taskSelected)}}
            <Button
                icon="pi pi-list"
                class="p-button-outlined"
                @click="goBack"
            />
          </h1>
        </div>
      </div>
    </div>

    <div class="view-content">
      <div class="content-container">
        <TaskDetail
            :task="store.taskSelected"
        />
      </div>
    </div>
  </div>
</template>
