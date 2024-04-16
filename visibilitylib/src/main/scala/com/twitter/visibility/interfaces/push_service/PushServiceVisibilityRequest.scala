package com.tw ter.v s b l y. nterfaces.push_serv ce

 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.V e rContext

case class PushServ ceV s b l yRequest(
  t et: T et,
  author: User,
  v e rContext: V e rContext,
  safetyLevel: SafetyLevel,
  s ceT et: Opt on[T et] = None,
  quotedT et: Opt on[T et] = None,
   sRet et: Boolean = false,
   s nnerQuotedT et: Boolean = false,
   sS ceT et: Boolean = false,
   sOutOfNetworkT et: Boolean = true,
)
