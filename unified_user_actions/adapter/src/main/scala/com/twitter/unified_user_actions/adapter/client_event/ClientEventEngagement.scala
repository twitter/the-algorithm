package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala. emType
 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.un f ed_user_act ons.thr ftscala._

object Cl entEventEngage nt {
  object T etFav extends BaseCl entEvent(Act onType.Cl entT etFav)

  /**
   * T   s f red w n a user unl kes a l ked(favor ed) t et
   */
  object T etUnfav extends BaseCl entEvent(Act onType.Cl entT etUnfav)

  /**
   * T   s "Send Reply" event to  nd cate publ sh ng of a reply T et as opposed to cl ck ng
   * on t  reply button to  n  ate a reply T et (captured  n Cl entT etCl ckReply).
   * T  d fference bet en t  and t  ServerT etReply are:
   * 1) ServerT etReply already has t  new T et  d, 2) A sent reply may be lost dur ng transfer
   * over t  w re and thus may not end up w h a follow-up ServerT etReply.
   */
  object T etReply extends BaseCl entEvent(Act onType.Cl entT etReply)

  /**
   * T   s t  "send quote" event to  nd cate publ sh ng of a quote t et as opposed to cl ck ng
   * on t  quote button to  n  ate a quote t et (captured  n Cl entT etCl ckQuote).
   * T  d fference bet en t  and t  ServerT etQuote are:
   * 1) ServerT etQuote already has t  new T et  d, 2) A sent quote may be lost dur ng transfer
   * over t  w re and thus may not ended up w h a follow-up ServerT etQuote.
   */
  object T etQuote extends BaseCl entEvent(Act onType.Cl entT etQuote)

  /**
   * T   s t  "ret et" event to  nd cate publ sh ng of a ret et.
   */
  object T etRet et extends BaseCl entEvent(Act onType.Cl entT etRet et)

  /**
   * "act on = reply"  nd cates that a user expressed t   ntent on to reply to a T et by cl ck ng
   * t  reply button. No new t et  s created  n t  event.
   */
  object T etCl ckReply extends BaseCl entEvent(Act onType.Cl entT etCl ckReply)

  /**
   * Please note that t  "act on == quote"  s NOT t  create quote T et event l ke what
   *   can get from T etyP e.
   *    s just cl ck on t  "quote t et" (after cl ck ng on t  ret et button t re are 2 opt ons,
   * one  s "ret et" and t  ot r  s "quote t et")
   *
   * Also c cked t  CE (BQ Table), t  ` em.t et_deta ls.quot ng_t et_ d`  s always NULL but
   * ` em.t et_deta ls.ret et ng_t et_ d`, ` em.t et_deta ls. n_reply_to_t et_ d`, ` em.t et_deta ls.quoted_t et_ d`
   * could be NON-NULL and UUA would just  nclude t se NON-NULL f elds as  s. T   s also c cked  n t  un  test.
   */
  object T etCl ckQuote extends BaseCl entEvent(Act onType.Cl entT etCl ckQuote)

  /**
   * Refer to go/c -scr b ng and go/ nteract on-event-spec for deta ls.
   * F red on t  f rst t ck of a track regardless of w re  n t  v deo    s play ng.
   * For loop ng playback, t   s only f red once and does not reset at loop boundar es.
   */
  object T etV deoPlaybackStart
      extends BaseV deoCl entEvent(Act onType.Cl entT etV deoPlaybackStart)

  /**
   * Refer to go/c -scr b ng and go/ nteract on-event-spec for deta ls.
   * F red w n playback reac s 100% of total track durat on.
   * Not val d for l ve v deos.
   * For loop ng playback, t   s only f red once and does not reset at loop boundar es.
   */
  object T etV deoPlaybackComplete
      extends BaseV deoCl entEvent(Act onType.Cl entT etV deoPlaybackComplete)

