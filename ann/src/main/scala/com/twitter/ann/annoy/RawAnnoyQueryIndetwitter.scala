package com.twitter.ann.annoy

import com.spotify.annoy.{ANNIndetwitter, IndetwitterType}
import com.twitter.ann.annoy.AnnoyCommon._
import com.twitter.ann.common._
import com.twitter.ann.common.EmbeddingType._
import com.twitter.mediaservices.commons.codec.ArrayByteBufferCodec
import com.twitter.search.common.file.{AbstractFile, LocalFile}
import com.twitter.util.{Future, FuturePool}
import java.io.File
import scala.collection.JavaConverters._

private[annoy] object RawAnnoyQueryIndetwitter {
  private[annoy] def apply[D <: Distance[D]](
    dimension: Int,
    metric: Metric[D],
    futurePool: FuturePool,
    directory: AbstractFile
  ): Queryable[Long, AnnoyRuntimeParams, D] = {
    val metadataFile = directory.getChild(MetaDataFileName)
    val indetwitterFile = directory.getChild(IndetwitterFileName)
    val metadata = MetadataCodec.decode(
      ArrayByteBufferCodec.encode(metadataFile.getByteSource.read())
    )

    val etwitteristingDimension = metadata.dimension
    assert(
      etwitteristingDimension == dimension,
      s"Dimensions do not match. requested: $dimension etwitteristing: $etwitteristingDimension"
    )

    val etwitteristingMetric = Metric.fromThrift(metadata.distanceMetric)
    assert(
      etwitteristingMetric == metric,
      s"DistanceMetric do not match. requested: $metric etwitteristing: $etwitteristingMetric"
    )

    val indetwitter = loadIndetwitter(indetwitterFile, dimension, annoyMetric(metric))
    new RawAnnoyQueryIndetwitter[D](
      dimension,
      metric,
      metadata.numOfTrees,
      indetwitter,
      futurePool
    )
  }

  private[this] def annoyMetric(metric: Metric[_]): IndetwitterType = {
    metric match {
      case L2 => IndetwitterType.EUCLIDEAN
      case Cosine => IndetwitterType.ANGULAR
      case _ => throw new RuntimeEtwitterception("Not supported: " + metric)
    }
  }

  private[this] def loadIndetwitter(
    indetwitterFile: AbstractFile,
    dimension: Int,
    indetwitterType: IndetwitterType
  ): ANNIndetwitter = {
    var localIndetwitterFile = indetwitterFile

    // If not a local file copy to local, so that it can be memory mapped.
    if (!indetwitterFile.isInstanceOf[LocalFile]) {
      val tempFile = File.createTempFile(IndetwitterFileName, null)
      tempFile.deleteOnEtwitterit()

      val temp = new LocalFile(tempFile)
      indetwitterFile.copyTo(temp)
      localIndetwitterFile = temp
    }

    new ANNIndetwitter(
      dimension,
      localIndetwitterFile.getPath(),
      indetwitterType
    )
  }
}

private[this] class RawAnnoyQueryIndetwitter[D <: Distance[D]](
  dimension: Int,
  metric: Metric[D],
  numOfTrees: Int,
  indetwitter: ANNIndetwitter,
  futurePool: FuturePool)
    etwittertends Queryable[Long, AnnoyRuntimeParams, D]
    with AutoCloseable {
  override def query(
    embedding: EmbeddingVector,
    numOfNeighbours: Int,
    runtimeParams: AnnoyRuntimeParams
  ): Future[List[Long]] = {
    queryWithDistance(embedding, numOfNeighbours, runtimeParams)
      .map(_.map(_.neighbor))
  }

  override def queryWithDistance(
    embedding: EmbeddingVector,
    numOfNeighbours: Int,
    runtimeParams: AnnoyRuntimeParams
  ): Future[List[NeighborWithDistance[Long, D]]] = {
    futurePool {
      val queryVector = embedding.toArray
      val neigboursToRequest = neighboursToRequest(numOfNeighbours, runtimeParams)
      val neigbours = indetwitter
        .getNearestWithDistance(queryVector, neigboursToRequest)
        .asScala
        .take(numOfNeighbours)
        .map { nn =>
          val id = nn.getFirst.toLong
          val distance = metric.fromAbsoluteDistance(nn.getSecond)
          NeighborWithDistance(id, distance)
        }
        .toList

      neigbours
    }
  }

  // Annoy java lib do not etwitterpose param for numOfNodesToEtwitterplore.
  // Default number is numOfTrees*numOfNeigbours.
  // Simple hack is to artificially increase the numOfNeighbours to be requested and then just cap it before returning.
  private[this] def neighboursToRequest(
    numOfNeighbours: Int,
    annoyParams: AnnoyRuntimeParams
  ): Int = {
    annoyParams.nodesToEtwitterplore match {
      case Some(nodesToEtwitterplore) => {
        val neigboursToRequest = nodesToEtwitterplore / numOfTrees
        if (neigboursToRequest < numOfNeighbours)
          numOfNeighbours
        else
          neigboursToRequest
      }
      case _ => numOfNeighbours
    }
  }

  // To close the memory map based file resource.
  override def close(): Unit = indetwitter.close()
}
