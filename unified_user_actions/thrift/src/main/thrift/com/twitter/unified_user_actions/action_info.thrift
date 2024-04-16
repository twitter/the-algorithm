na space java com.tw ter.un f ed_user_act ons.thr ftjava
#@na space scala com.tw ter.un f ed_user_act ons.thr ftscala
#@na space strato com.tw ter.un f ed_user_act ons

 nclude "com/tw ter/cl entapp/gen/cl ent_app.thr ft"
 nclude "com/tw ter/reportflow/report_flow_logs.thr ft"
 nclude "com/tw ter/soc algraph/soc al_graph_serv ce_wr e_log.thr ft"
 nclude "com/tw ter/g zmoduck/user_serv ce.thr ft"

/*
 * Act onType  s typ cally a three part enum cons st ng of
 * [Or g n][ em Type][Act on Na ]
 *
 * [Or g n]  s usually "cl ent" or "server" to  nd cate how t  act on was der ved.
 *
 * [ em Type]  s s ngular and refers to t  shorthand vers on of t  type of
 *  em (e.g. T et, Prof le, Not f cat on  nstead of T et nfo, Prof le nfo, Not f cat on nfo)
 * t  act on occurred on. Act on types and  em types should be 1:1, and w n an act on can be
 * perfor d on mult ple types of  ems, cons der granular act on types.
 *
 * [Act on Na ]  s t  descr pt ve na  of t  user act on (e.g. favor e, render  mpress on);
 * act on na s should correspond to U  act ons / ML labels (wh ch are typ cally based on user
 * behav or from U  act ons)
 *
 * Below are gu del nes around nam ng of act on types:
 * a) W n an act on  s coupled to a product surface, be conc se  n nam ng such that t 
 * comb nat on of  em type and act on na  captures t  user behav or for t  act on  n t  U . For example,
 * for an open on a Not f cat on  n t  PushNot f cat on product surface that  s parsed from cl ent events,
 * cons der Cl entNot f cat onOpen because t   em Not f cat on and t  act on na  Open conc sely represent
 * t  act on, and t  product surface PushNot f cat on can be  dent f ed  ndependently.
 *
 * b)    s OK to use gener c na s l ke Cl ck  f needed to d st ngu sh from anot r act on OR
 *    s t  best way to character ze an act on conc sely w hout confus on.
 * For example, for Cl entT etCl ckReply, t  refers to actually cl ck ng on t  Reply button but not
 * Reply ng, and    s OK to  nclude Cl ck. Anot r example  s Cl ck on a T et anyw re (ot r than t  fav,
 * reply, etc. buttons), wh ch leads to t  T etDeta ls page. Avo d gener c act on na s l ke Cl ck  f
 * t re  s a more spec f c U  aspect to reference and Cl ck  s  mpl ed, e.g. Cl entT etReport  s
 * preferred over Cl entT etCl ckReport and Cl entT etReportCl ck.
 *
 * c) Rely on vers on ng found  n t  or g n w n    s present for act on na s. For example,
 * a "V2 mpress on"  s na d as such because  n behav oral cl ent events, t re  s
 * a "v2 mpress" f eld. See go/bce-v2 mpress for more deta ls.
 *
 * d) T re  s a d st nct on bet en "UndoAct on" and "Un{Act on}" act on types.
 * An "UndoAct on"  s f red w n a user cl cks on t  expl c  "Undo" button, after t y perform an act on
 * T  "Undo" button  s a U  ele nt that may be temporary, e.g.,
 *  - t  user wa ed too long to cl ck t  button, t  button d sappears from t  U  (e.g., Undo for Mute, Block)
 *  - t  button does not d sappear due to t  out, but beco s unava lable after t  user closes a tab
 *    (e.g, Undo for Not nterested n, NotAboutTop c)
 * Examples:
    - Cl entProf leUndoMute: a user cl cks t  "Undo" button after mut ng a Prof le
    - Cl entT etUndoNot nterested n: a users cl cks t  "Undo" button
      after cl ck ng "Not  nterested  n t  T et" button  n t  caret  nu of a T et
 * An "Un{Act on}"  s f red w n a user reverses a prev ous act on, not by expl c ly cl ck ng an "Undo" button,
 * but through so  ot r act on that allows t m to revert.
 * Examples:
 *  - Cl entProf leUnmute: a user cl cks t  "Unmute" button from t  caret  nu of t  Prof le t y prev ously muted
 *  - Cl entT etUnfav: a user unl kes a t et by cl ck ng on l ke button aga n
 *
 * Examples: ServerT etFav, Cl entT etRender mpress on, Cl entNot f cat onSeeLessOften
 *
 * See go/uua-act on-type for more deta ls.
 */
enum Act onType {
  // 0 - 999 used for act ons der ved from Server-s de s ces (e.g. T  l neserv ce, T etyp e)
  // NOTE: Please match values for correspond ng server / cl ent enum  mbers (w h offset 1000).
  ServerT etFav   = 0
  ServerT etUnfav = 1
  // Reserve 2 and 3 for ServerT etL nger mpress on and ServerT etRender mpress on

  ServerT etCreate = 4
  ServerT etReply = 5
  ServerT etQuote = 6
  ServerT etRet et = 7
  // sk p 8-10 s nce t re are no server equ valents for Cl ckCreate, Cl ckReply, Cl ckQuote
  // reserve 11-16 for server v deo engage nts

  ServerT etDelete = 17      // User deletes a default t et
  ServerT etUnreply = 18     // User deletes a reply t et
  ServerT etUnquote = 19     // User deletes a quote t et
  ServerT etUnret et = 20   // User removes an ex st ng ret et
  // User ed s a t et. Ed  w ll create a new t et w h ed edT et d =  d of t  or g nal t et
  // T  or g nal t et or t  new t et from ed  can only be a default or quote t et.
  // A user can ed  a default t et to beco  a quote t et (by add ng t  l nk to anot r T et),
  // or ed  a quote t et to remove t  quote and make   a default t et.
  // Both t   n  al t et and t  new t et created from t  ed  can be ed ed, and each t   t 
  // new ed  w ll create a new t et. All subsequent ed s would have t  sa   n  al t et  d
  // as t  T et nfo.ed edT et d.
  // e.g. create T et A, ed  T et A -> T et B, ed  T et B -> T et C
  //  n  al t et  d for both T et B anc T et C would be T et A
  ServerT etEd  = 21
  // sk p 22 for delete an ed   f   want to add    n t  future

  // reserve 30-40 for server top c act ons

