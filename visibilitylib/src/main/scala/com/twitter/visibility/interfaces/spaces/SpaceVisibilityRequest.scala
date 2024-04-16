package com.tw ter.v s b l y. nterfaces.spaces

 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.V e rContext

case class SpaceV s b l yRequest(
  space d: Str ng,
  safetyLevel: SafetyLevel,
  v e rContext: V e rContext,
  spaceHostAndAdm nUser ds: Opt on[Seq[Long]])
