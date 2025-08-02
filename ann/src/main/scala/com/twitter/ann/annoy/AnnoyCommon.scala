package com.twitter.ann.annoy

import com.twitter.ann.common.RuntimeParams
import com.twitter.ann.common.thriftscala.AnnoyIndetwitterMetadata
import com.twitter.bijection.Injection
import com.twitter.mediaservices.commons.codec.ThriftByteBufferCodec
import com.twitter.ann.common.thriftscala.{AnnoyRuntimeParam, RuntimeParams => ServiceRuntimeParams}
import scala.util.{Failure, Success, Try}

object AnnoyCommon {
  private[annoy] lazy val MetadataCodec = new ThriftByteBufferCodec(AnnoyIndetwitterMetadata)
  private[annoy] val IndetwitterFileName = "annoy_indetwitter"
  private[annoy] val MetaDataFileName = "annoy_indetwitter_metadata"
  private[annoy] val IndetwitterIdMappingFileName = "annoy_indetwitter_id_mapping"

  val RuntimeParamsInjection: Injection[AnnoyRuntimeParams, ServiceRuntimeParams] =
    new Injection[AnnoyRuntimeParams, ServiceRuntimeParams] {
      override def apply(scalaParams: AnnoyRuntimeParams): ServiceRuntimeParams = {
        ServiceRuntimeParams.AnnoyParam(
          AnnoyRuntimeParam(
            scalaParams.nodesToEtwitterplore
          )
        )
      }

      override def invert(thriftParams: ServiceRuntimeParams): Try[AnnoyRuntimeParams] =
        thriftParams match {
          case ServiceRuntimeParams.AnnoyParam(annoyParam) =>
            Success(
              AnnoyRuntimeParams(annoyParam.numOfNodesToEtwitterplore)
            )
          case p => Failure(new IllegalArgumentEtwitterception(s"Etwitterpected AnnoyRuntimeParams got $p"))
        }
    }
}

case class AnnoyRuntimeParams(
  /* Number of vectors to evaluate while searching. A larger value will give more accurate results, but will take longer time to return.
   * Default value would be numberOfTrees*numberOfNeigboursRequested
   */
  nodesToEtwitterplore: Option[Int])
    etwittertends RuntimeParams {
  override def toString: String = s"AnnoyRuntimeParams( nodesToEtwitterplore = $nodesToEtwitterplore)"
}
