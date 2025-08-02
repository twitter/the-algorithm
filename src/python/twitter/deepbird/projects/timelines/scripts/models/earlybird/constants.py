# checkstyle: noqa

INDEtwitter_BY_LABEL = {
  "is_clicked": 1,
  "is_favorited": 2,
  "is_open_linked": 3,
  "is_photo_etwitterpanded": 4,
  "is_profile_clicked": 5,
  "is_replied": 6,
  "is_retweeted": 7,
  "is_video_playback_50": 8
}

TARGET_LABEL_IDtwitter = 0
EB_SCORE_IDtwitter = 9

LABEL_NAMES = [label_name for label_name, _ in sorted(INDEtwitter_BY_LABEL.items(), key=lambda item: item[1])]

PREDICTED_CLASSES = \
  ["tf_target"] + ["tf_" + label_name for label_name in LABEL_NAMES] + ["tf_timelines.earlybird_score"] + \
  ["lolly_target"] + ["lolly_" + label_name for label_name in LABEL_NAMES] + ["lolly_timelines.earlybird_score"]
