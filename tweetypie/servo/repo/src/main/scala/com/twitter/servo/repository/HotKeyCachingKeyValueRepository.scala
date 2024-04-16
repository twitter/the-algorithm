package com.tw ter.servo.repos ory

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.servo.cac .{ nProcessCac , StatsRece verCac Observer}
 mport com.tw ter.servo.ut l.FrequencyCounter
 mport com.tw ter.ut l.Future

/**
 * A KeyValueRepos ory wh ch uses a sl d ng w ndow to track
 * t  frequency at wh ch keys are requested and d verts requests
 * for keys above t  promot onThreshold through an  n- mory request cac .
 *
 * @param underly ngRepo
 *   t  underly ng KeyValueRepos ory
 * @param newQuery
 *   a funct on for convert ng a subset of t  keys of t  or g nal query  nto a new query.
 * @param w ndowS ze
 *   t  number of prev ous requests to  nclude  n t  w ndow
 * @param promot onThreshold
 *   t  number of requests for t  sa  key  n t  w ndow requ red
 *   to d vert t  request through t  request cac 
 * @param cac Factory
 *   a funct on wh ch constructs a future response cac  of t  g ven s ze
 * @param statsRece ver
 *   records stats on t  cac 
 * @param d sableLogg ng
 *   d sables logg ng  n token cac  for pdp purposes
 */
object HotKeyCach ngKeyValueRepos ory {
  def apply[Q <: Seq[K], K, V](
    underly ngRepo: KeyValueRepos ory[Q, K, V],
    newQuery: SubqueryBu lder[Q, K],
    w ndowS ze:  nt,
    promot onThreshold:  nt,
    cac Factory:  nt =>  nProcessCac [K, Future[Opt on[V]]],
    statsRece ver: StatsRece ver,
    d sableLogg ng: Boolean = false
  ): KeyValueRepos ory[Q, K, V] = {
    val log = Logger.get(getClass.getS mpleNa )

    val promot onsCounter = statsRece ver.counter("promot ons")

    val onPromot on = { (k: K) =>
      log.debug("key %s promoted to HotKeyCac ", k.toStr ng)
      promot onsCounter. ncr()
    }

    val frequencyCounter = new FrequencyCounter[K](w ndowS ze, promot onThreshold, onPromot on)

    // Max mum cac  s ze occurs  n t  event that every key  n t  buffer occurs
    // `promot onThreshold` t  s.   apply a fa lure-refresh ng f lter to avo d
    // cach ng fa led responses.
    val cac  =
       nProcessCac .w hF lter(
        cac Factory(w ndowS ze / promot onThreshold)
      )(
        ResponseCach ngKeyValueRepos ory.refreshFa lures
      )

    val observer =
      new StatsRece verCac Observer(statsRece ver, w ndowS ze, "request_cac ", d sableLogg ng)

    val cach ngRepo =
      new ResponseCach ngKeyValueRepos ory[Q, K, V](underly ngRepo, cac , newQuery, observer)

    KeyValueRepos ory.selected(
      frequencyCounter. ncr,
      cach ngRepo,
      underly ngRepo,
      newQuery
    )
  }
}
