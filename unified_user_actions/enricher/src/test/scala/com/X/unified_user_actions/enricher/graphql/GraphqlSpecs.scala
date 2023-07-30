package com.X.unified_user_actions.enricher.graphql

import com.X.dynmap.DynMap
import com.X.inject.Test
import com.X.util.Return
import com.X.util.Throw
import com.X.util.Try
import org.scalatest.matchers.should.Matchers

class GraphqlSpecs extends Test with Matchers {
  trait Fixtures {
    val sampleError = """
      |{
      |  "errors": [
      |    {
      |      "message": "Some err msg!",
      |      "code": 366,
      |      "kind": "Validation",
      |      "name": "QueryViolationError",
      |      "source": "Client",
      |      "tracing": {
      |        "trace_id": "1234567890"
      |      }
      |    }
      |  ]
      |}""".stripMargin

    val sampleValidRsp =
      """
        |{
        |  "data": {
        |    "tweet_result_by_rest_id": {
        |      "result": {
        |        "core": {
        |          "user": {
        |            "legacy": {
        |              "id_str": "12"
        |            }
        |          }
        |        }
        |      }
        |    }
        |  }
        |}
        |""".stripMargin

    val sampleValidRspExpected = Return(
      Set(("data.tweet_result_by_rest_id.result.core.user.legacy.id_str", "12")))
    val sampleErrorExpected = Throw(
      GraphqlRspErrors(
        DynMap.from(
          "errors" -> List(
            Map(
              "message" -> "Some err msg!",
              "code" -> 366,
              "kind" -> "Validation",
              "name" -> "QueryViolationError",
              "source" -> "Client",
              "tracing" -> Map("trace_id" -> "1234567890")
            )))))
    def toFlattened(testStr: String): Try[Set[(String, Any)]] =
      GraphqlRspParser.toDynMap(testStr).map { dm => dm.valuesFlattened.toSet }
  }

  test("Graphql Response Parser") {
    new Fixtures {
      toFlattened(sampleValidRsp) shouldBe sampleValidRspExpected
      toFlattened(sampleError) shouldBe sampleErrorExpected
    }
  }
}