  /**
   * Refer to go/c -scr b ng and go/ nteract on-event-spec for deta ls.
   * T   s f red w n playback reac s 25% of total track durat on. Not val d for l ve v deos.
   * For loop ng playback, t   s only f red once and does not reset at loop boundar es.
   */
  object T etV deoPlayback25 extends BaseV deoCl entEvent(Act onType.Cl entT etV deoPlayback25)
  object T etV deoPlayback50 extends BaseV deoCl entEvent(Act onType.Cl entT etV deoPlayback50)
  object T etV deoPlayback75 extends BaseV deoCl entEvent(Act onType.Cl entT etV deoPlayback75)
  object T etV deoPlayback95 extends BaseV deoCl entEvent(Act onType.Cl entT etV deoPlayback95)

  /**
   * Refer to go/c -scr b ng and go/ nteract on-event-spec for deta ls.
   * T   f f red w n t  v deo has been played  n non-prev ew
   * ( .e. not autoplay ng  n t  t  l ne) mode, and was not started v a auto-advance.
   * For loop ng playback, t   s only f red once and does not reset at loop boundar es.
   */
  object T etV deoPlayFromTap extends BaseV deoCl entEvent(Act onType.Cl entT etV deoPlayFromTap)

  /**
   * Refer to go/c -scr b ng and go/ nteract on-event-spec for deta ls.
   * T   s f red w n 50% of t  v deo has been on-screen and play ng for 10 consecut ve seconds
   * or 95% of t  v deo durat on, wh c ver co s f rst.
   * For loop ng playback, t   s only f red once and does not reset at loop boundar es.
   */
  object T etV deoQual yV ew extends BaseV deoCl entEvent(Act onType.Cl entT etV deoQual yV ew)

  object T etV deoV ew extends BaseV deoCl entEvent(Act onType.Cl entT etV deoV ew)
  object T etV deoMrcV ew extends BaseV deoCl entEvent(Act onType.Cl entT etV deoMrcV ew)
  object T etV deoV ewThreshold
      extends BaseV deoCl entEvent(Act onType.Cl entT etV deoV ewThreshold)
  object T etV deoCtaUrlCl ck extends BaseV deoCl entEvent(Act onType.Cl entT etV deoCtaUrlCl ck)
  object T etV deoCtaWatchCl ck
      extends BaseV deoCl entEvent(Act onType.Cl entT etV deoCtaWatchCl ck)

  /**
   * T   s f red w n a user cl cks on "Undo ret et" after re-t et ng a t et
   *
   */
  object T etUnret et extends BaseCl entEvent(Act onType.Cl entT etUnret et)

  /**
   * T   s f red w n a user cl cks on a photo attac d to a t et and t  photo expands to f 
   * t  screen.
   */
  object T etPhotoExpand extends BaseCl entEvent(Act onType.Cl entT etPhotoExpand)

  /**
   * T   s f red w n a user cl cks on a card, a card could be a photo or v deo for example
   */
  object CardCl ck extends BaseCardCl entEvent(Act onType.Cl entCardCl ck)
  object CardOpenApp extends BaseCardCl entEvent(Act onType.Cl entCardOpenApp)
  object CardApp nstallAttempt extends BaseCardCl entEvent(Act onType.Cl entCardApp nstallAttempt)
  object PollCardVote extends BaseCardCl entEvent(Act onType.Cl entPollCardVote)

  /**
   * T   s f red w n a user cl cks on a prof le  nt on  ns de a t et.
   */
  object T etCl ck nt onScreenNa 
      extends BaseCl entEvent(Act onType.Cl entT etCl ck nt onScreenNa ) {
    overr de def getUua em(
      ce em: LogEvent em,
      logEvent: LogEvent
    ): Opt on[ em] =
      (
        ce em. d,
        logEvent.eventDeta ls.flatMap(
          _.targets.flatMap(_.f nd(_. emType.conta ns( emType.User))))) match {
        case (So (t et d), So (target)) =>
          (target. d, target.na ) match {
            case (So (prof le d), So (prof leHandle)) =>
              So (
                 em.T et nfo(
                  Cl entEventCommonUt ls
                    .getBas cT et nfo(t et d, ce em, logEvent.eventNa space)
                    .copy(t etAct on nfo = So (
                      T etAct on nfo.Cl entT etCl ck nt onScreenNa (
                        Cl entT etCl ck nt onScreenNa (
                          act onProf le d = prof le d,
                          handle = prof leHandle
                        ))))))
            case _ => None
          }
        case _ => None
      }
  }

