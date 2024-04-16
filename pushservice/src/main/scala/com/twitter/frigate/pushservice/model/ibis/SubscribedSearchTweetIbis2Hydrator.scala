package com.tw ter.fr gate.pushserv ce.model. b s

 mport com.tw ter.fr gate.pushserv ce.model.Subscr bedSearchT etPushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.ut l. nl neAct onUt l
 mport com.tw ter.ut l.Future

tra  Subscr bedSearchT et b s2Hydrator extends T etCand date b s2Hydrator {
  self: Subscr bedSearchT etPushCand date =>

  overr de lazy val t etDynam c nl neAct onsModelValues = {
     f (target.params(PushFeatureSw chParams.EnableOONGenerated nl neAct ons)) {
      val act ons = target.params(PushFeatureSw chParams.T etDynam c nl neAct onsL st)
       nl neAct onUt l.getGeneratedT et nl neAct ons(target, statsRece ver, act ons)
    } else Map.empty[Str ng, Str ng]
  }

  pr vate lazy val searchTermValue: Map[Str ng, Str ng] =
    Map(
      "search_term" -> searchTerm,
      "search_url" -> pushLand ngUrl
    )

  pr vate lazy val searchModelValues = searchTermValue ++ t etDynam c nl neAct onsModelValues

  overr de lazy val t etModelValues: Future[Map[Str ng, Str ng]] =
    for {
      superModelValues <- super.t etModelValues
      t et nl neModelValues <- t et nl neAct onModelValue
    } y eld {
      superModelValues ++  d aModelValue ++ searchModelValues ++ t et nl neModelValues ++  nl neV deo d aMap
    }
}
