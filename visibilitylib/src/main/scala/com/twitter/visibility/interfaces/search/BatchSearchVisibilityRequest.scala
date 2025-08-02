package com.twitter.visibility.interfaces.search

import com.twitter.visibility.interfaces.common.search.SearchVFRequestContetwittert
import com.twitter.visibility.models.ViewerContetwittert

case class BatchSearchVisibilityRequest(
  tweetContetwitterts: Seq[TweetContetwittert],
  viewerContetwittert: ViewerContetwittert,
  searchVFRequestContetwittert: SearchVFRequestContetwittert)
