package com.tw ter.ann.ut l

 mport com.tw ter.ann.common.{Appendable, Ent yEmbedd ng}
 mport com.tw ter.concurrent.AsyncStream
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.ut l.Future
 mport java.ut l.concurrent.atom c.Atom c nteger

object  ndexBu lderUt ls {
  val Log = Logger.apply()

  def addTo ndex[T](
    appendable: Appendable[T, _, _],
    embedd ngs: Seq[Ent yEmbedd ng[T]],
    concurrencyLevel:  nt
  ): Future[ nt] = {
    val count = new Atom c nteger()
    // Async stream allows us to procss at most concurrentLevel futures at a t  .
    Future.Un .before {
      val stream = AsyncStream.fromSeq(embedd ngs)
      val appendStream = stream.mapConcurrent(concurrencyLevel) { annEmbedd ng =>
        val processed = count. ncre ntAndGet()
         f (processed % 10000 == 0) {
          Log. nfo(s"Perfor d $processed updates")
        }
        appendable.append(annEmbedd ng)
      }
      appendStream.s ze
    }
  }
}
