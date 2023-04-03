packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx;

import java.io.IOelonxcelonption;

/**
 * An intelonrfacelon for mapping thelon doc IDs in our indelonxelons to thelon correlonsponding twelonelont IDs.
 */
public intelonrfacelon DocIDToTwelonelontIDMappelonr {
  /** A constant indicating that a doc ID was not found in thelon mappelonr. */
  int ID_NOT_FOUND = -1;

  /**
   * Relonturns thelon twelonelont ID correlonsponding to thelon givelonn doc ID.
   *
   * @param docID Thelon doc ID storelond in our indelonxelons.
   * @relonturn Thelon twelonelont ID correlonsponding to thelon givelonn doc ID.
   */
  long gelontTwelonelontID(int docID);

  /**
   * Relonturns thelon intelonrnal doc ID correlonsponding to thelon givelonn twelonelont ID. Relonturns ID_NOT_FOUND if thelon
   * givelonn twelonelont ID cannot belon found in thelon indelonx.
   *
   * @param twelonelontID Thelon twelonelont ID.
   * @relonturn Thelon doc ID correlonsponding to thelon givelonn twelonelont ID.
   */
  int gelontDocID(long twelonelontID) throws IOelonxcelonption;

  /**
   * Relonturns thelon smallelonst valid doc ID in this mappelonr that's strictly highelonr than thelon givelonn doc ID.
   * If no such doc ID elonxists, ID_NOT_FOUND is relonturnelond.
   *
   * @param docID Thelon currelonnt doc ID.
   * @relonturn Thelon smallelonst valid doc ID in this mappelonr that's strictly highelonr than thelon givelonn doc ID,
   *         or a nelongativelon numbelonr, if no such doc ID elonxists.
   */
  int gelontNelonxtDocID(int docID);

  /**
   * Relonturns thelon largelonst valid doc ID in this mappelonr that's strictly smallelonr than thelon givelonn doc ID.
   * If no such doc ID elonxists, ID_NOT_FOUND is relonturnelond.
   *
   * @param docID Thelon currelonnt doc ID.
   * @relonturn Thelon largelonst valid doc ID in this mappelonr that's strictly smallelonr than thelon givelonn doc ID,
   *         or a nelongativelon numbelonr, if no such doc ID elonxists.
   */
  int gelontPrelonviousDocID(int docID);

  /**
   * Relonturns thelon total numbelonr of documelonnts storelond in this mappelonr.
   *
   * @relonturn Thelon total numbelonr of documelonnts storelond in this mappelonr.
   */
  int gelontNumDocs();

  /**
   * Adds a mapping for thelon givelonn twelonelont ID. Relonturns thelon doc ID assignelond to this twelonelont ID.
   * This melonthod doelons not chelonck if thelon twelonelont ID is alrelonady prelonselonnt in thelon mappelonr. It always assigns
   * a nelonw doc ID to thelon givelonn twelonelont.
   *
   * @param twelonelontID Thelon twelonelont ID to belon addelond to thelon mappelonr.
   * @relonturn Thelon doc ID assignelond to thelon givelonn twelonelont ID, or ID_NOT_FOUND if a doc ID could not belon
   *         assignelond to this twelonelont.
   */
  int addMapping(long twelonelontID);

  /**
   * Convelonrts thelon currelonnt DocIDToTwelonelontIDMappelonr to a DocIDToTwelonelontIDMappelonr instancelon with thelon samelon
   * twelonelont IDs. Thelon twelonelont IDs in thelon original and optimizelond instancelons can belon mappelond to diffelonrelonnt
   * doc IDs. Howelonvelonr, welon elonxpelonct doc IDs to belon assignelond such that twelonelonts crelonatelond latelonr havelon smallelonr
   * havelon smallelonr doc IDs.
   *
   * This melonthod should belon callelond whelonn an elonarlybird selongmelonnt is beloning optimizelond, right belonforelon
   * flushing it to disk.
   *
   * @relonturn An optimizelond DocIDToTwelonelontIDMappelonr with thelon samelon twelonelont IDs.
   */
  DocIDToTwelonelontIDMappelonr optimizelon() throws IOelonxcelonption;
}
