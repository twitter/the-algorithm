package com.tw ter.un f ed_user_act ons.enr c r.hcac 

 mport com.google.common.cac .Cac 
 mport com.google.common.cac .Cac Bu lder
 mport com.tw ter.f nagle.stats. n moryStatsRece ver
 mport com.tw ter. nject.Test
 mport com.tw ter.ut l.Awa 
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  
 mport java.ut l.concurrent.T  Un 
 mport java.lang.{ nteger => J nt}

class LocalCac Test extends Test {

  tra  F xture {
    val t   = T  .fromM ll seconds(123456L)
    val ttl = 5
    val maxS ze = 10

    val underly ng: Cac [J nt, Future[J nt]] = Cac Bu lder
      .newBu lder()
      .exp reAfterWr e(ttl, T  Un .SECONDS)
      .max mumS ze(maxS ze)
      .bu ld[J nt, Future[J nt]]()

    val stats = new  n moryStatsRece ver

    val cac  = new LocalCac [J nt, J nt](
      underly ng = underly ng,
      statsRece ver = stats
    )

    def getCounts(counterNa : Str ng*): Long = stats.counter(counterNa : _*)()
  }

  test("s mple local cac  works") {
    new F xture {
      T  .w hT  At(t  ) { _ =>
        assert(cac .s ze === 0)

        (1 to maxS ze + 1).foreach {  d =>
          cac .getOrElseUpdate( d)(Future.value( d))

          val actual = Awa .result(cac .get( d).get)
          assert(actual ===  d)
        }
        assert(cac .s ze === maxS ze)

        assert(getCounts("gets") === 2 * (maxS ze + 1))
        assert(getCounts("h s") === maxS ze + 1)
        assert(getCounts("m sses") === maxS ze + 1)
        assert(getCounts("sets", "ev ct ons", "fa led_futures") === 0)

        cac .reset()
        assert(cac .s ze === 0)
      }
    }
  }

  test("getOrElseUpdate successful futures") {
    new F xture {
      T  .w hT  At(t  ) { _ =>
        assert(cac .s ze === 0)

        (1 to maxS ze + 1).foreach { _ =>
          cac .getOrElseUpdate(1) {
            Future.value(1)
          }
        }
        assert(cac .s ze === 1)

        assert(getCounts("gets") === maxS ze + 1)
        assert(getCounts("h s") === maxS ze)
        assert(getCounts("m sses") === 1)
        assert(getCounts("sets", "ev ct ons", "fa led_futures") === 0)

        cac .reset()
        assert(cac .s ze === 0)
      }
    }
  }

  test("getOrElseUpdate Fa led Futures") {
    new F xture {
      T  .w hT  At(t  ) { _ =>
        assert(cac .s ze === 0)

        (1 to maxS ze + 1).foreach {  d =>
          cac .getOrElseUpdate( d)(Future.except on(new  llegalArgu ntExcept on("")))
          assert(cac .get( d).map {
            Awa .result(_)
          } === None)
        }
        assert(cac .s ze === 0)

        assert(getCounts("gets") === 2 * (maxS ze + 1))
        assert(getCounts("h s", "m sses", "sets") === 0)
        assert(getCounts("ev ct ons") === maxS ze + 1)
        assert(getCounts("fa led_futures") === maxS ze + 1)
      }
    }
  }

  test("Set successful Future") {
    new F xture {
      T  .w hT  At(t  ) { _ =>
        assert(cac .s ze === 0)

        cac .set(1, Future.value(2))
        assert(Awa .result(cac .get(1).get) === 2)
        assert(getCounts("gets") === 1)
        assert(getCounts("h s") === 1)
        assert(getCounts("m sses") === 0)
        assert(getCounts("sets") === 1)
        assert(getCounts("ev ct ons", "fa led_futures") === 0)
      }
    }
  }

  test("Ev ct") {
    new F xture {
      T  .w hT  At(t  ) { _ =>
        assert(cac .s ze === 0)

        // need to use reference  re!!!
        val f1 = Future.value( nt2 nteger(1))
        val f2 = Future.value( nt2 nteger(2))
        cac .set(1, f2)
        cac .ev ct(1, f1)
        cac .ev ct(1, f2)
        assert(getCounts("gets", "h s", "m sses") === 0)
        assert(getCounts("sets") === 1)
        assert(getCounts("ev ct ons") === 1) // not 2
        assert(getCounts("fa led_futures") === 0)
      }
    }
  }

  test("Set Fa led Futures") {
    new F xture {
      T  .w hT  At(t  ) { _ =>
        assert(cac .s ze === 0)

        cac .set(1, Future.except on(new  llegalArgu ntExcept on("")))
        assert(cac .s ze === 0)

        assert(getCounts("gets", "h s", "m sses", "sets") === 0)
        assert(getCounts("ev ct ons") === 1)
        assert(getCounts("fa led_futures") === 1)
      }
    }
  }
}
