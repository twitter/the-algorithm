package com.twitter.cr_mitwitterer.featureswitch

import com.twitter.finagle.Filter
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import scala.collection.concurrent.TrieMap
import com.twitter.abdecider.Bucket
import com.twitter.finagle.Service

@Singleton
class SetImpressedBucketsLocalContetwittertFilter @Inject() () etwittertends Filter.TypeAgnostic {
  override def toFilter[Req, Rep]: Filter[Req, Rep, Req, Rep] =
    (request: Req, service: Service[Req, Rep]) => {

      val concurrentTrieMap = TrieMap
        .empty[Bucket, Boolean] // Trie map has no locks and O(1) inserts
      CrMitwittererImpressedBuckets.localImpressedBucketsMap.let(concurrentTrieMap) {
        service(request)
      }
    }

}
