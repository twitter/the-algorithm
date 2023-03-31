package com.twitter.search.ingester.pipeline.twitter.filters;

import java.util.EnumSet;
import java.util.Set;

import com.twitter.decider.Decider;
import com.twitter.search.common.decider.DeciderUtil;
import com.twitter.search.common.relevance.entities.TwitterMessage;
import com.twitter.search.common.relevance.entities.TwitterMessageUtil;

public class IngesterValidMessageFilter {
  public static final String KEEP_NULLCAST_DECIDER_KEY =
      "ingester_all_keep_nullcasts";
  public static final String STRIP_SUPPLEMENTARY_EMOJIS_DECIDER_KEY_PREFIX =
      "valid_message_filter_strip_supplementary_emojis_";

  protected final Decider decider;

  public IngesterValidMessageFilter(Decider decider) {
    this.decider = decider;
  }

  /**
   * Evaluate a message to see if it matches the filter or not.
   *
   * @param message to evaluate
   * @return true if this message should be emitted.
   */
  public boolean accepts(TwitterMessage message) {
    return TwitterMessageUtil.validateTwitterMessage(
        message, getStripEmojisFields(), acceptNullcast());
  }

  private Set<TwitterMessageUtil.Field> getStripEmojisFields() {
    Set<TwitterMessageUtil.Field> stripEmojisFields =
        EnumSet.noneOf(TwitterMessageUtil.Field.class);
    for (TwitterMessageUtil.Field field : TwitterMessageUtil.Field.values()) {
      if (DeciderUtil.isAvailableForRandomRecipient(
          decider,
          STRIP_SUPPLEMENTARY_EMOJIS_DECIDER_KEY_PREFIX + field.getNameForStats())) {
        stripEmojisFields.add(field);
      }
    }
    return stripEmojisFields;
  }

  protected final boolean acceptNullcast() {
    return DeciderUtil.isAvailableForRandomRecipient(decider, KEEP_NULLCAST_DECIDER_KEY);
  }
}
