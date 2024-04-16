na space java com.tw ter.t etyp e.thr ftjava
#@na space scala com.tw ter.t etyp e.thr ftscala

 nclude "com/tw ter/context/feature_context.thr ft"
 nclude "com/tw ter/expandodo/cards.thr ft"
 nclude "com/tw ter/g zmoduck/user.thr ft"
 nclude "com/tw ter/ d aserv ces/commons/ d aCommon.thr ft"
 nclude "com/tw ter/ d aserv ces/commons/ d a nformat on.thr ft"
 nclude "com/tw ter/ d aserv ces/commons/T et d a.thr ft"
 nclude "com/tw ter/servo/except ons.thr ft"
 nclude "com/tw ter/servo/cac /servo_repo.thr ft"
 nclude "com/tw ter/tseng/w hhold ng/w hhold ng.thr ft"
 nclude "com/tw ter/t etyp e/delete_locat on_data.thr ft"
 nclude "com/tw ter/t etyp e/trans ent_context.thr ft"
 nclude "com/tw ter/t etyp e/ d a_ent y.thr ft"
 nclude "com/tw ter/t etyp e/t et.thr ft"
 nclude "com/tw ter/t etyp e/t et_aud .thr ft"
 nclude "com/tw ter/t etyp e/stored_t et_ nfo.thr ft"
 nclude "com/tw ter/t etyp e/t et_serv ce.thr ft"

typedef  16 F eld d

struct User dent y {
  1: requ red  64  d
  2: requ red str ng screen_na 
  3: requ red str ng real_na 
# obsolete 4: bool deact vated = 0
# obsolete 5: bool suspended = 0
}

enum Hydrat onType {
  MENT ONS          = 1,
  URLS              = 2,
  CACHEABLE_MED A   = 3,
  QUOTED_TWEET_REF  = 4,
  REPLY_SCREEN_NAME = 5,
  D RECTED_AT       = 6,
  CONTR BUTOR       = 7,
  SELF_THREAD_ NFO  = 8
}

struct Cac dT et {
  1: requ red t et.T et t et
  // @obsolete 2: opt onal set< 16>  ncluded_add  onal_f elds
  3: set<Hydrat onType> completed_hydrat ons = []

  //  nd cates that a t et was deleted after be ng bounced for v olat ng
  // t  Tw ter Rules.
  // W n set to true, all ot r f elds  n Cac dT et are  gnored.
  4: opt onal bool  s_bounce_deleted

  //  nd cates w t r t  t et has safety labels stored  n Strato.
  // See com.tw ter.t etyp e.core.T etData.hasSafetyLabels for more deta ls.
  // @obsolete 5: opt onal bool has_safety_labels
} (pers sted='true', hasPersonalData='true')

struct  d aFaces {
  1: requ red map<T et d a. d aS zeType, l st< d a nformat on.Face>> faces
}

enum AsyncWr eEventType {
   NSERT                          = 1,
  DELETE                          = 2,
  UNDELETE                        = 3,
  SET_ADD T ONAL_F ELDS           = 4,
  DELETE_ADD T ONAL_F ELDS        = 5,
  UPDATE_POSS BLY_SENS T VE_TWEET = 6,
  UPDATE_TWEET_MED A              = 7,
  TAKEDOWN                        = 8,
  SET_RETWEET_V S B L TY          = 9
}

// an enum of act ons that could happen  n an async-wr e ( nsert or delete)
enum AsyncWr eAct on {
  HOSEB RD_ENQUEUE        = 1
  SEARCH_ENQUEUE          = 2
  // obsolete MA L_ENQUEUE            = 3
  FANOUT_DEL VERY         = 4
  // obsolete FACEBOOK_ENQUEUE        = 5
  TWEET_ NDEX             = 6
  T MEL NE_UPDATE         = 7
  CACHE_UPDATE            = 8
  REPL CAT ON             = 9
  // obsolete MONORA L_EXP RY_ENQUEUE = 10
  USER_GEOTAG_UPDATE      = 11
  // obsolete  B S_ENQUEUE            = 12
  EVENT_BUS_ENQUEUE       = 13
  // obsolete HOSEB RD_B NARY_ENQUEUE = 14
  TB RD_UPDATE            = 15
  RETWEETS_DELET ON       = 16
  GUANO_SCR BE            = 17
  MED A_DELET ON          = 18
  GEO_SEARCH_REQUEST_ D   = 19
  SEARCH_THR FT_ENQUEUE    = 20
  RETWEET_ARCH VAL_ENQUEUE = 21
}

