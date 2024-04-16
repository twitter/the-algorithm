package com.tw ter.t etyp e
package handler

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.T etCreateFa lure
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.serverut l.Except onCounter
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.t etyp e.t ettext.Offset
 mport com.tw ter.tw tertext.Extractor
 mport scala.annotat on.ta lrec
 mport scala.collect on.JavaConverters._
 mport scala.collect on.mutable
 mport scala.ut l.control.NoStackTrace

object ReplyBu lder {
  pr vate val extractor = new Extractor
  pr vate val  nReplyToT etNotFound =
    T etCreateFa lure.State(T etCreateState. nReplyToT etNotFound)

  case class Request(
    author d: User d,
    authorScreenNa : Str ng,
     nReplyToT et d: Opt on[T et d],
    t etText: Str ng,
    prepend mpl c  nt ons: Boolean,
    enableT etToNarrowcast ng: Boolean,
    excludeUser ds: Seq[User d],
    spamResult: Spam.Result,
    batchMode: Opt on[BatchComposeMode])

  /**
   * T  case class conta ns t  f elds that are shared bet en legacy and s mpl f ed repl es.
   */
  case class BaseResult(
    reply: Reply,
    conversat on d: Opt on[Conversat on d],
    selfThread tadata: Opt on[SelfThread tadata],
    commun y: Opt on[Commun  es] = None,
    exclus veT etControl: Opt on[Exclus veT etControl] = None,
    trustedFr endsControl: Opt on[TrustedFr endsControl] = None,
    ed Control: Opt on[Ed Control] = None) {
    // Creates a Result by prov d ng t  f elds that d ffer bet en legacy and s mpl f ed repl es.
    def toResult(
      t etText: Str ng,
      d rectedAt tadata: D rectedAtUser tadata,
      v s bleStart: Offset.CodePo nt = Offset.CodePo nt(0),
    ): Result =
      Result(
        reply,
        t etText,
        d rectedAt tadata,
        conversat on d,
        selfThread tadata,
        v s bleStart,
        commun y,
        exclus veT etControl,
        trustedFr endsControl,
        ed Control
      )
  }

  /**
   * @param reply              t  Reply object to  nclude  n t  t et.
   * @param t etText          updated t et text wh ch may  nclude prepended at- nt ons, tr m d
   * @param d rectedAt tadata see D rectedAtHydrator for usage.
   * @param conversat on d     conversat on  d to ass gn to t  t et.
   * @param selfThread tadata returns t  result of `SelfThreadBu lder`
   * @param v s bleStart       offset  nto `t etText` separat ng h deable at- nt ons from t 
   *                           v s ble text.
   */
  case class Result(
    reply: Reply,
    t etText: Str ng,
    d rectedAt tadata: D rectedAtUser tadata,
    conversat on d: Opt on[Conversat on d] = None,
    selfThread tadata: Opt on[SelfThread tadata] = None,
    v s bleStart: Offset.CodePo nt = Offset.CodePo nt(0),
    commun y: Opt on[Commun  es] = None,
    exclus veT etControl: Opt on[Exclus veT etControl] = None,
    trustedFr endsControl: Opt on[TrustedFr endsControl] = None,
    ed Control: Opt on[Ed Control] = None) {

    /**
     * @param f nalText f nal t et text after any server-s de add  ons.
     * @return true  ff t  f nal t et text cons sts exclus vely of a h dden reply  nt on pref x.
     *         W n t  happens t re's no content to t  reply and thus t  t et creat on should
     *         fa l.
     */
    def replyText sEmpty(f nalText: Str ng): Boolean = {

      // Length of t  t et text or g nally output v a ReplyBu lder.Result before server-s de
      // add  ons (e.g.  d a, quoted-t et URLs)
      val or gTextLength = Offset.CodePo nt.length(t etText)

      // Length of t  t et text after server-s de add  ons.
      val f nalTextLength = Offset.CodePo nt.length(f nalText)

      val pref xWasEnt reText = or gTextLength == v s bleStart
      val textLenUnchanged = or gTextLength == f nalTextLength

      pref xWasEnt reText && textLenUnchanged
    }
  }

  type Type = Request => Future[Opt on[Result]]

  pr vate object  nval dUserExcept on extends NoStackTrace

  /**
   * A user  D and screen na  used for bu ld ng repl es.
   */
  pr vate case class User( d: User d, screenNa : Str ng)

