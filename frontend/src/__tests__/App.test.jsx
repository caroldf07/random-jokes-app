import { render, screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { describe, it, expect, vi, beforeEach } from 'vitest'
import App from '../App'

const mockJoke = {
  id: 1,
  setup: 'Por que o livro estava triste?',
  punchline: 'Tinha muitos problemas!',
  category: 'Português',
}

const mockJoke2 = {
  id: 2,
  setup: 'O que o zero disse ao oito?',
  punchline: 'Que cinto bonito!',
  category: 'Português',
}

describe('App', () => {
  beforeEach(() => {
    vi.resetAllMocks()
  })

  it('renders the app title', () => {
    global.fetch = vi.fn().mockResolvedValue({
      ok: true,
      json: async () => mockJoke,
    })
    render(<App />)
    expect(screen.getByText('Piadas Aleatórias')).toBeInTheDocument()
  })

  it('renders the subtitle', () => {
    global.fetch = vi.fn().mockResolvedValue({
      ok: true,
      json: async () => mockJoke,
    })
    render(<App />)
    expect(screen.getByText('Clique no botão para ouvir uma piada!')).toBeInTheDocument()
  })

  it('loads and displays a random joke on mount', async () => {
    global.fetch = vi.fn().mockResolvedValue({
      ok: true,
      json: async () => mockJoke,
    })
    render(<App />)
    await waitFor(() => {
      expect(screen.getByText('Por que o livro estava triste?')).toBeInTheDocument()
    })
  })

  it('fetches a new joke when button is clicked', async () => {
    global.fetch = vi.fn()
      .mockResolvedValueOnce({ ok: true, json: async () => mockJoke })
      .mockResolvedValueOnce({ ok: true, json: async () => mockJoke2 })

    render(<App />)
    await waitFor(() => {
      expect(screen.getByText('Por que o livro estava triste?')).toBeInTheDocument()
    })

    const button = screen.getByText('Nova Piada!')
    await userEvent.click(button)

    await waitFor(() => {
      expect(screen.getByText('O que o zero disse ao oito?')).toBeInTheDocument()
    })
  })

  it('shows error message when fetch fails', async () => {
    global.fetch = vi.fn().mockResolvedValue({
      ok: false,
    })
    render(<App />)
    await waitFor(() => {
      expect(screen.getByText(/Erro:/)).toBeInTheDocument()
    })
  })

  it('renders the new joke button', async () => {
    global.fetch = vi.fn().mockResolvedValue({
      ok: true,
      json: async () => mockJoke,
    })
    render(<App />)
    await waitFor(() => {
      expect(screen.getByText('Nova Piada!')).toBeInTheDocument()
    })
  })
})
