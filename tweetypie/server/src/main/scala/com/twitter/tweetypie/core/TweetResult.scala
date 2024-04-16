package com.tw ter.t etyp e.core

 mport com.tw ter.servo.data.Lens
 mport com.tw ter.t etyp e.Mutat on
 mport com.tw ter.t etyp e.thr ftscala.T et

/**
 *  lper class for bu ld ng  nstances of `T etResult`, wh ch  s a type al as
 * for `ValueState[T etData]`.
 */
object T etResult {
  object Lenses {
    val value: Lens[T etResult, T etData] =
      Lens[T etResult, T etData](_.value, (r, value) => r.copy(value = value))
    val state: Lens[T etResult, Hydrat onState] =
      Lens[T etResult, Hydrat onState](_.state, (r, state) => r.copy(state = state))
    val t et: Lens[T etResult, T et] = value.andT n(T etData.Lenses.t et)
  }

  def apply(value: T etData, state: Hydrat onState = Hydrat onState.empty): T etResult =
    ValueState(value, state)

  def apply(t et: T et): T etResult =
    apply(T etData(t et = t et))

  /**
   * Apply t  mutat on to t  t et conta ned  n t  result, updat ng t  mod f ed flag  f t  mutat on mod f es t  t et.
   */
  def mutate(mutat on: Mutat on[T et]): T etResult => T etResult =
    (result: T etResult) =>
      mutat on(result.value.t et) match {
        case None => result
        case So (updatedT et) =>
          T etResult(
            result.value.copy(t et = updatedT et),
            result.state ++ Hydrat onState.mod f ed
          )
      }
}
