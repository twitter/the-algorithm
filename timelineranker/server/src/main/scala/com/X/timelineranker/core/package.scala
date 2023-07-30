package com.X.timelineranker

import com.X.timelineranker.model.RecapQuery
import com.X.timelines.configapi

package object core {
  type FutureDependencyTransformer[-U, +V] = configapi.FutureDependencyTransformer[RecapQuery, U, V]
  object FutureDependencyTransformer
      extends configapi.FutureDependencyTransformerFunctions[RecapQuery]

  type DependencyTransformer[-U, +V] = configapi.DependencyTransformer[RecapQuery, U, V]
  object DependencyTransformer extends configapi.DependencyTransformerFunctions[RecapQuery]
}
