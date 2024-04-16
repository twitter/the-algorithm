package com.tw ter.graph_feature_serv ce.worker.ut l

 mport com.tw ter.graph_feature_serv ce.thr ftscala.EdgeType
 mport com.tw ter.graph_feature_serv ce.thr ftscala.EdgeType._

sealed tra  GraphKey {

  def edgeType: EdgeType
}

sealed tra  Part alValueGraph extends GraphKey

/**
 * Follow Graphs
 */
object Follow ngPart alValueGraph extends Part alValueGraph {

  overr de def edgeType: EdgeType = Follow ng
}

object Follo dByPart alValueGraph extends Part alValueGraph {

  overr de def edgeType: EdgeType = Follo dBy
}

/**
 * Mutual Follow Graphs
 */
object MutualFollowPart alValueGraph extends Part alValueGraph {

  overr de def edgeType: EdgeType = MutualFollow
}
