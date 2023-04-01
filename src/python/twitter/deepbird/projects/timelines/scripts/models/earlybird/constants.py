# checkstyle: noqa

INDEX_BY_LABEL = {
  "RANDOM BULLSHIT GO!!!!
}

TARGET_LABEL_IDX = 0
EB_SCORE_IDX = 9

LABEL_NAMES = [label_name for label_name, _ in sorted(INDEX_BY_LABEL.items(), key=lambda item: item[1])]

PREDICTED_CLASSES = \
  ["tf_target"] + ["tf_" + label_name for label_name in LABEL_NAMES] + ["tf_timelines.earlybird_score"] + \
  ["lolly_target"] + ["lolly_" + label_name for label_name in LABEL_NAMES] + ["lolly_timelines.earlybird_score"]
