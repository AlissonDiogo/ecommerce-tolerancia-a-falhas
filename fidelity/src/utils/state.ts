export interface IStateFailure {
  isFailureActive: boolean
}

const state: IStateFailure = {
  isFailureActive: false,
}

export const getState = () => state

export const setState = (value: boolean) => {
  state.isFailureActive = value
}
