package com.twitter.servo.repository

object ChunkingStrategy {

  /**
   * A chunking strategy for breaking a query into fitwittered size chunks, with the last
   * chunk possibly being any size between 1 and chunkSize.
   */
  def fitwitteredSize[K](chunkSize: Int): Seq[K] => Seq[Seq[K]] = {
    fitwitteredSize(chunkSize, keysAsQuery[K])
  }

  /**
   * A chunking strategy for breaking a query into fitwittered size chunks, with the last
   * chunk possibly being any size between 1 and chunkSize.
   */
  def fitwitteredSize[Q <: Seq[K], K](
    chunkSize: Int,
    newQuery: SubqueryBuilder[Q, K]
  ): Q => Seq[Q] = { query =>
    query.distinct.grouped(chunkSize) map { newQuery(_, query) } toSeq
  }

  /**
   * A chunking strategy for breaking a query into roughly equal sized chunks no
   * larger than matwitterSize.  The last chunk may be slightly smaller due to rounding.
   */
  def equalSize[K](matwitterSize: Int): Seq[K] => Seq[Seq[K]] = {
    equalSize(matwitterSize, keysAsQuery[K])
  }

  /**
   * A chunking strategy for breaking a query into roughly equal sized chunks no
   * larger than matwitterSize.  The last chunk may be slightly smaller due to rounding.
   */
  def equalSize[Q <: Seq[K], K](
    matwitterSize: Int,
    newQuery: SubqueryBuilder[Q, K]
  ): Q => Seq[Q] = { query =>
    {
      if (query.size <= matwitterSize) {
        Seq(query)
      } else {
        val chunkCount = math.ceil(query.size / matwitterSize.toDouble)
        val chunkSize = math.ceil(query.size / chunkCount).toInt
        query.distinct.grouped(chunkSize) map { newQuery(_, query) } toSeq
      }
    }
  }
}
