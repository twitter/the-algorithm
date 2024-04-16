package com.tw ter.t  l neranker.ut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.Level
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.t  l nes.model.t et.HydratedT et
 mport com.tw ter.t  l nes.ut l.stats.BooleanObserver
 mport com.tw ter.t  l nes.ut l.stats.RequestStats
 mport scala.collect on.mutable

object T etF lters extends Enu rat on {
  // F lters  ndependent of users or t  r follow graph.
  val Dupl cateRet ets: Value = Value
  val Dupl cateT ets: Value = Value
  val NullcastT ets: Value = Value
  val Repl es: Value = Value
  val Ret ets: Value = Value

  // F lters that depend on users or t  r follow graph.
  val D rectedAtNotFollo dUsers: Value = Value
  val NonReplyD rectedAtNotFollo dUsers: Value = Value
  val T etsFromNotFollo dUsers: Value = Value
  val ExtendedRepl es: Value = Value
  val NotQual f edExtendedRepl es: Value = Value
  val NotVal dExpandedExtendedRepl es: Value = Value
  val NotQual f edReverseExtendedRepl es: Value = Value
  val Recom ndedRepl esToNotFollo dUsers: Value = Value

  val None: T etF lters.ValueSet = ValueSet.empty

  val UserDependent: ValueSet = ValueSet(
    NonReplyD rectedAtNotFollo dUsers,
    D rectedAtNotFollo dUsers,
    T etsFromNotFollo dUsers,
    ExtendedRepl es,
    NotQual f edExtendedRepl es,
    NotVal dExpandedExtendedRepl es,
    NotQual f edReverseExtendedRepl es,
    Recom ndedRepl esToNotFollo dUsers
  )

  val User ndependent: ValueSet = ValueSet(
    Dupl cateRet ets,
    Dupl cateT ets,
    NullcastT ets,
    Repl es,
    Ret ets
  )
  requ re(
    (UserDependent ++ User ndependent) == T etF lters.values,
    "User ndependent and UserDependent should conta n all poss ble f lters"
  )

