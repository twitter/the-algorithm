package com.tw ter.v s b l y. nterfaces.dms

 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.V e rContext

case class DmConversat onV s b l yRequest(
  dmConversat on d: Str ng,
  safetyLevel: SafetyLevel,
  v e rContext: V e rContext)
