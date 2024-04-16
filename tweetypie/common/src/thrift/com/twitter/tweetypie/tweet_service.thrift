na space java com.tw ter.t etyp e.thr ftjava
#@na space scala com.tw ter.t etyp e.thr ftscala
#@na space strato com.tw ter.t etyp e
na space py gen.tw ter.t etyp e.serv ce
na space rb T etyP e
na space go t etyp e

 nclude "com/tw ter/bouncer/bounce.thr ft"
 nclude "com/tw ter/carousel/serv ce/carousel_serv ce.thr ft"
 nclude "com/tw ter/context/feature_context.thr ft"
 nclude "com/tw ter/ d aserv ces/commons/ d aCommon.thr ft"
 nclude "com/tw ter/ d aserv ces/commons/ d a nformat on.thr ft"
 nclude "com/tw ter/servo/except ons.thr ft"
 nclude "com/tw ter/spam/features/safety_ ta_data.thr ft"
 nclude "com/tw ter/spam/rtf/safety_label.thr ft"
 nclude "com/tw ter/spam/rtf/safety_level.thr ft"
 nclude "com/tw ter/spam/rtf/safety_result.thr ft"
 nclude "com/tw ter/tseng/w hhold ng/w hhold ng.thr ft"
 nclude "com/tw ter/t etyp e/deleted_t et.thr ft"
 nclude "com/tw ter/t etyp e/trans ent_context.thr ft"
 nclude "com/tw ter/t etyp e/t et.thr ft"
 nclude "com/tw ter/t etyp e/t et_aud .thr ft"
 nclude "com/tw ter/ ncent ves/j m ny/j m ny.thr ft"
 nclude "un f ed_cards_contract.thr ft"

typedef  16 F eld d

struct T etGeoSearchRequest D {
  1: requ red str ng  d (personalDataType = 'Pr vateT etEnt  esAnd tadata, Publ cT etEnt  esAnd tadata')
}(hasPersonalData = 'true')

struct T etCreateGeo {
  1: opt onal t et.GeoCoord nates coord nates
  2: opt onal str ng place_ d (personalDataType = ' nferredLocat on')
  3: opt onal map<str ng, str ng> place_ tadata (personalDataTypeKey = ' nferredLocat on', personalDataTypeValue = ' nferredLocat on')
  4: bool auto_create_place = 1
  // deprecated; use t et.GeoCoord nates.d splay
  5: bool d splay_coord nates = 1
  6: bool overr de_user_geo_sett ng = 0
  7: opt onal T etGeoSearchRequest D geo_search_request_ d
}(hasPersonalData = 'true')

enum StatusState {
  /**
   * T  t et was found and successfully hydrated.
   */
  FOUND              = 0

  /**
   * T  t et was not found.    may have been deleted, or could just be an  nval d or
   * unused t et  d.
   */
  NOT_FOUND          = 1

  /**
   * T  t et was found, but t re was at least one error hydrat ng so  data on t  t et.
   * GetT etResult.m ss ng_f elds  nd cates wh ch f elds may have not been hydrated completely.
   */
  PART AL            = 2

  /**
   * @deprecated All fa lures,  nclud ng t   outs, are  nd cated by `Fa led`.
   */
  T MED_OUT          = 3

  /**
   * T re was an upstream or  nternal fa lure read ng t  t et.  Usually  nd cates a
   * trans ent  ssue that  s safe to retry  m d ately.
   */
  FA LED             = 4

  /**
   * @deprecated t ets from deact vated users w ll soon be  nd cated v a `Drop` w h
   * a `F lteredReason` of `authorAccount s nact ve`.
   */
  DEACT VATED_USER   = 5

  /**
   * @deprecated t ets from suspended users w ll soon be  nd cated v a `Drop` w h
   * a `F lteredReason` of `authorAccount s nact ve`.
   */
  SUSPENDED_USER     = 6

  /**
   * @deprecated t ets from protected users that t  v e r can't see w ll soon be
   *  nd cated v a `Drop` w h a `F lteredReason` of `author sProtected`.
   */
  PROTECTED_USER     = 7
  /**
   * @deprecated t ets that have been reported by t  v e r w ll soon be  nd cated
   * v a `Drop` or `Suppress` w h a `F lteredReason` of `reportedT et`.
   */
  REPORTED_TWEET     = 8

  // Pr vateT et was or g nally used for Tw terSuggest v1 but has s nce been removed
  // obsolete: PR VATE_TWEET      = 9

  /**
   * Could not return t  t et because of backpressure, should
   * not be retr ed  m d ately; try aga n later
   */
  OVER_CAPAC TY      = 10

  /**
   * Returned w n t  request ng cl ent  s cons dered to not be
   * able to render t  t et properly
   */
  UNSUPPORTED_CL ENT = 11

  /**
   * T  t et ex sts, but was not returned because   should not be seen by t 
   * v e r.  T  reason for t  t et be ng f ltered  s  nd cated v a
   * GetT etResult.f ltered_reason.
   */
  DROP               = 12

  /**
   * T  t et ex sts and was returned, but should not be d rectly shown to t 
   * user w hout add  onal user  ntent to see t  t et, as   may be offens ve.
   * T  reason for t  suppress on  s  nd cated v a GetT etResult.f ltered_reason.
   */
  SUPPRESS           = 13

  /**
   * T  t et once ex sted and has been deleted.
   * W n GetT etOpt ons.enable_deleted_state  s true, deleted t ets
   * w ll be returned as DELETED
   * W n GetT etOpt ons.enable_deleted_state  s false, deleted t ets
   * w ll be returned as NOT_FOUND.
   */
  DELETED            = 14

  /**
   * T  t et once ex sted, had v olated Tw ter Rules, and has been deleted.
   * W n GetT etOpt ons.enable_deleted_state  s true, bounce-deleted t ets
   * w ll be returned as BOUNCE_DELETED
   * W n GetT etOpt ons.enable_deleted_state  s false, bounce-deleted t ets
   * w ll be returned as NOT_FOUND.
   */
  BOUNCE_DELETED     = 15

  RESERVED_1         = 16
  RESERVED_2         = 17
  RESERVED_3         = 18
  RESERVED_4         = 19
}

enum T etCreateState {
  /**
   * T et was created successfully.
   */
  OK = 0,

  /**
   * T  user_ d f eld from t  creat on request does not correspond to a user.
   */
  USER_NOT_FOUND = 1,

  SOURCE_TWEET_NOT_FOUND = 2,
  SOURCE_USER_NOT_FOUND = 3,

  /**
   * @deprecated Users can now ret et t  r own t ets.
   */
  CANNOT_RETWEET_OWN_TWEET = 4,

  CANNOT_RETWEET_PROTECTED_TWEET = 5,
  CANNOT_RETWEET_SUSPENDED_USER = 6,
  CANNOT_RETWEET_DEACT VATED_USER = 7,
  CANNOT_RETWEET_BLOCK NG_USER = 8,

  ALREADY_RETWEETED = 9,
  CONTR BUTOR_NOT_SUPPORTED = 10,

  /**
   * T  created_v a f eld from t  creat on request does not correspond to a
   * known cl ent appl cat on.
   */
  DEV CE_SOURCE_NOT_FOUND = 11,

  MALWARE_URL = 12,
   NVAL D_URL = 13,
  USER_DEACT VATED = 14,
  USER_SUSPENDED = 15,
  TEXT_TOO_LONG = 16,
  TEXT_CANNOT_BE_BLANK = 17,
  DUPL CATE = 18,

  /**
   * PostT etRequest. n_reply_to_t et_ d was set to a t et that cannot be found.
   *
   * T  usually  ans that t  t et was recently deleted, but could also
   *  an that t  t et  sn't v s ble to t  reply author. (T   s t 
   * case for repl es by blocked users.)
   */
   N_REPLY_TO_TWEET_NOT_FOUND = 19,

   NVAL D_ MAGE = 20,
   NVAL D_ADD T ONAL_F ELD = 21,
  RATE_L M T_EXCEEDED = 22,
   NVAL D_NARROWCAST = 23,

  /**
   * Ant spam systems (Scarecrow) den ed t  request.
   *
   * T  happens for t ets that are probably spam, but t re  s so 
   * uncerta nty. T ets that Scarecrow  s certa n are spam  w ll appear to
   * succeed, but w ll not be added to backends.
   */
  SPAM = 24,
  SPAM_CAPTCHA = 25,

  /**
   * A prov ded  d a upload  D can't be resolved.
   */
  MED A_NOT_FOUND = 26,

  /**
   * Catch-all for w n uploaded  d a v olate so  cond  on.
   *
   * For example, too many photos  n a mult -photo-set, or  nclud ng an
   * an mated g f or v deo  n a mult -photo-set.
   */
   NVAL D_MED A = 27,

  /**
   * Returned w n Scarecrow tell us to rate l m  a t et request.
   *
   * Non ver f ed users ( .e., phone ver f ed, ema l ver f ed) have more
   * str ct rate l m .
   */
  SAFETY_RATE_L M T_EXCEEDED = 28,

  /**
   * Scarecrow has rejected t  creat on request unt l t  user completes t 
   * bounce ass gn nt.
   *
   * T  flag  nd cates that PostT etResult.bounce w ll conta n a Bounce
   * struct to be propagated to t  cl ent.
   */
  BOUNCE = 29,

  /**
   * T et creat on was den ed because t  user  s  n ReadOnly mode.
   *
   * As w h SPAM, t ets w ll appear to succeed but w ll not be actually
   * created.
   */
  USER_READONLY = 30,

  /**
   * Max mum number of  nt ons allo d  n a t et was exceeded.
   */
  MENT ON_L M T_EXCEEDED = 31,

  /**
   * Max mum number of URLs allo d  n a t et was exceeded.
   */
  URL_L M T_EXCEEDED = 32,

  /**
   * Max mum number of hashtags allo d  n a t et was exceeded.
   */
  HASHTAG_L M T_EXCEEDED = 33,

  /**
   * Max mum number of cashtags allo d  n a t et was exceeded.
   */
  CASHTAG_L M T_EXCEEDED = 34,

  /**
   * Max mum length of a hashtag was exceeded.
   */
  HASHTAG_LENGTH_L M T_EXCEEDED = 35,

  /**
   * Returned  f a request conta ns more than one attach nt type, wh ch
   *  ncludes  d a, attach nt_url, and card_reference.
   */
  TOO_MANY_ATTACHMENT_TYPES = 36,

  /**
   * Returned  f t  request conta ned an attach nt URL that  sn't allo d.
   */
   NVAL D_ATTACHMENT_URL = 37,

  /**
   *   don't allow users w hout screen na s to be ret eted.
   */
  CANNOT_RETWEET_USER_W THOUT_SCREEN_NAME = 38,

