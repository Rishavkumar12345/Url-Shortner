import { BrowserRouter as Router } from 'react-router-dom'
import './App.css'

import { getapp } from './Utils/helper'

function App() {

  const CurrentApp=getapp();
  return (
    <>
      <Router>
        <CurrentApp/>
      </Router>
    </>
  )
}

export default App
