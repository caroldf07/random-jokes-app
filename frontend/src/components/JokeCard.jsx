import './JokeCard.css'

function JokeCard({ joke, onRate, rated }) {
  return (
    <div className="joke-card">
      <span className="joke-category">{joke.category}</span>
      <p className="joke-setup">{joke.setup}</p>
      <p className="joke-punchline">{joke.punchline}</p>

      {!rated && (
        <div className="joke-actions">
          <button className="btn-rate btn-good" onClick={() => onRate('good')}>
            Boa! 👍
          </button>
          <button className="btn-rate btn-bad" onClick={() => onRate('bad')}>
            Ruim! 👎
          </button>
        </div>
      )}

      {rated === 'good' && (
        <p className="joke-rated joke-rated--good">Piada salva! Buscando outra...</p>
      )}
      {rated === 'bad' && (
        <p className="joke-rated joke-rated--bad">Descartada! Buscando outra...</p>
      )}
    </div>
  )
}

export default JokeCard
