package com.twitter.tweetypie.federated.columns

import com.twitter.tweetypie.TweetId

// Case class to be used for grouping Stitch requests
// for Federated Fields
case class FederatedFieldReq(tweetId: TweetId, fieldId: Short)
