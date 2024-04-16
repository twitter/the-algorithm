na space java com.tw ter.t etyp e.thr ftjava
#@na space scala com.tw ter.t etyp e.thr ftscala
#@na space strato com.tw ter.t etyp e
na space py gen.tw ter.t etyp e.t et
na space rb T etyP e
// Spec f c na space to avo d golang c rcular  mport
na space go t etyp e.t et

 nclude "com/tw ter/esc rb rd/t et_annotat on.thr ft"
 nclude "com/tw ter/expandodo/cards.thr ft"
 nclude "com/tw ter/content- alth/tox creplyf lter/f ltered_reply_deta ls.thr ft"
 nclude "com/tw ter/dataproducts/enr ch nts_prof legeo.thr ft"
 nclude "com/tw ter/geoduck/publ c/thr ftv1/geoduck_common.thr ft"
 nclude "com/tw ter/ d aserv ces/commons/ d aCommon.thr ft"
 nclude "com/tw ter/ d aserv ces/commons/ d a nformat on.thr ft"
 nclude "com/tw ter/t etyp e/ap _f elds.thr ft"
 nclude "com/tw ter/t etyp e/ed _control.thr ft"
 nclude "com/tw ter/t etyp e/ d a_ent y.thr ft"
 nclude "com/tw ter/t etyp e/note_t et.thr ft"
 nclude "com/tw ter/serv ce/scarecrow/gen/t ered_act ons.thr ft"
 nclude "com/tw ter/spam/rtf/safety_label.thr ft"
 nclude "com/tw ter/t  l nes/self_thread/self_thread.thr ft"
 nclude "com/tw ter/tseng/w hhold ng/w hhold ng.thr ft"
 nclude "com/tw ter/t et_p vots/t et_p vots.thr ft"
 nclude "com/tw ter/t etyp e/geo/t et_locat on_ nfo.thr ft"
 nclude "com/tw ter/t etyp e/ d a/ d a_ref.thr ft"
 nclude "un f ed_cards_contract.thr ft"
 nclude "com/tw ter/t etyp e/creat ve-ent y-enr ch nts/creat ve_ent y_enr ch nts.thr ft"
 nclude "com/tw ter/t etyp e/un nt ons/un nt ons.thr ft"

/**
 *  Ds are annotated w h t  r correspond ng space for Strato.
 */

/**
 * A Reply  s data about a t et  n response to anot r t et or a
 * user.
 *
 * T  struct w ll be present  f:
 * 1. T  t et  s a reply to anot r t et, or
 * 2. T  t et  s d rected at a user (t  t et's text beg ns w h
 *    an @ nt on).
 */
struct Reply {
  /**
   * T   d of t  t et that t  t et  s reply ng to.
   *
   * T  f eld w ll be m ss ng for d rected-at t ets (t ets whose
   * text beg ns w h an @ nt on) that are not reply ng to anot r
   * t et.
   */
  1: opt onal  64  n_reply_to_status_ d (strato.space = "T et", strato.na  = " nReplyToStatus", personalDataType = 'T et d', t etEd Allo d='false')

  /**
   * T  user to whom t  t et  s d rected.
   *
   *  f  n_reply_to_status_ d  s set, t  f eld  s t  author of that t et.
   *  f  n_reply_to_status_ d  s not set, t  f eld  s t  user  nt oned at
   * t  beg nn ng of t  t et.
   */
  2:  64  n_reply_to_user_ d (strato.space = "User", strato.na  = " nReplyToUser", personalDataType = 'User d')

  /**
   * T  current userna  of  n_reply_to_user_ d.
   *
   * T  f eld  s not set w n G zmoduck returns a fa lure to T etyp e.
   */
  3: opt onal str ng  n_reply_to_screen_na  (personalDataType = 'Userna ')
}(pers sted='true', hasPersonalData = 'true')

/**
 *  ncludes  nformat on about t  user a t et  s d rected at (w n a t et
 * beg ns w h @ nt on).
 *
 * T ets w h a D rectedAtUser are del vered to users who follow both t 
 * author and t  D rectedAtUser. Normally t  D rectedAtUser w ll be t  sa 
 * as Reply. n_reply_to_user_ d, but w ll be d fferent  f t  t et's author
 * rearranges t  @ nt ons  n a reply.
 */
struct D rectedAtUser {
  1:  64 user_ d (strato.space = "User", strato.na  = "user", personalDataType = 'User d')
  2: str ng screen_na  (personalDataType = 'Userna ')
}(pers sted='true', hasPersonalData = 'true')

/**
 * A Share  s data about t  s ce t et of a ret et.
 *
 * Share was t   nternal na  for t  ret et feature.
 */
struct Share {
  /**
   * T   d of t  or g nal t et that was ret eted.
   *
   * T   s always a t et and never a ret et (unl ke parent_status_ d).
   */
  1:  64 s ce_status_ d (strato.space = "T et", strato.na  = "s ceStatus", personalDataType = 'T et d')

  /*
   * T  user  d of t  or g nal t et's author.
   */
  2:  64 s ce_user_ d (strato.space = "User", strato.na  = "s ceUser", personalDataType = 'User d')

  /**
   * T   d of t  t et that t  user ret eted.
   *
   * Often t   s t  sa  as s ce_status_ d, but    s d fferent w n a
   * user ret ets v a anot r ret et. For example, user A posts t et  d 1,
   * user B ret ets  , creat ng t et 2.  f user user C sees B's ret et and
   * ret ets  , t  result  s anot r ret et of t et  d 1, w h t  parent
   * status  d of t et 2.
   */
  3:  64 parent_status_ d (strato.space = "T et", strato.na  = "parentStatus", personalDataType = 'T et d')
}(pers sted='true', hasPersonalData = 'true')

/**
 * A record mapp ng a shortened URL (usually t.co) to a long url, and a prett f ed
 * d splay text. T   s s m lar to data found  n UrlEnt y, and may replace that
 * data  n t  future.
 */
struct ShortenedUrl {
  /**
   * Shortened t.co URL.
   */
  1: str ng short_url (personalDataType = 'ShortUrl')

  /**
   * Or g nal, full-length URL.
   */
  2: str ng long_url (personalDataType = 'LongUrl')

  /**
   * Truncated vers on of expanded URL that does not  nclude protocol and  s
   * l m ed to 27 characters.
   */
  3: str ng d splay_text (personalDataType = 'LongUrl')
}(pers sted='true', hasPersonalData = 'true')

/**
 * A QuotedT et  s data about a t et referenced w h n anot r t et.
 *
 * QuotedT et  s  ncluded  f T et.QuotedT etF eld  s requested, and t 
 * l nked-to t et  s publ c and v s ble at t  t   that t  l nk ng t et
 *  s hydrated, wh ch can be dur ng wr e-t   or later after a cac -m ss
 * read. S nce l nked-to t ets can be deleted, and users can beco 
 * suspended, deact vated, or protected, t  presence of t  value  s not a
 * guarantee that t  quoted t et  s st ll publ c and v s ble.
 *
 * Because a t et quot ng anot r t et may not requ re a permal nk URL  n
 * t  t et's text, t  URLs  n ShortenedUrl may be useful to cl ents that
 * requ re ma nta n ng a legacy-render ng of t  t et's text w h t  permal nk.
 * See ShortenedUrl for deta ls. Cl ents should avo d read ng permal nk w never
 * poss ble and prefer t  QuotedT et's t et_ d and user_ d  nstead.
 *
 *   always populate t  permal nk on t et hydrat on unless t re are part al
 * hydrat on errors or  nner quoted t et  s f ltered due to v s b l y rules.
 *
 */
