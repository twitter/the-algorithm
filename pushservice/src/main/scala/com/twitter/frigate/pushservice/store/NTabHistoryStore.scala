package com.twitter.frigate.pushservice.store

import com.twitter.hermit.store.common.ReadableWritableStore
import com.twitter.notificationservice.thriftscala.GenericNotificationOverrideKey
import com.twitter.stitch.Stitch
import com.twitter.storage.client.manhattan.bijections.Bijections.BinaryCompactScalaInjection
import com.twitter.storage.client.manhattan.bijections.Bijections.LongInjection
import com.twitter.storage.client.manhattan.bijections.Bijections.StringInjection
import com.twitter.storage.client.manhattan.kv.ManhattanKVEndpoint
import com.twitter.storage.client.manhattan.kv.impl.Component
import com.twitter.storage.client.manhattan.kv.impl.DescriptorP1L1
import com.twitter.storage.client.manhattan.kv.impl.KeyDescriptor
import com.twitter.storage.client.manhattan.kv.impl.ValueDescriptor
import com.twitter.util.Future

case class NTabHistoryStore(mhEndpoint: ManhattanKVEndpoint, dataset: String)
    extends ReadableWritableStore[(Long, String), GenericNotificationOverrideKey] {

  private val keyDesc: DescriptorP1L1.EmptyKey[Long, String] =
    KeyDescriptor(Component(LongInjection), Component(StringInjection))

  private val genericNotifKeyValDesc: ValueDescriptor.EmptyValue[GenericNotificationOverrideKey] =
    ValueDescriptor[GenericNotificationOverrideKey](
      BinaryCompactScalaInjection(GenericNotificationOverrideKey)
    )

  override def get(key: (Long, String)): Future[Option[GenericNotificationOverrideKey]] = {
    val (userId, impressionId) = key
    val mhKey = keyDesc.withDataset(dataset).withPkey(userId).withLkey(impressionId)

    Stitch
      .run(mhEndpoint.get(mhKey, genericNotifKeyValDesc))
      .map { optionMhValue =>
        optionMhValue.map(_.contents)
      }
  }

  override def put(keyValue: ((Long, String), GenericNotificationOverrideKey)): Future[Unit] = {
    val ((userId, impressionId), genericNotifOverrideKey) = keyValue
    val mhKey = keyDesc.withDataset(dataset).withPkey(userId).withLkey(impressionId)
    val mhVal = genericNotifKeyValDesc.withValue(genericNotifOverrideKey)
    Stitch.run(mhEndpoint.insert(mhKey, mhVal))
  }

}