  // 41-70 reserved for all negat ve engage nts and t  related pos  ve engage nts
  // For example, Follow and Unfollow, Mute and Unmute
  // T   s f red w n a user cl ck "Subm " at t  end of a "Report T et" flow
  // Cl entT etReport = 1041  s scr bed by  althCl ent team, on t  cl ent s de
  // T   s scr bed by spamacaw, on t  server s de
  // T y can be jo ned on reportFlow d
  // See https://confluence.tw ter.b z/pages/v ewpage.act on?spaceKey=HEALTH&t le=Understand ng+ReportDeta ls
  ServerT etReport = 41

  // reserve 42 for ServerT etNot nterested n
  // reserve 43 for ServerT etUndoNot nterested n
  // reserve 44 for ServerT etNotAboutTop c
  // reserve 45 for ServerT etUndoNotAboutTop c

  ServerProf leFollow = 50       // User follows a Prof le
  ServerProf leUnfollow = 51     // User unfollows a Prof le
  ServerProf leBlock = 52        // User blocks a Prof le
  ServerProf leUnblock = 53      // User unblocks a Prof le
  ServerProf leMute = 54         // User mutes a Prof le
  ServerProf leUnmute = 55       // User unmutes a Prof le
  // User reports a Prof le as Spam / Abuse
  // T  user act on type  ncludes Prof leReportAsSpam and Prof leReportAsAbuse
  ServerProf leReport = 56
  // reserve 57 for ServerProf leUnReport
  // reserve 56-70 for server soc al graph act ons

  // 71-90 reserved for cl ck-based events
  // reserve 71 for ServerT etCl ck

  // 1000 - 1999 used for act ons der ved from Cl ent-s de s ces (e.g. Cl ent Events, BCE)
  // NOTE: Please match values for correspond ng server / cl ent enum  mbers (w h offset 1000).
  // 1000 - 1499 used for legacy cl ent events
  Cl entT etFav = 1000
  Cl entT etUnfav = 1001
  Cl entT etL nger mpress on = 1002
  // Please note that: Render  mpress on for quoted T ets would em  2 events:
  // 1 for t  quot ng T et and 1 for t  or g nal T et!!!
  Cl entT etRender mpress on = 1003
  // 1004 reserved for Cl entT etCreate
  // T   s "Send Reply" event to  nd cate publ sh ng of a reply T et as opposed to cl ck ng
  // on t  reply button to  n  ate a reply T et (captured  n Cl entT etCl ckReply).
  // T  d fferences bet en t  and t  ServerT etReply are:
  // 1) ServerT etReply already has t  new T et  d 2) A sent reply may be lost dur ng transfer
  // over t  w re and thus may not end up w h a follow-up ServerT etReply.
  Cl entT etReply = 1005
  // T   s t  "send quote" event to  nd cate publ sh ng of a quote t et as opposed to cl ck ng
  // on t  quote button to  n  ate a quote t et (captured  n Cl entT etCl ckQuote).
  // T  d fferences bet en t  and t  ServerT etQuote are:
  // 1) ServerT etQuote already has t  new T et  d 2) A sent quote may be lost dur ng transfer
  // over t  w re and thus may not end up w h a follow-up ServerT etQuote.
  Cl entT etQuote = 1006
  // T   s t  "ret et" event to  nd cate publ sh ng of a ret et.
  Cl entT etRet et = 1007
  // 1008 reserved for Cl entT etCl ckCreate
  // T   s user cl ck ng on t  Reply button not actually send ng a reply T et,
  // thus t  na  Cl ckReply
  Cl entT etCl ckReply = 1009
  // T   s user cl ck ng t  Quote/Ret etW hCom nt button not actually send ng t  quote,
  // thus t  na  Cl ckQuote
  Cl entT etCl ckQuote = 1010

  // 1011 - 1016: Refer to go/c -scr b ng and go/ nteract on-event-spec for deta ls
  // T   s f red w n playback reac s 25% of total track durat on. Not val d for l ve v deos.
  // For loop ng playback, t   s only f red once and does not reset at loop boundar es.
  Cl entT etV deoPlayback25 = 1011
  // T   s f red w n playback reac s 50% of total track durat on. Not val d for l ve v deos.
  // For loop ng playback, t   s only f red once and does not reset at loop boundar es.
  Cl entT etV deoPlayback50 = 1012
  // T   s f red w n playback reac s 75% of total track durat on. Not val d for l ve v deos.
  // For loop ng playback, t   s only f red once and does not reset at loop boundar es.
  Cl entT etV deoPlayback75 = 1013
  // T   s f red w n playback reac s 95% of total track durat on. Not val d for l ve v deos.
  // For loop ng playback, t   s only f red once and does not reset at loop boundar es.
  Cl entT etV deoPlayback95 = 1014
  // T   f f red w n t  v deo has been played  n non-prev ew
  // ( .e. not autoplay ng  n t  t  l ne) mode, and was not started v a auto-advance.
  // For loop ng playback, t   s only f red once and does not reset at loop boundar es.
  Cl entT etV deoPlayFromTap = 1015
  // T   s f red w n 50% of t  v deo has been on-screen and play ng for 10 consecut ve seconds
  // or 95% of t  v deo durat on, wh c ver co s f rst.
  // For loop ng playback, t   s only f red once and does not reset at loop boundar es.
  Cl entT etV deoQual yV ew = 1016
  // F red w n e  r v ew_threshold or play_from_tap  s f red.
  // For loop ng playback, t   s only f red once and does not reset at loop boundar es.
  Cl entT etV deoV ew = 1109
  // F red w n 50% of t  v deo has been on-screen and play ng for 2 consecut ve seconds,
  // regardless of v deo durat on.
  // For loop ng playback, t   s only f red once and does not reset at loop boundar es.
  Cl entT etV deoMrcV ew = 1110
  // F red w n t  v deo  s:
  // - Play ng for 3 cumulat ve (not necessar ly consecut ve) seconds w h 100%  n v ew for loop ng v deo.
  // - Play ng for 3 cumulat ve (not necessar ly consecut ve) seconds or t  v deo durat on, wh c ver co s f rst, w h 100%  n v ew for non-loop ng v deo.
  // For loop ng playback, t   s only f red once and does not reset at loop boundar es.
  Cl entT etV deoV ewThreshold = 1111
  // F red w n t  user cl cks a gener c ‘v s  url’ call to act on.
  Cl entT etV deoCtaUrlCl ck = 1112
  // F red w n t  user cl cks a ‘watch now’ call to act on.
  Cl entT etV deoCtaWatchCl ck = 1113

  // 1017 reserved for Cl entT etDelete
  // 1018-1019 for Cl ent delete a reply and delete a quote  f   want to add t m  n t  future

