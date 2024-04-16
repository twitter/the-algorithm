package com.tw ter.v s b l y. nterfaces.common

 mport com.tw ter.search.blender.serv ces.strato.UserSearchSafetySett ngs
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabel
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabelMap
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabelType
 mport com.tw ter.st ch.St ch

package object t ets {
  type SafetyLabelFetc rType = (Long, SafetyLabelType) => St ch[Opt on[SafetyLabel]]
  type SafetyLabelMapFetc rType = Long => St ch[Opt on[SafetyLabelMap]]
  type UserSearchSafetySett ngsFetc rType = Long => St ch[UserSearchSafetySett ngs]
}
