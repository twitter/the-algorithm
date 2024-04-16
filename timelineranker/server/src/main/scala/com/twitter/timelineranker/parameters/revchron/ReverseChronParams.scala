package com.tw ter.t  l neranker.para ters.revchron

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam

object ReverseChronParams {
   mport ReverseChronT  l neQueryContext._

  /**
   * Controls l m  on t  number of follo d users fetc d from SGS w n mater al z ng ho  t  l nes.
   */
  object MaxFollo dUsersParam
      extends FSBoundedParam(
        "reverse_chron_max_follo d_users",
        default = MaxFollo dUsers.default,
        m n = MaxFollo dUsers.bounds.m n nclus ve,
        max = MaxFollo dUsers.bounds.max nclus ve
      )

  object ReturnEmptyW nOverMaxFollowsParam
      extends FSParam(
        na  = "reverse_chron_return_empty_w n_over_max_follows",
        default = true
      )

  /**
   * W n true, search requests for t  reverse chron t  l ne w ll  nclude an add  onal operator
   * so that search w ll not return t ets that are d rected at non-follo d users.
   */
  object D rectedAtNarrowcast ngV aSearchParam
      extends FSParam(
        na  = "reverse_chron_d rected_at_narrowcast ng_v a_search",
        default = false
      )

  /**
   * W n true, search requests for t  reverse chron t  l ne w ll request add  onal  tadata
   * from search and use t   tadata for post f lter ng.
   */
  object PostF lter ngBasedOnSearch tadataEnabledParam
      extends FSParam(
        na  = "reverse_chron_post_f lter ng_based_on_search_ tadata_enabled",
        default = true
      )
}
