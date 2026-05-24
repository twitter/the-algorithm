package com.twitter.ann.common
import com.google.common.annotations.VisibleForTesting
import com.twitter.util.{Future, FuturePool}

trait ReadWriteFuturePool {
  def read[T](f: => T): Future[T]
  def write[T](f: => T): Future[T]
}

object ReadWriteFuturePool {
  def apply(readPool: FuturePool, writePool: FuturePool): ReadWriteFuturePool = {
    new ReadWriteFuturePoolANN(readPool, writePool)
  }

  def apply(commonPool: FuturePool): ReadWriteFuturePool = {
    new ReadWriteFuturePoolANN(commonPool, commonPool)
  }
}

@VisibleForTesting
private[ann] class ReadWriteFuturePoolANN(readPool: FuturePool, writePool: FuturePool)
    extends ReadWriteFuturePool {
  def read[T](f: => T): Future[T] = {
    readPool.apply(f)
  }
  def write[T](f: => T): Future[T] = {
    writePool.apply(f)
  }
}
