package com.X.frigate.pushservice.store

import com.X.hermit.store.common.ReadableWritableStore
import com.X.notificationservice.thriftscala.GenericNotificationOverrideKey
import com.X.stitch.Stitch
import com.X.storage.client.manhattan.bijections.Bijections.BinaryCompactScalaInjection
import com.X.storage.client.manhattan.bijections.Bijections.LongInjection
import com.X.storage.client.manhattan.bijections.Bijections.StringInjection
import com.X.storage.client.manhattan.kv.ManhattanKVEndpoint
import com.X.storage.client.manhattan.kv.impl.Component
import com.X.storage.client.manhattan.kv.impl.DescriptorP1L1
import com.X.storage.client.manhattan.kv.impl.KeyDescriptor
import com.X.storage.client.manhattan.kv.impl.ValueDescriptor
import com.X.util.Future

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
