package com.tw ter.product_m xer.core.p pel ne.step

 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.ExecutorResult
 mport com.tw ter.st ch.Arrow

/**
 * A Step w h n a P pel ne, a Step  s a un ary phase w h n an ent re cha n that makes up a p pel ne.
 * @tparam State T  request doma n model.
 * @tparam ExecutorConf g T  conf gs that should be passed  nto t  executor at bu ld t  .
 * @tparam Executor nput T   nput type that an executor takes at request t  .
 * @tparam ExResult T  result that a step's executor w ll return.
 * @tparam OutputState T  f nal/updated state a step would output, t   s typ cally tak ng t  ExResult
 *                     and mutat ng/transform ng t  State.
 */
tra  Step[State, -Conf g, Executor nput, ExResult <: ExecutorResult] {

  /**
   * Adapt t  state  nto t  expected  nput for t  Step's Arrow.
   *
   * @param state State object passed  nto t  Step.
   * @param conf g T  conf g object used to bu ld t  executor arrow or  nput.
   * @return Executor nput that  s used  n t  arrow of t  underly ng executor.
   */
  def adapt nput(state: State, conf g: Conf g): Executor nput

  /**
   * T  actual arrow to be executed for t  step, tak ng  n t  adapted  nput from [[adapt nput]]
   * and return ng t  expected result.
   * @param conf g Runt   conf gurat ons to conf gure t  arrow w h.
   * @param context Context of Executor.
   */
  def arrow(
    conf g: Conf g,
    context: Executor.Context
  ): Arrow[Executor nput, ExResult]

  /**
   * W t r t  step  s cons dered a noop/empty based off of  nput be ng passed  n. Empty
   * steps are sk pped w n be ng executed.
   */
  def  sEmpty(conf g: Conf g): Boolean

  /**
   * Update t  passed  n state based off of t  result from [[arrow]]
   * @param state State object passed  nto t  Step.
   * @param executorResult Executor result returned from [[arrow]]
   * @param conf g T  conf g object used to bu ld t  executor arrow or  nput.
   * @return Updated state object passed.
   */
  def updateState(state: State, executorResult: ExResult, conf g: Conf g): State
}
