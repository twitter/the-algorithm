package com.tw ter.graph_feature_serv ce.worker.ut l

 mport com.tw ter.graph_feature_serv ce.thr ftscala.EdgeType

sealed tra  GfsQuery {
  def edgeType: EdgeType
  def user d: Long
}

/**
 * Search for edges for any users to users  n local part  on.
 */
case class ToPart alQuery(edgeType: EdgeType, user d: Long) extends GfsQuery

