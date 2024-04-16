package com.tw ter.un f ed_user_act ons.enr c r.hydrator

 mport com.google.common.cac .Cac Bu lder
 mport com.tw ter.dynmap.DynMap
 mport com.tw ter.graphql.thr ftscala.GraphQlRequest
 mport com.tw ter.graphql.thr ftscala.GraphQlResponse
 mport com.tw ter.graphql.thr ftscala.GraphqlExecut onServ ce
 mport com.tw ter. nject.Test
 mport com.tw ter.un f ed_user_act ons.enr c r.Enr c rF xture
 mport com.tw ter.un f ed_user_act ons.enr c r.FatalExcept on
 mport com.tw ter.un f ed_user_act ons.enr c r.hcac .LocalCac 
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntEnvelop
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt dType
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt nstruct on
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntKey
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Author nfo
 mport com.tw ter.ut l.Awa 
 mport com.tw ter.ut l.Future
 mport org.mock o.Argu ntMatc rs
 mport org.mock o.Mock oSugar

class DefaultHydratorTest extends Test w h Mock oSugar {

  tra  F xtures extends Enr c rF xture {
    val cac  = new LocalCac [Enr ch ntKey, DynMap](
      underly ng = Cac Bu lder
        .newBu lder()
        .max mumS ze(10)
        .bu ld[Enr ch ntKey, Future[DynMap]]())

    val cl ent = mock[GraphqlExecut onServ ce.F nagledCl ent]
    val key = Enr ch ntKey(Enr ch nt dType.T et d, 1L)
    val envelop = Enr ch ntEnvelop(123L, mkUUAT etEvent(1L), t et nfoEnr ch ntPlan)

    def mkGraphQLResponse(author d: Long): GraphQlResponse =
      GraphQlResponse(
        So (
          s"""
           |{
           |  "data": {
           |    "t et_result_by_rest_ d": {
           |      "result": {
           |        "core": {
           |          "user": {
           |            "legacy": {
           |              " d_str": "$author d"
           |            }
           |          }
           |        }
           |      }
           |    }
           |  }
           |}
           |""".str pMarg n
        ))
  }

  test("non-fatal errors should proceed as normal") {
    new F xtures {
      val hydrator = new DefaultHydrator(cac , cl ent)

      // w n graphql cl ent encounter any except on
      w n(cl ent.graphql(Argu ntMatc rs.any[GraphQlRequest]))
        .t nReturn(Future.except on(new  llegalStateExcept on("any except on")))

      val actual =
        Awa .result(hydrator.hydrate(Enr ch nt nstruct on.T etEnr ch nt, So (key), envelop))

      // t n t  or g nal envelop  s expected
      assert(envelop == actual)
    }
  }

  test("fatal errors should return a future except on") {
    new F xtures {
      val hydrator = new DefaultHydrator(cac , cl ent)

      // w n graphql cl ent encounter a fatal except on
      w n(cl ent.graphql(Argu ntMatc rs.any[GraphQlRequest]))
        .t nReturn(Future.except on(new FatalExcept on("fatal except on") {}))

      val actual = hydrator.hydrate(Enr ch nt nstruct on.T etEnr ch nt, So (key), envelop)

      // t n a fa led future  s expected
      assertFa ledFuture[FatalExcept on](actual)
    }
  }

  test("author_ d should be hydrated from graphql respond") {
    new F xtures {
      val hydrator = new DefaultHydrator(cac , cl ent)

      w n(cl ent.graphql(Argu ntMatc rs.any[GraphQlRequest]))
        .t nReturn(Future.value(mkGraphQLResponse(888L)))

      val actual = hydrator.hydrate(Enr ch nt nstruct on.T etEnr ch nt, So (key), envelop)

      assertFutureValue(
        actual,
        envelop.copy(uua = mkUUAT etEvent(1L, So (Author nfo(So (888L))))))
    }
  }

  test("w n Author nfo  s populated, t re should be no hydrat on") {
    new F xtures {
      val hydrator = new DefaultHydrator(cac , cl ent)

      w n(cl ent.graphql(Argu ntMatc rs.any[GraphQlRequest]))
        .t nReturn(Future.value(mkGraphQLResponse(333L)))

      val expected = envelop.copy(uua =
        mkUUAT etEvent(t et d = 3L, author = So (Author nfo(author d = So (222)))))
      val actual = hydrator.hydrate(Enr ch nt nstruct on.T etEnr ch nt, So (key), expected)

      assertFutureValue(actual, expected)
    }
  }
}
