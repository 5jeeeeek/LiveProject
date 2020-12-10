import Vue from 'vue';
import "./plugins/axios";
import "./plugins/vuetify";
import App from './App.vue';
import vuetify from './plugins/vuetify';
import router from './router';
import 'url-search-params-polyfill';

// import axios from 'axios'
// import VueAxios from 'vue-axios'

// Vue.use(VueAxios, axios)
var VueCookie = require('vue-cookie');
// Tell Vue to use the plugin
Vue.use(VueCookie);

Vue.config.productionTip = false

new Vue({
  vuetify,
  router,
  render: h => h(App)
}).$mount('#app')
