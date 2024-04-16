package com.tw ter.t etyp e
package handler

 mport com.tw ter.featuresw c s.v2.FeatureSw chResults
 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.g zmoduck.thr ftscala.AccessPol cy
 mport com.tw ter.g zmoduck.thr ftscala.LabelValue
 mport com.tw ter.g zmoduck.thr ftscala.UserType
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.add  onalf elds.Add  onalF elds._
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.j m ny.t etyp e.NudgeBu lder
 mport com.tw ter.t etyp e.j m ny.t etyp e.NudgeBu lderRequest
 mport com.tw ter.t etyp e. d a. d a
 mport com.tw ter.t etyp e.repos ory.StratoCommun yAccessRepos ory.Commun yAccess
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.serverut l.Dev ceS ceParser
 mport com.tw ter.t etyp e.serverut l.ExtendedT et tadataBu lder
 mport com.tw ter.t etyp e.store._
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.t etyp e.thr ftscala.ent  es.Ent yExtractor
 mport com.tw ter.t etyp e.t ettext._
 mport com.tw ter.t etyp e.ut l.Commun yAnnotat on
 mport com.tw ter.t etyp e.ut l.Commun yUt l
 mport com.tw ter.tw tertext.Regex.{VAL D_URL => UrlPattern}
 mport com.tw ter.tw tertext.Tw terTextParser

case class T etBu lderResult(
  t et: T et,
  user: User,
  createdAt: T  ,
  s ceT et: Opt on[T et] = None,
  s ceUser: Opt on[User] = None,
  parentUser d: Opt on[User d] = None,
   sS lentFa l: Boolean = false,
  geoSearchRequest d: Opt on[GeoSearchRequest d] = None,
   n  alT etUpdateRequest: Opt on[ n  alT etUpdateRequest] = None)

object T etBu lder {
   mport G zmoduckUserCountsUpdat ngStore. sUserT et
   mport PostT et._
   mport Preprocessor._
   mport T etCreateState.{Spam => CreateStateSpam, _}
   mport T etText._
   mport UpstreamFa lure._

  type Type = FutureArrow[PostT etRequest, T etBu lderResult]

  val log: Logger = Logger(getClass)

  pr vate[t ] val _un Mutat on = Future.value(Mutat on.un [Any])
  def Mutat onUn Future[T]: Future[Mutat on[T]] = _un Mutat on.as nstanceOf[Future[Mutat on[T]]]

  case class M ss ngConversat on d( nReplyToT et d: T et d) extends Runt  Except on

  case class TextV s b l y(
    v s bleTextRange: Opt on[TextRange],
    totalTextD splayLength: Offset.D splayUn ,
    v s bleText: Str ng) {
    val  sExtendedT et: Boolean = totalTextD splayLength.to nt > Or g nalMaxD splayLength

    /**
     *  Go ng forward   w ll be mov ng away from quoted-t ets urls  n t et text, but  
     *  have a backwards-compat layer  n T etyp e wh ch adds t  QT url to text to prov de
     *  support for all cl ents to read  n a backwards-compat ble way unt l t y upgrade.
     *
     *  T ets can beco  extended as t  r d splay length can go beyond 140
     *  after add ng t  QT short url. T refore,   are add ng below funct on
     *  to account for legacy formatt ng dur ng read-t   and generate a self-permal nk.
     */
    def  sExtendedW hExtraChars(extraChars:  nt): Boolean =
      totalTextD splayLength.to nt > (Or g nalMaxD splayLength - extraChars)
  }

  /** Max number of users that can be tagged on a s ngle t et */
  val Max d aTagCount = 10

  val Mob le bApp = "oauth:49152"
  val M2App = "oauth:3033294"
  val M5App = "oauth:3033300"

  val TestRateL m UserRole = "stresstest"

  /**
   * T  f elds to fetch for t  user creat ng t  t et.
   */
  val userF elds: Set[UserF eld] =
    Set(
      UserF eld.Prof le,
      UserF eld.Prof leDes gn,
      UserF eld.Account,
      UserF eld.Safety,
      UserF eld.Counts,
      UserF eld.Roles,
      UserF eld.UrlEnt  es,
      UserF eld.Labels
    )

  /**
   * T  f elds to fetch for t  user of t  s ce t et  n a ret et.
   */
  val s ceUserF elds: Set[UserF eld] =
    userF elds + UserF eld.V ew

  /**
   * Converts repos ory except ons  nto an AP -compat ble except on type
   */
  def convertRepoExcept ons[A](
    notFoundState: T etCreateState,
    fa lureHandler: Throwable => Throwable
  ): Part alFunct on[Throwable, St ch[A]] = {
    // st ch.NotFound  s converted to t  suppl ed T etCreateState, wrapped  n T etCreateFa lure
    case NotFound => St ch.except on(T etCreateFa lure.State(notFoundState))
    // OverCapac y except ons should not be translated and should bubble up to t  top
    case ex: OverCapac y => St ch.except on(ex)
    // Ot r except ons are wrapped  n t  suppl ed fa lureHandler
    case ex => St ch.except on(fa lureHandler(ex))
  }

  /**
   * Adapts a UserRepos ory to a Repos ory for look ng up a s ngle user and that
   * fa ls w h an appropr ate T etCreateFa lure  f t  user  s not found.
   */
  def userLookup(userRepo: UserRepos ory.Type): User d => St ch[User] = {
    val opts = UserQueryOpt ons(queryF elds = userF elds, v s b l y = UserV s b l y.All)

    user d =>
      userRepo(UserKey(user d), opts)
        .rescue(convertRepoExcept ons[User](UserNotFound, UserLookupFa lure(_)))
  }

  /**
   * Adapts a UserRepos ory to a Repos ory for look ng up a s ngle user and that
   * fa ls w h an appropr ate T etCreateFa lure  f t  user  s not found.
   */
  def s ceUserLookup(userRepo: UserRepos ory.Type): (User d, User d) => St ch[User] = {
    val opts = UserQueryOpt ons(queryF elds = s ceUserF elds, v s b l y = UserV s b l y.All)

    (user d, forUser d) =>
      userRepo(UserKey(user d), opts.copy(forUser d = So (forUser d)))
        .rescue(convertRepoExcept ons[User](S ceUserNotFound, UserLookupFa lure(_)))
  }

