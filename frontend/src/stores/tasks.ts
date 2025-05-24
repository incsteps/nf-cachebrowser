import {ref} from 'vue'
import {defineStore} from 'pinia'

export const useTasksStore = defineStore('tasks', () => {

  const tasks = ref([])

  const taskSelected = ref(null)

  const load = async (id:string)=>{
    const data = await fetch(`${import.meta.env.VITE_API_URL}/tasks/${id}`)
    tasks.value = await data.json()
  }

  const setSelected = (task:any)=>{
    taskSelected.value = task
  }

  return { tasks, load, taskSelected, setSelected }
})
