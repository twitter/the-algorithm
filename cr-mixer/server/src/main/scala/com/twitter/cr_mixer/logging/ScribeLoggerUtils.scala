package com.ExTwitter.cr_mixer.logging

import com.ExTwitter.cr_mixer.featureswitch.CrMixerImpressedBuckets
import com.ExTwitter.cr_mixer.thriftscala.ImpressesedBucketInfo
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.frigate.common.util.StatsUtil
import com.ExTwitter.logging.Logger
import com.ExTwitter.scrooge.BinaryThriftStructSerializer
import com.ExTwitter.scrooge.ThriftStruct
import com.ExTwitter.scrooge.ThriftStructCodec

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
      CrMixerImpressedBuckets.getAllImpressedBuckets.map { listBuckets =>
        val listBucketsSet = listBuckets.toSet
        scopedStats.stat("impressed_buckets").add(listBucketsSet.size)
        listBucketsSet.map { bucket =>
          ImpressesedBucketInfo(
            experimentId = bucket.experiment.settings.experimentId.getOrElse(-1L),
            bucketName = bucket.name,
            version = bucket.experiment.settings.version,
          )
        }.toList
      }
    }
  }

}
