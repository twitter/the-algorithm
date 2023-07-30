package com.X.usersignalservice
package base

import com.X.bijection.Codec
import com.X.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.X.storehaus.ReadableStore
import com.X.storehaus_internal.manhattan.ManhattanCluster
import com.X.storehaus_internal.manhattan.ManhattanRO
import com.X.storehaus_internal.manhattan.ManhattanROConfig
import com.X.storehaus_internal.util.HDFSPath
import com.X.twistly.common.UserId
import com.X.util.Future
import com.X.storehaus_internal.util.ApplicationID
import com.X.storehaus_internal.util.DatasetName

/**
 * A Manhattan signal fetcher extending BaseSignalFetcher to provide an interface to fetch signals
 * from a Manhattan dataset.
 *
 * Extends this when the underlying store is a single Manhattan dataset.
 * @tparam ManhattanKeyType
 * @tparam ManhattanValueType
 */
trait ManhattanSignalFetcher[ManhattanKeyType, ManhattanValueType] extends BaseSignalFetcher {
  /*
    Define the meta info of the Manhattan dataset
   */
  protected def manhattanAppId: String
  protected def manhattanDatasetName: String
  protected def manhattanClusterId: ManhattanCluster
  protected def manhattanKVClientMtlsParams: ManhattanKVClientMtlsParams

  protected def manhattanKeyCodec: Codec[ManhattanKeyType]
  protected def manhattanRawSignalCodec: Codec[ManhattanValueType]

  /**
   * Adaptor to transform the userId to the ManhattanKey
   * @param userId
   * @return ManhattanKeyType
   */
  protected def toManhattanKey(userId: UserId): ManhattanKeyType

  /**
   * Adaptor to transform the ManhattanValue to the Seq of RawSignalType
   * @param manhattanValue
   * @return Seq[RawSignalType]
   */
  protected def toRawSignals(manhattanValue: ManhattanValueType): Seq[RawSignalType]

  protected final lazy val underlyingStore: ReadableStore[UserId, Seq[RawSignalType]] = {
    ManhattanRO
      .getReadableStoreWithMtls[ManhattanKeyType, ManhattanValueType](
        ManhattanROConfig(
          HDFSPath(""),
          ApplicationID(manhattanAppId),
          DatasetName(manhattanDatasetName),
          manhattanClusterId),
        manhattanKVClientMtlsParams
      )(manhattanKeyCodec, manhattanRawSignalCodec)
      .composeKeyMapping(userId => toManhattanKey(userId))
      .mapValues(manhattanRawSignal => toRawSignals(manhattanRawSignal))
  }

  override final def getRawSignals(userId: UserId): Future[Option[Seq[RawSignalType]]] =
    underlyingStore.get(userId)
}
