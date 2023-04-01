package com.twitter.visibility.configapi.params

private[visibility] object TimelineConversationsDownrankingSpecificParams {

  object EnablePSpammyTweetDownrankConvosLowQualityParam extends RuleParam(false)

  object EnableRitoActionedTweetDownrankConvosLowQualityParam extends RuleParam(false)

  object EnableHighSpammyTweetContentScoreConvoDownrankAbusiveQualityRuleParam
      extends RuleParam(false)

  object EnableHighCryptospamScoreConvoDownrankAbusiveQualityRuleParam extends RuleParam(false)
}
