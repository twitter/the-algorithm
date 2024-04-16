na space java com.tw ter.un f ed_user_act ons.thr ftjava
#@na space scala com.tw ter.un f ed_user_act ons.thr ftscala
#@na space strato com.tw ter.un f ed_user_act ons

/*  nput s ce */
enum S ceL neage {
  /* Cl ent-s de. Also known as legacy cl ent events or LCE. */
  Cl entEvents = 0
  /* Cl ent-s de. Also known as BCE. */
  Behav oralCl entEvents = 1
  /* Server-s de T  l neserv ce favor es */
  ServerTlsFavs = 2
  /* Server-s de T etyp e events */
  ServerT etyp eEvents = 3
  /* Server-s de Soc alGraph events */
  ServerSoc alGraphEvents = 4
  /* Not f cat on Act ons respond ng to Y  H ghl ghts Ema ls */
  Ema lNot f cat onEvents = 5
  /**
  * G zmoduck's User Mod f cat on events https://docb rd.tw ter.b z/g zmoduck/user_mod f cat ons.html
  **/
  ServerG zmoduckUserMod f cat onEvents = 6
  /**
  * Server-s de Ads callback engage nts
  **/
  ServerAdsCallbackEngage nts = 7
  /**
  * Server-s de favor e arch val events
  **/
  ServerFavor eArch valEvents = 8
  /**
  * Server-s de ret et arch val events
  **/
  ServerRet etArch valEvents = 9
}(pers sted='true', hasPersonalData='false')

/*
 * Only ava lable  n behav oral cl ent events (BCE).
 *
 * A breadcrumb t et  s a t et that was  nteracted w h pr or to t  current act on.
 */
struct BreadcrumbT et {
  /*  d for t  t et that was  nteracted w h pr or to t  current act on */
  1: requ red  64 t et d(personalDataType = 'T et d')
  /*
   * T  U  component that hosted t  t et and was  nteracted w h preceed ng to t  current act on.
   * - t et: represents t  parent t et conta ner that wraps t  quoted t et
   * - quote_t et: represents t  nested or quoted t et w h n t  parent conta ner
   *
   * See more deta ls
   * https://docs.google.com/docu nt/d/16CdSRpsmUUd17yoFH9m n3nLBqDVawx4DaZo qSfCH /ed # ad ng=h.nb7tnjrhqxpm
   */
  2: requ red str ng s ceComponent(personalDataType = ' bs ePage')
}(pers sted='true', hasPersonalData='true')

/*
 * Cl entEvent's na spaces. See https://docb rd.tw ter.b z/cl ent_events/cl ent-event-na spaces.html
 *
 * - For Legacy Cl ent Events (LCE),   excludes t  cl ent part of t 
 * s x part na space (cl ent:page:sect on:component:ele nt:act on)
 * s nce t  part  s better captured by cl entApp d and cl entVers on.
 *
 * - For Behav oral Cl ent Events (BCE), use cl entPlatform to  dent fy t  cl ent.
 * Add  onally, BCE conta ns an opt onal subsect on to denote t  U  component of
 * t  current act on. T  Cl entEventNa space.component f eld w ll be always empty for
 * BCE na space. T re  s no stra ghtfoward 1-1 mapp ng bet en BCE and LCE na space.
 */
struct Cl entEventNa space {
  1: opt onal str ng page(personalDataType = 'AppUsage')
  2: opt onal str ng sect on(personalDataType = 'AppUsage')
  3: opt onal str ng component(personalDataType = 'AppUsage')
  4: opt onal str ng ele nt(personalDataType = 'AppUsage')
  5: opt onal str ng act on(personalDataType = 'AppUsage')
  6: opt onal str ng subsect on(personalDataType = 'AppUsage')
}(pers sted='true', hasPersonalData='true')

/*
 *  tadata that  s  ndependent of a part cular (user,  em, act on type) tuple
 * and mostly shared across user act on events.
 */
