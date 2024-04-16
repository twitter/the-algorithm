package com.tw ter.t etyp e.handler

 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.t etyp e.repos ory.Cac Control
 mport com.tw ter.t etyp e.repos ory.T etQuery
 mport com.tw ter.t etyp e.thr ftscala. d aEnt y
 mport com.tw ter.t etyp e.thr ftscala.StatusCounts
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.t etyp e.thr ftscala.Wr ePathHydrat onOpt ons

object Wr ePathQueryOpt ons {

  /**
   * Base T etQuery. nclude for all hydrat on opt ons.
   */
  val Base nclude: T etQuery. nclude =
    GetT etsHandler.Base nclude.also(
      t etF elds = Set(
        T et.CardReferenceF eld. d,
        T et. d aTagsF eld. d,
        T et.SelfPermal nkF eld. d,
        T et.ExtendedT et tadataF eld. d,
        T et.V s bleTextRangeF eld. d,
        T et.NsfaH ghRecallLabelF eld. d,
        T et.Commun  esF eld. d,
        T et.Exclus veT etControlF eld. d,
        T et.TrustedFr endsControlF eld. d,
        T et.CollabControlF eld. d,
        T et.Ed ControlF eld. d,
        T et.Ed Perspect veF eld. d,
        T et.NoteT etF eld. d
      )
    )

  /**
   * Base T etQuery. nclude for all creat on-related hydrat ons.
   */
  val BaseCreate nclude: T etQuery. nclude =
    Base nclude
      .also(
        t etF elds = Set(
          T et.PlaceF eld. d,
          T et.Prof leGeoEnr ch ntF eld. d,
          T et.SelfThread tadataF eld. d
        ),
         d aF elds = Set( d aEnt y.Add  onal tadataF eld. d),
        quotedT et = So (true),
        pasted d a = So (true)
      )

  /**
   * Base T etQuery. nclude for all delet on-related hydrat ons.
   */
  val BaseDelete nclude: T etQuery. nclude = Base nclude
    .also(t etF elds =
      Set(T et.BounceLabelF eld. d, T et.Conversat onControlF eld. d, T et.Ed ControlF eld. d))

  val AllCounts: Set[Short] = StatusCounts.f eld nfos.map(_.tf eld. d).toSet

  def  nsert(
    cause: T etQuery.Cause,
    user: User,
    opt ons: Wr ePathHydrat onOpt ons,
     sEd ControlEd : Boolean
  ): T etQuery.Opt ons =
    createOpt ons(
      wr ePathHydrat onOpt ons = opt ons,
       ncludePerspect ve = false,
      //  nclude counts  f t et ed , ot rw se false
       ncludeCounts =  sEd ControlEd ,
      cause = cause,
      forUser = user,
      // Do not perform any f lter ng w n   are hydrat ng t  t et   are creat ng
      safetyLevel = SafetyLevel.F lterNone
    )

  def ret etS ceT et(user: User, opt ons: Wr ePathHydrat onOpt ons): T etQuery.Opt ons =
    createOpt ons(
      wr ePathHydrat onOpt ons = opt ons,
       ncludePerspect ve = true,
       ncludeCounts = true,
      cause = T etQuery.Cause.Read,
      forUser = user,
      //  f Scarecrow  s down,   may proceed w h creat ng a RT. T  safetyLevel  s necessary
      // to prevent so that t   nner t et's count  s not sent  n t  T etCreateEvent   send
      // to EventBus.  f t   re em ted, l ve p pel ne would publ sh counts to t  cl ents.
      safetyLevel = SafetyLevel.T etWr esAp 
    )

  def quotedT et(user: User, opt ons: Wr ePathHydrat onOpt ons): T etQuery.Opt ons =
    createOpt ons(
      wr ePathHydrat onOpt ons = opt ons,
       ncludePerspect ve = true,
       ncludeCounts = true,
      cause = T etQuery.Cause.Read,
      forUser = user,
      //   pass  n t  safetyLevel so that t   nner t et's are excluded
      // from t  T etCreateEvent   send to EventBus.  f t   re em ted,
      // l ve p pel ne would publ sh counts to t  cl ents.
      safetyLevel = SafetyLevel.T etWr esAp 
    )

  pr vate def condSet[A](cond: Boolean,  em: A): Set[A] =
     f (cond) Set( em) else Set.empty

  pr vate def createOpt ons(
    wr ePathHydrat onOpt ons: Wr ePathHydrat onOpt ons,
     ncludePerspect ve: Boolean,
     ncludeCounts: Boolean,
    cause: T etQuery.Cause,
    forUser: User,
    safetyLevel: SafetyLevel,
  ): T etQuery.Opt ons = {
    val cardsEnabled: Boolean = wr ePathHydrat onOpt ons. ncludeCards
    val cardsPlatformKeySpec f ed: Boolean = wr ePathHydrat onOpt ons.cardsPlatformKey.nonEmpty
    val cardsV1Enabled: Boolean = cardsEnabled && !cardsPlatformKeySpec f ed
    val cardsV2Enabled: Boolean = cardsEnabled && cardsPlatformKeySpec f ed

    T etQuery.Opt ons(
       nclude = BaseCreate nclude.also(
        t etF elds =
          condSet( ncludePerspect ve, T et.Perspect veF eld. d) ++
            condSet(cardsV1Enabled, T et.CardsF eld. d) ++
            condSet(cardsV2Enabled, T et.Card2F eld. d) ++
            condSet( ncludeCounts, T et.CountsF eld. d) ++
            // for Prev ousCountsF eld, copy  ncludeCounts state on t  wr e path
            condSet( ncludeCounts, T et.Prev ousCountsF eld. d) ++
            // hydrate Conversat onControl on Reply T et creat ons so cl ents can consu 
            Set(T et.Conversat onControlF eld. d),
        countsF elds =  f ( ncludeCounts) AllCounts else Set.empty
      ),
      cause = cause,
      forUser d = So (forUser. d),
      cardsPlatformKey = wr ePathHydrat onOpt ons.cardsPlatformKey,
      languageTag = forUser.account.map(_.language).getOrElse("en"),
      extens onsArgs = wr ePathHydrat onOpt ons.extens onsArgs,
      safetyLevel = safetyLevel,
      s mpleQuotedT et = wr ePathHydrat onOpt ons.s mpleQuotedT et
    )
  }

  def deleteT ets: T etQuery.Opt ons =
    T etQuery.Opt ons(
       nclude = BaseDelete nclude,
      cac Control = Cac Control.ReadOnlyCac ,
      extens onsArgs = None,
      requ reS ceT et = false // ret et should be deletable even  f s ce t et m ss ng
    )

  def deleteT etsW houtEd Control: T etQuery.Opt ons =
    deleteT ets.copy(enableEd ControlHydrat on = false)
}
