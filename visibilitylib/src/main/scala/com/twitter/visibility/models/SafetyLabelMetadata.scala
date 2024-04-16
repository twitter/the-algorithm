package com.tw ter.v s b l y.models

 mport com.tw ter.guano.commons.thr ftscala.Pol cy nV olat on
 mport com.tw ter.spam.rtf.{thr ftscala => s}

case class SafetyLabel tadata(
  pol cy nV olat on: Opt on[Pol cy nV olat on] = None,
  pol cyUrl: Opt on[Str ng] = None) {

  def toThr ft: s.SafetyLabel tadata = {
    s.SafetyLabel tadata(
      pol cy nV olat on,
      pol cyUrl
    )
  }
}

object SafetyLabel tadata {
  def fromThr ft( tadata: s.SafetyLabel tadata): SafetyLabel tadata = {
    SafetyLabel tadata(
       tadata.pol cy nV olat on,
       tadata.pol cyUrl
    )
  }
}