  /**
   * T se are f red w n user follows/unfollows a Top c. Please see t  com nt  n t 
   * Cl entEventAdapter na space match ng to see t  subtle deta ls.
   */
  object Top cFollow extends BaseTop cCl entEvent(Act onType.Cl entTop cFollow)
  object Top cUnfollow extends BaseTop cCl entEvent(Act onType.Cl entTop cUnfollow)

  /**
   * T   s f red w n t  user cl cks t  "x"  con next to t  top c on t  r t  l ne,
   * and cl cks "Not  nterested  n {TOP C}"  n t  pop-up prompt
   * Alternat vely, t y can also cl ck "See more" button to v s  t  top c page, and cl ck "Not  nterested" t re.
   */
  object Top cNot nterested n extends BaseTop cCl entEvent(Act onType.Cl entTop cNot nterested n)

  /**
   * T   s f red w n t  user cl cks t  "Undo" button after cl ck ng "x" or "Not  nterested" on a Top c
   * wh ch  s captured  n Cl entTop cNot nterested n
   */
  object Top cUndoNot nterested n
      extends BaseTop cCl entEvent(Act onType.Cl entTop cUndoNot nterested n)

  /**
   * T   s f red w n a user cl cks on  "T  T et's not  lpful" flow  n t  caret  nu
   * of a T et result on t  Search Results Page
   */
  object T etNot lpful extends BaseCl entEvent(Act onType.Cl entT etNot lpful)

  /**
   * T   s f red w n a user cl cks Undo after cl ck ng on
   * "T  T et's not  lpful" flow  n t  caret  nu of a T et result on t  Search Results Page
   */
  object T etUndoNot lpful extends BaseCl entEvent(Act onType.Cl entT etUndoNot lpful)

  object T etReport extends BaseCl entEvent(Act onType.Cl entT etReport) {
    overr de def getUua em(
      ce em: LogEvent em,
      logEvent: LogEvent
    ): Opt on[ em] = {
      for {
        act onT et d <- ce em. d
      } y eld {
         em.T et nfo(
          Cl entEventCommonUt ls
            .getBas cT et nfo(
              act onT et d = act onT et d,
              ce em = ce em,
              ceNa spaceOpt = logEvent.eventNa space)
            .copy(t etAct on nfo = So (
              T etAct on nfo.Cl entT etReport(
                Cl entT etReport(
                   sReportT etDone =
                    logEvent.eventNa space.flatMap(_.act on).ex sts(_.conta ns("done")),
                  reportFlow d = logEvent.reportDeta ls.flatMap(_.reportFlow d)
                )
              ))))
      }
    }
  }

  /**
   * Not  nterested  n (Do Not l ke) event
   */
  object T etNot nterested n extends BaseCl entEvent(Act onType.Cl entT etNot nterested n)
  object T etUndoNot nterested n extends BaseCl entEvent(Act onType.Cl entT etUndoNot nterested n)

  /**
   * T   s f red w n a user F RST cl cks t  "Not  nterested  n t  T et" button  n t  caret  nu of a T et
   * t n cl cks "T  T et  s not about {TOP C}"  n t  subsequent prompt
   * Note: t  button  s h dden unless a user cl cks "Not  nterested  n t  T et" f rst.
   */
  object T etNotAboutTop c extends BaseCl entEvent(Act onType.Cl entT etNotAboutTop c)

  /**
   * T   s f red w n a user cl cks "Undo"  m d ately after cl ck ng "T  T et  s not about {TOP C}",
   * wh ch  s captured  n T etNotAboutTop c
   */
  object T etUndoNotAboutTop c extends BaseCl entEvent(Act onType.Cl entT etUndoNotAboutTop c)

  /**
   * T   s f red w n a user F RST cl cks t  "Not  nterested  n t  T et" button  n t  caret  nu of a T et
   * t n cl cks  "T  T et  sn't recent"  n t  subsequent prompt
   * Note: t  button  s h dden unless a user cl cks "Not  nterested  n t  T et" f rst.
   */
  object T etNotRecent extends BaseCl entEvent(Act onType.Cl entT etNotRecent)

