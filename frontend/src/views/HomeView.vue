<template>
  <div class="home">
    <div class="filters glass-panel" style="margin-bottom: 2rem; display: flex; gap: 1rem; flex-wrap: wrap;">
      <div class="form-group" style="margin-bottom: 0;">
        <label class="form-label" for="categoryFilter">Category Filter</label>
        <select id="categoryFilter" v-model="category" @change="fetchBooks" class="form-control" style="width: 200px;">
          <option value="">All Categories</option>
          <option value="Fantasy">Fantasy</option>
          <option value="Science Fiction">Science Fiction</option>
          <option value="Romance">Romance</option>
          <option value="Fiction">Fiction</option>
        </select>
      </div>

      <div class="form-group" style="margin-bottom: 0;">
        <label class="form-label" for="sortBy">Sort By</label>
        <select id="sortBy" v-model="sortBy" @change="fetchBooks" class="form-control" style="width: 200px;">
          <option value="">Default</option>
          <option value="name">Name (A-Z)</option>
          <option value="year">Publication Year (Newest)</option>
        </select>
      </div>
    </div>

    <div v-if="loading" style="text-align: center; padding: 2rem;">
      Loading books...
    </div>

    <div v-else-if="books.length === 0" class="glass-panel" style="text-align: center;">
      No books found. Try adding some!
    </div>

    <div v-else class="book-grid">
      <div v-for="book in books" :key="book.id" class="book-card glass-panel">
        <img v-if="book.coverImageUrl" :src="book.coverImageUrl" alt="Cover" class="book-cover">
        <div class="book-info" style="flex: 1;">
          <h3 style="margin-bottom: 0.5rem; font-size: 1.2rem;">{{ book.title }}</h3>
          <p style="color: #94a3b8; margin-bottom: 0.5rem; font-size: 0.9rem;">By {{ book.author }}</p>
          <p style="margin-bottom: 1rem; font-size: 0.85rem;">
            <span style="background: rgba(59, 130, 246, 0.2); color: #93c5fd; padding: 0.2rem 0.5rem; border-radius: 4px; margin-right: 0.5rem;">{{ book.category }}</span>
            <span>{{ book.publicationYear }}</span>
          </p>
        </div>
        <div style="display: flex; gap: 0.5rem; margin-top: 1rem;">
          <router-link :to="`/book/${book.id}`" class="btn btn-primary" style="flex: 1;">Details</router-link>
          <button @click="deleteBook(book.id)" class="btn btn-danger">Delete</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const books = ref([])
const category = ref('')
const sortBy = ref('')
const loading = ref(true)

const fetchBooks = async () => {
  loading.value = true
  try {
    const params = {}
    if (category.value) params.category = category.value
    if (sortBy.value) params.sortBy = sortBy.value
    
    const response = await axios.get('http://localhost:8080/api/books', { params })
    books.value = response.data
  } catch (error) {
    console.error('Error fetching books:', error)
  } finally {
    loading.value = false
  }
}

const deleteBook = async (id) => {
  if (confirm('Are you sure you want to delete this book?')) {
    try {
      await axios.delete(`http://localhost:8080/api/books/${id}`)
      fetchBooks()
    } catch (error) {
      console.error('Error deleting book:', error)
      alert('Failed to delete book.')
    }
  }
}

onMounted(() => {
  fetchBooks()
})
</script>
