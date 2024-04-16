package com.tw ter.t  l neranker.para ters.revchron

 mport com.tw ter.t  l neranker.model.ReverseChronT  l neQuery
 mport com.tw ter.t  l nes.ut l.bounds.BoundsW hDefault
 mport com.tw ter.t  l neserv ce.model.core.T  l neK nd
 mport com.tw ter.t  l neserv ce.model.core.T  l neL m s

object ReverseChronT  l neQueryContext {
  val MaxCountL m :  nt = T  l neL m s.default.lengthL m (T  l neK nd.ho )
  val MaxCount: BoundsW hDefault[ nt] = BoundsW hDefault[ nt](0, MaxCountL m , MaxCountL m )
  val MaxCountMult pl er: BoundsW hDefault[Double] = BoundsW hDefault[Double](0.5, 2.0, 1.0)
  val MaxFollo dUsers: BoundsW hDefault[ nt] = BoundsW hDefault[ nt](1, 15000, 5000)
  val T etsF lter ngLossageThresholdPercent: BoundsW hDefault[ nt] =
    BoundsW hDefault[ nt](10, 100, 20)
  val T etsF lter ngLossageL m Percent: BoundsW hDefault[ nt] =
    BoundsW hDefault[ nt](40, 65, 60)

  def getDefaultContext(query: ReverseChronT  l neQuery): ReverseChronT  l neQueryContext = {
    new ReverseChronT  l neQueryContext mpl(
      query,
      getMaxCount = () => MaxCount.default,
      getMaxCountMult pl er = () => MaxCountMult pl er.default,
      getMaxFollo dUsers = () => MaxFollo dUsers.default,
      getReturnEmptyW nOverMaxFollows = () => true,
      getD rectedAtNarrowast ngV aSearch = () => false,
      getPostF lter ngBasedOnSearch tadataEnabled = () => true,
      getBackf llF lteredEntr es = () => false,
      getT etsF lter ngLossageThresholdPercent = () =>
        T etsF lter ngLossageThresholdPercent.default,
      getT etsF lter ngLossageL m Percent = () => T etsF lter ngLossageL m Percent.default
    )
  }
}

// Note that  thods that return para ter value always use () to  nd cate that
// s de effects may be  nvolved  n t  r  nvocat on.
// for example, A l kely s de effect  s to cause exper  nt  mpress on.
tra  ReverseChronT  l neQueryContext {
  def query: ReverseChronT  l neQuery

  // Max mum number of t ets to be returned to caller.
  def maxCount():  nt

  // Mult pl er appl ed to t  number of t ets fetc d from search expressed as percentage.
  //   can be used to fetch more than t  number t ets requested by a caller (to  mprove s m lar y)
  // or to fetch less than requested to reduce load.
  def maxCountMult pl er(): Double

  // Max mum number of follo d user accounts to use w n mater al z ng ho  t  l nes.
  def maxFollo dUsers():  nt

  // W n true,  f t  user follows more than maxFollo dUsers, return an empty t  l ne.
  def returnEmptyW nOverMaxFollows(): Boolean

  // W n true, appends an operator for d rected-at narrowcast ng to t  ho  mater al zat on
  // search request
  def d rectedAtNarrowcast ngV aSearch(): Boolean

  // W n true, requests add  onal  tadata from search and use t   tadata for post f lter ng.
  def postF lter ngBasedOnSearch tadataEnabled(): Boolean

  // Controls w t r to back-f ll t  l ne entr es that get f ltered out by T etsPostF lter
  // dur ng ho  t  l ne mater al zat on.
  def backf llF lteredEntr es(): Boolean

  //  f back-f ll ng f ltered entr es  s enabled and  f number of t ets that get f ltered out
  // exceed t  percentage t n   w ll  ssue a second call to get more t ets.
  def t etsF lter ngLossageThresholdPercent():  nt

  //   need to ensure that t  number of t ets requested by t  second call
  // are not unbounded (for example,  f everyth ng  s f ltered out  n t  f rst call)
  // t refore   adjust t  actual f ltered out percentage to be no greater than
  // t  value below.
  def t etsF lter ngLossageL m Percent():  nt

  //   need to  nd cate to search  f   should use t  arch ve cluster
  // t  opt on w ll co  from ReverseChronT  l neQueryOpt ons and
  // w ll be `true` by default  f t  opt ons are not present.
  def getT etsFromArch ve ndex(): Boolean =
    query.opt ons.map(_.getT etsFromArch ve ndex).getOrElse(true)
}

class ReverseChronT  l neQueryContext mpl(
  overr de val query: ReverseChronT  l neQuery,
  getMaxCount: () =>  nt,
  getMaxCountMult pl er: () => Double,
  getMaxFollo dUsers: () =>  nt,
  getReturnEmptyW nOverMaxFollows: () => Boolean,
  getD rectedAtNarrowast ngV aSearch: () => Boolean,
  getPostF lter ngBasedOnSearch tadataEnabled: () => Boolean,
  getBackf llF lteredEntr es: () => Boolean,
  getT etsF lter ngLossageThresholdPercent: () =>  nt,
  getT etsF lter ngLossageL m Percent: () =>  nt)
    extends ReverseChronT  l neQueryContext {
  overr de def maxCount():  nt = { getMaxCount() }
  overr de def maxCountMult pl er(): Double = { getMaxCountMult pl er() }
  overr de def maxFollo dUsers():  nt = { getMaxFollo dUsers() }
  overr de def backf llF lteredEntr es(): Boolean = { getBackf llF lteredEntr es() }
  overr de def t etsF lter ngLossageThresholdPercent():  nt = {
    getT etsF lter ngLossageThresholdPercent()
  }
  overr de def t etsF lter ngLossageL m Percent():  nt = {
    getT etsF lter ngLossageL m Percent()
  }
  overr de def returnEmptyW nOverMaxFollows(): Boolean = {
    getReturnEmptyW nOverMaxFollows()
  }
  overr de def d rectedAtNarrowcast ngV aSearch(): Boolean = {
    getD rectedAtNarrowast ngV aSearch()
  }
  overr de def postF lter ngBasedOnSearch tadataEnabled(): Boolean = {
    getPostF lter ngBasedOnSearch tadataEnabled()
  }
}