  /**
   * T   s f red w n a user cl cks "Undo"  m d ately after cl ck ng "  T et  sn't recent",
   * wh ch  s captured  n T etNotRecent
   */
  object T etUndoNotRecent extends BaseCl entEvent(Act onType.Cl entT etUndoNotRecent)

  /**
   * T   s f red w n a user cl cks "Not  nterested  n t  T et" button  n t  caret  nu of a T et
   * t n cl cks  "Show fe r t ets from"  n t  subsequent prompt
   * Note: t  button  s h dden unless a user cl cks "Not  nterested  n t  T et" f rst.
   */
  object T etSeeFe r extends BaseCl entEvent(Act onType.Cl entT etSeeFe r)

  /**
   * T   s f red w n a user cl cks "Undo"  m d ately after cl ck ng "Show fe r t ets from",
   * wh ch  s captured  n T etSeeFe r
   */
  object T etUndoSeeFe r extends BaseCl entEvent(Act onType.Cl entT etUndoSeeFe r)

  /**
   * T   s f red w n a user cl ck "Subm " at t  end of a "Report T et" flow
   * Cl entT etReport = 1041  s scr bed by  althCl ent team, on t  cl ent s de
   * T   s scr bed by spamacaw, on t  server s de
   * T y can be jo ned on reportFlow d
   * See https://confluence.tw ter.b z/pages/v ewpage.act on?spaceKey=HEALTH&t le=Understand ng+ReportDeta ls
   */
  object T etReportServer extends BaseCl entEvent(Act onType.ServerT etReport) {
    overr de def getUua em(
      ce em: LogEvent em,
      logEvent: LogEvent
    ): Opt on[ em] =
      for {
        act onT et d <- ce em. d
      } y eld  em.T et nfo(
        Cl entEventCommonUt ls
          .getBas cT et nfo(
            act onT et d = act onT et d,
            ce em = ce em,
            ceNa spaceOpt = logEvent.eventNa space)
          .copy(t etAct on nfo = So (
            T etAct on nfo.ServerT etReport(
              ServerT etReport(
                reportFlow d = logEvent.reportDeta ls.flatMap(_.reportFlow d),
                reportType = logEvent.reportDeta ls.flatMap(_.reportType)
              )
            ))))
  }

  /**
   * T   s f red w n a user cl cks Block  n a Prof le page
   * A Prof le can also be blocked w n a user cl cks Block  n t   nu of a T et, wh ch
   *  s captured  n Cl entT etBlockAuthor
   */
  object Prof leBlock extends BaseProf leCl entEvent(Act onType.Cl entProf leBlock)

  /**
   * T   s f red w n a user cl cks unblock  n a pop-up prompt r ght after block ng a prof le
   *  n t  prof le page or cl cks unblock  n a drop-down  nu  n t  prof le page.
   */
  object Prof leUnblock extends BaseProf leCl entEvent(Act onType.Cl entProf leUnblock)

  /**
   * T   s f red w n a user cl cks Mute  n a Prof le page
   * A Prof le can also be muted w n a user cl cks Mute  n t   nu of a T et, wh ch
   *  s captured  n Cl entT etMuteAuthor
   */
  object Prof leMute extends BaseProf leCl entEvent(Act onType.Cl entProf leMute)

  /*
   * T   s f red w n a user cl cks "Report User" act on from user prof le page
   * */
  object Prof leReport extends BaseProf leCl entEvent(Act onType.Cl entProf leReport)

  // T   s f red w n a user prof le  s open  n a Prof le page
  object Prof leShow extends BaseProf leCl entEvent(Act onType.Cl entProf leShow)

  object Prof leCl ck extends BaseProf leCl entEvent(Act onType.Cl entProf leCl ck) {