struct QuotedT et {
  1:  64 t et_ d (strato.space = "T et", strato.na  = "t et", personalDataType = 'T et d')
  2:  64 user_ d (strato.space = "User", strato.na  = "user", personalDataType = 'User d')
  3: opt onal ShortenedUrl permal nk // URLs to access t  quoted-t et
}(pers sted='true', hasPersonalData = 'true')

/**
 * A Contr butor  s a user who has access to anot r user's account.
 */
struct Contr butor {
  1:  64 user_ d (strato.space = "User", strato.na  = "user", personalDataType = 'User d')
  2: opt onal str ng screen_na  (personalDataType = 'Userna ')// not set on G zmoduck fa lure
}(pers sted='true', hasPersonalData = 'true')

struct GeoCoord nates {
  1: double lat ude (personalDataType = 'GpsCoord nates')
  2: double long ude (personalDataType = 'GpsCoord nates')
  3:  32 geo_prec s on = 0 (personalDataType = 'GpsCoord nates')

  /**
   * W t r or not make t  coord nates publ c.
   *
   * T  para ter  s needed because coord nates are not typ cally publ s d
   * by t  author.  f false: A t et has geo coord nates shared but not make
   *   publ c.
   */
  4: bool d splay = 1
}(pers sted='true', hasPersonalData = 'true')

enum PlaceType {
  UNKNOWN = 0
  COUNTRY = 1
  ADM N = 2
  C TY = 3
  NE GHBORHOOD = 4
  PO  = 5
}

enum PlaceNa Type {
  NORMAL = 0
  ABBREV AT ON = 1
  SYNONYM = 2
}

struct PlaceNa  {
  1: str ng na 
  2: str ng language = ""
  3: PlaceNa Type type
  4: bool preferred
}(pers sted='true', hasPersonalData='false')

/**
 * A Place  s t  phys cal and pol  cal propert es of a locat on on Earth.
 */
struct Place {
  /**
   * Geo serv ce  dent f er.
   */
  1: str ng  d (personalDataType = 'Publ s dPrec seLocat onT et, Publ s dCoarseLocat onT et')

  /**
   * Granular y of place.
   */
  2: PlaceType type

  /**
   * T  na  of t  place composed w h  s parent locat ons.
   *
   * For example, t  full na  for "Brooklyn" would be "Brooklyn, NY". T 
   * na   s returned  n t  language spec f ed by
   * GetT etOpt ons.language_tag.
   */
  3: str ng full_na  (personalDataType = ' nferredLocat on')

  /**
   * T  best na  for t  place as determ ned by geoduck  ur st cs.
   *
   * T  na   s returned  n t  language spec f ed by
   * GetT etOpt ons.language_tag.
   *
   * @see com.tw ter.geoduck.ut l.pr m  ves.bestPlaceNa Match ngF lter
   */
  4: str ng na  (personalDataType = 'Publ s dPrec seLocat onT et, Publ s dCoarseLocat onT et')

  /**
   * Arb rary key/value data from t  geoduck PlaceAttr butes for t  place.
   */
  5: map<str ng, str ng> attr butes (personalDataTypeKey = 'PostalCode')

  7: set<PlaceNa > na s

  /**
   * T   SO 3166-1 alpha-2 code for t  country conta n ng t  place.
   */
  9: opt onal str ng country_code (personalDataType = 'Publ s dCoarseLocat onT et')

  /**
   * T  best na  for t  country conta n ng t  place as determ ned by
   * geoduck  ur st cs.
   *
   * T  na   s returned  n t  language spec f ed by
   * GetT etOpt ons.language_tag.
   */
  10: opt onal str ng country_na  (personalDataType = 'Publ s dCoarseLocat onT et')

  /**
   * A s mpl f ed polygon that encompasses t  place's geo try.
   */
  11: opt onal l st<GeoCoord nates> bound ng_box

  /**
   * An unordered l st of geo serv ce  dent f ers for places that conta n t 
   * one from t  most  m d ate parent up to t  country.
   */
  12: opt onal set<str ng> conta ners (personalDataType = 'Publ s dCoarseLocat onT et')

  /**
   * A centro d-l ke coord nate that  s w h n t  geo try of t  place.
   */
  13: opt onal GeoCoord nates centro d

  /**
   * Reason t  place  s be ng suppressed from d splay.
   *
   * T  f eld  s present w n   prev ously had a place for t   D, but are
   * now choos ng not to hydrate   and  nstead prov d ng fake place  tadata
   * along w h a reason for not  nclud ng place  nformat on.
   */
  14: opt onal geoduck_common.W h ldReason w h ldReason
}(pers sted='true', hasPersonalData='true')

/**
 * A UrlEnt y  s t  pos  on and content of a t.co shortened URL  n t 
 * t et's text.
 *
 *  f Talon returns an error to T etyp e dur ng t et hydrat on, t 
 * UrlEnt y w ll be om ted from t  response. UrlEnt  es are not  ncluded
 * for non-t.co-wrapped URLs found  n older t ets, for spam and user safety
 * reasons.
*/
struct UrlEnt y {
  /**
   * T  pos  on of t  ent y's f rst character,  n zero- ndexed Un code
   * code po nts.
   */
  1:  16 from_ ndex

  /**
   * T  pos  on after t  ent y's last character,  n zero- ndexed Un code
   * code po nts.
   */
  2:  16 to_ ndex

  /**
   * Shortened t.co URL.
   */
  3: str ng url (personalDataType = 'ShortUrl')

  /**
   * Or g nal, full-length URL.
   *
   * T  f eld w ll always be present on URL ent  es returned by
   * T etyp e;    s opt onal as an  mple ntat on art fact.
   */
  4: opt onal str ng expanded (personalDataType = 'LongUrl')

  /**
   * Truncated vers on of expanded URL that does not  nclude protocol and  s
   * l m ed to 27 characters.
   *
   * T  f eld w ll always be present on URL ent  es returned by
   * T etyp e;    s opt onal as an  mple ntat on art fact.
   */
  5: opt onal str ng d splay (personalDataType = 'LongUrl')

  6: opt onal  64 cl ck_count (personalDataType = 'CountOfT etEnt  esCl cked')
}(pers sted = 'true', hasPersonalData = 'true')

/**
 * A  nt onEnt y  s t  pos  on and content of a  nt on, (t  "@"
 * character follo d by t  na  of anot r val d user)  n a t et's text.
 *
 *  f G zmoduck returns an error to T etyp e dur ng t et hydrat on that
 *  nt onEnt y w ll be om ted from t  response.
 */
struct  nt onEnt y {
  /**
   * T  pos  on of t  ent y's f rst character ("@"),  n zero- ndexed
   * Un code code po nts.
   */
  1:  16 from_ ndex

  /**
   * T  pos  on after t  ent y's last character,  n zero- ndexed Un code
   * code po nts.
   */
  2:  16 to_ ndex

  /**
   * Contents of t   nt on w hout t  lead ng "@".
   */
  3: str ng screen_na  (personalDataType = 'Userna ')

  /**
   * User  d of t  current user w h t   nt oned screen na .
   *
   *  n t  current  mple ntat on user  d does not necessar ly  dent fy t 
   * user who was or g nally  nt oned w n t  t et was created, only t 
   * user who owns t   nt oned screen na  at t  t   of hydrat on.  f a
   *  nt oned user changes t  r screen na  and a second user takes t  old
   * na , t  f eld  dent f es t  second user.
   *
   * T  f eld w ll always be present on  nt on ent  es returned by
   * T etyp e;    s opt onal as an  mple ntat on art fact.
   */
  4: opt onal  64 user_ d (strato.space = "User", strato.na  = "user", personalDataType = 'User d')

  /**
   * D splay na  of t  current user w h t   nt oned screen na .
   *
   * See user_ d for caveats about wh ch user's na   s used  re. T  f eld
   * w ll always be present on  nt on ent  es returned by T etyp e;    s
   * opt onal as an  mple ntat on art fact.
   */
  5: opt onal str ng na  (personalDataType = 'D splayNa ')

