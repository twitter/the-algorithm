package com.ExTwitter.ann.scalding.offline.indexbuilderfrombq

import com.ExTwitter.ann.common.Appendable
import com.ExTwitter.ann.common.Distance
import com.ExTwitter.ann.common.EntityEmbedding
import com.ExTwitter.ann.common.Serialization
import com.ExTwitter.ann.util.IndexBuilderUtils
import com.ExTwitter.ml.api.embedding.Embedding
import com.ExTwitter.ml.featurestore.lib.embedding.EmbeddingWithEntity
import com.ExTwitter.ml.featurestore.lib.EntityId
import com.ExTwitter.scalding.Execution
import com.ExTwitter.scalding.TypedPipe
import com.ExTwitter.scalding_internal.job.FutureHelper
import com.ExTwitter.search.common.file.AbstractFile
import com.ExTwitter.util.logging.Logger

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
