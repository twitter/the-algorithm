na space java com.tw ter.t etyp e.thr ftjava.graphql
#@na space scala com.tw ter.t etyp e.thr ftscala.graphql
#@na space strato com.tw ter.t etyp e.graphql

/**
 * Reasons for def n ng "prefetch" structs:
 *  )    enables GraphQL prefetch cach ng
 *   ) All t et mutat on operat ons are def ned to support prefetch cach ng for AP  cons stency
 *     and future flex b l y. (Populat ng t  cac  w h VF results be ng a potent al use case.)
 */
 nclude "com/tw ter/ads/callback/engage nt_request.thr ft"
 nclude "com/tw ter/strato/graphql/ex stsAndPrefetch.thr ft"

struct Unret etRequest {
  /**
   * T et  D of t  s ce t et be ng referenced  n t  unret et.
   * Note: T  ret et_ d  sn't be ng passed  re as   w ll result  n a
   * successful response, but won't have any effect. T   s due to
   * how T etyp e's unret et endpo nt works.
   */
  1: requ red  64 s ce_t et_ d (
      strato.json.numbers.type='str ng',
      strato.descr pt on='T  s ce t et to be unret eted.'
    )
  2: opt onal str ng compar son_ d (
     strato.descr pt on='Correlates requests or g nat ng from REST endpo nts and GraphQL endpo nts.'
   )
} (strato.graphql.typena ='Unret etRequest')

struct Unret etResponse {
  /**
   * T  response conta ns t  s ce t et's  D be ng unret eted.
   * Reasons for t :
   *  )   T  operat on should return a non-vo d response to reta n cons stency
   *      w h ot r t et mutat on AP s.
   *   )  T  response struct should def ne at least one f eld due to requ re nts
   *      of t  GraphQL  nfrastructure.
   *    ) T  allows t  caller to hydrate t  s ce t et  f requ red and request
   *      updated counts on t  s ce t et  f des red. (s nce t  operat on decre nts
   *      t  s ce t et's ret et count)
   */
  1: opt onal  64 s ce_t et_ d (
    strato.space='T et',
    strato.graphql.f eldna ='s ce_t et',
    strato.descr pt on='T  s ce t et that was unret eted.'
  )
} (strato.graphql.typena ='Unret etResponse')

struct Unret etResponseW hSubqueryPrefetch ems {
  1: opt onal Unret etResponse data
  2: opt onal ex stsAndPrefetch.Prefetc dData subqueryPrefetch ems
}


struct CreateRet etRequest {
  1: requ red  64 t et_ d (strato.json.numbers.type='str ng')

  // @see com.tw ter.t etyp e.thr ftscala.PostT etRequest.nullcast
  2: bool nullcast = 0 (
    strato.descr pt on='Do not del ver t  ret et to a user\'s follo rs. http://go/nullcast'
  )

  // @see com.tw ter.ads.callback.thr ftscala.Engage ntRequest
  3: opt onal engage nt_request.Engage ntRequest engage nt_request (
    strato.descr pt on='T  ad engage nt from wh ch t  ret et was created.'
  )

  // @see com.tw ter.t etyp e.thr ftscala.PostT etRequest.PostT etRequest.compar son_ d
  4: opt onal str ng compar son_ d (
    strato.descr pt on='Correlates requests or g nat ng from REST endpo nts and GraphQL endpo nts. UU D v4 (random) 36 character str ng.'
  )
} (strato.graphql.typena ='CreateRet etRequest')

struct CreateRet etResponse {
  1: opt onal  64 ret et_ d (
    strato.space='T et',
    strato.graphql.f eldna ='ret et',
    strato.descr pt on='T  created ret et.'
  )
} (strato.graphql.typena ='CreateRet etResponse')

struct CreateRet etResponseW hSubqueryPrefetch ems {
  1: opt onal CreateRet etResponse data
  2: opt onal ex stsAndPrefetch.Prefetc dData subqueryPrefetch ems
}