  /**
   *  nd cates  f t  user referred to by t   nt onEnt y has been un nt oned
   * from t  conversat on.   f t  f eld  s set to true, t  from ndex and to ndex
   * f elds w ll have a value of 0.
   *
   * @deprecated  sUn nt oned  s no longer be ng populated
   */
  6: opt onal bool  sUn nt oned (personalDataType = 'ContentPr vacySett ngs')
}(pers sted = 'true', hasPersonalData = 'true')

/**
  * A l st of users that are  nt oned  n t  t et and have a block ng
  * relat onsh p w h t  t et author.  nt ons for t se users w ll be unl nked
  *  n t  t et.
  */
struct Block ngUn nt ons {
  1: opt onal l st< 64> un nt oned_user_ ds (strato.space = 'User', strato.na  = 'users', personalDataType = 'User d')
}(pers sted = 'true', hasPersonalData = 'true', strato.graphql.typena  = 'Block ngUn nt ons')

/**
  * A l st of users that are  nt oned  n t  t et and have  nd cated t y do not want
  * to be  nt oned v a t  r  nt on sett ngs.  nt ons for t se users w ll be unl nked
  *  n t  t et by Tw ter owned and operated cl ents.
  */
struct Sett ngsUn nt ons {
  1: opt onal l st< 64> un nt oned_user_ ds (strato.space = 'User', strato.na  = 'users', personalDataType = 'User d')
}(pers sted = 'true', hasPersonalData = 'true', strato.graphql.typena  = 'Sett ngsUn nt ons')

/**
 * A HashtagEnt y  s t  pos  on and content of a hashtag (a term start ng
 * w h "#")  n a t et's text.
 */
struct HashtagEnt y {
  /**
   * T  pos  on of t  ent y's f rst character ("#"),  n zero- ndexed
   * Un code code po nts.
   */
  1:  16 from_ ndex

  /**
   * T  pos  on after t  ent y's last character,  n zero- ndexed Un code
   * code po nts.
   */
  2:  16 to_ ndex

  /**
   * Contents of t  hashtag w hout t  lead ng "#".
   */
  3: str ng text (personalDataType = 'Pr vateT etEnt  esAnd tadata, Publ cT etEnt  esAnd tadata')
}(pers sted = 'true', hasPersonalData = 'true')

/**
 * A CashtagEnt y  s t  pos  on and content of a cashtag (a term start ng
 * w h "$")  n a t et's text.
 */
struct CashtagEnt y {
  /**
   * T  pos  on of t  ent y's f rst character,  n zero- ndexed Un code
   * code po nts.
   */
  1:  16 from_ ndex

  /**
   * T  pos  on after t  ent y's last character,  n zero- ndexed Un code
   * code po nts.
   */
  2:  16 to_ ndex

  /**
   * Contents of t  cashtag w hout t  lead ng "$"
   */
  3: str ng text (personalDataType = 'Pr vateT etEnt  esAnd tadata, Publ cT etEnt  esAnd tadata')
}(pers sted = 'true', hasPersonalData = 'true')

enum  d aTagType {
  USER = 0
  RESERVED_1 = 1
  RESERVED_2 = 2
  RESERVED_3 = 3
  RESERVED_4 = 4
}

struct  d aTag {
  1:  d aTagType tag_type
  2: opt onal  64 user_ d (strato.space = "User", strato.na  = "user",  personalDataType = 'User d')
  3: opt onal str ng screen_na  (personalDataType = 'Userna ')
  4: opt onal str ng na  (personalDataType = 'D splayNa ')
}(pers sted='true', hasPersonalData = 'true')

struct T et d aTags {
  1: map< d aCommon. d a d, l st< d aTag>> tag_map
}(pers sted='true', hasPersonalData = 'true')

/**
 * A User nt on  s a user reference not stored  n t  t et text.
 *
 * @deprecated Was used only  n ReplyAddresses
 */
struct User nt on {
  1:  64 user_ d (strato.space = "User", strato.na  = "user", personalDataType = 'User d')
  2: opt onal str ng screen_na  (personalDataType = 'Userna ')
  3: opt onal str ng na  (personalDataType = 'D splayNa ')
}(pers sted='true', hasPersonalData = 'true')

/**
 * ReplyAddresses  s a l st of reply ent  es wh ch are stored outs de of t 
 * text.
 *
 * @deprecated
 */
struct ReplyAddresses {
  1: l st<User nt on> users = []
}(pers sted='true', hasPersonalData = 'true')

/**
 * Sc dul ng nfo  s  tadata about t ets created by t  t et sc dul ng
 * serv ce.
 */
//
struct Sc dul ng nfo {
  /**
   *  d of t  correspond ng sc duled t et before   was created as a real
   * t et.
   */
  1:  64 sc duled_t et_ d (personalDataType = 'T et d')
}(pers sted='true', hasPersonalData = 'true')

/**
 * @deprecated
 */
enum SuggestType {
  WTF_CARD    =  0
  WORLD_CUP   =  1
  WTD_CARD    =  2
  NEWS_CARD   =  3
  RESERVED_4  =  4
  RESERVED_5  =  5
  RESERVED_6  =  6
  RESERVED_7  =  7
  RESERVED_8  =  8
  RESERVED_9  =  9
  RESERVED_10 = 10
  RESERVED_11 = 11
}

/**
 * @deprecated
 */
enum Tw terSuggestsV s b l yType {
  /**
   * Always publ c to everyone
   */
  PUBL C = 1

  /**
   *  n r s v s b l y rules of personal zed_for_user_ d.
   */
  RESTR CTED = 2

  /**
   * Only v s ble to personal zed_for_user_ d (and author).
   */
  PR VATE = 3
}

/**
 * Tw terSuggest nfo  s deta ls about a synt t c t et generated by an early
 * vers on of Tw ter Suggests.
 *
 * @deprecated
 */
struct Tw terSuggest nfo {
  1: SuggestType suggest_type
  2: Tw terSuggestsV s b l yType v s b l y_type
  3: opt onal  64 personal zed_for_user_ d (strato.space = "User", strato.na  = "personal zedForUser", personalDataType = 'User d')
  4: opt onal  64 d splay_t  stamp_secs (personalDataType = 'Publ cT  stamp')
}(pers sted='true', hasPersonalData = 'true')

/**
 * A Dev ceS ce conta ns  nformat on about t  cl ent appl cat on from wh ch
 * a t et was sent.
 *
 * T   nformat on  s stored  n Passb rd. T  developer that owns a cl ent
 * appl cat on prov des t   nformat on on https://apps.tw ter.com.
 */
struct Dev ceS ce {

  /**
   * T   d of t  cl ent  n t  now deprecated dev ce_s ces  SQL table.
   *
   * Today t  value w ll always be 0.
   *
   * @deprecated Use cl ent_app_ d
   */
  1: requ red  64  d (personalDataType = 'App d')

  /**
   *  dent f er for t  cl ent  n t  format "oauth:<cl ent_app_ d>"
   */
  2: str ng para ter

  /**
   *  dent f er for t  cl ent  n t  format "oauth:<cl ent_app_ d>"
   */
  3: str ng  nternal_na 

  /**
   * Developer-prov ded na  of t  cl ent appl cat on.
   */
  4: str ng na 

  /**
   * Developer-prov ded publ cly access ble ho  page for t  cl ent
   * appl cat on.
   */
  5: str ng url

  /**
   * HTML frag nt w h a l nk to t  cl ent-prov ded URL
   */
  6: str ng d splay

  /**
   * T  f eld  s marked opt onal for backwards compat b l y but w ll always
   * be populated by T etyp e.
   */
  7: opt onal  64 cl ent_app_ d (personalDataType = 'App d')
}(pers sted='true', hasPersonalData = 'true')

