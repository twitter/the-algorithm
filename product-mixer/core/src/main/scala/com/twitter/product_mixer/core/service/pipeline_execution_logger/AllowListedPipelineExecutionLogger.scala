package com.tw ter.product_m xer.core.serv ce.p pel ne_execut on_logger

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule.P pel neExecut onLoggerAllowL st
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule.Serv ceLocal
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.FuturePools
 mport com.tw ter.product_m xer.shared_l brary.observer.Observer.FutureObserver
 mport com.tw ter.ut l.Try
 mport com.tw ter.ut l.logg ng.Logg ng
 mport ppr nt.PPr nter
 mport ppr nt.Tree
 mport ppr nt.Ut l
 mport ppr nt.tuplePref x
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 *  n  al  mple ntat on from:
 * https://stackoverflow.com/quest ons/15718506/scala-how-to-pr nt-case-classes-l ke-pretty-pr nted-tree/57080463#57080463
 */
object AllowL stedP pel neExecut onLogger {

  /**
   * G ven a case class who's argu nts are all declared f elds on t  class,
   * returns an  erator of t  f eld na  and values
   */
  pr vate[p pel ne_execut on_logger] def caseClassF elds(
    caseClass: Product
  ):  erator[(Str ng, Any)] = {
    val f eldValues = caseClass.product erator.toSet
    val f elds = caseClass.getClass.getDeclaredF elds.toSeq
      .f lterNot(f => f. sSynt t c || java.lang.reflect.Mod f er. sStat c(f.getMod f ers))
    f elds. erator
      .map { f =>
        f.setAccess ble(true)
        f.getNa  -> f.get(caseClass)
      }.f lter { case (_, v) => f eldValues.conta ns(v) }
  }

  /**
   * Returns w t r a g ven [[Product]]  s a case class wh ch   can render n cely wh ch:
   * - has a [[Product.productAr y]] <= t  number of declared f elds
   * -  sn't a bu lt  n b nary operator
   * -  sn't a tuple
   * - who's argu nts are f elds (not  thods)
   * - every [[Product.productEle nt]] has a correspond ng f eld
   *
   * T  w ll return false for so  case classes w re   can not rel ably determ ne wh ch f eld na s correspond to
   * each value  n t  case class (t  can happen  f a case class  mple nts an abstract class result ng  n val f elds
   * becom ng  thods.
   */
  pr vate[p pel ne_execut on_logger] def  sRenderableCaseClass(caseClass: Product): Boolean = {
    val poss bleToBeRenderableCaseClass =
      caseClass.getClass.getDeclaredF elds.length >= caseClass.productAr y
    val  sntBu lt nOperator =
      !(caseClass.productAr y == 2 && Ut l. sOperator(caseClass.productPref x))
    val  sntTuple = !caseClass.getClass.getNa .startsW h(tuplePref x)
    val declaredF eldsMatc sCaseClassF elds = {
      val caseClassF elds = caseClass.product erator.toSet
      caseClass.getClass.getDeclaredF elds. erator
        .f lterNot(f => f. sSynt t c || java.lang.reflect.Mod f er. sStat c(f.getMod f ers))
        .count { f =>
          f.setAccess ble(true)
          caseClassF elds.conta ns(f.get(caseClass))
        } >= caseClass.productAr y
    }

    poss bleToBeRenderableCaseClass &&  sntBu lt nOperator &&  sntTuple && declaredF eldsMatc sCaseClassF elds
  }

  /** Makes a [[Tree]] wh ch w ll render as `key = value` */
  pr vate def keyValuePa r(key: Str ng, value: Tree): Tree = {
    Tree. nf x(Tree.L eral(key), "=", value)
  }

