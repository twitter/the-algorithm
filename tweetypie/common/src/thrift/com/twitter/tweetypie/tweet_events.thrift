na space java com.tw ter.t etyp e.thr ftjava
na space py gen.tw ter.t etyp e.t et_events
#@na space scala com.tw ter.t etyp e.thr ftscala
#@na space strato com.tw ter.t etyp e
na space rb T etyP e
na space go t etyp e

 nclude "com/tw ter/tseng/w hhold ng/w hhold ng.thr ft"
 nclude "com/tw ter/t etyp e/trans ent_context.thr ft"
 nclude "com/tw ter/t etyp e/t et.thr ft"
 nclude "com/tw ter/t etyp e/t et_aud .thr ft"
 nclude "com/tw ter/g zmoduck/user.thr ft"

/**
 * SafetyType encodes t  event user's safety state  n an enum so downstream
 * event processors can f lter events w hout hav ng to load t  user.
 */
enum SafetyType {
  PR VATE    = 0   // user.safety. sProtected
  RESTR CTED = 1   // !PR VATE && user.safety.suspended
  PUBL C     = 2   // !(PR VATE || RESTR CTED)
  RESERVED0  = 3
  RESERVED1  = 4
  RESERVED2  = 5
  RESERVED3  = 6
}

struct T etCreateEvent {
  /**
   * T  t et that has been created.
   */
  1: t et.T et t et

  /**
   * T  user who owns t  created t et.
   */
  2: user.User user

  /**
   * T  t et be ng ret eted.
   */
  3: opt onal t et.T et s ce_t et

  /**
   * T  user who owns s ce_t et.
   */
  4: opt onal user.User s ce_user

  /**
   * T  user whose t et or ret et  s be ng ret eted.
   *
   * T   s t   d of t  user who owns
   * t et.core_data.share.parent_status_ d.  n many cases t  w ll be t 
   * sa  as s ce_user. d;    s d fferent w n t  t et  s created v a
   * anot r ret et. See t  explanat on of s ce_user_ d and parent_user_ d
   *  n Share for examples.
   */
  5: opt onal  64 ret et_parent_user_ d (personalDataType = 'User d')

  /**
   * T  t et quoted  n t  created t et.
   */
  6: opt onal t et.T et quoted_t et

  /**
   * T  user who owns quoted_t et.
   */
  7: opt onal user.User quoted_user

  /**
   * Arb rary passthrough  tadata about t et creat on.
   *
   * See T etCreateContextKey for more deta ls about t  data that may be
   * present  re.
   */
  8: opt onal map<t et.T etCreateContextKey, str ng> add  onal_context (personalDataTypeValue='User d')

  /**
   * Add  onal request argu nts passed through to consu rs.
   */
  9: opt onal trans ent_context.Trans entCreateContext trans ent_context

  /**
  * Flag expos ng  f a quoted t et has been quoted by t  user prev ously.
  **/
  10: opt onal bool quoter_has_already_quoted_t et
}(pers sted='true', hasPersonalData = 'true')

struct T etDeleteEvent {
  /**
   * T  t et be ng deleted.
   */
  1: t et.T et t et

  /**
   * T  user who owns t  deleted t et.
   */
  2: opt onal user.User user

  /**
   * W t r t  t et was deleted as part of user erasure (t  process of delet ng t ets
   * belong ng to deact vated accounts).
   *
   * T se delet ons occur  n h gh volu  sp kes and t  t ets have already been made  nv s ble
   * externally.   may w sh to process t m  n batc s or offl ne.
   */
  3: opt onal bool  s_user_erasure

  /**
   * Aud   nformat on from t  DeleteT etRequest that caused t  delet on.
   *
   * T  f eld  s used to track t  reason for delet on  n non-user- n  ated
   * t et delet ons, l ke Tw ter support agents delet ng t ets or spam
   * cleanup.
   */
  4: opt onal t et_aud .Aud DeleteT et aud 

  /**
   *  d of t  user  n  at ng t  request.
   *   could be e  r t  owner of t  t et or an adm n.
   *    s used for scrubb ng.
   */
  5: opt onal  64 by_user_ d (personalDataType = 'User d')

  /**
   * W t r t  t et was deleted by an adm n user or not
   *
   *    s used for scrubb ng.
   */
  6: opt onal bool  s_adm n_delete
}(pers sted='true', hasPersonalData = 'true')

struct T etUndeleteEvent {
  1: t et.T et t et
  2: opt onal user.User user
  3: opt onal t et.T et s ce_t et
  4: opt onal user.User s ce_user
  5: opt onal  64 ret et_parent_user_ d (personalDataType = 'User d')
  6: opt onal t et.T et quoted_t et
  7: opt onal user.User quoted_user
  // t  stamp of t  delet on that t  undelete  s revers ng
  8: opt onal  64 deleted_at_msec
}(pers sted='true', hasPersonalData = 'true')

/**
 * W n a user deletes t  locat on  nformat on for t  r t ets,   send one
 * T etScrubGeoEvent for every t et from wh ch t  locat on  s removed.
 *
 * Users cause t  by select ng "Delete locat on  nformat on"  n Sett ngs ->
 * Pr vacy.
 */
struct T etScrubGeoEvent {
  1:  64 t et_ d (personalDataType = 'T et d')
  2:  64 user_ d (personalDataType = 'User d')
}(pers sted='true', hasPersonalData = 'true')

