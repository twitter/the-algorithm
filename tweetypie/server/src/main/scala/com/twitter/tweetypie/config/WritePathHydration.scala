package com.tw ter.t etyp e
package conf g

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.handler.T etBu lder
 mport com.tw ter.t etyp e.handler.Wr ePathQueryOpt ons
 mport com.tw ter.t etyp e.hydrator.Esc rb rdAnnotat onHydrator
 mport com.tw ter.t etyp e.hydrator.LanguageHydrator
 mport com.tw ter.t etyp e.hydrator.PlaceHydrator
 mport com.tw ter.t etyp e.hydrator.Prof leGeoHydrator
 mport com.tw ter.t etyp e.hydrator.T etDataValueHydrator
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.store. nsertT et
 mport com.tw ter.t etyp e.store.UndeleteT et
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.t etyp e.ut l.Ed ControlUt l

object Wr ePathHydrat on {
  type HydrateQuotedT et =
    FutureArrow[(User, QuotedT et, Wr ePathHydrat onOpt ons), Opt on[QuoteT et tadata]]

  case class QuoteT et tadata(
    quotedT et: T et,
    quotedUser: User,
    quoterHasAlreadyQuotedT et: Boolean)

  pr vate val log = Logger(getClass)

  val UserF eldsFor nsert: Set[UserF eld] =
    T etBu lder.userF elds

  val Allo dM ss ngF eldsOnWr e: Set[F eldByPath] =
    Set(
      Esc rb rdAnnotat onHydrator.hydratedF eld,
      LanguageHydrator.hydratedF eld,
      PlaceHydrator.HydratedF eld,
      Prof leGeoHydrator.hydratedF eld
    )

  /**
   * Bu lds a FutureArrow that performs t  necessary hydrat on  n t  wr e-path for a
   * a  nsertT et.Event.  T re are two separate hydrat on steps, pre-cac  and post-cac .
   * T  pre-cac  hydrat on step performs t  hydrat on wh ch  s safe to cac , wh le t 
   * post-cac  hydrat on step performs t  hydrat on whose results   don't want to cac 
   * on t  t et.
   *
   * T et nsertEvent conta ns two t et f elds, `t et` and ` nternalT et`.  `t et`  s
   * t   nput value used for hydrat on, and  n t  updated  nsertT et.Event returned by t 
   * FutureArrow, `t et` conta ns t  post-cac  hydrated t et wh le ` nternalT et` conta ns
   * t  pre-cac  hydrated t et.
   */
  def hydrate nsertT etEvent(
    hydrateT et: FutureArrow[(T etData, T etQuery.Opt ons), T etData],
    hydrateQuotedT et: HydrateQuotedT et
  ): FutureArrow[ nsertT et.Event,  nsertT et.Event] =
    FutureArrow { event =>
      val cause = T etQuery.Cause. nsert(event.t et. d)
      val hydrat onOpts = event.hydrateOpt ons
      val  sEd ControlEd  = event.t et.ed Control.ex sts(Ed ControlUt l. sEd ControlEd )
      val queryOpts: T etQuery.Opt ons =
        Wr ePathQueryOpt ons. nsert(cause, event.user, hydrat onOpts,  sEd ControlEd )

      val  n T etData =
        T etData(
          t et = event.t et,
          s ceT etResult = event.s ceT et.map(T etResult(_))
        )

      for {
        t etData <- hydrateT et(( n T etData, queryOpts))
        hydratedT et = t etData.t et
         nternalT et =
          t etData.cac ableT etResult
            .map(_.value.toCac dT et)
            .getOrElse(
              throw new  llegalStateExcept on(s"expected cac ableT etResult, e=${event}"))

        optQt = getQuotedT et(hydratedT et)
          .orElse(event.s ceT et.flatMap(getQuotedT et))

        hydratedQT <- optQt match {
          case None => Future.value(None)
          case So (qt) => hydrateQuotedT et((event.user, qt, hydrat onOpts))
        }
      } y eld {
        event.copy(
          t et = hydratedT et,
          _ nternalT et = So ( nternalT et),
          quotedT et = hydratedQT.map { case QuoteT et tadata(t, _, _) => t },
          quotedUser = hydratedQT.map { case QuoteT et tadata(_, u, _) => u },
          quoterHasAlreadyQuotedT et = hydratedQT.ex sts { case QuoteT et tadata(_, _, b) => b }
        )
      }
    }

