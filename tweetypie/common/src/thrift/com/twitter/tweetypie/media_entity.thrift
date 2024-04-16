na space java com.tw ter.t etyp e.thr ftjava
#@na space scala com.tw ter.t etyp e.thr ftscala
#@na space strato com.tw ter.t etyp e
na space py gen.tw ter.t etyp e. d a_ent y
na space rb T etyP e. d a_ent y
na space go t etyp e. d a_ent y

 nclude "com/tw ter/ d aserv ces/commons/ d a nformat on.thr ft"
 nclude "com/tw ter/ d aserv ces/commons/ d aCommon.thr ft"
 nclude "com/tw ter/ d aserv ces/commons/T et d a.thr ft"

/**
 * DEPRECATED
 * An RGB color.
 *
 * Each  8 should be  nterpreted as uns gned, rang ng  n value from 0 to
 * 255. Borro d from g zmoduck/user.thr ft.
 *
 * T  way  n wh ch   use ColorValue  re  s as  tadata for a  d a f le,
 * so   needs to be annotated as hav ng personal data. F elds that are of
 * structured types cannot be annotated, so   have to put t  annotat on
 * on t  structure  self's f elds even though  's more confus ng to do so
 * and could  ntroduce  ssues  f so one else reuses ColorValue outs de of
 * t  context of a  d a f le.
 */
struct ColorValue {
  1:  8 red  (personalDataType = ' d aF le')
  2:  8 green (personalDataType = ' d aF le')
  3:  8 blue (personalDataType = ' d aF le')
}(pers sted = 'true', hasPersonalData = 'true')

struct  d aEnt y {
  1:  16 from_ ndex (personalDataType = ' d aF le')
  2:  16 to_ ndex (personalDataType = ' d aF le')

  /**
   * T  shortened t.co url found  n t  t et text.
   */
  3: str ng url (personalDataType = 'ShortUrl')

  /**
   * T  text to d splay  n place of t  shortened url.
   */
  4: str ng d splay_url (personalDataType = 'LongUrl')

  /**
   * T  url to t   d a asset (a prev ew  mage  n t  case of a v deo).
   */
  5: str ng  d a_url (personalDataType = 'LongUrl')

  /**
   * T  https vers on of  d a_url.
   */
  6: str ng  d a_url_https (personalDataType = 'LongUrl')

  /**
   * T  expanded  d a permal nk.
   */
  7: str ng expanded_url (personalDataType = 'LongUrl')

  8:  d aCommon. d a d  d a_ d (strato.space = " d a", strato.na  = " d a", personalDataType = ' d a d')
  9: bool nsfw
  10: set<T et d a. d aS ze> s zes
  11: str ng  d a_path
  12: opt onal bool  s_protected

  /**
   * T  t et that t   d aEnt y was or g nally attac d to.  T  value w ll be set  f t 
   *  d aEnt y  s e  r on a ret et or a t et w h pasted-p c.
   */
  13: opt onal  64 s ce_status_ d (strato.space = "T et", strato.na  = "s ceStatus", personalDataType = 'T et d')


  /**
   * T  user to attr bute v ews of t   d a to.
   *
   * T  f eld should be set w n t   d a's attr butableUser d f eld does not match t  current
   * T et's owner.  Ret ets of a T et w h  d a and "managed  d a" are so  reasons t  may
   * occur.  W n t  value  s None any v ews should be attr buted to t  t et's owner.
   **/
  14: opt onal  64 s ce_user_ d (strato.space = "User", strato.na  = "s ceUser", personalDataType = 'User d')

  /**
   * Add  onal  nformat on spec f c to t   d a type.
   *
   * T  f eld  s opt onal w h  mages (as t   mage  nformat on  s  n t 
   * prev ous f elds), but requ red for an mated G F and nat ve v deo (as,  n
   * t  case, t  prev ous f elds only descr be t  prev ew  mage).
   */
  15: opt onal T et d a. d a nfo  d a_ nfo

  /**
   * DEPRECATED
   * T  dom nant color for t  ent re  mage (or keyfra  for v deo or G F).
   *
   * T  can be used for placeholders wh le t   d a downloads (e  r a
   * sol d color or a grad ent us ng t  gr d).
   */
  16: opt onal ColorValue dom nant_color_overall

  /**
   * DEPRECATED
   * Dom nant color of each quadrant of t   mage (keyfra  for v deo or G F).
   *
   *  f present t  l st should have 4 ele nts, correspond ng to
   * [top_left, top_r ght, bottom_left, bottom_r ght]
   */
  17: opt onal l st<ColorValue> dom nant_color_gr d

  // obsolete 18: opt onal map<str ng, b nary> extens ons

  /**
   * Stratostore extens on po nts data encoded as a Strato record.
   */
  19: opt onal b nary extens ons_reply

  /**
   * Holds  tadata def ned by t  user for t  t et-asset relat onsh p.
   */
  20: opt onal  d a nformat on.UserDef nedProduct tadata  tadata

  /**
   *  d a key used to  nteract w h t   d a systems.
   */
  21: opt onal  d aCommon. d aKey  d a_key

  /**
   * Flex ble structure for add  onal  d a  tadata.  T  f eld  s only
   *  ncluded  n a read-path request  f spec f cally requested.    w ll
   * always be  ncluded, w n appl cable,  n wr e-path responses.
   */
  22: opt onal  d a nformat on.Add  onal tadata add  onal_ tadata

}(pers sted='true', hasPersonalData = 'true')

