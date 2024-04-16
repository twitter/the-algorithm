package com.tw ter.v s b l y. nterfaces.search

 mport com.tw ter.v s b l y. nterfaces.common.search.SearchVFRequestContext
 mport com.tw ter.v s b l y.models.V e rContext

case class BatchSearchV s b l yRequest(
  t etContexts: Seq[T etContext],
  v e rContext: V e rContext,
  searchVFRequestContext: SearchVFRequestContext)
