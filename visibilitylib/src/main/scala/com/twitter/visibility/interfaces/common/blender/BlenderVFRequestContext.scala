package com.tw ter.v s b l y. nterfaces.common.blender

 mport com.tw ter.search.blender.serv ces.strato.UserSearchSafetySett ngs
 mport com.tw ter.search.common.constants.thr ftscala.Thr ftQueryS ce

case class BlenderVFRequestContext(
  resultsPageNumber:  nt,
  cand dateCount:  nt,
  queryS ceOpt on: Opt on[Thr ftQueryS ce],
  userSearchSafetySett ngs: UserSearchSafetySett ngs,
  queryHasUser: Boolean = false) {

  def t (
    resultsPageNumber:  nt,
    cand dateCount:  nt,
    queryS ceOpt on: Opt on[Thr ftQueryS ce],
    userSearchSafetySett ngs: UserSearchSafetySett ngs
  ) = t (resultsPageNumber, cand dateCount, queryS ceOpt on, userSearchSafetySett ngs, false)
}
