package com.tw ter.t etyp e
package handler

 mport com.tw ter.expandodo.thr ftscala.Card2RequestOpt ons
 mport com.tw ter.featuresw c s.v2.FeatureSw chResults
 mport com.tw ter.g zmoduck.ut l.UserUt l
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.T etCreateFa lure
 mport com.tw ter.t etyp e.repos ory.Card2Repos ory
 mport com.tw ter.t etyp e.repos ory.StratoPromotedT etRepos ory
 mport com.tw ter.t etyp e.repos ory.StratoSubscr pt onVer f cat onRepos ory
 mport com.tw ter.t etyp e.repos ory.T etQuery
 mport com.tw ter.t etyp e.repos ory.T etRepos ory
 mport com.tw ter.t etyp e.repos ory.UrlCard2Key
 mport com.tw ter.t etyp e.thr ftscala.Ed Control
 mport com.tw ter.t etyp e.thr ftscala.Ed Opt ons
 mport com.tw ter.t etyp e.thr ftscala.T etCreateState
 mport com.tw ter.t etyp e.ut l.Ed ControlUt l._
 mport com.tw ter.t etyp e.thr ftscala.CardReference
 mport com.tw ter.t etyp e.thr ftscala.Ed Control n  al
 mport com.tw ter.t etyp e.thr ftscala.PostT etRequest
 mport com.tw ter.t etyp e.ut l.Commun yAnnotat on
 mport com.tw ter.t etyp e.ut l.Ed ControlUt l
 mport com.tw ter.ut l.Future

object Ed ControlBu lder {
  type Type = Request => Future[Opt on[Ed Control]]

  val ed T etCountStat = "ed _t et_count"
  val ed ControlQueryOpt ons = T etQuery.Opt ons(
    T etQuery. nclude(Set(T et.CoreDataF eld. d, T et.Ed ControlF eld. d))
  )
  val T etEd Creat onEnabledKey = "t et_ed _creat on_enabled"
  val T etEd Creat onEnabledForTw terBlueKey = "t et_ed _creat on_enabled_for_tw ter_blue"

  val pollCardNa s: Set[Str ng] = Set(
    "poll2cho ce_text_only",
    "poll3cho ce_text_only",
    "poll4cho ce_text_only",
    "poll2cho ce_ mage",
    "poll3cho ce_ mage",
    "poll4cho ce_ mage",
    "poll2cho ce_v deo",
    "poll3cho ce_v deo",
    "poll4cho ce_v deo",
  )

  /** Used just for c ck ng card na  for poll c ck  n case cards platform key not prov ded. */
  val defaultCardsPlatformKey = " Phone-13"

  /**
   * Do   assu  a T et has a poll (wh ch makes   not ed able) w n   has a card
   * that could be a poll, and   cannot be resolved at create.
   */
  val  sPollCardAssumpt on = true

  val t etEd Subscr pt onRes ce = "feature/t et_ed "

  val log: Logger = Logger(getClass)

  case class Request(
    postT etRequest: PostT etRequest,
    t et: T et,
    matc dResults: Opt on[FeatureSw chResults]) {
    def ed Opt ons: Opt on[Ed Opt ons] = postT etRequest.ed Opt ons

    def author d: User d = postT etRequest.user d

    def createdAt: T   = T  .fromM ll seconds(t et.coreData.get.createdAtSecs * 1000L)

    def t et d: T et d = t et. d

    def cardReference: Opt on[CardReference] =
      postT etRequest.add  onalF elds.flatMap(_.cardReference)

    def cardsPlatformKey: Opt on[Str ng] =
      postT etRequest.hydrat onOpt ons.flatMap(_.cardsPlatformKey)
  }