  /**
   * T ets may not be allo d  f reply ng or ret et ng  P 'd t ets
   * See go/tp- p -tdd for more deta ls
   */
  D SABLED_BY_ P _POL CY = 39,

  /**
   * T  state expands   transparency around wh ch URLs are blackl sted or l m ed
   */
  URL_SPAM = 40,

  // Conversat on controls are only val d w n present on a root
  // conversat on t et and quoted t ets.
   NVAL D_CONVERSAT ON_CONTROL = 41,

  // Reply T et  s l m ed due to conversat on controls state set on
  // root conversat on T et.
  REPLY_TWEET_NOT_ALLOWED = 42,

  // Nudge  s returned w n t  cl ent prov des nudgeOpt ons and t etyp e rece ves a nudge
  // from t  J m ny strato column.
  NUDGE = 43,

  // Ap Error BadRequest (400) "Reply to a commun y t et must also be a commun y t et"
  // -- Tr ggered w n a user tr es reply ng to a commun y t et w h a non commun y t et.
  COMMUN TY_REPLY_TWEET_NOT_ALLOWED = 44,
  // Ap Error Forb dden (403) "User  s not author zed to post to t  commun y"
  // -- Tr ggered w n a user tr es post ng to a publ c/closed commun y that t y are not part of.
  COMMUN TY_USER_NOT_AUTHOR ZED = 45,
  // Ap Error NotFound (404)  "Commun y does not ex st" -- Tr ggered w n:
  //  a) A user tr es post ng to a pr vate commun y t y are not a part of.
  //  b) A user tr es post ng to a non ex stent commun y
  COMMUN TY_NOT_FOUND = 46,
  // Ap Error BadRequest (400) "Cannot ret et a commun y t et"
  // -- Tr ggered w n a user tr es to ret et a commun y t et. Commun y t ets can not be ret eted.
  COMMUN TY_RETWEET_NOT_ALLOWED = 47,

  // Attempt to t et w h Conversat on Controls was rejected, e.g. due to feature sw ch author zat on.
  CONVERSAT ON_CONTROL_NOT_ALLOWED = 48,

  // Super follow t ets requ re a spec al perm ss on to create.
  SUPER_FOLLOWS_CREATE_NOT_AUTHOR ZED = 49,

  // Not all params can go toget r. E.g. super follow t ets can not be commun y t ets.
  SUPER_FOLLOWS_ NVAL D_PARAMS = 50,

  // Ap Error Forb dden (403) "Protected user can not post to commun  es"
  // -- Tr ggered w n a protected user tr es t et ng or reply ng
  // to a commun y t et. T y are not allo d to create commun y t ets.
  COMMUN TY_PROTECTED_USER_CANNOT_TWEET = 51,

  // Ap Error Forb dden (451) "User  s not perm ted to engage w h t  exclus ve t et."
  // -- Tr ggered w n a user tr es to reply to an exclus ve t et w hout be ng
  // a superfollo r of t  t et author. Could be used for ot r engage nts  n t  future (e.g. favor e)
  EXCLUS VE_TWEET_ENGAGEMENT_NOT_ALLOWED = 52

  /**
   * Ap Error BadRequest (400) " nval d para ters on Trusted Fr ends t et creat on"
   *
   * Returned w n e  r of t  follow ng occur:
   *   a) A user tr es sett ng Trusted Fr ends Control on a reply
   *   b) A user tr es sett ng Trusted Fr ends Control on a t et w h any of t  follow ng set:
   *      ) Conversat on Control
   *       ) Commun y
   *        ) Exclus ve T et Control
   */
  TRUSTED_FR ENDS_ NVAL D_PARAMS = 53,

  /**
   * Ap Error Forb dden (403)
   *
   * Returned w n a user tr es to ret et a Trusted Fr ends t et.
   */
  TRUSTED_FR ENDS_RETWEET_NOT_ALLOWED = 54,

  /**
   * Ap Error Forb dden (457)
   *
   * Returned w n a user tr es to reply to a Trusted Fr ends t et
   * and t y are not a trusted fr end.
   */
  TRUSTED_FR ENDS_ENGAGEMENT_NOT_ALLOWED = 55,

 /**
  * Ap Error BadRequest (400) " nval d para ters for creat ng a CollabT et or Collab nv at on"
  *
  * Returned w n any of t  follow ng are true:
   *   a) A user tr es sett ng Collab Control on a reply
   *   b) A user tr es sett ng Collab Control on a t et w h any of t  follow ng set:
   *      ) Conversat on Control
   *       ) Commun y
   *        ) Exclus ve T et Control
   *      v) Trusted Fr ends Control
  **/
  COLLAB_TWEET_ NVAL D_PARAMS = 56,

  /**
   * Ap Error Forb dden (457)
   *
   * Returned w n a user tr es to create a Trusted Fr ends t et but t y are not allo d to t et
   * to t  requested Trusted Fr ends l st.
   */
  TRUSTED_FR ENDS_CREATE_NOT_ALLOWED = 57,

  /**
   * Returned w n t  current user  s not allo d to ed   n general, t  m ght be due to m ss ng
   * roles dur ng develop nt, or a m ss ng subscr pt on.
   */
  ED T_TWEET_USER_NOT_AUTHOR ZED = 58,

  /**
   * Returned w n a user tr es to ed  a T et wh ch t y d dn't author.
   */
  ED T_TWEET_USER_NOT_AUTHOR = 59,

  /**
   * Returned w n a user tr es ed  a stale t et,  an ng a t et wh ch has already been ed ed.
   */
  ED T_TWEET_NOT_LATEST_VERS ON = 60,

  /**
   * Ap Error Forb dden (460)
   *
   * Returned w n a user tr es to create a Trusted Fr ends t et that quotes t ets a Trusted
   * Fr ends t et.
   */
  TRUSTED_FR ENDS_QUOTE_TWEET_NOT_ALLOWED = 61,

  /**
   * Returned w n a user tr es ed  a t et for wh ch t  ed  ng t   has already exp red.
   */
  ED T_T ME_L M T_REACHED = 62,

  /**
   * Returned w n a user tr es ed  a t et wh ch has been already ed ed max mum number of t  s.
   */
  ED T_COUNT_L M T_REACHED = 63,

  /* Returned w n a user tr es to ed  a f eld that  s not allo d to be ed ed */
  F ELD_ED T_NOT_ALLOWED = 64,

  /* Returned w n t   n  al T et could not be found w n try ng to val date an ed  */
   N T AL_TWEET_NOT_FOUND = 65,

  /**
   * Ap Error Forb dden (457)
   *
   * Returned w n a user tr es to reply to a stale t et
   */
  STALE_TWEET_ENGAGEMENT_NOT_ALLOWED = 66,

  /**
   * Ap Error Forb dden (460)
   *
   * Returned w n a user tr es to create a t et that quotes t ets a stale t et
   */
  STALE_TWEET_QUOTE_TWEET_NOT_ALLOWED = 67,

  /* T et cannot be ed ed because t   n  al t et  s
  * marked as not ed  el g ble */
  NOT_EL G BLE_FOR_ED T = 68,

  /* A stale vers on of an ed  t et cannot be ret eted
  *  Only latest vers on of an ed  cha n should be allo d to be ret eted. */
  STALE_TWEET_RETWEET_NOT_ALLOWED = 69,

  RESERVED_32 = 70,
  RESERVED_33 = 71,
  RESERVED_34 = 72,
  RESERVED_35 = 73,
  RESERVED_36 = 74,
  RESERVED_37 = 75,
}

enum UndeleteT etState {
  /**
   * T  T et was successfully undeleted.
   */
  SUCCESS = 0,

  /**
   * T  T et was deleted and  s st ll deleted.   cannot be undeleted
   * because t  t et  s no longer  n t  soft delete arch ve.
   */
  SOFT_DELETE_EXP RED = 1,

  /**
   * T  T et l kely has never ex sted, and t refore cannot be undeleted.
   */
  TWEET_NOT_FOUND = 2,

  /**
   * T  T et could not be undeleted because   was not deleted  n
   * t  f rst place.
   */
  TWEET_ALREADY_EX STS = 3,

  /**
   * T  user who created t  T et be ng undeleted could not be found.
   */
  USER_NOT_FOUND = 4,

  /**
   * T  T et could not be undeleted because    s a ret et and t  or g nal
   * t et  s gone.
   */
  SOURCE_TWEET_NOT_FOUND = 5,

  /**
   * T  T et could not be undeleted because    s a ret et and t  author
   * of t  or g nal t et  s gone.
   */
  SOURCE_USER_NOT_FOUND = 6,

  /**
   * T  T et was deleted and  s st ll deleted.   cannot be undeleted
   * because t  t et has been bounce deleted. Bounce deleted t et
   * has been found to v olate Tw ter Rules. go/bouncer go/bounced-t et
   */
  TWEET_ S_BOUNCE_DELETED = 7,

  /**
  * T  t et cannot be undeleted because t  t et was created by a
  * user w n t y  re under 13.
  **/
  TWEET_ S_U13_TWEET = 8,

  RESERVED_2 = 9,
  RESERVED_3 = 10
}

enum T etDeleteState {
  /**
   * T et was deleted successfully.
   */
  OK = 0,

  /**
   * T et was not deleted because of t  assoc ated user.
   *
   * T  DeleteT etsRequest.by_user_ d must match t  t et owner or be an
   * adm n user.
   */
  PERM SS ON_ERROR = 1,

  /**
   * T  expected_user_ d prov ded  n DeleteT etsRequest does not match t 
   * user_ d of t  t et owner.
   */
  EXPECTED_USER_ D_M SMATCH = 2,

  /**
   * @deprecated.
   *
   *  s_user_erasure was set  n DeleteT etsRequest but t  user was not  n
   * t  erased state.
   */
  USER_NOT_ N_ERASED_STATE = 3,

  /**
   * Fa led to Load t  s ce T et wh le unret et ng stale rev s ons  n an ed  cha n.
   */
  SOURCE_TWEET_NOT_FOUND = 4,

  RESERVED_4 = 5,
  RESERVED_5 = 6,
  RESERVED_6 = 7,
  RESERVED_7 = 8
}

enum DeletedT etState {
  /**
   * T  t et has been marked as deleted but has not been permanently deleted.
   */
  SOFT_DELETED = 1

  /**
   * T  t et has never ex sted.
   */
  NOT_FOUND    = 2

  /**
   * T  t et has been permanently deleted.
   */
  HARD_DELETED = 3

  /**
   * T  t et ex sts and  s not currently deleted.
   */
  NOT_DELETED  = 4

  RESERVED1    = 5
  RESERVED2    = 6
  RESERVED3    = 7
}

/**
 * Hydrat ons to perform on t  T et returned by post_t et and post_ret et.
 */