struct T etReply {
  //@see com.tw ter.t etyp e.thr ftscala.PostT etRequest. n_reply_to_t et_ d
  1:  64  n_reply_to_t et_ d (
    strato.json.numbers.type='str ng',
    strato.descr pt on='T   d of t  t et that t  t et  s reply ng to.'
  )
  //@see com.tw ter.t etyp e.thr ftscala.PostT etRequest.exclude_reply_user_ ds
  2: l st< 64> exclude_reply_user_ ds = [] (
    strato.json.numbers.type='str ng',
    strato.descr pt on='Screen na s appear ng  n t   nt on pref x can be excluded. Because t   nt on pref x must always  nclude t  lead ng  nt on to preserve d rected-at address ng for t   n-reply-to t et author, attempt ng to exclude that user  d w ll have no effect. Spec fy ng a user  d not  n t  pref x w ll be s lently  gnored.'
  )
} (strato.graphql.typena ='T etReply')

struct T et d aEnt y {
  // @see com.tw ter.t etyp e.thr ftscala.PostT etRequest. d a_upload_ ds
  1:  64  d a_ d (
    strato.json.numbers.type='str ng',
    strato.descr pt on=' d a  d as obta ned from t  User  mage Serv ce w n uploaded.'
  )

  // @see com.tw ter.t etyp e.thr ftscala.T et. d a_tags
  2: l st< 64> tagged_users = [] (
    strato.json.numbers.type='str ng',
    strato.descr pt on='L st of user_ ds to tag  n t   d a ent y. Requ res Cl ent App Pr velege MED A_TAGS. Contr butors (http://go/teams) are not supported. Tags are s lently dropped w n unauthor zed.'
  )
} (strato.graphql.typena ='T et d aEnt y')

struct T et d a {
  1: l st<T et d aEnt y>  d a_ent  es = [] (
    strato.descr pt on='  may  nclude up to 4 photos or 1 an mated G F or 1 v deo  n a T et.'
  )

  /**
   * @deprecated @see com.tw ter.t etyp e.thr ftscala.PostT etRequest.poss bly_sens  ve for
   * more deta ls on why t  f eld  s  gnored.
   */
  2: bool poss bly_sens  ve = 0 (
    strato.descr pt on='Mark t  t et as poss bly conta n ng object onable  d a.'
  )
} (strato.graphql.typena ='T et d a')

//T   s s m lar to t  AP T etAnnotat on struct except that  re all t   d f elds are requ red.
struct T etAnnotat on {
  1:  64 group_ d (strato.json.numbers.type='str ng')
  2:  64 doma n_ d (strato.json.numbers.type='str ng')
  3:  64 ent y_ d (strato.json.numbers.type='str ng')
} (strato.graphql.typena ='T etAnnotat on', strato.case.format='preserve')

struct T etGeoCoord nates {
  1: double lat ude (strato.descr pt on='T  lat ude of t  locat on t  T et refers to. T  val d range for lat ude  s -90.0 to +90.0 (North  s pos  ve)  nclus ve.')
  2: double long ude (strato.descr pt on='T  long ude of t  locat on t  T et refers to. T  val d range for long ude  s -180.0 to +180.0 (East  s pos  ve)  nclus ve.')
  3: bool d splay_coord nates = 1 (strato.descr pt on='W t r or not make t  coord nates publ c. W n false, geo coord nates are pers sted w h t  T et but are not shared publ cly.')
} (strato.graphql.typena ='T etGeoCoord nates')

struct T etGeo {
  1: opt onal T etGeoCoord nates coord nates (
    strato.descr pt on='T  geo coord nates of t  locat on t  T et refers to.'
  )
  2: opt onal str ng place_ d (
    strato.descr pt on='A place  n t  world. See also https://developer.tw ter.com/en/docs/tw ter-ap /v1/data-d ct onary/object-model/geo#place'
  )
  3: opt onal str ng geo_search_request_ d (
    strato.descr pt on='See https://confluence.tw ter.b z/d splay/GEO/Pass ng+t +geo+search+request+ D'
  )
} (
  strato.graphql.typena ='T etGeo',
  strato.descr pt on='T et geo locat on  tadata. See https://developer.tw ter.com/en/docs/tw ter-ap /v1/data-d ct onary/object-model/geo'
)

