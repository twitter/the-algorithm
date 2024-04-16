package com.tw ter.t  l neranker

 mport com.tw ter.t  l neranker.core.FutureDependencyTransfor r
 mport com.tw ter.t  l neranker.recap.model.ContentFeatures
 mport com.tw ter.t  l nes.model.T et d

package object contentfeatures {
  type ContentFeaturesProv der =
    FutureDependencyTransfor r[Seq[T et d], Map[T et d, ContentFeatures]]
}
