package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala. emType

object  emTypeF lterPred cates {
  pr vate val T et emTypes = Set[ emType]( emType.T et,  emType.QuotedT et)
  pr vate val Top c emTypes = Set[ emType]( emType.T et,  emType.QuotedT et,  emType.Top c)
  pr vate val Prof le emTypes = Set[ emType]( emType.User)
  pr vate val Typea adResult emTypes = Set[ emType]( emType.Search,  emType.User)
  pr vate val SearchResultsPageFeedbackSubm  emTypes =
    Set[ emType]( emType.T et,  emType.RelevancePrompt)

  /**
   *  DDG lambda  tr cs count T ets based on t  ` emType`
   *  Reference code - https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/src/scala/com/tw ter/exper  nts/lambda/shared/T  l nes.scala?L156
   *  S nce enums `PROMOTED_TWEET` and `POPULAR_TWEET` are deprecated  n t  follow ng thr ft
   *  https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/src/thr ft/com/tw ter/cl entapp/gen/cl ent_app.thr ft?L131
   *  UUA f lters two types of T ets only: `TWEET` and `QUOTED_TWEET`
   */
  def  s emTypeT et( emTypeOpt: Opt on[ emType]): Boolean =
     emTypeOpt.ex sts( emType => T et emTypes.conta ns( emType))

  def  s emTypeTop c( emTypeOpt: Opt on[ emType]): Boolean =
     emTypeOpt.ex sts( emType => Top c emTypes.conta ns( emType))

  def  s emTypeProf le( emTypeOpt: Opt on[ emType]): Boolean =
     emTypeOpt.ex sts( emType => Prof le emTypes.conta ns( emType))

  def  s emTypeTypea adResult( emTypeOpt: Opt on[ emType]): Boolean =
     emTypeOpt.ex sts( emType => Typea adResult emTypes.conta ns( emType))

  def  s emTypeForSearchResultsPageFeedbackSubm ( emTypeOpt: Opt on[ emType]): Boolean =
     emTypeOpt.ex sts( emType => SearchResultsPageFeedbackSubm  emTypes.conta ns( emType))

  /**
   * Always return true. Use t  w n t re  s no need to f lter based on ` em_type` and all
   * values of ` em_type` are acceptable.
   */
  def  gnore emType( emTypeOpt: Opt on[ emType]): Boolean = true
}
