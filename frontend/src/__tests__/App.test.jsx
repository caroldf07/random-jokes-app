import { render, screen, waitFor, act } from '@testing-library/react'
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
    global.fetch = vi.fn().mockResolvedValue({ ok: true, json: async () => mockJoke })
    render(<App />)
    expect(screen.getByText('Piadas Aleatórias')).toBeInTheDocument()
  })

  it('loads and displays a random joke on mount', async () => {
    global.fetch = vi.fn().mockResolvedValue({ ok: true, json: async () => mockJoke })
    render(<App />)
    await waitFor(() => {
      expect(screen.getByText('Por que o livro estava triste?')).toBeInTheDocument()
    })
  })

  it('shows error message when fetch fails', async () => {
    global.fetch = vi.fn().mockResolvedValue({ ok: false })
    render(<App />)
    await waitFor(() => {
      expect(screen.getByText(/Erro:/)).toBeInTheDocument()
    })
  })

  it('fetches new joke when Pular button is clicked', async () => {
    global.fetch = vi.fn()
      .mockResolvedValueOnce({ ok: true, json: async () => mockJoke })
      .mockResolvedValueOnce({ ok: true, json: async () => mockJoke2 })

    render(<App />)
    await waitFor(() => screen.getByText('Por que o livro estava triste?'))

    await userEvent.click(screen.getByText('Pular piada'))
    await waitFor(() => {
      expect(screen.getByText('O que o zero disse ao oito?')).toBeInTheDocument()
    })
  })

  it('saves joke via POST when Boa button is clicked', async () => {
    global.fetch = vi.fn()
      .mockResolvedValueOnce({ ok: true, json: async () => mockJoke })
      .mockResolvedValueOnce({ ok: true })
      .mockResolvedValueOnce({ ok: true, json: async () => mockJoke2 })

    render(<App />)
    await waitFor(() => screen.getByText('Boa! 👍'))

    await userEvent.click(screen.getByText('Boa! 👍'))

    expect(global.fetch).toHaveBeenCalledWith(
      'http://localhost:8080/api/jokes',
      expect.objectContaining({ method: 'POST' })
    )
  })

  it('does not save joke when Ruim button is clicked', async () => {
    global.fetch = vi.fn()
      .mockResolvedValueOnce({ ok: true, json: async () => mockJoke })
      .mockResolvedValueOnce({ ok: true, json: async () => mockJoke2 })

    render(<App />)
    await waitFor(() => screen.getByText('Ruim! 👎'))

    await userEvent.click(screen.getByText('Ruim! 👎'))

    const postCalls = global.fetch.mock.calls.filter(
      ([, opts]) => opts?.method === 'POST'
    )
    expect(postCalls).toHaveLength(0)
  })

  it('shows rated feedback after clicking Boa', async () => {
    global.fetch = vi.fn()
      .mockResolvedValueOnce({ ok: true, json: async () => mockJoke })
      .mockResolvedValueOnce({ ok: true })
      .mockResolvedValueOnce({ ok: true, json: async () => mockJoke2 })

    render(<App />)
    await waitFor(() => screen.getByText('Boa! 👍'))

    await userEvent.click(screen.getByText('Boa! 👍'))

    expect(screen.getByText('Piada salva! Buscando outra...')).toBeInTheDocument()
  })
})