    /**
     * Cl entT etCl ckProf le would em  2 events, 1 w h  em type T et and 1 w h  em type User
     * Both events w ll go to both act ons (t  actual classes). For Cl entT etCl ckProf le,
     *  em type of T et w ll f lter out t  event w h  em type User. But for Cl entProf leCl ck,
     * because   need to  nclude  em type of User, t n   w ll also  nclude t  event of T etCl ckProf le
     *  f   don't do anyth ng  re. T  overr de ensures   don't  nclude t et author cl cks events  n Prof leCl ck
     */
    overr de def getUua em(
      ce em: LogEvent em,
      logEvent: LogEvent
    ): Opt on[ em] =
       f (logEvent.eventDeta ls
          .flatMap(_. ems).ex sts( ems =>  ems.ex sts(_. emType.conta ns( emType.T et)))) {
        None
      } else {
        super.getUua em(ce em, logEvent)
      }
  }

  /**
   * T   s f red w n a user follows a prof le from t 
   * prof le page / people module and people tab on t  Search Results Page / s debar on t  Ho  page
   * A Prof le can also be follo d w n a user cl cks follow  n t 
   * caret  nu of a T et / follow button on hover ng on prof le avatar,
   * wh ch  s captured  n Cl entT etFollowAuthor
   */
  object Prof leFollow extends BaseProf leCl entEvent(Act onType.Cl entProf leFollow) {

    /**
     * Cl entT etFollowAuthor would em  2 events, 1 w h  em type T et and 1 w h  em type User
     *  Both events w ll go to both act ons (t  actual classes). For Cl entT etFollowAuthor,
     *   em type of T et w ll f lter out t  event w h  em type User. But for Cl entProf leFollow,
     *  because   need to  nclude  em type of User, t n   w ll also  nclude t  event of T etFollowAuthor
     *   f   don't do anyth ng  re. T  overr de ensures   don't  nclude t et author follow events  n Prof leFollow
     */
    overr de def getUua em(
      ce em: LogEvent em,
      logEvent: LogEvent
    ): Opt on[ em] =
       f (logEvent.eventDeta ls
          .flatMap(_. ems).ex sts( ems =>  ems.ex sts(_. emType.conta ns( emType.T et)))) {
        None
      } else {
        super.getUua em(ce em, logEvent)
      }
  }

  /**
   * T   s f red w n a user cl cks Follow  n t  caret  nu of a T et or hovers on t  avatar of t  t et author
   * and cl cks on t  Follow button. A prof le can also be follo d by cl ck ng t  Follow button on t  Prof le
   * page and conf rm, wh ch  s captured  n Cl entProf leFollow.
   * T  event em s two  ems, one of user type and anot r of t et type, s nce t  default  mple ntat on of
   * BaseCl entEvent only looks for T et type, t  ot r  em  s dropped wh ch  s t  expected behav  
   */
  object T etFollowAuthor extends BaseCl entEvent(Act onType.Cl entT etFollowAuthor) {
    overr de def getUua em(
      ce em: LogEvent em,
      logEvent: LogEvent
    ): Opt on[ em] = {
      for {
        act onT et d <- ce em. d
      } y eld {
         em.T et nfo(
          Cl entEventCommonUt ls
            .getBas cT et nfo(
              act onT et d = act onT et d,
              ce em = ce em,
              ceNa spaceOpt = logEvent.eventNa space)
            .copy(t etAct on nfo = So (
              T etAct on nfo.Cl entT etFollowAuthor(
                Cl entT etFollowAuthor(
                  Cl entEventCommonUt ls.getT etAuthorFollowS ce(logEvent.eventNa space))
              ))))
      }
    }
  }

  /**
   * T   s f red w n a user cl cks Unfollow  n t  caret  nu of a T et or hovers on t  avatar of t  t et author
   * and cl cks on t  Unfollow button. A prof le can also be unfollo d by cl ck ng t  Unfollow button on t  Prof le
   * page and conf rm, wh ch w ll be captured  n Cl entProf leUnfollow.
   * T  event em s two  ems, one of user type and anot r of t et type, s nce t  default  mple ntat on of
   * BaseCl entEvent only looks for T et type, t  ot r  em  s dropped wh ch  s t  expected behav  
   */
  object T etUnfollowAuthor extends BaseCl entEvent(Act onType.Cl entT etUnfollowAuthor) {
    overr de def getUua em(
      ce em: LogEvent em,
      logEvent: LogEvent
    ): Opt on[ em] = {
      for {
        act onT et d <- ce em. d
      } y eld {
         em.T et nfo(
          Cl entEventCommonUt ls
            .getBas cT et nfo(
              act onT et d = act onT et d,
              ce em = ce em,
              ceNa spaceOpt = logEvent.eventNa space)
            .copy(t etAct on nfo = So (
              T etAct on nfo.Cl entT etUnfollowAuthor(
                Cl entT etUnfollowAuthor(
                  Cl entEventCommonUt ls.getT etAuthorUnfollowS ce(logEvent.eventNa space))
              ))))
      }
    }
  }

