<template>
    <header>
        <div class="nav">
            <div class="heading">
            <h1 class="title">Under the Korea</h1>    
            <h3 class="subtitle">Citizen Journalism</h3>
            </div>
            <hr class="divider">
            <div class="button_nav">
            <nav>
                <router-link class="nav_button" to="/philosophy">philosophy</router-link> |
                <router-link class="nav_button" to="/politics">politics</router-link> |
                <router-link class="nav_button" to="/suggest">suggest</router-link> |
                <router-link class="nav_button" to="/motto">motto</router-link>
                <input type="text" class="search" placeholder="search">
            </nav>
            <router-view/>
            </div>
            <hr class="divider">
        </div>
    </header>
</template>

<script>
    export default {
        
    }
</script>

<style scoped>
@font-face {
  font-family: 'Italianno-Regular';
  src: url('@/assets/fonts/Italianno-Regular.ttf') format('truetype');
}

#app {
  font-family: 'Italianno-Regular', Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
html, body header {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.main, .nav {
  margin: 0;
  background-color: #f4f4f4;
  min-height: 100vh;
}
.container {
  max-width: 100%;
  margin: 0 auto;
  padding: 2% 1.5%;
}
.heading {
  padding-top : 1%;
  text-align: center;
}
.title {
  font-family: 'Times New Roman', Times, serif;
  font-size: 3rem;
  font-style: italic;
  margin: 0;
}
.subtitle {
  font-family: 'Times New Roman', Times, serif;
  font-size: 1.8rem;
  font-style: italic;
  margin: 0;
}
.divider {
  border: none;
  border-top: 0.2vw solid #222;
  margin: 1.5% 0;
}
.nav_button {
  background: transparent;
  border: none;
  color: #222;
  font-family: 'Times New Roman', serif;
  font-style: italic;
  font-size: 1.1rem;
  padding: 0.7% 0;
  cursor: pointer;
  transition: 
    color 0.5s cubic-bezier(.4,0,.2,1),
    transform 0.5s cubic-bezier(.4,0,.2,1),
    background 0.5s cubic-bezier(.4,0,.2,1);
  outline: none;
  min-width: 8%;
  width: 9%;
  max-width: 13%;
  margin: 0 2%;
}

.nav_button:hover,
.nav_button:focus {
  color: #7a5c20;
  transform: translateY(-4%);
}

.search {
  flex: 1 1 30%;
  border: 0.15vw solid gray;
  border-radius: 20px;
  padding: 0.7% 1.5%;
  font-size: 1.2rem;
  font-family: 'Times New Roman', Times, serif;
  font-style: italic;
  outline: none;
  margin-left: 1%;
}
  @media (max-width: 800px) {
  .button_nav {
    flex-direction: column;
    gap: 2%;
    align-items: stretch;
  }
  .nav_button,
  .search {
    width: 100%;
    flex: unset;
    margin: 0;
  }
}
</style>