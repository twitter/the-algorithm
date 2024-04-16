package com.tw ter.fr gate.pushserv ce.model. b s

 mport com.tw ter.fr gate.common.base.OutOfNetworkT etCand date
 mport com.tw ter.fr gate.common.base.Top cCand date
 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.common.rec_types.RecTypes._
 mport com.tw ter.fr gate.common.ut l.MrPushCopyObjects
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.ut l. nl neAct onUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.Push b sUt l. rgeModelValues
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.ut l.Future

tra  OutOfNetworkT et b s2HydratorForCand date extends T etCand date b s2Hydrator {
  self: PushCand date w h OutOfNetworkT etCand date w h Top cCand date w h T etAuthorDeta ls =>

  pr vate lazy val useNewOonCopyValue =
     f (target.params(PushFeatureSw chParams.EnableNewMROONCopyForPush)) {
      Map(
        "use_new_oon_copy" -> "true"
      )
    } else Map.empty[Str ng, Str ng]

  overr de lazy val t etDynam c nl neAct onsModelValues =
     f (target.params(PushFeatureSw chParams.EnableOONGenerated nl neAct ons)) {
      val act ons = target.params(PushFeatureSw chParams.OONT etDynam c nl neAct onsL st)
       nl neAct onUt l.getGeneratedT et nl neAct ons(target, statsRece ver, act ons)
    } else Map.empty[Str ng, Str ng]

  pr vate lazy val  btModelValues: Map[Str ng, Str ng] =
    Map(
      " s_t et" -> s"${!(hasPhoto || hasV deo)}",
      " s_photo" -> s"$hasPhoto",
      " s_v deo" -> s"$hasV deo"
    )

  pr vate lazy val launchV deos n m rs veExploreValue =
    Map(
      "launch_v deos_ n_ m rs ve_explore" -> s"${hasV deo && target.params(PushFeatureSw chParams.EnableLaunchV deos n m rs veExplore)}"
    )

  pr vate lazy val oonT etModelValues =
    useNewOonCopyValue ++  btModelValues ++ t etDynam c nl neAct onsModelValues ++ launchV deos n m rs veExploreValue

  lazy val useTop cCopyForMBCG b s = mrModel ngBasedTypes.conta ns(commonRecType) && target.params(
    PushFeatureSw chParams.EnableMrModel ngBasedCand datesTop cCopy)
  lazy val useTop cCopyForFrs b s = frsTypes.conta ns(commonRecType) && target.params(
    PushFeatureSw chParams.EnableFrsT etCand datesTop cCopy)
  lazy val useTop cCopyForTagspace b s = tagspaceTypes.conta ns(commonRecType) && target.params(
    PushFeatureSw chParams.EnableHashspaceCand datesTop cCopy)

  overr de lazy val modelNa : Str ng = {
     f (local zedUttEnt y. sDef ned &&
      (useTop cCopyForMBCG b s || useTop cCopyForFrs b s || useTop cCopyForTagspace b s)) {
      MrPushCopyObjects.Top cT et. b sPushModelNa  // uses top c copy
    } else super.modelNa 
  }

  lazy val exploreV deoParams: Map[Str ng, Str ng] = {
     f (self.commonRecType == CommonRecom ndat onType.ExploreV deoT et) {
      Map(
        " s_explore_v deo" -> "true"
      )
    } else Map.empty[Str ng, Str ng]
  }

  overr de lazy val customF eldsMapFut: Future[Map[Str ng, Str ng]] =
     rgeModelValues(super.customF eldsMapFut, exploreV deoParams)

  overr de lazy val t etModelValues: Future[Map[Str ng, Str ng]] =
     f (local zedUttEnt y. sDef ned &&
      (useTop cCopyForMBCG b s || useTop cCopyForFrs b s || useTop cCopyForTagspace b s)) {
      lazy val top cT etModelValues: Map[Str ng, Str ng] =
        Map("top c_na " -> s"${local zedUttEnt y.get.local zedNa ForD splay}")
      for {
        superModelValues <- super.t etModelValues
        t et nl neModelValue <- t et nl neAct onModelValue
      } y eld {
        superModelValues ++ top cT etModelValues ++ t et nl neModelValue
      }
    } else {
      for {
        superModelValues <- super.t etModelValues
        t et nl neModelValues <- t et nl neAct onModelValue
      } y eld {
        superModelValues ++  d aModelValue ++ oonT etModelValues ++ t et nl neModelValues ++  nl neV deo d aMap
      }
    }
}
