package com.tw ter.cr_m xer.model

 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.recos.recos_common.thr ftscala.Soc alProofType

/***
 * B nd a t et d w h a raw score and soc al proofs by type
 */
case class T etW hScoreAndSoc alProof(
  t et d: T et d,
  score: Double,
  soc alProofByType: Map[Soc alProofType, Seq[Long]])
