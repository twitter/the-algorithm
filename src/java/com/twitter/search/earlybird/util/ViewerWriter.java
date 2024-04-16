package com.tw ter.search.earlyb rd.ut l;

 mport java. o. OExcept on;

/**
 *  nterface class for wr er.  Wr er should be passed  n
 * and have t se  thods.  Currently keeps t  h erarchy for
 * completed and val d json,  thods m rror t  ones found  n
 * JsonWr er
 * http://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/stream/JsonWr er.html
 */
publ c  nterface V e rWr er {
  /**
   * Wr es a mark for t  beg nn ng of an array.
   */
  V e rWr er beg nArray() throws  OExcept on;

  /**
   * Wr es a mark for t  beg nn ng of an object.
   */
  V e rWr er beg nObject() throws  OExcept on;

  /**
   * Wr es a mark for t  end of an array.
   */
  V e rWr er endArray() throws  OExcept on;

  /**
   * Wr es a mark for t  end of an object.
   */
  V e rWr er endObject() throws  OExcept on;

  /**
   * Wr es t  na  (key) of a property.
   */
  V e rWr er na (Str ng f eld) throws  OExcept on;

  /**
   * Wr es t  value of a property.
   */
  V e rWr er value(Str ng s) throws  OExcept on;

  /**
   * Wr es a new l ne.
   */
  V e rWr er newl ne() throws  OExcept on;
}