  // T   s f red w n a user cl cks on "Undo ret et" after re-t et ng a t et
  Cl entT etUnret et = 1020
  // 1021 reserved for Cl entT etEd 
  // 1022 reserved for Cl ent delete an ed   f   want to add    n t  future
  // T   s f red w n a user cl cks on a photo w h n a t et and t  photo expands to f 
  // t  screen.
  Cl entT etPhotoExpand = 1023

  // T   s f red w n a user cl cks on a prof le  nt on  ns de a t et.
  Cl entT etCl ck nt onScreenNa  = 1024

  // 1030 - 1035 for top c act ons
  // T re are mult ple cases:
  // 1. Follow from t  Top c page (or so-called land ng page)
  // 2. Cl ck on T et's caret  nu of "Follow (t  top c)",   needs to be:
  //    1) user follows t  Top c already (ot rw se t re  s no "Follow"  nu by default),
  //    2) and cl cked on t  "Unfollow Top c" f rst.
  Cl entTop cFollow = 1030
  // T re are mult ple cases:
  // 1. Unfollow from t  Top c page (or so-called land ng page)
  // 2. Cl ck on T et's caret  nu of "Unfollow (t  top c)"  f t  user has already follo d
  //    t  top c.
  Cl entTop cUnfollow = 1031
  // T   s f red w n t  user cl cks t  "x"  con next to t  top c on t  r t  l ne,
  // and cl cks "Not  nterested  n {TOP C}"  n t  pop-up prompt
  // Alternat vely, t y can also cl ck "See more" button to v s  t  top c page, and cl ck "Not  nterested" t re.
  Cl entTop cNot nterested n = 1032
  // T   s f red w n t  user cl cks t  "Undo" button after cl ck ng "x" or "Not  nterested" on a Top c
  // wh ch  s captured  n Cl entTop cNot nterested n
  Cl entTop cUndoNot nterested n = 1033

  // 1036-1070 reserved for all negat ve engage nts and t  related pos  ve engage nts
  // For example, Follow and Unfollow, Mute and Unmute

  // T   s f red w n a user cl cks on  "T  T et's not  lpful" flow  n t  caret  nu
  // of a T et result on t  Search Results Page
  Cl entT etNot lpful = 1036
  // T   s f red w n a user cl cks Undo after cl ck ng on
  // "T  T et's not  lpful" flow  n t  caret  nu of a T et result on t  Search Results Page
  Cl entT etUndoNot lpful = 1037
  // T   s f red w n a user starts and/or completes t  "Report T et" flow  n t  caret  nu of a T et
  Cl entT etReport = 1041
  /*
   * 1042-1045 refers to act ons that are related to t 
   * "Not  nterested  n" button  n t  caret  nu of a T et.
   *
   * Cl entT etNot nterested n  s f red w n a user cl cks t 
   * "Not  nterested  n t  T et" button  n t  caret  nu of a T et.
   * A user can undo t  Cl entT etNot nterested n act on by cl ck ng t 
   * "Undo" button that appears as a prompt  n t  caret  nu, result ng
   *  n Cl entT etUndoNot nterested n be ng f red.
   *  f a user chooses to not undo and proceed, t y are g ven mult ple cho ces
   *  n a prompt to better docu nt why t y are not  nterested  n a T et.
   * For example,  f a T et  s not about a Top c, a user can cl ck
   * "T  T et  s not about {TOP C}"  n t  prov ded prompt, result ng  n
   *  n Cl entT etNotAboutTop c be ng f red.
   * A user can undo t  Cl entT etNotAboutTop c act on by cl ck ng t  "Undo"
   * button that appears as a subsequent prompt  n t  caret  nu. Undo ng t  act on
   * results  n t  prev ous U  state, w re t  user had only marked "Not  nterested  n" and
   * can st ll undo t  or g nal Cl entT etNot nterested n act on.
   * S m larly a user can select "T  T et  sn't recent" act on result ng  n Cl entT etNotRecent
   * and   could undo t  act on  m d ately wh ch results  n Cl entT etUndoNotRecent
   * S m larly a user can select "Show fe r t ets from" act on result ng  n Cl entT etSeeFe r
   * and   could undo t  act on  m d ately wh ch results  n Cl entT etUndoSeeFe r
   */
  Cl entT etNot nterested n = 1042
  Cl entT etUndoNot nterested n = 1043
  Cl entT etNotAboutTop c = 1044
  Cl entT etUndoNotAboutTop c = 1045
  Cl entT etNotRecent = 1046
  Cl entT etUndoNotRecent = 1047
  Cl entT etSeeFe r = 1048
  Cl entT etUndoSeeFe r = 1049

  // T   s f red w n a user follows a prof le from t 
  // prof le page  ader / people module and people tab on t  Search Results Page / s debar on t  Ho  page
  // A Prof le can also be follo d w n a user cl cks follow  n t  caret  nu of a T et
  // or follow button on hover ng on prof le avatar, wh ch  s captured  n Cl entT etFollowAuthor = 1060
  Cl entProf leFollow = 1050
  // reserve 1050/1051 for cl ent s de Follow/Unfollow
  // T   s f red w n a user cl cks Block  n a Prof le page
  // A Prof le can also be blocked w n a user cl cks Block  n t  caret  nu of a T et,
  // wh ch  s captured  n Cl entT etBlockAuthor = 1062
  Cl entProf leBlock = 1052
  // T   s f red w n a user cl cks unblock  n a pop-up prompt r ght after block ng a prof le
  //  n t  prof le page or cl cks unblock  n a drop-down  nu  n t  prof le page.
  Cl entProf leUnblock = 1053
  // T   s f red w n a user cl cks Mute  n a Prof le page
  // A Prof le can also be muted w n a user cl cks Mute  n t  caret  nu of a T et, wh ch  s captured  n Cl entT etMuteAuthor = 1064
  Cl entProf leMute = 1054
  // reserve 1055 for cl ent s de Unmute
  // T   s f red w n a user cl cks "Report User" act on from user prof le page
  Cl entProf leReport = 1056

  // reserve 1057 for Cl entProf leUnreport

  // T   s f red w n a user cl cks on a prof le from all modules except t ets
  // (eg: People Search / people module  n Top tab  n Search Result Page
  // For t ets, t  cl ck  s captured  n Cl entT etCl ckProf le
  Cl entProf leCl ck = 1058
  // reserve 1059-1070 for cl ent soc al graph act ons

  // T   s f red w n a user cl cks Follow  n t  caret  nu of a T et or hovers on t  avatar of t  t et
  // author and cl cks on t  Follow button. A prof le can also be follo d by cl ck ng t  Follow button on t 
  // Prof le page and conf rm, wh ch  s captured  n Cl entProf leFollow. T  event em s two  ems, one of user type
  // and anot r of t et type, s nce t  default  mple ntat on of BaseCl entEvent only looks for T et type,
  // t  ot r  em  s dropped wh ch  s t  expected behav  
  Cl entT etFollowAuthor = 1060

