package com.tw ter.v s b l y. nterfaces.common.t ets

 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabel
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabelType
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._
 mport com.tw ter.ut l. mo ze

object StratoSafetyLabelFetc r {
  def apply(cl ent: StratoCl ent): SafetyLabelFetc rType = {
    val getFetc r: SafetyLabelType => Fetc r[Long, Un , SafetyLabel] =
       mo ze((safetyLabelType: SafetyLabelType) =>
        cl ent.fetc r[Long, SafetyLabel](s"v s b l y/${safetyLabelType.na }.T et"))

    (t et d, safetyLabelType) => getFetc r(safetyLabelType).fetch(t et d).map(_.v)
  }
}
