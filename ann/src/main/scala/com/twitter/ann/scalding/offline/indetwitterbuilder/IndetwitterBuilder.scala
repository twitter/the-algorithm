package com.twitter.ann.scalding.offline.indetwitterbuilder

import com.twitter.ann.common.Appendable
import com.twitter.ann.common.Distance
import com.twitter.ann.common.EntityEmbedding
import com.twitter.ann.common.Serialization
import com.twitter.ann.util.IndetwitterBuilderUtils
import com.twitter.cortetwitter.ml.embeddings.common.EmbeddingFormat
import com.twitter.ml.api.embedding.Embedding
import com.twitter.ml.featurestore.lib.EntityId
import com.twitter.scalding.Etwitterecution
import com.twitter.scalding_internal.job.FutureHelper
import com.twitter.search.common.file.AbstractFile
import com.twitter.util.logging.Logger

object IndetwitterBuilder {
  private[this] val Log = Logger.apply[IndetwitterBuilder.type]

  def run[T <: EntityId, _, D <: Distance[D]](
    embeddingFormat: EmbeddingFormat[T],
    embeddingLimit: Option[Int],
    indetwitter: Appendable[T, _, D] with Serialization,
    concurrencyLevel: Int,
    outputDirectory: AbstractFile,
    numDimensions: Int
  ): Etwitterecution[Unit] = {
    val embeddingsPipe = embeddingFormat.getEmbeddings
    val limitedEmbeddingsPipe = embeddingLimit
      .map { limit =>
        embeddingsPipe.limit(limit)
      }.getOrElse(embeddingsPipe)

    val annEmbeddingPipe = limitedEmbeddingsPipe.map { embedding =>
      val embeddingSize = embedding.embedding.length
      assert(
        embeddingSize == numDimensions,
        s"Specified number of dimensions $numDimensions does not match the dimensions of the " +
          s"embedding $embeddingSize"
      )
      EntityEmbedding[T](embedding.entityId, Embedding(embedding.embedding.toArray))
    }

    annEmbeddingPipe.toIterableEtwitterecution.flatMap { annEmbeddings =>
      val future = IndetwitterBuilderUtils.addToIndetwitter(indetwitter, annEmbeddings.toStream, concurrencyLevel)
      val result = future.map { numberUpdates =>
        Log.info(s"Performed $numberUpdates updates")
        indetwitter.toDirectory(outputDirectory)
        Log.info(s"Finished writing to $outputDirectory")
      }
      FutureHelper.etwitterecutionFrom(result).unit
    }
  }
}
