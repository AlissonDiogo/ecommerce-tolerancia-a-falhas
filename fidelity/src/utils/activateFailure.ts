import { checkIfFail } from './checkIfFail'
import { delay } from './delay'
import { setState } from './state'

interface IStateFailure {
  isFailureActive: boolean
}

const TIMEOUT_DELAY = 2000
const TIMEOUT_FAILURE = 30000

export const activateFailure = async (state: IStateFailure) => {
  if (state.isFailureActive) {
    await delay(TIMEOUT_DELAY)
  } else {
    const shouldActivateFailure = checkIfFail()

    if (shouldActivateFailure) {
      setState(true)

      setTimeout(() => {
        setState(false)
      }, TIMEOUT_FAILURE)

      await delay(TIMEOUT_DELAY)
    }
  }
}
