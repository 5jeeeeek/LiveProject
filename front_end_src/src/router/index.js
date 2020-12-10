import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../user/login.vue'
import Join from '../user/Join.vue'
import Mypage from '../user/Mypage.vue'
import MyInfo from '../user/MyInfo.vue'
import Seat from '../user/Seat.vue'
import Board from '../board/Board.vue'
import BoardDetail from '../board/BoardDetail.vue'
import MyBoard from '../board/MyBoard.vue'
import Notice from '../board/Notice.vue'
import NoticeDetail from '../board/NoticeDetail.vue'
import ItemList from '../item/ItemList.vue'
import ItemDetail from '../item/ItemDetail.vue'
import WishList from '../item/WishList.vue'
import Note from '../user/Note.vue'
import AdminPage from '../admin/AdminPage.vue'
import UserAdmin from '../admin/UserAdmin.vue'
import BlackList from '../admin/BlackList.vue'
import ItemAdmin from '../admin/ItemAdmin.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/user/login',
    name: 'login',
    component: Login
  },
  {
    path: '/user/join',
    name: 'join',
    component: Join
  },
  {
    path: '/user/mypage',
    name: 'mypage',
    component: Mypage
  },
  {
    path: '/user/myinfo',
    name: 'myinfo',
    component: MyInfo
  },
  {
    path: '/board/board',
    name: 'board',
    component: Board
  },
  {
    path: '/board/boarddetail/:bcode',
    name: 'boarddetail',
    component: BoardDetail
  },
  {
    path: '/board/myboard',
    name: 'myboard',
    component: MyBoard
  },
  {
    path: '/board/notice',
    name: 'notice',
    component: Notice
  },
  {
    path: '/board/noticedetail/:tcode',
    name: 'noticedetail',
    component: NoticeDetail
  },
  {
    path: '/item/itemlist',
    name: 'itemlist',
    component: ItemList
  },
  {
    path: '/item/itemdetail/:icode',
    name: 'itemdetail',
    component: ItemDetail
  },
  {
    path: '/item/wishlist',
    name: 'wishlist',
    component: WishList
  },
  {
    path: '/user/note',
    name: 'note',
    component: Note
  },
  {
    path: '/user/seat',
    name: 'seat',
    component: Seat
  },
  {
    path: '/admin/adminpage',
    name: 'adminpage',
    component: AdminPage
  },
  {
    path: '/admin/useradmin',
    name: 'useradmin',
    component: UserAdmin
  },
  {
    path: '/admin/blacklist',
    name: 'blacklist',
    component: BlackList
  },
  {
    path: '/admin/itemadmin',
    name: 'itemadmin',
    component: ItemAdmin
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