/**
 * A Narrowcast restr cts del very of a t et geograph cally.
 *
 * Narrowcasts allow mult -nat onal advert sers to create geo-relevant content
 * from a central handle that  s only del vered to to follo rs  n a
 * part cular country or set of countr es.
 */
struct Narrowcast {
  2: l st<str ng> locat on = [] (personalDataType = 'Publ s dCoarseLocat onT et')
}(pers sted='true', hasPersonalData = 'true')

/**
 * StatusCounts  s a summary of engage nt  tr cs for a t et.
 *
 * T se  tr cs are loaded from TFlock.
 */
struct StatusCounts {

  /**
   * Number of t  s t  t et has been ret eted.
   *
   * T  number may not match t  l st of users who have ret eted because  
   *  ncludes ret ets from protected and suspended users who are not l sted.
   */
  1: opt onal  64 ret et_count (personalDataType = 'CountOfPr vateRet ets, CountOfPubl cRet ets', strato.json.numbers.type = ' nt53')

  /**
   * Number of d rect repl es to t  t et.
   *
   * T  number does not  nclude repl es to repl es.
   */
  2: opt onal  64 reply_count (personalDataType = 'CountOfPr vateRepl es, CountOfPubl cRepl es', strato.json.numbers.type = ' nt53')

  /**
   * Number of favor es t  t et has rece ved.
   *
   * T  number may not match t  l st of users who have favor ed a t et
   * because    ncludes favor es from protected and suspended users who are
   * not l sted.
   */
  3: opt onal  64 favor e_count (personalDataType = 'CountOfPr vateL kes, CountOfPubl cL kes', strato.json.numbers.type = ' nt53')

  /**
   * @deprecated
   */
  4: opt onal  64 un que_users_ mpressed_count (strato.json.numbers.type = ' nt53')

  /**
   * Number of repl es to t  t et  nclud ng repl es to repl es.
   *
   * @deprecated
   */
  5: opt onal  64 descendent_reply_count (personalDataType = 'CountOfPr vateRepl es, CountOfPubl cRepl es', strato.json.numbers.type = ' nt53')

  /**
   * Number of t  s t  t et has been quote t eted.
   *
   * T  number may not match t  l st of users who have quote t eted because  
   *  ncludes quote t ets from protected and suspended users who are not l sted.
   */
  6: opt onal  64 quote_count (personalDataType = 'CountOfPr vateRet ets, CountOfPubl cRet ets', strato.json.numbers.type = ' nt53')

  /**
   * Number of bookmarks t  t et has rece ved.
   */
  7: opt onal  64 bookmark_count (personalDataType = 'CountOfPr vateL kes', strato.json.numbers.type = ' nt53')

}(pers sted='true', hasPersonalData = 'true', strato.graphql.typena ='StatusCounts')

/**
 * A  s a t et's propert es from one user's po nt of v ew.
 */
struct StatusPerspect ve {
  1:  64 user_ d (strato.space = "User", strato.na  = "user", personalDataType = 'User d')

  /**
   * W t r user_ d has favor ed t  t et.
   */
  2: bool favor ed

  /**
   * W t r user_ d has ret eted t  t et.
   */
  3: bool ret eted

  /**
   *  f user_ d has ret eted t  t et, ret et_ d  dent f es that t et.
   */
  4: opt onal  64 ret et_ d (strato.space = "T et", strato.na  = "ret et", personalDataType = 'T et d')

  /**
   * W t r user_ d has reported t  t et as spam, offens ve, or ot rw se
   * object onable.
   */
  5: bool reported

  /**
   * W t r user_ d has bookmarked t  t et.
   */
  6: opt onal bool bookmarked
}(pers sted='true', hasPersonalData = 'true')

/**
 * A Language  s a guess about t  human language of a t et's text.
 *
 * Language  s determ ned by Tw terLanguage dent f er from t 
 * com.tw ter.common.text package (commonly called "Pengu n").
 */
struct Language {
  /**
   * Language code  n BCP-47 format.
   */
  1: requ red str ng language (personalDataType = ' nferredLanguage')

  /**
   * Language d rect on.
   */
  2: bool r ght_to_left

  /**
   * Conf dence level of t  detected language.
   */
  3: double conf dence = 1.0

  /**
   * Ot r poss ble languages and t  r conf dence levels.
   */
  4: opt onal map<str ng, double> ot r_cand dates
}(pers sted='true', hasPersonalData = 'true')

/**
 * A Supple ntalLanguage  s a guess about t  human language of a t et's
 * text.
 *
 * Supple ntalLanguage  s typ cally determ ned by a th rd-party translat on
 * serv ce.    s only stored w n t  serv ce detects a d fferent language
 * than Tw terLanguage dent f er.
 *
 * @deprecated 2020-07-08 no longer populated.
 */
struct Supple ntalLanguage {
  /**
   * Language code  n BCP-47 format.
   */
  1: requ red str ng language (personalDataType = ' nferredLanguage')
}(pers sted='true', hasPersonalData = 'true')

/**
 * A SpamLabel  s a collect on of spam act ons for a t et.
 *
 * Absence of a SpamLabel  nd cates that no act on needs to be taken
 */
struct SpamLabel {
  /**
   * F lter t  content at render-t  
   *
   * @deprecated 2014-05-19 Use f lter_renders
   */
  1: bool spam = 0

  2: opt onal set<t ered_act ons.T eredAct onResult> act ons;
}(pers sted='true')


/**
 * T  ava lable types of spam s gnal
 *
 * @deprecated
 */
enum SpamS gnalType {
  MENT ON           = 1
  SEARCH            = 2
  STREAM NG         = 4
  # OBSOLETE HOME_T MEL NE = 3
  # OBSOLETE NOT F CAT ON  = 5
  # OBSOLETE CONVERSAT ON  = 6
  # OBSOLETE CREAT ON      = 7
  RESERVED_VALUE_8  = 8
  RESERVED_VALUE_9  = 9
  RESERVED_VALUE_10 = 10
}

/**
 * @deprecated
 * CardB nd ngValues  s a collect on of key-value pa rs used to render a card.
 */
struct CardB nd ngValues {
  1: l st<cards.Card2 m d ateValue> pa rs = []
}(pers sted='true')

/**
 * A CardReference  s a  chan sm for expl c ly assoc at ng a card w h a
 * t et.
 */
struct CardReference {
  /**
   * L nk to t  card to assoc ate w h a t et.
   *
   * T  UR  may reference e  r a card stored  n t  card serv ce, or
   * anot r res ce, such as a crawled  b page URL. T  value supercedes
   * any URL present  n t et text.
   */
  1: str ng card_ur 
}(pers sted='true')

/**
 * A T etP vot  s a semant c ent y related to a t et.
 *
 * T etP vots are used to d rect to t  user to anot r related locat on. For
 * example, a "See more about <na >" U  ele nt that takes t  user to <url>
 * w n cl cked.
 */
struct T etP vot {
  1: requ red t et_annotat on.T etEnt yAnnotat on annotat on
  2: requ red t et_p vots.T etP votData data
}(pers sted='true')

struct T etP vots {
  1: requ red l st<T etP vot> t et_p vots
}(pers sted='true')

struct Esc rb rdEnt yAnnotat ons {
  1: l st<t et_annotat on.T etEnt yAnnotat on> ent y_annotat ons
}(pers sted='true')

struct TextRange {
  /**
   * T   nclus ve  ndex of t  start of t  range,  n zero- ndexed Un code
   * code po nts.
   */
  1: requ red  32 from_ ndex

  /**
   * T  exclus ve  ndex of t  end of t  range,  n zero- ndexed Un code
   * code po nts.
   */
  2: requ red  32 to_ ndex
}(pers sted='true')