struct Wr ePathHydrat onOpt ons {
  /**
   * Return cards for t ets w h cards  n T et.cards or T et.card2
   *
   * card2 also requ res sett ng a val d cards_platform_key
   */
  1: bool  nclude_cards = 0

  /**
   * T  card format vers on supported by t  request ng cl ent
   */
  2: opt onal str ng cards_platform_key

  # 3: obsolete
  # 4: obsolete

  /**
   * T  argu nt passed to t  Stratostore extens on po nts  chan sm.
   */
  5: opt onal b nary extens ons_args

  /**
   * W n return ng a t et that quotes anot r t et, do not  nclude
   * t  URL to t  quoted t et  n t  t et text and url ent  es.
   * T   s  ntended for cl ents that use t  quoted_t et f eld of
   * t  t et to d splay quoted t ets. Also see s mple_quoted_t et
   * f eld  n GetT etOpt ons and GetT etF eldsOpt ons
   */
  6: bool s mple_quoted_t et = 0
}

struct Ret etRequest {
  /**
   *  d of t  t et be ng ret eted.
   */
  1: requ red  64 s ce_status_ d (personalDataType = 'T et d')

  /**
   * User creat ng t  ret et.
   */
  2: requ red  64 user_ d (personalDataType = 'User d')

  /**
   * @see PostT etRequest.created_v a
   */
  3: requ red str ng created_v a (personalDataType = 'Cl entType')
  4: opt onal  64 contr butor_user_ d (personalDataType = 'User d') // no longer supported

  /**
   * @see PostT etRequest.track ng_ d
   */
  5: opt onal  64 track ng_ d (personalDataType = ' mpress on d')
  6: opt onal t et.Narrowcast narrowcast

  /**
   * @see PostT etRequest.nullcast
   */
  7: bool nullcast = 0

  /**
   * @see PostT etRequest.dark
   */
  8: bool dark = 0

  // OBSOLETE 9: bool send_ret et_sms_push = 0

  10: opt onal Wr ePathHydrat onOpt ons hydrat on_opt ons

  /**
   * @see PostT etRequest.add  onal_f elds
   */
  11: opt onal t et.T et add  onal_f elds

  /**
   * @see PostT etRequest.un queness_ d
   */
  12: opt onal  64 un queness_ d (personalDataType = 'Pr vateT etEnt  esAnd tadata, Publ cT etEnt  esAnd tadata')

  13: opt onal feature_context.FeatureContext feature_context

  14: bool return_success_on_dupl cate = 0

  /**
   * Passthrough data for Scarecrow that  s used for safety c cks.
   */
  15: opt onal safety_ ta_data.Safety taData safety_ ta_data

  /**
   * T   s a un que  dent f er used  n both t  REST and GraphQL-dark
   * requests that w ll be used to correlate t  GraphQL mutat on requests to t  REST requests
   * dur ng a trans  on per od w n cl ents w ll be mov ng toward t et creat on v a GraphQL.
   * See also, t  "Compar son Test ng" sect on at go/t et-create-on-graphql-tdd for add  onal
   * context.
   */
  16: opt onal str ng compar son_ d (personalDataType = 'Un versallyUn que dent f erUu d')
}(hasPersonalData = 'true')

/**
 * A request to set or unset nsfw_adm n and/or nsfw_user.
 */
struct UpdatePoss blySens  veT etRequest {
  /**
   *  d of t et be ng updated
   */
  1: requ red  64 t et_ d (personalDataType = 'T et d')

  /**
   *  d of t  user  n  at ng t  request.
   *
   *   could be e  r t  owner of t  t et or an adm n.    s used w n
   * aud  ng t  request  n Guano.
   */
  2: requ red  64 by_user_ d (personalDataType = 'User d')

  /**
   * New value for t et.core_data.nsfw_adm n.
   */
  3: opt onal bool nsfw_adm n

  /**
   * New value for t et.core_data.nsfw_user.
   */
  4: opt onal bool nsfw_user

  /**
   * Host or remote  P w re t  request or g nated.
   *
   * T  data  s used w n aud  ng t  request  n Guano.  f unset,   w ll
   * be logged as "<unknown>".
   */
  5: opt onal str ng host (personalDataType = ' pAddress')

  /**
   * Pass-through  ssage sent to t  aud  serv ce.
   */
  6: opt onal str ng note
}(hasPersonalData = 'true')

struct UpdateT et d aRequest {
  /**
   * T  t et  d that's be ng updated
   */
  1: requ red  64 t et_ d (personalDataType = 'T et d')

  /**
   * A mapp ng from old (ex st ng)  d a  ds on t  t et to new  d a  ds.
   *
   * Ex st ng t et  d a not  n t  map w ll rema n unchanged.
   */
  2: requ red map< 64,  64> old_to_new_ d a_ ds (personalDataTypeKey = ' d a d', personalDataTypeValue = ' d a d')
}(hasPersonalData = 'true')

struct TakedownRequest {
  1: requ red  64 t et_ d (personalDataType = 'T et d')

  /**
   * T  l st of takedown country codes to add to t  t et.
   *
   * DEPRECATED, reasons_to_add should be used  nstead.
   */
  2: l st<str ng> countr es_to_add = [] (personalDataType = 'ContentRestr ct onStatus')

  /**
   * T  f eld  s t  l st of takedown country codes to remove from t  t et.
   *
   * DEPRECATED, reasons_to_remove should be used  nstead.
   */
  3: l st<str ng> countr es_to_remove = [] (personalDataType = 'ContentRestr ct onStatus')

  /**
   * T  f eld  s t  l st of takedown reasons to add to t  t et.
   */
  11: l st<w hhold ng.TakedownReason> reasons_to_add = []

  /**
   * T  f eld  s t  l st of takedown reasons to remove from t  t et.
   */
  12: l st<w hhold ng.TakedownReason> reasons_to_remove = []

  /**
   * Mot vat on for t  takedown wh ch  s wr ten to t  aud  serv ce.
   *
   * T  data  s not pers sted w h t  takedown  self.
   */
  4: opt onal str ng aud _note (personalDataType = 'Aud  ssage')

  /**
   * W t r to send t  request to t  aud  serv ce.
   */
  5: bool scr be_for_aud  = 1

  // DEPRECATED, t  f eld  s no longer used.
  6: bool set_has_takedown = 1

  // DEPRECATED, t  f eld  s no longer used.
  7: opt onal l st<str ng> prev ous_takedown_country_codes (personalDataType = 'ContentRestr ct onStatus')

  /**
   * W t r t  request should enqueue a T etTakedownEvent to EventBus and
   * Hoseb rd.
   */
  8: bool eventbus_enqueue = 1

  /**
   *  D of t  user who  n  ated t  takedown.
   *
   * T   s used w n wr  ng t  takedown to t  aud  serv ce.  f unset,  
   * w ll be logged as -1.
   */
  9: opt onal  64 by_user_ d (personalDataType = 'User d')

  /**
   * Host or remote  P w re t  request or g nated.
   *
   * T  data  s used w n aud  ng t  request  n Guano.  f unset,   w ll
   * be logged as "<unknown>".
   */
  10: opt onal str ng host (personalDataType = ' pAddress')
}(hasPersonalData = 'true')

// Argu nts to delete_locat on_data
struct DeleteLocat onDataRequest {
  1:  64 user_ d (personalDataType = 'User d')
}(hasPersonalData = 'true')

// structs for AP  V2 (flex ble sc ma)

struct GetT etOpt ons {
  /**
   * Return t  or g nal t et  n GetT etResult.s ce_t et for ret ets.
   */
  1: bool  nclude_s ce_t et = 1

  /**
   * Return t  hydrated Place object  n T et.place for t ets w h geolocat on.
   */
  2: bool  nclude_places = 0

  /**
   * Language used for place na s w n  nclude_places  s true. Also passed to
   * t  cards serv ce,  f cards are hydrated for t  request.
   */
  3: str ng language_tag = "en"

  /**
   * Return cards for t ets w h cards  n T et.cards or T et.card2
   *
   * card2 also requ res sett ng a val d cards_platform_key
   */
  4: bool  nclude_cards = 0

  /**
   * Return t  number of t  s a t et has been ret eted  n
   * T et.counts.ret et_count.
   */
  5: bool  nclude_ret et_count = 0

  /**
   * Return t  number of d rect repl es to a t et  n
   * T et.counts.reply_count.
   */
  6: bool  nclude_reply_count = 0

  /**
   * Return t  number of favor es a t et has rece ved  n
   * T et.counts.favor e_count.
   */
  7: bool  nclude_favor e_count = 0

  # OBSOLETE 8: bool  nclude_un que_users_ mpressed_count = 0
  # OBSOLETE 9: bool  nclude_cl ck_count = 0
  # OBSOLETE 10: bool  nclude_descendent_reply_count = 0

  /**
   * @deprecated Use safety_level for spam f lter ng.
   */
  11: opt onal t et.SpamS gnalType spam_s gnal_type

  /**
   *  f t  requested t et  s not already  n cac , do not add  .
   *
   *   should set do_not_cac  to true  f   are request ng old t ets
   * (older than 30 days) and t y are unl kely to be requested aga n.
   */
  12: bool do_not_cac  = 0

  /**
   * T  card format vers on supported by t  request ng cl ent
   */
  13: opt onal str ng cards_platform_key (personalDataType = 'Pr vateT etEnt  esAnd tadata, Publ cT etEnt  esAnd tadata')

  /**
   * T  user for whose perspect ve t  request should be processed.
   *
   *  f   are request ng t ets on behalf of a user, set t  to t  r user
   *  d. T  effect of sett ng t  opt on  s:
   *
   * - T etyp e w ll return protected t ets that t  user  s allo d to
   *   access, rat r than f lter ng out protected t ets.
   *
   * -  f t  f eld  s set *and* ` nclude_perspect vals`  s set, t n t 
   *   t ets w ll have t  `perspect ve` f eld set to a struct w h flags
   *   that  nd cate w t r t  user has favor ed, ret eted, or reported
   *   t  t et  n quest on.
   *
   *  f   have a spec f c need to access all protected t ets (not
   * just t ets that should be access ble to t  current user), see t 
   * docu ntat on for ` nclude_protected`.
   */
  14: opt onal  64 for_user_ d (personalDataType = 'User d')

