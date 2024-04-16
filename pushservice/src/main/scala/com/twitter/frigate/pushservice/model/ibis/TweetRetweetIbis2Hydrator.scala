package com.tw ter.fr gate.pushserv ce.model. b s

 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.common.base.T etRet etCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.ut l.Push b sUt l. rgeModelValues

 mport com.tw ter.ut l.Future

tra  T etRet etCand date b s2Hydrator
    extends T etCand date b s2Hydrator
    w h RankedSoc alContext b s2Hydrator {
  self: PushCand date w h T etRet etCand date w h T etAuthorDeta ls =>

  overr de lazy val t etModelValues: Future[Map[Str ng, Str ng]] =
    for {
      soc alContextModelValues <- soc alContextModelValues
      superModelValues <- super.t etModelValues
      t et nl neModelValues <- t et nl neAct onModelValue
    } y eld {
      superModelValues ++  d aModelValue ++ ot rModelValues ++ soc alContextModelValues ++ t et nl neModelValues ++  nl neV deo d aMap
    }

  lazy val soc alContextForRet etMap: Map[Str ng, Str ng] =
     f (self.target.params(PushFeatureSw chParams.EnableSoc alContextForRet et)) {
      Map("enable_soc al_context_ret et" -> "true")
    } else Map.empty[Str ng, Str ng]

  overr de lazy val customF eldsMapFut: Future[Map[Str ng, Str ng]] =
     rgeModelValues(super.customF eldsMapFut, soc alContextForRet etMap)
}