struct T etCoreData {
  1:  64 user_ d (strato.space = "User", strato.na  = "user", personalDataType = 'User d', t etEd Allo d='false')

  /**
   * T  body of t  t et cons st ng of t  user-suppl ed d splayable  ssage
   * and:
   * - an opt onal pref x l st of @ nt ons
   * - an opt onal suff x attach nt url.
   *
   * T   nd ces from v s ble_text_range spec fy t  substr ng of text  ndended
   * to be d splayed, whose length  s l m ed to 140 d splay characters.  Note
   * that t  v s ble substr ng may be longer than 140 characters due to HTML
   * ent y encod ng of &, <, and > .

   * For ret ets t  text  s that of t  or g nal t et, prepended w h "RT
   * @userna : " and truncated to 140 characters.
   */
  2: str ng text (personalDataType = 'Pr vateT ets, Publ cT ets')

  /**
   * T  cl ent from wh ch t  t et was created
   *
   * T  format of t  value  s oauth:<cl ent  d>.
   */
  3: str ng created_v a (personalDataType = 'Cl entType')

  /**
   * T   t  t et was created.
   *
   * T  value  s seconds s nce t  Un x epoch. For t ets w h Snowflake  Ds
   * t  value  s redundant, s nce a m ll second-prec s on t  stamp  s part
   * of t   d.
   */
  4:  64 created_at_secs

  /**
   * Present w n t  t et  s a reply to anot r t et or anot r user.
   */
  5: opt onal Reply reply

  /**
   * Present w n a t et beg ns w h an @ nt on or has  tadata  nd cat ng t  d rected-at user.
   */
  6: opt onal D rectedAtUser d rected_at_user

  /**
   * Present w n t  t et  s a ret et.
   */
  7: opt onal Share share

  /**
   * W t r t re  s a takedown country code or takedown reason set for t  spec f c t et.
   *
   * See takedown_country_codes for t  countr es w re t  takedown  s act ve.  (deprecated)
   * See takedown_reasons for a l st of reasons why t  t et  s taken down.
   *
   * has_takedown w ll be set to true  f e  r t  spec f c t et or t  author has a
   * takedown act ve.
   */
  8: bool has_takedown = 0

  /**
   * W t r t  t et m ght be not-safe-for-work, judged by t  t et author.
   *
   * Users can flag t  r own accounts as not-safe-for-work  n account
   * preferences by select ng "Mark  d a   t et as conta n ng mater al that
   * may be sens  ve" and each t et created after that po nt w ll have
   * t  flag set.
   *
   * T  value can also be updated after t et create t   v a t 
   * update_poss bly_sens  ve_t et  thod.
   */
  9: bool nsfw_user = 0

  /**
   * W t r t  t et m ght be not-safe-for-work, judged by an  nternal Tw ter
   * support agent.
   *
   * T  t et value or g nates from t  user's nsfw_adm n flag at
   * t et create t   but can be updated afterwards us ng t 
   * update_poss bly_sens  ve_t et  thod.
   */
  10: bool nsfw_adm n = 0

  /**
   * W n nullcast  s true a t et  s not del vered to a user's follo rs, not
   * shown  n t  user's t  l ne, and does not appear  n search results.
   *
   * T   s pr mar ly used to create t ets that can be used as ads w hout
   * broadcast ng t m to an advert ser's follo rs.
   */
  11: bool nullcast = 0 (t etEd Allo d='false')

  /**
   * Narrowcast l m s del very of a t et to follo rs  n spec f c geograph c
   * reg ons.
   */
  12: opt onal Narrowcast narrowcast (t etEd Allo d='false')

  /**
   * T   mpress on  d of t  ad from wh ch t  t et was created.
   *
   * T   s set w n a user ret ets or repl es to a promoted t et.    s
   * used to attr bute t  "earned" exposure of an advert se nt.
   */
  13: opt onal  64 track ng_ d (personalDataType = ' mpress on d', t etEd Allo d='false')

  /**
   * A shared  dent f er among all t  t ets  n t  reply cha n for a s ngle
   * t et.
   *
   * T  conversat on  d  s t   d of t  t et that started t  conversat on.
   */
  14: opt onal  64 conversat on_ d (strato.space = "T et", strato.na  = "conversat on", personalDataType = 'T et d')

  /**
   * W t r t  t et has  d a of any type.
   *
   *  d a can be  n t  form of  d a ent  es,  d a cards, or URLs  n t 
   * t et text that l nk to  d a partners.
   *
   * @see  d a ndex lper
   */
  15: opt onal bool has_ d a

  /**
   * Supported for legacy cl ents to assoc ate a locat on w h a T et.
   *
   * Tw ter owned cl ents must use place_ d REST AP  param for geo-tagg ng.
   *
   * @deprecated Use place_ d REST AP  param
   */
  16: opt onal GeoCoord nates coord nates (personalDataType = 'GpsCoord nates', t etEd Allo d='false')

  /**
   * T  locat on w re a t et was sent from.
   *
   * Place  s e  r publ s d  n AP  request expl c ly or  mpl c ly reverse
   * geocoded from AP  lat/lon coord nates params.
   *
   * T etyp e  mple ntat on notes:
   *  - Currently,  f both place_ d and coord nates are spec f ed, coord nates
   *    takes precedence  n geo-tagg ng.  .e.: Place returned rgc(coord nates)
   *    sets t  place_ d f eld.
   *  - place_ d  s reverse geocoded on wr e-path.
   */
  17: opt onal str ng place_ d (personalDataType = 'Publ s dPrec seLocat onT et, Publ s dCoarseLocat onT et')
}(pers sted='true', hasPersonalData = 'true', t etEd Allo d='false')

/**
 * L st of commun y  D's t  t et belongs to.
 */
struct Commun  es {
  1: requ red l st< 64> commun y_ ds (personalDataType = 'Engage nt d')
}(pers sted='true')

/**
 * T et  tadata that  s present on extended t ets, a t et whose total text length  s greater
 * than t  class c l m  of 140 characters.
 */
struct ExtendedT et tadata {
  /**
   * @deprecated was d splay_count
   */
  1:  32 unused1 = 0

  /**
   * T   ndex,  n un code code po nts, at wh ch t  t et text should be truncated
   * for render ng  n a publ c AP  backwards-compat ble mode.  Once truncated to t 
   * po nt, t  text should be appended w h an ell ps s, a space, and t  short_url
   * from self_permal nk.  T  result ng text must conform to t  140 d splay glyph
   * l m .
   */
  2: requ red  32 ap _compat ble_truncat on_ ndex

  /**
   * @deprecated was default_d splay_truncat on_ ndex
   */
  3:  32 unused3 = 0

  /**
   * @deprecated was  s_long_form
   */
  4: bool unused4 = 0

  /**
   * @deprecated was prev ew_range
   */
  5: opt onal TextRange unused5

  /**
   * @deprecated was extended_prev ew_range
   */
  6: opt onal TextRange unused6
}(pers sted='true')

/**
 * @deprecated use Trans entCreateContext  nstead
 */
enum T etCreateContextKey {
  PER SCOPE_ S_L VE    = 0,
  PER SCOPE_CREATOR_ D = 1
}

/**
 * D rectedAtUser tadata  s a t etyp e- nternal structure that can be used to store  tadata about
 * a d rected-at user on t  t et.
 *
 * Note: absence of t  f eld does not  mply t  t et does not have a D rectedAtUser, see
 * t et.d rectedAtUser tadata for more  nformat on.
 */
struct D rectedAtUser tadata {
  /**
   *  D of t  user a t et  s d rected-at.
   */
  1: opt onal  64 user_ d (personalDataType = 'User d')
}(pers sted='true', hasPersonalData = 'true')