/**
 * W n a user deletes t  locat on  nformat on for t  r t ets,   send one
 * UserScrubGeoEvent w h t  max t et  D that was scrubbed ( n add  on to
 * send ng mult ple T etScrubGeoEvents as descr bed above).
 *
 * Users cause t  by select ng "Delete locat on  nformat on"  n Sett ngs ->
 * Pr vacy. T  add  onal event  s sent to ma nta n backwards compat b l y
 * w h Hoseb rd.
 */
struct UserScrubGeoEvent {
  1:  64 user_ d (personalDataType = 'User d')
  2:  64 max_t et_ d (personalDataType = 'T et d')
}(pers sted='true', hasPersonalData = 'true')

struct T etTakedownEvent {
  1:  64 t et_ d (personalDataType = 'T et d')
  2:  64 user_ d (personalDataType = 'User d')
  // T   s t  complete l st of takedown country codes for t  t et,
  //  nclud ng whatever mod f cat ons  re made to tr gger t  event.
  // @deprecated Prefer takedown_reasons once TWEETYP E-4329 deployed
  3: l st<str ng> takedown_country_codes = []
  // T   s t  complete l st of takedown reasons for t  t et,
  //  nclud ng whatever mod f cat ons  re made to tr gger t  event.
  4: l st<w hhold ng.TakedownReason> takedown_reasons = []
}(pers sted='true', hasPersonalData = 'true')

struct Add  onalF eldUpdateEvent {
  // Only conta ns t  t et  d and mod f ed or newly added f elds on that t et.
  // Unchanged f elds and t et core data are om ted.
  1: t et.T et updated_f elds
  2: opt onal  64 user_ d (personalDataType = 'User d')
}(pers sted='true', hasPersonalData = 'true')

struct Add  onalF eldDeleteEvent {
  // a map from t et  d to deleted f eld  ds
  // Each event w ll only conta n one t et.
  1: map< 64, l st< 16>> deleted_f elds (personalDataTypeKey='T et d')
  2: opt onal  64 user_ d (personalDataType = 'User d')
}(pers sted='true', hasPersonalData = 'true')

// T  event  s only logged to scr be not sent to EventBus
struct T et d aTagEvent {
  1:  64 t et_ d (personalDataType = 'T et d')
  2:  64 user_ d (personalDataType = 'User d')
  3: set< 64> tagged_user_ ds (personalDataType = 'User d')
  4: opt onal  64 t  stamp_ms
}(pers sted='true', hasPersonalData = 'true')

struct T etPoss blySens  veUpdateEvent {
  1:  64 t et_ d (personalDataType = 'T et d')
  2:  64 user_ d (personalDataType = 'User d')
  // T  below two f elds conta n t  results of t  update.
  3: bool nsfw_adm n
  4: bool nsfw_user
}(pers sted='true', hasPersonalData = 'true')

struct QuotedT etDeleteEvent {
  1:  64 quot ng_t et_ d (personalDataType = 'T et d')
  2:  64 quot ng_user_ d (personalDataType = 'User d')
  3:  64 quoted_t et_ d (personalDataType = 'T et d')
  4:  64 quoted_user_ d (personalDataType = 'User d')
}(pers sted='true', hasPersonalData = 'true')

struct QuotedT etTakedownEvent {
  1:  64 quot ng_t et_ d (personalDataType = 'T et d')
  2:  64 quot ng_user_ d (personalDataType = 'User d')
  3:  64 quoted_t et_ d (personalDataType = 'T et d')
  4:  64 quoted_user_ d (personalDataType = 'User d')
  // T   s t  complete l st of takedown country codes for t  t et,
  //  nclud ng whatever mod f cat ons  re made to tr gger t  event.
  // @deprecated Prefer takedown_reasons
  5: l st<str ng> takedown_country_codes = []
  // T   s t  complete l st of takedown reasons for t  t et,
  //  nclud ng whatever mod f cat ons  re made to tr gger t  event.
  6: l st<w hhold ng.TakedownReason> takedown_reasons = []
}(pers sted='true', hasPersonalData = 'true')

un on T etEventData {
  1:  T etCreateEvent t et_create_event
  2:  T etDeleteEvent t et_delete_event
  3:  Add  onalF eldUpdateEvent add  onal_f eld_update_event
  4:  Add  onalF eldDeleteEvent add  onal_f eld_delete_event
  5:  T etUndeleteEvent t et_undelete_event
  6:  T etScrubGeoEvent t et_scrub_geo_event
  7:  T etTakedownEvent t et_takedown_event
  8:  UserScrubGeoEvent user_scrub_geo_event
  9:  T etPoss blySens  veUpdateEvent t et_poss bly_sens  ve_update_event
  10: QuotedT etDeleteEvent quoted_t et_delete_event
  11: QuotedT etTakedownEvent quoted_t et_takedown_event
}(pers sted='true', hasPersonalData = 'true')

/**
 * @deprecated
 */
struct C cksum {
  1:  32 c cksum
}(pers sted='true')

struct T etEventFlags {
  /**
   * @deprecated Was dark_for_serv ce.
   */
  1: l st<str ng> unused1 = []

  2:  64 t  stamp_ms

  3: opt onal SafetyType safety_type

  /**
   * @deprecated Was c cksum.
   */
  4: opt onal C cksum unused4
}(pers sted='true')

/**
 * A T etEvent  s a not f cat on publ s d to t  t et_events stream.
 */
struct T etEvent {
  1: T etEventData data
  2: T etEventFlags flags
}(pers sted='true', hasPersonalData = 'true')