  def apply(
    t etRepo: T etRepos ory.Type,
    card2Repo: Card2Repos ory.Type,
    promotedT etRepo: StratoPromotedT etRepos ory.Type,
    subscr pt onVer f cat onRepo: StratoSubscr pt onVer f cat onRepos ory.Type,
    d sablePromotedT etEd : Gate[Un ],
    c ckTw terBlueSubscr pt on: Gate[Un ],
    setEd W ndowToS xtyM nutes: Gate[Un ],
    stats: StatsRece ver
  ): Type = {

    // Nullcast t ets not allo d, except  f t  t et has a commun y annotat on
    def  sNullcastedButNotCommun yT et(request: Request): Boolean = {

      val  sNullcasted: Boolean = request.t et.coreData.get.nullcast

      val commun y ds: Opt on[Seq[Commun y d]] =
        request.postT etRequest.add  onalF elds
          .flatMap(Commun yAnnotat on.add  onalF eldsToCommun y Ds)

       sNullcasted && !(commun y ds.ex sts(_.nonEmpty))
    }

    def  sSuperFollow(t et: T et): Boolean = t et.exclus veT etControl. sDef ned

    def  sCollabT et(t et: T et): Boolean = t et.collabControl. sDef ned

    def  sReplyToT et(t et: T et): Boolean =
      getReply(t et).flatMap(_. nReplyToStatus d). sDef ned

    // W n card  s tombstone, t et  s not cons dered a poll, and t refore can be ed  el g ble.
    val cardReferenceUr  sTombstone = stats.counter("ed _control_bu lder_card_tombstoned")
    //   c ck w t r t ets are polls s nce t se are not ed  el g ble.
    //  f   are not sure due to lookup fa lure,   take an ` sPollCardAssumpt on`.
    def  sPoll(
      card2Repo: Card2Repos ory.Type,
      cardReference: CardReference,
      cardsPlatformKey: Str ng,
    ): St ch[Boolean] = {
       f (cardReference.cardUr  == "tombstone://card") {
        cardReferenceUr  sTombstone. ncr()
        St ch.value(false)
      } else {
        val key = UrlCard2Key(cardReference.cardUr )
        // `allowNonTcoUrls = true` T  allows us to c ck  f non-tco urls (e.g. apple.com) have a card
        // at t  po nt  n t et bu lder urls can be  n t  r or g nal form and not tco f ed.
        val opt ons = Card2RequestOpt ons(
          platformKey = cardsPlatformKey,
          allowNonTcoUrls = true
        )
        card2Repo(key, opt ons)
          .map(card2 => pollCardNa s.conta ns(card2.na ))
      }
    }

    def  sFeatureSw chEnabled(matc dResults: Opt on[FeatureSw chResults], key: Str ng): Boolean =
      matc dResults.flatMap(_.getBoolean(key, shouldLog mpress on = false)).conta ns(true)

    def wrap n  al( n  al: Ed Control n  al): Opt on[Ed Control. n  al] =
      So (Ed Control. n  al( n  al =  n  al))

    // C cks for val d y of an ed  are  mple nted as procedures
    // that throw an error  n case a c ck fa ls. T  composes way better than
    // return ng a Try/Future/St ch because:
    // 1.   do not need to dec de wh ch of t  afore nt oned conta ners to use.
    // 2. T  c cks as below compose w h callbacks  n all t  afore nt oned conta ners.

    val ed RequestOuts deOfAllowl st = stats.counter("ed _control_bu lder_rejected", "allowl st")

    // T   thod uses two feature sw c s:
    // - T etEd Creat onEnabledKey author zes t  user to ed  t ets d rectly
    // - T etEd Creat onEnabledForTw terBlueKey author zes t  user to ed  t ets  f t y have
    //     a Tw ter Blue subscr pt on
    //
    // Test users are always author zed to ed  t ets.
    def c ckUserEl g b l y(
      author d: User d,
      matc dResults: Opt on[FeatureSw chResults]
    ): St ch[Un ] = {
      val  sTestUser = UserUt l. sTestUser d(author d)
      val author zedW houtTw terBlue =
         sFeatureSw chEnabled(matc dResults, T etEd Creat onEnabledKey)

       f ( sTestUser || author zedW houtTw terBlue) {
        //  f t  ed  ng user  s a test user or  s author zed by t  non-Tw ter Blue feature
        // sw ch, allow ed  ng.
        St ch.Done
      } else {
        // Ot rw se, c ck  f t y're author zed by t  Tw ter Blue feature sw ch and  f t y're
        // subscr bed to Tw ter Blue.
        val author zedW hTw terBlue: St ch[Boolean] =
           f (c ckTw terBlueSubscr pt on() &&
             sFeatureSw chEnabled(matc dResults, T etEd Creat onEnabledForTw terBlueKey)) {
            subscr pt onVer f cat onRepo(author d, t etEd Subscr pt onRes ce)
          } else St ch.value(false)

        author zedW hTw terBlue.flatMap { author zed =>
           f (!author zed) {
            log.error(s"User ${author d} unauthor zed to ed ")
            ed RequestOuts deOfAllowl st. ncr()
            St ch.except on(T etCreateFa lure.State(T etCreateState.Ed T etUserNotAuthor zed))
          } else St ch.Done
        }
      }
    }

    val ed RequestByNonAuthor = stats.counter("ed _control_bu lder_rejected", "not_author")
    def c ckAuthor(
      author d: User d,
      prev ousT etAuthor d: User d
    ): Un  = {
       f (author d != prev ousT etAuthor d) {
        ed RequestByNonAuthor. ncr()
        throw T etCreateFa lure.State(T etCreateState.Ed T etUserNotAuthor)
      }
    }

    val t etEd ForStaleT et = stats.counter("ed _control_bu lder_rejected", "stale")
    def c ckLatestEd (
      prev ousT et d: T et d,
       n  al: Ed Control n  al,
    ): Un  = {
       f (prev ousT et d !=  n  al.ed T et ds.last) {
        t etEd ForStaleT et. ncr()
        throw T etCreateFa lure.State(T etCreateState.Ed T etNotLatestVers on)
      }
    }

    val t etEd ForL m Reac d = stats.counter("ed _control_bu lder_rejected", "ed s_l m ")
    def c ckEd sRema n ng( n  al: Ed Control n  al): Un  = {
       n  al.ed sRema n ng match {
        case So (number)  f number > 0 => // OK
        case _ =>
          t etEd ForL m Reac d. ncr()
          throw T etCreateFa lure.State(T etCreateState.Ed CountL m Reac d)
      }
    }

    val ed T etExp red = stats.counter("ed _control_bu lder_rejected", "exp red")
    val ed T etExp redNoEd Control =
      stats.counter("ed _control_bu lder_rejected", "exp red", "no_ed _control")
    def c ckEd T  W ndow( n  al: Ed Control n  al): Un  = {
       n  al.ed ableUnt lMsecs match {
        case So (m ll s)  f T  .now < T  .fromM ll seconds(m ll s) => // OK
        case So (_) =>
          ed T etExp red. ncr()
          throw T etCreateFa lure.State(T etCreateState.Ed T  L m Reac d)
        case ed able =>
          ed T etExp red. ncr()
           f (ed able. sEmpty) {
            ed T etExp redNoEd Control. ncr()
          }
          throw T etCreateFa lure.State(T etCreateState.Ed T  L m Reac d)
      }
    }

    val t etEd NotEl g ble = stats.counter("ed _control_bu lder_rejected", "not_el g ble")
    def c ck sEd El g ble( n  al: Ed Control n  al): Un  = {
       n  al. sEd El g ble match {
        case So (true) => // OK
        case _ =>
          t etEd NotEl g ble. ncr()
          throw T etCreateFa lure.State(T etCreateState.NotEl g bleForEd )
      }
    }

    val ed Control n  alM ss ng =
      stats.counter("ed _control_bu lder_rejected", " n  al_m ss ng")
    def f ndEd Control n  al(prev ousT et: T et): Ed Control n  al = {
      prev ousT et.ed Control match {
        case So (Ed Control. n  al( n  al)) =>  n  al
        case So (Ed Control.Ed (ed )) =>
          ed .ed Control n  al.getOrElse {
            ed Control n  alM ss ng. ncr()
            throw new  llegalStateExcept on(
              "Encountered ed  t et w h m ss ng ed Control n  al.")
          }
        case _ =>
          throw T etCreateFa lure.State(T etCreateState.Ed T  L m Reac d)
      }
    }

    val ed PromotedT et = stats.counter("t et_ed _for_promoted_t et")
    def c ckPromotedT et(
      prev ousT et d: T et d,
      promotedT etRepo: StratoPromotedT etRepos ory.Type,
      d sablePromotedT etEd : Gate[Un ]
    ): St ch[Un ] = {
       f (d sablePromotedT etEd ()) {
        promotedT etRepo(prev ousT et d).flatMap {
          case false =>
            St ch.Done
          case true =>
            ed PromotedT et. ncr()
            St ch.except on(T etCreateFa lure.State(T etCreateState.Ed T etUserNotAuthor zed))
        }
      } else {
        St ch.Done
      }
    }

    // Each t   ed   s made, count how many vers ons a t et already has.
    // Value should be always bet en 1 and 4.
    val ed T etCount = 0
      .to(Ed ControlUt l.maxT etEd sAllo d)
      .map(  =>   -> stats.counter("ed _control_bu lder_ed s_count",  .toStr ng))
      .toMap
    // Overall counter and fa lures of card resolut on for poll lookups. Needed because polls are not ed able.
    val pollCardResolut onTotal = stats.counter("ed _control_bu lder_card_resolut on", "total")
    val pollCardResolut onFa lure =
      stats.counter("ed _control_bu lder_card_resolut on", "fa lures")
    // Ed  of  n  al t et requested, and all ed  c cks successful.
    val  n  alEd T et = stats.counter("ed _control_bu lder_ n  al_ed ")
    request =>
      St ch.run {
        request.ed Opt ons match {
          case None =>
            val ed Control =
              makeEd Control n  al(
                t et d = request.t et d,
                createdAt = request.createdAt,
                setEd W ndowToS xtyM nutes = setEd W ndowToS xtyM nutes
              ). n  al.copy(
                 sEd El g ble = So (
                  ! sNullcastedButNotCommun yT et(request)
                    && ! sSuperFollow(request.t et)
                    && ! sCollabT et(request.t et)
                    && ! sReplyToT et(request.t et)
                ),
              )
            (ed Control. sEd El g ble, request.cardReference) match {
              case (So (true), So (reference)) =>
                pollCardResolut onTotal. ncr()
                 sPoll(
                  card2Repo = card2Repo,
                  cardReference = reference,
                  cardsPlatformKey = request.cardsPlatformKey.getOrElse(defaultCardsPlatformKey),
                ).rescue {
                    // Revert to t  assu d value  f card cannot be resolved.
                    case _ =>
                      pollCardResolut onFa lure. ncr()
                      St ch.value( sPollCardAssumpt on)
                  }
                  .map { t et sAPoll =>
                    wrap n  al(ed Control.copy( sEd El g ble = So (!t et sAPoll)))
                  }
              case _ => St ch.value(wrap n  al(ed Control))
            }
          case So (ed Opt ons) =>
            for {
              (prev ousT et, _, _) <- St ch.jo n(
                t etRepo(ed Opt ons.prev ousT et d, ed ControlQueryOpt ons),
                c ckPromotedT et(
                  ed Opt ons.prev ousT et d,
                  promotedT etRepo,
                  d sablePromotedT etEd ),
                c ckUserEl g b l y(
                  author d = request.author d,
                  matc dResults = request.matc dResults)
              )
            } y eld {
              val  n  al = f ndEd Control n  al(prev ousT et)
              c ckAuthor(
                author d = request.author d,
                prev ousT etAuthor d = getUser d(prev ousT et))
              ed T etCount
                .get( n  al.ed T et ds.s ze)
                .orElse(ed T etCount.get(Ed ControlUt l.maxT etEd sAllo d))
                .foreach(counter => counter. ncr())
              c ckLatestEd (prev ousT et. d,  n  al)
              c ckEd sRema n ng( n  al)
              c ckEd T  W ndow( n  al)
              c ck sEd El g ble( n  al)
               f ( n  al.ed T et ds == Seq(prev ousT et. d)) {
                 n  alEd T et. ncr()
              }
              So (ed ControlEd ( n  alT et d =  n  al.ed T et ds. ad))
            }
        }
      }
  }
}