  /**
   * Any f elds that are loaded on t  user v a T etBu lder/Ret etBu lder, but wh ch should not
   * be  ncluded on t  user  n t  async- nsert act ons (such as hoseb rd) should be removed  re.
   *
   * T  w ll  nclude perspect val f elds that  re loaded relat ve to t  user creat ng t  t et.
   */
  def scrubUser nAsync nserts: User => User =
    user => user.copy(v ew = None)

  /**
   * Any f elds that are loaded on t  s ce user v a T etBu lder/Ret etBu lder, but wh ch
   * should not be  ncluded on t  user  n t  async- nsert act ons (such as hoseb rd) should
   * be removed  re.
   *
   * T  w ll  nclude perspect val f elds that  re loaded relat ve to t  user creat ng t  t et.
   */
  def scrubS ceUser nAsync nserts: User => User =
    // currently t  sa  as scrubUser nAsync nserts, could be d fferent  n t  future
    scrubUser nAsync nserts

  /**
   * Any f elds that are loaded on t  s ce t et v a Ret etBu lder, but wh ch should not be
   *  ncluded on t  s ce t etyp e  n t  async- nsert act ons (such as hoseb rd) should
   * be removed  re.
   *
   * T  w ll  nclude perspect val f elds that  re loaded relat ve to t  user creat ng t  t et.
   */
  def scrubS ceT et nAsync nserts: T et => T et =
    t et => t et.copy(perspect ve = None, cards = None, card2 = None)

  /**
   * Adapts a Dev ceS ce to a Repos ory for look ng up a s ngle dev ce-s ce and that
   * fa ls w h an appropr ate T etCreateFa lure  f not found.
   */
  def dev ceS ceLookup(devSrcRepo: Dev ceS ceRepos ory.Type): Dev ceS ceRepos ory.Type =
    app dStr => {
      val result: St ch[Dev ceS ce] =
         f (Dev ceS ceParser. sVal d(app dStr)) {
          devSrcRepo(app dStr)
        } else {
          St ch.except on(NotFound)
        }

      result.rescue(convertRepoExcept ons(Dev ceS ceNotFound, Dev ceS ceLookupFa lure(_)))
    }

  /**
   * C cks:
   *   - that   have all t  user f elds   need
   *   - that t  user  s act ve
   *   - that t y are not a fr ct onless follo r account
   */
  def val dateUser(user: User): Future[Un ] =
     f (user.safety. sEmpty)
      Future.except on(UserSafetyEmptyExcept on)
    else  f (user.prof le. sEmpty)
      Future.except on(UserProf leEmptyExcept on)
    else  f (user.safety.get.deact vated)
      Future.except on(T etCreateFa lure.State(UserDeact vated))
    else  f (user.safety.get.suspended)
      Future.except on(T etCreateFa lure.State(UserSuspended))
    else  f (user.labels.ex sts(_.labels.ex sts(_.labelValue == LabelValue.ReadOnly)))
      Future.except on(T etCreateFa lure.State(CreateStateSpam))
    else  f (user.userType == UserType.Fr ct onless)
      Future.except on(T etCreateFa lure.State(UserNotFound))
    else  f (user.userType == UserType.Soft)
      Future.except on(T etCreateFa lure.State(UserNotFound))
    else  f (user.safety.get.accessPol cy == AccessPol cy.BounceAll ||
      user.safety.get.accessPol cy == AccessPol cy.BounceAllPubl cWr es)
      Future.except on(T etCreateFa lure.State(UserReadonly))
    else
      Future.Un 

  def val dateCommun yReply(
    commun  es: Opt on[Commun  es],
    replyResult: Opt on[ReplyBu lder.Result]
  ): Future[Un ] = {

     f (replyResult.flatMap(_.reply. nReplyToStatus d).nonEmpty) {
      val rootCommun  es = replyResult.flatMap(_.commun y)
      val rootCommun y ds = Commun yUt l.commun y ds(rootCommun  es)
      val replyCommun y ds = Commun yUt l.commun y ds(commun  es)

       f (rootCommun y ds == replyCommun y ds) {
        Future.Un 
      } else {
        Future.except on(T etCreateFa lure.State(Commun yReplyT etNotAllo d))
      }
    } else {
      Future.Un 
    }
  }

  // Project requ re nts do not allow exclus ve t ets to be repl es.
  // All exclus ve t ets must be root t ets.
  def val dateExclus veT etNotRepl es(
    exclus veT etControls: Opt on[Exclus veT etControl],
    replyResult: Opt on[ReplyBu lder.Result]
  ): Future[Un ] = {
    val  s nReplyToT et = replyResult.ex sts(_.reply. nReplyToStatus d. sDef ned)
     f (exclus veT etControls. sDef ned &&  s nReplyToT et) {
      Future.except on(T etCreateFa lure.State(SuperFollows nval dParams))
    } else {
      Future.Un 
    }
  }

  //  nval d para ters for Exclus ve T ets:
  // - Commun y f eld set # T ets can not be both at t  sa  t  .
  def val dateExclus veT etParams(
    exclus veT etControls: Opt on[Exclus veT etControl],
    commun  es: Opt on[Commun  es]
  ): Future[Un ] = {
     f (exclus veT etControls. sDef ned && Commun yUt l.hasCommun y(commun  es)) {
      Future.except on(T etCreateFa lure.State(SuperFollows nval dParams))
    } else {
      Future.Un 
    }
  }

  def val dateTrustedFr endsNotRepl es(
    trustedFr endsControl: Opt on[TrustedFr endsControl],
    replyResult: Opt on[ReplyBu lder.Result]
  ): Future[Un ] = {
    val  s nReplyToT et = replyResult.ex sts(_.reply. nReplyToStatus d. sDef ned)
     f (trustedFr endsControl. sDef ned &&  s nReplyToT et) {
      Future.except on(T etCreateFa lure.State(TrustedFr ends nval dParams))
    } else {
      Future.Un 
    }
  }

