import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { describe, it, expect, vi } from 'vitest'
import JokeCard from '../components/JokeCard'

const mockJoke = {
  id: 1,
  setup: 'Por que o livro estava triste?',
  punchline: 'Tinha muitos problemas!',
  category: 'Português',
}

describe('JokeCard', () => {
  it('renders the joke setup', () => {
    render(<JokeCard joke={mockJoke} onRate={vi.fn()} />)
    expect(screen.getByText('Por que o livro estava triste?')).toBeInTheDocument()
  })

  it('renders the joke punchline', () => {
    render(<JokeCard joke={mockJoke} onRate={vi.fn()} />)
    expect(screen.getByText('Tinha muitos problemas!')).toBeInTheDocument()
  })

  it('renders the joke category', () => {
    render(<JokeCard joke={mockJoke} onRate={vi.fn()} />)
    expect(screen.getByText('Português')).toBeInTheDocument()
  })

  it('renders Boa and Ruim buttons when not rated', () => {
    render(<JokeCard joke={mockJoke} onRate={vi.fn()} />)
    expect(screen.getByText('Boa! 👍')).toBeInTheDocument()
    expect(screen.getByText('Ruim! 👎')).toBeInTheDocument()
  })

  it('calls onRate with "good" when Boa button is clicked', async () => {
    const onRate = vi.fn()
    render(<JokeCard joke={mockJoke} onRate={onRate} />)
    await userEvent.click(screen.getByText('Boa! 👍'))
    expect(onRate).toHaveBeenCalledWith('good')
  })

  it('calls onRate with "bad" when Ruim button is clicked', async () => {
    const onRate = vi.fn()
    render(<JokeCard joke={mockJoke} onRate={onRate} />)
    await userEvent.click(screen.getByText('Ruim! 👎'))
    expect(onRate).toHaveBeenCalledWith('bad')
  })

  it('hides buttons and shows saved message when rated good', () => {
    render(<JokeCard joke={mockJoke} onRate={vi.fn()} rated="good" />)
    expect(screen.queryByText('Boa! 👍')).not.toBeInTheDocument()
    expect(screen.getByText('Piada salva! Buscando outra...')).toBeInTheDocument()
  })

  it('hides buttons and shows discarded message when rated bad', () => {
    render(<JokeCard joke={mockJoke} onRate={vi.fn()} rated="bad" />)
    expect(screen.queryByText('Ruim! 👎')).not.toBeInTheDocument()
    expect(screen.getByText('Descartada! Buscando outra...')).toBeInTheDocument()
  })
})
