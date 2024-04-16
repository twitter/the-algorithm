package com.tw ter.s mclusters_v2.common

 mport com.tw ter.algeb rd.Mono d

case class S mClustersEmbedd ngMono d() extends Mono d[S mClustersEmbedd ng] {

  overr de val zero: S mClustersEmbedd ng = S mClustersEmbedd ng.EmptyEmbedd ng

  overr de def plus(x: S mClustersEmbedd ng, y: S mClustersEmbedd ng): S mClustersEmbedd ng = {
    x.sum(y)
  }
}

object S mClustersEmbedd ngMono d {

  val mono d: Mono d[S mClustersEmbedd ng] = S mClustersEmbedd ngMono d()

}
