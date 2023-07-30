package com.X.ann.service.query_server.common

import com.X.ann.common.{Distance, Queryable, RuntimeParams}
import com.X.search.common.file.AbstractFile

trait QueryableProvider[T, P <: RuntimeParams, D <: Distance[D]] {
  def provideQueryable(indexDir: AbstractFile): Queryable[T, P, D]
}
