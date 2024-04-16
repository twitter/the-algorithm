package com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.decay

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.conf gap .Stat cParam
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.Cand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on

object DecayScore extends Feature[Un versalNoun[Long], Double]

/**
 * Hydrates snowflake  D cand dates w h a decay score:
 *
 *    s us ng exponent al decay formula to calculate t  score
 * exp(k * age)
 * w re k = ln(0.5) / half-l fe
 *
 *  re  s an example for half-l fe = 1 day
 * For t  brand new t et   w ll be exp((ln(0.5)/1)*0) = 1
 * For t  t et wh ch was created 1 day ago   w ll be exp((ln(0.5)/1)*1) = 0.5
 * For t  t et wh ch was created 10 day ago   w ll be exp((ln(0.5)/1)*10) = 0.00097
 *
 * Reference: https://www.cuemath.com/exponent al-decay-formula/
 *
 * @note T  penal zes but does not f lter out t  cand date, so "stale" cand dates can st ll appear.
 */
case class DecayCand dateFeatureHydrator[Cand date <: Un versalNoun[Long]](
  halfL fe: Param[Durat on] = Stat cParam[Durat on](2.days),
  resultFeature: Feature[Un versalNoun[Long], Double] = DecayScore)
    extends Cand dateFeatureHydrator[P pel neQuery, Cand date] {

  overr de val features: Set[Feature[_, _]] = Set(resultFeature)

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("Decay")

  overr de def apply(
    query: P pel neQuery,
    cand date: Cand date,
    ex st ngFeatures: FeatureMap
  ): St ch[FeatureMap] = {
    val halfL fe nM ll s = query.params(halfL fe). nM ll s

    val creat onT   = Snowflake d.t  From d(cand date. d)
    val age nM ll s = creat onT  .unt lNow. nM ll seconds

    //    s us ng a exponent al decay formula:  e^(k * t etAge)
    // w re k = ln(0.5) / half-l fe
    val k = math.log(0.5D) / halfL fe nM ll s
    val decayScore = math.exp(k * age nM ll s)

    St ch.value(
      FeatureMapBu lder()
        .add(resultFeature, decayScore)
        .bu ld())
  }
}
