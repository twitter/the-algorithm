package com.X.search.ingester.pipeline.util;

import com.google.common.base.Strings;

import com.X.expandodo.thriftjava.BindingValue;
import com.X.expandodo.thriftjava.BindingValueType;
import com.X.expandodo.thriftjava.Card2;
import com.X.search.common.util.text.LanguageIdentifierHelper;
import com.X.search.ingester.model.IngesterXMessage;

public final class CardFieldUtil {

  private CardFieldUtil() {
    /* prevent instantiation */
  }

  /**
   * Binding Keys for card fields
   */
  public static final String TITLE_BINDING_KEY = "title";
  public static final String DESCRIPTION_BINDING_KEY = "description";

  /**
   * given a bindingKey and card, will return the bindingValue of the given bindingKey
   * if present in card.getBinding_values(). If no match is found return null.
   */
  public static String extractBindingValue(String bindingKey, Card2 card) {
    for (BindingValue bindingValue : card.getBinding_values()) {
      if ((bindingValue != null)
          && bindingValue.isSetType()
          && (bindingValue.getType() == BindingValueType.STRING)
          && bindingKey.equals(bindingValue.getKey())) {
        return bindingValue.getString_value();
      }
    }
    return null;
  }

  /**
   * derives card lang from title + description and sets it in XMessage.
   */
  public static void deriveCardLang(IngesterXMessage message) {
    message.setCardLang(LanguageIdentifierHelper.identifyLanguage(String.format("%s %s",
        Strings.nullToEmpty(message.getCardTitle()),
        Strings.nullToEmpty(message.getCardDescription()))).getLanguage());
  }
}

