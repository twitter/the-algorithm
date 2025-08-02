package com.twitter.ann.annoy

import com.spotify.annoy.jni.base.{Annoy => AnnoyLib}
import com.twitter.ann.annoy.AnnoyCommon.IndetwitterFileName
import com.twitter.ann.annoy.AnnoyCommon.MetaDataFileName
import com.twitter.ann.annoy.AnnoyCommon.MetadataCodec
import com.twitter.ann.common.EmbeddingType._
import com.twitter.ann.common._
import com.twitter.ann.common.thriftscala.AnnoyIndetwitterMetadata
import com.twitter.concurrent.AsyncSemaphore
import com.twitter.mediaservices.commons.codec.ArrayByteBufferCodec
import com.twitter.search.common.file.AbstractFile
import com.twitter.search.common.file.LocalFile
import com.twitter.util.Future
import com.twitter.util.FuturePool
import java.io.File
import java.nio.file.Files
import org.apache.beam.sdk.io.fs.ResourceId
import scala.collection.JavaConverters._

private[annoy] object RawAnnoyIndetwitterBuilder {
  private[annoy] def apply[D <: Distance[D]](
    dimension: Int,
    numOfTrees: Int,
    metric: Metric[D],
    futurePool: FuturePool
  ): RawAppendable[AnnoyRuntimeParams, D] with Serialization = {
    val indetwitterBuilder = AnnoyLib.newIndetwitter(dimension, annoyMetric(metric))
    new RawAnnoyIndetwitterBuilder(dimension, numOfTrees, metric, indetwitterBuilder, futurePool)
  }

  private[this] def annoyMetric(metric: Metric[_]): AnnoyLib.Metric = {
    metric match {
      case L2 => AnnoyLib.Metric.EUCLIDEAN
      case Cosine => AnnoyLib.Metric.ANGULAR
      case _ => throw new RuntimeEtwitterception("Not supported: " + metric)
    }
  }
}

private[this] class RawAnnoyIndetwitterBuilder[D <: Distance[D]](
  dimension: Int,
  numOfTrees: Int,
  metric: Metric[D],
  indetwitterBuilder: AnnoyLib.Builder,
  futurePool: FuturePool)
    etwittertends RawAppendable[AnnoyRuntimeParams, D]
    with Serialization {
  private[this] var counter = 0
  // Note: Only one thread can access the underlying indetwitter, multithreaded indetwitter building not supported
  private[this] val semaphore = new AsyncSemaphore(1)

  override def append(embedding: EmbeddingVector): Future[Long] =
    semaphore.acquireAndRun({
      counter += 1
      indetwitterBuilder.addItem(
        counter,
        embedding.toArray
          .map(float => float2Float(float))
          .toList
          .asJava
      )

      Future.value(counter)
    })

  override def toQueryable: Queryable[Long, AnnoyRuntimeParams, D] = {
    val tempDirParent = Files.createTempDirectory("raw_annoy_indetwitter").toFile
    tempDirParent.deleteOnEtwitterit
    val tempDir = new LocalFile(tempDirParent)
    this.toDirectory(tempDir)
    RawAnnoyQueryIndetwitter(
      dimension,
      metric,
      futurePool,
      tempDir
    )
  }

  override def toDirectory(directory: ResourceId): Unit = {
    toDirectory(new IndetwitterOutputFile(directory))
  }

  /**
   * Serialize the annoy indetwitter in a directory.
   * @param directory: Directory to save to.
   */
  override def toDirectory(directory: AbstractFile): Unit = {
    toDirectory(new IndetwitterOutputFile(directory))
  }

  private def toDirectory(directory: IndetwitterOutputFile): Unit = {
    val indetwitterFile = directory.createFile(IndetwitterFileName)
    saveIndetwitter(indetwitterFile)

    val metaDataFile = directory.createFile(MetaDataFileName)
    saveMetadata(metaDataFile)
  }

  private[this] def saveIndetwitter(indetwitterFile: IndetwitterOutputFile): Unit = {
    val indetwitter = indetwitterBuilder
      .build(numOfTrees)
    val temp = new LocalFile(File.createTempFile(IndetwitterFileName, null))
    indetwitter.save(temp.getPath)
    indetwitterFile.copyFrom(temp.getByteSource.openStream())
    temp.delete()
  }

  private[this] def saveMetadata(metadataFile: IndetwitterOutputFile): Unit = {
    val numberOfVectorsIndetwittered = counter
    val metadata = AnnoyIndetwitterMetadata(
      dimension,
      Metric.toThrift(metric),
      numOfTrees,
      numberOfVectorsIndetwittered
    )
    val bytes = ArrayByteBufferCodec.decode(MetadataCodec.encode(metadata))
    val temp = new LocalFile(File.createTempFile(MetaDataFileName, null))
    temp.getByteSink.write(bytes)
    metadataFile.copyFrom(temp.getByteSource.openStream())
    temp.delete()
  }
}
