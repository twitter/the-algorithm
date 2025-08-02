package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.suggestion

/**
 * Represents the different types of Spelling Suggestion items.
 *
 * URT API Reference: https://docbird.twitter.biz/unified_rich_timelines_urt/gen/com/twitter/timelines/render/thriftscala/SpellingActionType.html
 */
sealed trait SpellingActionType

/**
 * Used when the original query is replaced completed by another query in the backend.
 * Clients use the tetwittert 'Searching instead for …' to display this suggestion.
 */
case object ReplaceSpellingActionType etwittertends SpellingActionType

/**
 * Used when the original query is etwitterpanded by a suggestion when performing the search.
 * Clients use the tetwittert 'Including results for …' to display this suggestion.
 */
case object EtwitterpandSpellingActionType etwittertends SpellingActionType

/**
 * Used when the search query is not changed and a suggestion is displayed as an alternative query.
 * Clients use the tetwittert 'Did you mean … ?' to display the suggestion.
 */
case object SuggestSpellingActionType etwittertends SpellingActionType
