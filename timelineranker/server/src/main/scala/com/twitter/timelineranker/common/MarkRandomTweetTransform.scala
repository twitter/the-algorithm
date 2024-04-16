package com.tw ter.t  l neranker.common

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.model.Cand dateT et
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  
 mport scala.ut l.Random

/**
 * p cks up one or more random t ets and sets  s t etFeatures. sRandomT et f eld to true.
 */
class MarkRandomT etTransform(
   ncludeRandomT etProv der: DependencyProv der[Boolean],
  randomGenerator: Random = new Random(T  .now. nM ll seconds),
   ncludeS ngleRandomT etProv der: DependencyProv der[Boolean],
  probab l yRandomT etProv der: DependencyProv der[Double])
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {

  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    val  ncludeRandomT et =  ncludeRandomT etProv der(envelope.query)
    val  ncludeS ngleRandomT et =  ncludeS ngleRandomT etProv der(envelope.query)
    val probab l yRandomT et = probab l yRandomT etProv der(envelope.query)
    val searchResults = envelope.searchResults

     f (! ncludeRandomT et || searchResults. sEmpty) { // random t et off
      Future.value(envelope)
    } else  f ( ncludeS ngleRandomT et) { // p ck only one
      val random dx = randomGenerator.next nt(searchResults.s ze)
      val randomT et = searchResults(random dx)
      val randomT etW hFlag = randomT et.copy(
        t etFeatures = randomT et.t etFeatures
          .orElse(So (Cand dateT et.DefaultFeatures))
          .map(_.copy( sRandomT et = So (true)))
      )
      val updatedSearchResults = searchResults.updated(random dx, randomT etW hFlag)

      Future.value(envelope.copy(searchResults = updatedSearchResults))
    } else { // p ck t ets w h perT etProbab l y
      val updatedSearchResults = searchResults.map { result =>
         f (randomGenerator.nextDouble() < probab l yRandomT et) {
          result.copy(
            t etFeatures = result.t etFeatures
              .orElse(So (Cand dateT et.DefaultFeatures))
              .map(_.copy( sRandomT et = So (true))))

        } else
          result
      }

      Future.value(envelope.copy(searchResults = updatedSearchResults))
    }
  }
}
