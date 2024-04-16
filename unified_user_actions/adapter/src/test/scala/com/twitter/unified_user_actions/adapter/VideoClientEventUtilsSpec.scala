package com.tw ter.un f ed_user_act ons.adapter

 mport com.tw ter.cl entapp.thr ftscala.Ampl fyDeta ls
 mport com.tw ter.cl entapp.thr ftscala. d aDeta ls
 mport com.tw ter.cl entapp.thr ftscala. d aType
 mport com.tw ter. d aserv ces.commons.thr ftscala. d aCategory
 mport com.tw ter.un f ed_user_act ons.adapter.cl ent_event.V deoCl entEventUt ls.getV deo tadata
 mport com.tw ter.un f ed_user_act ons.adapter.cl ent_event.V deoCl entEventUt ls.v deo dFrom d a dent f er
 mport com.tw ter.un f ed_user_act ons.thr ftscala._
 mport com.tw ter.ut l.mock.Mock o
 mport com.tw ter.v deo.analyt cs.thr ftscala._
 mport org.jun .runner.RunW h
 mport org.scalatest.funsu e.AnyFunSu e
 mport org.scalatest.matc rs.should.Matc rs
 mport org.scalatest.prop.TableDr venPropertyC cks
 mport org.scalatestplus.jun .JUn Runner

@RunW h(classOf[JUn Runner])
class V deoCl entEventUt lsSpec
    extends AnyFunSu e
    w h Matc rs
    w h Mock o
    w h TableDr venPropertyC cks {

  tra  F xture {
    val  d aDeta ls = Seq[ d aDeta ls](
       d aDeta ls(
        content d = So ("456"),
         d aType = So ( d aType.Consu rV deo),
        dynam cAds = So (false)),
       d aDeta ls(
        content d = So ("123"),
         d aType = So ( d aType.Consu rV deo),
        dynam cAds = So (false)),
       d aDeta ls(
        content d = So ("789"),
         d aType = So ( d aType.Consu rV deo),
        dynam cAds = So (false))
    )

    val v deo tadata: T etAct on nfo = T etAct on nfo.T etV deoWatch(
      T etV deoWatch( d aType = So ( d aType.Consu rV deo),  sMonet zable = So (false)))

    val v deo tadataW hAmpl fyDeta lsV deoType: T etAct on nfo = T etAct on nfo.T etV deoWatch(
      T etV deoWatch(
         d aType = So ( d aType.Consu rV deo),
         sMonet zable = So (false),
        v deoType = So ("content")))

    val val d d a dent f er:  d a dent f er =  d a dent f er. d aPlatform dent f er(
       d aPlatform dent f er( d a d = 123L,  d aCategory =  d aCategory.T etV deo))

    val  nval d d a dent f er:  d a dent f er =  d a dent f er.Ampl fyCard dent f er(
      Ampl fyCard dent f er(vmapUrl = "", content d = "")
    )
  }

  test("f ndV deo tadata") {
    new F xture {
      val testData = Table(
        ("testType", " d a d", " d a ems", "ampl fyDeta ls", "expectedOutput"),
        ("empty d aDeta ls", "123", Seq[ d aDeta ls](), None, None),
        (" d a dNotFound", "111",  d aDeta ls, None, None),
        (" d a dFound", "123",  d aDeta ls, None, So (v deo tadata)),
        (
          " d a dFound",
          "123",
           d aDeta ls,
          So (Ampl fyDeta ls(v deoType = So ("content"))),
          So (v deo tadataW hAmpl fyDeta lsV deoType))
      )

      forEvery(testData) {
        (
          _: Str ng,
           d a d: Str ng,
           d a ems: Seq[ d aDeta ls],
          ampl fyDeta ls: Opt on[Ampl fyDeta ls],
          expectedOutput: Opt on[T etAct on nfo]
        ) =>
          val actual = getV deo tadata( d a d,  d a ems, ampl fyDeta ls)
          assert(expectedOutput === actual)
      }
    }
  }

  test("v deo dFrom d a dent f er") {
    new F xture {
      val testData = Table(
        ("testType", " d a dent f er", "expectedOutput"),
        ("val d d a dent f erType", val d d a dent f er, So ("123")),
        (" nval d d a dent f erType",  nval d d a dent f er, None)
      )

      forEvery(testData) {
        (_: Str ng,  d a dent f er:  d a dent f er, expectedOutput: Opt on[Str ng]) =>
          val actual = v deo dFrom d a dent f er( d a dent f er)
          assert(expectedOutput === actual)
      }
    }
  }
}