  /**
   * Do not enforce normal f lter ng for protected t ets, blocked quote t ets,
   * contr butor data, etc. T  does not affect V s b l y L brary (http://go/vf)
   * based f lter ng wh ch executes w n safety_level  s spec f ed, see request
   * f eld 24 safety_level below
   *
   *  f `bypass_v s b l y_f lter ng`  s true, T etyp e w ll not enforce f lter ng
   * for protected t ets, blocked quote t ets, contr butor data, etc. and y  cl ent
   * w ll rece ve all t ets regardless of follow relat onsh p.   w ll also be able
   * to access t ets from deact vated and suspended users. T   s only necessary
   * for spec al cases, such as  ndex ng or analyz ng t ets, or adm n strator access.
   * S nce t  elevated access  s usually unnecessary, and  s a secur y r sk,   w ll
   * need to get y  cl ent  d wh el sted to access t  feature.
   *
   *  f   are access ng t ets on behalf of a user, set
   * `bypass_v s b l y_f lter ng` to false and set `for_user_ d`. T  w ll
   * allow access to exactly t  set of t ets that that user  s author zed to
   * access, and f lter out t ets t  user should not be author zed to access
   * (returned w h a StatusState of PROTECTED_USER).
   */
  15: bool bypass_v s b l y_f lter ng = 0

  /**
   * Return t  user-spec f c v ew of a t et  n T et.perspect ve
   *
   * for_user_ d must also be set.
   */
  16: bool  nclude_perspect vals = 0

  // OBSOLETE  d a faces are always  ncluded
  17: bool  nclude_ d a_faces = 0

  /**
   * T  flex ble sc ma f elds of t  t et to return.
   *
   * F elds of t ets  n t  100+ range w ll only be returned  f t y are
   * expl c ly requested.
   */
  18: l st<F eld d> add  onal_f eld_ ds = []

  // OBSOLETE 19: bool  nclude_top c_labels = 0

  /**
   * Exclude user-reported t ets from t  request. Only appl cable  f
   * forUser d  s set.
   *
   * Users can report  nd v dual t ets  n t  U  as un nterest ng, spam,
   * sens  ve, or abus ve.
   */
  20: bool exclude_reported = 0

  //  f set to true, d sables suggested t et v s b l y c cks
  // OBSOLETE (Tw terSuggest nfo vers on of suggested t ets has been removed)
  21: bool obsolete_sk p_tw ter_suggests_v s b l y_c ck = 0
  // OBSOLETE 22: opt onal set<t et.SpamS gnalType> spam_s gnal_types

  /**
   * Return t  quoted t et  n GetT etResult.quoted_t et
   */
  23: bool  nclude_quoted_t et = 0

  /**
   * Content f lter ng pol cy that w ll be used to drop or suppress t ets
   * from response. T  f lter ng  s based on t  result of V s b l y L brary
   * and does not affect f lter ng of t ets from blocked or non-follo d protected users, see
   * request f eld 15 bypass_v s b l y_f lter ng above
   *
   *  f not spec f ed SafetyLevel.F lterDefault w ll be used.
   */
  24: opt onal safety_level.SafetyLevel safety_level

  // obsolete 25: bool  nclude_an mated_g f_ d a_ent  es = 0
  26: bool  nclude_prof le_geo_enr ch nt = 0
  // obsolete 27: opt onal set<str ng> extens ons
  28: bool  nclude_t et_p vots = 0

  /**
   * T  argu nt passed to t  Stratostore extens on po nts  chan sm.
   */
  29: opt onal b nary extens ons_args

  /**
   * Return t  number of t  s a t et has been quoted  n T et.counts.quote_count
   */
  30: bool  nclude_quote_count = 0

  /**
   * Return  d a  tadata from  d a nfoServ ce  n  d aEnt y.add  onal_ tadata
   */
  31: bool  nclude_ d a_add  onal_ tadata = 0

  /**
   * Populate t  conversat on_muted f eld of t  T et for t  request ng
   * user.
   *
   * Sett ng t  to true w ll have no effect unless for_user_ d  s set.
   */
  32: bool  nclude_conversat on_muted = 0

  /**
   * @deprecated go/sunsett ng-carousels
   */
  33: bool  nclude_carousels = 0

  /**
   * W n enable_deleted_state  s true and   have ev dence that t 
   * t et once ex sted and was deleted, T etyp e returns
   * StatusState.DELETED or StatusState.BOUNCE_DELETED. (See com nts
   * on StatusState for deta ls on t se two states.)
   *
   * W n enable_deleted_state  s false, deleted t ets are
   * returned as StatusState.NOT_FOUND.
   *
   * Note: even w n enable_deleted_state  s true, a deleted t et may
   * st ll be returned as StatusState.NOT_FOUND due to eventual
   * cons stency.
   *
   * T  opt on  s false by default for compat b l y w h cl ents
   * expect ng StatusState.NOT_FOUND.
   */
  34: bool enable_deleted_state = 0

  /**
   * Populate t  conversat on_owner_ d f eld of t  T et for t  request ng
   * user. Wh ch translate  nto  s_conversat on_owner  n b rd rd
   *
   */
  // obsolete 35: bool  nclude_conversat on_owner_ d = 0

  /**
   * Populate t   s_removed_from_conversat on f eld of t  T et for t  request ng
   * user.
   *
   */
  // obsolete 36: bool  nclude_ s_removed_from_conversat on = 0

  // To retr eve self-thread  tadata request f eld T et.SelfThread tadataF eld
  // obsolete 37: bool  nclude_self_thread_ nfo = 0

  /**
   * T  opt on surfaces CardReference f eld (118)  n T et thr ft object.
   *   use card_ur  present  n card reference, to get access to stored card  nformat on.
   */
  37: bool  nclude_card_ur  = 0

  /**
   * W n return ng a t et that quotes anot r t et, do not  nclude
   * t  URL to t  quoted t et  n t  t et text and url ent  es.
   * T   s  ntended for cl ents that use t  quoted_t et f eld of
   * t  t et to d splay quoted t ets.
   */
  38: bool s mple_quoted_t et = 0

  /**
   * T  flag  s used and only take affect  f t  requested t et  s  creat ves conta ner backed
   * t et. T  w ll suprress t  t et mater al zat on and return t et not found.
   *
   * go/creat ves-conta ners-tdd
  **/
  39: bool d sable_t et_mater al zat on = 0

   
  /**
   * Used for load s dd ng.  f set to true, T etyp e serv ce m ght s d t  request,  f t  serv ce 
   *  s struggl ng.
  **/
  40: opt onal bool  s_request_s ddable

}(hasPersonalData = 'true')

struct GetT etsRequest {
  1: requ red l st< 64> t et_ ds (personalDataType = 'T et d')
  // @deprecated unused
  2: opt onal l st< 64> s ce_t et_ d_h nts (personalDataType = 'T et d')
  3: opt onal GetT etOpt ons opt ons
  // @deprecated unused
  4: opt onal l st< 64> quoted_t et_ d_h nts (personalDataType = 'T et d')
}(hasPersonalData = 'true')

/**
 * Can be used to reference an arb rary nested f eld of so  struct v a
 * a l st of f eld  Ds descr b ng t  path of f elds to reach t  referenced
 * f eld.
 */
struct F eldByPath {
  1: requ red l st<F eld d> f eld_ d_path
}

struct GetT etResult {
  1: requ red  64 t et_ d (personalDataType = 'T et d')

  /**
   *  nd cates what happened w n t  t et was loaded.
   */
  2: requ red StatusState t et_state

  /**
   * T  requested t et w n t et_state  s `FOUND`, `PART AL`, or `SUPPRESS`.
   *
   * T  f eld w ll be set  f t  t et ex sts, access  s author zed,
   * and enough data about t  t et  s ava lable to mater al ze a
   * t et. W n t  f eld  s set,   should look at t  t et_state
   * f eld to determ ne how to treat t  t et.
   *
   *  f t et_state  s FOUND, t n t  t et  s complete and passes t 
   * author zat on c cks requested  n GetT etOpt ons. (See
   * GetT etOpt ons.for_user_ d for more  nformat on about author zat on.)
   *
   *  f t et_state  s PART AL, t n enough data was ava lable to return
   * a t et, but t re was an error w n load ng t  t et that prevented
   * so  data from be ng returned (for example,  f a request to t  cards
   * serv ce t  s out w n cards  re requested, t n t  t et w ll be
   * marked PART AL). `m ss ng_f elds`  nd cates wh ch parts of t  t et
   * fa led to load. W n   rece ve a PART AL t et,    s up to  
   * w t r to proceed w h t  degraded t et data or to cons der   a
   * fa lure. For example, a mob le cl ent m ght choose to d splay a
   * PART AL t et to t  user, but not store    n an  nternal cac .
   *
   *  f t et_state  s SUPPRESS, t n t  t et  s complete, but soft
   * f lter ng  s enabled. T  state  s  ntended to h de potent ally
   * harmful t ets from user's v ew wh le not tak ng away t  opt on for
   * t  user to overr de   f lter ng dec s on. See http://go/rtf
   * (render-t   f lter ng) for more  nformat on about how to treat t se
   * t ets.
   */
  3: opt onal t et.T et t et

  /**
   * T  t et f elds that could not be loaded w n t et_state  s `PART AL`
   * or `SUPPRESS`.
   *
   * T  f eld w ll be set w n t  `t et_state`  s `PART AL`, and may
   * be set w n `t et_state`  s SUPPRESS.    nd cates degraded data  n
   * t  `t et`. Each entry  n `m ss ng_f elds`  nd cates a traversal of
   * t  `T et` thr ft object term nat ng at t  f eld that  s
   * m ss ng. For most non-core f elds, t  path w ll just be t  f eld  d
   * of t  f eld that  s m ss ng.
   *
   * For example,  f card2 fa led to load for a t et, t  `t et_state`
   * w ll be `PART AL`, t  `t et` f eld w ll be set, t  T et's `card2`
   * f eld w ll be empty, and t  f eld w ll be set to:
   *
   *     Set(F eldByPath(Seq(17)))
   */
  4: opt onal set<F eldByPath> m ss ng_f elds

  /**
   * T  or g nal t et w n `t et`  s a ret et and
   * GetT etOpt ons. nclude_s ce_t et  s true.
   */
  5: opt onal t et.T et s ce_t et

  /**
   * T  ret et f elds that could not be loaded w n t et_state  s `PART AL`.
   */
  6: opt onal set<F eldByPath> s ce_t et_m ss ng_f elds

  /**
   * T  quoted t et w n `t et`  s a quote t et and
   * GetT etOpt ons. nclude_quoted_t et  s true.
   */
  7: opt onal t et.T et quoted_t et

  /**
   * T  quoted t et f elds that could not be loaded w n t et_state  s `PART AL`.
   */
  8: opt onal set<F eldByPath> quoted_t et_m ss ng_f elds

  /**
   * T  reason that a t et should not be d splayed w n t et_state  s
   * `SUPPRESS` or `DROP`.
   */
  9: opt onal safety_result.F lteredReason f ltered_reason

