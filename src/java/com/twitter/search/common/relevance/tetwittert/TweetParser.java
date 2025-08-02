package com.twitter.search.common.relevance.tetwittert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

import com.twitter.common.tetwittert.util.CharSequenceUtils;
import com.twitter.common_internal.tetwittert.version.PenguinVersion;
import com.twitter.search.common.indetwittering.thriftjava.ThriftEtwitterpandedUrl;
import com.twitter.search.common.relevance.entities.TwitterMessage;
import com.twitter.search.common.relevance.features.TweetTetwittertFeatures;
import com.twitter.search.common.util.tetwittert.NormalizerHelper;
import com.twitter.search.common.util.tetwittert.Smileys;
import com.twitter.search.common.util.tetwittert.TokenizerHelper;
import com.twitter.search.common.util.tetwittert.TokenizerResult;

/**
 * A parser to etwittertract very basic information from a tweet.
 */
public class TweetParser {
  private static final boolean DO_NOT_REMOVE_WWW = false;

  /** Parses the given TwitterMessage. */
  public void parseTweet(TwitterMessage message) {
    parseTweet(message, false, true);
  }

  /** Parses the given TwitterMessage. */
  public void parseTweet(TwitterMessage message,
                         boolean useEntitiesFromTweetTetwittert,
                         boolean parseUrls) {
    for (PenguinVersion penguinVersion : message.getSupportedPenguinVersions()) {
      parseTweet(message, useEntitiesFromTweetTetwittert, parseUrls, penguinVersion);
    }
  }

  /** Parses the given TwitterMessage. */
  public void parseTweet(TwitterMessage message,
                         boolean useEntitiesFromTweetTetwittert,
                         boolean parseUrls,
                         PenguinVersion penguinVersion) {
    TweetTetwittertFeatures tetwittertFeatures = message.getTweetTetwittertFeatures(penguinVersion);
    String rawTetwittert = message.getTetwittert();
    Locale locale = message.getLocale();

    // don't lower case first.
    String normalizedTetwittert = NormalizerHelper.normalizeKeepCase(rawTetwittert, locale, penguinVersion);
    String lowercasedNormalizedTetwittert =
      CharSequenceUtils.toLowerCase(normalizedTetwittert, locale).toString();

    tetwittertFeatures.setNormalizedTetwittert(lowercasedNormalizedTetwittert);

    TokenizerResult result = TokenizerHelper.tokenizeTweet(normalizedTetwittert, locale, penguinVersion);
    List<String> tokens = new ArrayList<>(result.tokens);
    tetwittertFeatures.setTokens(tokens);
    tetwittertFeatures.setTokenSequence(result.tokenSequence);

    if (parseUrls) {
      parseUrls(message, tetwittertFeatures);
    }

    tetwittertFeatures.setStrippedTokens(result.strippedDownTokens);
    tetwittertFeatures.setNormalizedStrippedTetwittert(Joiner.on(" ").skipNulls()
                                                 .join(result.strippedDownTokens));

    // Sanity checks, make sure there is no null token list.
    if (tetwittertFeatures.getTokens() == null) {
      tetwittertFeatures.setTokens(Collections.<String>emptyList());
    }
    if (tetwittertFeatures.getResolvedUrlTokens() == null) {
      tetwittertFeatures.setResolvedUrlTokens(Collections.<String>emptyList());
    }
    if (tetwittertFeatures.getStrippedTokens() == null) {
      tetwittertFeatures.setStrippedTokens(Collections.<String>emptyList());
    }

    setHashtagsAndMentions(message, tetwittertFeatures, penguinVersion);
    tetwittertFeatures.setStocks(sanitizeTokenizerResults(result.stocks, '$'));
    tetwittertFeatures.setHasQuestionMark(findQuestionMark(tetwittertFeatures));

    // Set smiley polarities.
    tetwittertFeatures.setSmileys(result.smileys);
    for (String smiley : tetwittertFeatures.getSmileys()) {
      if (Smileys.isValidSmiley(smiley)) {
        boolean polarity = Smileys.getPolarity(smiley);
        if (polarity) {
          tetwittertFeatures.setHasPositiveSmiley(true);
        } else {
          tetwittertFeatures.setHasNegativeSmiley(true);
        }
      }
    }
    message.setTokenizedCharSequence(penguinVersion, result.rawSequence);

    if (useEntitiesFromTweetTetwittert) {
      takeEntities(message, tetwittertFeatures, result, penguinVersion);
    }
  }

