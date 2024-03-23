package com.ExTwitter.ann.common

import com.ExTwitter.ann.common.EmbeddingType._
import com.ExTwitter.ann.common.thriftscala.{
  NearestNeighborQuery,
  NearestNeighborResult,
  Distance => ServiceDistance,
  RuntimeParams => ServiceRuntimeParams
}
import com.ExTwitter.bijection.Injection
import com.ExTwitter.finagle.Service
import com.ExTwitter.mediaservices.commons.codec.ArrayByteBufferCodec
import com.ExTwitter.util.Future

class ServiceClientQueryable[T, P <: RuntimeParams, D <: Distance[D]](
  service: Service[NearestNeighborQuery, NearestNeighborResult],
  runtimeParamInjection: Injection[P, ServiceRuntimeParams],
  distanceInjection: Injection[D, ServiceDistance],
  idInjection: Injection[T, Array[Byte]])
    extends Queryable[T, P, D] {
  override def query(
    embedding: EmbeddingVector,
    numOfNeighbors: Int,
    runtimeParams: P
  ): Future[List[T]] = {
    service
      .apply(
        NearestNeighborQuery(
          embeddingSerDe.toThrift(embedding),
          withDistance = false,
          runtimeParamInjection(runtimeParams),
          numOfNeighbors
        )
      )
      .map { result =>
        result.nearestNeighbors.map { nearestNeighbor =>
          idInjection.invert(ArrayByteBufferCodec.decode(nearestNeighbor.id)).get
        }.toList
      }
  }

  override def queryWithDistance(
    embedding: EmbeddingVector,
    numOfNeighbors: Int,
    runtimeParams: P
  ): Future[List[NeighborWithDistance[T, D]]] =
    service
      .apply(
        NearestNeighborQuery(
          embeddingSerDe.toThrift(embedding),
          withDistance = true,
          runtimeParamInjection(runtimeParams),
          numOfNeighbors
        )
      )
      .map { result =>
        result.nearestNeighbors.map { nearestNeighbor =>
          NeighborWithDistance(
            idInjection.invert(ArrayByteBufferCodec.decode(nearestNeighbor.id)).get,
            distanceInjection.invert(nearestNeighbor.distance.get).get
          )
        }.toList
      }
}
