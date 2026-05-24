package com.twitter.follow_recommendations.common.models

import com.twitter.hermit.model.Algorithm
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier

/**
 * contains information if a candidate is from a candidate source generated using the following signals.
 */
case class AddressBookMetadata(
  inForwardPhoneBook: Boolean,
  inReversePhoneBook: Boolean,
  inForwardEmailBook: Boolean,
  inReverseEmailBook: Boolean)

object AddressBookMetadata {

  val ForwardPhoneBookCandidateSource = CandidateSourceIdentifier(
    Algorithm.ForwardPhoneBook.toString)

  val ReversePhoneBookCandidateSource = CandidateSourceIdentifier(
    Algorithm.ReversePhoneBook.toString)

  val ForwardEmailBookCandidateSource = CandidateSourceIdentifier(
    Algorithm.ForwardEmailBook.toString)

  val ReverseEmailBookCandidateSource = CandidateSourceIdentifier(
    Algorithm.ReverseEmailBookIbis.toString)

}
