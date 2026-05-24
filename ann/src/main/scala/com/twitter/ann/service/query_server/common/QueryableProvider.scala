package com.twitter.ann.service.query_server.common

import com.twitter.ann.common.{Distance, Queryable, RuntimeParams}
import com.twitter.search.common.file.AbstractFile

trait QueryableProvider[T, P <: RuntimeParams, D <: Distance[D]] {
  def provideQueryable(indexDir: AbstractFile): Queryable[T, P, D]
}
