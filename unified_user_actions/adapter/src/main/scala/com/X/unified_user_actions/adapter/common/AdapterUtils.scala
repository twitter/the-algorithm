package com.X.unified_user_actions.adapter.common

import com.X.snowflake.id.SnowflakeId
import com.X.util.Time

object AdapterUtils {
  def currentTimestampMs: Long = Time.now.inMilliseconds
  def getTimestampMsFromTweetId(tweetId: Long): Long = SnowflakeId.unixTimeMillisFromId(tweetId)

  // For now just make sure both language code and country code are in upper cases for consistency
  // For language code, there are mixed lower and upper cases
  // For country code, there are mixed lower and upper cases
  def normalizeLanguageCode(inputLanguageCode: String): String = inputLanguageCode.toUpperCase
  def normalizeCountryCode(inputCountryCode: String): String = inputCountryCode.toUpperCase
}
