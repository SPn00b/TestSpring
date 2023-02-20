<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated class="glossy">
      <q-toolbar>
        <q-btn flat dense round @click="leftDrawerOpen = !leftDrawerOpen" aria-label="Menu" icon="menu" />

        <q-toolbar-title>
          {{ title }}
        </q-toolbar-title>

        <!-- <div>Quasar v{{ $q.version }}</div> -->
      </q-toolbar>
    </q-header>


    <q-drawer id="sidenavbutton" v-model="leftDrawerOpen" show-if-above bordered class="bg-grey-2">
      <SideNaigation />
    </q-drawer>

    <q-page-container>
      <HelloWorld />
    </q-page-container>
    <q-page-container>
      <TestVue />
    </q-page-container>
    <q-page-container>
      <TestVue />
    </q-page-container>
  </q-layout>
</template>

<script>
import { ref } from 'vue'
import SideNaigation from './components/SideNav.vue'
import HelloWorld from './components/HelloWorld.vue'
import TestVue from './components/Test.vue'

export default {
  name: 'LayoutDefault',

  data() {
    return {
      title: "Shree Hari Mandir",
    };
  },

  components: {
    SideNaigation,
    HelloWorld,
    TestVue,
  },

  mounted() {
    alert("Mounted!!!");
    // while (document.getElementById("sidenavbutton") != null) {
    //   console.log(1);
    //   document.getElementById("sidenavbutton").click();
    //   break;
    // }
  },

  setup() {
    alert("setup");
    return {
      leftDrawerOpen: ref(false),
    }
  }
}

</script>
