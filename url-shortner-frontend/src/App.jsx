import { BrowserRouter, Routes,Route } from 'react-router-dom'
import './App.css'
import LandingPage from './component/LandingPage'
import AboutPage from './component/AboutPage'
import Navbar from './component/Navbar'
import Footer from './component/Footer'
import RegisterPage from './component/RegisterPage'
import Login from './component/Login'
import { Toaster } from 'react-hot-toast'
import Dashboard from './component/Dashboard/Dashboard'

function App() {


  return (
    <>
      <BrowserRouter>
        <Navbar/>
        <Toaster  position='bottom-center' autoClose={3000} />
          <Routes>
            <Route path="/" element={<LandingPage/>}/>
            <Route path="/about" element={<AboutPage/>}/>
            <Route path="/register" element={<RegisterPage/>}/>
            <Route path="/login" element={<Login/>}/>
            <Route path="/dashboard" element={<Dashboard/>}/>
          </Routes>
        <Footer/>
      </BrowserRouter>
    </>
  )
}

export default App
