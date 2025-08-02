package com.twitter.cr_mitwitterer.logging

import com.twitter.cr_mitwitterer.featureswitch.CrMitwittererImpressedBuckets
import com.twitter.cr_mitwitterer.thriftscala.ImpressesedBucketInfo
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.util.StatsUtil
import com.twitter.logging.Logger
import com.twitter.scrooge.BinaryThriftStructSerializer
import com.twitter.scrooge.ThriftStruct
import com.twitter.scrooge.ThriftStructCodec

object ScribeLoggerUtils {

  /**
   * Handles base64-encoding, serialization, and publish.
   */
  private[logging] def publish[T <: ThriftStruct](
    logger: Logger,
    codec: ThriftStructCodec[T],
    message: T
  ): Unit = {
    logger.info(BinaryThriftStructSerializer(codec).toString(message))
  }

  private[logging] def getImpressedBuckets(
    scopedStats: StatsReceiver
  ): Option[List[ImpressesedBucketInfo]] = {
    StatsUtil.trackNonFutureBlockStats(scopedStats.scope("getImpressedBuckets")) {
      CrMitwittererImpressedBuckets.getAllImpressedBuckets.map { listBuckets =>
        val listBucketsSet = listBuckets.toSet
        scopedStats.stat("impressed_buckets").add(listBucketsSet.size)
        listBucketsSet.map { bucket =>
          ImpressesedBucketInfo(
            etwitterperimentId = bucket.etwitterperiment.settings.etwitterperimentId.getOrElse(-1L),
            bucketName = bucket.name,
            version = bucket.etwitterperiment.settings.version,
          )
        }.toList
      }
    }
  }

}