  def val dateTrustedFr endsParams(
    trustedFr endsControl: Opt on[TrustedFr endsControl],
    conversat onControl: Opt on[T etCreateConversat onControl],
    commun  es: Opt on[Commun  es],
    exclus veT etControl: Opt on[Exclus veT etControl]
  ): Future[Un ] = {
     f (trustedFr endsControl. sDef ned &&
      (conversat onControl. sDef ned || Commun yUt l.hasCommun y(
        commun  es) || exclus veT etControl. sDef ned)) {
      Future.except on(T etCreateFa lure.State(TrustedFr ends nval dParams))
    } else {
      Future.Un 
    }
  }

  /**
   * C cks t    ghted t et text length us ng tw ter-text, as used by cl ents.
   * T  should ensure that any t et t  cl ent deems val d w ll also be dee d
   * val d by T etyp e.
   */
  def preval dateTextLength(text: Str ng, stats: StatsRece ver): Future[Un ] = {
    val tw terTextConf g = Tw terTextParser.TW TTER_TEXT_DEFAULT_CONF G
    val tw terTextResult = Tw terTextParser.parseT et(text, tw terTextConf g)
    val textTooLong = !tw terTextResult. sVal d && text.length > 0

    Future.w n(textTooLong) {
      val   ghtedLength = tw terTextResult.  ghtedLength
      log.debug(
        s"  ghted length too long.   ghtedLength: $  ghtedLength" +
          s", T et text: '${d ffshow.show(text)}'"
      )
      stats.counter("c ck_  ghted_length/text_too_long"). ncr()
      Future.except on(T etCreateFa lure.State(TextTooLong))
    }
  }

  /**
   * C cks that t  t et text  s ne  r blank nor too long.
   */
  def val dateTextLength(
    text: Str ng,
    v s bleText: Str ng,
    replyResult: Opt on[ReplyBu lder.Result],
    stats: StatsRece ver
  ): Future[Un ] = {
    val utf8Length = Offset.Utf8.length(text)

    def v s bleTextTooLong =
      Offset.D splayUn .length(v s bleText) > Offset.D splayUn (MaxV s ble  ghtedEmoj Length)

    def utf8LengthTooLong =
      utf8Length > Offset.Utf8(MaxUtf8Length)

     f ( sBlank(text)) {
      stats.counter("val date_text_length/text_cannot_be_blank"). ncr()
      Future.except on(T etCreateFa lure.State(TextCannotBeBlank))
    } else  f (replyResult.ex sts(_.replyText sEmpty(text))) {
      stats.counter("val date_text_length/reply_text_cannot_be_blank"). ncr()
      Future.except on(T etCreateFa lure.State(TextCannotBeBlank))
    } else  f (v s bleTextTooLong) {
      // F nal c ck that v s ble text does not exceed MaxV s ble  ghtedEmoj Length
      // characters.
      // preval dateTextLength() does so  port on of val dat on as  ll, most notably
      //   ghted length on raw, unescaped text.
      stats.counter("val date_text_length/text_too_long.v s ble_length_expl c "). ncr()
      log.debug(
        s"Expl c  MaxV s ble  ghtedLength v s ble length c ck fa led. " +
          s"v s bleText: '${d ffshow.show(v s bleText)}' and " +
          s"total text: '${d ffshow.show(text)}'"
      )
      Future.except on(T etCreateFa lure.State(TextTooLong))
    } else  f (utf8LengthTooLong) {
      stats.counter("val date_text_length/text_too_long.utf8_length"). ncr()
      Future.except on(T etCreateFa lure.State(TextTooLong))
    } else {
      stats.stat("val date_text_length/utf8_length").add(utf8Length.to nt)
      Future.Un 
    }
  }

  def getTextV s b l y(
    text: Str ng,
    replyResult: Opt on[ReplyBu lder.Result],
    urlEnt  es: Seq[UrlEnt y],
     d aEnt  es: Seq[ d aEnt y],
    attach ntUrl: Opt on[Str ng]
  ): TextV s b l y = {
    val totalTextLength = Offset.CodePo nt.length(text)
    val totalTextD splayLength = Offset.D splayUn .length(text)

    /**
     * v s bleEnd for mult ple scenar os:
     *
     *  normal t et +  d a - from ndex of  d aEnt y (hydrated from last  d a permal nk)
     *  quote t et +  d a - from ndex of  d aEnt y
     *  repl es +  d a - from ndex of  d aEnt y
     *  normal quote t et - total text length (v s ble text range w ll be None)
     *  t ets w h ot r attach nts (DM deep l nks)
     *  from ndex of t  last URL ent y
     */
    val v s bleEnd =  d aEnt  es. adOpt on
      .map(_.from ndex)
      .orElse(attach ntUrl.flatMap(_ => urlEnt  es.lastOpt on).map(_.from ndex))
      .map(from => (from - 1).max(0)) // for wh espace, unless t re  s none
      .map(Offset.CodePo nt(_))
      .getOrElse(totalTextLength)

    val v s bleStart = replyResult match {
      case So (rr) => rr.v s bleStart.m n(v s bleEnd)
      case None => Offset.CodePo nt(0)
    }

     f (v s bleStart.to nt == 0 && v s bleEnd == totalTextLength) {
      TextV s b l y(
        v s bleTextRange = None,
        totalTextD splayLength = totalTextD splayLength,
        v s bleText = text
      )
    } else {
      val charFrom = v s bleStart.toCodeUn (text)
      val charTo = charFrom.offsetByCodePo nts(text, v s bleEnd - v s bleStart)
      val v s bleText = text.substr ng(charFrom.to nt, charTo.to nt)

      TextV s b l y(
        v s bleTextRange = So (TextRange(v s bleStart.to nt, v s bleEnd.to nt)),
        totalTextD splayLength = totalTextD splayLength,
        v s bleText = v s bleText
      )
    }
  }

  def  sVal dHashtag(ent y: HashtagEnt y): Boolean =
    T etText.codePo ntLength(ent y.text) <= T etText.MaxHashtagLength