enum BatchComposeMode {
  BATCH_F RST = 1 (strato.descr pt on='T   s t  f rst T et  n a batch.')
  BATCH_SUBSEQUENT = 2 (strato.descr pt on='T   s any of t  subsequent T ets  n a batch.')
}(
  strato.graphql.typena ='BatchComposeMode',
  strato.descr pt on=' nd cates w t r a T et was created us ng a batch composer, and  f so pos  on of a T et w h n t  batch. A value of None,  nd cates that t  t et was not created  n a batch. More  nfo: go/batchcompose.'
)

/**
 * Conversat on Controls
 * See also:
 *   t et.thr ft/T et.conversat on_control
 *   t et_serv ce.thr ft/T etCreateConversat onControl
 *   t et_serv ce.thr ft/PostT etRequest.conversat on_control
 *
 * T se types are  somorph c/equ valent to t et_serv ce.thr ft/T etCreateConversat onControl* to
 * avo d expos ng  nternal serv ce thr ft types.
 */
enum Conversat onControlMode {
  BY_ NV TAT ON = 1 (strato.descr pt on='Users that t  conversat on owner  nt ons by @screenna   n t  t et text are  nv ed.')
  COMMUN TY = 2 (strato.descr pt on='T  conversat on owner,  nv ed users, and users who t  conversat on owner follows can reply.')
} (
  strato.graphql.typena ='Conversat onControlMode'
)

struct T etConversat onControl {
  1: Conversat onControlMode mode
} (
  strato.graphql.typena ='T etConversat onControl',
  strato.descr pt on='Spec f es l m s on user part c pat on  n a conversat on. See also http://go/dont-at- . Up to one value may be prov ded. (Conceptually t   s a un on, ho ver graphql doesn\'t support un on types as  nputs.)'
)

// empty for now, but  ntended to be populated  n later  erat ons of t  super follows project.
struct Exclus veT etControlOpt ons {} (
  strato.descr pt on='Marks a t et as exclus ve. See go/superfollows.',
  strato.graphql.typena ='Exclus veT etControlOpt ons',
)

struct Ed Opt ons {
  1: opt onal  64 prev ous_t et_ d (strato.json.numbers.type='str ng', strato.descr pt on='prev ous T et  d')
} (
  strato.descr pt on='Ed  opt ons for a T et.',
  strato.graphql.typena ='Ed Opt ons',
)

struct T etPer scopeContext {
  1: bool  s_l ve = 0 (
    strato.descr pt on=' nd cates  f t  t et conta ns l ve stream ng v deo. A value of false  s equ valent to t  struct be ng undef ned  n t  CreateT etRequest.'
  )

  // Note that t  REST AP  also def nes a context_per scope_creator_ d param. T  GraphQL
  // AP   nfers t  value from t  Tw terContext V e r.user d s nce   should always be
  // t  sa  as t  T et.coreData.user d wh ch  s also  nferred from V e r.user d.
} (
  strato.descr pt on='Spec f es  nformat on about l ve v deo stream ng. Note that t  Per scope product was shut down  n March 2021, ho ver so  l ve v deo stream ng features rema n  n t  Tw ter app. T  struct keeps t  Per scope nam ng convent on to reta n par y and traceab l y to ot r areas of t  codebase that also reta n t  Per scope na .',
  strato.graphql.typena ='T etPer scopeContext',
)

struct TrustedFr endsControlOpt ons {
  1: requ red  64 trusted_fr ends_l st_ d (
    strato.json.numbers.type='str ng',
    strato.descr pt on='T   D of t  Trusted Fr ends L st whose  mbers can v ew t  t et.'
  )
} (
  strato.descr pt on='Spec f es  nformat on for a Trusted Fr ends t et.  See go/trusted-fr ends',
  strato.graphql.typena ='TrustedFr endsControlOpt ons',
)

enum CollabControlType {
  COLLAB_ NV TAT ON = 1 (strato.descr pt on='T  represents a Collab nv at on.')
  // Note that a CollabT et cannot be created through external graphql request,
  // rat r a user can create a Collab nv at on (wh ch  s automat cally nullcasted) and a
  // publ c CollabT et w ll be created w n all Collaborators have accepted t  Collab nv at on,
  // tr gger ng a strato column to  nstant ate t  CollabT et d rectly
}(
  strato.graphql.typena ='CollabControlType',
)