  /**
   * T   s f red w n a user cl cks Block  n t  caret  nu of a T et to block t  prof le
   * that authors t  T et. A prof le can also be blocked  n t  Prof le page, wh ch  s captured
   *  n Cl entProf leBlock
   */
  object T etBlockAuthor extends BaseCl entEvent(Act onType.Cl entT etBlockAuthor)

  /**
   * T   s f red w n a user cl cks unblock  n a pop-up prompt r ght after block ng an author
   *  n t  drop-down  nu of a t et
   */
  object T etUnblockAuthor extends BaseCl entEvent(Act onType.Cl entT etUnblockAuthor)

  /**
   * T   s f red w n a user cl cks Mute  n t  caret  nu of a T et to mute t  prof le
   * that authors t  T et. A prof le can also be muted  n t  Prof le page, wh ch  s captured
   *  n Cl entProf leMute
   */
  object T etMuteAuthor extends BaseCl entEvent(Act onType.Cl entT etMuteAuthor)

  /**
   * T   s f red w n a user cl cks on a T et to open t  T et deta ls page. Note that for
   * T ets  n t  Not f cat on Tab product surface, a cl ck can be reg stered d fferently
   * depend ng on w t r t  T et  s a rendered T et (a cl ck results  n Cl entT etCl ck)
   * or a wrapper Not f cat on (a cl ck results  n Cl entNot f cat onCl ck).
   */
  object T etCl ck extends BaseCl entEvent(Act onType.Cl entT etCl ck)

  /**
   * T   s f red w n a user cl cks to v ew t  prof le page of anot r user from a T et
   */
  object T etCl ckProf le extends BaseCl entEvent(Act onType.Cl entT etCl ckProf le)

  /**
   * T   s f red w n a user cl cks on t  "share"  con on a T et to open t  share  nu.
   * T  user may or may not proceed and f n sh shar ng t  T et.
   */
  object T etCl ckShare extends BaseCl entEvent(Act onType.Cl entT etCl ckShare)

  /**
   * T   s f red w n a user cl cks "Copy l nk to T et"  n a  nu appeared after h t ng
   * t  "share"  con on a T et OR w n a user selects share_v a -> copy_l nk after long-cl ck
   * a l nk  ns de a t et on a mob le dev ce
   */
  object T etShareV aCopyL nk extends BaseCl entEvent(Act onType.Cl entT etShareV aCopyL nk)

  /**
   * T   s f red w n a user cl cks "Send v a D rect  ssage" after
   * cl ck ng on t  "share"  con on a T et to open t  share  nu.
   * T  user may or may not proceed and f n sh Send ng t  DM.
   */
  object T etCl ckSendV aD rect ssage
      extends BaseCl entEvent(Act onType.Cl entT etCl ckSendV aD rect ssage)

  /**
   * T   s f red w n a user cl cks "Bookmark" after
   * cl ck ng on t  "share"  con on a T et to open t  share  nu.
   */
  object T etShareV aBookmark extends BaseCl entEvent(Act onType.Cl entT etShareV aBookmark)

  /**
   * T   s f red w n a user cl cks "Remove T et from Bookmarks" after
   * cl ck ng on t  "share"  con on a T et to open t  share  nu.
   */
  object T etUnbookmark extends BaseCl entEvent(Act onType.Cl entT etUnbookmark)