  /**
   * Val dates that t  number of var ous ent  es are w h n t  l m s, and t 
   * length of hashtags are w h t  l m .
   */
  def val dateEnt  es(t et: T et): Future[Un ] =
     f (get nt ons(t et).length > T etText.Max nt ons)
      Future.except on(T etCreateFa lure.State( nt onL m Exceeded))
    else  f (getUrls(t et).length > T etText.MaxUrls)
      Future.except on(T etCreateFa lure.State(UrlL m Exceeded))
    else  f (getHashtags(t et).length > T etText.MaxHashtags)
      Future.except on(T etCreateFa lure.State(HashtagL m Exceeded))
    else  f (getCashtags(t et).length > T etText.MaxCashtags)
      Future.except on(T etCreateFa lure.State(CashtagL m Exceeded))
    else  f (getHashtags(t et).ex sts(e => ! sVal dHashtag(e)))
      Future.except on(T etCreateFa lure.State(HashtagLengthL m Exceeded))
    else
      Future.Un 

  /**
   * Update t  user to what   should look l ke after t  t et  s created
   */
  def updateUserCounts(has d a: T et => Boolean): (User, T et) => Future[User] =
    (user: User, t et: T et) => {
      val countAsUserT et =  sUserT et(t et)
      val t etsDelta =  f (countAsUserT et) 1 else 0
      val  d aT etsDelta =  f (countAsUserT et && has d a(t et)) 1 else 0

      Future.value(
        user.copy(
          counts = user.counts.map { counts =>
            counts.copy(
              t ets = counts.t ets + t etsDelta,
               d aT ets = counts. d aT ets.map(_ +  d aT etsDelta)
            )
          }
        )
      )
    }

  def val dateAdd  onalF elds[R]( mpl c  v ew: RequestV ew[R]): FutureEffect[R] =
    FutureEffect[R] { req =>
      v ew
        .add  onalF elds(req)
        .map(t et =>
          unsettableAdd  onalF eld ds(t et) ++ rejectedAdd  onalF eld ds(t et)) match {
        case So (unsettableF eld ds)  f unsettableF eld ds.nonEmpty =>
          Future.except on(
            T etCreateFa lure.State(
               nval dAdd  onalF eld,
              So (unsettableAdd  onalF eld dsError ssage(unsettableF eld ds))
            )
          )
        case _ => Future.Un 
      }
    }

  def val dateT et d aTags(
    stats: StatsRece ver,
    getUser d aTagRateL m : RateL m C cker.GetRema n ng,
    userRepo: UserRepos ory.Opt onal
  ): (T et, Boolean) => Future[Mutat on[T et]] = {
    val userRepoW hStats: UserRepos ory.Opt onal =
      (userKey, queryOpt ons) =>
        userRepo(userKey, queryOpt ons).l ftToTry.map {
          case Return(res @ So (_)) =>
            stats.counter("found"). ncr()
            res
          case Return(None) =>
            stats.counter("not_found"). ncr()
            None
          case Throw(_) =>
            stats.counter("fa led"). ncr()
            None
        }

    (t et: T et, dark: Boolean) => {
      val  d aTags = get d aTagMap(t et)

       f ( d aTags. sEmpty) {
        Mutat onUn Future
      } else {
        getUser d aTagRateL m ((getUser d(t et), dark)).flatMap { rema n ng d aTagCount =>
          val max d aTagCount = math.m n(rema n ng d aTagCount, Max d aTagCount)

          val taggedUser ds =
             d aTags.values.flatten.toSeq.collect {
              case  d aTag( d aTagType.User, So (user d), _, _) => user d
            }.d st nct

          val droppedTagCount = taggedUser ds.s ze - max d aTagCount
           f (droppedTagCount > 0) stats.counter("over_l m _tags"). ncr(droppedTagCount)

          val userQueryOpts =
            UserQueryOpt ons(
              queryF elds = Set(UserF eld. d aV ew),
              v s b l y = UserV s b l y. d aTaggable,
              forUser d = So (getUser d(t et))
            )

          val keys = taggedUser ds.take(max d aTagCount).map(UserKey.by d)
          val keyOpts = keys.map((_, userQueryOpts))

          St ch.run {
            St ch
              .traverse(keyOpts)(userRepoW hStats.tupled)
              .map(_.flatten)
              .map { users =>
                val userMap = users.map(u => u. d -> u).toMap
                val  d aTagsMutat on =
                  Mutat on[Seq[ d aTag]] {  d aTags =>
                    val val d d aTags =
                       d aTags.f lter {
                        case  d aTag( d aTagType.User, So (user d), _, _) =>
                          userMap.get(user d).ex sts(_. d aV ew.ex sts(_.can d aTag))
                        case _ => false
                      }
                    val  nval dCount =  d aTags.s ze - val d d aTags.s ze

                     f ( nval dCount != 0) {
                      stats.counter(" nval d"). ncr( nval dCount)
                      So (val d d aTags)
                    } else {
                      None
                    }
                  }
                T etLenses. d aTagMap.mutat on( d aTagsMutat on.l ftMapValues)
              }
          }
        }
      }
    }
  }

  def val dateCommun y mbersh p(
    commun y mbersh pRepos ory: StratoCommun y mbersh pRepos ory.Type,
    commun yAccessRepos ory: StratoCommun yAccessRepos ory.Type,
    commun  es: Opt on[Commun  es]
  ): Future[Un ] =
    commun  es match {
      case So (Commun  es(Seq(commun y d))) =>
        St ch
          .run {
            commun y mbersh pRepos ory(commun y d).flatMap {
              case true => St ch.value(None)
              case false =>
                commun yAccessRepos ory(commun y d).map {
                  case So (Commun yAccess.Publ c) | So (Commun yAccess.Closed) =>
                    So (T etCreateState.Commun yUserNotAuthor zed)
                  case So (Commun yAccess.Pr vate) | None =>
                    So (T etCreateState.Commun yNotFound)
                }
            }
          }.flatMap {
            case None =>
              Future.Done
            case So (t etCreateState) =>
              Future.except on(T etCreateFa lure.State(t etCreateState))
          }
      case So (Commun  es(commun  es))  f commun  es.length > 1 =>
        // Not allo d to spec fy more than one commun y  D.
        Future.except on(T etCreateFa lure.State(T etCreateState. nval dAdd  onalF eld))
      case _ => Future.Done
    }

