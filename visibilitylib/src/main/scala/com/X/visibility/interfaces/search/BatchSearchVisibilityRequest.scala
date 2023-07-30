package com.X.visibility.interfaces.search

import com.X.visibility.interfaces.common.search.SearchVFRequestContext
import com.X.visibility.models.ViewerContext

case class BatchSearchVisibilityRequest(
  tweetContexts: Seq[TweetContext],
  viewerContext: ViewerContext,
  searchVFRequestContext: SearchVFRequestContext)
