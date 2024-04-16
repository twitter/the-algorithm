package com.tw ter.product_m xer.core.funct onal_component.conf gap .reg stry

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.servo.dec der.Dec derGateBu lder
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Opt onalOverr de
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derUt ls

tra  ParamConf gBu lder { paramConf g: ParamConf g =>

  /** Bu lds a Seq of [[Opt onalOverr de]]s based on t  [[paramConf g]] */
  def bu ld(
    dec derGateBu lder: Dec derGateBu lder,
    statsRece ver: StatsRece ver
  ): Seq[Opt onalOverr de[_]] = {
    val logger = Logger(classOf[ParamConf g])

    Dec derUt ls.getBooleanDec derOverr des(dec derGateBu lder, booleanDec derOverr des: _*) ++
      FeatureSw chOverr deUt l.getBooleanFSOverr des(booleanFSOverr des: _*) ++
      FeatureSw chOverr deUt l.getOpt onalBooleanOverr des(opt onalBooleanOverr des: _*) ++
      FeatureSw chOverr deUt l.getEnumFSOverr des(
        statsRece ver.scope("enumConvers on"),
        logger,
        enumFSOverr des: _*) ++
      FeatureSw chOverr deUt l.getEnumSeqFSOverr des(
        statsRece ver.scope("enumSeqConvers on"),
        logger,
        enumSeqFSOverr des: _*) ++
      FeatureSw chOverr deUt l.getBoundedDurat onFSOverr des(boundedDurat onFSOverr des: _*) ++
      FeatureSw chOverr deUt l.getBounded ntFSOverr des(bounded ntFSOverr des: _*) ++
      FeatureSw chOverr deUt l.getBoundedOpt onal ntOverr des(boundedOpt onal ntOverr des: _*) ++
      FeatureSw chOverr deUt l.get ntSeqFSOverr des( ntSeqFSOverr des: _*) ++
      FeatureSw chOverr deUt l.getBoundedLongFSOverr des(boundedLongFSOverr des: _*) ++
      FeatureSw chOverr deUt l.getBoundedOpt onalLongOverr des(boundedOpt onalLongOverr des: _*) ++
      FeatureSw chOverr deUt l.getLongSeqFSOverr des(longSeqFSOverr des: _*) ++
      FeatureSw chOverr deUt l.getLongSetFSOverr des(longSetFSOverr des: _*) ++
      FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des(boundedDoubleFSOverr des: _*) ++
      FeatureSw chOverr deUt l.getBoundedOpt onalDoubleOverr des(
        boundedOpt onalDoubleOverr des: _*) ++
      FeatureSw chOverr deUt l.getDoubleSeqFSOverr des(doubleSeqFSOverr des: _*) ++
      FeatureSw chOverr deUt l.getStr ngFSOverr des(str ngFSOverr des: _*) ++
      FeatureSw chOverr deUt l.getStr ngSeqFSOverr des(str ngSeqFSOverr des: _*) ++
      FeatureSw chOverr deUt l.getOpt onalStr ngOverr des(opt onalStr ngOverr des: _*) ++
      gatedOverr des.flatMap {
        case (fsNa , overr des) => FeatureSw chOverr deUt l.gatedOverr des(fsNa , overr des: _*)
      }.toSeq
  }
}