  pr vate[t ] val CardUr Sc  Regex = "(? )^(?:card|tombstone):".r

  /**
   *  s t  g ven Str ng a UR  that  s allo d as a card reference
   * w hout a match ng URL  n t  text?
   */
  def hasCardsUr Sc  (ur : Str ng): Boolean =
    CardUr Sc  Regex.f ndPref xMatchOf(ur ). sDef ned

  val  nval dAdd  onalF eldEmptyUrlEnt  es: T etCreateFa lure.State =
    T etCreateFa lure.State(
      T etCreateState. nval dAdd  onalF eld,
      So ("url ent  es are empty")
    )

  val  nval dAdd  onalF eldNonMatch ngUrlAndShortUrl: T etCreateFa lure.State =
    T etCreateFa lure.State(
      T etCreateState. nval dAdd  onalF eld,
      So ("non-match ng url and short url")
    )

  val  nval dAdd  onalF eld nval dUr : T etCreateFa lure.State =
    T etCreateFa lure.State(
      T etCreateState. nval dAdd  onalF eld,
      So (" nval d UR ")
    )

  val  nval dAdd  onalF eld nval dCardUr : T etCreateFa lure.State =
    T etCreateFa lure.State(
      T etCreateState. nval dAdd  onalF eld,
      So (" nval d card UR ")
    )

  type CardReferenceBu lder =
    (T et, UrlShortener.Context) => Future[Mutat on[T et]]

  def cardReferenceBu lder(
    cardReferenceVal dator: CardReferenceVal dat onHandler.Type,
    urlShortener: UrlShortener.Type
  ): CardReferenceBu lder =
    (t et, urlShortenerCtx) => {
      getCardReference(t et) match {
        case So (CardReference(ur )) =>
          for {
            cardUr  <-
               f (hasCardsUr Sc  (ur )) {
                // T   s an expl c  card references that does not
                // need a correspond ng URL  n t  text.
                Future.value(ur )
              } else  f (UrlPattern.matc r(ur ).matc s) {
                // T  card reference  s be ng used to spec fy wh ch URL
                // card to show.   need to ver fy that t  URL  s
                // actually  n t  t et text, or   can be effect vely
                // used to bypass t  t et length l m .
                val urlEnt  es = getUrls(t et)

                 f (urlEnt  es. sEmpty) {
                  // Fa l fast  f t re can't poss bly be a match ng URL ent y
                  Future.except on( nval dAdd  onalF eldEmptyUrlEnt  es)
                } else {
                  // Look for t  URL  n t  expanded URL ent  es.  f
                  //    s present, t n map   to t  t.co shortened
                  // vers on of t  URL.
                  urlEnt  es
                    .collectF rst {
                      case urlEnt y  f urlEnt y.expanded.ex sts(_ == ur ) =>
                        Future.value(urlEnt y.url)
                    }
                    .getOrElse {
                      // T  URL may have been altered w n   was
                      // returned from Talon, such as expand ng a pasted
                      // t.co l nk.  n t  case,   t.co- ze t  l nk and
                      // make sure that t  correspond ng t.co  s present
                      // as a URL ent y.
                      urlShortener((ur , urlShortenerCtx)).flatMap { shortened =>
                         f (urlEnt  es.ex sts(_.url == shortened.shortUrl)) {
                          Future.value(shortened.shortUrl)
                        } else {
                          Future.except on( nval dAdd  onalF eldNonMatch ngUrlAndShortUrl)
                        }
                      }
                    }
                }
              } else {
                Future.except on( nval dAdd  onalF eld nval dUr )
              }

            val datedCardUr  <- cardReferenceVal dator((getUser d(t et), cardUr )).rescue {
              case CardReferenceVal dat onFa ledExcept on =>
                Future.except on( nval dAdd  onalF eld nval dCardUr )
            }
          } y eld {
            T etLenses.cardReference.mutat on(
              Mutat on[CardReference] { cardReference =>
                So (cardReference.copy(cardUr  = val datedCardUr ))
              }.c ckEq.l ftOpt on
            )
          }

        case None =>
          Mutat onUn Future
      }
    }

  def f lter nval dData(
    val dateT et d aTags: (T et, Boolean) => Future[Mutat on[T et]],
    cardReferenceBu lder: CardReferenceBu lder
  ): (T et, PostT etRequest, UrlShortener.Context) => Future[T et] =
    (t et: T et, request: PostT etRequest, urlShortenerCtx: UrlShortener.Context) => {
      Future
        .jo n(
          val dateT et d aTags(t et, request.dark),
          cardReferenceBu lder(t et, urlShortenerCtx)
        )
        .map {
          case ( d aMutat on, cardRefMutat on) =>
             d aMutat on.also(cardRefMutat on).endo(t et)
        }
    }

