package com.tw ter.follow_recom ndat ons.common.cl ents.graph_feature_serv ce

 mport com.tw ter.follow_recom ndat ons.common.models.FollowProof
 mport com.tw ter.graph_feature_serv ce.thr ftscala.PresetFeatureTypes.WtfTwoHop
 mport com.tw ter.graph_feature_serv ce.thr ftscala.EdgeType
 mport com.tw ter.graph_feature_serv ce.thr ftscala.Gfs ntersect onResponse
 mport com.tw ter.graph_feature_serv ce.thr ftscala.GfsPreset ntersect onRequest
 mport com.tw ter.graph_feature_serv ce.thr ftscala.{Server => GraphFeatureServ ce}
 mport com.tw ter.st ch.St ch
 mport javax. nject.{ nject, S ngleton}

@S ngleton
class GraphFeatureServ ceCl ent @ nject() (
  graphFeatureServ ce: GraphFeatureServ ce. thodPerEndpo nt) {

   mport GraphFeatureServ ceCl ent._
  def get ntersect ons(
    user d: Long,
    cand date ds: Seq[Long],
    num ntersect on ds:  nt
  ): St ch[Map[Long, FollowProof]] = {
    St ch
      .callFuture(
        graphFeatureServ ce.getPreset ntersect on(
          GfsPreset ntersect onRequest(user d, cand date ds, WtfTwoHop, So (num ntersect on ds))
        )
      ).map {
        case Gfs ntersect onResponse(gfs ntersect onResults) =>
          (for {
            cand date d <- cand date ds
            gfs ntersect onResultForCand date =
              gfs ntersect onResults.f lter(_.cand dateUser d == cand date d)
            followProof <- for {
              result <- gfs ntersect onResultForCand date
               ntersect on <- result. ntersect onValues
               f leftEdgeTypes.conta ns( ntersect on.featureType.leftEdgeType)
               f r ghtEdgeTypes.conta ns( ntersect on.featureType.r ghtEdgeType)
               ntersect on ds <-  ntersect on. ntersect on ds.toSeq
            } y eld FollowProof( ntersect on ds,  ntersect on.count.getOrElse(0))
          } y eld {
            cand date d -> followProof
          }).toMap
      }
  }
}

object GraphFeatureServ ceCl ent {
  val leftEdgeTypes: Set[EdgeType] = Set(EdgeType.Follow ng)
  val r ghtEdgeTypes: Set[EdgeType] = Set(EdgeType.Follo dBy)
}
