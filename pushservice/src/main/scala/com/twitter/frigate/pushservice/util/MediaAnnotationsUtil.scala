package com.tw ter.fr gate.pushserv ce.ut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateDeta ls
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date

object  d aAnnotat onsUt l {

  val  d a dToCategoryMapp ng = Map("0" -> "0")

  val nud yCategory d = "0"
  val beautyCategory d = "0"
  val s nglePersonCategory d = "0"
  val sens  ve d aCategoryFeatureNa  =
    "t et. d aunderstand ng.t et_annotat ons.sens  ve_category_probab l  es"

  def update d aCategoryStats(
    cand dates: Seq[Cand dateDeta ls[PushCand date]]
  )(
     mpl c  statsRece ver: StatsRece ver
  ) = {

    val statScope = statsRece ver.scope(" d aStats")
    val f lteredCand dates = cand dates.f lter { cand date =>
      !cand date.cand date.sparseCont nuousFeatures
        .getOrElse(sens  ve d aCategoryFeatureNa , Map.empty[Str ng, Double]).conta ns(
          nud yCategory d)
    }

     f (f lteredCand dates. sEmpty)
      statScope.counter("emptyCand dateL stAfterNud yF lter"). ncr()
    else
      statScope.counter("nonEmptyCand dateL stAfterNud yF lter"). ncr()
    cand dates.foreach { cand date =>
      statScope.counter("totalCand dates"). ncr()
      val  d aFeature = cand date.cand date.sparseCont nuousFeatures
        .getOrElse(sens  ve d aCategoryFeatureNa , Map.empty[Str ng, Double])
       f ( d aFeature.nonEmpty) {
        val  d aCategoryByMaxScore =  d aFeature.maxBy(_._2)._1
        statScope
          .scope(" d aCategoryByMaxScore").counter( d a dToCategoryMapp ng
            .getOrElse( d aCategoryByMaxScore, "undef ned")). ncr()

         d aFeature.keys.map { feature =>
          statScope
            .scope(" d aCategory").counter( d a dToCategoryMapp ng
              .getOrElse(feature, "undef ned")). ncr()
        }
      }
    }
  }
}
