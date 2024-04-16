package com.tw ter.v s b l y. nterfaces.t ets

 mport com.tw ter.v s b l y.models.UserUnava lableStateEnum
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.V e rContext

case class UserUnava lableStateV s b l yRequest(
  safetyLevel: SafetyLevel,
  t et d: Long,
  v e rContext: V e rContext,
  userUnava lableState: UserUnava lableStateEnum,
   sRet et: Boolean,
   s nnerQuotedT et: Boolean,
)
