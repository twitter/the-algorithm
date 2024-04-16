na space java com.tw ter.un f ed_user_act ons.thr ftjava
#@na space scala com.tw ter.un f ed_user_act ons.thr ftscala
#@na space strato com.tw ter.un f ed_user_act ons

 nclude "com/tw ter/un f ed_user_act ons/act on_ nfo.thr ft"
 nclude "com/tw ter/cl entapp/gen/cl ent_app.thr ft"

/*
 * T et  em  nformat on. So  develop nt notes:
 * 1. Please keep t  top-level struct as m n mal as poss ble to reduce over ad.
 * 2.    ntent onally avo d nest ng act on t et  n a separate structure
 * to underscore  s  mportance and fac l ate extract on of most commonly
 * needed f elds such as act onT et d. New f elds related to t  act on t et
 * should generally be pref xed w h "act onT et". 
 * 3. For t  related T ets, e.g. ret et ngT et d,  nReplyToT et d, etc,  
 * mostly only keep t  r  ds for cons stency and s mpl c y.
 */
struct T et nfo {
  
  /*  d for t  t et that was act oned on */
  1: requ red  64 act onT et d(personalDataType = 'T et d')
  // Deprecated, please don't re-use!
  // 2: opt onal  64 act onT etAuthor d(personalDataType = 'User d')
  /* T  soc al proof ( .e. banner) Top c  d that t  act on T et  s assoc ated to */
  3: opt onal  64 act onT etTop cSoc alProof d(personalDataType=' nferred nterests, Prov ded nterests')
  4: opt onal Author nfo act onT etAuthor nfo

  // F elds 1-99 reserved for `act onFooBar` f elds

  /* Add  onal deta ls for t  act on that took place on act onT et d */
  100: opt onal act on_ nfo.T etAct on nfo t etAct on nfo

  /*  d of t  t et ret et ng t  act on t et */
  101: opt onal  64 ret et ngT et d(personalDataType = 'T et d')
  /*  d of t  t et quot ng t  act on T et, w n t  act on type  s quote */
  102: opt onal  64 quot ngT et d(personalDataType = 'T et d')
  /*  d of t  t et reply ng to t  act on T et, w n t  act on type  s reply */
  103: opt onal  64 reply ngT et d(personalDataType = 'T et d')
  /*  d of t  t et be ng quoted by t  act on t et */
  104: opt onal  64 quotedT et d(personalDataType = 'T et d')
  /*  d of t  t et be ng repl ed to by t  act on t et */
  105: opt onal  64  nReplyToT et d(personalDataType = 'T et d')
  /*  d of t  t et be ng ret eted by t  act on t et, t   s just for Unret et act on */
  106: opt onal  64 ret etedT et d(personalDataType = 'T et d')
  /*  d of t  t et be ng ed ed, t   s only ava lable for T etEd  act on, and T etDelete
   * act on w n t  deleted t et was created from Ed . */
  107: opt onal  64 ed edT et d(personalDataType = 'T et d')
  /* Pos  on of a t et  em  n a page such as ho  and t et deta l, and  s populated  n
   * Cl ent Event. */
  108: opt onal  32 t etPos  on
  /* Promoted d  s prov ded by ads team for each promoted t et and  s logged  n cl ent event */
  109: opt onal str ng promoted d(personalDataType = 'Ads d')
  /* correspond ng to  nReplyToT et d */
  110: opt onal  64  nReplyToAuthor d(personalDataType = 'User d')
  /* correspond ng to ret et ngT et d */
  111: opt onal  64 ret et ngAuthor d(personalDataType = 'User d')
  /* correspond ng to quotedT et d */
  112: opt onal  64 quotedAuthor d(personalDataType = 'User d')
}(pers sted='true', hasPersonalData='true')

/*
 * Prof le  em  nformat on. T  follows T et nfo's develop nt notes.
 */
struct Prof le nfo {

  /*  d for t  prof le (user_ d) that was act oned on
   *
   *  n a soc al graph user act on, e.g., user1 follows/blocks/mutes user2,
   * user dent f er captures user d of user1 and act onProf le d records
   * t  user d of user2.
   */
  1: requ red  64 act onProf le d(personalDataType = 'User d')

  // F elds 1-99 reserved for `act onFooBar` f elds
  /* t  full na  of t  user. max length  s 50. */
  2: opt onal str ng na (personalDataType = 'D splayNa ')
  /* T  handle/screenNa  of t  user. T  can't be changed.
   */
  3: opt onal str ng handle(personalDataType = 'UserNa ')
  /* t  "b o" of t  user. max length  s 160. May conta n one or more t.co
   * l nks, wh ch w ll be hydrated  n t  UrlEnt  es substruct  f t 
   * QueryF elds.URL_ENT T ES  s spec f ed.
   */
  4: opt onal str ng descr pt on(personalDataType = 'B o')

