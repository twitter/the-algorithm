package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.cl entapp.thr ftscala.EventNa space
 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.un f ed_user_act ons.adapter.AbstractAdapter
 mport com.tw ter.un f ed_user_act ons.adapter.cl ent_event.Cl entEvent mpress on._
 mport com.tw ter.un f ed_user_act ons.adapter.cl ent_event.Cl entEventEngage nt._
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport scala.ut l.match ng.Regex

class Cl entEventAdapter extends AbstractAdapter[LogEvent, UnKeyed, Un f edUserAct on] {
   mport Cl entEventAdapter._

  overr de def adaptOneToKeyedMany(
     nput: LogEvent,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): Seq[(UnKeyed, Un f edUserAct on)] =
    adaptEvent( nput).map { e => (UnKeyed, e) }
}

object Cl entEventAdapter {
  // Refer to go/c -scr b ng and go/ nteract on-event-spec for deta ls
  def  sV deoEvent(ele nt: Str ng): Boolean = Seq[Str ng](
    "g f_player",
    "per scope_player",
    "platform_ampl fy_card",
    "v deo_player",
    "v ne_player").conta ns(ele nt)

  /**
   * T et cl cks on t  Not f cat on Tab on  OS are a spec al case because t  `ele nt`  s d fferent
   * from T et cl cks everyw re else on t  platform.
   *
   * For Not f cat on Tab on  OS, `ele nt` could be one of `user_ nt oned_ `,
   * `user_ nt oned_ _ n_a_quote_t et`, `user_repl ed_to_y _t et`, or `user_quoted_y _t et`.
   *
   *  n ot r places, `ele nt` = `t et`.
   */
  def  sT etCl ckEvent(ele nt: Str ng): Boolean =
    Seq[Str ng](
      "t et",
      "user_ nt oned_ ",
      "user_ nt oned_ _ n_a_quote_t et",
      "user_repl ed_to_y _t et",
      "user_quoted_y _t et"
    ).conta ns(ele nt)

  f nal val val dUAS osCl ent ds = Seq[Long](
    129032L, // Tw ter for  Phone
    191841L // Tw ter for  Pad
  )
  // Tw ter for Andro d
  f nal val val dUASAndro dCl ent ds = Seq[Long](258901L)

