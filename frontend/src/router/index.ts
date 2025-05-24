import { createRouter, createWebHistory } from 'vue-router'
import ExecutionsView from '../views/ExecutionsView.vue'
import TasksView from "@/views/TasksView.vue";
import TaskView from "@/views/TaskView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: ExecutionsView,
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
    },
    {
      path: '/tasks/:id',
      name: 'tasks',
      component: TasksView,
      props: true,
    },
    {
      path: '/task/:id/:taskId',
      name: 'task',
      component: TaskView,
      props: true,
    },
  ],
})

export default router
