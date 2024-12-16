const FAIL_PROBABILITY = 0.1

export const checkIfFail = () => {
  return Math.random() <= FAIL_PROBABILITY
}
