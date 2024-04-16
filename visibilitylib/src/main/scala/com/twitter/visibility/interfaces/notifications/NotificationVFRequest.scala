package com.tw ter.v s b l y. nterfaces.not f cat ons

 mport com.tw ter.v s b l y.models.Content d
 mport com.tw ter.v s b l y.models.SafetyLevel

case class Not f cat onVFRequest(
  rec p ent d: Long,
  subject: Content d.User d,
  safetyLevel: SafetyLevel)