/**
 * T et  tadata that may be present on t ets  n a self-thread (t etstorm).
 *
 * A self-thread  s a tree of self-repl es that may e  r:
 * 1. beg n as a reply to anot r user's t et (called a non-root self-thread) or
 * 2. stand alone (called root self-thread).
 *
 * Note that not all self-threads have SelfThread tadata.
 */
struct SelfThread tadata {
  /**
   * A shared  dent f er among all t  t ets  n t  self-thread (t etstorm).
   *
   * T  t etstorm  d  s t   d of t  t et that started t  self thread.
   *
   *  f t   d matc s t  t et's conversat on_ d t n    s a root self-thread, ot rw se    s
   * a non-root self-thread.
   */
  1: requ red  64  d (personalDataType = 'T et d')

  /**
   *  nd cates  f t  t et w h t  SelfThread tadata  s a leaf  n t  self-thread tree.
   * T  flag m ght be used to enc age t  author to extend t  r t etstorm at t  end.
   */
  2: bool  sLeaf = 0
}(pers sted='true', hasPersonalData = 'true')

/**
 * Composer flow used to create t  t et. Unless us ng t  News Ca ra (go/newsca ra)
 * flow, t  should be `STANDARD`.
 *
 * W n set to `CAMERA`, cl ents are expected to d splay t  t et w h a d fferent U 
 * to emphas ze attac d  d a.
 */
enum ComposerS ce {
  STANDARD = 1
  CAMERA = 2
}


/**
 * T  conversat on owner and users  n  nv ed_user_ ds can reply
 **/
struct Conversat onControlBy nv at on {
  1: requ red l st< 64>  nv ed_user_ ds (personalDataType = 'User d')
  2: requ red  64 conversat on_t et_author_ d (personalDataType = 'User d')
  3: opt onal bool  nv e_v a_ nt on
}(pers sted='true', hasPersonalData = 'true')

/**
 * T  conversat on owner, users  n  nv ed_user_ ds, and users who t  conversat on owner follows can reply
 **/
struct Conversat onControlCommun y {
  1: requ red l st< 64>  nv ed_user_ ds (personalDataType = 'User d')
  2: requ red  64 conversat on_t et_author_ d (personalDataType = 'User d')
  3: opt onal bool  nv e_v a_ nt on
}(pers sted='true', hasPersonalData = 'true')

/**
 * T  conversat on owner, users  n  nv ed_user_ ds, and users who follows t  conversat on owner can reply
 **/
struct Conversat onControlFollo rs {
 1: requ red l st< 64>  nv ed_user_ ds (personalDataType = 'User d')
 2: requ red  64 conversat on_t et_author_ d (personalDataType = 'User d')
 3: opt onal bool  nv e_v a_ nt on
}(pers sted='true', hasPersonalData = 'true')

/**
* T  t et  tadata captures restr ct ons on who  s allo d to reply  n a conversat on.
*/
un on Conversat onControl {

  1: Conversat onControlCommun y commun y

  2: Conversat onControlBy nv at on by nv at on

  3: Conversat onControlFollo rs follo rs
}(pers sted='true', hasPersonalData = 'true')

// T  t et  tadata shows t  exclus v y of a t et and  s used to determ ne
// w t r repl es / v s b l y of a t et  s l m ed
struct Exclus veT etControl {
  1: requ red  64 conversat on_author_ d (personalDataType = 'User d')
}(pers sted='true', hasPersonalData = 'true')

/**
 * T et  tadata for a Trusted Fr ends t et.
 *
 * A Trusted Fr ends t et  s a t et whose v s b l y  s restr cted to  mbers
 * of an author-spec f ed l st.
 *
 * Repl es to a Trusted Fr ends t et w ll  n r  a copy of t   tadata from
 * t  root t et.
 */
struct TrustedFr endsControl {
  /**
   * T   D of t  Trusted Fr ends L st whose  mbers can v ew t  t et.
   */
  1: requ red  64 trusted_fr ends_l st_ d (personalDataType = 'TrustedFr endsL st tadata')
}(pers sted='true', hasPersonalData = 'true')

enum Collab nv at onStatus {
  PEND NG = 0
  ACCEPTED = 1
  REJECTED = 2
}

/**
 * Represents a user who has been  nv ed to collaborate on a CollabT et, assoc ated w h w t r
 * t y have accepted or rejected collaborat on
 */
struct  nv edCollaborator {
  1: requ red  64 collaborator_user_ d (personalDataType = 'User d')
  2: requ red Collab nv at onStatus collab_ nv at on_status
}(pers sted='true', hasPersonalData='true')

/**
 * Present  f T et  s a Collab nv at on awa  ng publ sh ng, stores l st of  nv ed Collaborators
 */
struct Collab nv at on {
  1: requ red l st< nv edCollaborator>  nv ed_collaborators
}(pers sted='true', hasPersonalData='true')

/**
 * Present  f T et  s a publ s d CollabT et, stores l st of Collaborators
 */
struct CollabT et {
  1: requ red l st< 64> collaborator_user_ ds (personalDataType = 'User d')
}(pers sted='true', hasPersonalData='true')

/**
 * CollabT ets treat mult ple users as co-authors or "Collaborators" of a s ngle "Collab T et".
 *
 * W n creat ng a Collab T et, t  or g nal author w ll beg n by creat ng a Collab nv at on wh ch
 *  s sent to anot r Collaborator to accept or reject collaborat on.  f and w n ot r
 * Collaborators have accepted, t  Collab nv at on  s replaced by a CollabT et wh ch  s publ s d
 * publ cly and fanned out to follo rs of all Collaborators. A Collab nv at on w ll be h dden from
 * anyone except t  l st of Collaborators us ng VF. T  CollabT et w ll t n be fanned out l ke
 * a regular T et to t  prof les and comb ned aud ences of all Collaborators.
 *
 * A T et represent ng a CollabT et or Collab nv at on  s denoted by t  presence of a
 * CollabControl f eld on a T et.
 */
un on CollabControl {
  1: Collab nv at on collab_ nv at on
  2: CollabT et collab_t et
}(pers sted='true', hasPersonalData='true')

/**
 * A T et  s a  ssage that belongs to a Tw ter user.
 *
 * T  T et struct replaces t  deprecated Status struct. All f elds except
 *  d are opt onal.
 *
 * T  struct supports t  add  onal f elds flex ble sc ma. Add  onal f elds are
 * def ned start ng from f eld 101.
 *
 * T  gu del nes for add ng a new Add  onal f eld:
 * 1.  's requ red to def ne t  add  onal f eld as an opt onal struct.
 *     ns de t  struct, def ne opt onal or non-opt onal f eld(s) accord ng
 *    to y  needs.
 * 2.  f   have several  mmutable p ece of data that are always accessed
 *    toget r,   should def ne t m  n t  sa  struct for better storage
 *    local y.
 * 3.  f y  data model has several mutable p eces, and d fferent p ece can
 *    be updated  n a close success on,   should group t m  nto
 *    separate structs and each struct conta ns one mutable p ece.
 */
struct T et {
  /**
  * T  pr mary key for a t et.
  *
  * A t et's  d  s ass gned by t  t et serv ce at creat on t  . S nce
  * 2010-11-04 t et  ds have been generated us ng Snowflake. Pr or to t 
  *  ds  re ass gned sequent ally by  SQL AUTO NCREMENT.
  */
  1:  64  d (personalDataType = 'T et d')

  /**
   * T  essent al propert es of a t et.
   *
   * T  f eld w ll always be present on t ets returned by T etyp e.    s
   * marked opt onal so an empty t et can be prov ded to wr e add  onal
   * f elds.
   */
  2: opt onal T etCoreData core_data

  /**
   * URLs extracted from t  t et's text.
   */
  3: opt onal l st<UrlEnt y> urls

  /**
   *  nt ons extracted from t  t et's text.
   */
  4: opt onal l st< nt onEnt y>  nt ons

