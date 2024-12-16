import { FastifyInstance } from 'fastify'
import { activateFailure } from '../utils/activateFailure'
import { IStateFailure } from '../utils/state'

interface IQuerystring {
  idUser: string
  bonus: number
}

export async function bonus(app: FastifyInstance, state: IStateFailure) {
  app.post<{ Querystring: IQuerystring }>('/bonus', async (request, reply) => {
    await activateFailure(state)

    const { idUser, bonus } = request.query

    if (!idUser || !bonus) {
      return reply.status(400).send()
    }

    return reply.status(200).send()
  })
}
