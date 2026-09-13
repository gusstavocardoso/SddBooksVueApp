import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import HomeView from './HomeView.vue'
import axios from 'axios'
import { createRouter, createWebHistory } from 'vue-router'

// Mock axios
vi.mock('axios')

// Setup router
const router = createRouter({
  history: createWebHistory(),
  routes: [{ path: '/', component: HomeView }]
})

describe('HomeView.vue', () => {
  beforeEach(() => {
    vi.resetAllMocks()
  })

  it('renders loading state initially', () => {
    // Return a promise that never resolves for loading state
    axios.get.mockReturnValue(new Promise(() => {}))
    
    const wrapper = mount(HomeView, {
      global: { plugins: [router] }
    })
    
    expect(wrapper.text()).toContain('Loading books...')
  })

  it('renders book list after fetching', async () => {
    const mockBooks = [
      { id: 1, title: 'Test Book', author: 'Author 1', category: 'Sci-Fi', publicationYear: 2020 }
    ]
    axios.get.mockResolvedValue({ data: mockBooks })
    
    const wrapper = mount(HomeView, {
      global: { plugins: [router] }
    })
    
    // Wait for promises to resolve
    await new Promise(r => setTimeout(r, 50))
    
    expect(wrapper.text()).not.toContain('Loading books...')
    expect(wrapper.text()).toContain('Test Book')
    expect(wrapper.text()).toContain('Author 1')
  })

  it('calls API with correct parameters when filtering', async () => {
    axios.get.mockResolvedValue({ data: [] })
    
    const wrapper = mount(HomeView, {
      global: { plugins: [router] }
    })
    
    await new Promise(r => setTimeout(r, 50))
    
    // Change select value
    const select = wrapper.findAll('select')[0] // First select is category
    await select.setValue('Fantasy')
    
    // Expect axios to have been called again with category
    expect(axios.get).toHaveBeenCalledWith('http://localhost:8080/api/books', { params: { category: 'Fantasy' } })
  })
})
