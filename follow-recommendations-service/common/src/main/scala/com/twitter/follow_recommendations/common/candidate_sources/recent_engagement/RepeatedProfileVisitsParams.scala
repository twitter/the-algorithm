package com.tw ter.follow_recom ndat ons.common.cand date_s ces.recent_engage nt

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam

object RepeatedProf leV s sParams {

  //  f RepeatedProf leV s sS ce  s run and t re are recom nded cand dates for t  target user, w t r or not
  // to actually  nclude such cand dates  n   output recom ndat ons. T  FS w ll be used to control bucket ng of
  // users  nto control vs treat nt buckets.
  case object  ncludeCand dates
      extends FSParam[Boolean](na  = "repeated_prof le_v s s_ nclude_cand dates", default = false)

  // T  threshold at or above wh ch   w ll cons der a prof le to have been v s ed "frequently enough" to recom nd
  // t  prof le to t  target user.
  case object Recom ndat onThreshold
      extends FSBoundedParam[ nt](
        na  = "repeated_prof le_v s s_recom ndat on_threshold",
        default = 3,
        m n = 0,
        max =  nteger.MAX_VALUE)

  // T  threshold at or above wh ch   w ll cons der a prof le to have been v s ed "frequently enough" to recom nd
  // t  prof le to t  target user.
  case object Bucket ngThreshold
      extends FSBoundedParam[ nt](
        na  = "repeated_prof le_v s s_bucket ng_threshold",
        default = 3,
        m n = 0,
        max =  nteger.MAX_VALUE)

  // W t r or not to use t  onl ne dataset (wh ch has repeated prof le v s s  nformat on updated to w h n m nutes)
  //  nstead of t  offl ne dataset (updated v a offl ne jobs, wh ch can have delays of h s to days).
  case object UseOnl neDataset
      extends FSParam[Boolean](na  = "repeated_prof le_v s s_use_onl ne_dataset", default = true)

}
