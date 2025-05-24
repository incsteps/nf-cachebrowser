import {ref} from 'vue'
import {defineStore} from 'pinia'

export const useExecutionsStore = defineStore('executions', () => {
  const executions = ref([])

  const load = async ()=>{
    const data = await fetch(`${import.meta.env.VITE_API_URL}/executions`)
    executions.value = await data.json()
  }

  return { executions, load}
})