  /**
   * Hydrated carousel  f t  t et conta ns a carousel URL and t 
   * GetT etOpt ons. nclude_carousel  s true.
   *
   *  n t  case Carousel Serv ce  s requested to hydrate t  carousel, and
   * t  result stored  n t  f eld.
   *
   * @deprecated go/sunsett ng-carousels
   */
  10: opt onal carousel_serv ce.GetCarouselResult carousel_result

  /**
   *  f a quoted t et would be present, but   was f ltered out, t n
   * t  f eld w ll be set to t  reason that   was f ltered.
   */
  11: opt onal safety_result.F lteredReason quoted_t et_f ltered_reason
}(hasPersonalData = 'true')

un on T et nclude {
  /**
   * F eld  D w h n t  `T et` struct to  nclude.  All f elds may be opt onally  ncluded
   * except for t  ` d` f eld.
   */
  1: F eld d t etF eld d

  /**
   * F eld  D w h n t  `StatusCounts` struct to  nclude.  Only spec f cally requested
   * count f elds w ll be  ncluded.   nclud ng any `countsF eld ds` values automat cally
   *  mpl es  nclud ng `T et.counts`.
   *
   */
  2: F eld d countsF eld d

  /**
   * F eld  D w h n t  ` d aEnt y` struct to  nclude.  Currently, only ` d aEnt y.add  onal tadata`
   * may be opt onally  ncluded ( .e.,   w ll not be  ncluded by default  f    nclude
   * `t etF eld d` = `T et. d a` w hout also  nclud ng ` d aEnt yF eld d`  = 
   * ` d aEnt y.add  onal tadata`.   nclud ng any ` d aEnt yF eld d` values automat cally
   *  mpl es  nclude `T et. d a`.
   */
  3: F eld d  d aEnt yF eld d
}

/**
 * An enu rat on of pol cy opt ons  nd cat ng how t ets should be f ltered (protected t ets, blocked quote t ets,
 * contr butor data, etc.). T  does not affect V s b l y L brary (http://go/vf) based f lter ng.
 * T   s equ valent to `bypass_v s b l y_f lter ng`  n get_t ets() call. T   ans that
 * `T etV s b l yPol cy.NO_F LTER NG`  s equ valent to `bypass_v s b l y_f lter ng` = true
 */
enum T etV s b l yPol cy {
  /**
   * only return t ets that should be v s ble to e  r t  `forUser d` user,  f spec f ed,
   * or from t  perspect ve of a logged-out user  f `forUser d`  s not spec f ed. T  opt on
   * should always be used  f request ng data to be returned v a t  publ c AP .
   */
  USER_V S BLE = 1,

  /**
   * returns all t ets that can be found, regardless of user v s b l y.  T  opt on should
   * never be used w n gat r data to be return  n an AP , and should only be used for  nternal
   * process ng.  because t  opt on allows access to potent ally sens  ve data, cl ents
   * must be wh el sted to use  .
   */
  NO_F LTER NG = 2
}

struct GetT etF eldsOpt ons {
  /**
   *  dent f es wh ch `T et` or nested f elds to  nclude  n t  response.
   */
  1: requ red set<T et nclude> t et_ ncludes

  /**
   *  f true and t  requested t et  s a ret et, t n a `T et`
   * conta n ng t  requested f elds for t  ret eted t et w ll be
   *  ncluded  n t  response.
   */
  2: bool  ncludeRet etedT et = 0

  /**
   *  f true and t  requested t et  s a quote-t et, t n t  quoted
   * t et w ll also be quer ed and t  result for t  quoted t et
   *  ncluded  n `GetT etF eldsResult.quotedT etResult`.
   */
  3: bool  ncludeQuotedT et = 0

  /**
   *  f true and t  requested t et conta ns a carousel URL, t n t 
   * carousel w ll also be quer ed and t  result for t  carousel
   *  ncluded  n `GetT etF eldsResult.carouselResult`.
   *
   * @deprecated go/sunsett ng-carousels
   */
  4: bool  ncludeCarousel = 0

  /**
   *  f   are request ng t ets on behalf of a user, set t  to t  r
   * user  d. T  effect of sett ng t  opt on  s:
   *
   * - T etyp e w ll return protected t ets that t  user  s allo d
   *   to access, rat r than f lter ng out protected t ets, w n `v s b l y_pol cy`
   *    s set to `USER_V S BLE`.
   *
   * -  f t  f eld  s set *and* `T et.perspect ve`  s requested, t n
   *   t  t ets w ll have t  `perspect ve` f eld set to a struct w h
   *   flags that  nd cate w t r t  user has favor ed, ret eted, or
   *   reported t  t et  n quest on.
   */
  10: opt onal  64 forUser d (personalDataType = 'User d')

  /**
   * language_tag  s used w n hydrat ng a `Place` object, to get local zed na s.
   * Also passed to t  cards serv ce,  f cards are hydrated for t  request.
   */
  11: opt onal str ng languageTag (personalDataType = ' nferredLanguage')

  /**
   *  f request ng card2 cards,   must spec fy t  platform key
   */
  12: opt onal str ng cardsPlatformKey (personalDataType = 'Pr vateT etEnt  esAnd tadata, Publ cT etEnt  esAnd tadata') 

  /**
   * T  argu nt passed to t  Stratostore extens on po nts  chan sm.
   */
  13: opt onal b nary extens onsArgs

  /**
   * t  pol cy to use w n f lter ng t ets for bas c v s b l y.
   */
  20: T etV s b l yPol cy v s b l yPol cy = T etV s b l yPol cy.USER_V S BLE

  /**
   * Content f lter ng pol cy that w ll be used to drop or suppress t ets from response.
   * T  f lter ng  s based on t  result of V s b l y L brary (http://go/vf)
   * and does not affect f lter ng of t ets from blocked or non-follo d protected users, see
   * request f eld 20 v s b l yPol cy above
   *
   *  f not spec f ed SafetyLevel.F lterNone w ll be used.
   */
  21: opt onal safety_level.SafetyLevel safetyLevel

  /**
   * T  t et result won't be cac d by T etyp e  f doNotCac   s true.
   *   should set   as true  f old t ets (older than 30 days) are requested,
   * and t y are unl kely to be requested aga n.
   */
  30: bool doNotCac  = 0

  /**
   * W n return ng a t et that quotes anot r t et, do not  nclude
   * t  URL to t  quoted t et  n t  t et text and url ent  es.
   * T   s  ntended for cl ents that use t  quoted_t et f eld of
   * t  t et to d splay quoted t ets.
   *
   */
  31: bool s mple_quoted_t et = 0

  /**
   * T  flag  s used and only take affect  f t  requested t et  s  creat ves conta ner backed
   * t et. T  w ll suprress t  t et mater al zat on and return t et not found.
   *
   * go/creat ves-conta ners-tdd
  **/
  32: bool d sable_t et_mater al zat on = 0

  /**
   * Used for load s dd ng.  f set to true, T etyp e serv ce m ght s d t  request,  f t  serv ce 
   *  s struggl ng.
  **/
  33: opt onal bool  s_request_s ddable  
}(hasPersonalData = 'true')

struct GetT etF eldsRequest {
  1: requ red l st< 64> t et ds (personalDataType = 'T et d')
  2: requ red GetT etF eldsOpt ons opt ons
} (hasPersonalData = 'true')

/**
 * Used  n `T etF eldsResultState` w n t  requested t et  s found.
 */
struct T etF eldsResultFound {
  1: requ red t et.T et t et

  /**
   *  f `t et`  s a ret et, `ret etedT et` w ll be t  ret eted t et.
   * Just l ke w h t  requested t et, only t  requested f elds w ll be
   * hydrated and set on t  ret eted t et.
   */
  2: opt onal t et.T et ret etedT et

  /**
   *  f spec f ed, t n t  t et should be soft f ltered.
   */
  3: opt onal safety_result.F lteredReason suppressReason
}

/**
 * Used  n `T etF eldsResultState` w n t  requested t et  s not found.
 */
struct T etF eldsResultNotFound {
  //  f t  f eld  s true, t n   know that t  t et once ex sted and
  // has s nce been deleted.
  1: bool deleted = 0

  // T  t et  s deleted after be ng bounced for v olat ng t  Tw ter
  // Rules and should never be rendered or undeleted. see go/bounced-t et
  //  n certa n t  l nes   render a tombstone  n  s place.
  2: bool bounceDeleted = 0

  // T  reason that a t et should not be d splayed. See go/vf-tombstones- n-t etyp e
  // T ets that are not found do not go ng through V s b l y F lter ng rule evaluat on and thus
  // are not `T etF eldsResultF ltered`, but may st ll have a f ltered_reason that d st ngu s s
  // w t r t  unava lable t et should be tombstoned or hard-f ltered based on t  Safety Level.
  3: opt onal safety_result.F lteredReason f ltered_reason
}

struct T etF eldsPart al {
  1: requ red T etF eldsResultFound found

  /**
    * T  t et f elds that could not be loaded w n hydrat on fa ls
    * and a backend fa ls w h an except on. T  f eld  s populated
    * w n a t et  s "part ally" hydrated,  .e. so  f elds  re
    * successfully fetc d wh le ot rs  re not.
    *
    *    nd cates degraded data  n t  `t et`. Each entry  n `m ss ng_f elds`
    *  nd cates a traversal of t  `T et` thr ft object term nat ng at
    * t  f eld that  s m ss ng. For most non-core f elds, t  path w ll
    * just be t  f eld  d of t  f eld that  s m ss ng.
    *
    * For example,  f card2 fa led to load for a t et, t  t et  s marked "part al",
    * t  `t et` f eld w ll be set, t  T et's `card2`
    * f eld w ll be empty, and t  f eld w ll be set to:
    *
    *     Set(F eldByPath(Seq(17)))
    */
  2: requ red set<F eldByPath> m ss ngF elds

  /**
    * Sa  as `m ss ng_f elds` but for t  s ce t et  n case t  requested t et
    * was a ret et.
    */
  3: requ red set<F eldByPath> s ceT etM ss ngF elds
}
/**
 * Used  n `T etF eldsResultState` w n t re was a fa lure load ng t  requested t et.
 */
struct T etF eldsResultFa led {
  /**
   *  f true, t  fa lure was t  result of backpressure, wh ch  ans t  request
   * should not be  m d ately retr ed.     s safe to retry aga n later.
   *
   *  f false, t  fa lure  s probably trans ent and safe to retry  m d ately.
   */
  1: requ red bool overCapac y

  /**
   * An opt onal  ssage about t  cause of t  fa lure.
   */
  2: opt onal str ng  ssage

  /**
   * T  f eld  s populated w n so  t et f elds fa l to load and t 
   * t et  s marked "part al"  n t etyp e.   conta ns t  t et/RT
   *  nformat on along w h t  set of t et f elds that fa led to
   * get populated.
   */
  3: opt onal T etF eldsPart al part al
}