  /* Add  onal deta ls for t  act on that took place on act onProf le d */
  100: opt onal act on_ nfo.Prof leAct on nfo prof leAct on nfo
}(pers sted='true', hasPersonalData='true')

/*
 * Top c  em  nformat on. T  follows T et nfo's develop nt notes.
 */
struct Top c nfo {
  /*  d for t  Top c that was act oned on */
  1: requ red  64 act onTop c d(personalDataType=' nferred nterests, Prov ded nterests')

  // F elds 1-99 reserved for `act onFooBar` f elds
}(pers sted='true', hasPersonalData='true')

/*
 * Not f cat on  em  nformat on.
 *
 * See go/phab-d973370-d scuss, go/phab-d968144-d scuss, and go/uua-act on-type for deta ls about
 * t  sc ma des gn for Not f cat on events.
 */
struct Not f cat on nfo {
 /*
  *  d of t  Not f cat on was act oned on.
  *
  * Note that t  f eld represents t  ` mpress on d` of a Not f cat on.   has been rena d to
  * `not f cat on d`  n UUA so that t  na  effect vely represents t  value   holds,
  *  .e. a un que  d for a Not f cat on and request.
  */
  1: requ red str ng act onNot f cat on d(personalDataType='Un versallyUn que dent f erUu d')
  /*
   * Add  onal  nformat on conta ned  n a Not f cat on. T   s a `un on` arm to d fferent ate
   * among d fferent types of Not f cat ons and store relevant  tadata for each type.
   *
   * For example, a Not f cat on w h a s ngle T et w ll hold t  T et  d  n `T etNot f cat on`.
   * S m larly, `Mult T etNot f cat on`  s def ned for Not  fcat ons w h mult ple T et  ds.
   *
   * Refer to t  def n  on of `un on Not f cat onContent` below for more deta ls.
   */
  2: requ red Not f cat onContent content
}(pers sted='true', hasPersonalData='true')

/*
 * Add  onal  nformat on conta ned  n a Not f cat on.
 */
un on Not f cat onContent {
  1: T etNot f cat on t etNot f cat on
  2: Mult T etNot f cat on mult T etNot f cat on

  // 3 - 100 reserved for ot r spec f c Not f cat on types (for example, prof le, event, etc.).

  /*
   *  f a Not f cat on cannot be categor zed  nto any of t  types at  nd ces 1 - 100,
   *    s cons dered of `Unknown` type.
   */
  101: UnknownNot f cat on unknownNot f cat on
}(pers sted='true', hasPersonalData='true')

/*
 * Not f cat on conta ns exactly one `t et d`.
 */
struct T etNot f cat on {
  1: requ red  64 t et d(personalDataType = 'T et d')
}(pers sted='true', hasPersonalData='true')

/*
 * Not f cat on conta ns mult ple `t et ds`.
 * For example, user A rece ves a Not f cat on w n user B l kes mult ple T ets authored by user A.
 */
struct Mult T etNot f cat on {
  1: requ red l st< 64> t et ds(personalDataType = 'T et d')
}(pers sted='true', hasPersonalData='true')

/*
 * Not f cat on could not be categr zed  nto known types at  nd ces 1 - 100  n `Not f cat onContent`.
 */
struct UnknownNot f cat on {
  // t  f eld  s just a placeholder s nce Sparrow doesn't support empty struct
  100: opt onal bool placeholder
}(pers sted='true', hasPersonalData='false')

/*
 * Trend  em  nformat on for promoted and non-promoted Trends.  
 */
struct Trend nfo {
  /* 
   *  dent f er for promoted Trends only. 
   * T   s not ava lable for non-promoted Trends and t  default value should be set to 0. 
   */
  1: requ red  32 act onTrend d(personalDataType= 'Trend d')
  /*
   * Empty for promoted Trends only. 
   * T  should be set for all non-promoted Trends. 
   */
  2: opt onal str ng act onTrendNa 
}(pers sted='true', hasPersonalData='true')

struct Typea ad nfo {
  /* search query str ng */
  1: requ red str ng act onQuery(personalDataType = 'SearchQuery')
  2: requ red Typea adAct on nfo typea adAct on nfo
}(pers sted='true', hasPersonalData='true')

un on Typea adAct on nfo {
  1: UserResult userResult
  2: Top cQueryResult top cQueryResult
}(pers sted='true', hasPersonalData='true')