  /**
   * Hashtags extracted from t  t et's text.
   */
  5: opt onal l st<HashtagEnt y> hashtags

  /**
   * Cashtags extracted from t  t et's text
   */
  6: opt onal l st<CashtagEnt y> cashtags

  7: opt onal l st< d a_ent y. d aEnt y>  d a

  /**
   * Place  dent f ed by T et.core_data.place_ d.
   */
  10: opt onal Place place

  11: opt onal QuotedT et quoted_t et

  /**
   * T  l st of countr es w re t  t et w ll not be shown.
   *
   * T  f eld conta ns countr es for both t  t et and t  user, so   may
   * conta n values even  f has_takedown  s false.
   *
   * @deprecated, use f eld 30 takedown_reasons wh ch  ncludes t  sa   nformat on and more
   */
  12: opt onal l st<str ng> takedown_country_codes (personalDataType = 'ContentRestr ct onStatus')

  /**
   *  nteract on  tr cs for t  t et.
   *
   *  ncluded w n one of GetT etOpt ons.load_ret et_count,
   * GetT etOpt ons.load_reply_count, or GetT etOpt ons.load_favor e_count
   *  s set. T  can be m ss ng  n a PART AL response  f t  TFlock request
   * fa ls.
   */
  13: opt onal StatusCounts counts

  /**
   * Propert es of t  cl ent from wh ch t  t et was sent.
   *
   * T  can be m ss ng  n a PART AL response  f t  Passb rd request fa ls.
   */
  14: opt onal Dev ceS ce dev ce_s ce

  /**
   * Propert es of t  t et from t  po nt of v ew of
   * GetT etOpt ons.for_user_ d.
   *
   * T  f eld  s  ncluded only w n for_user_ d  s prov ded and
   *  nclude_perspect ve == true T  can be m ss ng  n a PART AL response  f
   * t  t  l ne serv ce request fa ls.
   */
  15: opt onal StatusPerspect ve perspect ve

  /**
   * Vers on 1 cards.
   *
   * T  f eld  s  ncluded only w n GetT etOpt ons. nclude_cards == true.
   */
  16: opt onal l st<cards.Card> cards

  /**
   * Vers on 2 cards.
   *
   * T  f eld  s  ncluded only  ncluded w n GetT etOpt ons. nclude_cards
   * == true and GetT etOpt ons.cards_platform_key  s set to val d value.
   */
  17: opt onal cards.Card2 card2

  /**
   * Human language of t et text as determ ned by Tw terLanguage dent f er.
   */
  18: opt onal Language language

  /**
   * @deprecated
   */
  19: opt onal map<SpamS gnalType, SpamLabel> spam_labels

  /**
   * User respons ble for creat ng t  t et w n    s not t  sa  as t 
   * core_data.user_ d.
   *
   * T   s sens  ve  nformat on and must not be shared externally (v a U ,
   * AP , or stream ng) except to t  t  owner of t  t et
   * (core_data.user_ d) or a contr butor to t  owner's account.
   */
  20: opt onal Contr butor contr butor

  // obsolete 21: opt onal l st<Top cLabel> top c_labels

  22: opt onal enr ch nts_prof legeo.Prof leGeoEnr ch nt prof le_geo_enr ch nt

  // Maps extens on na  to value; only populated  f t  request conta ned an extens on on t ets.
  // obsolete 24: opt onal map<str ng, b nary> extens ons

  /**
   * Deprecated.
   * Semant c ent  es that are related to t  t et.
   */
  25: opt onal T etP vots t et_p vots

  /**
   * @deprecated
   * Strato T et Extens ons support has moved to b rd rd.
   *
   *  nternal thr ft cl ents should query strato columns d rectly and
   * not rely upon ext/*.T et columns wh ch are des gned to serve
   * cl ent AP s.
   */
  26: opt onal b nary extens ons_reply

  /**
   * Has t  request ng user muted t  conversat on referred to by
   * `conversat on_ d`? W n t  f eld  s absent, t  conversat on may
   * or may not be muted. Use t  ` nclude_conversat on_muted` f eld  n
   * GetT etOpt ons to request t  f eld.
   *
   *  f t  f eld has a value, t  value appl es to t  user  n t 
   * `for_user_ d` f eld of t  request ng `GetT etOpt ons`.
   */
  27: opt onal bool conversat on_muted

  /**
   * T  user  d of t  t et referenced by conversat on_ d
   *
   * @deprecated Was conversat on_owner_ d. T  was never  mple nted.
   */
  28: opt onal  64 unused28

  /**
   * Has t  t et been removed from  s conversat on by t  conversat on owner?
   *
   * @deprecated Was  s_removed_from_conversat on. T  was never  mple nted.
   */
  29: opt onal bool unused29

  /**
   * A l st of takedown reasons  nd cat ng wh ch country and reason t  t et was taken down.
   */
  30: opt onal l st<w hhold ng.TakedownReason> takedown_reasons

  /**
   * @obsolete, self-thread  tadata  s now stored  n f eld 151, self_thread_ tadata
   */
  31: opt onal self_thread.SelfThread nfo self_thread_ nfo

  // f eld 32 to 99 are reserved
  // f eld 100  s used for flex ble sc ma proof of concept
  // add  onal f elds
  // t se f elds are stored  n Manhattan flex ble sc ma
  101: opt onal T et d aTags  d a_tags
  102: opt onal Sc dul ng nfo sc dul ng_ nfo

  /**
   * @deprecated
   */
  103: opt onal CardB nd ngValues b nd ng_values

  /**
   * @deprecated
   */
  104: opt onal ReplyAddresses reply_addresses

  /**
   * OBSOLETE, but or g nally conta ned  nformat on about synt t c t ets created by t  f rst
   * vers on of Tw ter Suggests.
   *
   * @deprecated
   */
  105: opt onal Tw terSuggest nfo obsolete_tw ter_suggest_ nfo

  106: opt onal Esc rb rdEnt yAnnotat ons esc rb rd_ent y_annotat ons (personalDataType = 'Annotat onValue')

  // @deprecated 2021-07-19
  107: opt onal safety_label.SafetyLabel spam_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  108: opt onal safety_label.SafetyLabel abus ve_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  109: opt onal safety_label.SafetyLabel low_qual y_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  110: opt onal safety_label.SafetyLabel nsfw_h gh_prec s on_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  111: opt onal safety_label.SafetyLabel nsfw_h gh_recall_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  112: opt onal safety_label.SafetyLabel abus ve_h gh_recall_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  113: opt onal safety_label.SafetyLabel low_qual y_h gh_recall_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  114: opt onal safety_label.SafetyLabel persona_non_grata_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  115: opt onal safety_label.SafetyLabel recom ndat ons_low_qual y_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  116: opt onal safety_label.SafetyLabel exper  ntat on_label (personalDataType = 'T etSafetyLabels')

  117: opt onal t et_locat on_ nfo.T etLocat on nfo t et_locat on_ nfo
  118: opt onal CardReference card_reference

  /**
   * @deprecated 2020-07-08 no longer populated.
   */
  119: opt onal Supple ntalLanguage supple ntal_language

  // f eld 120, add  onal_ d a_ tadata,  s deprecated.
  // f eld 121,  d a_ tadatas,  s deprecated

  // under certa n c rcumstances,  nclud ng long form t ets,   create and store a self-permal nk
  // to t  t et.  n t  case of a long-form t et, t  w ll be used  n a truncated vers on
  // of t  t et text.
  122: opt onal ShortenedUrl self_permal nk

  //  tadata that  s present on extended t ets.
  123: opt onal ExtendedT et tadata extended_t et_ tadata

  // obsolete 124: crosspost_dest nat ons.CrosspostDest nat ons crosspost_dest nat ons

  // Commun  es assoc ated w h a t et
  125: opt onal Commun  es commun  es (personalDataType = 'Pr vateT etEnt  esAnd tadata', t etEd Allo d='false')

