package com.tw ter.v s b l y. nterfaces.search

case class BatchSearchV s b l yResponse(
  v s b l yResults: Map[Long, Comb nedV s b l yResult],
  fa ledT et ds: Seq[Long])
