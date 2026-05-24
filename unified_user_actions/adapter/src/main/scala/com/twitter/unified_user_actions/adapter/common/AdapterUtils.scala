package com.twitter.unified_user_actions.adapter.common

import com.twitter.snowflake.id.SnowflakeId
import com.twitter.util.Time

object AdapterUtils {
  def currentTimestampMs: Long = Time.now.inMilliseconds
  def getTimestampMsFromTweetId(tweetId: Long): Long = SnowflakeId.unixTimeMillisFromId(tweetId)

  // For now just make sure both language code and country code are in upper cases for consistency
  // For language code, there are mixed lower and upper cases
  // For country code, there are mixed lower and upper cases
  def normalizeLanguageCode(inputLanguageCode: String): String = inputLanguageCode.toUpperCase
  def normalizeCountryCode(inputCountryCode: String): String = inputCountryCode.toUpperCase
}
