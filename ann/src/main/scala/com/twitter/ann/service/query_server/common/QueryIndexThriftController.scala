package com.twitter.ann.service.query_server.common

import com.twitter.ann.common._
import com.twitter.ann.common.EmbeddingType._
import com.twitter.ann.common.thriftscala.AnnQueryService.Query
import com.twitter.ann.common.thriftscala.AnnQueryService
import com.twitter.ann.common.thriftscala.NearestNeighbor
import com.twitter.ann.common.thriftscala.NearestNeighborResult
import com.twitter.ann.common.thriftscala.{Distance => ServiceDistance}
import com.twitter.ann.common.thriftscala.{RuntimeParams => ServiceRuntimeParams}
import com.twitter.bijection.Injection
import com.twitter.finagle.Service
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.thrift.Controller
import com.twitter.mediaservices.commons.{ThriftServer => TServer}
import java.nio.ByteBuffer
import javax.inject.Inject

class QueryIndexThriftController[T, P <: RuntimeParams, D <: Distance[D]] @Inject() (
  statsReceiver: StatsReceiver,
  queryable: Queryable[T, P, D],
  runtimeParamInjection: Injection[P, ServiceRuntimeParams],
  distanceInjection: Injection[D, ServiceDistance],
  idInjection: Injection[T, Array[Byte]])
    extends Controller(AnnQueryService) {

  private[this] val thriftServer = new TServer(statsReceiver, Some(RuntimeExceptionTransform))

  val trackingStatName = "ann_query"

  private[this] val stats = statsReceiver.scope(trackingStatName)
  private[this] val numOfneighborsRequested = stats.stat("num_of_neighbors_requested")
  private[this] val numOfneighborsResponse = stats.stat("num_of_neighbors_response")
  private[this] val queryKeyNotFound = stats.stat("query_key_not_found")

  /**
   * Implements AnnQueryService.query, returns nearest neighbors for a given query
   */
  val query: Service[Query.Args, Query.SuccessType] = { args: Query.Args =>
    thriftServer.track(trackingStatName) {
      val query = args.query
      val key = query.key
      val embedding = embeddingSerDe.fromThrift(query.embedding)
      val numOfneighbors = query.numberOfNeighbors
      val withDistance = query.withDistance
      val runtimeParams = runtimeParamInjection.invert(query.runtimeParams).get
      numOfneighborsRequested.add(numOfneighbors)

      val result = if (withDistance) {
        val nearestNeighbors = if (queryable.isInstanceOf[QueryableGrouped[T, P, D]]) {
          queryable
            .asInstanceOf[QueryableGrouped[T, P, D]]
            .queryWithDistance(embedding, numOfneighbors, runtimeParams, key)
        } else {
          queryable
            .queryWithDistance(embedding, numOfneighbors, runtimeParams)
        }

        nearestNeighbors.map { list =>
          list.map { nn =>
            NearestNeighbor(
              ByteBuffer.wrap(idInjection.apply(nn.neighbor)),
              Some(distanceInjection.apply(nn.distance))
            )
          }
        }
      } else {

        val nearestNeighbors = if (queryable.isInstanceOf[QueryableGrouped[T, P, D]]) {
          queryable
            .asInstanceOf[QueryableGrouped[T, P, D]]
            .query(embedding, numOfneighbors, runtimeParams, key)
        } else {
          queryable
            .query(embedding, numOfneighbors, runtimeParams)
        }

        nearestNeighbors
          .map { list =>
            list.map { nn =>
              NearestNeighbor(ByteBuffer.wrap(idInjection.apply(nn)))
            }
          }
      }

      result.map(NearestNeighborResult(_)).onSuccess { r =>
        numOfneighborsResponse.add(r.nearestNeighbors.size)
      }
    }
  }
  handle(Query) { query }
}