struct UserResult {
  /* T  user d of t  prof le suggested  n t  typea ad drop-down, upon wh ch t  user took t  act on */
  1: requ red  64 prof le d(personalDataType = 'User d')
}(pers sted='true', hasPersonalData='true')

struct Top cQueryResult {
  /* T  top c query na  suggested  n t  typea ad drop-down, upon wh ch t  user took t  act on */
  1: requ red str ng suggestedTop cQuery(personalDataType = 'SearchQuery')
}(pers sted='true', hasPersonalData='true')



/*
 *  em that captures feedback related  nformat on subm ted by t  user across modules /  em (Eg: Search Results / T ets)
 * Des gn d scuss on doc: https://docs.google.com/docu nt/d/1UH CrGzf XOSymRAUM565KchVLZBAByMwvP4ARxe xY/ed #
 */
struct FeedbackPrompt nfo {
  1: requ red FeedbackPromptAct on nfo feedbackPromptAct on nfo
}(pers sted='true', hasPersonalData='true')

un on FeedbackPromptAct on nfo {
  1: D d F nd Search d d F nd Search
  2: T etRelevantToSearch t etRelevantToSearch
}(pers sted='true', hasPersonalData='true')

struct D d F nd Search {
  1: requ red str ng searchQuery(personalDataType= 'SearchQuery')
  2: opt onal bool  sRelevant
}(pers sted='true', hasPersonalData='true')

struct T etRelevantToSearch {
  1: requ red str ng searchQuery(personalDataType= 'SearchQuery')
  2: requ red  64 t et d
  3: opt onal bool  sRelevant
}(pers sted='true', hasPersonalData='true')

/*
 * For (T et) Author  nfo
 */
struct Author nfo {
  /*  n pract ce, t  should be set. Rarely,   may be unset. */
  1: opt onal  64 author d(personalDataType = 'User d')
  /*  .e.  n-network (true) or out-of-network (false) */
  2: opt onal bool  sFollo dByAct ngUser
  /*  .e.  s a follo r (true) or not (false) */
  3: opt onal bool  sFollow ngAct ngUser
}(pers sted='true', hasPersonalData='true')

/*
 * Use for Call to Act on events.
 */
struct CTA nfo {
  // t  f eld  s just a placeholder s nce Sparrow doesn't support empty struct
  100: opt onal bool placeholder
}(pers sted='true', hasPersonalData='false')

/*
 * Card  nfo
 */
struct Card nfo {
  1: opt onal  64  d
  2: opt onal cl ent_app. emType  emType
  // author d  s deprecated, please use Author nfo  nstead
  // 3: opt onal  64 author d(personalDataType = 'User d')
  4: opt onal Author nfo act onT etAuthor nfo
}(pers sted='true', hasPersonalData='false')

/*
 * W n t  user ex s t  app, t  t   ( n m ll s) spent by t m on t  platform  s recorded as User Act ve Seconds (UAS). 
 */
struct UAS nfo {
  1: requ red  64 t  SpentMs
}(pers sted='true', hasPersonalData='false')

/*
 * Correspond ng  em for a user act on.
 * An  em should be treated  ndependently  f   has d fferent affordances
 * (https://www. nteract on-des gn.org/l erature/top cs/affordances) for t  user.
 * For example, a Not f cat on has d fferent affordances than a T et  n t  Not f cat on Tab;
 *  n t  for r,   can e  r "cl ck" or "see less often" and  n t  latter,
 *   can perform  nl ne engage nts such as "l ke" or "reply".
 * Note that an  em may be rendered d fferently  n d fferent contexts, but as long as t 
 * affordances rema n t  sa  or nearly s m lar,   can be treated as t  sa   em
 * (e.g. T ets can be rendered  n sl ghtly d fferent ways  n embeds vs  n t  app).
 *  em types (e.g. T ets, Not f cat ons) and Act onTypes should be 1:1, and w n an act on can be
 * perfor d on mult ple types of  ems, cons der granular act on types.
 * For example, a user can take t  Cl ck act on on T ets and Not f cat ons, and   have
 * separate Act onTypes for T et Cl ck and Not f cat on Cl ck. T  makes   eas er to  dent fy all t 
 * act ons assoc ated w h a part cular  em.
 */
un on  em {
  1: T et nfo t et nfo
  2: Prof le nfo prof le nfo
  3: Top c nfo top c nfo
  4: Not f cat on nfo not f cat on nfo
  5: Trend nfo trend nfo
  6: CTA nfo cta nfo
  7: FeedbackPrompt nfo feedbackPrompt nfo
  8: Typea ad nfo typea ad nfo
  9: UAS nfo uas nfo
  10: Card nfo card nfo
}(pers sted='true', hasPersonalData='true')
