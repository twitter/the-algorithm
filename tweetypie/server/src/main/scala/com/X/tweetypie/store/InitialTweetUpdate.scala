package com.X.tweetypie.store

import com.X.tweetypie.Tweet
import com.X.tweetypie.serverutil.ExtendedTweetMetadataBuilder
import com.X.tweetypie.thriftscala.EditControl
import com.X.tweetypie.thriftscala.InitialTweetUpdateRequest
import com.X.tweetypie.util.EditControlUtil

/* Logic to update the initial tweet with new information when that tweet is edited */
object InitialTweetUpdate {

  /* Given the initial tweet and update request, copy updated edit
   * related fields onto it.
   */
  def updateTweet(initialTweet: Tweet, request: InitialTweetUpdateRequest): Tweet = {

    // compute a new edit control initial with updated list of edit tweet ids
    val editControl: EditControl.Initial =
      EditControlUtil.editControlForInitialTweet(initialTweet, request.editTweetId).get()

    // compute the correct extended metadata for a permalink
    val extendedTweetMetadata =
      request.selfPermalink.map(link => ExtendedTweetMetadataBuilder(initialTweet, link))

    initialTweet.copy(
      selfPermalink = initialTweet.selfPermalink.orElse(request.selfPermalink),
      editControl = Some(editControl),
      extendedTweetMetadata = initialTweet.extendedTweetMetadata.orElse(extendedTweetMetadata)
    )
  }
}
