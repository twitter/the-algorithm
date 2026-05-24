package com.twitter.ann.annoy

import com.twitter.ann.common.RuntimeParams
import com.twitter.ann.common.thriftscala.AnnoyIndexMetadata
import com.twitter.bijection.Injection
import com.twitter.mediaservices.commons.codec.ThriftByteBufferCodec
import com.twitter.ann.common.thriftscala.{AnnoyRuntimeParam, RuntimeParams => ServiceRuntimeParams}
import scala.util.{Failure, Success, Try}

object AnnoyCommon {
  private[annoy] lazy val MetadataCodec = new ThriftByteBufferCodec(AnnoyIndexMetadata)
  private[annoy] val IndexFileName = "annoy_index"
  private[annoy] val MetaDataFileName = "annoy_index_metadata"
  private[annoy] val IndexIdMappingFileName = "annoy_index_id_mapping"

  val RuntimeParamsInjection: Injection[AnnoyRuntimeParams, ServiceRuntimeParams] =
    new Injection[AnnoyRuntimeParams, ServiceRuntimeParams] {
      override def apply(scalaParams: AnnoyRuntimeParams): ServiceRuntimeParams = {
        ServiceRuntimeParams.AnnoyParam(
          AnnoyRuntimeParam(
            scalaParams.nodesToExplore
          )
        )
      }

      override def invert(thriftParams: ServiceRuntimeParams): Try[AnnoyRuntimeParams] =
        thriftParams match {
          case ServiceRuntimeParams.AnnoyParam(annoyParam) =>
            Success(
              AnnoyRuntimeParams(annoyParam.numOfNodesToExplore)
            )
          case p => Failure(new IllegalArgumentException(s"Expected AnnoyRuntimeParams got $p"))
        }
    }
}

case class AnnoyRuntimeParams(
  /* Number of vectors to evaluate while searching. A larger value will give more accurate results, but will take longer time to return.
   * Default value would be numberOfTrees*numberOfNeigboursRequested
   */
  nodesToExplore: Option[Int])
    extends RuntimeParams {
  override def toString: String = s"AnnoyRuntimeParams( nodesToExplore = $nodesToExplore)"
}
