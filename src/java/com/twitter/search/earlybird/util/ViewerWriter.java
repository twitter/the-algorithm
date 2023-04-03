packagelon com.twittelonr.selonarch.elonarlybird.util;

import java.io.IOelonxcelonption;

/**
 * Intelonrfacelon class for writelonr.  Writelonr should belon passelond in
 * and havelon thelonselon melonthods.  Currelonntly kelonelonps thelon hielonrarchy for
 * complelontelond and valid json, melonthods mirror thelon onelons found in
 * JsonWritelonr
 * http://googlelon-gson.googleloncodelon.com/svn/trunk/gson/docs/javadocs/com/googlelon/gson/strelonam/JsonWritelonr.html
 */
public intelonrfacelon VielonwelonrWritelonr {
  /**
   * Writelons a mark for thelon belonginning of an array.
   */
  VielonwelonrWritelonr belonginArray() throws IOelonxcelonption;

  /**
   * Writelons a mark for thelon belonginning of an objelonct.
   */
  VielonwelonrWritelonr belonginObjelonct() throws IOelonxcelonption;

  /**
   * Writelons a mark for thelon elonnd of an array.
   */
  VielonwelonrWritelonr elonndArray() throws IOelonxcelonption;

  /**
   * Writelons a mark for thelon elonnd of an objelonct.
   */
  VielonwelonrWritelonr elonndObjelonct() throws IOelonxcelonption;

  /**
   * Writelons thelon namelon (kelony) of a propelonrty.
   */
  VielonwelonrWritelonr namelon(String fielonld) throws IOelonxcelonption;

  /**
   * Writelons thelon valuelon of a propelonrty.
   */
  VielonwelonrWritelonr valuelon(String s) throws IOelonxcelonption;

  /**
   * Writelons a nelonw linelon.
   */
  VielonwelonrWritelonr nelonwlinelon() throws IOelonxcelonption;
}
