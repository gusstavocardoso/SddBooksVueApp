<template>
  <div class="book-form">
    <div class="glass-panel" style="max-width: 600px; margin: 0 auto;">
      <h2 style="margin-bottom: 2rem;">{{ isEdit ? 'Edit Book' : 'Add New Book' }}</h2>
      
      <form @submit.prevent="saveBook">
        <div class="form-group">
          <label class="form-label">Title</label>
          <input type="text" v-model="book.title" class="form-control" required>
        </div>
        
        <div class="form-group">
          <label class="form-label">Author</label>
          <input type="text" v-model="book.author" class="form-control" required>
        </div>
        
        <div style="display: flex; gap: 1rem; margin-bottom: 1rem;">
          <div class="form-group" style="flex: 1; margin-bottom: 0;">
            <label class="form-label">Category</label>
            <input type="text" v-model="book.category" class="form-control" required>
          </div>
          <div class="form-group" style="width: 150px; margin-bottom: 0;">
            <label class="form-label">Year</label>
            <input type="number" v-model="book.publicationYear" class="form-control" required>
          </div>
        </div>
        
        <div class="form-group">
          <label class="form-label">Cover Image URL</label>
          <input type="url" v-model="book.coverImageUrl" class="form-control">
        </div>
        
        <div class="form-group">
          <label class="form-label">Description</label>
          <textarea v-model="book.description" class="form-control" rows="5"></textarea>
        </div>
        
        <div style="display: flex; gap: 1rem; margin-top: 2rem;">
          <button type="submit" class="btn btn-primary" style="flex: 1;" :disabled="saving">
            {{ saving ? 'Saving...' : 'Save Book' }}
          </button>
          <button type="button" @click="router.back()" class="btn btn-secondary">Cancel</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

const route = useRoute()
const router = useRouter()
const isEdit = ref(false)
const saving = ref(false)

const book = ref({
  title: '',
  author: '',
  category: '',
  publicationYear: new Date().getFullYear(),
  coverImageUrl: '',
  description: ''
})

onMounted(async () => {
  if (route.params.id) {
    isEdit.value = true
    try {
      const response = await axios.get(`http://localhost:8080/api/books/${route.params.id}`)
      book.value = response.data
    } catch (error) {
      console.error('Error fetching book:', error)
      alert('Failed to load book data.')
      router.push('/')
    }
  }
})

const saveBook = async () => {
  saving.value = true
  try {
    if (isEdit.value) {
      await axios.put(`http://localhost:8080/api/books/${route.params.id}`, book.value)
    } else {
      await axios.post('http://localhost:8080/api/books', book.value)
    }
    router.push('/')
  } catch (error) {
    console.error('Error saving book:', error)
    alert('Failed to save book. Please try again.')
  } finally {
    saving.value = false
  }
}
</script>