  /**
   * Captures t   n-reply-to t et,  s author, and  f t  user  s attempt ng to reply to a
   * ret et, t n that ret et and  s author.
   */
  pr vate case class ReplyS ce(
    srcT et: T et,
    srcUser: User,
    ret et: Opt on[T et] = None,
    rtUser: Opt on[User] = None) {
    pr vate val photoTaggedUsers: Seq[User] =
      srcT et. d aTags
        .map(_.tagMap.values.flatten)
        .getOrElse(N l)
        .map(toUser)
        .toSeq

    pr vate def toUser(mt:  d aTag): User =
      mt match {
        case  d aTag(_, So ( d), So (screenNa ), _) => User( d, screenNa )
        case _ => throw  nval dUserExcept on
      }

    pr vate def toUser(e:  nt onEnt y): User =
      e match {
        case  nt onEnt y(_, _, screenNa , So ( d), _, _) => User( d, screenNa )
        case _ => throw  nval dUserExcept on
      }

    pr vate def toUser(d: D rectedAtUser) = User(d.user d, d.screenNa )

    def allCardUsers(authorUser: User, cardUsersF nder: CardUsersF nder.Type): Future[Set[User d]] =
      St ch.run(
        cardUsersF nder(
          CardUsersF nder.Request(
            cardReference = getCardReference(srcT et),
            urls = getUrls(srcT et).map(_.url),
            perspect veUser d = authorUser. d
          )
        )
      )

    def srcT et nt onedUsers: Seq[User] = get nt ons(srcT et).map(toUser)

    pr vate tra  ReplyType {

      val allExcludedUser ds: Set[User d]

      def d rectedAt: Opt on[User]
      def requ redText nt on: Opt on[User]

      def  sExcluded(u: User): Boolean = allExcludedUser ds.conta ns(u. d)

      def bu ldPref x(ot r nt ons: Seq[User], max mpl c s:  nt): Str ng = {
        val seen = new mutable.HashSet[User d]
        seen ++= allExcludedUser ds
        // Never exclude t  requ red  nt on
        seen --= requ redText nt on.map(_. d)

        (requ redText nt on.toSeq ++ ot r nt ons)
          .f lter(u => seen.add(u. d))
          .take(max mpl c s.max(requ redText nt on.s ze))
          .map(u => s"@${u.screenNa }")
          .mkStr ng(" ")
      }
    }

    pr vate case class SelfReply(
      allExcludedUser ds: Set[User d],
      enableT etToNarrowcast ng: Boolean)
        extends ReplyType {

      pr vate def srcT etD rectedAt: Opt on[User] = getD rectedAtUser(srcT et).map(toUser)

      overr de def d rectedAt: Opt on[User] =
         f (!enableT etToNarrowcast ng) None
        else Seq.concat(rtUser, srcT etD rectedAt).f nd(! sExcluded(_))

      overr de def requ redText nt on: Opt on[User] =
        // Make sure t  d rectedAt user  s  n t  text to avo d confus on
        d rectedAt
    }

    pr vate case class BatchSubsequentReply(allExcludedUser ds: Set[User d]) extends ReplyType {

      overr de def d rectedAt: Opt on[User] = None

      overr de def requ redText nt on: Opt on[User] = None

      overr de def bu ldPref x(ot r nt ons: Seq[User], max mpl c s:  nt): Str ng = ""
    }

    pr vate case class RegularReply(
      allExcludedUser ds: Set[User d],
      enableT etToNarrowcast ng: Boolean)
        extends ReplyType {

      overr de def d rectedAt: Opt on[User] =
        So (srcUser)
          .f lterNot( sExcluded)
          .f lter(_ => enableT etToNarrowcast ng)

      overr de def requ redText nt on: Opt on[User] =
        //  nclude t  s ce t et's author as a  nt on  n t  reply, even  f t  reply  s not
        // narrowcasted to that user.  All non-self-reply t ets requ re t   nt on.
        So (srcUser)
    }

    /**
     * Computes an  mpl c   nt on pref x to add to t  t et text as  ll as any d rected-at user.
     *
     * T  f rst  mpl c   nt on  s t  s ce-t et's author unless t  reply  s a self-reply,  n
     * wh ch case    n r s t  D rectedAtUser from t  s ce t et, though t  current author  s
     * never added.  T   nt on,  f   ex sts,  s t  only  nt on that may be used to d rect-at a
     * user and  s t  user that ends up  n D rectedAtUser tadata.   f t  user repl ed to a
     * ret et and t  reply doesn't expl c ly  nt on t  ret et author, t n t  ret et author
     * w ll be next, follo d by s ce t et  nt ons and s ce t et photo-tagged users.
     *
     * Users  n excludedScreenNa s or g nate from t  PostT etRequest and are f ltered out of any
     * non-lead ng  nt on.
     *
     * Note on max mpl c s:
     * T   thod returns at most 'max mpl c s'  nt ons unless 'max mpl c s'  s 0 and a
     * d rected-at  nt on  s requ red,  n wh ch case   returns 1.   f t  happens t  reply may
     * fa l downstream val dat on c cks (e.g. T etBu lder).  W h 280 v s ble character l m   's
     * t oret cally poss ble to expl c ly  nt on 93 users (280 / 3) but t  bug shouldn't really
     * be an  ssue because:
     * 1.) Most repl es don't have 50 expl c   nt ons
     * 2.) TOO-cl ents have sw c d to batchMode=Subsequent for self-repl es wh ch d sable
      s ce t et's d rected-at user  n r ance
     * 3.) Requests rarely are rejected due to  nt on_l m _exceeded
     *  f t  beco s a problem   could reopen t   nt on l m  d scuss on, spec f cally  f t 
     * backend should allow 51 wh le t  expl c  l m  rema ns at 50.
     *
     * Note on batchMode:
     *  mpl c   nt on pref x w ll be empty str ng  f batchMode  s BatchSubsequent. T   s to
     * support batch composer.
     */
    def  mpl c  nt onPref xAndDAU(
      max mpl c s:  nt,
      excludedUsers: Seq[User],
      author: User,
      enableT etToNarrowcast ng: Boolean,
      batchMode: Opt on[BatchComposeMode]
    ): (Str ng, Opt on[User]) = {
      def allExcludedUser ds =
        (excludedUsers ++ Seq(author)).map(_. d).toSet

      val replyType =
         f (author. d == srcUser. d) {
           f (batchMode.conta ns(BatchComposeMode.BatchSubsequent)) {
            BatchSubsequentReply(allExcludedUser ds)
          } else {
            SelfReply(allExcludedUser ds, enableT etToNarrowcast ng)
          }
        } else {
          RegularReply(allExcludedUser ds, enableT etToNarrowcast ng)
        }

      val pref x =
        replyType.bu ldPref x(
          ot r nt ons = L st.concat(rtUser, srcT et nt onedUsers, photoTaggedUsers),
          max mpl c s = max mpl c s
        )

      (pref x, replyType.d rectedAt)
    }

    /**
     * F nds t  longest poss ble pref x of wh espace separated @- nt ons, restr cted to
     * @- nt ons that are der ved from t  reply cha n.
     */
    def h deablePref x(
      text: Str ng,
      cardUsers: Seq[User],
      expl c  nt ons: Seq[Extractor.Ent y]
    ): Offset.CodePo nt = {
      val allo d nt ons =
        (srcT et nt onedUsers.toSet + srcUser ++ rtUser.toSet ++ photoTaggedUsers ++ cardUsers)
          .map(_.screenNa .toLo rCase)
      val len = Offset.CodeUn .length(text)

      // To allow NO-BREAK SPACE' (U+00A0)  n t  pref x need . sSpaceChar
      def  sWh espace(c: Char) = c. sWh espace || c. sSpaceChar

      @ta lrec
      def sk pWs(offset: Offset.CodeUn ): Offset.CodeUn  =
         f (offset == len || ! sWh espace(text.charAt(offset.to nt))) offset
        else sk pWs(offset. ncr)

      @ta lrec
      def go(offset: Offset.CodeUn ,  nt ons: Stream[Extractor.Ent y]): Offset.CodeUn  =
         f (offset == len) offset
        else {
           nt ons match {
            //  f   are at t  next  nt on, and    s allo d, sk p past and recurse
            case next #:: ta l  f next.getStart == offset.to nt =>
               f (!allo d nt ons.conta ns(next.getValue.toLo rCase)) offset
              else go(sk pWs(Offset.CodeUn (next.getEnd)), ta l)
            //   found non- nt on text
            case _ => offset
          }
        }

      go(Offset.CodeUn (0), expl c  nt ons.toStream).toCodePo nt(text)
    }
  }

