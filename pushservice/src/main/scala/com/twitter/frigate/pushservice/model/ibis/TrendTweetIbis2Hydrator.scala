package com.tw ter.fr gate.pushserv ce.model. b s

 mport com.tw ter.fr gate.common.base.TrendT etCand date
 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date

tra  TrendT et b s2Hydrator extends T etCand date b s2Hydrator {
  self: PushCand date w h TrendT etCand date w h T etAuthorDeta ls =>

  lazy val trendNa ModelValue = Map("trend_na " -> trendNa )

  overr de lazy val t etModelValues = for {
    t etValues <- super.t etModelValues
     nl neAct onValues <- t et nl neAct onModelValue
  } y eld t etValues ++  nl neAct onValues ++ trendNa ModelValue
}
