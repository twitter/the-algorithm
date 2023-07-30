package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.article

import com.X.product_mixer.core.model.marshalling.response.urt.item.article.ArticleSeedType
import com.X.product_mixer.core.model.marshalling.response.urt.item.article.FollowingListSeed
import com.X.product_mixer.core.model.marshalling.response.urt.item.article.FriendsOfFriendsSeed
import com.X.product_mixer.core.model.marshalling.response.urt.item.article.ListIdSeed
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleSeedTypeMarshaller @Inject() () {

  def apply(articleSeedType: ArticleSeedType): urt.ArticleSeedType =
    articleSeedType match {
      case FollowingListSeed => urt.ArticleSeedType.FollowingList
      case FriendsOfFriendsSeed => urt.ArticleSeedType.FriendsOfFriends
      case ListIdSeed => urt.ArticleSeedType.ListId
    }
}
