# checkstyle: noqa
from twml.feature_config import FeatureConfigBuilder


def get_feature_config(data_spec_path, label):
  return (
    FeatureConfigBuilder(data_spec_path=data_spec_path, debug=True)
    .batch_add_features(
      [
        ("ebd.author_specific_score", "A"),
        ("ebd.has_diff_lang", "A"),
        ("ebd.has_english_tweet_diff_ui_lang", "A"),
        ("ebd.has_english_ui_diff_tweet_lang", "A"),
        ("ebd.is_self_tweet", "A"),
        ("ebd.tweet_age_in_secs", "A"),
        ("encoded_tweet_features.favorite_count", "A"),
        ("encoded_tweet_features.from_verified_account_flag", "A"),
        ("encoded_tweet_features.has_card_flag", "A"),
        # ("encoded_tweet_features.has_consumer_video_flag", "A"),
        ("encoded_tweet_features.has_image_url_flag", "A"),
        ("encoded_tweet_features.has_link_flag", "A"),
        ("encoded_tweet_features.has_multiple_hashtags_or_trends_flag", "A"),
        # ("encoded_tweet_features.has_multiple_media_flag", "A"),
        ("encoded_tweet_features.has_native_image_flag", "A"),
        ("encoded_tweet_features.has_news_url_flag", "A"),
        ("encoded_tweet_features.has_periscope_flag", "A"),
        ("encoded_tweet_features.has_pro_video_flag", "A"),
        ("encoded_tweet_features.has_quote_flag", "A"),
        ("encoded_tweet_features.has_trend_flag", "A"),
        ("encoded_tweet_features.has_video_url_flag", "A"),
        ("encoded_tweet_features.has_vine_flag", "A"),
        ("encoded_tweet_features.has_visible_link_flag", "A"),
        ("encoded_tweet_features.is_offensive_flag", "A"),
        ("encoded_tweet_features.is_reply_flag", "A"),
        ("encoded_tweet_features.is_retweet_flag", "A"),
        ("encoded_tweet_features.is_sensitive_content", "A"),
        # ("encoded_tweet_features.is_user_new_flag", "A"),
        ("encoded_tweet_features.language", "A"),
        ("encoded_tweet_features.link_language", "A"),
        ("encoded_tweet_features.num_hashtags", "A"),
        ("encoded_tweet_features.num_mentions", "A"),
        # ("encoded_tweet_features.profile_is_egg_flag", "A"),
        ("encoded_tweet_features.reply_count", "A"),
        ("encoded_tweet_features.retweet_count", "A"),
        ("encoded_tweet_features.text_score", "A"),
        ("encoded_tweet_features.user_reputation", "A"),
        ("extended_encoded_tweet_features.embeds_impression_count", "A"),
        ("extended_encoded_tweet_features.embeds_impression_count_v2", "A"),
        ("extended_encoded_tweet_features.embeds_url_count", "A"),
        ("extended_encoded_tweet_features.embeds_url_count_v2", "A"),
        ("extended_encoded_tweet_features.favorite_count_v2", "A"),
        ("extended_encoded_tweet_features.label_abusive_hi_rcl_flag", "A"),
        ("extended_encoded_tweet_features.label_dup_content_flag", "A"),
        ("extended_encoded_tweet_features.label_nsfw_hi_prc_flag", "A"),
        ("extended_encoded_tweet_features.label_nsfw_hi_rcl_flag", "A"),
        ("extended_encoded_tweet_features.label_spam_flag", "A"),
        ("extended_encoded_tweet_features.label_spam_hi_rcl_flag", "A"),
        ("extended_encoded_tweet_features.quote_count", "A"),
        ("extended_encoded_tweet_features.reply_count_v2", "A"),
        ("extended_encoded_tweet_features.retweet_count_v2", "A"),
        ("extended_encoded_tweet_features.weighted_favorite_count", "A"),
        ("extended_encoded_tweet_features.weighted_quote_count", "A"),
        ("extended_encoded_tweet_features.weighted_reply_count", "A"),
        ("extended_encoded_tweet_features.weighted_retweet_count", "A"),
      ]
    )
    .add_labels(
      [
        label,  # Tensor index: 0
        "recap.engagement.is_clicked",  # Tensor index: 1
        "recap.engagement.is_favorited",  # Tensor index: 2
        "recap.engagement.is_open_linked",  # Tensor index: 3
        "recap.engagement.is_photo_expanded",  # Tensor index: 4
        "recap.engagement.is_profile_clicked",  # Tensor index: 5
        "recap.engagement.is_replied",  # Tensor index: 6
        "recap.engagement.is_retweeted",  # Tensor index: 7
        "recap.engagement.is_video_playback_50",  # Tensor index: 8
        "timelines.earlybird_score",  # Tensor index: 9
      ]
    )
    .define_weight("meta.record_weight/type=earlybird")
    .build()
  )
