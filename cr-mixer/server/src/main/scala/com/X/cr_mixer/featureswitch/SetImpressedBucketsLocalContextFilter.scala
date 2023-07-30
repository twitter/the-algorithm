package com.X.cr_mixer.featureswitch

import com.X.finagle.Filter
import javax.inject.Inject
import javax.inject.Singleton
import scala.collection.concurrent.TrieMap
import com.X.abdecider.Bucket
import com.X.finagle.Service

@Singleton
class SetImpressedBucketsLocalContextFilter @Inject() () extends Filter.TypeAgnostic {
  override def toFilter[Req, Rep]: Filter[Req, Rep, Req, Rep] =
    (request: Req, service: Service[Req, Rep]) => {

      val concurrentTrieMap = TrieMap
        .empty[Bucket, Boolean] // Trie map has no locks and O(1) inserts
      CrMixerImpressedBuckets.localImpressedBucketsMap.let(concurrentTrieMap) {
        service(request)
      }
    }

}
