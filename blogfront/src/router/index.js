import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from '../views/Index.vue'

Vue.use(VueRouter)

const routes = [
	{
		path: '/',
		name: 'index',
		alias: '/index',
		component: Index,
		redirect: '/index/home',
		children: [
			{
				path: 'home',
				name: 'home',
				component: () => import('../views/index/Home.vue')
			},
			{
				path: 'cate',
				name: 'category',
				component: () => import('../views/index/Category.vue')
			},
			{
				path: 'tag',
				name: 'label',
				component: () => import('../views/index/Label.vue')
			},
			{
				path: 'file',
				name: 'file',
				component: () => import('../views/index/File.vue')
			},
			{
				path: 'about',
				name: 'about',
				component: () => import('../views/index/About.vue')
			},
			{
				path: 'read',
				name: 'read',
				component: () => import('../views/index/Read.vue')
			},
			{
				path: 'search',
				name: 'search',
				component: () => import('../views/index/Search.vue')
			}
		]
	},
	{
		path: '/auth',
		name: 'auth',
		component: () => import('../views/Auth.vue'),
		redirect: '/auth/login',
		children: [
			{
				path: 'login',
				name: 'login',
				component: () => import('../views/auth/Verification.vue')
			}
		]
	},
	{
		path: '/admin',
		name: 'admin',
		component: () => import('../views/Admin.vue'),
		redirect: '/admin/category',
		children: [
			{
				path: 'category',
				name: 'admin-category',
				component: () => import('../views/admin/Category.vue')
			},
			{
				path: 'tag',
				name: 'admin-tag',
				component: () => import('../views/admin/Tag.vue')
			},
			{
				path: 'article',
				name: 'admin-article',
				component: () => import('../views/admin/Article.vue')
			},
			{
				path: 'write',
				name: 'admin-write',
				component: () => import('../views/admin/Write.vue')
			},
			{
				path: 'about',
				name: 'admin-about',
				component: () => import('../views/admin/About.vue')
			},
			{
				path: 'comment',
				name: 'admin-articlecomment',
				component: () => import('../views/admin/ArticleComment.vue')
			},
			{
				path: 'me',
				name: 'admin-me',
				component: () => import('../views/admin/Me.vue')
			}
		]
	},
	{
		path: '*',
		redirect: '/index'
	}
]

const router = new VueRouter({
	routes
})

export default router
