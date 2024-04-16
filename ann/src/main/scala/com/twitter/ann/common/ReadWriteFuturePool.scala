package com.tw ter.ann.common
 mport com.google.common.annotat ons.V s bleForTest ng
 mport com.tw ter.ut l.{Future, FuturePool}

tra  ReadWr eFuturePool {
  def read[T](f: => T): Future[T]
  def wr e[T](f: => T): Future[T]
}

object ReadWr eFuturePool {
  def apply(readPool: FuturePool, wr ePool: FuturePool): ReadWr eFuturePool = {
    new ReadWr eFuturePoolANN(readPool, wr ePool)
  }

  def apply(commonPool: FuturePool): ReadWr eFuturePool = {
    new ReadWr eFuturePoolANN(commonPool, commonPool)
  }
}

@V s bleForTest ng
pr vate[ann] class ReadWr eFuturePoolANN(readPool: FuturePool, wr ePool: FuturePool)
    extends ReadWr eFuturePool {
  def read[T](f: => T): Future[T] = {
    readPool.apply(f)
  }
  def wr e[T](f: => T): Future[T] = {
    wr ePool.apply(f)
  }
}