  def adaptEvent( nputLogEvent: LogEvent): Seq[Un f edUserAct on] =
    Opt on( nputLogEvent).toSeq
      .f lterNot { logEvent: LogEvent =>
        should gnoreCl entEvent(logEvent.eventNa space)
      }
      .flatMap { logEvent: LogEvent =>
        val act onTypesPerEvent: Seq[BaseCl entEvent] = logEvent.eventNa space.toSeq.flatMap {
          na  =>
            (na .page, na .sect on, na .component, na .ele nt, na .act on) match {
              case (_, _, _, _, So ("favor e")) => Seq(T etFav)
              case (_, _, _, _, So ("unfavor e")) => Seq(T etUnfav)
              case (_, _, So ("stream"), So ("l nger"), So ("results")) =>
                Seq(T etL nger mpress on)
              case (_, _, So ("stream"), None, So ("results")) =>
                Seq(T etRender mpress on)
              case (_, _, _, _, So ("send_reply")) => Seq(T etReply)
              // D fferent cl ents may have d fferent act ons of t  sa  "send quote"
              // but   turns out that both send_quote and ret et_w h_com nt should correspond to
              // "send quote"
              case (_, _, _, _, So ("send_quote_t et")) |
                  (_, _, _, _, So ("ret et_w h_com nt")) =>
                Seq(T etQuote)
              case (_, _, _, _, So ("ret et")) => Seq(T etRet et)
              case (_, _, _, _, So ("unret et")) => Seq(T etUnret et)
              case (_, _, _, _, So ("reply")) => Seq(T etCl ckReply)
              case (_, _, _, _, So ("quote")) => Seq(T etCl ckQuote)
              case (_, _, _, So (ele nt), So ("playback_start"))  f  sV deoEvent(ele nt) =>
                Seq(T etV deoPlaybackStart)
              case (_, _, _, So (ele nt), So ("playback_complete"))  f  sV deoEvent(ele nt) =>
                Seq(T etV deoPlaybackComplete)
              case (_, _, _, So (ele nt), So ("playback_25"))  f  sV deoEvent(ele nt) =>
                Seq(T etV deoPlayback25)
              case (_, _, _, So (ele nt), So ("playback_50"))  f  sV deoEvent(ele nt) =>
                Seq(T etV deoPlayback50)
              case (_, _, _, So (ele nt), So ("playback_75"))  f  sV deoEvent(ele nt) =>
                Seq(T etV deoPlayback75)
              case (_, _, _, So (ele nt), So ("playback_95"))  f  sV deoEvent(ele nt) =>
                Seq(T etV deoPlayback95)
              case (_, _, _, So (ele nt), So ("play_from_tap"))  f  sV deoEvent(ele nt) =>
                Seq(T etV deoPlayFromTap)
              case (_, _, _, So (ele nt), So ("v deo_qual y_v ew"))  f  sV deoEvent(ele nt) =>
                Seq(T etV deoQual yV ew)
              case (_, _, _, So (ele nt), So ("v deo_v ew"))  f  sV deoEvent(ele nt) =>
                Seq(T etV deoV ew)
              case (_, _, _, So (ele nt), So ("v deo_mrc_v ew"))  f  sV deoEvent(ele nt) =>
                Seq(T etV deoMrcV ew)
              case (_, _, _, So (ele nt), So ("v ew_threshold"))  f  sV deoEvent(ele nt) =>
                Seq(T etV deoV ewThreshold)
              case (_, _, _, So (ele nt), So ("cta_url_cl ck"))  f  sV deoEvent(ele nt) =>
                Seq(T etV deoCtaUrlCl ck)
              case (_, _, _, So (ele nt), So ("cta_watch_cl ck"))  f  sV deoEvent(ele nt) =>
                Seq(T etV deoCtaWatchCl ck)
              case (_, _, _, So ("platform_photo_card"), So ("cl ck")) => Seq(T etPhotoExpand)
              case (_, _, _, So ("platform_card"), So ("cl ck")) => Seq(CardCl ck)
              case (_, _, _, _, So ("open_app")) => Seq(CardOpenApp)
              case (_, _, _, _, So (" nstall_app")) => Seq(CardApp nstallAttempt)
              case (_, _, _, So ("platform_card"), So ("vote")) |
                  (_, _, _, So ("platform_forward_card"), So ("vote")) =>
                Seq(PollCardVote)
              case (_, _, _, So (" nt on"), So ("cl ck")) |
                  (_, _, _, _, So (" nt on_cl ck")) =>
                Seq(T etCl ck nt onScreenNa )
              case (_, _, _, So (ele nt), So ("cl ck"))  f  sT etCl ckEvent(ele nt) =>
                Seq(T etCl ck)
              case // Follow from t  Top c page (or so-called land ng page)
                  (_, _, _, So ("top c"), So ("follow")) |
                  // Actually not sure how t   s generated ... but saw qu e so  events  n BQ
                  (_, _, _, So ("soc al_proof"), So ("follow")) |
                  // Cl ck on T et's caret  nu of "Follow (t  top c)",   needs to be:
                  // 1) user follows t  Top c already, 2) and cl cked on t  "Unfollow Top c" f rst.
                  (_, _, _, So ("feedback_follow_top c"), So ("cl ck")) =>
                Seq(Top cFollow)
              case (_, _, _, So ("top c"), So ("unfollow")) |
                  (_, _, _, So ("soc al_proof"), So ("unfollow")) |
                  (_, _, _, So ("feedback_unfollow_top c"), So ("cl ck")) =>
                Seq(Top cUnfollow)
              case (_, _, _, So ("top c"), So ("not_ nterested")) |
                  (_, _, _, So ("feedback_not_ nterested_ n_top c"), So ("cl ck")) =>
                Seq(Top cNot nterested n)
              case (_, _, _, So ("top c"), So ("un_not_ nterested")) |
                  (_, _, _, So ("feedback_not_ nterested_ n_top c"), So ("undo")) =>
                Seq(Top cUndoNot nterested n)
              case (_, _, _, So ("feedback_g vefeedback"), So ("cl ck")) =>
                Seq(T etNot lpful)
              case (_, _, _, So ("feedback_g vefeedback"), So ("undo")) =>
                Seq(T etUndoNot lpful)
              case (_, _, _, So ("report_t et"), So ("cl ck")) |
                  (_, _, _, So ("report_t et"), So ("done")) =>
                Seq(T etReport)
              case (_, _, _, So ("feedback_dontl ke"), So ("cl ck")) =>
                Seq(T etNot nterested n)
              case (_, _, _, So ("feedback_dontl ke"), So ("undo")) =>
                Seq(T etUndoNot nterested n)
              case (_, _, _, So ("feedback_notabouttop c"), So ("cl ck")) =>
                Seq(T etNotAboutTop c)
              case (_, _, _, So ("feedback_notabouttop c"), So ("undo")) =>
                Seq(T etUndoNotAboutTop c)
              case (_, _, _, So ("feedback_notrecent"), So ("cl ck")) =>
                Seq(T etNotRecent)
              case (_, _, _, So ("feedback_notrecent"), So ("undo")) =>
                Seq(T etUndoNotRecent)
              case (_, _, _, So ("feedback_seefe r"), So ("cl ck")) =>
                Seq(T etSeeFe r)
              case (_, _, _, So ("feedback_seefe r"), So ("undo")) =>
                Seq(T etUndoSeeFe r)
              // Only w n act on = "subm "   get all f elds  n ReportDeta ls, such as reportType
              // See https://confluence.tw ter.b z/pages/v ewpage.act on?spaceKey=HEALTH&t le=Understand ng+ReportDeta ls
              case (So (page), _, _, So ("t cket"), So ("subm "))
                   f page.startsW h("report_") =>
                Seq(T etReportServer)
              case (So ("prof le"), _, _, _, So ("block")) =>
                Seq(Prof leBlock)
              case (So ("prof le"), _, _, _, So ("unblock")) =>
                Seq(Prof leUnblock)
              case (So ("prof le"), _, _, _, So ("mute_user")) =>
                Seq(Prof leMute)
              case (So ("prof le"), _, _, _, So ("report")) =>
                Seq(Prof leReport)
              case (So ("prof le"), _, _, _, So ("show")) =>
                Seq(Prof leShow)
              case (_, _, _, So ("follow"), So ("cl ck")) => Seq(T etFollowAuthor)
              case (_, _, _, _, So ("follow")) => Seq(T etFollowAuthor, Prof leFollow)
              case (_, _, _, So ("unfollow"), So ("cl ck")) => Seq(T etUnfollowAuthor)
              case (_, _, _, _, So ("unfollow")) => Seq(T etUnfollowAuthor)
              case (_, _, _, So ("block"), So ("cl ck")) => Seq(T etBlockAuthor)
              case (_, _, _, So ("unblock"), So ("cl ck")) => Seq(T etUnblockAuthor)
              case (_, _, _, So ("mute"), So ("cl ck")) => Seq(T etMuteAuthor)
              case (_, _, _, So (ele nt), So ("cl ck"))  f  sT etCl ckEvent(ele nt) =>
                Seq(T etCl ck)
              case (_, _, _, _, So ("prof le_cl ck")) => Seq(T etCl ckProf le, Prof leCl ck)
              case (_, _, _, _, So ("share_ nu_cl ck")) => Seq(T etCl ckShare)
              case (_, _, _, _, So ("copy_l nk")) => Seq(T etShareV aCopyL nk)
              case (_, _, _, _, So ("share_v a_dm")) => Seq(T etCl ckSendV aD rect ssage)
              case (_, _, _, _, So ("bookmark")) => Seq(T etShareV aBookmark, T etBookmark)
              case (_, _, _, _, So ("unbookmark")) => Seq(T etUnbookmark)
              case (_, _, _, _, So ("hashtag_cl ck")) |
                  // T  scr be  s tr ggered on mob le platforms (andro d/ phone) w n user cl ck on hashtag  n a t et.
                  (_, _, _, So ("hashtag"), So ("search")) =>
                Seq(T etCl ckHashtag)
              case (_, _, _, _, So ("open_l nk")) => Seq(T etOpenL nk)
              case (_, _, _, _, So ("take_screenshot")) => Seq(T etTakeScreenshot)
              case (_, _, _, So ("feedback_notrelevant"), So ("cl ck")) =>
                Seq(T etNotRelevant)
              case (_, _, _, So ("feedback_notrelevant"), So ("undo")) =>
                Seq(T etUndoNotRelevant)
              case (_, _, _, _, So ("follow_attempt")) => Seq(Prof leFollowAttempt)
              case (_, _, _, _, So ("favor e_attempt")) => Seq(T etFavor eAttempt)
              case (_, _, _, _, So ("ret et_attempt")) => Seq(T etRet etAttempt)
              case (_, _, _, _, So ("reply_attempt")) => Seq(T etReplyAttempt)
              case (_, _, _, _, So ("log n")) => Seq(CTALog nCl ck)
              case (So ("log n"), _, _, _, So ("show")) => Seq(CTALog nStart)
              case (So ("log n"), _, _, _, So ("success")) => Seq(CTALog nSuccess)
              case (_, _, _, _, So ("s gnup")) => Seq(CTAS gnupCl ck)
              case (So ("s gnup"), _, _, _, So ("success")) => Seq(CTAS gnupSuccess)
              case // Andro d app runn ng  n t  background
                  (So ("not f cat on"), So ("status_bar"), None, _, So ("background_open")) |
                  // Andro d app runn ng  n t  foreground
                  (So ("not f cat on"), So ("status_bar"), None, _, So ("open")) |
                  //  OS app runn ng  n t  background
                  (So ("not f cat on"), So ("not f cat on_center"), None, _, So ("open")) |
                  //  OS app runn ng  n t  foreground
                  (None, So ("toasts"), So ("soc al"), So ("favor e"), So ("open")) |
                  // m5
                  (So ("app"), So ("push"), _, _, So ("open")) =>
                Seq(Not f cat onOpen)
              case (So ("ntab"), So ("all"), So ("urt"), _, So ("nav gate")) =>
                Seq(Not f cat onCl ck)
              case (So ("ntab"), So ("all"), So ("urt"), _, So ("see_less_often")) =>
                Seq(Not f cat onSeeLessOften)
              case (So ("not f cat on"), So ("status_bar"), None, _, So ("background_d sm ss")) |
                  (So ("not f cat on"), So ("status_bar"), None, _, So ("d sm ss")) | (
                    So ("not f cat on"),
                    So ("not f cat on_center"),
                    None,
                    _,
                    So ("d sm ss")
                  ) =>
                Seq(Not f cat onD sm ss)
              case (_, _, _, So ("typea ad"), So ("cl ck")) => Seq(Typea adCl ck)
              case (So ("search"), _, So (component), _, So ("cl ck"))
                   f component == "relevance_prompt_module" || component == "d d_ _f nd_ _module" =>
                Seq(FeedbackPromptSubm )
              case (So ("app"), So ("enter_background"), _, _, So ("beco _ nact ve"))
                   f logEvent.logBase
                    .flatMap(_.cl entApp d)
                    .ex sts(val dUAS osCl ent ds.conta ns(_)) =>
                Seq(AppEx )
              case (So ("app"), _, _, _, So ("beco _ nact ve"))
                   f logEvent.logBase
                    .flatMap(_.cl entApp d)
                    .ex sts(val dUASAndro dCl ent ds.conta ns(_)) =>
                Seq(AppEx )
              case (_, _, So ("gallery"), So ("photo"), So (" mpress on")) =>
                Seq(T etGallery mpress on)
              case (_, _, _, _, _)
                   f T etDeta ls mpress on. sT etDeta ls mpress on(logEvent.eventNa space) =>
                Seq(T etDeta ls mpress on)
              case _ => N l
            }
        }
        act onTypesPerEvent.map(_.toUn f edUserAct on(logEvent))
      }.flatten

  def should gnoreCl entEvent(eventNa space: Opt on[EventNa space]): Boolean =
    eventNa space.ex sts { na  =>
      (na .page, na .sect on, na .component, na .ele nt, na .act on) match {
        case (So ("ddg"), _, _, _, So ("exper  nt")) => true
        case (So ("q g_ranker"), _, _, _, _) => true
        case (So ("t  l nem xer"), _, _, _, _) => true
        case (So ("t  l neserv ce"), _, _, _, _) => true
        case (So ("t etconvosvc"), _, _, _, _) => true
        case _ => false
      }
    }
}
