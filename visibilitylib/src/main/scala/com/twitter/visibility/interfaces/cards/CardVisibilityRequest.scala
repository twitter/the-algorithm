package com.tw ter.v s b l y. nterfaces.cards

 mport com.tw ter.t etyp e.{thr ftscala => t etyp ethr ft}
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.V e rContext

case class CardV s b l yRequest(
  cardUr : Str ng,
  author d: Opt on[Long],
  t etOpt: Opt on[t etyp ethr ft.T et],
   sPollCardType: Boolean,
  safetyLevel: SafetyLevel,
  v e rContext: V e rContext)
