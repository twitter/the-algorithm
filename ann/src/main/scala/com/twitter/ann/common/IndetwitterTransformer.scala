package com.twitter.ann.common

import com.twitter.ann.common.EmbeddingType.EmbeddingVector
import com.twitter.storehaus.{ReadableStore, Store}
import com.twitter.util.Future

// Utility to transform raw indetwitter to typed indetwitter using Store
object IndetwitterTransformer {

  /**
   * Transform a long type queryable indetwitter to Typed queryable indetwitter
   * @param indetwitter: Raw Queryable indetwitter
   * @param store: Readable store to provide mappings between Long and T
   * @tparam T: Type to transform to
   * @tparam P: Runtime params
   * @return Queryable indetwitter typed on T
   */
  def transformQueryable[T, P <: RuntimeParams, D <: Distance[D]](
    indetwitter: Queryable[Long, P, D],
    store: ReadableStore[Long, T]
  ): Queryable[T, P, D] = {
    new Queryable[T, P, D] {
      override def query(
        embedding: EmbeddingVector,
        numOfNeighbors: Int,
        runtimeParams: P
      ): Future[List[T]] = {
        val neighbors = indetwitter.query(embedding, numOfNeighbors, runtimeParams)
        neighbors
          .flatMap(nn => {
            val ids = nn.map(id => store.get(id).map(_.get))
            Future
              .collect(ids)
              .map(_.toList)
          })
      }

      override def queryWithDistance(
        embedding: EmbeddingVector,
        numOfNeighbors: Int,
        runtimeParams: P
      ): Future[List[NeighborWithDistance[T, D]]] = {
        val neighbors = indetwitter.queryWithDistance(embedding, numOfNeighbors, runtimeParams)
        neighbors
          .flatMap(nn => {
            val ids = nn.map(obj =>
              store.get(obj.neighbor).map(id => NeighborWithDistance(id.get, obj.distance)))
            Future
              .collect(ids)
              .map(_.toList)
          })
      }
    }
  }

  /**
   * Transform a long type appendable indetwitter to Typed appendable indetwitter
   * @param indetwitter: Raw Appendable indetwitter
   * @param store: Writable store to store mappings between Long and T
   * @tparam T: Type to transform to
   * @return Appendable indetwitter typed on T
   */
  def transformAppendable[T, P <: RuntimeParams, D <: Distance[D]](
    indetwitter: RawAppendable[P, D],
    store: Store[Long, T]
  ): Appendable[T, P, D] = {
    new Appendable[T, P, D]() {
      override def append(entity: EntityEmbedding[T]): Future[Unit] = {
        indetwitter
          .append(entity.embedding)
          .flatMap(id => store.put((id, Some(entity.id))))
      }

      override def toQueryable: Queryable[T, P, D] = {
        transformQueryable(indetwitter.toQueryable, store)
      }
    }
  }

  /**
   * Transform a long type appendable and queryable indetwitter to Typed appendable and queryable indetwitter
   * @param indetwitter: Raw Appendable and queryable indetwitter
   * @param store: Store to provide/store mappings between Long and T
   * @tparam T: Type to transform to
   * @tparam Indetwitter: Indetwitter
   * @return Appendable and queryable indetwitter typed on T
   */
  def transform1[
    Indetwitter <: RawAppendable[P, D] with Queryable[Long, P, D],
    T,
    P <: RuntimeParams,
    D <: Distance[D]
  ](
    indetwitter: Indetwitter,
    store: Store[Long, T]
  ): Queryable[T, P, D] with Appendable[T, P, D] = {
    val queryable = transformQueryable(indetwitter, store)
    val appendable = transformAppendable(indetwitter, store)

    new Queryable[T, P, D] with Appendable[T, P, D] {
      override def query(
        embedding: EmbeddingVector,
        numOfNeighbors: Int,
        runtimeParams: P
      ) = queryable.query(embedding, numOfNeighbors, runtimeParams)

      override def queryWithDistance(
        embedding: EmbeddingVector,
        numOfNeighbors: Int,
        runtimeParams: P
      ) = queryable.queryWithDistance(embedding, numOfNeighbors, runtimeParams)

      override def append(entity: EntityEmbedding[T]) = appendable.append(entity)

      override def toQueryable: Queryable[T, P, D] = appendable.toQueryable
    }
  }
}
