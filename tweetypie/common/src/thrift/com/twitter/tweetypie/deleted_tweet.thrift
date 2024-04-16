na space java com.tw ter.t etyp e.thr ftjava
#@na space scala com.tw ter.t etyp e.thr ftscala
#@na space strato com.tw ter.t etyp e
na space py gen.tw ter.t etyp e.deletedt et
na space rb T etyP e
na space go t etyp e

// Structs used for response from getDeletedT ets

struct DeletedT et d aEnt y {
  1: requ red  64  d
  2: requ red  8  d aType
  3: requ red  16 w dth
  4: requ red  16   ght
} (pers sted = 'true')

struct DeletedT etShare {
  1: requ red  64 s ceStatus d
  2: requ red  64 s ceUser d
  3: requ red  64 parentStatus d
} (pers sted = 'true')

/**
 * A t et that has been soft- or hard-deleted.
 *
 * Or g nally DeletedT et used t  sa  f eld  ds as tb rd.Status.
 * T   s no longer t  case.
 */
struct DeletedT et {
  // Uses t  sa  f eld  ds as tb rd.thr ft so   can eas ly map and add f elds later
  1: requ red  64  d

  /**
   * User who created t  t et. Only ava lable for soft-deleted t ets.
   */
  2: opt onal  64 user d

  /**
   * Content of t  t et. Only ava lable for soft-deleted t ets.
   */
  3: opt onal str ng text

  /**
   * W n t  t et was created. Only ava lable for soft-deleted t ets.
   */
  5: opt onal  64 createdAtSecs

  /**
   * Ret et  nformat on  f t  deleted t et was a ret et. Only ava lable
   * for soft-deleted t ets.
   */
  7: opt onal DeletedT etShare share

  /**
   *  d a  tadata  f t  deleted t et  ncluded  d a. Only ava lable for
   * soft-deleted t ets.
   */
  14: opt onal l st<DeletedT et d aEnt y>  d a

  /**
   * T  t   w n t  t et was deleted by a user,  n epoch m ll seconds, e  r normally (aka
   * "softDelete") or v a a bouncer flow (aka "bounceDelete").
   *
   * T  data  s not ava lable for all deleted t ets.
   */
  18: opt onal  64 deletedAtMsec

  /**
   * T  t   w n t  t et was permanently deleted,  n epoch m ll seconds.
   *
   * T  data  s not ava lable for all deleted t ets.
   */
  19: opt onal  64 hardDeletedAtMsec

  /**
  * T   D of t  NoteT et assoc ated w h t  T et  f one ex sts. T   s used by safety tools
  * to fetch t  NoteT et content w n v ew ng soft deleted T ets.
  */
  20: opt onal  64 noteT et d

  /**
  * Spec f es  f t  T et can be expanded  nto t  NoteT et, or  f t y have t  sa  text. Can
  * be used to d st ngu sh bet en Longer T ets and R chText T ets.
  */
  21: opt onal bool  sExpandable
} (pers sted = 'true')
