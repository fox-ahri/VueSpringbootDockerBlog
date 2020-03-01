import Vue from 'vue'
import App from './App.vue'
import router from './router'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)

import axios from 'axios'
import VueAxios from 'vue-axios'
axios.interceptors.request.use(
	config => {
		config.headers.token = localStorage.getItem('token')
			? localStorage.getItem('token')
			: null
		return config
	},
	err => {
		return Promise.reject(err)
	}
)

Vue.use(VueAxios, axios)

Vue.config.productionTip = false

new Vue({
	router,
	render: h => h(App)
}).$mount('#app')
