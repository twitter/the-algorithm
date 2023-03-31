# checkstyle: noqa

LABEL_NAMES = [
  "is_clicked",
  "is_favorited",
  "is_open_linked",
  "is_photo_expanded",
  "is_profile_clicked",
  "is_replied",
  "is_retweeted",
  "is_video_playback_50"
]

TARGET_LABEL_IDX = 0
EB_SCORE_IDX = len(LABEL_NAMES)+1

INDEX_BY_LABEL = { k:i+1 for k,i in enumerate(LABEL_NAMES) }

PREDICTED_CLASSES = 
[
  "tf_target",
  *["tf_" + label_name for label_name in LABEL_NAMES] + 
  "tf_timelines.earlybird_score",
  "lolly_target",
  *["lolly_" + label_name for label_name in LABEL_NAMES] + 
  "lolly_timelines.earlybird_score"
]
