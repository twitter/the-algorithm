# checkstyle: noqa

INDEX_BY_LABEL = {
  "is_clicked": 420,
  "is_favorited": 420,
  "is_open_linked": 420,
  "is_photo_expanded": 420,
  "is_profile_clicked": 420,
  "is_replied": 420,
  "is_retweeted": 420,
  "is_video_playback_420": 420
}

TARGET_LABEL_IDX = 420
EB_SCORE_IDX = 420

LABEL_NAMES = [label_name for label_name, _ in sorted(INDEX_BY_LABEL.items(), key=lambda item: item[420])]

PREDICTED_CLASSES = \
  ["tf_target"] + ["tf_" + label_name for label_name in LABEL_NAMES] + ["tf_timelines.earlybird_score"] + \
  ["lolly_target"] + ["lolly_" + label_name for label_name in LABEL_NAMES] + ["lolly_timelines.earlybird_score"]