  def apply(
    stats: StatsRece ver,
    val dateRequest: PostT etRequest => Future[Un ],
    val dateEd : Ed Val dator.Type,
    val dateUser: User => Future[Un ] = T etBu lder.val dateUser,
    val dateUpdateRateL m : RateL m C cker.Val date,
    t et dGenerator: T et dGenerator,
    userRepo: UserRepos ory.Type,
    dev ceS ceRepo: Dev ceS ceRepos ory.Type,
    commun y mbersh pRepo: StratoCommun y mbersh pRepos ory.Type,
    commun yAccessRepo: StratoCommun yAccessRepos ory.Type,
    urlShortener: UrlShortener.Type,
    urlEnt yBu lder: UrlEnt yBu lder.Type,
    geoBu lder: GeoBu lder.Type,
    replyBu lder: ReplyBu lder.Type,
     d aBu lder:  d aBu lder.Type,
    attach ntBu lder: Attach ntBu lder.Type,
    dupl cateT etF nder: Dupl cateT etF nder.Type,
    spamC cker: Spam.C cker[T etSpamRequest],
    f lter nval dData: (T et, PostT etRequest, UrlShortener.Context) => Future[T et],
    updateUserCounts: (User, T et) => Future[User],
    val dateConversat onControl: Conversat onControlBu lder.Val date.Type,
    conversat onControlBu lder: Conversat onControlBu lder.Type,
    val dateT etWr e: T etWr eVal dator.Type,
    nudgeBu lder: NudgeBu lder.Type,
    commun  esVal dator: Commun  esVal dator.Type,
    collabControlBu lder: CollabControlBu lder.Type,
    ed ControlBu lder: Ed ControlBu lder.Type,
    featureSw c s: FeatureSw c s
  ): T etBu lder.Type = {
    val ent yExtractor = Ent yExtractor.mutat onW houtUrls.endo
    val getUser = userLookup(userRepo)
    val getDev ceS ce = dev ceS ceLookup(dev ceS ceRepo)

    // create a tco of t  permal nk for g ven a t et d
    val permal nkShortener = (t et d: T et d, ctx: UrlShortener.Context) =>
      urlShortener((s"https://tw ter.com/ / b/status/$t et d", ctx)).rescue {
        // propagate OverCapac y
        case e: OverCapac y => Future.except on(e)
        // convert any ot r fa lure  nto UrlShorten ngFa lure
        case e => Future.except on(UrlShorten ngFa lure(e))
      }

    def extractGeoSearchRequest d(t etGeoOpt: Opt on[T etCreateGeo]): Opt on[GeoSearchRequest d] =
      for {
        t etGeo <- t etGeoOpt
        geoSearchRequest d <- t etGeo.geoSearchRequest d
      } y eld GeoSearchRequest d(geoSearchRequest d. d)

    def featureSw chResults(user: User, stats: StatsRece ver): Opt on[FeatureSw chResults] =
      Tw terContext()
        .flatMap { v e r =>
          UserV e rRec p ent(user, v e r, stats)
        }.map { rec p ent =>
          featureSw c s.matchRec p ent(rec p ent)
        }

    FutureArrow { request =>
      for {
        () <- val dateRequest(request)

        (t et d, user, devsrc) <- Future.jo n(
          t et dGenerator().rescue { case t => Future.except on(SnowflakeFa lure(t)) },
          St ch.run(getUser(request.user d)),
          St ch.run(getDev ceS ce(request.createdV a))
        )

        () <- val dateUser(user)
        () <- val dateUpdateRateL m ((user. d, request.dark))

        // Feature Sw ch results are calculated once and shared bet en mult ple bu lders
        matc dResults = featureSw chResults(user, stats)

        () <- val dateConversat onControl(
          Conversat onControlBu lder.Val date.Request(
            matc dResults = matc dResults,
            conversat onControl = request.conversat onControl,
             nReplyToT et d = request. nReplyToT et d
          )
        )

        // str p  llegal chars, normal ze newl nes, collapse blank l nes, etc.
        text = preprocessText(request.text)

        () <- preval dateTextLength(text, stats)

        attach ntResult <- attach ntBu lder(
          Attach ntBu lderRequest(
            t et d = t et d,
            user = user,
             d aUpload ds = request. d aUpload ds,
            cardReference = request.add  onalF elds.flatMap(_.cardReference),
            attach ntUrl = request.attach ntUrl,
            remoteHost = request.remoteHost,
            darkTraff c = request.dark,
            dev ceS ce = devsrc
          )
        )

        // updated text w h appended attach nt url,  f any.
        text <- Future.value(
          attach ntResult.attach ntUrl match {
            case None => text
            case So (url) => s"$text $url"
          }
        )

        spamResult <- spamC cker(
          T etSpamRequest(
            t et d = t et d,
            user d = request.user d,
            text = text,
             d aTags = request.add  onalF elds.flatMap(_. d aTags),
            safety taData = request.safety taData,
             nReplyToT et d = request. nReplyToT et d,
            quotedT et d = attach ntResult.quotedT et.map(_.t et d),
            quotedT etUser d = attach ntResult.quotedT et.map(_.user d)
          )
        )

        safety = user.safety.get
        createdAt = Snowflake d(t et d).t  

        urlShortenerCtx = UrlShortener.Context(
          t et d = t et d,
          user d = user. d,
          createdAt = createdAt,
          userProtected = safety. sProtected,
          cl entApp d = devsrc.cl entApp d,
          remoteHost = request.remoteHost,
          dark = request.dark
        )

        replyRequest = ReplyBu lder.Request(
          author d = request.user d,
          authorScreenNa  = user.prof le.map(_.screenNa ).get,
           nReplyToT et d = request. nReplyToT et d,
          t etText = text,
          prepend mpl c  nt ons = request.autoPopulateReply tadata,
          enableT etToNarrowcast ng = request.enableT etToNarrowcast ng,
          excludeUser ds = request.excludeReplyUser ds.getOrElse(N l),
          spamResult = spamResult,
          batchMode = request.trans entContext.flatMap(_.batchCompose)
        )

        replyResult <- replyBu lder(replyRequest)
        replyOpt = replyResult.map(_.reply)

        replyConversat on d <- replyResult match {
          case So (r)  f r.reply. nReplyToStatus d.nonEmpty =>
            r.conversat on d match {
              case None =>
                // Throw t  spec f c except on to make   eas er to
                // count how often   h  t  corner case.
                Future.except on(M ss ngConversat on d(r.reply. nReplyToStatus d.get))
              case conversat on dOpt => Future.value(conversat on dOpt)
            }
          case _ => Future.value(None)
        }

        // Val date that t  current user can reply to t  conversat on, based on
        // t  conversat on's Conversat onControl.
        // Note: currently   only val date conversat on controls access on repl es,
        // t refore   use t  conversat on d from t   nReplyToStatus.
        // Val date that t  exclus ve t et control opt on  s only used by allo d users.
        () <- val dateT etWr e(
          T etWr eVal dator.Request(
            replyConversat on d,
            request.user d,
            request.exclus veT etControlOpt ons,
            replyResult.flatMap(_.exclus veT etControl),
            request.trustedFr endsControlOpt ons,
            replyResult.flatMap(_.trustedFr endsControl),
            attach ntResult.quotedT et,
            replyResult.flatMap(_.reply. nReplyToStatus d),
            replyResult.flatMap(_.ed Control),
            request.ed Opt ons
          )
        )

        convo d = replyConversat on d match {
          case So (replyConvo d) => replyConvo d
          case None =>
            // T   s a root t et, so t  t et  d  s t  conversat on  d.
            t et d
        }

        () <- nudgeBu lder(
          NudgeBu lderRequest(
            text = text,
             nReplyToT et d = replyOpt.flatMap(_. nReplyToStatus d),
            conversat on d =  f (convo d == t et d) None else So (convo d),
            hasQuotedT et = attach ntResult.quotedT et.nonEmpty,
            nudgeOpt ons = request.nudgeOpt ons,
            t et d = So (t et d),
          )
        )

        // updated text w h  mpl c  reply  nt ons  nserted,  f any
        text <- Future.value(
          replyResult.map(_.t etText).getOrElse(text)
        )

        // updated text w h urls replaced w h t.cos
        ((text, urlEnt  es), (geoCoords, place dOpt)) <- Future.jo n(
          urlEnt yBu lder((text, urlShortenerCtx))
            .map {
              case (text, urlEnt  es) =>
                UrlEnt yBu lder.updateTextAndUrls(text, urlEnt  es)(part alHtmlEncode)
            },
           f (request.geo. sEmpty)
            Future.value((None, None))
          else
            geoBu lder(
              GeoBu lder.Request(
                request.geo.get,
                user.account.map(_.geoEnabled).getOrElse(false),
                user.account.map(_.language).getOrElse("en")
              )
            ).map(r => (r.geoCoord nates, r.place d))
        )

        // updated text w h tra l ng  d a url
         d aBu lder.Result(text,  d aEnt  es,  d aKeys) <-
          request. d aUpload ds.getOrElse(N l) match {
            case N l => Future.value( d aBu lder.Result(text, N l, N l))
            case  ds =>
               d aBu lder(
                 d aBu lder.Request(
                   d aUpload ds =  ds,
                  text = text,
                  t et d = t et d,
                  user d = user. d,
                  userScreenNa  = user.prof le.get.screenNa ,
                   sProtected = user.safety.get. sProtected,
                  createdAt = createdAt,
                  dark = request.dark,
                  product tadata = request. d a tadata.map(_.toMap)
                )
              )
          }

        () <- Future.w n(!request.dark) {
          val req nfo =
            Dupl cateT etF nder.Request nfo.fromPostT etRequest(request, text)

          dupl cateT etF nder(req nfo).flatMap {
            case None => Future.Un 
            case So (dupl cate d) =>
              log.debug(s"t  l ne_dupl cate_c ck_fa led:$dupl cate d")
              Future.except on(T etCreateFa lure.State(T etCreateState.Dupl cate))
          }
        }

        textV s b l y = getTextV s b l y(
          text = text,
          replyResult = replyResult,
          urlEnt  es = urlEnt  es,
           d aEnt  es =  d aEnt  es,
          attach ntUrl = attach ntResult.attach ntUrl
        )

        () <- val dateTextLength(
          text = text,
          v s bleText = textV s b l y.v s bleText,
          replyResult = replyResult,
          stats = stats
        )

        commun  es =
          request.add  onalF elds
            .flatMap(Commun yAnnotat on.add  onalF eldsToCommun y Ds)
            .map( ds => Commun  es(commun y ds =  ds))

        rootExclus veControls = request.exclus veT etControlOpt ons.map { _ =>
          Exclus veT etControl(request.user d)
        }

        () <- val dateExclus veT etNotRepl es(rootExclus veControls, replyResult)
        () <- val dateExclus veT etParams(rootExclus veControls, commun  es)

        replyExclus veControls = replyResult.flatMap(_.exclus veT etControl)

        // T  user d  s pulled off of t  request rat r than be ng suppl ed
        // v a t  Exclus veT etControlOpt ons because add  onal f elds
        // can be set by cl ents to conta n any value t y want.
        // T  could  nclude user ds that don't match t  r actual user d.
        // Only one of replyResult or request.exclus veT etControlOpt ons w ll be def ned.
        exclus veT etControl = replyExclus veControls.orElse(rootExclus veControls)

        rootTrustedFr endsControl = request.trustedFr endsControlOpt ons.map { opt ons =>
          TrustedFr endsControl(opt ons.trustedFr endsL st d)
        }

        () <- val dateTrustedFr endsNotRepl es(rootTrustedFr endsControl, replyResult)
        () <- val dateTrustedFr endsParams(
          rootTrustedFr endsControl,
          request.conversat onControl,
          commun  es,
          exclus veT etControl
        )

        replyTrustedFr endsControl = replyResult.flatMap(_.trustedFr endsControl)

        trustedFr endsControl = replyTrustedFr endsControl.orElse(rootTrustedFr endsControl)

        collabControl <- collabControlBu lder(
          CollabControlBu lder.Request(
            collabControlOpt ons = request.collabControlOpt ons,
            replyResult = replyResult,
            commun  es = commun  es,
            trustedFr endsControl = trustedFr endsControl,
            conversat onControl = request.conversat onControl,
            exclus veT etControl = exclus veT etControl,
            user d = request.user d
          ))

         sCollab nv at on = collabControl. sDef ned && (collabControl.get match {
          case CollabControl.Collab nv at on(_: Collab nv at on) => true
          case _ => false
        })

        coreData = T etCoreData(
          user d = request.user d,
          text = text,
          createdAtSecs = createdAt. nSeconds,
          createdV a = devsrc. nternalNa ,
          reply = replyOpt,
          hasTakedown = safety.hasTakedown,
          //   want to nullcast commun y t ets and Collab nv at ons
          // T  w ll d sable t et fanout to follo rs' ho  t  l nes,
          // and f lter t  t ets from appear ng from t  t eter's prof le
          // or search results for t  t eter's t ets.
          nullcast =
            request.nullcast || Commun yUt l.hasCommun y(commun  es) ||  sCollab nv at on,
          narrowcast = request.narrowcast,
          nsfwUser = request.poss blySens  ve.getOrElse(safety.nsfwUser),
          nsfwAdm n = safety.nsfwAdm n,
          track ng d = request.track ng d,
          place d = place dOpt,
          coord nates = geoCoords,
          conversat on d = So (convo d),
          // Set has d a to true  f   know that t re  s  d a,
          // and leave   unknown  f not, so that   w ll be
          // correctly set for pasted  d a.
          has d a =  f ( d aEnt  es.nonEmpty) So (true) else None
        )

        t et = T et(
           d = t et d,
          coreData = So (coreData),
          urls = So (urlEnt  es),
           d a = So ( d aEnt  es),
           d aKeys =  f ( d aKeys.nonEmpty) So ( d aKeys) else None,
          contr butor = getContr butor(request.user d),
          v s bleTextRange = textV s b l y.v s bleTextRange,
          selfThread tadata = replyResult.flatMap(_.selfThread tadata),
          d rectedAtUser tadata = replyResult.map(_.d rectedAt tadata),
          composerS ce = request.composerS ce,
          quotedT et = attach ntResult.quotedT et,
          exclus veT etControl = exclus veT etControl,
          trustedFr endsControl = trustedFr endsControl,
          collabControl = collabControl,
          noteT et = request.noteT etOpt ons.map(opt ons =>
            NoteT et(opt ons.noteT et d, opt ons. sExpandable))
        )

        ed Control <- ed ControlBu lder(
          Ed ControlBu lder.Request(
            postT etRequest = request,
            t et = t et,
            matc dResults = matc dResults
          )
        )

        t et <- Future.value(t et.copy(ed Control = ed Control))

        t et <- Future.value(ent yExtractor(t et))

        () <- val dateEnt  es(t et)

        t et <- {
          val cctlRequest =
            Conversat onControlBu lder.Request.fromT et(
              t et,
              request.conversat onControl,
              request.noteT etOpt ons.flatMap(_. nt onedUser ds))
          St ch.run(conversat onControlBu lder(cctlRequest)).map { conversat onControl =>
            t et.copy(conversat onControl = conversat onControl)
          }
        }

        t et <- Future.value(
          setAdd  onalF elds(t et, request.add  onalF elds)
        )
        () <- val dateCommun y mbersh p(commun y mbersh pRepo, commun yAccessRepo, commun  es)
        () <- val dateCommun yReply(commun  es, replyResult)
        () <- commun  esVal dator(
          Commun  esVal dator.Request(matc dResults, safety. sProtected, commun  es))

        t et <- Future.value(t et.copy(commun  es = commun  es))

        t et <- Future.value(
          t et.copy(underly ngCreat vesConta ner d = request.underly ngCreat vesConta ner d)
        )

        // For certa n t ets   want to wr e a self-permal nk wh ch  s used to generate mod f ed
        // t et text for legacy cl ents that conta ns a l nk. NOTE: t  permal nk  s for
        // t  t et be ng created -   also create permal nks for related t ets furt r down
        // e.g.  f t  t et  s an ed ,   m ght create a permal nk for t   n  al t et as  ll
        t et <- {
          val  sBeyond140 = textV s b l y. sExtendedW hExtraChars(attach ntResult.extraChars)
          val  sEd T et = request.ed Opt ons. sDef ned
          val  sM xed d a =  d a. sM xed d a( d aEnt  es)
          val  sNoteT et = request.noteT etOpt ons. sDef ned

           f ( sBeyond140 ||  sEd T et ||  sM xed d a ||  sNoteT et)
            permal nkShortener(t et d, urlShortenerCtx)
              .map { selfPermal nk =>
                t et.copy(
                  selfPermal nk = So (selfPermal nk),
                  extendedT et tadata = So (ExtendedT et tadataBu lder(t et, selfPermal nk))
                )
              }
          else {
            Future.value(t et)
          }
        }

        // W n an ed  t et  s created   have to update so   nformat on on t 
        //  n  al t et, t  object stores  nfo about those updates for use
        //  n t  t et  nsert store.
        //   update t  ed Control for each ed  t et and for t  f rst ed  t et
        //   update t  self permal nk.
         n  alT etUpdateRequest: Opt on[ n  alT etUpdateRequest] <- ed Control match {
          case So (Ed Control.Ed (ed )) =>
            //  dent f es t  f rst ed  of an  n  al t et
            val  sF rstEd  =
              request.ed Opt ons.map(_.prev ousT et d).conta ns(ed . n  alT et d)

            // A potent al permal nk for t  t et be ng created's  n  al t et
            val selfPermal nkFor n  al: Future[Opt on[ShortenedUrl]] =
               f ( sF rstEd ) {
                // `t et`  s t  f rst ed  of an  n  al t et, wh ch  ans
                //   need to wr e a self permal nk.   create    re  n
                // T etBu lder and pass   through to t  t et store to
                // be wr ten to t   n  al t et.
                permal nkShortener(ed . n  alT et d, urlShortenerCtx).map(So (_))
              } else {
                Future.value(None)
              }

            selfPermal nkFor n  al.map { l nk =>
              So (
                 n  alT etUpdateRequest(
                   n  alT et d = ed . n  alT et d,
                  ed T et d = t et. d,
                  selfPermal nk = l nk
                ))
            }

          // T   s not an ed  t   s t   n  al t et - so t re are no  n  al
          // t et updates
          case _ => Future.value(None)
        }

        t et <- f lter nval dData(t et, request, urlShortenerCtx)

        () <- val dateEd (t et, request.ed Opt ons)

        user <- updateUserCounts(user, t et)

      } y eld {
        T etBu lderResult(
          t et,
          user,
          createdAt,
           sS lentFa l = spamResult == Spam.S lentFa l,
          geoSearchRequest d = extractGeoSearchRequest d(request.geo),
           n  alT etUpdateRequest =  n  alT etUpdateRequest
        )
      }
    }
  }
}
