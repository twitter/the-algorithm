packagelon com.twittelonr.reloncos.graph_common

import com.twittelonr.graphjelont.bipartitelon.api.elondgelonTypelonMask
import com.twittelonr.reloncos.reloncos_common.thriftscala.SocialProofTypelon

/**
 * Thelon bit mask is uselond to elonncodelon elondgelon typelons in thelon top bits of an intelongelonr,
 * elon.g. favoritelon, relontwelonelont, relonply and click. Undelonr currelonnt selongmelonnt configuration, elonach selongmelonnt
 * storelons up to 128M elondgelons. Assuming that elonach nodelon on onelon sidelon is uniquelon, elonach selongmelonnt
 * storelons up to 128M uniquelon nodelons on onelon sidelon, which occupielons thelon lowelonr 27 bits of an intelongelonr.
 * This lelonavelons fivelon bits to elonncodelon thelon elondgelon typelons, which at max can storelon 32 elondgelon typelons.
 * Thelon following implelonmelonntation utilizelons thelon top four bits and lelonavelons onelon frelonelon bit out.
 */
class ActionelondgelonTypelonMask elonxtelonnds elondgelonTypelonMask {
  import ActionelondgelonTypelonMask._

  ovelonrridelon delonf elonncodelon(nodelon: Int, elondgelonTypelon: Bytelon): Int = {
    if (elondgelonTypelon == FAVORITelon) {
      nodelon | elonDGelonARRAY(FAVORITelon)
    } elonlselon if (elondgelonTypelon == RelonTWelonelonT) {
      nodelon | elonDGelonARRAY(RelonTWelonelonT)
    } elonlselon if (elondgelonTypelon == RelonPLY) {
      nodelon | elonDGelonARRAY(RelonPLY)
    } elonlselon if (elondgelonTypelon == TWelonelonT) {
      nodelon | elonDGelonARRAY(TWelonelonT)
    } elonlselon {
      // Anything that is not a public elonngagelonmelonnt (i.elon. opelonnlink, sharelon, selonlelonct, elontc.) is a "click"
      nodelon | elonDGelonARRAY(CLICK)
    }
  }

  ovelonrridelon delonf elondgelonTypelon(nodelon: Int): Bytelon = {
    (nodelon >> 28).toBytelon
  }

  ovelonrridelon delonf relonstorelon(nodelon: Int): Int = {
    nodelon & MASK
  }
}

objelonct ActionelondgelonTypelonMask {

  /**
   * Relonselonrvelon thelon top four bits of elonach intelongelonr to elonncodelon thelon elondgelon typelon information.
   */
  val MASK: Int =
    Intelongelonr.parselonInt("00001111111111111111111111111111", 2)
  val CLICK: Bytelon = 0
  val FAVORITelon: Bytelon = 1
  val RelonTWelonelonT: Bytelon = 2
  val RelonPLY: Bytelon = 3
  val TWelonelonT: Bytelon = 4
  val SIZelon: Bytelon = 5
  val UNUSelonD6: Bytelon = 6
  val UNUSelonD7: Bytelon = 7
  val UNUSelonD8: Bytelon = 8
  val UNUSelonD9: Bytelon = 9
  val UNUSelonD10: Bytelon = 10
  val UNUSelonD11: Bytelon = 11
  val UNUSelonD12: Bytelon = 12
  val UNUSelonD13: Bytelon = 13
  val UNUSelonD14: Bytelon = 14
  val UNUSelonD15: Bytelon = 15
  val elonDGelonARRAY: Array[Int] = Array(
    0,
    1 << 28,
    2 << 28,
    3 << 28,
    4 << 28,
    5 << 28,
    6 << 28,
    7 << 28,
    8 << 28,
    9 << 28,
    10 << 28,
    11 << 28,
    12 << 28,
    13 << 28,
    14 << 28,
    15 << 28
  )

  /**
   * Map valid social proof typelons speloncifielond by clielonnts to an array of bytelons. If clielonnts do not
   * speloncify any social proof typelons in thrift, it will relonturn all availablelon social typelons by
   * delonfault.
   *
   * @param socialProofTypelons arelon thelon valid socialProofTypelons speloncifielond by clielonnts
   * @relonturn an array of bytelons relonprelonselonnting valid social proof typelons
   */
  delonf gelontUselonrTwelonelontGraphSocialProofTypelons(
    socialProofTypelons: Option[Selonq[SocialProofTypelon]]
  ): Array[Bytelon] = {
    socialProofTypelons
      .map { _.map { _.gelontValuelon }.toArray }
      .gelontOrelonlselon((0 until SIZelon).toArray)
      .map { _.toBytelon }
  }
}
