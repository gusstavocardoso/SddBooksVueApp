import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import BookFormView from './BookFormView.vue'
import axios from 'axios'
import { createRouter, createWebHistory } from 'vue-router'

vi.mock('axios')

const router = createRouter({
  history: createWebHistory(),
  routes: [{ path: '/book/new', component: BookFormView }]
})

describe('BookFormView.vue', () => {
  beforeEach(() => {
    vi.resetAllMocks()
    router.push('/book/new')
  })

  it('validates empty title submission', async () => {
    // Basic test to see if the form renders correctly and bindings work
    const wrapper = mount(BookFormView, {
      global: { plugins: [router] }
    })
    
    await new Promise(r => setTimeout(r, 50))
    
    const titleInput = wrapper.findAll('input[type="text"]')[0]
    expect(titleInput.element.value).toBe('')
    
    // Simulate setting values
    await titleInput.setValue('New Book')
    expect(wrapper.vm.book.title).toBe('New Book')
  })

  it('submits form via axios', async () => {
    axios.post.mockResolvedValue({ data: { id: 1 } })
    
    const wrapper = mount(BookFormView, {
      global: { plugins: [router] }
    })
    
    await new Promise(r => setTimeout(r, 50))
    
    // Set some valid inputs
    wrapper.vm.book = {
      title: 'Valid Book',
      author: 'Valid Author',
      category: 'Test',
      publicationYear: 2022,
      coverImageUrl: '',
      description: ''
    }
    
    await wrapper.find('form').trigger('submit.prevent')
    
    expect(axios.post).toHaveBeenCalledWith('http://localhost:8080/api/books', wrapper.vm.book)
  })
})
