package com.tw ter.t  l neranker.para ters.recap

 mport com.tw ter.t  l nes.conf gap .dec der._
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.t  l nes.ut l.bounds.BoundsW hDefault

object RecapParams {
  val MaxFollo dUsers: BoundsW hDefault[ nt] = BoundsW hDefault[ nt](1, 3000, 1000)
  val MaxCountMult pl er: BoundsW hDefault[Double] = BoundsW hDefault[Double](0.1, 2.0, 2.0)
  val MaxRealGraphAndFollo dUsers: BoundsW hDefault[ nt] = BoundsW hDefault[ nt](0, 2000, 1000)
  val Probab l yRandomT et: BoundsW hDefault[Double] = BoundsW hDefault[Double](0.0, 1.0, 0.0)

  /**
   * Controls l m  on t  number of follo d users fetc d from SGS.
   *
   * T  spec f c default value below  s for blender-t  l nes par y.
   */
  object MaxFollo dUsersParam
      extends FSBoundedParam[ nt](
        na  = "recap_max_follo d_users",
        default = MaxFollo dUsers.default,
        m n = MaxFollo dUsers.bounds.m n nclus ve,
        max = MaxFollo dUsers.bounds.max nclus ve
      )

  /**
   * Controls l m  on t  number of h s for Earlyb rd.
   *   added   solely for backward compat b l y, to al gn w h recycled.
   * RecapS ce  s deprecated, but, t  param  s used by RecapAuthor s ce
   */
  object RelevanceOpt onsMaxH sToProcessParam
      extends FSBoundedParam[ nt](
        na  = "recap_relevance_opt ons_max_h s_to_process",
        default = 500,
        m n = 100,
        max = 20000
      )

  /**
   * Enables fetch ng author seedset from real graph users. Only used  f user follows >= 1000.
   *  f true, expands author seedset w h real graph users and recent follo d users.
   * Ot rw se, user seedset only  ncludes follo d users.
   */
  object EnableRealGraphUsersParam extends Param(false)

  /**
   * Only used  f EnableRealGraphUsersParam  s true and OnlyRealGraphUsersParam  s false.
   * Max mum number of real graph users and recent follo d users w n m x ng recent/real-graph users.
   */
  object MaxRealGraphAndFollo dUsersParam
      extends Param(MaxRealGraphAndFollo dUsers.default)
      w h Dec derValueConverter[ nt] {
    overr de def convert:  ntConverter[ nt] =
      OutputBound ntConverter(MaxRealGraphAndFollo dUsers.bounds)
  }

  /**
   * FS-controlled param to overr de t  MaxRealGraphAndFollo dUsersParam dec der value for exper  nts
   */
  object MaxRealGraphAndFollo dUsersFSOverr deParam
      extends FSBoundedParam[Opt on[ nt]](
        na  = "max_real_graph_and_follo rs_users_fs_overr de_param",
        default = None,
        m n = So (100),
        max = So (10000)
      )

  /**
   * Exper  ntal params for level ng t  play ng f eld bet en user folo es rece ved from
   * real-graph and follow-graph stores.
   * Author relevance scores returned by real-graph are currently be ng used for l ght-rank ng
   *  n-network t et cand dates.
   * Follow-graph store returns t  most recent follo es w hout any relevance scores
   *   are try ng to  mpute t  m ss ng scores by us ng aggregated stat st cs (m n, avg, p50, etc.)
   * of real-graph scores.
   */
  object  mputeRealGraphAuthor  ghtsParam
      extends FSParam(na  = " mpute_real_graph_author_  ghts", default = false)

  object  mputeRealGraphAuthor  ghtsPercent leParam
      extends FSBoundedParam[ nt](
        na  = " mpute_real_graph_author_  ghts_percent le",
        default = 50,
        m n = 0,
        max = 99)

  /**
   * Enable runn ng t  new p pel ne for recap author s ce
   */
  object EnableNewRecapAuthorP pel ne extends Param(false)

  /**
   * Fallback value for max mum number of search results,  f not spec f ed by query.maxCount
   */
  object DefaultMaxT etCount extends Param(200)

