import { useState, useEffect } from 'react'
import JokeCard from './components/JokeCard'
import './App.css'

const API_BASE_URL = 'http://localhost:8080/api/jokes'

function App() {
  const [joke, setJoke] = useState(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  const fetchRandomJoke = async () => {
    setLoading(true)
    setError(null)
    try {
      const response = await fetch(`${API_BASE_URL}/random`)
      if (!response.ok) {
        throw new Error('Erro ao buscar piada')
      }
      const data = await response.json()
      setJoke(data)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchRandomJoke()
  }, [])

  return (
    <div className="app">
      <header className="app-header">
        <h1>Piadas Aleatórias</h1>
        <p className="subtitle">Clique no botão para ouvir uma piada!</p>
      </header>

      <main className="app-main">
        {loading && <div className="loading">Carregando...</div>}
        {error && <div className="error">Erro: {error}</div>}
        {joke && !loading && <JokeCard joke={joke} />}

        <button
          className="btn-new-joke"
          onClick={fetchRandomJoke}
          disabled={loading}
        >
          {loading ? 'Carregando...' : 'Nova Piada!'}
        </button>
      </main>
    </div>
  )
}

export default App
