package com.ExTwitter.home_mixer.store

import com.ExTwitter.bijection.Injection
import com.ExTwitter.home_mixer.store.ManhattanRealGraphKVDescriptor._
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.storage.client.manhattan.bijections.Bijections
import com.ExTwitter.storage.client.manhattan.bijections.Bijections.BinaryScalaInjection
import com.ExTwitter.storage.client.manhattan.kv.ManhattanKVEndpoint
import com.ExTwitter.storage.client.manhattan.kv.impl.ReadOnlyKeyDescriptor
import com.ExTwitter.storage.client.manhattan.kv.impl.ValueDescriptor
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.util.Future
import com.ExTwitter.wtf.candidate.{thriftscala => wtf}

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
