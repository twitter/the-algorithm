package com.X.ann.scalding.offline.indexbuilderfrombq

import com.X.ann.common.Appendable
import com.X.ann.common.Distance
import com.X.ann.common.EntityEmbedding
import com.X.ann.common.Serialization
import com.X.ann.util.IndexBuilderUtils
import com.X.ml.api.embedding.Embedding
import com.X.ml.featurestore.lib.embedding.EmbeddingWithEntity
import com.X.ml.featurestore.lib.EntityId
import com.X.scalding.Execution
import com.X.scalding.TypedPipe
import com.X.scalding_internal.job.FutureHelper
import com.X.search.common.file.AbstractFile
import com.X.util.logging.Logger

object IndexBuilder {
  private[this] val Log = Logger.apply[IndexBuilder.type]

  def run[T <: EntityId, _, D <: Distance[D]](
    embeddingsPipe: TypedPipe[EmbeddingWithEntity[T]],
    embeddingLimit: Option[Int],
    index: Appendable[T, _, D] with Serialization,
    concurrencyLevel: Int,
    outputDirectory: AbstractFile,
    numDimensions: Int
  ): Execution[Unit] = {
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

    annEmbeddingPipe.toIterableExecution.flatMap { annEmbeddings =>
      val future = IndexBuilderUtils.addToIndex(index, annEmbeddings.toStream, concurrencyLevel)
      val result = future.map { numberUpdates =>
        Log.info(s"Performed $numberUpdates updates")
        index.toDirectory(outputDirectory)
        Log.info(s"Finished writing to $outputDirectory")
      }
      FutureHelper.executionFrom(result).unit
    }
  }
}
