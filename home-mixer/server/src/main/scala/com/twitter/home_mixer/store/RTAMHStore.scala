package com.twitter.home_mixer.store

import com.twitter.bijection.Injection
import com.twitter.bijection.scrooge.BinaryScalaCodec
import com.twitter.home_mixer.store.RTAManhattanRealGraphKVDescriptor._
import com.twitter.io.Buf
import com.twitter.ml.api.DataRecord
import com.twitter.stitch.Stitch
import com.twitter.storage.client.manhattan.bijections.Bijections
import com.twitter.storage.client.manhattan.kv.ManhattanKVEndpoint
import com.twitter.storage.client.manhattan.kv.impl.ReadOnlyKeyDescriptor
import com.twitter.storage.client.manhattan.kv.impl.ValueDescriptor
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future
import com.twitter.ml.api.{thriftscala => mlThrift}
import com.twitter.timelines.realtime_aggregates.{thriftscala => thrift}
import com.twitter.ml.api.util.ScalaToJavaDataRecordConversions._

object RTAManhattanRealGraphKVDescriptor {
  val datasetName = "timelines_real_time_aggregates_0"

  val keyInjection: Injection[thrift.AggregationKey, Buf] =
    BinaryScalaCodec(thrift.AggregationKey).andThen(Bijections.byteArray2Buf)
  val keyDesc = ReadOnlyKeyDescriptor(keyInjection)
  val datasetKey = keyDesc.withDataset(datasetName)
  val valueInjection = BinaryScalaCodec(mlThrift.DataRecord).andThen(Bijections.byteArray2Buf)
  val valueDesc = ValueDescriptor(valueInjection)
}

/**
 *
 */
class RTAMHStore(manhattanKVEndpoint: ManhattanKVEndpoint)
    extends ReadableStore[thrift.AggregationKey, DataRecord] {

  override def get(key: thrift.AggregationKey): Future[Option[DataRecord]] = Stitch
    .run(manhattanKVEndpoint.get(datasetKey.withPkey(key), valueDesc))
    .map(_.map(mhResponse => mhResponse.contents).map(scalaDataRecord2JavaDataRecord))
}
