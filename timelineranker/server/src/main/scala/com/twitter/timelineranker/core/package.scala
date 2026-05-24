package com.twitter.timelineranker

import com.twitter.timelineranker.model.RecapQuery
import com.twitter.timelines.configapi

package object core {
  type FutureDependencyTransformer[-U, +V] = configapi.FutureDependencyTransformer[RecapQuery, U, V]
  object FutureDependencyTransformer
      extends configapi.FutureDependencyTransformerFunctions[RecapQuery]

  type DependencyTransformer[-U, +V] = configapi.DependencyTransformer[RecapQuery, U, V]
  object DependencyTransformer extends configapi.DependencyTransformerFunctions[RecapQuery]
}
