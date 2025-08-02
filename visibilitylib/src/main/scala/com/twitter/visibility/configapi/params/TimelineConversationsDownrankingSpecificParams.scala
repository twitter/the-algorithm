package com.twitter.visibility.configapi.params

private[visibility] object TimelineConversationsDownrankingSpecificParams {

  object EnablePSpammyTweetDownrankConvosLowQualityParam etwittertends RuleParam(false)

  object EnableRitoActionedTweetDownrankConvosLowQualityParam etwittertends RuleParam(false)

  object EnableHighSpammyTweetContentScoreConvoDownrankAbusiveQualityRuleParam
      etwittertends RuleParam(false)

  object EnableHighCryptospamScoreConvoDownrankAbusiveQualityRuleParam etwittertends RuleParam(false)
}