  /**
   * T  event  s f red w n t  user cl cks on a hashtag  n a T et.
   */
  object T etCl ckHashtag extends BaseCl entEvent(Act onType.Cl entT etCl ckHashtag) {
    overr de def getUua em(
      ce em: LogEvent em,
      logEvent: LogEvent
    ): Opt on[ em] = for {
      act onT et d <- ce em. d
    } y eld  em.T et nfo(
      Cl entEventCommonUt ls
        .getBas cT et nfo(
          act onT et d = act onT et d,
          ce em = ce em,
          ceNa spaceOpt = logEvent.eventNa space)
        .copy(t etAct on nfo = logEvent.eventDeta ls
          .map(
            _.targets.flatMap(_. adOpt on.flatMap(_.na ))
          ) // fetch t  f rst  em  n t  deta ls and t n t  na  w ll have t  hashtag value w h t  '#' s gn
          .map { hashtagOpt =>
            T etAct on nfo.Cl entT etCl ckHashtag(
              Cl entT etCl ckHashtag(hashtag = hashtagOpt)
            )
          }))
  }

  /**
   * T   s f red w n a user cl cks "Bookmark" after cl ck ng on t  "share"  con on a T et to
   * open t  share  nu, or w n a user cl cks on t  'bookmark'  con on a T et (bookmark  con
   *  s ava lable to  os only as of March 2023).
   * T etBookmark and T etShareByBookmark log t  sa  events but serve for  nd v dual use cases.
   */
  object T etBookmark extends BaseCl entEvent(Act onType.Cl entT etBookmark)

  /**
   * T   s f red w n a user cl cks on a l nk  n a t et.
   * T  l nk could be d splayed as a URL or embedded
   *  n a component such as an  mage or a card  n a t et.
   */
  object T etOpenL nk extends BaseCl entEvent(Act onType.Cl entT etOpenL nk) {
    overr de def getUua em(
      ce em: LogEvent em,
      logEvent: LogEvent
    ): Opt on[ em] =
      for {
        act onT et d <- ce em. d
      } y eld  em.T et nfo(
        Cl entEventCommonUt ls
          .getBas cT et nfo(
            act onT et d = act onT et d,
            ce em = ce em,
            ceNa spaceOpt = logEvent.eventNa space)
          .copy(t etAct on nfo = So (
            T etAct on nfo.Cl entT etOpenL nk(
              Cl entT etOpenL nk(url = logEvent.eventDeta ls.flatMap(_.url))
            ))))
  }

  /**
   * T   s f red w n a user takes a screenshot.
   * T   s ava lable for only mob le cl ents.
   */
  object T etTakeScreenshot extends BaseCl entEvent(Act onType.Cl entT etTakeScreenshot) {
    overr de def getUua em(
      ce em: LogEvent em,
      logEvent: LogEvent
    ): Opt on[ em] =
      for {
        act onT et d <- ce em. d
      } y eld  em.T et nfo(
        Cl entEventCommonUt ls
          .getBas cT et nfo(
            act onT et d = act onT et d,
            ce em = ce em,
            ceNa spaceOpt = logEvent.eventNa space)
          .copy(t etAct on nfo = So (
            T etAct on nfo.Cl entT etTakeScreenshot(
              Cl entT etTakeScreenshot(percentV s ble  ght100k = ce em.percentV s ble  ght100k)
            ))))
  }

  /**
   * T   s f red w n a user cl cks t  "T  T et  sn't relevant" button  n a prompt d splayed
   * after cl ck ng "T  T et's not  lpful"  n search result page or "Not  nterested  n t  T et"
   *  n t  ho  t  l ne page.
   * Note: t  button  s h dden unless a user cl cks "T  T et  sn't relevant" or
   * "T  T et's not  lpful" f rst
   */
  object T etNotRelevant extends BaseCl entEvent(Act onType.Cl entT etNotRelevant)

  /**
   * T   s f red w n a user cl cks "Undo"  m d ately after cl ck ng "t  T et  sn't relevant",
   * wh ch  s captured  n T etNotRelevant
   */
  object T etUndoNotRelevant extends BaseCl entEvent(Act onType.Cl entT etUndoNotRelevant)

  /**
   * T   s f red w n a user  s logged out and follows a prof le from t 
   * prof le page / people module from  b.
   * One can only try to follow from  b,  OS and Andro d do not support logged out brows ng
   */
  object Prof leFollowAttempt extends BaseProf leCl entEvent(Act onType.Cl entProf leFollowAttempt)

