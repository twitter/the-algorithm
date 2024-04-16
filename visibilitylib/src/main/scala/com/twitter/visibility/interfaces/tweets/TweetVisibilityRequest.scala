package com.tw ter.v s b l y. nterfaces.t ets

 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.V e rContext

case class T etV s b l yRequest(
  t et: T et,
  safetyLevel: SafetyLevel,
  v e rContext: V e rContext,
   s nnerQuotedT et: Boolean,
   sRet et: Boolean,
  hydrateConversat onControl: Boolean = false,
   sS ceT et: Boolean = false)
