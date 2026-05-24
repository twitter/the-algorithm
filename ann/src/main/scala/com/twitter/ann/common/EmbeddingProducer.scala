package com.twitter.ann.common

import com.twitter.stitch.Stitch

trait EmbeddingProducer[T] {

  /**
   * Produce an embedding from type T. Implementations of this could do a lookup from an id to an
   * embedding. Or they could run a deep model on features that output and embedding.
   * @return An embedding Stitch. See go/stitch for details on how to use the Stitch API.
   */
  def produceEmbedding(input: T): Stitch[Option[EmbeddingType.EmbeddingVector]]
}