# T  struct  s scr bed to test_t etyp e_fa led_async_wr e after
# an async-wr e act on has fa led mult ple retr es
struct Fa ledAsyncWr e {
  1: requ red AsyncWr eEventType event_type
  2: requ red AsyncWr eAct on act on
  3: opt onal t et.T et t et
} (pers sted='true', hasPersonalData='true')

# T  struct  s scr bed to test_t etyp e_detac d_ret ets after
# attempt ng to read a ret et for wh ch t  s ce t et has been deleted.
struct Detac dRet et {
  1: requ red  64 t et_ d (personalDataType='T et d')
  2: requ red  64 user_ d (personalDataType='User d')
  3: requ red  64 s ce_t et_ d (personalDataType='T et d')
} (pers sted='true', hasPersonalData='true')

struct T etCac Wr e {
  1: requ red  64 t et_ d (personalDataType = 'T et d')
  //  f t  t et  d  s a snowflake  d, t   s an offset s nce t et creat on. 
  //  f    s not a snowflake  d, t n t   s a Un x epoch t    n
  // m ll seconds. (T   dea  s that for most t ets, t  encod ng w ll make
  //   eas er to see t   nterval bet en events and w t r   occured soon
  // acter t et creat on.)
  2: requ red  64 t  stamp (personalDataType = 'Transact onT  stamp')
  3: requ red str ng act on // One of "set", "add", "replace", "cas", "delete"
  4: requ red servo_repo.Cac dValue cac d_value // Conta ns  tadata about t  cac d value
  5: opt onal Cac dT et cac d_t et
} (pers sted='true', hasPersonalData='true')

struct Async nsertRequest {
  12: requ red t et.T et t et
  18: requ red user.User user
  21: requ red  64 t  stamp
  // t  cac able vers on of t et from f eld 12
  29: requ red Cac dT et cac d_t et
  # 13: obsolete t et.T et  nternal_t et
  19: opt onal t et.T et s ce_t et
  20: opt onal user.User s ce_user
  // Used for quote t et feature
  22: opt onal t et.T et quoted_t et
  23: opt onal user.User quoted_user
  28: opt onal  64 parent_user_ d
  // Used for del ver ng t  request d of a geotagged t et
  24: opt onal str ng geo_search_request_ d
  # 7: obsolete
  #  f not spec f ed, all async  nsert act ons are perfor d.  f spec f ed, only
  # t  spec f ed act on  s perfor d; t   s used for retry ng spec f c act ons
  # that fa led on a prev ous attempt.
  10: opt onal AsyncWr eAct on retry_act on
  # 11: obsolete: bool from_monora l = 0
  # 14: obsolete
  15: opt onal feature_context.FeatureContext feature_context
  # 16: obsolete
  # 17: obsolete
  # 26: obsolete: opt onal t et.T et debug_t et_copy
  27: opt onal map<t et.T etCreateContextKey, str ng> add  onal_context
  30: opt onal trans ent_context.Trans entCreateContext trans ent_context
  // Used to c ck w t r t  sa  t et has been quoted mult ple
  // t  s by a g ven user.
  31: opt onal bool quoter_has_already_quoted_t et
  32: opt onal  n  alT etUpdateRequest  n  alT etUpdateRequest
  // User  ds of users  nt oned  n note t et. Used for tls events
  33: opt onal l st< 64> note_t et_ nt oned_user_ ds
}

struct AsyncUpdatePoss blySens  veT etRequest {
  1: requ red t et.T et t et
  2: requ red user.User user
  3: requ red  64 by_user_ d
  4: requ red  64 t  stamp
  5: opt onal bool nsfw_adm n_change
  6: opt onal bool nsfw_user_change
  7: opt onal str ng note
  8: opt onal str ng host
  9: opt onal AsyncWr eAct on act on
}

struct AsyncUpdateT et d aRequest {
  1: requ red  64 t et_ d
  2: requ red l st< d a_ent y. d aEnt y> orphaned_ d a
  3: opt onal AsyncWr eAct on retry_act on
  4: opt onal l st< d aCommon. d aKey>  d a_keys
}

struct AsyncSetAdd  onalF eldsRequest {
  1: requ red t et.T et add  onal_f elds
  3: requ red  64 t  stamp
  4: requ red  64 user_ d
  2: opt onal AsyncWr eAct on retry_act on
}

