package com.X.home_mixer.store

import com.X.bijection.Injection
import com.X.home_mixer.store.ManhattanRealGraphKVDescriptor._
import com.X.stitch.Stitch
import com.X.storage.client.manhattan.bijections.Bijections
import com.X.storage.client.manhattan.bijections.Bijections.BinaryScalaInjection
import com.X.storage.client.manhattan.kv.ManhattanKVEndpoint
import com.X.storage.client.manhattan.kv.impl.ReadOnlyKeyDescriptor
import com.X.storage.client.manhattan.kv.impl.ValueDescriptor
import com.X.storehaus.ReadableStore
import com.X.util.Future
import com.X.wtf.candidate.{thriftscala => wtf}

object ManhattanRealGraphKVDescriptor {
  implicit val byteArray2Buf = Bijections.BytesBijection

  val realGraphDatasetName = "real_graph_scores_in"
  val keyInjection = Injection.connect[Long, Array[Byte]].andThen(Bijections.BytesInjection)
  val keyDesc = ReadOnlyKeyDescriptor(keyInjection)
  val valueDesc = ValueDescriptor(BinaryScalaInjection(wtf.CandidateSeq))
  val realGraphDatasetKey = keyDesc.withDataset(realGraphDatasetName)
}

/**
 * Hydrates real graph in network scores for a viewer
 */
class RealGraphInNetworkScoresStore(manhattanKVEndpoint: ManhattanKVEndpoint)
    extends ReadableStore[Long, Seq[wtf.Candidate]] {

  override def get(viewerId: Long): Future[Option[Seq[wtf.Candidate]]] = Stitch
    .run(manhattanKVEndpoint.get(realGraphDatasetKey.withPkey(viewerId), valueDesc))
    .map(_.map(mhResponse => mhResponse.contents.candidates))
}
