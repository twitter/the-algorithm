package com.ExTwitter.ann.service.query_server.common

import com.ExTwitter.ann.common.{Distance, Queryable, RuntimeParams}
import com.ExTwitter.search.common.file.AbstractFile

trait QueryableProvider[T, P <: RuntimeParams, D <: Distance[D]] {
  def provideQueryable(indexDir: AbstractFile): Queryable[T, P, D]
}