  // T   s f red w n a user cl cks Unfollow  n t  caret  nu of a T et or hovers on t  avatar of t  t et
  // author and cl cks on t  Unfollow button. A prof le can also be unfollo d by cl ck ng t  Unfollow button on t 
  // Prof le page and conf rm, wh ch w ll be captured  n Cl entProf leUnfollow. T  event em s two  ems, one of user type
  // and anot r of t et type, s nce t  default  mple ntat on of BaseCl entEvent only looks for T et type,
  // t  ot r  em  s dropped wh ch  s t  expected behav  
  Cl entT etUnfollowAuthor = 1061

  // T   s f red w n a user cl cks Block  n t   nu of a T et to block t  Prof le that
  // authored t  T et. A Prof le can also be blocked  n t  Prof le page, wh ch  s captured
  //  n Cl entProf leBlock = 1052
  Cl entT etBlockAuthor = 1062
  // T   s f red w n a user cl cks unblock  n a pop-up prompt r ght after block ng an author
  //  n t  drop-down  nu of a t et
  Cl entT etUnblockAuthor = 1063

  // T   s f red w n a user cl cks Mute  n t   nu of a T et to block t  Prof le that
  // authored t  T et. A Prof le can also be muted  n t  Prof le page, wh ch  s captured  n Cl entProf leMute = 1054
  Cl entT etMuteAuthor = 1064

  // reserve 1065 for Cl entT etUnmuteAuthor

  // 1071-1090 reserved for cl ck-based events
  // cl ck-based events are def ned as cl cks on a U  conta ner (e.g., t et, prof le, etc.), as opposed to clearly na d
  // button or  nu (e.g., follow, block, report, etc.), wh ch requ res a spec f c act on na  than "cl ck".

  // T   s f red w n a user cl cks on a T et to open t  T et deta ls page. Note that for
  // T ets  n t  Not f cat on Tab product surface, a cl ck can be reg stered d fferently
  // depend ng on w t r t  T et  s a rendered T et (a cl ck results  n Cl entT etCl ck)
  // or a wrapper Not f cat on (a cl ck results  n Cl entNot f cat onCl ck).
  Cl entT etCl ck = 1071
  // T   s f red w n a user cl cks to v ew t  prof le page of a user from a t et
  // Conta ns a T et nfo of t  t et
  Cl entT etCl ckProf le = 1072
  // T   s f red w n a user cl cks on t  "share"  con on a T et to open t  share  nu.
  // T  user may or may not proceed and f n sh shar ng t  T et.
  Cl entT etCl ckShare = 1073
  // T   s f red w n a user cl cks "Copy l nk to T et"  n a  nu appeared after h t ng
  // t  "share"  con on a T et OR w n a user selects share_v a -> copy_l nk after long-cl ck
  // a l nk  ns de a t et on a mob le dev ce
  Cl entT etShareV aCopyL nk = 1074
  // T   s f red w n a user cl cks "Send v a D rect  ssage" after
  // cl ck ng on t  "share"  con on a T et to open t  share  nu.
  // T  user may or may not proceed and f n sh Send ng t  DM.
  Cl entT etCl ckSendV aD rect ssage = 1075
  // T   s f red w n a user cl cks "Bookmark" after
  // cl ck ng on t  "share"  con on a T et to open t  share  nu.
  Cl entT etShareV aBookmark = 1076
  // T   s f red w n a user cl cks "Remove T et from Bookmarks" after
  // cl ck ng on t  "share"  con on a T et to open t  share  nu.
  Cl entT etUnbookmark = 1077
   // T   s f red w n a user cl cks on t  hashtag  n a T et.
  // T  cl ck on hashtag  n "What's happen ng" sect on g ves   ot r scr be '*:*:s debar:*:trend:search'
  // Currenly   are only f lter ng for  emType=T et. T re are ot r  ems present  n t  event w re  emType = user
  // but those  ems are  n dual-events (events w h mult ple  emTypes) and happen w n   cl ck on a hashtag  n a T et from so one's prof le,
  //  nce   are  gnor ng those  emType and only keep ng  emType=T et.
  Cl entT etCl ckHashtag = 1078
  // T   s f red w n a user cl cks "Bookmark" after cl ck ng on t  "share"  con on a T et to open t  share  nu, or
  // w n a user cl cks on t  'bookmark'  con on a T et (bookmark  con  s ava lable to  os only as of March 2023).
  // T etBookmark and T etShareByBookmark log t  sa  events but serve for  nd v dual use cases.
  Cl entT etBookmark = 1079

  // 1078 - 1089 for all Share related act ons.

  // T   s f red w n a user cl cks on a l nk  n a t et.
  // T  l nk could be d splayed as a URL or embedded  n a component such as an  mage or a card  n a t et.
  Cl entT etOpenL nk = 1090
  // T   s f red w n a user takes screenshot.
  // T   s ava lable for mob le cl ents only.
  Cl entT etTakeScreenshot = 1091

  // 1100 - 1101: Refer to go/c -scr b ng and go/ nteract on-event-spec for deta ls
  // F red on t  f rst t ck of a track regardless of w re  n t  v deo    s play ng.
  // For loop ng playback, t   s only f red once and does not reset at loop boundar es.
  Cl entT etV deoPlaybackStart = 1100
  // F red w n playback reac s 100% of total track durat on.
  // Not val d for l ve v deos.
  // For loop ng playback, t   s only f red once and does not reset at loop boundar es.
  Cl entT etV deoPlaybackComplete = 1101

  // A user can select "T  T et  sn't relevant" act on result ng  n Cl entT etNotRelevant
  // and t y could undo t  act on  m d ately wh ch results  n Cl entT etUndoNotRelevant
  Cl entT etNotRelevant = 1102
  Cl entT etUndoNotRelevant = 1103

  // A gener c act on type to subm  feedback for d fferent modules /  ems ( T ets / Search Results )
  Cl entFeedbackPromptSubm  = 1104

  // T   s f red w n a user prof le  s open  n a Prof le page
  Cl entProf leShow = 1105

  /*
   * T   s tr ggered w n a user ex s t  Tw ter platform. T  amount of t  t   spent on t 
   * platform  s recorded  n ms that can be used to compute t  User Act ve Seconds (UAS).
   */
  Cl entAppEx  = 1106

  /*
   * For "card" related act ons
   */
  Cl entCardCl ck = 1107
  Cl entCardOpenApp = 1108
  Cl entCardApp nstallAttempt = 1114
  Cl entPollCardVote = 1115

