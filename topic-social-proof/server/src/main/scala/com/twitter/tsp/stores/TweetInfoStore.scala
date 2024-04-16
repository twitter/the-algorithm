package com.tw ter.tsp.stores

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.tsp.thr ftscala.TspT et nfo
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.thr ftscala.T et althScores
 mport com.tw ter.fr gate.thr ftscala.UserAgathaScores
 mport com.tw ter.logg ng.Logger
 mport com.tw ter. d aserv ces.commons.thr ftscala. d aCategory
 mport com.tw ter. d aserv ces.commons.t et d a.thr ftscala. d a nfo
 mport com.tw ter. d aserv ces.commons.t et d a.thr ftscala. d aS zeType
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.storehaus.ReadableStoreOfSt ch
 mport com.tw ter.st ch.t etyp e.T etyP e
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eExcept on
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.top cl st ng.Annotat onRuleProv der
 mport com.tw ter.tsp.ut ls. althS gnalsUt ls
 mport com.tw ter.t etyp e.thr ftscala.T et nclude
 mport com.tw ter.t etyp e.thr ftscala.{T et => TT et}
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  outExcept on
 mport com.tw ter.ut l.T  r

object T etyP eF eldsStore {

  // T et f elds opt ons. Only f elds spec f ed  re w ll be hydrated  n t  t et
  pr vate val CoreT etF elds: Set[T et nclude] = Set[T et nclude](
    T et nclude.T etF eld d(TT et. dF eld. d),
    T et nclude.T etF eld d(TT et.CoreDataF eld. d), // needed for t  author d
    T et nclude.T etF eld d(TT et.LanguageF eld. d),
    T et nclude.CountsF eld d(StatusCounts.Favor eCountF eld. d),
    T et nclude.CountsF eld d(StatusCounts.Ret etCountF eld. d),
    T et nclude.T etF eld d(TT et.QuotedT etF eld. d),
    T et nclude.T etF eld d(TT et. d aKeysF eld. d),
    T et nclude.T etF eld d(TT et.Esc rb rdEnt yAnnotat onsF eld. d),
    T et nclude.T etF eld d(TT et. d aF eld. d),
    T et nclude.T etF eld d(TT et.UrlsF eld. d)
  )

  pr vate val gtfo: GetT etF eldsOpt ons = GetT etF eldsOpt ons(
    t et ncludes = CoreT etF elds,
    safetyLevel = So (SafetyLevel.Recom ndat ons)
  )

  def getStoreFromT etyP e(
    t etyP e: T etyP e,
    convertExcept onsToNotFound: Boolean = true
  ): ReadableStore[Long, GetT etF eldsResult] = {
    val log = Logger("T etyP eF eldsStore")

    ReadableStoreOfSt ch { t et d: Long =>
      t etyP e
        .getT etF elds(t et d, opt ons = gtfo)
        .rescue {
          case ex: T etyP eExcept on  f convertExcept onsToNotFound =>
            log.error(ex, s"Error wh le h t ng t etyp e ${ex.result}")
            St ch.NotFound
        }
    }
  }
}

object T et nfoStore {

  case class  sPassT et althF lters(t etStr ctest: Opt on[Boolean])

  case class  sPassAgatha althF lters(agathaStr ctest: Opt on[Boolean])

  pr vate val  althStoreT  out: Durat on = 40.m ll seconds
  pr vate val  sPassT et althF lters:  sPassT et althF lters =  sPassT et althF lters(None)
  pr vate val  sPassAgatha althF lters:  sPassAgatha althF lters =  sPassAgatha althF lters(None)
}