  /**
   *   mult ply maxCount (caller suppl ed value) by t  mult pl er and fetch those many
   * cand dates from search so that   are left w h suff c ent number of cand dates after
   * hydrat on and f lter ng.
   */
  object MaxCountMult pl erParam
      extends Param(MaxCountMult pl er.default)
      w h Dec derValueConverter[Double] {
    overr de def convert:  ntConverter[Double] =
      OutputBound ntConverter[Double](d v deDec derBy100 _, MaxCountMult pl er.bounds)
  }

  /**
   * Enables return all results from search  ndex.
   */
  object EnableReturnAllResultsParam
      extends FSParam(na  = "recap_enable_return_all_results", default = false)

  /**
   *  ncludes one or mult ple random t ets  n t  response.
   */
  object  ncludeRandomT etParam
      extends FSParam(na  = "recap_ nclude_random_t et", default = false)

  /**
   * One s ngle random t et (true) or tag t et as random w h g ven probab l y (false).
   */
  object  ncludeS ngleRandomT etParam
      extends FSParam(na  = "recap_ nclude_random_t et_s ngle", default = true)

  /**
   * Probab l y to tag a t et as random (w ll not be ranked).
   */
  object Probab l yRandomT etParam
      extends FSBoundedParam(
        na  = "recap_ nclude_random_t et_probab l y",
        default = Probab l yRandomT et.default,
        m n = Probab l yRandomT et.bounds.m n nclus ve,
        max = Probab l yRandomT et.bounds.max nclus ve)

  /**
   * Enable extra sort ng by score for search results.
   */
  object EnableExtraSort ng nSearchResultParam extends Param(true)

  /**
   * Enables semant c core, pengu n, and t etyp e content features  n recap s ce.
   */
  object EnableContentFeaturesHydrat onParam extends Param(true)

  /**
   * add  onally enables tokens w n hydrat ng content features.
   */
  object EnableTokens nContentFeaturesHydrat onParam
      extends FSParam(
        na  = "recap_enable_tokens_ n_content_features_hydrat on",
        default = false
      )

  /**
   * add  onally enables t et text w n hydrat ng content features.
   * T  only works  f EnableContentFeaturesHydrat onParam  s set to true
   */
  object EnableT etText nContentFeaturesHydrat onParam
      extends FSParam(
        na  = "recap_enable_t et_text_ n_content_features_hydrat on",
        default = false
      )

  /**
   * Enables hydrat ng  n-network  nReplyToT et features
   */
  object Enable nNetwork nReplyToT etFeaturesHydrat onParam
      extends FSParam(
        na  = "recap_enable_ n_network_ n_reply_to_t et_features_hydrat on",
        default = false
      )

  /**
   * Enables hydrat ng root t et of  n-network repl es and extended repl es
   */
  object EnableReplyRootT etHydrat onParam
      extends FSParam(
        na  = "recap_enable_reply_root_t et_hydrat on",
        default = false
      )

  /**
   * Enable sett ng t etTypes  n search quer es w h T etK ndOpt on  n RecapQuery
   */
  object EnableSett ngT etTypesW hT etK ndOpt on
      extends FSParam(
        na  = "recap_enable_sett ng_t et_types_w h_t et_k nd_opt on",
        default = false
      )

  /**
   * Enable relevance search, ot rw se recency search from earlyb rd.
   */
  object EnableRelevanceSearchParam
      extends FSParam(
        na  = "recap_enable_relevance_search",
        default = true
      )

  object EnableExpandedExtendedRepl esF lterParam
      extends FSParam(
        na  = "recap_enable_expanded_extended_repl es_f lter",
        default = false
      )

  /**
   * add  onally enables conversat onControl w n hydrat ng content features.
   * T  only works  f EnableContentFeaturesHydrat onParam  s set to true
   */
  object EnableConversat onControl nContentFeaturesHydrat onParam
      extends FSParam(
        na  = "conversat on_control_ n_content_features_hydrat on_recap_enable",
        default = false
      )

  object EnableT et d aHydrat onParam
      extends FSParam(
        na  = "t et_ d a_hydrat on_recap_enable",
        default = false
      )

  object EnableExcludeS ceT et dsQueryParam
      extends FSParam[Boolean](
        na  = "recap_exclude_s ce_t et_ ds_query_enable",
        default = false
      )
}
