package com.tw ter.servo.repos ory

 mport com.tw ter.servo.cac ._
 mport com.tw ter.ut l.Future

class Cach ngCounterKeyValueRepos ory[K](
  underly ng: CounterKeyValueRepos ory[K],
  cac : CounterCac [K],
  observer: Cac Observer = NullCac Observer)
    extends CounterKeyValueRepos ory[K] {

  def apply(keys: Seq[K]): Future[KeyValueResult[K, Long]] = {
    val un queKeys = keys.d st nct
    cac .get(un queKeys) flatMap { cac dResults =>
      recordResults(cac dResults)

      val m ssed = cac dResults.notFound ++ cac dResults.fa led.keySet
      readThrough(m ssed.toSeq) map { readResults =>
        KeyValueResult(cac dResults.found) ++ readResults
      }
    }
  }

  pr vate def readThrough(keys: Seq[K]): Future[KeyValueResult[K, Long]] =
     f (keys. sEmpty) {
      KeyValueResult.emptyFuture
    } else {
      underly ng(keys) onSuccess { readResults =>
        for ((k, v) <- readResults.found) {
          cac .add(k, v)
        }
      }
    }

  pr vate def recordResults(cac dResults: KeyValueResult[K, Long]): Un  = {
    cac dResults.found.keys foreach { key =>
      observer.h (key.toStr ng)
    }
    cac dResults.notFound foreach { key =>
      observer.m ss(key.toStr ng)
    }
    observer.fa lure(cac dResults.fa led.s ze)
  }
}