struct CollabControlOpt ons {
  1: requ red CollabControlType collabControlType
  2: requ red l st< 64> collaborator_user_ ds (
  strato.json.numbers.type='str ng',
  strato.descr pt on='A l st of user  ds represent ng all Collaborators on a CollabT et or Collab nv at on')
}(
  strato.graphql.typena ='CollabControlOpt ons',
  strato.descr pt on='Spec f es  nformat on about a CollabT et or Collab nv at on (a un on  s used to ensure CollabControl def nes one or t  ot r). See more at go/collab-t ets.'
)

struct NoteT etOpt ons {
  1: requ red  64 note_t et_ d (
  strato.json.numbers.type='str ng',
  strato.descr pt on='T   D of t  Note T et that has to be assoc ated w h t  created T et.')
  // Deprecated
  2: opt onal l st<str ng>  nt oned_screen_na s (
  strato.descr pt on = 'Screen na s of t  users  nt oned  n t  NoteT et. T   s used to set conversat on control on t  T et.')

  3: opt onal l st< 64>  nt oned_user_ ds (
  strato.descr pt on = 'User  ds of  nt oned users  n t  NoteT et. T   s used to set conversat on control on t  T et, send  nt oned user  ds to TLS'
  )
  4: opt onal bool  s_expandable (
  strato.descr pt on = 'Spec f es  f t  T et can be expanded  nto t  NoteT et, or  f t y have t  sa  text'
  )
} (
  strato.graphql.typena ='NoteT etOpt ons',
  strato.descr pt on='Note T et opt ons for a T et.'
)

// NOTE: So  cl ents  re us ng t  dark_request d rect ve  n GraphQL to s gnal that a T et should not be pers sted
// but t   s not recom nded, s nce t  dark_request d rect ve  s not  ant to be used for bus ness log c. 
struct UndoOpt ons {
  1: requ red bool  s_undo (
  strato.descr pt on='Set to true  f t  T et  s undo-able. T etyp e w ll process t  T et but w ll not pers st  .'
  )
} (
  strato.graphql.typena ='UndoOpt ons'
)

struct CreateT etRequest {
  1: str ng t et_text = "" (
    strato.descr pt on='T  user-suppl ed text of t  t et. Defaults to empty str ng. Lead ng & tra l ng wh espace are tr m d, rema n ng value may be empty  f and only  f one or more  d a ent y  ds are also prov ded.'
  )

  // @see com.tw ter.t etyp e.thr ftscala.PostT etRequest.nullcast
  2: bool nullcast = 0 (
    strato.descr pt on='Do not del ver t  t et to a user\'s follo rs. http://go/nullcast'
  )

  // @see com.tw ter.t etyp e.thr ftscala.PostT etRequest.PostT etRequest.compar son_ d
  3: opt onal str ng compar son_ d (
    strato.descr pt on='Correlates requests or g nat ng from REST endpo nts and GraphQL endpo nts. UU D v4 (random) 36 character str ng.'
  )

  // @see com.tw ter.ads.callback.thr ftscala.Engage ntRequest
  4: opt onal engage nt_request.Engage ntRequest engage nt_request (
    strato.descr pt on='T  ad engage nt from wh ch t  t et was created.'
  )

  // @see com.tw ter.t etyp e.thr ftscala.PostT etRequest.attach nt_url
  5: opt onal str ng attach nt_url (
    strato.descr pt on='T et permal nk ( .e. Quoted T et) or D rect  ssage deep l nk. T  URL  s not  ncluded  n t  v s ble_text_range.'
  )

  // @see com.tw ter.t etyp e.thr ftscala.T et.card_reference
  6: opt onal str ng card_ur  (
    strato.descr pt on='L nk to t  card to assoc ate w h a t et.'
  )

  7: opt onal T etReply reply (
    strato.descr pt on='Reply para ters.'
  )

  8: opt onal T et d a  d a (
    strato.descr pt on=' d a para ters.'
  )

