package com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.models

 mport com.tw ter.addressbook.{thr ftscala => t}

case class QueryOpt on(
  onlyD scoverable nExpans on: Boolean,
  onlyConf r d nExpans on: Boolean,
  onlyD scoverable nResult: Boolean,
  onlyConf r d nResult: Boolean,
  fetchGlobalAp Na space: Boolean,
   sDebugRequest: Boolean,
  resolveEma ls: Boolean,
  resolvePhoneNumbers: Boolean) {
  def toThr ft: t.QueryOpt on = t.QueryOpt on(
    onlyD scoverable nExpans on,
    onlyConf r d nExpans on,
    onlyD scoverable nResult,
    onlyConf r d nResult,
    fetchGlobalAp Na space,
     sDebugRequest,
    resolveEma ls,
    resolvePhoneNumbers
  )
}