/**
 * Used  n `T etF eldsResultState` w n t  requested t et has been f ltered out.
 */
struct T etF eldsResultF ltered {
  1: requ red safety_result.F lteredReason reason
}

/**
 * A un on of t  d fferent poss ble outco s of a fetch ng a s ngle t et.
 */
un on T etF eldsResultState {
  1: T etF eldsResultFound found
  2: T etF eldsResultNotFound notFound
  3: T etF eldsResultFa led fa led
  4: T etF eldsResultF ltered f ltered
}

/**
 * T  response to get_t et_f elds w ll  nclude a T etF eldsResultRow for each
 * requested t et  d.
 */
struct GetT etF eldsResult {
  /**
   * T   d of t  requested t et.
   */
  1: requ red  64 t et d (personalDataType = 'T et d')

  /**
   * t  result for t  requested t et
   */
  2: requ red T etF eldsResultState t etResult

  /**
   *  f quoted-t ets  re requested and t  pr mary t et was found,
   * t  f eld w ll conta n t  result state for t  quoted t eted.
   */
  3: opt onal T etF eldsResultState quotedT etResult

  /**
   *  f t  pr mary t et was found, carousels  re requested and t re
   * was a carousel URL  n t  pr mary t et, t  f eld w ll conta n t 
   * result for t  carousel.
   *
   * @deprecated
   */
  4: opt onal carousel_serv ce.GetCarouselResult carouselResult
}

struct T etCreateConversat onControlBy nv at on {
  1: opt onal bool  nv e_v a_ nt on
}

struct T etCreateConversat onControlCommun y {
  1: opt onal bool  nv e_v a_ nt on
}

struct T etCreateConversat onControlFollo rs {
  1: opt onal bool  nv e_v a_ nt on
}

/**
 * Spec fy l m s on user part c pat on  n a conversat on.
 *
 * T   s a un on rat r than a struct to support add ng conversat on
 * controls that requ re carry ng  tadata along w h t m, such as a l st  d.
 *
 * See also:
 *   T et.conversat on_control
 *   PostT etRequest.conversat on_control
 */
un on T etCreateConversat onControl {
  1: T etCreateConversat onControlCommun y commun y
  2: T etCreateConversat onControlBy nv at on by nv at on
  3: T etCreateConversat onControlFollo rs follo rs
}

/*
 * Spec f es t  exclus v y of a t et
 * T  l m s t  aud ence of t  t et to t  author
 * and t  author's super follo rs
 * Wh le empty now,   are expect ng to add add  onal f elds  n v1+
 */
struct Exclus veT etControlOpt ons {}

struct TrustedFr endsControlOpt ons {
  1:  64 trusted_fr ends_l st_ d = 0 (personalDataType = 'TrustedFr endsL st tadata')
}(hasPersonalData = 'true')

struct Collab nv at onOpt ons {
  1: requ red l st< 64> collaborator_user_ ds (personalDataType = 'User d')
  // Note: status not sent  re, w ll be added  n T etBu lder to set all but author as PEND NG
}

struct CollabT etOpt ons {
  1: requ red l st< 64> collaborator_user_ ds (personalDataType = 'User d')
}

un on CollabControlOpt ons {
  1: Collab nv at onOpt ons collab nv at on
  2: CollabT etOpt ons collabT et
}

/**
 * W n t  struct  s suppl ed, t  PostT etRequest  s  nterpreted as
 * an ed  of t  T et whose latest vers on  s represented by prev ous_t et_ d.
 *  f t   s t  f rst ed  of a T et, t  w ll be t  sa  as t   n  al_t et_ d.
 **/
struct Ed Opt ons {
  /**
   * T   D of t  prev ous latest vers on of t  T et that  s be ng ed ed.
   *  f t   s t  f rst ed , t  w ll be t  sa  as t   n  al_t et_ d.
   **/
  1: requ red  64 prev ous_t et_ d (personalDataType = 'T et d')
}

struct NoteT etOpt ons {
  /**
   * T   D of t  NoteT et to be assoc ated w h t  T et.
   **/
  1: requ red  64 note_t et_ d (personalDataType = 'Tw terArt cle D')
  // Deprecated
  2: opt onal l st<str ng>  nt oned_screen_na s (personalDataType = 'Userna ')
  /**
  * T  user  Ds of t   nt oned users
  **/
  3: opt onal l st< 64>  nt oned_user_ ds (personalDataType = 'User d')
  /**
  * Spec f es  f t  T et can be expanded  nto t  NoteT et, or  f t y have t  sa  text
  **/
  4: opt onal bool  s_expandable
}

struct PostT etRequest {
  /**
   *  d of t  user creat ng t  t et.
   */
  1: requ red  64 user_ d (personalDataType = 'User d')

  /**
   * T  user-suppl ed text of t  t et.
   */
  2: requ red str ng text (personalDataType = 'Pr vateT ets, Publ cT ets')

  /**
   * T  OAuth cl ent appl cat on from wh ch t  creat on request or g nated.
   *
   * T  must be  n t  format "oauth:<cl ent appl cat on  d>". For requests
   * from a user t   s t  appl cat on  d of t  r cl ent; for  nternal
   * serv ces t   s t   d of an assoc ated appl cat on reg stered at
   * https://apps.tw ter.com.
   */
  3: requ red str ng created_v a (personalDataType = 'Cl entType')

  4: opt onal  64  n_reply_to_t et_ d (personalDataType = 'T et d')
  5: opt onal T etCreateGeo geo
  6: opt onal l st< 64>  d a_upload_ ds (personalDataType = ' d a d')
  7: opt onal t et.Narrowcast narrowcast

  /**
   * Do not del ver t  t et to a user's follo rs.
   *
   * W n true t  t et w ll not be fanned out, appear  n t  user's
   * t  l ne, or appear  n search results.   w ll be d str buted v a t 
   * f rehose and ava lable  n t  publ c AP .
   *
   * T   s pr mar ly used to create t ets that can be used as ads w hout
   * broadcast ng t m to an advert ser's follo rs.
   *
   */
  8: bool nullcast = 0

  /**
   * T   mpress on  d of t  ad from wh ch t  t et was created.
   *
   * T   s set w n a user ret ets or repl es to a promoted t et.    s
   * used to attr bute t  "earned" exposure of an advert se nt.
   */
  9: opt onal  64 track ng_ d (personalDataType = ' mpress on d')

  /**
   * @deprecated.
   * TOO cl ents don't act vely use t   nput param, and t  v2 AP  does not plan
   * to expose t  para ter. T  value assoc ated w h t  f eld that's
   * stored w h a t et  s obta ned from t  user's account preferences stored  n
   * `User.safety.nsfw_user`. (See go/user.thr ft for more deta ls on t  f eld)
   *
   * F eld  nd cates w t r a  nd v dual t et may conta n object onable content.
   *
   *  f spec f ed, t et.core_data.nsfw_user w ll equal t  value (ot rw se,
   * t et.core_data.nsfw_user w ll be set to user.nsfw_user).
   */
  10: opt onal bool poss bly_sens  ve

  /**
   * Do not save,  ndex, fanout, or ot rw se pers st t  t et.
   *
   * W n true, t  t et  s val dated, created, and returned but  s not
   * pers sted. T  can be used for dark test ng or pre-val dat ng a t et
   * sc duled for later creat on.
   */
  11: bool dark = 0

  /**
   *  P address of t  user mak ng t  request.
   *
   * T   s used for logg ng certa n k nds of act ons, l ke attempt ng to
   * t et malware urls.
   */
  12: opt onal str ng remote_host (personalDataType = ' pAddress')

  /**
   * Add  onal f elds to wr e w h t  t et.
   *
   * T  T et object should conta n only add  onal f elds to wr e w h
   * t  t et. Add  onal f elds are t et f elds w h  d > 100. Set
   * t et. d to be 0; t   d w ll be generated by T etyp e. Any ot r non-
   * add  onal f elds set on t  t et w ll be cons dered an  nval d
   * request.
   *
   */
  14: opt onal t et.T et add  onal_f elds

  15: opt onal Wr ePathHydrat onOpt ons hydrat on_opt ons

  // OBSOLETE 16: opt onal bool bypass_rate_l m _for_xfactor

  /**
   *  D to expl c ly  dent fy a creat on request for t  purpose of reject ng
   * dupl cates.
   *
   *  f two requests are rece ved w h t  sa  un queness_ d, t n t y w ll
   * be cons dered dupl cates of each ot r. T  only appl es for t ets
   * created w h n t  sa  datacenter. T   d should be a snowflake  d so
   * that  's globally un que.
   */
  17: opt onal  64 un queness_ d (personalDataType = 'Pr vateT etEnt  esAnd tadata, Publ cT etEnt  esAnd tadata')

  18: opt onal feature_context.FeatureContext feature_context

  /**
   * Passthrough data for Scarecrow that  s used for safety c cks.
   */
  19: opt onal safety_ ta_data.Safety taData safety_ ta_data

  // OBSOLETE 20: bool commun y_narrowcast = 0

  /**
   * Toggle narrowcast ng behav or for lead ng @ nt ons.
   *
   *  f  n_reply_to_t et_ d  s not set:
   *   - W n t  flag  s true and t  t et text starts w h a lead ng  nt on t n t  t et
   *     w ll be narrowcasted.
   *
   *  f  n_reply_to_t et_ d  s set:
   *   -  f auto_populate_reply_ tadata  s true
   *       - Sett ng t  flag to true w ll use t  default narrowcast determ nat on log c w re
   *         most repl es w ll be narrowcast but so  spec al-cases of self-repl es w ll not.
   *       - Sett ng t  flag to false w ll d sable narrowcast ng and t  t et w ll be fanned out
   *         to all t  author's follo rs.  Prev ously users pref xed t  r reply text w h "." to
   *         ach eve t  effect.
   *   -  f auto_populate_reply_ tadata  s false, t  flag w ll control w t r a lead ng
   *      nt on  n t  t et text w ll be narrowcast (true) or broadcast (false).
   */
  21: bool enable_t et_to_narrowcast ng = 1

  /**
   * Automat cally populate repl es w h lead ng  nt ons from t et text.
   */
  22: bool auto_populate_reply_ tadata = 0

  /**
   *  tadata at t  t et-asset relat onsh p level.
   */
  23: opt onal map< d aCommon. d a d,  d a nformat on.UserDef nedProduct tadata>  d a_ tadata

  /**
   * An opt onal URL that  dent f es a res ce that  s treated as an attach nt of t 
   * t  t et, such as a quote-t et permal nk.
   *
   * W n prov ded,    s appended to t  end of t  t et text, but  s not
   *  ncluded  n t  v s ble_text_range.
   */
  24: opt onal str ng attach nt_url (personalDataType = 'Card d, ShortUrl')

