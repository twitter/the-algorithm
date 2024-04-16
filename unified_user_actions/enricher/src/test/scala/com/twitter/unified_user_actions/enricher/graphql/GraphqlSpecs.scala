package com.tw ter.un f ed_user_act ons.enr c r.graphql

 mport com.tw ter.dynmap.DynMap
 mport com.tw ter. nject.Test
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try
 mport org.scalatest.matc rs.should.Matc rs

class GraphqlSpecs extends Test w h Matc rs {
  tra  F xtures {
    val sampleError = """
      |{
      |  "errors": [
      |    {
      |      " ssage": "So  err msg!",
      |      "code": 366,
      |      "k nd": "Val dat on",
      |      "na ": "QueryV olat onError",
      |      "s ce": "Cl ent",
      |      "trac ng": {
      |        "trace_ d": "1234567890"
      |      }
      |    }
      |  ]
      |}""".str pMarg n

    val sampleVal dRsp =
      """
        |{
        |  "data": {
        |    "t et_result_by_rest_ d": {
        |      "result": {
        |        "core": {
        |          "user": {
        |            "legacy": {
        |              " d_str": "12"
        |            }
        |          }
        |        }
        |      }
        |    }
        |  }
        |}
        |""".str pMarg n

    val sampleVal dRspExpected = Return(
      Set(("data.t et_result_by_rest_ d.result.core.user.legacy. d_str", "12")))
    val sampleErrorExpected = Throw(
      GraphqlRspErrors(
        DynMap.from(
          "errors" -> L st(
            Map(
              " ssage" -> "So  err msg!",
              "code" -> 366,
              "k nd" -> "Val dat on",
              "na " -> "QueryV olat onError",
              "s ce" -> "Cl ent",
              "trac ng" -> Map("trace_ d" -> "1234567890")
            )))))
    def toFlattened(testStr: Str ng): Try[Set[(Str ng, Any)]] =
      GraphqlRspParser.toDynMap(testStr).map { dm => dm.valuesFlattened.toSet }
  }

  test("Graphql Response Parser") {
    new F xtures {
      toFlattened(sampleVal dRsp) shouldBe sampleVal dRspExpected
      toFlattened(sampleError) shouldBe sampleErrorExpected
    }
  }
}
