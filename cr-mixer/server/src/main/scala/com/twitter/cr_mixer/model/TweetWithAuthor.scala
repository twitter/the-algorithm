package com.twitter.cr_mixer.model

import com.twitter.simclusters_v2.common.TweetId
import com.twitter.simclusters_v2.common.UserId

case class TweetWithAuthor(tweetId: TweetId, authorId: UserId)