struct Event tadata {
  /* W n t  act on happened accord ng to whatever s ce   are read ng from */
  1: requ red  64 s ceT  stampMs(personalDataType = 'Pr vateT  stamp, Publ cT  stamp')
  /* W n t  act on was rece ved for process ng  nternally 
   *  (compare w h s ceT  stampMs for delay)
   */
  2: requ red  64 rece vedT  stampMs
  /* Wh ch s ce  s t  event der ved, e.g. CE, BCE, T  l neFavs */
  3: requ red S ceL neage s ceL neage
  /* To be deprecated and replaced by requestJo n d
   * Useful for jo n ng w h ot r datasets
   * */
  4: opt onal  64 trace d(personalDataType = 'TfeTransact on d')
  /*
   * T   s t  language  nferred from t  request of t  user act on event (typ cally user's current cl ent language)
   * NOT t  language of any T et,
   * NOT t  language that user sets  n t  r prof le!!!
   *
   *  - Cl entEvents && Behav oralCl entEvents: Cl ent U  language or from G zmoduck wh ch  s what user set  n Tw ter App.
   *      Please see more at https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/f natra- nternal/ nternat onal/src/ma n/scala/com/tw ter/f natra/ nternat onal/Language dent f er.scala
   *      T  format should be  SO 639-1.
   *  - ServerTlsFavs: Cl ent U  language, see more at http://go/languagepr or y. T  format should be  SO 639-1.
   *  - ServerT etyp eEvents: UUA sets t  to None s nce t re  s no request level language  nfo.
   */
  5: opt onal str ng language(personalDataType = ' nferredLanguage')
  /*
   * T   s t  country  nferred from t  request of t  user act on event (typ cally user's current country code)
   * NOT t  country of any T et (by geo-tagg ng),
   * NOT t  country set by t  user  n t  r prof le!!!
   *
   *  - Cl entEvents && Behav oralCl entEvents: Country code could be  P address (geoduck) or
   *      User reg strat on country (g zmoduck) and t  for r takes precedence.
   *        don’t know exactly wh ch one  s appl ed, unfortunately,
   *      see https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/f natra- nternal/ nternat onal/src/ma n/scala/com/tw ter/f natra/ nternat onal/Country dent f er.scala
   *      T  format should be  SO_3166-1_alpha-2.
   *  - ServerTlsFavs: From t  request (user’s current locat on),
   *      see https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/src/thr ft/com/tw ter/context/v e r.thr ft?L54
   *      T  format should be  SO_3166-1_alpha-2.
   *  - ServerT etyp eEvents:
   *      UUA sets t  to be cons stent w h  ES ce to  et ex st ng use requ re nt.
   *      see https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/src/thr ft/com/tw ter/t etyp e/t et.thr ft?L1001.
   *      T  def n  ons  re confl cts w h t   ntent on of UUA to log t  request country code
   *      rat r than t  s gnup / geo-tagg ng country.
   */
  6: opt onal str ng countryCode(personalDataType = ' nferredCountry')
  /* Useful for debugg ng cl ent appl cat on related  ssues */
  7: opt onal  64 cl entApp d(personalDataType = 'App d')
  /* Useful for debugg ng cl ent appl cat on related  ssues */
  8: opt onal str ng cl entVers on(personalDataType = 'Cl entVers on')
  /* Useful for f lter ng */
  9: opt onal Cl entEventNa space cl entEventNa space
  /*
   * T  f eld  s only populated  n behav oral cl ent events (BCE).
   *
   * T  cl ent platform such as one of [" Phone", " Pad", "Mac", "Andro d", " b"]
   * T re can be mult ple cl entApp ds for t  sa  platform.
   */
  10: opt onal str ng cl entPlatform(personalDataType = 'Cl entType')
  /*
   * T  f eld  s only populated  n behav oral cl ent events (BCE).
   *
   * T  current U  h erarchy  nformat on w h human readable labels.
   * For example, [ho ,t  l ne,t et] or [tab_bar,ho ,scrollable_content,t et]
   *
   * For more deta ls see https://docs.google.com/docu nt/d/16CdSRpsmUUd17yoFH9m n3nLBqDVawx4DaZo qSfCH /ed # ad ng=h.uv3md49 0j4j
   */
  11: opt onal l st<str ng> v ewH erarchy(personalDataType = ' bs ePage')
  /*
   * T  f eld  s only populated  n behav oral cl ent events (BCE).
   *
   * T  sequence of v ews (breadcrumb) that was  nteracted w h that caused t  user to nav gate to
   * t  current product surface (e.g. prof le page) w re an act on was taken.
   *
   * T  breadcrumb  nformat on may only be present for certa n preced ng product surfaces (e.g. Ho  T  l ne).
   * See more deta ls  n https://docs.google.com/docu nt/d/16CdSRpsmUUd17yoFH9m n3nLBqDVawx4DaZo qSfCH /ed # ad ng=h.nb7tnjrhqxpm
   */
  12: opt onal l st<str ng> breadcrumbV ews(personalDataType = ' bs ePage')
  /*
   * T  f eld  s only populated  n behav oral cl ent events (BCE).
   *
   * T  sequence of t ets (breadcrumb) that was  nteracted w h that caused t  user to nav gate to
   * current product surface (e.g. prof le page) w re an act on was taken.
   *
   * T  breadcrumb  nformat on may only be present for certa n preced ng product surfaces (e.g. Ho  T  l ne).
   * See more deta ls  n https://docs.google.com/docu nt/d/16CdSRpsmUUd17yoFH9m n3nLBqDVawx4DaZo qSfCH /ed # ad ng=h.nb7tnjrhqxpm
   */
   13: opt onal l st<BreadcrumbT et> breadcrumbT ets(personalDataType = 'T et d')
  /*
    * A request jo n  d  s created by backend serv ces and broadcasted  n subsequent calls
    * to ot r downstream serv ces as part of t  request path. T  requestJo n d  s logged
    *  n server logs and scr bed  n cl ent events, enabl ng jo ns across cl ent and server
    * as  ll as w h n a g ven request across backend servers. See go/jo nkey-tdd for more
    * deta ls.
    */
   14: opt onal  64 requestJo n d(personalDataType = 'Transact on d')
   15: opt onal  64 cl entEventTr ggeredOn
}(pers sted='true', hasPersonalData='true')
