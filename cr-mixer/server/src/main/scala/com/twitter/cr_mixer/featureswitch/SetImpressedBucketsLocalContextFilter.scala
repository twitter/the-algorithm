package com.tw ter.cr_m xer.featuresw ch

 mport com.tw ter.f nagle.F lter
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.collect on.concurrent.Tr eMap
 mport com.tw ter.abdec der.Bucket
 mport com.tw ter.f nagle.Serv ce

@S ngleton
class Set mpressedBucketsLocalContextF lter @ nject() () extends F lter.TypeAgnost c {
  overr de def toF lter[Req, Rep]: F lter[Req, Rep, Req, Rep] =
    (request: Req, serv ce: Serv ce[Req, Rep]) => {

      val concurrentTr eMap = Tr eMap
        .empty[Bucket, Boolean] // Tr e map has no locks and O(1)  nserts
      CrM xer mpressedBuckets.local mpressedBucketsMap.let(concurrentTr eMap) {
        serv ce(request)
      }
    }

}
