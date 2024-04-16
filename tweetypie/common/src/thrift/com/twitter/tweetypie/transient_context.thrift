/**
 * T  f le conta ns def n  ons for trans ent, passthrough structured data.
 *
 *  f   need to add structured data that T etyp e accepts  n a request
 * and passes t  data through to one or more backends (eg. EventBus), t 
 *  s t  place to put  . T etyp e may or may not  nspect t  data and
 * alter t  behav or based on  , but   won't change  .
 */

na space java com.tw ter.t etyp e.thr ftjava
#@na space scala com.tw ter.t etyp e.thr ftscala
#@na space strato com.tw ter.t etyp e
na space py gen.tw ter.t etyp e.trans ent_context
na space rb T etyP e
na space go t etyp e

 nclude "com/tw ter/t etyp e/t et.thr ft"

enum BatchComposeMode {
  /**
   * T   s t  f rst T et  n a batch.
   */
  BATCH_F RST = 1

  /**
   * T   s any of t  subsequent T ets  n a batch.
   */
  BATCH_SUBSEQUENT = 2
}

/**
 * Data suppl ed at T et creat on t   that  s not served by T etyp e, but
 *  s passed through to consu rs of t  t et_events eventbus stream as part
 * of T etCreateEvent.
 * T   s d fferent from add  onal_context  n that T etyp e
 *  nspects t  data as  ll, and   prefer structs over str ngs.
 *  f add ng a new f eld that w ll be passed through to eventbus, prefer t 
 * over add  onal_context.
 */
struct Trans entCreateContext {
  /**
   *  nd cates w t r a T et was created us ng a batch composer, and  f so
   * pos  on of a T et w h n t  batch.
   *
   * A value of 'None'  nd cates that t  t et was not created  n a batch.
   *
   * More  nfo: https://docs.google.com/docu nt/d/1dJ9K0KzXPzhk0V-Nsekt0CAdOvyV 8sH9ESE A2eDW4/ed 
   */
  1: opt onal BatchComposeMode batch_compose

  /**
   *  nd cates  f t  t et conta ns a l ve Per scope stream ng v deo.
   *
   * T  enables Per scope L veFollow.
   */
  2: opt onal bool per scope_ s_l ve

  /**
   *  nd cates t  user d of t  l ve Per scope stream ng v deo.
   *
   * T  enables Per scope L veFollow.
   */
  3: opt onal  64 per scope_creator_ d (personalDataType='User d')
}(pers sted='true', hasPersonalData='true')
