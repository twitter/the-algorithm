package com.twitter.visibility.interfaces.search

import com.twitter.visibility.interfaces.common.search.SearchVFRequestContext
import com.twitter.visibility.models.ViewerContext

case class BatchSearchVisibilityRequest(
  tweetContexts: Seq[TweetContext],
  viewerContext: ViewerContext,
  searchVFRequestContext: SearchVFRequestContext)