  pr vate[ut l] type F lter thod =
    (HydratedT et, T etsPostF lterParams, MutableState) => Boolean

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

case class T etsPostF lterParams(
  user d: User d,
  follo dUser ds: Seq[User d],
   nNetworkUser ds: Seq[User d],
  mutedUser ds: Set[User d],
  numRet etsAllo d:  nt,
  logg ngPref x: Str ng = "",
  s ceT ets: Seq[HydratedT et] = N l) {
  lazy val s ceT etsBy d: Map[T et d, HydratedT et] =
    s ceT ets.map(t et => t et.t et d -> t et).toMap
}

/**
 * Performs post-f lter ng on t ets obta ned from search.
 *
 * Search currently does not perform certa n steps or performs t m  nadequately.
 * T  class addresses those shortcom ngs by post-process ng hydrated search results.
 */
abstract class T etsPostF lterBase(
  f lters: T etF lters.ValueSet,
  logger: Logger,
  statsRece ver: StatsRece ver)
    extends RequestStats {
   mport T etF lters.F lter thod
   mport T etF lters.MutableState

  pr vate[t ] val baseScope = statsRece ver.scope("f lter")
  pr vate[t ] val d rectedAtNotFollo dCounter = baseScope.counter("d rectedAtNotFollo d")
  pr vate[t ] val nonReplyD rectedAtNotFollo dObserver =
    BooleanObserver(baseScope.scope("nonReplyD rectedAtNotFollo d"))
  pr vate[t ] val dupRet etCounter = baseScope.counter("dupRet et")
  pr vate[t ] val dupT etCounter = baseScope.counter("dupT et")
  pr vate[t ] val notFollo dCounter = baseScope.counter("notFollo d")
  pr vate[t ] val nullcastCounter = baseScope.counter("nullcast")
  pr vate[t ] val repl esCounter = baseScope.counter("repl es")
  pr vate[t ] val ret etsCounter = baseScope.counter("ret ets")
  pr vate[t ] val extendedRepl esCounter = baseScope.counter("extendedRepl es")
  pr vate[t ] val notQual f edExtendedRepl esObserver =
    BooleanObserver(baseScope.scope("notQual f edExtendedRepl es"))
  pr vate[t ] val notVal dExpandedExtendedRepl esObserver =
    BooleanObserver(baseScope.scope("notVal dExpandedExtendedRepl es"))
  pr vate[t ] val notQual f edReverseExtendedRepl esCounter =
    baseScope.counter("notQual f edReverseExtendedRepl es")
  pr vate[t ] val recom ndedRepl esToNotFollo dUsersObserver =
    BooleanObserver(baseScope.scope("recom ndedRepl esToNotFollo dUsers"))

  pr vate[t ] val totalCounter = baseScope.counter(Total)
  pr vate[t ] val resultCounter = baseScope.counter("result")

  // Used for debugg ng.  s values should rema n false for prod use.
  pr vate[t ] val alwaysLog = false

  val appl cableF lters: Seq[F lter thod] = F lters.getAppl cableF lters(f lters)

  protected def f lter(
    t ets: Seq[HydratedT et],
    params: T etsPostF lterParams
  ): Seq[HydratedT et] = {
    val  nvocat onState = MutableState()
    val result = t ets.reverse erator
      .f lterNot { t et => appl cableF lters.ex sts(_(t et, params,  nvocat onState)) }
      .toSeq
      .reverse
    totalCounter. ncr(t ets.s ze)
    resultCounter. ncr(result.s ze)
    result
  }

  object F lters {
    case class F lterData(k nd: T etF lters.Value,  thod: F lter thod)
    pr vate val allF lters = Seq[F lterData](
      F lterData(T etF lters.Dupl cateT ets,  sDupl cateT et),
      F lterData(T etF lters.Dupl cateRet ets,  sDupl cateRet et),
      F lterData(T etF lters.D rectedAtNotFollo dUsers,  sD rectedAtNonFollo dUser),
      F lterData(
        T etF lters.NonReplyD rectedAtNotFollo dUsers,
         sNonReplyD rectedAtNonFollo dUser
      ),
      F lterData(T etF lters.NullcastT ets,  sNullcast),
      F lterData(T etF lters.Repl es,  sReply),
      F lterData(T etF lters.Ret ets,  sRet et),
      F lterData(T etF lters.T etsFromNotFollo dUsers,  sFromNonFollo dUser),
      F lterData(T etF lters.ExtendedRepl es,  sExtendedReply),
      F lterData(T etF lters.NotQual f edExtendedRepl es,  sNotQual f edExtendedReply),
      F lterData(T etF lters.NotVal dExpandedExtendedRepl es,  sNotVal dExpandedExtendedReply),
      F lterData(
        T etF lters.NotQual f edReverseExtendedRepl es,
         sNotQual f edReverseExtendedReply),
      F lterData(
        T etF lters.Recom ndedRepl esToNotFollo dUsers,
         sRecom ndedRepl esToNotFollo dUsers)
    )

    def getAppl cableF lters(f lters: T etF lters.ValueSet): Seq[F lter thod] = {
      requ re(allF lters.map(_.k nd).toSet == T etF lters.values)
      allF lters.f lter(data => f lters.conta ns(data.k nd)).map(_. thod)
    }

    pr vate def  sNullcast(
      t et: HydratedT et,
      params: T etsPostF lterParams,
       nvocat onState: MutableState
    ): Boolean = {
       f (t et. sNullcast) {
        nullcastCounter. ncr()
        log(
          Level.ERROR,
          () => s"${params.logg ngPref x}:: Found nullcast t et: t et- d: ${t et.t et d}"
        )
        true
      } else {
        false
      }
    }

    pr vate def  sReply(
      t et: HydratedT et,
      params: T etsPostF lterParams,
       nvocat onState: MutableState
    ): Boolean = {
       f (t et.hasReply) {
        repl esCounter. ncr()
        log(Level.OFF, () => s"${params.logg ngPref x}:: Removed reply: t et- d: ${t et.t et d}")
        true
      } else {
        false
      }
    }

    pr vate def  sRet et(
      t et: HydratedT et,
      params: T etsPostF lterParams,
       nvocat onState: MutableState
    ): Boolean = {
       f (t et. sRet et) {
        ret etsCounter. ncr()
        log(
          Level.OFF,
          () => s"${params.logg ngPref x}:: Removed ret et: t et- d: ${t et.t et d}"
        )
        true
      } else {
        false
      }
    }

    pr vate def  sFromNonFollo dUser(
      t et: HydratedT et,
      params: T etsPostF lterParams,
       nvocat onState: MutableState
    ): Boolean = {
       f ((t et.user d != params.user d) && !params. nNetworkUser ds.conta ns(t et.user d)) {
        notFollo dCounter. ncr()
        log(
          Level.ERROR,
          () =>
            s"${params.logg ngPref x}:: Found t et from not-follo d user: ${t et.t et d} from ${t et.user d}"
        )
        true
      } else {
        false
      }
    }

    pr vate def  sD rectedAtNonFollo dUser(
      t et: HydratedT et,
      params: T etsPostF lterParams,
       nvocat onState: MutableState
    ): Boolean = {
      t et.d rectedAtUser.ex sts { d rectedAtUser d =>
        val shouldF lterOut = (t et.user d != params.user d) && !params. nNetworkUser ds
          .conta ns(d rectedAtUser d)
        //   do not log  re because search  s known to not handle t  case.
         f (shouldF lterOut) {
          log(
            Level.OFF,
            () =>
              s"${params.logg ngPref x}:: Found t et: ${t et.t et d} d rected-at not-follo d user: $d rectedAtUser d"
          )
          d rectedAtNotFollo dCounter. ncr()
        }
        shouldF lterOut
      }
    }

    pr vate def  sNonReplyD rectedAtNonFollo dUser(
      t et: HydratedT et,
      params: T etsPostF lterParams,
       nvocat onState: MutableState
    ): Boolean = {
      t et.d rectedAtUser.ex sts { d rectedAtUser d =>
        val shouldF lterOut = !t et.hasReply &&
          (t et.user d != params.user d) &&
          !params. nNetworkUser ds.conta ns(d rectedAtUser d)
        //   do not log  re because search  s known to not handle t  case.
         f (nonReplyD rectedAtNotFollo dObserver(shouldF lterOut)) {
          log(
            Level.OFF,
            () =>
              s"${params.logg ngPref x}:: Found non-reply t et: ${t et.t et d} d rected-at not-follo d user: $d rectedAtUser d"
          )
        }
        shouldF lterOut
      }
    }

    /**
     * Determ nes w t r t  g ven t et has already been seen.
     */
    pr vate def  sDupl cateT et(
      t et: HydratedT et,
      params: T etsPostF lterParams,
       nvocat onState: MutableState
    ): Boolean = {
      val shouldF lterOut =  nvocat onState. sSeen(t et.t et d)
       f (shouldF lterOut) {
        dupT etCounter. ncr()
        log(Level.ERROR, () => s"${params.logg ngPref x}:: Dupl cate t et found: ${t et.t et d}")
      }
      shouldF lterOut
    }

    /**
     *  f t  g ven t et  s a ret et, determ nes w t r t  s ce t et
     * of that ret et has already been seen.
     */
    pr vate def  sDupl cateRet et(
      t et: HydratedT et,
      params: T etsPostF lterParams,
       nvocat onState: MutableState
    ): Boolean = {
       nvocat onState. ncre nt f0(t et.t et d)
      t et.s ceT et d.ex sts { s ceT et d =>
        val seenCount =  nvocat onState. ncre ntT nGetCount(s ceT et d)
        val shouldF lterOut = seenCount > params.numRet etsAllo d
         f (shouldF lterOut) {
          //   do not log  re because search  s known to not handle t  case.
          dupRet etCounter. ncr()
          log(
            Level.OFF,
            () =>
              s"${params.logg ngPref x}:: Found dup ret et: ${t et.t et d} (s ce t et: $s ceT et d), count: $seenCount"
          )
        }
        shouldF lterOut
      }
    }

    pr vate def  sExtendedReply(
      t et: HydratedT et,
      params: T etsPostF lterParams,
       nvocat onState: MutableState
    ): Boolean = {
      val shouldF lterOut = ExtendedRepl esF lter. sExtendedReply(
        t et,
        params.follo dUser ds
      )
       f (shouldF lterOut) {
        extendedRepl esCounter. ncr()
        log(
          Level.DEBUG,
          () => s"${params.logg ngPref x}:: extended reply to be f ltered: ${t et.t et d}"
        )
      }
      shouldF lterOut
    }

    pr vate def  sNotQual f edExtendedReply(
      t et: HydratedT et,
      params: T etsPostF lterParams,
       nvocat onState: MutableState
    ): Boolean = {
      val shouldF lterOut = ExtendedRepl esF lter. sNotQual f edExtendedReply(
        t et,
        params.user d,
        params.follo dUser ds,
        params.mutedUser ds,
        params.s ceT etsBy d
      )
       f (notQual f edExtendedRepl esObserver(shouldF lterOut)) {
        log(
          Level.DEBUG,
          () =>
            s"${params.logg ngPref x}:: non qual f ed extended reply to be f ltered: ${t et.t et d}"
        )
      }
      shouldF lterOut
    }

    pr vate def  sNotVal dExpandedExtendedReply(
      t et: HydratedT et,
      params: T etsPostF lterParams,
       nvocat onState: MutableState
    ): Boolean = {
      val shouldF lterOut = ExtendedRepl esF lter. sNotVal dExpandedExtendedReply(
        t et,
        params.user d,
        params.follo dUser ds,
        params.mutedUser ds,
        params.s ceT etsBy d
      )
       f (notVal dExpandedExtendedRepl esObserver(shouldF lterOut)) {
        log(
          Level.DEBUG,
          () =>
            s"${params.logg ngPref x}:: non qual f ed extended reply to be f ltered: ${t et.t et d}"
        )
      }
      shouldF lterOut
    }

    pr vate def  sRecom ndedRepl esToNotFollo dUsers(
      t et: HydratedT et,
      params: T etsPostF lterParams,
       nvocat onState: MutableState
    ): Boolean = {
      val shouldF lterOut = Recom ndedRepl esF lter. sRecom ndedReplyToNotFollo dUser(
        t et,
        params.user d,
        params.follo dUser ds,
        params.mutedUser ds
      )
       f (recom ndedRepl esToNotFollo dUsersObserver(shouldF lterOut)) {
        log(
          Level.DEBUG,
          () =>
            s"${params.logg ngPref x}:: non qual f ed recom nded reply to be f ltered: ${t et.t et d}"
        )
      }
      shouldF lterOut
    }

    //For now t  f lter  s  ant to be used only w h reply t ets from t   nReplyToUser d query
    pr vate def  sNotQual f edReverseExtendedReply(
      t et: HydratedT et,
      params: T etsPostF lterParams,
       nvocat onState: MutableState
    ): Boolean = {
      val shouldF lterOut = !ReverseExtendedRepl esF lter. sQual f edReverseExtendedReply(
        t et,
        params.user d,
        params.follo dUser ds,
        params.mutedUser ds,
        params.s ceT etsBy d
      )

       f (shouldF lterOut) {
        notQual f edReverseExtendedRepl esCounter. ncr()
        log(
          Level.DEBUG,
          () =>
            s"${params.logg ngPref x}:: non qual f ed reverse extended reply to be f ltered: ${t et.t et d}"
        )
      }
      shouldF lterOut
    }

    pr vate def log(level: Level,  ssage: () => Str ng): Un  = {
       f (alwaysLog || ((level != Level.OFF) && logger. sLoggable(level))) {
        val updatedLevel =  f (alwaysLog) Level. NFO else level
        logger.log(updatedLevel,  ssage())
      }
    }
  }
}

class T etsPostF lter(f lters: T etF lters.ValueSet, logger: Logger, statsRece ver: StatsRece ver)
    extends T etsPostF lterBase(f lters, logger, statsRece ver) {

  def apply(
    user d: User d,
    follo dUser ds: Seq[User d],
     nNetworkUser ds: Seq[User d],
    mutedUser ds: Set[User d],
    t ets: Seq[HydratedT et],
    numRet etsAllo d:  nt = 1,
    s ceT ets: Seq[HydratedT et] = N l
  ): Seq[HydratedT et] = {
    val logg ngPref x = s"user d: $user d"
    val params = T etsPostF lterParams(
      user d = user d,
      follo dUser ds = follo dUser ds,
       nNetworkUser ds =  nNetworkUser ds,
      mutedUser ds = mutedUser ds,
      numRet etsAllo d = numRet etsAllo d,
      logg ngPref x = logg ngPref x,
      s ceT ets = s ceT ets
    )
    super.f lter(t ets, params)
  }
}

class T etsPostF lterUser ndependent(
  f lters: T etF lters.ValueSet,
  logger: Logger,
  statsRece ver: StatsRece ver)
    extends T etsPostF lterBase(f lters, logger, statsRece ver) {

  requ re(
    (f lters -- T etF lters.User ndependent). sEmpty,
    "Only user  ndependent f lters are supported"
  )

  def apply(t ets: Seq[HydratedT et], numRet etsAllo d:  nt = 1): Seq[HydratedT et] = {
    val params = T etsPostF lterParams(
      user d = 0L,
      follo dUser ds = Seq.empty,
       nNetworkUser ds = Seq.empty,
      mutedUser ds = Set.empty,
      numRet etsAllo d
    )
    super.f lter(t ets, params)
  }
}
