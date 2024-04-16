package com.tw ter.follow_recom ndat ons.common.transforms.  ghted_sampl ng

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Sampl ngTransformFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val  ntFSParams: Seq[FSBoundedParam[ nt]] = Seq(Sampl ngTransformParams.TopKF xed)

  overr de val doubleFSParams: Seq[FSBoundedParam[Double]] = Seq(
    Sampl ngTransformParams.Mult pl cat veFactor)

  overr de val booleanFSParams: Seq[FSParam[Boolean]] = Seq(
    Sampl ngTransformParams.Scr beRank ng nfo nSampl ngTransform)
}