  /*
   * T   mpress ons 1121-1123 toget r w h t  Cl entT etRender mpress on 1003 are used by V ewCount
   * and Un f edEngage ntCounts as Engage ntType.D splayed and Engage ntType.Deta ls.
   *
   * For def n  ons, please refer to https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/common- nternal/analyt cs/cl ent-event-ut l/src/ma n/java/com/tw ter/common_ nternal/analyt cs/cl ent_event_ut l/T et mpress onUt ls.java?L14&subtree=true
   */
  Cl entT etGallery mpress on = 1121
  Cl entT etDeta ls mpress on = 1122

  /**
   *  T   s f red w n a user  s logged out and follows a prof le from t 
   *  prof le page / people module from  b.
   *  One can only try to follow from  b because  OS and Andro d do not support logged out brows ng as of Jan 2023.
   */
  Cl entProf leFollowAttempt = 1200

  /**
   * T   s f red w n a user  s logged out and fav  e a t et from  b.
   * One can only try to fav  e from  b,  OS and Andro d do not support logged out brows ng
   */
  Cl entT etFavor eAttempt = 1201

  /**
   * T   s f red w n a user  s logged out and Ret et a t et from  b.
   * One can only try to fav  e from  b,  OS and Andro d do not support logged out brows ng
   */
  Cl entT etRet etAttempt = 1202

  /**
   * T   s f red w n a user  s logged out and reply on t et from  b.
   * One can only try to fav  e from  b,  OS and Andro d do not support logged out brows ng
   */
  Cl entT etReplyAttempt = 1203

  /**
   * T   s f red w n a user  s logged out and cl cks on log n button.
   * Currently seem to be generated only on [m5, L eNat veWrapper]
  */
  Cl entCTALog nCl ck = 1204
  /**
   * T   s f red w n a user  s logged out and log n w ndow  s shown.
   */
  Cl entCTALog nStart = 1205
  /**
   * T   s f red w n a user  s logged out and log n  s successful.
  */
  Cl entCTALog nSuccess = 1206

  /**
   * T   s f red w n a user  s logged out and cl cks on s gnup button.
   */
  Cl entCTAS gnupCl ck = 1207

  /**
   * T   s f red w n a user  s logged out and s gnup  s successful.
   */
  Cl entCTAS gnupSuccess = 1208
  // 1400 - 1499 for product surface spec f c act ons
  // T   s f red w n a user opens a Push Not f cat on
  Cl entNot f cat onOpen = 1400
  // T   s f red w n a user cl cks on a Not f cat on  n t  Not f cat on Tab
  Cl entNot f cat onCl ck = 1401
  // T   s f red w n a user taps t  "See Less Often" caret  nu  em of a Not f cat on  n t  Not f cat on Tab
  Cl entNot f cat onSeeLessOften = 1402
  // T   s f red w n a user closes or sw pes away a Push Not f cat on
  Cl entNot f cat onD sm ss = 1403

  // 1420 - 1439  s reserved for Search Results Page related act ons
  // 1440 - 1449  s reserved for Typea ad related act ons

  // T   s f red w n a user cl cks on a typea ad suggest on(quer es, events, top cs, users)
  //  n a drop-down  nu of a search box or a t et compose box.
  Cl entTypea adCl ck = 1440

  // 1500 - 1999 used for behav oral cl ent events
  // T et related  mpress ons
  Cl entT etV2 mpress on = 1500
  /* Fullscreen  mpress ons
   *
   * Andro d cl ent w ll always log fullscreen_v deo  mpress ons, regardless of t   d a type
   *  .e. v deo,  mage, MM w ll all be logged as fullscreen_v deo
   *
   *  OS cl ents w ll log fullscreen_v deo or fullscreen_ mage depend ng on t   d a type
   * on d splay w n t  user ex s fullscreen.  .e.
   * -  mage t et => fullscreen_ mage
   * - v deo t et => fullscreen_v deo
   * - MM t et => fullscreen_v deo   f user ex s fullscreen from t  v deo
   *            => fullscreen_ mage   f user ex s fullscreen from t   mage
   *
   *  b cl ents w ll always log fullscreen_ mage  mpress ons, regardless of t   d a type
   *
   * References
   * https://docs.google.com/docu nt/d/1oEt9_Gtz34cmO_JWNag5YKKEq4Q7cJFL-nbHOmhnq1Y
   * https://docs.google.com/docu nt/d/1V_7TbfPvTQgtE_91r5SubD7n78JsVR_ ToW59gOMrfQ
   */
  Cl entT etV deoFullscreenV2 mpress on = 1501
  Cl entT et mageFullscreenV2 mpress on = 1502
  // Prof le related  mpress ons
  Cl entProf leV2 mpress on = 1600
  /*
   * Ema l Not f cat ons: T se are act ons taken by t  user  n response to Y  H ghl ghts ema l
   * Cl entT etEma lCl ck refers to t  act on Not f cat onType.Cl ck
   */
  Cl entT etEma lCl ck = 5001

