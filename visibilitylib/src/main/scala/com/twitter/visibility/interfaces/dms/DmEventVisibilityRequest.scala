package com.tw ter.v s b l y. nterfaces.dms

 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.V e rContext

case class DmEventV s b l yRequest(
  dmEvent d: Long,
  safetyLevel: SafetyLevel,
  v e rContext: V e rContext)
