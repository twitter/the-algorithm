package com.tw ter.v s b l y.bu lder.t ets

 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.models.V e rContext

class Commun yT etFeaturesPart  oned(
  a: Commun yT etFeatures,
  b: Commun yT etFeatures,
  bEnabled: Gate[Un ],
) extends Commun yT etFeatures {
  overr de def forT et(
    t et: T et,
    v e rContext: V e rContext
  ): FeatureMapBu lder => FeatureMapBu lder =
    bEnabled.p ck(
      b.forT et(t et, v e rContext),
      a.forT et(t et, v e rContext),
    )

  overr de def forT etOnly(t et: T et): FeatureMapBu lder => FeatureMapBu lder = bEnabled.p ck(
    b.forT etOnly(t et),
    a.forT etOnly(t et),
  )
}
