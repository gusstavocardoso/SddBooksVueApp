import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import BookDetailsView from '../views/BookDetailsView.vue'
import BookFormView from '../views/BookFormView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/book/:id',
      name: 'book-details',
      component: BookDetailsView
    },
    {
      path: '/book/new',
      name: 'book-new',
      component: BookFormView
    },
    {
      path: '/book/:id/edit',
      name: 'book-edit',
      component: BookFormView
    }
  ]
})

export default router
