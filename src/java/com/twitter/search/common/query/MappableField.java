package com.twitter.search.common.query;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * The indices may map the fields declared here to fields internally without exposing their schemas
 * to other services. This can be used, for example, to set boosts for URL-like fields in Earlybird
 * without direct knowledge of the internal Earlybird field name
 */
public enum MappableField {
  REFERRAL,
  URL;

  static {
    ImmutableMap.Builder<MappableField, String> builder = ImmutableMap.builder();
    for (MappableField mappableField : MappableField.values()) {
      builder.put(mappableField, mappableField.toString().toLowerCase());
    }
    MAPPABLE_FIELD_TO_NAME_MAP = Maps.immutableEnumMap(builder.build());
  }

  private static final ImmutableMap<MappableField, String> MAPPABLE_FIELD_TO_NAME_MAP;

  /** Returns the name of the given MappableField. */
  public static String mappableFieldName(MappableField mappableField) {
    return MAPPABLE_FIELD_TO_NAME_MAP.get(mappableField);
  }

  /** Returns the name of this MappableField. */
  public String getName() {
    return MAPPABLE_FIELD_TO_NAME_MAP.get(this);
  }
}