  pr vate def replyToUser(user: User,  nReplyToStatus d: Opt on[T et d] = None): Reply =
    Reply(
       nReplyToUser d = user. d,
       nReplyToScreenNa  = So (user.screenNa ),
       nReplyToStatus d =  nReplyToStatus d
    )

  /**
   * A bu lder that generates reply from ` nReplyToT et d` or t et text
   *
   * T re are two k nds of "reply":
   * 1. reply to t et, wh ch  s generated from ` nReplyToT et d`.
   *
   * A val d reply-to-t et sat sf es t  follow ng cond  ons:
   * 1). t  t et that  s  n-reply-to ex sts (and  s v s ble to t  user creat ng t  t et)
   * 2). t  author of t   n-reply-to t et  s  nt oned anyw re  n t  t et, or
   *     t   s a t et that  s  n reply to t  author's own t et
   *
   * 2. reply to user,  s generated w n t  t et text starts w h @user_na .  T   s only
   * attempted  f PostT etRequest.enableT etToNarrowcast ng  s true (default).
   */
  def apply(
    user dent yRepo: User dent yRepos ory.Type,
    t etRepo: T etRepos ory.Opt onal,
    replyCardUsersF nder: CardUsersF nder.Type,
    selfThreadBu lder: SelfThreadBu lder,
    relat onsh pRepo: Relat onsh pRepos ory.Type,
    un nt onedEnt  esRepo: Un nt onedEnt  esRepos ory.Type,
    enableRemoveUn nt oned mpl c s: Gate[Un ],
    stats: StatsRece ver,
    max nt ons:  nt
  ): Type = {
    val except onCounters = Except onCounter(stats)
    val modeScope = stats.scope("mode")
    val compatModeCounter = modeScope.counter("compat")
    val s mpleModeCounter = modeScope.counter("s mple")

    def getUser(key: UserKey): Future[Opt on[User]] =
      St ch.run(
        user dent yRepo(key)
          .map( dent => User( dent. d,  dent.screenNa ))
          .l ftNotFoundToOpt on
      )

    def getUsers(user ds: Seq[User d]): Future[Seq[ReplyBu lder.User]] =
      St ch.run(
        St ch
          .traverse(user ds)( d => user dent yRepo(UserKey( d)).l ftNotFoundToOpt on)
          .map(_.flatten)
          .map {  dent  es =>  dent  es.map {  dent => User( dent. d,  dent.screenNa ) } }
      )

    val t etQuery ncludes =
      T etQuery. nclude(
        t etF elds = Set(
          T et.CoreDataF eld. d,
          T et.CardReferenceF eld. d,
          T et.Commun  esF eld. d,
          T et. d aTagsF eld. d,
          T et. nt onsF eld. d,
          T et.UrlsF eld. d,
          T et.Ed ControlF eld. d
        ) ++ selfThreadBu lder.requ redReplyS ceF elds.map(_. d)
      )

    def t etQueryOpt ons(forUser d: User d) =
      T etQuery.Opt ons(
        t etQuery ncludes,
        forUser d = So (forUser d),
        enforceV s b l yF lter ng = true
      )

    def getT et(t et d: T et d, forUser d: User d): Future[Opt on[T et]] =
      St ch.run(t etRepo(t et d, t etQueryOpt ons(forUser d)))

    def c ckBlockRelat onsh p(author d: User d, result: Result): Future[Un ] = {
      val  nReplyToBlocksT eter =
        Relat onsh pKey.blocks(
          s ce d = result.reply. nReplyToUser d,
          dest nat on d = author d
        )

      St ch.run(relat onsh pRepo( nReplyToBlocksT eter)).flatMap {
        case true => Future.except on( nReplyToT etNotFound)
        case false => Future.Un 
      }
    }

    def c ck P Pol cy(request: Request, reply: Reply): Future[Un ] = {
       f (request.spamResult == Spam.D sabledBy p Pol cy) {
        Future.except on(Spam.D sabledBy p Fa lure(reply. nReplyToScreenNa ))
      } else {
        Future.Un 
      }
    }

    def getUn nt onedUsers(replyS ce: ReplyS ce): Future[Seq[User d]] = {
       f (enableRemoveUn nt oned mpl c s()) {
        val srcD rectedAt = replyS ce.srcT et.d rectedAtUser tadata.flatMap(_.user d)
        val srcT et nt ons = replyS ce.srcT et. nt ons.getOrElse(N l).flatMap(_.user d)
        val  dsToC ck = srcT et nt ons ++ srcD rectedAt

        val conversat on d = replyS ce.srcT et.coreData.flatMap(_.conversat on d)
        conversat on d match {
          case So (c d)  f  dsToC ck.nonEmpty =>
            stats.counter("un nt oned_ mpl c s_c ck"). ncr()
            St ch
              .run(un nt onedEnt  esRepo(c d,  dsToC ck)).l ftToTry.map {
                case Return(So (un nt onedUser ds)) =>
                  un nt onedUser ds
                case _ => Seq[User d]()
              }
          case _ => Future.N l

        }
      } else {
        Future.N l
      }
    }

    /**
     * Constructs a `ReplyS ce` for t  g ven `t et d`, wh ch captures t  s ce t et to be
     * repl ed to,  s author, and  f `t et d`  s for a ret et of t  s ce t et, t n also
     * that ret et and  s author.   f t  s ce t et (or a ret et of  ), or a correspond ng
     * author, can't be found or  sn't v s ble to t  repl er, t n ` nReplyToT etNotFound`  s
     * thrown.
     */
    def getReplyS ce(t et d: T et d, forUser d: User d): Future[ReplyS ce] =
      for {
        t et <- getT et(t et d, forUser d).flatMap {
          case None => Future.except on( nReplyToT etNotFound)
          case So (t) => Future.value(t)
        }

        user <- getUser(UserKey(getUser d(t et))).flatMap {
          case None => Future.except on( nReplyToT etNotFound)
          case So (u) => Future.value(u)
        }

        res <- getShare(t et) match {
          case None => Future.value(ReplyS ce(t et, user))
          case So (share) =>
            //  f t  user  s reply ng to a ret et, f nd t  ret et s ce t et,
            // t n update w h t  ret et and author.
            getReplyS ce(share.s ceStatus d, forUser d)
              .map(_.copy(ret et = So (t et), rtUser = So (user)))
        }
      } y eld res

    /**
     * Computes a `Result` for t  reply-to-t et case.   f ` nReplyToT et d`  s for a ret et,
     * t  reply w ll be computed aga nst t  s ce t et.   f `prepend mpl c  nt ons`  s true
     * and s ce t et can't be found or  sn't v s ble to repl er, t n t   thod w ll return
     * a ` nReplyToT etNotFound` fa lure.   f `prepend mpl c  nt ons`  s false, t n t  reply
     * text must e  r  nt on t  s ce t et user, or   must be a reply to self;  f both of
     * those cond  ons fa l, t n `None`  s returned.
     */
    def makeReplyToT et(
       nReplyToT et d: T et d,
      text: Str ng,
      author: User,
      prepend mpl c  nt ons: Boolean,
      enableT etToNarrowcast ng: Boolean,
      excludeUser ds: Seq[User d],
      batchMode: Opt on[BatchComposeMode]
    ): Future[Opt on[Result]] = {
      val expl c  nt ons: Seq[Extractor.Ent y] =
        extractor.extract nt onedScreenna sW h nd ces(text).asScala.toSeq
      val  nt onedScreenNa s =
        expl c  nt ons.map(_.getValue.toLo rCase).toSet

      /**
       *  f `prepend mpl c  nt ons`  s true, or t  reply author  s t  sa  as t   n-reply-to
       * author, t n t  reply text doesn't have to  nt on t   n-reply-to author.  Ot rw se,
       * c ck that t  text conta ns a  nt on of t  reply author.
       */
      def  sVal dReplyTo( nReplyToUser: User): Boolean =
        prepend mpl c  nt ons ||
          ( nReplyToUser. d == author. d) ||
           nt onedScreenNa s.conta ns( nReplyToUser.screenNa .toLo rCase)

      getReplyS ce( nReplyToT et d, author. d)
        .flatMap { replySrc =>
          val baseResult = BaseResult(
            reply = replyToUser(replySrc.srcUser, So (replySrc.srcT et. d)),
            conversat on d = getConversat on d(replySrc.srcT et),
            selfThread tadata = selfThreadBu lder.bu ld(author. d, replySrc.srcT et),
            commun y = replySrc.srcT et.commun  es,
            // Reply t ets reta n t  sa  exclus ve
            // t et controls as t  t et be ng repl ed to.
            exclus veT etControl = replySrc.srcT et.exclus veT etControl,
            trustedFr endsControl = replySrc.srcT et.trustedFr endsControl,
            ed Control = replySrc.srcT et.ed Control
          )

           f ( sVal dReplyTo(replySrc.srcUser)) {
             f (prepend mpl c  nt ons) {

              // S mpl f ed Repl es mode - append server-s de generated pref x to passed  n text
              s mpleModeCounter. ncr()
              // remove t   n-reply-to t et author from t  excluded users,  n-reply-to t et author w ll always be a d rectedAtUser
              val f lteredExcluded ds =
                excludeUser ds.f lterNot(u d => u d == T etLenses.user d(replySrc.srcT et))
              for {
                un nt onedUser ds <- getUn nt onedUsers(replySrc)
                excludedUsers <- getUsers(f lteredExcluded ds ++ un nt onedUser ds)
                (pref x, d rectedAtUser) = replySrc. mpl c  nt onPref xAndDAU(
                  max mpl c s = math.max(0, max nt ons - expl c  nt ons.s ze),
                  excludedUsers = excludedUsers,
                  author = author,
                  enableT etToNarrowcast ng = enableT etToNarrowcast ng,
                  batchMode = batchMode
                )
              } y eld {
                // pref x or text (or both) can be empty str ngs.  Add " " separator and adjust
                // pref x length only w n both pref x and text are non-empty.
                val textChunks = Seq(pref x, text).map(_.tr m).f lter(_.nonEmpty)
                val t etText = textChunks.mkStr ng(" ")
                val v s bleStart =
                   f (textChunks.s ze == 2) {
                    Offset.CodePo nt.length(pref x + " ")
                  } else {
                    Offset.CodePo nt.length(pref x)
                  }

                So (
                  baseResult.toResult(
                    t etText = t etText,
                    d rectedAt tadata = D rectedAtUser tadata(d rectedAtUser.map(_. d)),
                    v s bleStart = v s bleStart
                  )
                )
              }
            } else {
              // Backwards-compat b l y mode - walk from beg nn ng of text unt l f nd v s bleStart
              compatModeCounter. ncr()
              for {
                cardUser ds <- replySrc.allCardUsers(author, replyCardUsersF nder)
                cardUsers <- getUsers(cardUser ds.toSeq)
                optUser dent y <- extractReplyToUser(text)
                d rectedAtUser d = optUser dent y.map(_. d).f lter(_ => enableT etToNarrowcast ng)
              } y eld {
                So (
                  baseResult.toResult(
                    t etText = text,
                    d rectedAt tadata = D rectedAtUser tadata(d rectedAtUser d),
                    v s bleStart = replySrc.h deablePref x(text, cardUsers, expl c  nt ons),
                  )
                )
              }
            }
          } else {
            Future.None
          }
        }
        .handle {
          //  f `getReplyS ce` throws t  except on, but   aren't comput ng  mpl c 
          //  nt ons, t n   fall back to t  reply-to-user case  nstead of reply-to-t et
          case  nReplyToT etNotFound  f !prepend mpl c  nt ons => None
        }
    }

    def makeReplyToUser(text: Str ng): Future[Opt on[Result]] =
      extractReplyToUser(text).map(_.map { user =>
        Result(replyToUser(user), text, D rectedAtUser tadata(So (user. d)))
      })

    def extractReplyToUser(text: Str ng): Future[Opt on[User]] =
      Opt on(extractor.extractReplyScreenna (text)) match {
        case None => Future.None
        case So (screenNa ) => getUser(UserKey(screenNa ))
      }

    FutureArrow[Request, Opt on[Result]] { request =>
      except onCounters {
        (request. nReplyToT et d.f lter(_ > 0) match {
          case None =>
            Future.None

          case So (t et d) =>
            makeReplyToT et(
              t et d,
              request.t etText,
              User(request.author d, request.authorScreenNa ),
              request.prepend mpl c  nt ons,
              request.enableT etToNarrowcast ng,
              request.excludeUser ds,
              request.batchMode
            )
        }).flatMap {
          case So (r) =>
            // Ensure that t  author of t  reply  s not blocked by
            // t  user who t y are reply ng to.
            c ckBlockRelat onsh p(request.author d, r)
              .before(c ck P Pol cy(request, r.reply))
              .before(Future.value(So (r)))

          case None  f request.enableT etToNarrowcast ng =>
            //   don't c ck t  block relat onsh p w n t  t et  s
            // not part of a conversat on (wh ch  s to say,   allow
            // d rected-at t ets from a blocked user.) T se t ets
            // w ll not cause not f cat ons for t  block ng user,
            // desp e t  presence of t  reply struct.
            makeReplyToUser(request.t etText)

          case None =>
            Future.None
        }
      }
    }
  }
}
