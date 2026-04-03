import './JokeCard.css'

function JokeCard({ joke }) {
  return (
    <div className="joke-card">
      <span className="joke-category">{joke.category}</span>
      <p className="joke-setup">{joke.setup}</p>
      <p className="joke-punchline">{joke.punchline}</p>
    </div>
  )
}

export default JokeCard
