package com.tw ter.t  l neranker.para ters.recap

 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.t  l nes.ut l.bounds.BoundsW hDefault

object RecapQueryContext {
  val MaxFollo dUsers: BoundsW hDefault[ nt] = BoundsW hDefault[ nt](1, 3000, 1000)
  val MaxCountMult pl er: BoundsW hDefault[Double] = BoundsW hDefault[Double](0.1, 2.0, 2.0)
  val MaxRealGraphAndFollo dUsers: BoundsW hDefault[ nt] = BoundsW hDefault[ nt](0, 2000, 1000)

  def getDefaultContext(query: RecapQuery): RecapQueryContext = {
    new RecapQueryContext mpl(
      query,
      getEnableHydrat onUs ngT etyP e = () => false,
      getMaxFollo dUsers = () => MaxFollo dUsers.default,
      getMaxCountMult pl er = () => MaxCountMult pl er.default,
      getEnableRealGraphUsers = () => false,
      getOnlyRealGraphUsers = () => false,
      getMaxRealGraphAndFollo dUsers = () => MaxRealGraphAndFollo dUsers.default,
      getEnableTextFeatureHydrat on = () => false
    )
  }
}

// Note that  thods that return para ter value always use () to  nd cate that
// s de effects may be  nvolved  n t  r  nvocat on.
tra  RecapQueryContext {
  def query: RecapQuery

  //  f true, t et hydrat on are perfor d by call ng T etyP e.
  // Ot rw se, t ets are part ally hydrated based on  nformat on  n Thr ftSearchResult.
  def enableHydrat onUs ngT etyP e(): Boolean

  // Max mum number of follo d user accounts to use w n fetch ng recap t ets.
  def maxFollo dUsers():  nt

  //   mult ply maxCount (caller suppl ed value) by t  mult pl er and fetch those many
  // cand dates from search so that   are left w h suff c ent number of cand dates after
  // hydrat on and f lter ng.
  def maxCountMult pl er(): Double

  // Only used  f user follows >= 1000.
  //  f true, fetc s recap/recycled t ets us ng author seedset m x ng w h real graph users and follo d users.
  // Ot rw se, fetc s recap/recycled t ets only us ng follo d users
  def enableRealGraphUsers(): Boolean

  // Only used  f enableRealGraphUsers  s true.
  //  f true, user seedset only conta ns real graph users.
  // Ot rw se, user seedset conta ns real graph users and recent follo d users.
  def onlyRealGraphUsers(): Boolean

  // Only used  f enableRealGraphUsers  s true and onlyRealGraphUsers  s false.
  // Max mum number of real graph users and recent follo d users w n m x ng recent/real-graph users.
  def maxRealGraphAndFollo dUsers():  nt

  //  f true, text features are hydrated for pred ct on.
  // Ot rw se those feature values are not set at all.
  def enableTextFeatureHydrat on(): Boolean
}

class RecapQueryContext mpl(
  overr de val query: RecapQuery,
  getEnableHydrat onUs ngT etyP e: () => Boolean,
  getMaxFollo dUsers: () =>  nt,
  getMaxCountMult pl er: () => Double,
  getEnableRealGraphUsers: () => Boolean,
  getOnlyRealGraphUsers: () => Boolean,
  getMaxRealGraphAndFollo dUsers: () =>  nt,
  getEnableTextFeatureHydrat on: () => Boolean)
    extends RecapQueryContext {

  overr de def enableHydrat onUs ngT etyP e(): Boolean = { getEnableHydrat onUs ngT etyP e() }
  overr de def maxFollo dUsers():  nt = { getMaxFollo dUsers() }
  overr de def maxCountMult pl er(): Double = { getMaxCountMult pl er() }
  overr de def enableRealGraphUsers(): Boolean = { getEnableRealGraphUsers() }
  overr de def onlyRealGraphUsers(): Boolean = { getOnlyRealGraphUsers() }
  overr de def maxRealGraphAndFollo dUsers():  nt = { getMaxRealGraphAndFollo dUsers() }
  overr de def enableTextFeatureHydrat on(): Boolean = { getEnableTextFeatureHydrat on() }
}