  /** Parse the URLs in the given TwitterMessage. */
  public void parseUrls(TwitterMessage message) {
    for (PenguinVersion penguinVersion : message.getSupportedPenguinVersions()) {
      parseUrls(message, message.getTweetTetwittertFeatures(penguinVersion));
    }
  }

  /** Parse the URLs in the given TwitterMessage. */
  public void parseUrls(TwitterMessage message, TweetTetwittertFeatures tetwittertFeatures) {
    if (message.getEtwitterpandedUrlMap() != null) {
      Set<String> urlsToTokenize = Sets.newLinkedHashSet();
      for (ThriftEtwitterpandedUrl url : message.getEtwitterpandedUrlMap().values()) {
        if (url.isSetEtwitterpandedUrl()) {
          urlsToTokenize.add(url.getEtwitterpandedUrl());
        }
        if (url.isSetCanonicalLastHopUrl()) {
          urlsToTokenize.add(url.getCanonicalLastHopUrl());
        }
      }
      TokenizerResult resolvedUrlResult =
          TokenizerHelper.tokenizeUrls(urlsToTokenize, message.getLocale(), DO_NOT_REMOVE_WWW);
      List<String> urlTokens = new ArrayList<>(resolvedUrlResult.tokens);
      tetwittertFeatures.setResolvedUrlTokens(urlTokens);
    }
  }

  private void takeEntities(TwitterMessage message,
                            TweetTetwittertFeatures tetwittertFeatures,
                            TokenizerResult result,
                            PenguinVersion penguinVersion) {
    if (message.getHashtags().isEmpty()) {
      // add hashtags to TwitterMessage if it doens't already have them, from
      // JSON entities, this happens when we do offline indetwittering
      for (String hashtag : sanitizeTokenizerResults(result.hashtags, '#')) {
        message.addHashtag(hashtag);
      }
    }

    if (message.getMentions().isEmpty()) {
      // add mentions to TwitterMessage if it doens't already have them, from
      // JSON entities, this happens when we do offline indetwittering
      for (String mention : sanitizeTokenizerResults(result.mentions, '@')) {
        message.addMention(mention);
      }
    }

    setHashtagsAndMentions(message, tetwittertFeatures, penguinVersion);
  }

  private void setHashtagsAndMentions(TwitterMessage message,
                                      TweetTetwittertFeatures tetwittertFeatures,
                                      PenguinVersion penguinVersion) {
    tetwittertFeatures.setHashtags(message.getNormalizedHashtags(penguinVersion));
    tetwittertFeatures.setMentions(message.getLowercasedMentions());
  }

  // The strings in the mentions, hashtags and stocks lists in TokenizerResult should already have
  // the leading characters ('@', '#' and '$') stripped. So in most cases, this sanitization is not
  // needed. However, sometimes Penguin tokenizes hashtags, cashtags and mentions incorrectly
  // (for etwitterample, when using the Korean tokenizer for tokens like ~@mention or ?#hashtag -- see
  // SEARCHQUAL-11924 for more details). So we're doing this etwittertra sanitization here to try to work
  // around these tokenization issues.
  private List<String> sanitizeTokenizerResults(List<String> tokens, char tokenSymbol) {
    List<String> sanitizedTokens = new ArrayList<String>();
    for (String token : tokens) {
      int indetwitterOfTokenSymbol = token.indetwitterOf(tokenSymbol);
      if (indetwitterOfTokenSymbol < 0) {
        sanitizedTokens.add(token);
      } else {
        String sanitizedToken = token.substring(indetwitterOfTokenSymbol + 1);
        if (!sanitizedToken.isEmpty()) {
          sanitizedTokens.add(sanitizedToken);
        }
      }
    }
    return sanitizedTokens;
  }

  /** Determines if the normalized tetwittert of the given features contain a question mark. */
  public static boolean findQuestionMark(TweetTetwittertFeatures tetwittertFeatures) {
    // t.co links don't contain ?'s, so it's not necessary to subtract ?'s occurring in Urls
    // the tweet tetwittert always contains t.co, even if the display url is different
    // all links on twitter are now wrapped into t.co
    return tetwittertFeatures.getNormalizedTetwittert().contains("?");
  }
}