struct AsyncSetRet etV s b l yRequest {
  1: requ red  64 ret et_ d
  // W t r to arch ve or unarch ve(v s ble=true) t  ret et_ d edge  n t  Ret etsGraph.
  2: requ red bool v s ble
  3: requ red  64 src_ d
  5: requ red  64 ret et_user_ d
  6: requ red  64 s ce_t et_user_ d
  7: requ red  64 t  stamp
  4: opt onal AsyncWr eAct on retry_act on
}

struct SetRet etV s b l yRequest {
  1: requ red  64 ret et_ d
  // W t r to arch ve or unarch ve(v s ble=true) t  ret et_ d edge  n t  Ret etsGraph.
  2: requ red bool v s ble
}

struct AsyncEraseUserT etsRequest {
  1: requ red  64 user_ d
  3: requ red  64 flock_cursor
  4: requ red  64 start_t  stamp
  5: requ red  64 t et_count
}

struct AsyncDeleteRequest {
  4: requ red t et.T et t et
  11: requ red  64 t  stamp
  2: opt onal user.User user
  9: opt onal  64 by_user_ d
  12: opt onal t et_aud .Aud DeleteT et aud _passthrough
  13: opt onal  64 cascaded_from_t et_ d
  #  f not spec f ed, all async-delete act ons are perfor d.  f spec f ed, only
  # t  spec f ed act on  s perfor d; t   s used for retry ng spec f c act ons
  # that fa led on a prev ous attempt.
  3: opt onal AsyncWr eAct on retry_act on
  5: bool delete_ d a = 1
  6: bool delete_ret ets = 1
  8: bool scr be_for_aud  = 1
  15: bool  s_user_erasure = 0
  17: bool  s_bounce_delete = 0
  18: opt onal bool  s_last_quote_of_quoter
  19: opt onal bool  s_adm n_delete
}

struct AsyncUndeleteT etRequest {
  1: requ red t et.T et t et
  3: requ red user.User user
  4: requ red  64 t  stamp
  // t  cac able vers on of t et from f eld 1
  12: requ red Cac dT et cac d_t et
  # 2: obsolete t et.T et  nternal_t et
  5: opt onal AsyncWr eAct on retry_act on
  6: opt onal  64 deleted_at
  7: opt onal t et.T et s ce_t et
  8: opt onal user.User s ce_user
  9: opt onal t et.T et quoted_t et
  10: opt onal user.User quoted_user
  11: opt onal  64 parent_user_ d
  13: opt onal bool quoter_has_already_quoted_t et
}

struct Async ncrFavCountRequest {
  1: requ red  64 t et_ d
  2: requ red  32 delta
}

struct Async ncrBookmarkCountRequest {
  1: requ red  64 t et_ d
  2: requ red  32 delta
}

struct AsyncDeleteAdd  onalF eldsRequest {
  6: requ red  64 t et_ d
  7: requ red l st< 16> f eld_ ds
  4: requ red  64 t  stamp
  5: requ red  64 user_ d
  3: opt onal AsyncWr eAct on retry_act on
}

// Used for both t et and user takedowns.
// user w ll be None for user takedowns because user  s only used w n scr be_for_aud  or
// eventbus_enqueue are true, wh ch  s never t  case for user takedown.
struct AsyncTakedownRequest {
  1: requ red t et.T et t et

  // Author of t  t et.  Used w n scr be_for_aud  or eventbus_enqueue are true wh ch  s t  case
  // for t et takedown but not user takedown.
  2: opt onal user.User user

  // T  f eld  s t  result ng l st of takedown country codes on t  t et after t 
  // countr es_to_add and countr es_to_remove changes have been appl ed.
  13: l st<w hhold ng.TakedownReason> takedown_reasons = []

  // T  f eld  s t  l st of takedown reaons to add to t  t et.
  14: l st<w hhold ng.TakedownReason> reasons_to_add = []

  // T  f eld  s t  l st of takedown reasons to remove from t  t et.
  15: l st<w hhold ng.TakedownReason> reasons_to_remove = []

  // T  f eld determ nes w t r or not T etyp e should wr e takedown aud s
  // for t  request to Guano.
  6: requ red bool scr be_for_aud 

  // T  f eld determ nes w t r or not T etyp e should enqueue a
  // T etTakedownEvent to EventBus and Hoseb rd for t  request.
  7: requ red bool eventbus_enqueue

