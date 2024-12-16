import fastify from 'fastify'
import { bonus } from './bonus'
import { getState } from '../utils/state'

const app = fastify()

const state = getState()

app.register(() => bonus(app, state))

app.listen({ port: 8082, host: '0.0.0.0' }).then(() => {
  console.log('Fidelity server is running!')
})
