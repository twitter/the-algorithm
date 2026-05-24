namespace java com.twitter.follow_recommendations.thriftjava
#@namespace scala com.twitter.follow_recommendations.thriftscala
#@namespace strato com.twitter.follow_recommendations

enum DisplayLocation {
    SIDEBAR = 0
    PROFILE_SIDEBAR = 2
    CLUSTER_FOLLOW = 7
    NEW_USER_SARUS_BACKFILL = 12
    PROFILE_DEVICE_FOLLOW = 23
    RECOS_BACKFILL = 32
    HOME_TIMELINE = 39    # HOME_TIMELINE_WTF in Hermit
    PROFILE_TOP_FOLLOWING = 42
    PROFILE_TOP_FOLLOWERS = 43
    PEOPLE_PLUS_PLUS = 47
    EXPLORE_TAB = 57
    MagicRecs = 59        # Account recommendation in notification
    AB_UPLOAD_INJECTION = 60
    /**
     * To prevent setting 2 display locations with the same index in FRS.
     *
     * The display location should be added to the following files:
     *  - follow-recommendations-service/thrift/src/main/thrift/display_location.thrift
     *  - follow-recommendations-service/thrift/src/main/thrift/logging/display_location.thrift
     *  - follow-recommendations-service/common/src/main/scala/com/twitter/follow_recommendations/common/models/DisplayLocation.scala
   */
    CAMPAIGN_FORM = 61
    RUX_LANDING_PAGE = 62
    PROFILE_BONUS_FOLLOW = 63
    ELECTION_EXPLORE_WTF = 64
    HTL_BONUS_FOLLOW = 65
    TOPIC_LANDING_PAGE_HEADER = 66
    NUX_PYMK = 67
    NUX_INTERESTS = 68
    REACTIVE_FOLLOW = 69
    RUX_PYMK = 70
    INDIA_COVID19_CURATED_ACCOUNTS_WTF = 71
    NUX_TOPIC_BONUS_FOLLOW = 72
    TWEET_NOTIFICATION_RECS = 73
    HTL_SPACE_HOSTS = 74
    POST_NUX_FOLLOW_TASK = 75
    TOPIC_LANDING_PAGE = 76
    USER_TYPEAHEAD_PREFETCH = 77
    HOME_TIMELINE_RELATABLE_ACCOUNTS = 78
    NUX_GEO_CATEGORY = 79
    NUX_INTERESTS_CATEGORY = 80
    NUX_PYMK_CATEGORY = 81
    TOP_ARTICLES = 82
    HOME_TIMELINE_TWEET_RECS = 83
    HTL_BULK_FRIEND_FOLLOWS = 84
    NUX_AUTO_FOLLOW = 85
    SEARCH_BONUS_FOLLOW = 86
    CONTENT_RECOMMENDER = 87
    HOME_TIMELINE_REVERSE_CHRON = 88
}
