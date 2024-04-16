package com.tw ter.t  l neranker.para ters.recap

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.dec der.Dec derGateBu lder
 mport com.tw ter.servo.dec der.Dec derKeyNa 
 mport com.tw ter.t  l neranker.dec der.Dec derKey
 mport com.tw ter.t  l neranker.para ters.recap.RecapParams._
 mport com.tw ter.t  l neranker.para ters.ut l.Conf g lper
 mport com.tw ter.t  l nes.conf gap ._
 mport com.tw ter.servo.dec der.Dec derKeyEnum

object RecapProduct on {
  val dec derByParam: Map[Param[_], Dec derKeyEnum#Value] = Map[Param[_], Dec derKeyNa ](
    EnableRealGraphUsersParam -> Dec derKey.EnableRealGraphUsers,
    MaxRealGraphAndFollo dUsersParam -> Dec derKey.MaxRealGraphAndFollo dUsers,
    EnableContentFeaturesHydrat onParam -> Dec derKey.RecapEnableContentFeaturesHydrat on,
    MaxCountMult pl erParam -> Dec derKey.RecapMaxCountMult pl er,
    EnableNewRecapAuthorP pel ne -> Dec derKey.RecapAuthorEnableNewP pel ne,
    RecapParams.EnableExtraSort ng nSearchResultParam -> Dec derKey.RecapEnableExtraSort ng nResults
  )

  val  ntParams: Seq[MaxRealGraphAndFollo dUsersParam.type] = Seq(
    MaxRealGraphAndFollo dUsersParam
  )

  val doubleParams: Seq[MaxCountMult pl erParam.type] = Seq(
    MaxCountMult pl erParam
  )

  val boundedDoubleFeatureSw chParams: Seq[FSBoundedParam[Double]] = Seq(
    RecapParams.Probab l yRandomT etParam
  )

  val booleanParams: Seq[Param[Boolean]] = Seq(
    EnableRealGraphUsersParam,
    EnableContentFeaturesHydrat onParam,
    EnableNewRecapAuthorP pel ne,
    RecapParams.EnableExtraSort ng nSearchResultParam
  )

  val booleanFeatureSw chParams: Seq[FSParam[Boolean]] = Seq(
    RecapParams.EnableReturnAllResultsParam,
    RecapParams. ncludeRandomT etParam,
    RecapParams. ncludeS ngleRandomT etParam,
    RecapParams.Enable nNetwork nReplyToT etFeaturesHydrat onParam,
    RecapParams.EnableReplyRootT etHydrat onParam,
    RecapParams.EnableSett ngT etTypesW hT etK ndOpt on,
    RecapParams.EnableRelevanceSearchParam,
    EnableTokens nContentFeaturesHydrat onParam,
    EnableT etText nContentFeaturesHydrat onParam,
    EnableExpandedExtendedRepl esF lterParam,
    EnableConversat onControl nContentFeaturesHydrat onParam,
    EnableT et d aHydrat onParam,
     mputeRealGraphAuthor  ghtsParam,
    EnableExcludeS ceT et dsQueryParam
  )

  val bounded ntFeatureSw chParams: Seq[FSBoundedParam[ nt]] = Seq(
    RecapParams.MaxFollo dUsersParam,
     mputeRealGraphAuthor  ghtsPercent leParam,
    RecapParams.RelevanceOpt onsMaxH sToProcessParam
  )
}

class RecapProduct on(dec derGateBu lder: Dec derGateBu lder, statsRece ver: StatsRece ver) {

  val conf g lper: Conf g lper =
    new Conf g lper(RecapProduct on.dec derByParam, dec derGateBu lder)
  val  ntOverr des: Seq[Opt onalOverr de[ nt]] =
    conf g lper.createDec derBasedOverr des(RecapProduct on. ntParams)
  val opt onalBounded ntFeatureSw chOverr des: Seq[Opt onalOverr de[Opt on[ nt]]] =
    FeatureSw chOverr deUt l.getBoundedOpt onal ntOverr des(
      (
        MaxRealGraphAndFollo dUsersFSOverr deParam,
        "max_real_graph_and_follo rs_users_fs_overr de_def ned",
        "max_real_graph_and_follo rs_users_fs_overr de_value"
      )
    )
  val doubleOverr des: Seq[Opt onalOverr de[Double]] =
    conf g lper.createDec derBasedOverr des(RecapProduct on.doubleParams)
  val booleanOverr des: Seq[Opt onalOverr de[Boolean]] =
    conf g lper.createDec derBasedBooleanOverr des(RecapProduct on.booleanParams)
  val booleanFeatureSw chOverr des: Seq[Opt onalOverr de[Boolean]] =
    FeatureSw chOverr deUt l.getBooleanFSOverr des(RecapProduct on.booleanFeatureSw chParams: _*)
  val boundedDoubleFeatureSw chOverr des: Seq[Opt onalOverr de[Double]] =
    FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des(
      RecapProduct on.boundedDoubleFeatureSw chParams: _*)
  val bounded ntFeatureSw chOverr des: Seq[Opt onalOverr de[ nt]] =
    FeatureSw chOverr deUt l.getBounded ntFSOverr des(
      RecapProduct on.bounded ntFeatureSw chParams: _*)

  val conf g: BaseConf g = new BaseConf gBu lder()
    .set(
       ntOverr des: _*
    )
    .set(
      booleanOverr des: _*
    )
    .set(
      doubleOverr des: _*
    )
    .set(
      booleanFeatureSw chOverr des: _*
    )
    .set(
      bounded ntFeatureSw chOverr des: _*
    )
    .set(
      opt onalBounded ntFeatureSw chOverr des: _*
    )
    .set(
      boundedDoubleFeatureSw chOverr des: _*
    )
    .bu ld(RecapProduct on.getClass.getS mpleNa )
}
