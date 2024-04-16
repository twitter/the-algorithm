na space java com.tw ter.t etyp e.thr ftjava
#@na space scala com.tw ter.t etyp e.thr ftscala
na space py gen.tw ter.t etyp e
na space rb T etyP e
na space go t etyp e

/**
 * Event that tr ggers delet on of t  geo  nformat on on t ets created
 * at t  stamp_ms or earl er.
 */
struct DeleteLocat onData {
  /**
   * T   d of t  user whose t ets should have t  r geo  nformat on
   * removed.
   */
  1: requ red  64 user_ d (personalDataType='User d')

  /**
   * T  t   at wh ch t  request was  n  ated. T ets by t  user
   * whose snowflake  ds conta n t  stamps less than or equal to t 
   * value w ll no longer be returned w h geo  nformat on.
   */
  2: requ red  64 t  stamp_ms

  /**
   * T  last t   t  user requested delet on of locat on data pr or
   * to t  request. T  value may be om ted, but should be  ncluded
   *  f ava lable for  mple ntat on eff c ency, s nce   el m nates t 
   * need to scan t ets older than t  value for geo  nformat on.
   */
  3: opt onal  64 last_t  stamp_ms
}(pers sted='true', hasPersonalData='true')
