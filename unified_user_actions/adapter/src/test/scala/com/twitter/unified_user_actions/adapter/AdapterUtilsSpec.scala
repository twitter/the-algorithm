package com.twitter.unified_user_actions.adapter

import com.twitter.inject.Test
import com.twitter.unified_user_actions.adapter.common.AdapterUtils
import com.twitter.util.Time

class AdapterUtilsSpec extends Test {
  trait Fixture {

    val frozenTime: Time = Time.fromMilliseconds(1658949273000L)
    val languageCode = "en"
    val countryCode = "us"
  }

  test("tests") {
    new Fixture {
      Time.withTimeAt(frozenTime) { _ =>
        val actual = Time.fromMilliseconds(AdapterUtils.currentTimestampMs)
        assert(frozenTime === actual)
      }

      val actionedTweetId = 1554576940756246272L
      assert(AdapterUtils.getTimestampMsFromTweetId(actionedTweetId) === 1659474999976L)

      assert(languageCode.toUpperCase === AdapterUtils.normalizeLanguageCode(languageCode))
      assert(countryCode.toUpperCase === AdapterUtils.normalizeCountryCode(countryCode))
    }
  }
}
