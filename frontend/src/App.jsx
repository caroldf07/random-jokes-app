import { useState, useEffect } from 'react'
import JokeCard from './components/JokeCard'
import './App.css'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

function App() {
  const [joke, setJoke] = useState(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)
  const [rated, setRated] = useState(null)

  const fetchRandomJoke = async () => {
    setLoading(true)
    setError(null)
    setRated(null)
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

  const handleRate = async (rating) => {
    setRated(rating)

    if (rating === 'good') {
      try {
        await fetch(API_BASE_URL, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            setup: joke.setup,
            punchline: joke.punchline,
            category: joke.category,
          }),
        })
      } catch (err) {
        console.error('Erro ao salvar piada:', err)
      }
    }

    setTimeout(fetchRandomJoke, 800)
  }

  useEffect(() => {
    fetchRandomJoke()
  }, [])

  return (
    <div className="app">
      <header className="app-header">
        <h1>Piadas Aleatórias</h1>
        <p className="subtitle">Boa ou ruim? Você decide!</p>
      </header>

      <main className="app-main">
        {loading && <div className="loading">Carregando...</div>}
        {error && <div className="error">Erro: {error}</div>}
        {joke && !loading && (
          <JokeCard joke={joke} onRate={handleRate} rated={rated} />
        )}

        <button
          className="btn-new-joke"
          onClick={fetchRandomJoke}
          disabled={loading}
        >
          {loading ? 'Carregando...' : 'Pular piada'}
        </button>
      </main>
    </div>
  )
}

export default App
