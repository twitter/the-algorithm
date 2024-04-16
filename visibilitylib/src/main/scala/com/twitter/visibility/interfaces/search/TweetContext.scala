package com.tw ter.v s b l y. nterfaces.search

 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.models.SafetyLevel

case class T etContext(
  t et: T et,
  quotedT et: Opt on[T et],
  ret etS ceT et: Opt on[T et] = None,
  safetyLevel: SafetyLevel)