  /**
   * Spec al handl ng for case classes who's f eld na s can be eas ly pa red w h t  r values.
   * T  w ll make t  [[PPr nter]] render t m as
   * {{{
   *   CaseClassNa (
   *     f eld1 = value1,
   *     f eld2 = value2
   *   )
   * }}}
   *  nstead of
   * {{{
   *   CaseClassNa (
   *     value1,
   *     value2
   *   )
   * }}}
   *
   * For case classes who's f elds end up be ng comp led as  thods, t  w ll fall back
   * to t  bu lt  n handl ng of case classes w hout t  r f eld na s.
   */
  pr vate[p pel ne_execut on_logger] def add  onalHandlers: Part alFunct on[Any, Tree] = {
    case caseClass: Product  f  sRenderableCaseClass(caseClass) =>
      Tree.Apply(
        caseClass.productPref x,
        caseClassF elds(caseClass).flatMap {
          case (key, value) =>
            val valueTree = pr nter.tree fy(value, false, true)
            Seq(keyValuePa r(key, valueTree))
        }
      )
  }

  /**
   * [[PPr nter]]  nstance to use w n render ng scala objects
   * uses BlackAndWh e because colors mangle t  output w n look ng at t  logs  n pla n text
   */
  pr vate val pr nter: PPr nter = PPr nter.BlackWh e.copy(
    // arb rary h gh value to turn off truncat on
    default  ght =  nt.MaxValue,
    // t  relat vely h gh w dth w ll cause so  wrapp ng but many of t  pretty pr nted objects
    // w ll be sparse (e.g. None,\n None,\n, None,\n)
    defaultW dth = 300,
    // use reflect on to pr nt f eld na s (can be deleted  n Scala 2.13)
    add  onalHandlers = add  onalHandlers
  )

  /** G ven any scala object, return a Str ng representat on of   */
  pr vate[p pel ne_execut on_logger] def objectAsStr ng(o: Any): Str ng =
    pr nter.token ze(o).mkStr ng
}

@S ngleton
class AllowL stedP pel neExecut onLogger @ nject() (
  @Flag(Serv ceLocal)  sServ ceLocal: Boolean,
  @Flag(P pel neExecut onLoggerAllowL st) allowL st: Seq[Str ng],
  statsRece ver: StatsRece ver)
    extends P pel neExecut onLogger
    w h Logg ng {

  pr vate val scopedStats = statsRece ver.scope("AllowL stedP pel neExecut onLogger")

  val allowL stRoles: Set[Str ng] = allowL st.toSet

  pr vate val futurePool =
    FuturePools.boundedF xedThreadPool(
      "AllowL stedP pel neExecut onLogger",
      // s ngle thread, may need to be adjusted h g r  f   cant keep up w h t  work queue
      f xedThreadCount = 1,
      // arb rar ly large enough to handle sp kes w hout caus ng large allocat ons or reta n ng past mult ple GC cycles
      workQueueS ze = 100,
      scopedStats
    )

  pr vate val futureObserver = new FutureObserver[Un ](scopedStats, Seq.empty)

  pr vate val loggerOutputPath = Try(System.getProperty("log.allow_l sted_execut on_logger.output"))

  overr de def apply(p pel neQuery: P pel neQuery,  ssage: Any): Un  = {

    val userRoles: Set[Str ng] = p pel neQuery.cl entContext.userRoles.getOrElse(Set.empty)

    // C ck  f t  request  s  n t  allowl st v a a cleverly opt m zed set  ntersect on
    val allowL sted =
       f (allowL stRoles.s ze > userRoles.s ze)
        userRoles.ex sts(allowL stRoles.conta ns)
      else
        allowL stRoles.ex sts(userRoles.conta ns)

     f ( sServ ceLocal || allowL sted) {
      futureObserver(
        /**
         * fa lure to enqueue t  work w ll result w h a fa led [[com.tw ter.ut l.Future]]
         * conta n ng a [[java.ut l.concurrent.RejectedExecut onExcept on]] wh ch t  wrapp ng [[futureObserver]]
         * w ll record  tr cs for.
         */
        futurePool {
          logger. nfo(AllowL stedP pel neExecut onLogger.objectAsStr ng( ssage))

           f ( sServ ceLocal && loggerOutputPath. sReturn) {
            pr ntln(s"Logged request to: ${loggerOutputPath.get()}")
          }
        }
      )
    }
  }
}
