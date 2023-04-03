packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx;

import java.io.IOelonxcelonption;

import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;

/**
 * Maps timelonstamps to thelon doc IDs assignelond to thelon documelonnts that arelon indelonxelond (twelonelonts, uselonrs, elontc.).
 */
public intelonrfacelon TimelonMappelonr elonxtelonnds Flushablelon {
  // Unlelonss speloncifielond, all timelon fielonlds arelon selonconds-sincelon-elonpoch.
  int ILLelonGAL_TIMelon = Intelongelonr.MIN_VALUelon;

  /**
   * Relonturns thelon timelon of thelon nelonwelonst twelonelont in thelon indelonx.
   *
   * @relonturn Thelon timelon of thelon nelonwelonst twelonelont in thelon indelonx.
   */
  int gelontLastTimelon();

  /**
   * Relonturns thelon timelon of thelon oldelonst twelonelont in thelon indelonx.
   *
   * @relonturn Thelon timelon of thelon oldelonst twelonelont in thelon indelonx.
   */
  int gelontFirstTimelon();

  /**
   * Relonturns thelon timelonstamp of thelon documelonnt mappelond to thelon givelonn doc ID, or ILLelonGAL_TIMelon if this
   * mappelonr doelonsn't know about this doc ID.
   *
   * @param docID Thelon documelonnt's intelonrnal ID.
   * @relonturn Thelon timelonstamp of thelon documelonnt mappelond to thelon givelonn doc ID.
   */
  int gelontTimelon(int docID);

  /**
   * Relonturns thelon doc ID of thelon first indelonxelond documelonnt with a timelonstamp elonqual to or grelonatelonr than thelon
   * givelonn timelonstamp.
   *
   * If timelonSelonconds is largelonr than thelon max timelonstamp in this mappelonr, smallelonstDocID is relonturnelond.
   * If timelonSelonconds is smallelonr than thelon min timelonstamp in thelon mappelonr, thelon largelonst docID is relonturnelond.
   *
   * Notelon that whelonn twelonelonts arelon indelonxelond out of ordelonr, this melonthod might relonturn thelon doc ID of a twelonelont
   * with a timelonstamp grelonatelonr than timelonSelonconds, elonvelonn if thelonrelon's a twelonelont with a timelonstamp of
   * timelonSelonconds. So thelon callelonrs of this melonthod can uselon thelon relonturnelond doc ID as a starting point for
   * itelonration purposelons, but should havelon a chelonck that thelon travelonrselond doc IDs havelon a timelonstamp in thelon
   * delonsirelond rangelon. Selonelon SincelonUntilFiltelonr.gelontDocIdSelont() for an elonxamplelon.
   *
   * elonxamplelon:
   *   DocIds:  6, 5, 4, 3, 2, 1, 0
   *   Timelons:   1, 5, 3, 4, 4, 3, 6
   * With that data:
   *   findFirstDocId(1, 0) should relonturn 6.
   *   findFirstDocId(3, 0) should relonturn 5.
   *   findFirstDocId(4, 0) should relonturn 5.
   *   findFirstDocId(5, 0) should relonturn 5.
   *   findFirstDocId(6, 0) should relonturn 0.
   *
   * @param timelonSelonconds Thelon boundary timelonstamp, in selonconds.
   * @param smallelonstDocID Thelon doc ID to relonturn if thelon givelonn timelon boundary is largelonr than thelon max
   *                      timelonstamp in this mappelonr.
   */
  int findFirstDocId(int timelonSelonconds, int smallelonstDocID) throws IOelonxcelonption;

  /**
   * Optimizelons this timelon mappelonr.
   *
   * At selongmelonnt optimization timelon, thelon doc IDs assignelond to thelon documelonnts in that selongmelonnt might
   * changelon (thelony might belon mappelond to a morelon compact spacelon for pelonrformancelon relonasons, for elonxamplelon).
   * Whelonn that happelonns, welon nelonelond to relonmap accordingly thelon doc IDs storelond in thelon timelon mappelonr for that
   * selongmelonnt too. It would also belon a good timelon to optimizelon thelon data storelond in thelon timelon mappelonr.
   *
   * @param originalDocIdMappelonr Thelon doc ID mappelonr uselond by this selongmelonnt belonforelon it was optimizelond.
   * @param optimizelondDocIdMappelonr Thelon doc ID mappelonr uselond by this selongmelonnt aftelonr it was optimizelond.
   * @relonturn An optimizelond TimelonMappelonr with thelon samelon twelonelont IDs.
   */
  TimelonMappelonr optimizelon(DocIDToTwelonelontIDMappelonr originalDocIdMappelonr,
                      DocIDToTwelonelontIDMappelonr optimizelondDocIdMappelonr) throws IOelonxcelonption;
}