  // T  f eld  s sent as part of t  takedown aud  that's wr ten to Guano,
  // and  s not pers sted w h t  takedown  self.
  8: opt onal str ng aud _note

  // T  f eld  s t   D of t  user who  n  ated t  takedown.     s used
  // w n aud  ng t  takedown  n Guano.   f unset,   w ll be logged as -1.
  9: opt onal  64 by_user_ d

  // T  f eld  s t  host w re t  request or g nated or t  remote  P that
  //  s assoc ated w h t  request.     s used w n aud  ng t  takedown  n
  // Guano.   f unset,   w ll be logged as "<unknown>".
  10: opt onal str ng host

  11: opt onal AsyncWr eAct on retry_act on
  12: requ red  64 t  stamp
}

struct SetT etUserTakedownRequest {
  1: requ red  64 t et_ d
  2: requ red bool has_takedown
  3: opt onal  64 user_ d
}

enum DataErrorCause {
  UNKNOWN = 0
  // Returned on set_t et_user_takedown w n
  // t  SetT etUserTakedownRequest.user_ d does not match t  author
  // of t  t et  dent f ed by SetT etUserTakedownRequest.t et_ d.
  USER_TWEET_RELAT ONSH P = 1
}

/**
 * DataError  s returned for operat ons that perform data changes,
 * but encountered an  ncons stency, and t  operat on cannot
 * be  an nfully perfor d.
 */
except on DataError {
  1: requ red str ng  ssage
  2: opt onal DataErrorCause errorCause
}

struct Repl catedDeleteAdd  onalF eldsRequest {
  /**  s a map for backwards compat b l y, but w ll only conta n a s ngle t et  d */
  1: requ red map< 64, l st< 16>> f elds_map
}

struct CascadedDeleteT etRequest {
  1: requ red  64 t et_ d
  2: requ red  64 cascaded_from_t et_ d
  3: opt onal t et_aud .Aud DeleteT et aud _passthrough
}

struct QuotedT etDeleteRequest {
  1:  64 quot ng_t et_ d
  2:  64 quoted_t et_ d
  3:  64 quoted_user_ d
}

struct QuotedT etTakedownRequest {
  1:  64 quot ng_t et_ d
  2:  64 quoted_t et_ d
  3:  64 quoted_user_ d
  4: l st<str ng> takedown_country_codes = []
  5: l st<w hhold ng.TakedownReason> takedown_reasons = []
}

struct Repl cated nsertT et2Request {
  1: requ red Cac dT et cac d_t et
  // Used to c ck w t r t  sa  t et has been quoted by a user.
  2: opt onal bool quoter_has_already_quoted_t et
  3: opt onal  n  alT etUpdateRequest  n  alT etUpdateRequest
}

struct Repl catedDeleteT et2Request {
  1: requ red t et.T et t et
  2: requ red bool  s_erasure
  3: requ red bool  s_bounce_delete
  4: opt onal bool  s_last_quote_of_quoter
}

struct Repl catedSetRet etV s b l yRequest {
  1: requ red  64 src_ d
  // W t r to arch ve or unarch ve(v s ble=true) t  ret et_ d edge  n t  Ret etsGraph.
  2: requ red bool v s ble
}

struct Repl catedUndeleteT et2Request {
  1: requ red Cac dT et cac d_t et
  2: opt onal bool quoter_has_already_quoted_t et
}

struct GetStoredT etsOpt ons {
  1: bool bypass_v s b l y_f lter ng = 0
  2: opt onal  64 for_user_ d
  3: l st<F eld d> add  onal_f eld_ ds = []
}

struct GetStoredT etsRequest {
  1: requ red l st< 64> t et_ ds
  2: opt onal GetStoredT etsOpt ons opt ons
}

struct GetStoredT etsResult {
  1: requ red stored_t et_ nfo.StoredT et nfo stored_t et
}

struct GetStoredT etsByUserOpt ons {
  1: bool bypass_v s b l y_f lter ng = 0
  2: bool set_for_user_ d = 0
  3: opt onal  64 start_t  _msec
  4: opt onal  64 end_t  _msec
  5: opt onal  64 cursor
  6: bool start_from_oldest = 0
  7: l st<F eld d> add  onal_f eld_ ds = []
}

