package com.tw ter.t  l neranker.ut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.Level
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.search.earlyb rd.thr ftscala.Thr ftSearchResult
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.t  l nes.ut l.stats.RequestStats
 mport scala.collect on.mutable

object T etF ltersBasedOnSearch tadata extends Enu rat on {
  val Dupl cateRet ets: Value = Value
  val Dupl cateT ets: Value = Value

  val None: T etF ltersBasedOnSearch tadata.ValueSet = ValueSet.empty

  pr vate[ut l] type F lterBasedOnSearch tadata thod =
    (Thr ftSearchResult, T etsPostF lterBasedOnSearch tadataParams, MutableState) => Boolean

  case class MutableState(
    seenT et ds: mutable.Map[T et d,  nt] = mutable.Map.empty[T et d,  nt].w hDefaultValue(0)) {
    def  sSeen(t et d: T et d): Boolean = {
      val seen = seenT et ds(t et d) >= 1
       ncre nt f0(t et d)
      seen
    }

    def  ncre nt f0(key: T et d): Un  = {
       f (seenT et ds(key) == 0) {
        seenT et ds(key) = 1
      }
    }

    def  ncre ntT nGetCount(key: T et d):  nt = {
      seenT et ds(key) += 1
      seenT et ds(key)
    }
  }
}

case class T etsPostF lterBasedOnSearch tadataParams(
  user d: User d,
   nNetworkUser ds: Seq[User d],
  numRet etsAllo d:  nt,
  logg ngPref x: Str ng = "")

/**
 * Performs post-f lter ng on t ets obta ned from search us ng  tadata returned from search.
 *
 * Search currently does not perform certa n steps wh le search ng, so t  class addresses those
 * shortcom ngs by post-process ng search results us ng t  returned  tadata.
 */
class T etsPostF lterBasedOnSearch tadata(
  f lters: T etF ltersBasedOnSearch tadata.ValueSet,
  logger: Logger,
  statsRece ver: StatsRece ver)
    extends RequestStats {
   mport T etF ltersBasedOnSearch tadata.F lterBasedOnSearch tadata thod
   mport T etF ltersBasedOnSearch tadata.MutableState

  pr vate[t ] val baseScope = statsRece ver.scope("f lter_based_on_search_ tadata")
  pr vate[t ] val dupRet etCounter = baseScope.counter("dupRet et")
  pr vate[t ] val dupT etCounter = baseScope.counter("dupT et")

  pr vate[t ] val totalCounter = baseScope.counter(Total)
  pr vate[t ] val resultCounter = baseScope.counter("result")

  // Used for debugg ng.  s values should rema n false for prod use.
  pr vate[t ] val alwaysLog = false

  val appl cableF lters: Seq[F lterBasedOnSearch tadata thod] =
    F ltersBasedOnSearch tadata.getAppl cableF lters(f lters)

  def apply(
    user d: User d,
     nNetworkUser ds: Seq[User d],
    t ets: Seq[Thr ftSearchResult],
    numRet etsAllo d:  nt = 1
  ): Seq[Thr ftSearchResult] = {
    val logg ngPref x = s"user d: $user d"
    val params = T etsPostF lterBasedOnSearch tadataParams(
      user d = user d,
       nNetworkUser ds =  nNetworkUser ds,
      numRet etsAllo d = numRet etsAllo d,
      logg ngPref x = logg ngPref x,
    )
    f lter(t ets, params)
  }

  protected def f lter(
    t ets: Seq[Thr ftSearchResult],
    params: T etsPostF lterBasedOnSearch tadataParams
  ): Seq[Thr ftSearchResult] = {
    val  nvocat onState = MutableState()
    val result = t ets.reverse erator
      .f lterNot { t et => appl cableF lters.ex sts(_(t et, params,  nvocat onState)) }
      .toSeq
      .reverse
    totalCounter. ncr(t ets.s ze)
    resultCounter. ncr(result.s ze)
    result
  }

  object F ltersBasedOnSearch tadata {
    case class F lterData(
      k nd: T etF ltersBasedOnSearch tadata.Value,
       thod: F lterBasedOnSearch tadata thod)
    pr vate val allF lters = Seq[F lterData](
      F lterData(T etF ltersBasedOnSearch tadata.Dupl cateT ets,  sDupl cateT et),
      F lterData(T etF ltersBasedOnSearch tadata.Dupl cateRet ets,  sDupl cateRet et)
    )

    def getAppl cableF lters(
      f lters: T etF ltersBasedOnSearch tadata.ValueSet
    ): Seq[F lterBasedOnSearch tadata thod] = {
      requ re(allF lters.map(_.k nd).toSet == T etF ltersBasedOnSearch tadata.values)
      allF lters.f lter(data => f lters.conta ns(data.k nd)).map(_. thod)
    }

    /**
     * Determ nes w t r t  g ven t et has already been seen.
     */
    pr vate def  sDupl cateT et(
      t et: Thr ftSearchResult,
      params: T etsPostF lterBasedOnSearch tadataParams,
       nvocat onState: MutableState
    ): Boolean = {
      val shouldF lterOut =  nvocat onState. sSeen(t et. d)
       f (shouldF lterOut) {
        dupT etCounter. ncr()
        log(Level.ERROR, () => s"${params.logg ngPref x}:: Dupl cate t et found: ${t et. d}")
      }
      shouldF lterOut
    }

    /**
     *  f t  g ven t et  s a ret et, determ nes w t r t  s ce t et
     * of that ret et has already been seen.
     */
    pr vate def  sDupl cateRet et(
      t et: Thr ftSearchResult,
      params: T etsPostF lterBasedOnSearch tadataParams,
       nvocat onState: MutableState
    ): Boolean = {
       nvocat onState. ncre nt f0(t et. d)
      SearchResultUt l.getRet etS ceT et d(t et).ex sts { s ceT et d =>
        val seenCount =  nvocat onState. ncre ntT nGetCount(s ceT et d)
        val shouldF lterOut = seenCount > params.numRet etsAllo d
         f (shouldF lterOut) {
          //   do not log  re because search  s known to not handle t  case.
          dupRet etCounter. ncr()
          log(
            Level.OFF,
            () =>
              s"${params.logg ngPref x}:: Found dup ret et: ${t et. d} (s ce t et: $s ceT et d), count: $seenCount"
          )
        }
        shouldF lterOut
      }
    }

    pr vate def log(level: Level,  ssage: () => Str ng): Un  = {
       f (alwaysLog || ((level != Level.OFF) && logger. sLoggable(level))) {
        val updatedLevel =  f (alwaysLog) Level. NFO else level
        logger.log(updatedLevel,  ssage())
      }
    }
  }
}
