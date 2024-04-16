package com.tw ter.cr_m xer.model

 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.s mclusters_v2.common.T et d

/***
 * B nd a t et d w h a raw score generated from one s ngle S m lar y Eng ne
 * @param s m lar yEng neType, wh ch underly ng top c s ce t  top c t et  s from
 */
case class Top cT etW hScore(
  t et d: T et d,
  score: Double,
  s m lar yEng neType: S m lar yEng neType)