  /**
   * Pass-through  nformat on to be publ s d  n `T etCreateEvent`.
   *
   * T  data  s not pers sted by T etyp e.
   *
   * @deprecated prefer trans ent_context (see f eld 27) over t .
   */
  25: opt onal map<t et.T etCreateContextKey, str ng> add  onal_context

  /**
   * Users to exclude from t  automat c reply populat on behav or.
   *
   * W n auto_populate_reply_ tadata  s true, screen na s appear ng  n t 
   *  nt on pref x can be excluded by spec fy ng a correspond ng user  d  n
   * exclude_reply_user_ ds.  Because t   nt on pref x must always  nclude
   * t  lead ng  nt on to preserve d rected-at address ng for t   n-reply-
   * to t et author, attempt ng to exclude that user  d w ll have no effect.
   * Spec fy ng a user  d not  n t  pref x w ll be s lently  gnored.
   */
  26: opt onal l st< 64> exclude_reply_user_ ds (personalDataType = 'User d')

  /**
   * Used to pass structured data to T etyp e and t et_events eventbus
   * stream consu rs. T  data  s not pers sted by T etyp e.
   *
   *  f add ng a new passthrough f eld, prefer t  over add  onal_context,
   * as t   s structured data, wh le add  onal_context  s text data.
   */
  27: opt onal trans ent_context.Trans entCreateContext trans ent_context

  /**
   * Composer flow used to create t  t et. Unless us ng t  News Ca ra (go/newsca ra)
   * flow, t  should be `STANDARD`.
   *
   * W n set to `CAMERA`, cl ents are expected to d splay t  t et w h a d fferent U 
   * to emphas ze attac d  d a.
   */
  28: opt onal t et.ComposerS ce composer_s ce

  /**
  * present  f   want to restr ct repl es to t  t et (go/dont-at- -ap )
  * - T  gets converted to T et.conversat on_control and changes type
  * - T   s only val d for conversat on root t ets
  * - T  appl es to all repl es to t  t et
  */
  29: opt onal T etCreateConversat onControl conversat on_control

  // OBSOLETE 30: opt onal j m ny.CreateNudgeOpt ons nudge_opt ons

  /**
  * Prov ded  f t  cl ent wants to have t  t et create evaluated for a nudge (e.g. to not fy
  * t  user that t y are about to create a tox c t et). Reference: go/docb rd/j m ny
  */
  31: opt onal j m ny.CreateT etNudgeOpt ons nudge_opt ons

  /**
   * Prov ded for correlat ng requests or g nat ng from REST endpo nts and GraphQL endpo nts.
   *  s presence or absence does not affect T et mutat on.   used for val dat on
   * and debugg ng. T  expected format  s a 36 ASC   UU Dv4.
   * Please see AP  spec f cat on at go/graphql-t et-mutat ons for more  nformat on.
   */
  32: opt onal str ng compar son_ d (personalDataType = 'Un versallyUn que dent f erUu d')

  /**
   * Opt ons that determ ne t  shape of an exclus ve t et's restr ct ons.
   * T  ex stence of t  object  nd cates that t  t et  s  ntended to be an exclus ve t et
   * Wh le t   s an empty structure for now,   w ll have f elds added to   later  n later vers ons.
   */
  33: opt onal Exclus veT etControlOpt ons exclus veT etControlOpt ons

  34: opt onal TrustedFr endsControlOpt ons trustedFr endsControlOpt ons

  /**
   * Prov ded  f t et data  s backed up by a creat ve conta ner, that at t et hydrat on
   * t  , t etyp e would delegate to creat ve conta ner serv ce.
   *
   * go/creat ves-conta ners-tdd
   * Please note that t   d  s never publ cally shared w h cl ents,  s only used for
   *  nternal purposes.
   */
  35: opt onal  64 underly ng_creat ves_conta ner_ d (personalDataType = 'T et d')

  /**
   * Prov ded  f t et  s a CollabT et or a Collab nv at on, along w h a l st of Collaborators
   * wh ch  ncludes t  or g nal author.
   *
   * go/collab-t ets
   **/
  36: opt onal CollabControlOpt ons collabControlOpt ons

   /**
    * W n suppl ed, t  PostT etRequest  s an ed . See [[Ed Opt ons]] for more deta ls.
    **/
  37: opt onal Ed Opt ons ed Opt ons

  /**
   * W n suppl ed, t  NoteT et spec f ed  s assoc ated w h t  created T et.
   **/
  38: opt onal NoteT etOpt ons noteT etOpt ons
} (hasPersonalData = 'true')

struct SetAdd  onalF eldsRequest {
  1: requ red t et.T et add  onal_f elds
}

struct DeleteAdd  onalF eldsRequest {
  1: requ red l st< 64> t et_ ds (personalDataType = 'T et d')
  2: requ red l st<F eld d> f eld_ ds
}(hasPersonalData = 'true')

struct DeleteT etsRequest {
  1: requ red l st< 64> t et_ ds (personalDataType = 'T et d')
  // DEPRECATED and moved to t etyp e_ nternal.thr ft's CascadedDeleteT etsRequest
  2: opt onal  64 cascaded_from_t et_ d (personalDataType = 'T et d')
  3: opt onal t et_aud .Aud DeleteT et aud _passthrough

  /**
   * T   d of t  user  n  at ng t  request.
   *
   *   could be e  r t  owner of t  t et or an adm n.  f not spec f ed
   *   w ll use Tw terContext.user d.
   */
  4: opt onal  64 by_user_ d (personalDataType = 'User d')


  /**
   * W re t se t ets are be ng deleted as part of a user erasure, t  process
   * of delet ng t ets belong ng to deact vated accounts.
   *
   * T  lets backends opt m ze process ng of mass deletes of t ets from t 
   * sa  user. Talk to t  T etyp e team before sett ng t  flag.
   */
  5: bool  s_user_erasure = 0

  /**
   *  d to compare w h t  user  d of t  t ets be ng deleted.
   *
   * T  prov des extra protect on aga nst acc dental delet on of t ets.
   * T   s requ red w n  s_user_erasure  s true.  f any of t  t ets
   * spec f ed  n t et_ ds do not match expected_user_ d a
   * EXPECTED_USER_ D_M SMATCH state w ll be returned.
   */
  6: opt onal  64 expected_user_ d (personalDataType = 'User d')

  /**
   * A bounced t et  s a t et that has been found to v olate Tw ter Rules.
   * T   s represented as a t et w h  s bounce_label f eld set.
   *
   * W n t  T et owner deletes t  r offend ng bounced t et  n t  Bounced workflow, Bouncer
   * w ll subm  a delete request w h ` s_bounce_delete` set to true.  f t  t et(s) be ng deleted
   * have a bounce_label set, t  request results  n t  t et trans  on ng  nto t 
   * BounceDeleted state wh ch  ans t  t et  s part ally deleted.
   *
   * Most of t  normal t et delet on s de-effects occur but t  t et rema ns  n a
   * few tflock graphs, t et cac , and a Manhattan marker  s added. Ot r than t  l nes serv ces,
   * bounce deleted t ets are cons dered deleted and w ll return a StatusState.BounceDelete.
   *
   * After a def ned grace per od, t ets  n t  state w ll be fully deleted.
   *
   *  f t  t et(s) be ng deleted do not have t  bounce_label set, t y w ll be deleted as usual.
   *
   * Ot r than Bouncer, no serv ce should use ` s_bounce_delete` flag.
   */
  7: bool  s_bounce_delete = 0

  /**
    * T   s a un que  dent f er used  n both t  REST and GraphQL-dark
    * requests that w ll be used to correlate t  GraphQL mutat on requests to t  REST requests
    * dur ng a trans  on per od w n cl ents w ll be mov ng toward t et creat on v a GraphQL.
    * See also, t  "Compar son Test ng" sect on at go/t et-create-on-graphql-tdd for add  onal
    * context.
    */
  8: opt onal str ng compar son_ d (personalDataType = 'Un versallyUn que dent f erUu d')

  /**
    * W n an ed ed t et  s deleted v a daemons,   take a d fferent act on
    * than  f   was deleted normally.  f deleted normally,   delete t 
    *  n  al t et  n t  cha n. W n deleted v a daemons,   delete t  actual t et.
    */
  9: opt onal bool cascaded_ed ed_t et_delet on 
}(hasPersonalData = 'true')

struct DeleteT etResult {
  1: requ red  64 t et_ d (personalDataType = 'T et d')
  2: requ red T etDeleteState state
}(hasPersonalData = 'true')

struct Unret etResult {
  /**
   *  d of t  ret et that was deleted  f a ret et could be found.
   */
  1: opt onal  64 t et_ d (personalDataType = 'T et d')

  2: requ red T etDeleteState state
}(hasPersonalData = 'true')

struct PostT etResult {
  1: requ red T etCreateState state

  /**
   * T  created t et w n state  s OK.
   */
  2: opt onal t et.T et t et

  /**
   * T  or g nal t et w n state  s OK and t et  s a ret et.
   */
  3: opt onal t et.T et s ce_t et

  /**
   * T  quoted t et w n state  s OK and t et  s a quote t et.
   */
  4: opt onal t et.T et quoted_t et

  /**
   * T  requ red user re d at on from Scarecrow w n state  s BOUNCE.
   */
  5: opt onal bounce.Bounce bounce

  /**
   * Add  onal  nformat on w n T etCreateState  s not OK.
   *
   * Not all fa lures prov de a reason.
   */
  6: opt onal str ng fa lure_reason

  // OBSOLETE 7: opt onal j m ny.Nudge nudge

  /**
  * Returned w n t  state  s NUDGE to  nd cate that t  t et has not been created, and that
  * t  cl ent should  nstead d splay t  nudge to t  user. Reference: go/docb rd/j m ny
  */
  8: opt onal j m ny.T etNudge nudge
} (pers sted = "true", hasPersonalData = "true")

/**
 * Spec f es t  cause of an AccessDen ed error.
 */
enum AccessDen edCause {
  // obsolete:  NVAL D_CL ENT_ D = 0,
  // obsolete: DEPRECATED = 1,
  USER_DEACT VATED = 2,
  USER_SUSPENDED = 3,

  RESERVED_4 = 4,
  RESERVED_5 = 5,
  RESERVED_6 = 6
}

/**
 * AccessDen ed error  s returned by delete_t ets endpo nt w n
 * by_user_ d  s suspended or deact vated.
 */
except on AccessDen ed {
  1: requ red str ng  ssage
  2: opt onal AccessDen edCause errorCause
}

struct UndeleteT etRequest {
  1: requ red  64 t et_ d (personalDataType = 'T et d')
  2: opt onal Wr ePathHydrat onOpt ons hydrat on_opt ons