  /**
   * T   s f red w n a user  s logged out and fav  e a t et from  b.
   * One can only try to fav  e from  b,  OS and Andro d do not support logged out brows ng
   */
  object T etFavor eAttempt extends BaseCl entEvent(Act onType.Cl entT etFavor eAttempt)

  /**
   * T   s f red w n a user  s logged out and Ret et a t et from  b.
   * One can only try to fav  e from  b,  OS and Andro d do not support logged out brows ng
   */
  object T etRet etAttempt extends BaseCl entEvent(Act onType.Cl entT etRet etAttempt)

  /**
   * T   s f red w n a user  s logged out and reply on t et from  b.
   * One can only try to fav  e from  b,  OS and Andro d do not support logged out brows ng
   */
  object T etReplyAttempt extends BaseCl entEvent(Act onType.Cl entT etReplyAttempt)

  /**
   * T   s f red w n a user  s logged out and cl cks on log n button.
   * Currently seem to be generated only on [m5, L eNat veWrapper] as of Jan 2023.
   */
  object CTALog nCl ck extends BaseCTACl entEvent(Act onType.Cl entCTALog nCl ck)

  /**
   * T   s f red w n a user  s logged out and log n w ndow  s shown.
   */
  object CTALog nStart extends BaseCTACl entEvent(Act onType.Cl entCTALog nStart)

  /**
   * T   s f red w n a user  s logged out and log n  s successful.
   */
  object CTALog nSuccess extends BaseCTACl entEvent(Act onType.Cl entCTALog nSuccess)

  /**
   * T   s f red w n a user  s logged out and cl cks on s gnup button.
   */
  object CTAS gnupCl ck extends BaseCTACl entEvent(Act onType.Cl entCTAS gnupCl ck)

  /**
   * T   s f red w n a user  s logged out and s gnup  s successful.
   */
  object CTAS gnupSuccess extends BaseCTACl entEvent(Act onType.Cl entCTAS gnupSuccess)

  /**
   * T   s f red w n a user opens a Push Not f cat on.
   * Refer to https://confluence.tw ter.b z/pages/v ewpage.act on?page d=161811800
   * for Push Not f cat on scr be deta ls
   */
  object Not f cat onOpen extends BasePushNot f cat onCl entEvent(Act onType.Cl entNot f cat onOpen)

  /**
   * T   s f red w n a user cl cks on a not f cat on  n t  Not f cat on Tab.
   * Refer to go/ntab-urt-scr be for Not f cat on Tab scr be deta ls.
   */
  object Not f cat onCl ck
      extends BaseNot f cat onTabCl entEvent(Act onType.Cl entNot f cat onCl ck)

  /**
   * T   s f red w n a user taps t  "See Less Often" caret  nu  em of a not f cat on  n
   * t  Not f cat on Tab.
   * Refer to go/ntab-urt-scr be for Not f cat on Tab scr be deta ls.
   */
  object Not f cat onSeeLessOften
      extends BaseNot f cat onTabCl entEvent(Act onType.Cl entNot f cat onSeeLessOften)

  /**
   * T   s f red w n a user closes or sw pes away a Push Not f cat on.
   * Refer to https://confluence.tw ter.b z/pages/v ewpage.act on?page d=161811800
   * for Push Not f cat on scr be deta ls
   */
  object Not f cat onD sm ss
      extends BasePushNot f cat onCl entEvent(Act onType.Cl entNot f cat onD sm ss)

  /**
   *  T   s f red w n a user cl cks on a typea ad suggest on(quer es, events, top cs, users)
   *   n a drop-down  nu of a search box or a t et compose box.
   */
  object Typea adCl ck extends BaseSearchTypea adEvent(Act onType.Cl entTypea adCl ck)

  /**
   * T   s a gener c event f red w n t  user subm s feedback on a prompt.
   * So  examples  nclude D d   F nd   Prompt and T et Relevance on Search Results Page.
   */
  object FeedbackPromptSubm 
      extends BaseFeedbackSubm Cl entEvent(Act onType.Cl entFeedbackPromptSubm )

  object AppEx  extends BaseUASCl entEvent(Act onType.Cl entAppEx )
}