case class T et nfoStore(
  t etF eldsStore: ReadableStore[T et d, GetT etF eldsResult],
  t et althModelStore: ReadableStore[T et d, T et althScores],
  user althModelStore: ReadableStore[User d, UserAgathaScores],
  t  r: T  r
)(
  statsRece ver: StatsRece ver)
    extends ReadableStore[T et d, TspT et nfo] {

   mport T et nfoStore._

  pr vate[t ] def toT et nfo(
    t etF eldsResult: GetT etF eldsResult
  ): Future[Opt on[TspT et nfo]] = {
    t etF eldsResult.t etResult match {
      case result: T etF eldsResultState.Found  f result.found.suppressReason. sEmpty =>
        val t et = result.found.t et

        val author dOpt = t et.coreData.map(_.user d)
        val favCountOpt = t et.counts.flatMap(_.favor eCount)

        val languageOpt = t et.language.map(_.language)
        val has mageOpt =
          t et. d aKeys.map(_.map(_. d aCategory).ex sts(_ ==  d aCategory.T et mage))
        val hasG fOpt =
          t et. d aKeys.map(_.map(_. d aCategory).ex sts(_ ==  d aCategory.T etG f))
        val  sNsfwAuthorOpt = So (
          t et.coreData.ex sts(_.nsfwUser) || t et.coreData.ex sts(_.nsfwAdm n))
        val  sT etReplyOpt = t et.coreData.map(_.reply. sDef ned)
        val hasMult ple d aOpt =
          t et. d aKeys.map(_.map(_. d aCategory).s ze > 1)

        val  sKGODenyl st = So (
          t et.esc rb rdEnt yAnnotat ons
            .ex sts(_.ent yAnnotat ons.ex sts(Annotat onRuleProv der. sSuppressedTop csDenyl st)))

        val  sNullcastOpt = t et.coreData.map(_.nullcast) // T se are Ads. go/nullcast

        val v deoDurat onOpt = t et. d a.flatMap(_.flatMap {
          _. d a nfo match {
            case So ( d a nfo.V deo nfo( nfo)) =>
              So (( nfo.durat onM ll s + 999) / 1000) // v deo playt   always round up
            case _ => None
          }
        }. adOpt on)

        // T re many d fferent types of v deos. To be robust to new types be ng added,   just use
        // t  v deoDurat onOpt to keep track of w t r t   em has a v deo or not.
        val hasV deo = v deoDurat onOpt. sDef ned

        val  d aD  ns onsOpt =
          t et. d a.flatMap(_. adOpt on.flatMap(
            _.s zes.f nd(_.s zeType ==  d aS zeType.Or g).map(s ze => (s ze.w dth, s ze.  ght))))

        val  d aW dth =  d aD  ns onsOpt.map(_._1).getOrElse(1)
        val  d a  ght =  d aD  ns onsOpt.map(_._2).getOrElse(1)
        // h gh resolut on  d a's w dth  s always greater than 480px and   ght  s always greater than 480px
        val  sH gh d aResolut on =  d a  ght > 480 &&  d aW dth > 480
        val  sVert calAspectRat o =  d a  ght >=  d aW dth &&  d aW dth > 1
        val hasUrlOpt = t et.urls.map(_.nonEmpty)

        (author dOpt, favCountOpt) match {
          case (So (author d), So (favCount)) =>
            hydrate althScores(t et. d, author d).map {
              case ( sPassAgatha althF lters,  sPassT et althF lters) =>
                So (
                  TspT et nfo(
                    author d = author d,
                    favCount = favCount,
                    language = languageOpt,
                    has mage = has mageOpt,
                    hasV deo = So (hasV deo),
                    hasG f = hasG fOpt,
                     sNsfwAuthor =  sNsfwAuthorOpt,
                     sKGODenyl st =  sKGODenyl st,
                     sNullcast =  sNullcastOpt,
                    v deoDurat onSeconds = v deoDurat onOpt,
                     sH gh d aResolut on = So ( sH gh d aResolut on),
                     sVert calAspectRat o = So ( sVert calAspectRat o),
                     sPassAgatha althF lterStr ctest =  sPassAgatha althF lters.agathaStr ctest,
                     sPassT et althF lterStr ctest =  sPassT et althF lters.t etStr ctest,
                     sReply =  sT etReplyOpt,
                    hasMult ple d a = hasMult ple d aOpt,
                    hasUrl = hasUrlOpt
                  ))
            }
          case _ =>
            statsRece ver.counter("m ss ngF elds"). ncr()
            Future.None // T se values should always ex st.
        }
      case _: T etF eldsResultState.NotFound =>
        statsRece ver.counter("notFound"). ncr()
        Future.None
      case _: T etF eldsResultState.Fa led =>
        statsRece ver.counter("fa led"). ncr()
        Future.None
      case _: T etF eldsResultState.F ltered =>
        statsRece ver.counter("f ltered"). ncr()
        Future.None
      case _ =>
        statsRece ver.counter("unknown"). ncr()
        Future.None
    }
  }

  pr vate[t ] def hydrate althScores(
    t et d: T et d,
    author d: Long
  ): Future[( sPassAgatha althF lters,  sPassT et althF lters)] = {
    Future
      .jo n(
        t et althModelStore
          .mult Get(Set(t et d))(t et d),
        user althModelStore
          .mult Get(Set(author d))(author d)
      ).map {
        case (t et althScoresOpt, userAgathaScoresOpt) =>
          // T  stats  lp us understand empty rate for AgathaCal bratedNsfw / NsfwTextUserScore
          statsRece ver.counter("totalCountAgathaScore"). ncr()
           f (userAgathaScoresOpt.getOrElse(UserAgathaScores()).agathaCal bratedNsfw. sEmpty)
            statsRece ver.counter("emptyCountAgathaCal bratedNsfw"). ncr()
           f (userAgathaScoresOpt.getOrElse(UserAgathaScores()).nsfwTextUserScore. sEmpty)
            statsRece ver.counter("emptyCountNsfwTextUserScore"). ncr()

          val  sPassAgatha althF lters =  sPassAgatha althF lters(
            agathaStr ctest =
              So ( althS gnalsUt ls. sT etAgathaModelQual f ed(userAgathaScoresOpt)),
          )

          val  sPassT et althF lters =  sPassT et althF lters(
            t etStr ctest =
              So ( althS gnalsUt ls. sT et althModelQual f ed(t et althScoresOpt))
          )

          ( sPassAgatha althF lters,  sPassT et althF lters)
      }.ra seW h n( althStoreT  out)(t  r).rescue {
        case _: T  outExcept on =>
          statsRece ver.counter("hydrate althScoreT  out"). ncr()
          Future.value(( sPassAgatha althF lters,  sPassT et althF lters))
        case _ =>
          statsRece ver.counter("hydrate althScoreFa lure"). ncr()
          Future.value(( sPassAgatha althF lters,  sPassT et althF lters))
      }
  }

  overr de def mult Get[K1 <: T et d](ks: Set[K1]): Map[K1, Future[Opt on[TspT et nfo]]] = {
    statsRece ver.counter("t etF eldsStore"). ncr(ks.s ze)
    t etF eldsStore
      .mult Get(ks).mapValues(_.flatMap { _.map { v => toT et nfo(v) }.getOrElse(Future.None) })
  }
}