struct GetStoredT etsByUserRequest {
  1: requ red  64 user_ d
  2: opt onal GetStoredT etsByUserOpt ons opt ons
}

struct GetStoredT etsByUserResult {
  1: requ red l st<stored_t et_ nfo.StoredT et nfo> stored_t ets
  2: opt onal  64 cursor
}

/* T   s a request to update an  n  al t et based on t  creat on of a ed  t et
 *  n  alT et d: T  t et to be updated
 * ed T et d: T  t et be ng created, wh ch  s an ed  of  n  alT et d
 * selfPermal nk: A self permal nk for  n  alT et d
 */
struct  n  alT etUpdateRequest {
  1: requ red  64  n  alT et d
  2: requ red  64 ed T et d
  3: opt onal t et.ShortenedUrl selfPermal nk
}

serv ce T etServ ce nternal extends t et_serv ce.T etServ ce {

  /**
   * Performs t  async port on of T etServ ce.erase_user_t ets.
   * Only t etyp e  self can call t .
   */
  vo d async_erase_user_t ets(1: AsyncEraseUserT etsRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Performs t  async port on of T etServ ce.post_t et.
   * Only t etyp e  self can call t .
   */
  vo d async_ nsert(1: Async nsertRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Performs t  async port on of T etServ ce.delete_t ets.
   * Only t etyp e  self can call t .
   */
  vo d async_delete(1: AsyncDeleteRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Performs t  async port on of T etServ ce.undelete_t et.
   * Only t etyp e  self can call t .
   */
  vo d async_undelete_t et(1: AsyncUndeleteT etRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Performs t  async port on of T etServ ce.update_poss bly_sens  ve_t et.
   * Only t etyp e  self can call t .
   */
  vo d async_update_poss bly_sens  ve_t et(1: AsyncUpdatePoss blySens  veT etRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Performs t  async port on of T etServ ce. ncr_t et_fav_count.
   * Only t etyp e  self can call t .
   */
  vo d async_ ncr_fav_count(1: Async ncrFavCountRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Performs t  async port on of T etServ ce. ncr_t et_bookmark_count.
   * Only t etyp e  self can call t .
   */
  vo d async_ ncr_bookmark_count(1: Async ncrBookmarkCountRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Performs t  async port on of T etServ ce.set_add  onal_f elds.
   * Only t etyp e  self can call t .
   */
  vo d async_set_add  onal_f elds(1: AsyncSetAdd  onalF eldsRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Performs t  async port on of T etServ ce nternal.set_ret et_v s b l y.
   * Only t etyp e  self can call t .
   */
  vo d async_set_ret et_v s b l y(1: AsyncSetRet etV s b l yRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Set w t r t  spec f ed ret et  D should be  ncluded  n  s s ce t et's ret et count.
   * T  endpo nt  s  nvoked from a t etyp e-daemon to adjust ret et counts for all t ets a
   * suspended or fraudulent (e.g. ROPO-'d) user has ret eted to d s ncent v ze t  r false engage nt.
   */
  vo d set_ret et_v s b l y(1: SetRet etV s b l yRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Performs t  async port on of T etServ ce.delete_add  onal_f elds.
   * Only t etyp e  self can call t .
   */
  vo d async_delete_add  onal_f elds(1: AsyncDeleteAdd  onalF eldsRequest f eld_delete) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Performs t  async port on of T etServ ce.takedown.
   * Only t etyp e  self can call t .
   */
  vo d async_takedown(1: AsyncTakedownRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Update t  t et's takedown f elds w n a user  s taken down.
   * Only t etyp e's UserTakedownChange daemon can call t .
   */
  vo d set_t et_user_takedown(1: SetT etUserTakedownRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error,
    3: DataError data_error)

  /**
   * Cascade delete t et  s t  log c for remov ng t ets that are detac d
   * from t  r dependency wh ch has been deleted. T y are already f ltered
   * out from serv ng, so t  operat on reconc les storage w h t  v ew
   * presented by T etyp e.
   * T  RPC call  s delegated from daemons or batch jobs. Currently t re
   * are two use-cases w n t  call  s  ssued:
   * *   Delet ng detac d ret ets after t  s ce t et was deleted.
   *     T   s done through Ret etsDelet on daemon and t 
   *     CleanupDetac dRet ets job.
   * *   Delet ng ed s of an  n  al t et that has been deleted.
   *     T   s done by CascadedEd edT etDelete daemon.
   *     Note that, w n serv ng t  or g nal delete request for an ed ,
   *     t   n  al t et  s only deleted, wh ch makes all ed s h dden.
   */
  vo d cascaded_delete_t et(1: CascadedDeleteT etRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Update t  t  stamp of t  user's most recent request to delete
   * locat on data on t  r t ets. T  does not actually remove t 
   * geo  nformat on from t  user's t ets, but   w ll prevent t  geo
   *  nformat on for t  user's t ets from be ng returned by
   * T etyp e.
   */
  vo d scrub_geo_update_user_t  stamp(1: delete_locat on_data.DeleteLocat onData request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Look up t ets quot ng a t et that has been deleted and enqueue a compl ance event.
   * Only t etyp e's QuotedT etDelete daemon can call t .
  **/
  vo d quoted_t et_delete(1: QuotedT etDeleteRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Look up t ets quot ng a t et that has been taken down and enqueue a compl ance event.
   * Only t etyp e's QuotedT etTakedown daemon can call t .
  **/
  vo d quoted_t et_takedown(1: QuotedT etTakedownRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Repl cates T etServ ce.get_t et_counts from anot r cluster.
   */
  vo d repl cated_get_t et_counts(1: t et_serv ce.GetT etCountsRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Repl cates T etServ ce.get_t et_f elds from anot r cluster.
   */
  vo d repl cated_get_t et_f elds(1: t et_serv ce.GetT etF eldsRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Repl cates T etServ ce.get_t ets from anot r cluster.
   */
  vo d repl cated_get_t ets(1: t et_serv ce.GetT etsRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Repl cates a T etServ ce.post_t et  nsertT et event from anot r cluster.
   * Note: v1 vers on of t  endpo nt prev ously just took a T et wh ch  s why   was replaced
   */
  vo d repl cated_ nsert_t et2(1: Repl cated nsertT et2Request request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Repl cates a T etServ ce.delete_t ets DeleteT et event from anot r cluster.
   */
  vo d repl cated_delete_t et2(1: Repl catedDeleteT et2Request request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Repl cates a T etServ ce. ncr_t et_fav_count event from anot r cluster.
   */
  vo d repl cated_ ncr_fav_count(1:  64 t et_ d, 2:  32 delta) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Repl cates a T etServ ce. ncr_t et_bookmark_count event from anot r cluster.
   */
  vo d repl cated_ ncr_bookmark_count(1:  64 t et_ d, 2:  32 delta) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Repl cates a T etServ ce nternal.set_ret et_v s b l y event from anot r cluster.
   */
  vo d repl cated_set_ret et_v s b l y(1: Repl catedSetRet etV s b l yRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Repl cates a T etServ ce.scrub_geo from anot r cluster.
   */
  vo d repl cated_scrub_geo(1: l st< 64> t et_ ds) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Repl cates a T etServ ce.set_add  onal_f elds event from anot r cluster.
   */
  vo d repl cated_set_add  onal_f elds(
    1: t et_serv ce.SetAdd  onalF eldsRequest request
  ) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Repl cates a T etServ ce.delete_add  onal_f elds event from anot r cluster.
   */
  vo d repl cated_delete_add  onal_f elds(
    1: Repl catedDeleteAdd  onalF eldsRequest request
  ) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Repl cates a T etServ ce.undelete_t et event from anot r cluster.
   * Note: v1 vers on of t  endpo nt prev ously just took a T et wh ch  s why   was replaced
   */
  vo d repl cated_undelete_t et2(1: Repl catedUndeleteT et2Request request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Repl cates a T etServ ce.takedown event from anot r cluster.
   */
  vo d repl cated_takedown(1: t et.T et t et) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Repl cates a T etServ ce.update_poss bly_sens  ve_t et event from anot r cluster.
   */
  vo d repl cated_update_poss bly_sens  ve_t et(1: t et.T et t et) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
   * Fetc s hydrated T ets and so   tadata  rrespect ve of t  T ets' state.
   */
  l st<GetStoredT etsResult> get_stored_t ets(1: GetStoredT etsRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)

  /**
  * Fetc s hydrated T ets and so   tadata for a part cular user,  rrespect ve of t  T ets'
  * state.
  */
  GetStoredT etsByUserResult get_stored_t ets_by_user(1: GetStoredT etsByUserRequest request) throws (
    1: except ons.Cl entError cl ent_error,
    2: except ons.ServerError server_error)
}
