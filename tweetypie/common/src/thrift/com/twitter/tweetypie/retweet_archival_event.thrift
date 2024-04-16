na space java com.tw ter.t etyp e.thr ftjava
na space py gen.tw ter.t etyp e.ret et_arch val_event
#@na space scala com.tw ter.t etyp e.thr ftscala
#@na space strato com.tw ter.t etyp e
na space rb T etyP e
na space go t etyp e

/**
 * T  event  s publ s d to "ret et_arch val_events" w n T etyp e processes an
 * AsyncSetRet etV s b l yRequest.
 *
 * T   s useful for serv ces ( nteract on Counter,  ns ghts Track) that need to
 * know w n t  ret et engage nt count of a t et has been mod f ed due to t 
 * ret et ng user be ng put  n to or out of suspens on or read-only mode.
 */
struct Ret etArch valEvent {
  // T  ret et  d affected by t  arch val event.
  1: requ red  64 ret et_ d (personalDataType = 'T et d')
  // T  s ce t et  d for t  ret et. T  t et had  s ret et count mod f ed.
  2: requ red  64 src_t et_ d (personalDataType = 'T et d')
  3: requ red  64 ret et_user_ d (personalDataType = 'User d')
  4: requ red  64 src_t et_user_ d (personalDataType = 'User d')
  // Approx mate t    n m ll seconds for w n t  count mod f cat on occurred, based on
  // Un x Epoch (1 January 1970 00:00:00 UTC). T etyp e w ll use t  t   w n    s
  // about to send t  asynchronous wr e request to tflock for t  t  stamp.
  5: requ red  64 t  stamp_ms
  // Marks  f t  event  s for arch v ng(True) or unarch v ng(False) act on.
  // Arch v ng  nd cates an engage nt count decre nt occurred and unarch v ng  nd cates an  ncre ntal.
  6: opt onal bool  s_arch v ng_act on
}(pers sted='true', hasPersonalData = 'true')
