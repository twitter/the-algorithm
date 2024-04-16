package com.tw ter.fr gate.pushserv ce.take.pred cates.cand date_map

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model._
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType._
 mport com.tw ter. rm .pred cate.Na dPred cate

object Cand datePred catesMap {

  def apply(
     mpl c  conf g: Conf g
  ): Map[CommonRecom ndat onType, L st[Na dPred cate[_ <: PushCand date]]] = {

    val trendT etCand datePred cates = TrendT etPred cates(conf g).pred cates
    val tr pT etCand datePred cates = Tr pT etCand datePred cates(conf g).pred cates
    val f1T etCand datePred cates = F1T etCand datePred cates(conf g).pred cates
    val oonT etCand datePred cates = OutOfNetworkT etCand datePred cates(conf g).pred cates
    val t etAct onCand datePred cates = T etAct onCand datePred cates(conf g).pred cates
    val top cProofT etCand datePred cates = Top cProofT etCand datePred cates(conf g).pred cates
    val addressBookPushPred cates = AddressBookPushCand datePred cates(conf g).pred cates
    val completeOnboard ngPushPred cates = CompleteOnboard ngPushCand datePred cates(
      conf g).pred cates
    val popGeoT etCand datePred cate = PopGeoT etCand datePred cates(conf g).pred cates
    val topT et mpress onsCand datePred cates = TopT et mpress onsPushCand datePred cates(
      conf g).pred cates
    val l stCand datePred cates = L stRecom ndat onPred cates(conf g).pred cates
    val subscr bedSearchT etCand datePred cates = Subscr bedSearchT etCand datePred cates(
      conf g).pred cates

    Map(
      F1F rstdegreeT et -> f1T etCand datePred cates,
      F1F rstdegreePhoto -> f1T etCand datePred cates,
      F1F rstdegreeV deo -> f1T etCand datePred cates,
      Elast cT  l neT et -> oonT etCand datePred cates,
      Elast cT  l nePhoto -> oonT etCand datePred cates,
      Elast cT  l neV deo -> oonT etCand datePred cates,
      Tw stlyT et -> oonT etCand datePred cates,
      Tw stlyPhoto -> oonT etCand datePred cates,
      Tw stlyV deo -> oonT etCand datePred cates,
      ExploreV deoT et -> oonT etCand datePred cates,
      User nterest nT et -> oonT etCand datePred cates,
      User nterest nPhoto -> oonT etCand datePred cates,
      User nterest nV deo -> oonT etCand datePred cates,
      PastEma lEngage ntT et -> oonT etCand datePred cates,
      PastEma lEngage ntPhoto -> oonT etCand datePred cates,
      PastEma lEngage ntV deo -> oonT etCand datePred cates,
      TagSpaceT et -> oonT etCand datePred cates,
      Twh nT et -> oonT etCand datePred cates,
      FrsT et -> oonT etCand datePred cates,
      MrModel ngBasedT et -> oonT etCand datePred cates,
      TrendT et -> trendT etCand datePred cates,
      ReverseAddressbookT et -> oonT etCand datePred cates,
      ForwardAddressbookT et -> oonT etCand datePred cates,
      Tr pGeoT et -> oonT etCand datePred cates,
      Tr pHqT et -> tr pT etCand datePred cates,
      Detop cT et -> oonT etCand datePred cates,
      CrowdSearchT et -> oonT etCand datePred cates,
      T etFavor e -> t etAct onCand datePred cates,
      T etFavor ePhoto -> t etAct onCand datePred cates,
      T etFavor eV deo -> t etAct onCand datePred cates,
      T etRet et -> t etAct onCand datePred cates,
      T etRet etPhoto -> t etAct onCand datePred cates,
      T etRet etV deo -> t etAct onCand datePred cates,
      Top cProofT et -> top cProofT etCand datePred cates,
      Subscr bedSearch -> subscr bedSearchT etCand datePred cates,
      AddressBookUploadPush -> addressBookPushPred cates,
      CompleteOnboard ngPush -> completeOnboard ngPushPred cates,
      L st -> l stCand datePred cates,
      GeoPopT et -> popGeoT etCand datePred cate,
      T et mpress ons -> topT et mpress onsCand datePred cates
    )
  }
}
