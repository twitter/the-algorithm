package com.tw ter.v s b l y. nterfaces.common.t ets

 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabelMap
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._

object StratoSafetyLabelMapFetc r {
  val column = "v s b l y/baseT etSafetyLabelMap"

  def apply(cl ent: StratoCl ent): SafetyLabelMapFetc rType = {
    val fetc r: Fetc r[Long, Un , SafetyLabelMap] =
      cl ent.fetc r[Long, SafetyLabelMap](column)

    t et d => fetc r.fetch(t et d).map(_.v)
  }
}
