import { render, screen } from '@testing-library/react'
import { describe, it, expect } from 'vitest'
import JokeCard from '../components/JokeCard'

describe('JokeCard', () => {
  const mockJoke = {
    id: 1,
    setup: 'Por que o livro estava triste?',
    punchline: 'Tinha muitos problemas!',
    category: 'Português',
  }

  it('renders the joke setup', () => {
    render(<JokeCard joke={mockJoke} />)
    expect(screen.getByText('Por que o livro estava triste?')).toBeInTheDocument()
  })

  it('renders the joke punchline', () => {
    render(<JokeCard joke={mockJoke} />)
    expect(screen.getByText('Tinha muitos problemas!')).toBeInTheDocument()
  })

  it('renders the joke category', () => {
    render(<JokeCard joke={mockJoke} />)
    expect(screen.getByText('Português')).toBeInTheDocument()
  })

  it('renders all joke fields correctly', () => {
    render(<JokeCard joke={mockJoke} />)
    const card = screen.getByText('Por que o livro estava triste?').closest('.joke-card')
    expect(card).toBeInTheDocument()
  })
})
