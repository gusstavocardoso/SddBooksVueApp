<template>
  <div id="app">
    <nav class="nav-bar">
      <h1>SDD Books</h1>
      <div class="nav-links">
        <router-link to="/" class="btn btn-secondary" style="margin-right: 1rem">Catalog</router-link>
        <router-link to="/book/new" class="btn btn-primary">+ Add Book</router-link>
      </div>
    </nav>
    <main>
      <router-view />
    </main>
  </div>
</template>

<style>
@import './assets/main.css';
</style>