  /*
   * User create v a G zmoduck
   */
  ServerUserCreate = 6000
  ServerUserUpdate = 6001
  /*
   * Ads callback engage nts
   */
  /*
   * T  engage nt  s generated w n a user Favs a promoted T et.
   */
  ServerPromotedT etFav = 7000
  /*
   * T  engage nt  s generated w n a user Unfavs a promoted T et that t y prev ously Faved.
   */
  ServerPromotedT etUnfav = 7001
  ServerPromotedT etReply = 7002
  ServerPromotedT etRet et = 7004
  /*
   * T  block could be perfor d from t  promoted t et or on t  promoted t et's author's prof le
   * ads_spend_event data shows major y (~97%) of blocks have an assoc ated promoted t et  d
   * So for now   assu  t  blocks are largely perfor d from t  t et and follow ng nam ng convent on of Cl entT etBlockAuthor
   */
  ServerPromotedT etBlockAuthor = 7006
  ServerPromotedT etUnblockAuthor = 7007
  /*
   * T   s w n a user cl cks on t  Conversat onal Card  n t  Promoted T et wh ch
   * leads to t  T et Compose page. T  user may or may not send t  new T et.
   */
  ServerPromotedT etComposeT et = 7008
  /*
   * T   s w n a user cl cks on t  Promoted T et to v ew  s deta ls/repl es.
   */
  ServerPromotedT etCl ck = 7009
  /*
   * T  v deo ads engage nts are d v ded  nto two sets: V DEO_CONTENT_* and V DEO_AD_*. T se engage nts
   * have s m lar def n  ons. V DEO_CONTENT_* engage nts are f red for v deos that are part of
   * a T et. V DEO_AD_* engage nts are f red for a preroll ad. A preroll ad can play on a promoted
   * T et or on an organ c T et. go/preroll-match ng for more  nformat on.
   *
   * 7011-7013: A Promoted Event  s f red w n playback reac s 25%, 50%, 75% of total track durat on.
   * T   s for t  v deo on a promoted T et.
   * Not val d for l ve v deos. Refer go/avscr b ng.
   * For a v deo that has a preroll ad played before  , t   tadata w ll conta n  nformat on about
   * t  preroll ad as  ll as t  v deo  self. T re w ll be no preroll  tadata  f t re was no
   * preroll ad played.
   */
  ServerPromotedT etV deoPlayback25 = 7011
  ServerPromotedT etV deoPlayback50 = 7012
  ServerPromotedT etV deoPlayback75 = 7013
  /*
   * T   s w n a user successfully completes t  Report flow on a Promoted T et.
   *   covers reports for all pol c es from Cl ent Event.
   */
  ServerPromotedT etReport = 7041
  /*
   * Follow from Ads data stream,   could be from both T et or ot r places
   */
  ServerPromotedProf leFollow = 7060
  /*
   * Follow from Ads data stream,   could be from both T et or ot r places
   */
  ServerPromotedProf leUnfollow = 7061
  /*
   * T   s w n a user cl cks on t  mute promoted t et's author opt on from t   nu.
   */
  ServerPromotedT etMuteAuthor = 7064
  /*
   * T   s f red w n a user cl cks on t  prof le  mage, screen na , or t  user na  of t  
   * author of t  Promoted T et wh ch leads to t  author's prof le page.
   */
  ServerPromotedT etCl ckProf le = 7072
  /*
   * T   s f red w n a user cl cks on a hashtag  n t  Promoted T et.
   */
  ServerPromotedT etCl ckHashtag = 7078
  /*
   * T   s f red w n a user opens l nk by cl ck ng on a URL  n t  Promoted T et.
   */
  ServerPromotedT etOpenL nk = 7079
  /*
   * T   s f red w n a user sw pes to t  next ele nt of t  carousel  n t  Promoted T et.
   */
  ServerPromotedT etCarouselSw peNext = 7091
  /*
   * T   s f red w n a user sw pes to t  prev ous ele nt of t  carousel  n t  Promoted T et.
   */
  ServerPromotedT etCarouselSw pePrev ous = 7092
  /*
   * T  event  s only for t  Promoted T ets w h a  b URL.
   *    s f red after ex  ng a  bV ew from a Promoted T et  f t  user was on t   bV ew for
   * at least 1 second.
   *
   * See https://confluence.tw ter.b z/d splay/REVENUE/d ll_short for more deta ls.
   */
  ServerPromotedT etL nger mpress onShort = 7093
  /*
   * T  event  s only for t  Promoted T ets w h a  b URL.
   *    s f red after ex  ng a  bV ew from a Promoted T et  f t  user was on t   bV ew for
   * at least 2 seconds.
   *
   * See https://confluence.tw ter.b z/d splay/REVENUE/d ll_ d um for more deta ls.
   */
  ServerPromotedT etL nger mpress on d um = 7094
  /*
   * T  event  s only for t  Promoted T ets w h a  b URL.
   *    s f red after ex  ng a  bV ew from a Promoted T et  f t  user was on t   bV ew for
   * at least 10 seconds.
   *
   * See https://confluence.tw ter.b z/d splay/REVENUE/d ll_long for more deta ls.
   */
  ServerPromotedT etL nger mpress onLong = 7095
  /*
   * T   s f red w n a user nav gates to explorer page (taps search magn fy ng glass on Ho  page)
   * and a Promoted Trend  s present and taps ON t  promoted spotl ght - a v deo/g f/ mage  n t 
   * " ro" pos  on (top of t  explorer page).
   */
  ServerPromotedT etCl ckSpotl ght = 7096
  /*
   * T   s f red w n a user nav gates to explorer page (taps search magn fy ng glass on Ho  page)
   * and a Promoted Trend  s present.
   */
  ServerPromotedT etV ewSpotl ght = 7097
  /*
   * 7098-7099: Promoted Trends appear  n t  f rst or second slots of t  “Trends for  ” sect on
   *  n t  Explore tab and “What’s Happen ng” module on Tw ter.com. For more  nformat on, c ck go/ads-takeover.
   * 7099: T   s f red w n a user v ews a promoted Trend.   should be cons dered as an  mpress on.
   */
  ServerPromotedTrendV ew = 7098
  /*
   * 7099: T   s f red w n a user cl cks a promoted Trend.   should be cons dered as an engag nt.
   */
  ServerPromotedTrendCl ck = 7099
  /*
   * 7131-7133: A Promoted Event f red w n playback reac s 25%, 50%, 75% of total track durat on.
   * T   s for t  preroll ad that plays before a v deo on a promoted T et.
   * Not val d for l ve v deos. Refer go/avscr b ng.
   * T  w ll only conta n  tadata for t  preroll ad.
   */
  ServerPromotedT etV deoAdPlayback25 = 7131
  ServerPromotedT etV deoAdPlayback50 = 7132
  ServerPromotedT etV deoAdPlayback75 = 7133
  /*
   * 7151-7153: A Promoted Event f red w n playback reac s 25%, 50%, 75% of total track durat on.
   * T   s for t  preroll ad that plays before a v deo on an organ c T et.
   * Not val d for l ve v deos. Refer go/avscr b ng.
   * T  w ll only conta n  tadata for t  preroll ad.
   */
  ServerT etV deoAdPlayback25 = 7151
  ServerT etV deoAdPlayback50 = 7152
  ServerT etV deoAdPlayback75 = 7153

  ServerPromotedT etD sm ssW houtReason = 7180
  ServerPromotedT etD sm ssUn nterest ng = 7181
  ServerPromotedT etD sm ssRepet  ve = 7182
  ServerPromotedT etD sm ssSpam = 7183


  /*
   * For Favor eArch val Events
   */
  ServerT etArch veFavor e = 8000
  ServerT etUnarch veFavor e = 8001
  /*
   * For Ret etArch val Events
   */
  ServerT etArch veRet et = 8002
  ServerT etUnarch veRet et = 8003
}(pers sted='true', hasPersonalData='false')

/*
 * T  un on w ll be updated w n   have a part cular
 * act on that has attr butes un que to that part cular act on
 * (e.g. l nger  mpress ons have start/end t  s) and not common
 * to all t et act ons.
 * Nam ng convent on for T etAct on nfo should be cons stent w h
 * Act onType. For example, `Cl entT etL nger mpress on` Act onType enum
 * should correspond to `Cl entT etL nger mpress on` T etAct on nfo un on arm.
 *   typ cally preserve 1:1 mapp ng bet en Act onType and T etAct on nfo. Ho ver,   make
 * except ons w n opt m z ng for custo r requ re nts. For example, mult ple 'Cl entT etV deo*'
 * Act onType enums correspond to a s ngle `T etV deoWatch` T etAct on nfo un on arm because
 * custo rs want  nd v dual act on labels but common  nformat on across those labels.
 */