  //  f so  text at t  beg nn ng or end of t  t et should be h dden, t n t 
  // f eld  nd cates t  range of text that should be shown  n cl ents.
  126: opt onal TextRange v s ble_text_range

  // @deprecated 2021-07-19
  127: opt onal safety_label.SafetyLabel spam_h gh_recall_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  128: opt onal safety_label.SafetyLabel dupl cate_content_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  129: opt onal safety_label.SafetyLabel l ve_low_qual y_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  130: opt onal safety_label.SafetyLabel nsfa_h gh_recall_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  131: opt onal safety_label.SafetyLabel pdna_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  132: opt onal safety_label.SafetyLabel search_blackl st_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  133: opt onal safety_label.SafetyLabel low_qual y_ nt on_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  134: opt onal safety_label.SafetyLabel bystander_abus ve_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  135: opt onal safety_label.SafetyLabel automat on_h gh_recall_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  136: opt onal safety_label.SafetyLabel gore_and_v olence_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  137: opt onal safety_label.SafetyLabel untrusted_url_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  138: opt onal safety_label.SafetyLabel gore_and_v olence_h gh_recall_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  139: opt onal safety_label.SafetyLabel nsfw_v deo_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  140: opt onal safety_label.SafetyLabel nsfw_near_perfect_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  141: opt onal safety_label.SafetyLabel automat on_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  142: opt onal safety_label.SafetyLabel nsfw_card_ mage_label (personalDataType = 'T etSafetyLabels')
  // @deprecated 2021-07-19
  143: opt onal safety_label.SafetyLabel dupl cate_ nt on_label (personalDataType = 'T etSafetyLabels')

  // @deprecated 2021-07-19
  144: opt onal safety_label.SafetyLabel bounce_label (personalDataType = 'T etSafetyLabels')
  // f eld 145 to 150  s reserved for safety labels

  /**
   *  f t  t et  s part of a self_thread (t etstorm) t n t  value may be set.
   * See SelfThread tadata for deta ls.
   */
  151: opt onal SelfThread tadata self_thread_ tadata
  // f eld 152 has been deprecated

  // T  composer used to create t  t et. E  r v a t  standard t et creator or t 
  // Ca ra flow (go/newsca ra).
  //
  // NOTE: t  f eld  s only set  f a cl ent passed an expl c  ComposerS ce  n t  PostT etRequest.
  // News Ca ra  s deprecated and   no longer set ComposerS ce  n t  PostT etRequest so no new T ets w ll
  // have t  f eld.
  153: opt onal ComposerS ce composer_s ce

  // Present  f repl es are restr cted, see Conversat onControl for more deta ls
  154: opt onal Conversat onControl conversat on_control

  // Determ nes t  super follows requ re nts for be ng able to v ew a t et.
  155: opt onal Exclus veT etControl exclus ve_t et_control (t etEd Allo d='false')

  // Present for a Trusted Fr ends t et, see TrustedFr endsControl for more deta ls.
  156: opt onal TrustedFr endsControl trusted_fr ends_control (t etEd Allo d='false')

  // Data about ed s and ed ab l y. See Ed Control for more deta ls.
  157: opt onal ed _control.Ed Control ed _control

  // Present for a CollabT et or Collab nv at on, see CollabControl for more deta ls.
  158: opt onal CollabControl collab_control (t etEd Allo d='false')

  // Present for a 3rd-party developer-bu lt card. See http://go/developer-bu lt-cards-prd
  159: opt onal  64 developer_bu lt_card_ d (personalDataType = 'Card d')

  // Data about enr ch nts attac d to a t et.
  160: opt onal creat ve_ent y_enr ch nts.Creat veEnt yEnr ch nts creat ve_ent y_enr ch nts_for_t et

  // T  f eld  ncludes sum d engage nts from t  prev ous t ets  n t  ed  cha n.
  161: opt onal StatusCounts prev ous_counts

  // A l st of  d a references,  nclud ng  nformat on about t  s ce T et for pasted  d a.
  // Prefer t  f eld to  d a_keys, as  d a_keys  s not present for old T ets or pasted  d a T ets.
  162: opt onal l st< d a_ref. d aRef>  d a_refs

  // W t r t  t et  s a 'backend t et' to be referenced only by t  creat ves conta ners serv ce
  // go/cea-cc- ntegrat on for more deta ls
  163: opt onal bool  s_creat ves_conta ner_backend_t et

  /**
  * Aggregated perspect ve of t  t et and all ot r vers ons from t  po nt of v ew of t 
  * user spec f ed  n for_user_ d.
  *
  * T  f eld  s  ncluded only w n for_user_ d  s prov ded and can be m ss ng  n a PART AL response
  *  f t  t  l ne serv ce request fa ls.
  */
  164: opt onal ap _f elds.T etPerspect ve ed _perspect ve

  // V s b l y controls related to Tox c Reply F lter ng
  // go/toxrf for more deta ls
  165: opt onal f ltered_reply_deta ls.F lteredReplyDeta ls f ltered_reply_deta ls

  // T  l st of  nt ons that have un nt oned from t  t et's assoc ated conversat on
  166: opt onal un nt ons.Un nt onData un nt on_data

  /**
    * A l st of users that  re  nt oned  n t  t et and have a block ng
    * relat onsh p w h t  author.
    */
  167: opt onal Block ngUn nt ons block ng_un nt ons

  /**
      * A l st of users that  re  nt oned  n t  t et and should be un nt oned
      * based on t  r  nt on settt ngs
      */
  168: opt onal Sett ngsUn nt ons sett ngs_un nt ons

  /**
    * A Note assoc ated w h t  T et.
    */
  169: opt onal note_t et.NoteT et note_t et

  // For add  onal f elds, t  next ava lable f eld  d  s 169.
  // NOTE: w n add ng a new add  onal f eld, please also update UnrequestedF eldScrubber.scrubKnownF elds

  /**
   *  NTERNAL F ELDS
   *
   * T se f elds are used by t etyp e only and should not be accessed externally.
   * T  f eld  ds are  n descend ng order, start ng w h `32767`.
   */

  /**
   * Present  f t et data  s prov ded creat ves conta ner serv ce  nstead of t etyp e storage,
   * w h encapsulated t ets or custom zed data.
   */
  32763: opt onal  64 underly ng_creat ves_conta ner_ d

  /**
   * Stores t etyp e- nternal  tadata about a D rectedAtUser.
   *
   * A t et's D rectedAtUser  s hydrated as follows:
   * 1.  f t  f eld  s present, t n D rectedAtUser tadata.user d  s t  d rected-at user
   * 2.  f t  f eld  s absent, t n  f t  t et has a reply and has a  nt on start ng at text
   *     ndex 0 t n that user  s t  d rected-at user.
   *
   * Note: External cl ents should use CoreData.d rected_at_user.
   */
  32764: opt onal D rectedAtUser tadata d rected_at_user_ tadata

  // l st of takedowns that are appl ed d rectly to t  t et
  32765: opt onal l st<w hhold ng.TakedownReason> t etyp e_only_takedown_reasons

  // Stores t   d a keys used to  nteract w h t   d a platform systems.
  // Prefer ` d a_refs` wh ch w ll always have  d a data, unl ke t  f eld wh ch  s empty for
  // older T ets and T ets w h pasted  d a.
  32766: opt onal l st< d aCommon. d aKey>  d a_keys

  // f eld 32767  s t  l st of takedowns that are appl ed d rectly to t  t et
  32767: opt onal l st<str ng> t etyp e_only_takedown_country_codes (personalDataType = 'ContentRestr ct onStatus')


  // for  nternal f elds, t  next ava lable f eld  d  s 32765 (count ng down)
}(pers sted='true', hasPersonalData = 'true')
