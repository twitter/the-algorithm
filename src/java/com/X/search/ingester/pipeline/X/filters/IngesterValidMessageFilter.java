package com.X.search.ingester.pipeline.X.filters;

import java.util.EnumSet;
import java.util.Set;

import com.X.decider.Decider;
import com.X.search.common.decider.DeciderUtil;
import com.X.search.common.relevance.entities.XMessage;
import com.X.search.common.relevance.entities.XMessageUtil;

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
  public boolean accepts(XMessage message) {
    return XMessageUtil.validateXMessage(
        message, getStripEmojisFields(), acceptNullcast());
  }

  private Set<XMessageUtil.Field> getStripEmojisFields() {
    Set<XMessageUtil.Field> stripEmojisFields =
        EnumSet.noneOf(XMessageUtil.Field.class);
    for (XMessageUtil.Field field : XMessageUtil.Field.values()) {
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