un on T etAct on nfo {
  // 41 matc s enum  ndex ServerT etReport  n Act onType
  41: ServerT etReport serverT etReport
  // 1002 matc s enum  ndex Cl entT etL nger mpress on  n Act onType
  1002: Cl entT etL nger mpress on cl entT etL nger mpress on
  // Common  tadata for
  // 1. "Cl entT etV deo*" Act onTypes w h enum  nd ces 1011-1016 and 1100-1101
  // 2. "ServerPromotedT etV deo*" Act onTypes w h enum  nd ces 7011-7013 and 7131-7133
  // 3. "ServerT etV deo*" Act onTypes w h enum  nd ces 7151-7153
  // T   s because:
  // 1. all t  above l sted Act onTypes share common  tadata
  // 2. more modular code as t  sa  struct can be reused
  // 3. reduces chance of error wh le populat ng and pars ng t   tadata
  // 4. consu rs can eas ly process t   tadata
  1011: T etV deoWatch t etV deoWatch
  // 1012: sk p
  // 1013: sk p
  // 1014: sk p
  // 1015: sk p
  // 1016: sk p
  // 1024 matc s enum  ndex Cl entT etCl ck nt onScreenNa   n Act onType
  1024: Cl entT etCl ck nt onScreenNa  cl entT etCl ck nt onScreenNa 
  // 1041 matc s enum  ndex Cl entT etReport  n Act onType
  1041: Cl entT etReport cl entT etReport
  // 1060 matc s enum  ndex Cl entT etFollowAuthor  n Act onType
  1060: Cl entT etFollowAuthor cl entT etFollowAuthor
  // 1061 matc s enum  ndex Cl entT etUnfollowAuthor  n Act onType
  1061: Cl entT etUnfollowAuthor cl entT etUnfollowAuthor
  // 1078 matc s enum  ndex Cl entT etCl ckHashtag  n Act onType
  1078: Cl entT etCl ckHashtag cl entT etCl ckHashtag
  // 1090 matc s enum  ndex Cl entT etOpenL nk  n Act onType
  1090: Cl entT etOpenL nk cl entT etOpenL nk
  // 1091 matc s enum  ndex Cl entT etTakeScreenshot  n Act onType
  1091: Cl entT etTakeScreenshot cl entT etTakeScreenshot
  // 1500 matc s enum  ndex Cl entT etV2 mpress on  n Act onType
  1500: Cl entT etV2 mpress on cl entT etV2 mpress on
  // 7079 matc s enum  ndex ServerPromotedT etOpenL nk  n Act onType
  7079: ServerPromotedT etOpenL nk serverPromotedT etOpenL nk
}(pers sted='true', hasPersonalData='true')


struct Cl entT etOpenL nk {
  //Url wh ch was cl cked.
  1: opt onal str ng url(personalDataType = 'RawUrlPath')
}(pers sted='true', hasPersonalData='true')

struct ServerPromotedT etOpenL nk {
  //Url wh ch was cl cked.
  1: opt onal str ng url(personalDataType = 'RawUrlPath')
}(pers sted='true', hasPersonalData='true')

struct Cl entT etCl ckHashtag {
  /* Hashtag str ng wh ch was cl cked. T  PDP annotat on  s SearchQuery,
   * because cl ck ng on t  hashtag tr ggers a search w h t  hashtag
   */
  1: opt onal str ng hashtag(personalDataType = 'SearchQuery')
}(pers sted='true', hasPersonalData='true')

struct Cl entT etTakeScreenshot {
  //percentage v s ble   ght.
  1: opt onal  32 percentV s ble  ght100k
}(pers sted='true', hasPersonalData='false')

/*
 * See go/ osl nger mpress onbehav ors and go/l ngerandro dfaq
 * for  os and andro d cl ent def n  ons of a l nger respect vely.
 */
struct Cl entT etL nger mpress on {
  /* M ll seconds s nce epoch w n t  t et beca  more than 50% v s ble. */
  1: requ red  64 l ngerStartT  stampMs(personalDataType = ' mpress on tadata')
  /* M ll seconds s nce epoch w n t  t et beca  less than 50% v s ble. */
  2: requ red  64 l ngerEndT  stampMs(personalDataType = ' mpress on tadata')
}(pers sted='true', hasPersonalData='true')

/*
 * See go/behav oral-cl ent-events for general behav oral cl ent event (BCE)  nformat on
 * and go/bce-v2 mpress for deta led  nformat on about BCE  mpress on events.
 *
 * Unl ke Cl entT etL nger mpress on, t re  s no lo r bound on t  amount of t  
 * necessary for t   mpress event to occur. T re  s also no v s b l y requ re nt for a  mpress
 * event to occur.
 */
struct Cl entT etV2 mpress on {
  /* M ll seconds s nce epoch w n t  t et beca  v s ble. */
  1: requ red  64  mpressStartT  stampMs(personalDataType = ' mpress on tadata')
  /* M ll seconds s nce epoch w n t  t et beca  v s ble. */
  2: requ red  64  mpressEndT  stampMs(personalDataType = ' mpress on tadata')
  /*
   * T  U  component that hosted t  t et w re t   mpress event happened.
   *
   * For example, s ceComponent = "t et"  f t   mpress event happened on a t et d splayed amongst
   * a collect on of t ets, or s ceComponent = "t et_deta ls"  f t   mpress event happened on
   * a t et deta l U  component.
   */
  3: requ red str ng s ceComponent(personalDataType = ' bs ePage')
}(pers sted='true', hasPersonalData='true')

 /*
 * Refer to go/c -scr b ng and go/ nteract on-event-spec for deta ls
 */
struct T etV deoWatch {
   /*
   * Type of v deo  ncluded  n t  T et
   */
  1: opt onal cl ent_app. d aType  d aType(personalDataType = ' d aF le')
  /*
   * W t r t  v deo content  s "monet zable",  .e.,
   *  f a preroll ad may be served dynam cally w n t  v deo plays
   */
  2: opt onal bool  sMonet zable(personalDataType = ' d aF le')

  /*
   * T  owner of t  v deo, prov ded by playl st.
   *
   * For ad engage nts related to a preroll ad (V DEO_AD_*),
   * t  w ll be t  owner of t  preroll ad and sa  as t  prerollOwner d.
   *
   * For ad engage nts related to a regular v deo (V DEO_CONTENT_*), t  w ll be t  owner of t 
   * v deo and not t  preroll ad.
   */
  3: opt onal  64 v deoOwner d(personalDataType = 'User d')

