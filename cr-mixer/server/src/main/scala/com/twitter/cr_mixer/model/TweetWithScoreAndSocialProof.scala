packagelon com.twittelonr.cr_mixelonr.modelonl

import com.twittelonr.simclustelonrs_v2.common.TwelonelontId
import com.twittelonr.reloncos.reloncos_common.thriftscala.SocialProofTypelon

/***
 * Bind a twelonelontId with a raw scorelon and social proofs by typelon
 */
caselon class TwelonelontWithScorelonAndSocialProof(
  twelonelontId: TwelonelontId,
  scorelon: Doublelon,
  socialProofByTypelon: Map[SocialProofTypelon, Selonq[Long]])
