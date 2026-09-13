<template>
  <div class="book-details" v-if="book">
    <div class="glass-panel" style="display: flex; gap: 2rem; flex-wrap: wrap;">
      <img v-if="book.coverImageUrl" :src="book.coverImageUrl" alt="Cover" style="width: 300px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.3);">
      
      <div style="flex: 1; min-width: 300px;">
        <h1 style="margin-bottom: 0.5rem; font-size: 2rem;">{{ book.title }}</h1>
        <p style="font-size: 1.2rem; color: #cbd5e1; margin-bottom: 1rem;">By {{ book.author }}</p>
        
        <div style="display: flex; gap: 1rem; margin-bottom: 2rem;">
          <span style="background: rgba(59, 130, 246, 0.2); color: #93c5fd; padding: 0.25rem 0.75rem; border-radius: 4px;">{{ book.category }}</span>
          <span style="background: rgba(255, 255, 255, 0.1); padding: 0.25rem 0.75rem; border-radius: 4px;">Year: {{ book.publicationYear }}</span>
        </div>
        
        <h3 style="margin-bottom: 0.5rem;">Description</h3>
        <p style="line-height: 1.6; color: #e2e8f0; margin-bottom: 2rem;">{{ book.description || 'No description available.' }}</p>
        
        <div style="display: flex; gap: 1rem;">
          <router-link :to="`/book/${book.id}/edit`" class="btn btn-primary">Edit Book</router-link>
          <button @click="deleteBook" class="btn btn-danger">Delete</button>
        </div>
      </div>
    </div>
  </div>
  <div v-else-if="loading" style="text-align: center; padding: 2rem;">
    Loading details...
  </div>
  <div v-else class="glass-panel" style="text-align: center;">
    Book not found.
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

const route = useRoute()
const router = useRouter()
const book = ref(null)
const loading = ref(true)

const fetchBook = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/api/books/${route.params.id}`)
    book.value = response.data
  } catch (error) {
    console.error('Error fetching book:', error)
  } finally {
    loading.value = false
  }
}

const deleteBook = async () => {
  if (confirm('Are you sure you want to delete this book?')) {
    try {
      await axios.delete(`http://localhost:8080/api/books/${book.value.id}`)
      router.push('/')
    } catch (error) {
      console.error('Error deleting book:', error)
      alert('Failed to delete book.')
    }
  }
}

onMounted(() => {
  fetchBook()
})
</script>