  9: opt onal l st<T etAnnotat on> semant c_annotat on_ ds (
    strato.descr pt on='Esc rb rd Annotat ons.'
  )

  10: opt onal T etGeo geo (
    strato.descr pt on='T et geo locat on  tadata. See https://developer.tw ter.com/en/docs/tw ter-ap /v1/data-d ct onary/object-model/geo'
  )

  11: opt onal BatchComposeMode batch_compose (
    strato.descr pt on='Batch Compose Mode. See go/batchcompose'
  )

  12: opt onal Exclus veT etControlOpt ons exclus ve_t et_control_opt ons (
    strato.descr pt on='W n def ned, t  t et w ll be marked as exclus ve. Leave undef ned to s gn fy a regular, non-exclus ve t et. See go/superfollows.'
  )

  13: opt onal T etConversat onControl conversat on_control (
    strato.descr pt on='Restr ct repl es to t  t et. See http://go/dont-at- -ap . Only val d for conversat on root t ets. Appl es to all repl es to t  t et.'
  )

  14: opt onal T etPer scopeContext per scope (
    strato.descr pt on='Spec f es  nformat on about l ve v deo stream ng. Note that t  Per scope product was shut down  n March 2021, ho ver so  l ve v deo stream ng features rema n  n t  Tw ter app. T  struct keeps t  Per scope nam ng convent on to reta n par y and traceab l y to ot r areas of t  codebase that also reta n t  Per scope na . Note: A value of per scope. sL ve=false  s equ valent to t  struct be ng left undef ned.'
  )

  15: opt onal TrustedFr endsControlOpt ons trusted_fr ends_control_opt ons (
    strato.descr pt on='Trusted Fr ends para ters.'
  )

  16: opt onal CollabControlOpt ons collab_control_opt ons (
    strato.descr pt on='Collab T et & Collab  nv at on para ters.'
  )

  17: opt onal Ed Opt ons ed _opt ons (
    strato.descr pt on='w n def ned, t  t et w ll be marked as an ed  of t  t et represented by prev ous_t et_ d  n ed _opt ons.'
  )

  18: opt onal NoteT etOpt ons note_t et_opt ons (
    strato.descr pt on='T  Note T et that  s to be assoc ated w h t  created T et.',
    strato.graphql.sk p='true'
  )

  19: opt onal UndoOpt ons undo_opt ons (
    strato.descr pt on=' f t  user has Undo T ets enabled, t  T et  s created so that   can be prev e d by t  cl ent but  s not pers sted.',
  )
} (strato.graphql.typena ='CreateT etRequest')

struct CreateT etResponse {
  1: opt onal  64 t et_ d (
    strato.space='T et',
    strato.graphql.f eldna ='t et',
    strato.descr pt on='T  created t et.'
  )
} (strato.graphql.typena ='CreateT etResponse')

struct CreateT etResponseW hSubqueryPrefetch ems {
  1: opt onal CreateT etResponse data
  2: opt onal ex stsAndPrefetch.Prefetc dData subqueryPrefetch ems
}

// Request struct, ResponseStruct, ResponseW hPrefetchStruct
struct DeleteT etRequest {
  1: requ red  64 t et_ d (strato.json.numbers.type='str ng')

  // @see com.tw ter.t etyp e.thr ftscala.PostT etRequest.PostT etRequest.compar son_ d
  2: opt onal str ng compar son_ d (
    strato.descr pt on='Correlates requests or g nat ng from REST endpo nts and GraphQL endpo nts. UU D v4 (random) 36 character str ng.'
  )
} (strato.graphql.typena ='DeleteT etRequest')

struct DeleteT etResponse {
  1: opt onal  64 t et_ d (
    strato.space='T et',
    strato.graphql.f eldna ='t et',
    strato.descr pt on='T  deleted T et. S nce t  T et w ll always be not found after delet on, t  T etResult w ll always be empty.'
  )
} (strato.graphql.typena ='DeleteT etResponse')

struct DeleteT etResponseW hSubqueryPrefetch ems {
  1: opt onal DeleteT etResponse data
  2: opt onal ex stsAndPrefetch.Prefetc dData subqueryPrefetch ems
}