  /*
   *  dent f es t  v deo assoc ated w h a card.
   *
   * For ad Engage nts,  n t  case of engage nts related to a preroll ad (V DEO_AD_*),
   * t  w ll be t   d of t  preroll ad and sa  as t  prerollUu d.
   *
   * For ad engage nts related to a regular v deo (V DEO_CONTENT_*), t  w ll be  d of t  v deo
   * and not t  preroll ad.
   */
  4: opt onal str ng v deoUu d(personalDataType = ' d a d')

  /*
   *  d of t  preroll ad shown before t  v deo
   */
  5: opt onal str ng prerollUu d(personalDataType = ' d a d')

  /*
   * Advert ser  d of t  preroll ad
   */
  6: opt onal  64 prerollOwner d(personalDataType = 'User d')
  /*
   * for ampl fy_flayer events,  nd cates w t r preroll or t  ma n v deo  s be ng played
   */
  7: opt onal str ng v deoType(personalDataType = ' d aF le')
}(pers sted='true', hasPersonalData='true')

struct Cl entT etCl ck nt onScreenNa  {
  /*  d for t  prof le (user_ d) that was act oned on */
  1: requ red  64 act onProf le d(personalDataType = 'User d')
  /* T  handle/screenNa  of t  user. T  can't be changed. */
  2: requ red str ng handle(personalDataType = 'UserNa ')
}(pers sted='true', hasPersonalData='true')

struct Cl entT etReport {
  /*
   * W t r t  "Report T et" flow was successfully completed.
   * `true`  f t  flow was completed successfully, `false` ot rw se.
   */
  1: requ red bool  sReportT etDone
  /*
   * report-flow- d  s  ncluded  n Cl ent Event w n t  "Report T et" flow was  n  ated
   * See go/report-flow- ds and
   * https://confluence.tw ter.b z/pages/v ewpage.act on?spaceKey=HEALTH&t le=Understand ng+ReportDeta ls
   */
  2: opt onal str ng reportFlow d
}(pers sted='true', hasPersonalData='true')

enum T etAuthorFollowCl ckS ce {
  UNKNOWN = 1
  CARET_MENU = 2
  PROF LE_ MAGE = 3
}

struct Cl entT etFollowAuthor {
  /*
   * W re d d t  user cl ck t  Follow button on t  t et - from t  caret  nu("CARET_MENU")
   * or v a hover ng over t  prof le and cl ck ng on Follow ("PROF LE_ MAGE") - only appl cable for  b cl ents
   * "UNKNOWN"  f t  scr be do not match t  expected na space for t  above
   */
  1: requ red T etAuthorFollowCl ckS ce followCl ckS ce
}(pers sted='true', hasPersonalData='false')

enum T etAuthorUnfollowCl ckS ce {
  UNKNOWN = 1
  CARET_MENU = 2
  PROF LE_ MAGE = 3
}

struct Cl entT etUnfollowAuthor {
  /*
   * W re d d t  user cl ck t  Unfollow button on t  t et - from t  caret  nu("CARET_MENU")
   * or v a hover ng over t  prof le and cl ck ng on Unfollow ("PROF LE_ MAGE") - only appl cable for  b cl ents
   * "UNKNOWN"  f t  scr be do not match t  expected na space for t  above
   */
  1: requ red T etAuthorUnfollowCl ckS ce unfollowCl ckS ce
}(pers sted='true', hasPersonalData='false')

struct ServerT etReport {
  /*
   * ReportDeta ls w ll be populated w n t  t et report was scr bed by spamacaw (server s de)
   * Only for t  act on subm , all t  f elds under ReportDeta ls w ll be ava lable.
   * T   s because only after successful subm ss on,   w ll know t  report_type and report_flow_na .
   * Reference: https://confluence.tw ter.b z/pages/v ewpage.act on?spaceKey=HEALTH&t le=Understand ng+ReportDeta ls
   */
  1: opt onal str ng reportFlow d
  2: opt onal report_flow_logs.ReportType reportType
}(pers sted='true', hasPersonalData='false')

/*
 * T  un on w ll be updated w n   have a part cular
 * act on that has attr butes un que to that part cular act on
 * (e.g. l nger  mpress ons have start/end t  s) and not common
 * to ot r prof le act ons.
 *
 * Nam ng convent on for Prof leAct on nfo should be cons stent w h
 * Act onType. For example, `Cl entProf leV2 mpress on` Act onType enum
 * should correspond to `Cl entProf leV2 mpress on` Prof leAct on nfo un on arm.
 */
un on Prof leAct on nfo {
  // 56 matc s enum  ndex ServerProf leReport  n Act onType
  56: ServerProf leReport serverProf leReport
  // 1600 matc s enum  ndex Cl entProf leV2 mpress on  n Act onType
  1600: Cl entProf leV2 mpress on cl entProf leV2 mpress on
  // 6001 matc s enum  ndex ServerUserUpdate  n Act onType
  6001: ServerUserUpdate serverUserUpdate
}(pers sted='true', hasPersonalData='true')

/*
 * See go/behav oral-cl ent-events for general behav oral cl ent event (BCE)  nformat on
 * and https://docs.google.com/docu nt/d/16CdSRpsmUUd17yoFH9m n3nLBqDVawx4DaZo qSfCH /ed # ad ng=h.3tu05p92xgxc
 * for deta led  nformat on about BCE  mpress on event.
 *
 * Unl ke Cl entT etL nger mpress on, t re  s no lo r bound on t  amount of t  
 * necessary for t   mpress event to occur. T re  s also no v s b l y requ re nt for a  mpress
 * event to occur.
 */
struct Cl entProf leV2 mpress on {
  /* M ll seconds s nce epoch w n t  prof le page beca  v s ble. */
  1: requ red  64  mpressStartT  stampMs(personalDataType = ' mpress on tadata')
  /* M ll seconds s nce epoch w n t  prof le page beca  v s ble. */
  2: requ red  64  mpressEndT  stampMs(personalDataType = ' mpress on tadata')
  /*
   * T  U  component that hosted t  prof le w re t   mpress event happened.
   *
   * For example, s ceComponent = "prof le"  f t   mpress event happened on a prof le page
   */
  3: requ red str ng s ceComponent(personalDataType = ' bs ePage')
}(pers sted='true', hasPersonalData='true')

struct ServerProf leReport {
  1: requ red soc al_graph_serv ce_wr e_log.Act on reportType(personalDataType = 'ReportType')
}(pers sted='true', hasPersonalData='true')

struct ServerUserUpdate {
  1: requ red l st<user_serv ce.UpdateD ff em> updates
  2: opt onal bool success (personalDataType = 'Aud  ssage')
}(pers sted='true', hasPersonalData='true')
