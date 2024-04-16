package com.tw ter.t  l neranker.common

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.t  l neranker.v s b l y.FollowGraphDataProv der
 mport com.tw ter.ut l.Future

class FollowGraphDataTransform(
  followGraphDataProv der: FollowGraphDataProv der,
  maxFollo dUsersProv der: DependencyProv der[ nt])
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {

  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {

    val followGraphData = followGraphDataProv der.getAsync(
      envelope.query.user d,
      maxFollo dUsersProv der(envelope.query)
    )

    Future.value(envelope.copy(followGraphData = followGraphData))
  }
}