  /**
   * Perform t  s de effects of undelet on even  f t  t et  s not deleted.
   *
   * T  flag  s useful  f   know that t  t et  s present  n Manhattan
   * but  s not undeleted w h respect to ot r serv ces.
   */
  3: opt onal bool force
}(hasPersonalData = 'true')

struct UndeleteT etResponse {
  1: requ red UndeleteT etState state
  2: opt onal t et.T et t et
}

struct EraseUserT etsRequest {
  1: requ red  64 user_ d (personalDataType = 'User d')
}(hasPersonalData = 'true')

struct Unret etRequest {
  /**
   * T   d of t  user who owns t  ret et.
   */
  1: requ red  64 user_ d (personalDataType = 'User d')

  /**
   * T  s ce t et that should be unret eted.
   */
  2: requ red  64 s ce_t et_ d (personalDataType = 'T et d')

  /**
    * T   s a un que  dent f er used  n both t  REST and GraphQL-dark
    * requests that w ll be used to correlate t  GraphQL mutat on requests to t  REST requests
    * dur ng a trans  on per od w n cl ents w ll be mov ng toward t et creat on v a GraphQL.
    * See also, t  "Compar son Test ng" sect on at go/t et-create-on-graphql-tdd for add  onal
    * context.
    */
  3: opt onal str ng compar son_ d (personalDataType = 'Un versallyUn que dent f erUu d')
}(hasPersonalData = 'true')

struct GetDeletedT etsRequest {
  1: requ red l st< 64> t et ds (personalDataType = 'T et d')
}(hasPersonalData = 'true')

struct GetDeletedT etResult {
  1: requ red  64 t et d (personalDataType = 'T et d')
  2: requ red DeletedT etState state
  4: opt onal deleted_t et.DeletedT et t et
}(hasPersonalData = 'true')

/**
 * Flus s t ets and/or t  r counts from cac .
 *
 * Typ cally w ll be used manually for test ng or w n a part cular problem  s
 * found that needs to be f xed by hand. Defaults to flush ng both t et
 * struct and assoc ated counts.
 */
struct FlushRequest {
  1: requ red l st< 64> t et_ ds (personalDataType = 'T et d')
  2: bool flushT ets = 1
  3: bool flushCounts = 1
}(hasPersonalData = 'true')

/**
 * A request to retr eve counts for one or more t ets.
 */
struct GetT etCountsRequest {
  1: requ red l st< 64> t et_ ds (personalDataType = 'T et d')
  2: bool  nclude_ret et_count = 0
  3: bool  nclude_reply_count = 0
  4: bool  nclude_favor e_count = 0
  5: bool  nclude_quote_count = 0
  6: bool  nclude_bookmark_count = 0
}(hasPersonalData = 'true')

/**
 * A response opt onally  nd cat ng one or more counts for a t et.
 */
struct GetT etCountsResult {
  1: requ red  64 t et_ d (personalDataType = 'T et d')
  2: opt onal  64 ret et_count (personalDataType = 'CountOfPr vateRet ets, CountOfPubl cRet ets')
  3: opt onal  64 reply_count (personalDataType = 'CountOfPr vateRepl es, CountOfPubl cRepl es')
  4: opt onal  64 favor e_count (personalDataType = 'CountOfPr vateL kes, CountOfPubl cL kes')
  5: opt onal  64 quote_count (personalDataType = 'CountOfPr vateRet ets, CountOfPubl cRet ets')
  6: opt onal  64 bookmark_count (personalDataType = 'CountOfPr vateL kes')
}(hasPersonalData = 'true')

/**
 * A request to  ncre nt t  cac d favor es count for a t et.
 *
 * Negat ve values decre nt t  count. T  request  s automat cally
 * repl cated to ot r data centers.
 */
struct  ncrT etFavCountRequest {
  1: requ red  64 t et_ d (personalDataType = 'T et d')
  2: requ red  32 delta (personalDataType = 'CountOfPr vateL kes, CountOfPubl cL kes')
}(hasPersonalData = 'true')

/**
 * A request to  ncre nt t  cac d bookmarks count for a t et.
 *
 * Negat ve values decre nt t  count. T  request  s automat cally
 * repl cated to ot r data centers.
 */
struct  ncrT etBookmarkCountRequest {
  1: requ red  64 t et_ d (personalDataType = 'T et d')
  2: requ red  32 delta (personalDataType = 'CountOfPr vateL kes')
}(hasPersonalData = 'true')

/**
 * Request to scrub geolocat on from 1 or more t ets, and repl cates to ot r
 * data centers.
 */
struct GeoScrub {
  1: requ red l st< 64> status_ ds (personalDataType = 'T et d')
  // OBSOLETE 2: bool wr e_through = 1
  3: bool hoseb rd_enqueue = 0
  4:  64 user_ d = 0 (personalDataType = 'User d') // should always be set for hoseb rd enqueue
}(hasPersonalData = 'true')

/**
 * Conta ns d fferent  nd cators of a t ets "nsfw" status.
 */
struct NsfwState {
  1: requ red bool nsfw_user
  2: requ red bool nsfw_adm n
  3: opt onal safety_label.SafetyLabel nsfw_h gh_prec s on_label
  4: opt onal safety_label.SafetyLabel nsfw_h gh_recall_label
}

/**
 *  nterface to T etyp e
 */
serv ce T etServ ce {
  /**
   * Performs a mult -get of t ets.  T  endpo nt  s geared towards fetch ng
   * t ets for t  AP , w h many f elds returned by default.
   *
   * T  response l st  s ordered t  sa  as t  requested  ds l st.
   */
  l st<GetT etResult> get_t ets(1: GetT etsRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Performs a mult -get of t ets.  T  endpo nt  s geared towards  nternal
   * process ng that needs only spec f c subsets of t  data.
   *
   * T  response l st  s ordered t  sa  as t  requested  ds l st.
   */
  l st<GetT etF eldsResult> get_t et_f elds(1: GetT etF eldsRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Execute a {@l nk GetT etCountsRequest} and return one or more {@l nk GetT etCountsResult}
   */
  l st<GetT etCountsResult> get_t et_counts(1: GetT etCountsRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Set/Update add  onal f elds on an ex st ng t et
   */
  vo d set_add  onal_f elds(1: SetAdd  onalF eldsRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Delete add  onal f elds on a t et
   */
  vo d delete_add  onal_f elds(1: DeleteAdd  onalF eldsRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Creates and saves a t et.
   *
   * URLs conta ned  n t  text w ll be shortened v a Talon. Val dat ons that are
   * handled by t  endpo nt  nclude:
   *
   *   - t et length not greater than 140 d splay characters, after URL shorten ng;
   *   - t et  s not a dupl cate of a recently created t et by t  sa  user;
   *   - user  s not suspended or deact vated;
   *   - text does not conta n malware urls, as determ ned by talon;
   *
   * C cks that are not handled  re that should be handled by t   b AP :
   *   - oauth aut nt cat on;
   *   - cl ent appl cat on has narrowcast ng/nullcast ng pr v leges;
   */
  PostT etResult post_t et(1: PostT etRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Creates and saves a ret et.
   *
   * Val dat ons that are handled by t  endpo nt  nclude:
   *
   *   - s ce t et ex sts;
   *   - s ce-t et user ex sts and  s not suspended or deact vated;
   *   - s ce-t et user  s not block ng ret eter;
   *   - user has not already ret eted t  s ce t et;
   *
   * C cks that are not handled  re that should be handled by t   b AP :
   *   - oauth aut nt cat on;
   *   - cl ent appl cat on has narrowcast ng/nullcast ng pr v leges;
   */
  PostT etResult post_ret et(1: Ret etRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Remove t ets.   removes all assoc ated f elds of t  t ets  n
   * cac  and t  pers stent storage.
   */
  l st<DeleteT etResult> delete_t ets(1: DeleteT etsRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error,
    3: AccessDen ed access_den ed)

  /**
   * Restore a deleted T et.
   *
   * T ets ex st  n a soft-deleted state for N days dur ng wh ch t y can be
   * restored by support agents follow ng t   nternal restorat on gu del nes.
   *  f t  undelete succeeds, t  T et  s g ven s m lar treat nt to a new
   * t et e.g  nserted  nto cac , sent to t  t  l ne serv ce, re ndexed by
   * TFlock etc.
   */
  UndeleteT etResponse undelete_t et(1: UndeleteT etRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Add or remove takedown countr es assoc ated w h a T et.
   */
  vo d takedown(1: TakedownRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Set or unset t  nsfw_adm n and/or nsfw_user b  of t et.core_data.
   **/
  vo d update_poss bly_sens  ve_t et(1: UpdatePoss blySens  veT etRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error
  )

  /**
   * Delete all t ets for a g ven user. Currently only called by Test User Serv ce, but  
   * can also use   ad-hoc.
   *
   * Note: regular user erasure  s handled by t  EraseUserT ets daemon.
   */
  vo d erase_user_t ets(1: EraseUserT etsRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Unret et a g ven t et.
   *
   * T re are two ways to unret et:
   *  - call deleteT ets() w h t  ret et d
   *  - call unret et() w h t  ret eter user d and s ceT et d
   *
   * T   s useful  f   want to be able to undo a ret et w hout hav ng to
   * keep track of a ret et d.
   */
  Unret etResult unret et(1: Unret etRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Get t et content and delet on t  s for soft-deleted t ets.
   *
   * T  response l st  s ordered t  sa  as t  requested  ds l st.
   */
  l st<GetDeletedT etResult> get_deleted_t ets(1: GetDeletedT etsRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Execute a {@l nk FlushRequest}
   */
  vo d flush(1: FlushRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Execute an {@l nk  ncrT etFavCountRequest}
   */
  vo d  ncr_t et_fav_count(1:  ncrT etFavCountRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Execute an {@l nk  ncrT etBookmarkCountRequest}
   */
  vo d  ncr_t et_bookmark_count(1:  ncrT etBookmarkCountRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Delete locat on data from all of a user's t ets.
   *
   * T  endpo nt  n  ates t  process of delet ng t  user's locat on data
   * from all of t  r t ets, as  ll as clear ng t  has_geotagged_statuses
   * flag of t  user. T   thod returns as soon as t  event  s enqueued,
   * but t  locat on data won't be scrubbed unt l t  event  s processed.
   * Usually t  latency for t  whole process to complete  s small, but  
   * could take up to a couple of m nutes  f t  user has a very large number
   * of t ets, or  f t  request gets backed up beh nd ot r requests that
   * need to scrub a large number of t ets.
   *
   * T  event  s processed by t  T etyp e geoscrub daemon.
   *
   */
  vo d delete_locat on_data(1: DeleteLocat onDataRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Execute a {@l nk GeoScrub} request.
   *
   */
  vo d scrub_geo(1: GeoScrub geo_scrub) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)
}