  /**
   * Bu lds a FutureArrow for retr ev ng a quoted t et  tadata
   * QuotedT et struct.   f e  r t  quoted t et or t  quoted user
   *  sn't v s ble to t  t et ng user, t  FutureArrow w ll return None.
   */
  def hydrateQuotedT et(
    t etRepo: T etRepos ory.Opt onal,
    userRepo: UserRepos ory.Opt onal,
    quoterHasAlreadyQuotedRepo: QuoterHasAlreadyQuotedRepos ory.Type
  ): HydrateQuotedT et = {
    FutureArrow {
      case (t et ngUser, qt, hydrateOpt ons) =>
        val t etQueryOpts = Wr ePathQueryOpt ons.quotedT et(t et ngUser, hydrateOpt ons)
        val userQueryOpts =
          UserQueryOpt ons(
            UserF eldsFor nsert,
            UserV s b l y.V s ble,
            forUser d = So (t et ngUser. d)
          )

        St ch.run(
          St ch
            .jo n(
              t etRepo(qt.t et d, t etQueryOpts),
              userRepo(UserKey.by d(qt.user d), userQueryOpts),
              //  're fa l ng open  re on tflock except ons s nce t  should not
              // affect t  ab l y to quote t et  f tflock goes down. (although  f
              // t  call doesn't succeed, quote counts may be  naccurate for a br ef
              // per od of t  )
              quoterHasAlreadyQuotedRepo(qt.t et d, t et ngUser. d).l ftToTry
            )
            .map {
              case (So (t et), So (user),  sAlreadyQuoted) =>
                So (QuoteT et tadata(t et, user,  sAlreadyQuoted.getOrElse(false)))
              case _ => None
            }
        )
    }
  }

  /**
   * Bu lds a FutureArrow that performs any add  onal hydrat on on an UndeleteT et.Event before
   * be ng passed to a T etStore.
   */
  def hydrateUndeleteT etEvent(
    hydrateT et: FutureArrow[(T etData, T etQuery.Opt ons), T etData],
    hydrateQuotedT et: HydrateQuotedT et
  ): FutureArrow[UndeleteT et.Event, UndeleteT et.Event] =
    FutureArrow { event =>
      val cause = T etQuery.Cause.Undelete(event.t et. d)
      val hydrat onOpts = event.hydrateOpt ons
      val  sEd ControlEd  = event.t et.ed Control.ex sts(Ed ControlUt l. sEd ControlEd )
      val queryOpts = Wr ePathQueryOpt ons. nsert(cause, event.user, hydrat onOpts,  sEd ControlEd )

      // w n undelet ng a ret et, don't set s ceT etResult to enable S ceT etHydrator to
      // hydrate  
      val  n T etData = T etData(t et = event.t et)

      for {
        t etData <- hydrateT et(( n T etData, queryOpts))
        hydratedT et = t etData.t et
         nternalT et =
          t etData.cac ableT etResult
            .map(_.value.toCac dT et)
            .getOrElse(
              throw new  llegalStateExcept on(s"expected cac ableT etResult, e=${event}"))

        optQt = getQuotedT et(hydratedT et)
          .orElse(t etData.s ceT etResult.map(_.value.t et).flatMap(getQuotedT et))

        hydratedQt <- optQt match {
          case None => Future.value(None)
          case So (qt) => hydrateQuotedT et((event.user, qt, hydrat onOpts))
        }
      } y eld {
        event.copy(
          t et = hydratedT et,
          _ nternalT et = So ( nternalT et),
          s ceT et = t etData.s ceT etResult.map(_.value.t et),
          quotedT et = hydratedQt.map { case QuoteT et tadata(t, _, _) => t },
          quotedUser = hydratedQt.map { case QuoteT et tadata(_, u, _) => u },
          quoterHasAlreadyQuotedT et = hydratedQt.ex sts { case QuoteT et tadata(_, _, b) => b }
        )
      }
    }

  /**
   * Converts a T etDataValueHydrator  nto a FutureArrow that hydrates a t et for t  wr e-path.
   */
  def hydrateT et(
    hydrator: T etDataValueHydrator,
    stats: StatsRece ver,
    allo dM ss ngF elds: Set[F eldByPath] = Allo dM ss ngF eldsOnWr e
  ): FutureArrow[(T etData, T etQuery.Opt ons), T etData] = {
    val hydrat onStats = stats.scope("hydrat on")
    val m ss ngF eldsStats = hydrat onStats.scope("m ss ng_f elds")

    FutureArrow[(T etData, T etQuery.Opt ons), T etData] {
      case (td, opts) =>
        St ch
          .run(hydrator(td, opts))
          .rescue {
            case ex =>
              log.warn("Hydrat on fa led w h except on", ex)
              Future.except on(
                T etHydrat onError("Hydrat on fa led w h except on: " + ex, So (ex))
              )
          }
          .flatMap { r =>
            // Record m ss ng f elds even  f t  request succeeds)
            for (m ss ngF eld <- r.state.fa ledF elds)
              m ss ngF eldsStats.counter(m ss ngF eld.f eld dPath.mkStr ng(".")). ncr()

             f ((r.state.fa ledF elds -- allo dM ss ngF elds).nonEmpty) {
              Future.except on(
                T etHydrat onError(
                  "Fa led to hydrate. M ss ng F elds: " + r.state.fa ledF elds.mkStr ng(",")
                )
              )
            } else {
              Future.value(r.value)
            }
          }
    }
  }.trackOutco (stats, (_: Any) => "hydrat on")
}
