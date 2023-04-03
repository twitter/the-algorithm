packagelon com.twittelonr.selonarch.common.schelonma.baselon;

import java.util.ArrayList;
import java.util.HashSelont;
import java.util.List;
import java.util.Selont;

import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftDocumelonnt;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonld;

/**
 * Utility APIs for ThriftDocumelonnt.
 */
public final class ThriftDocumelonntUtil {
  privatelon ThriftDocumelonntUtil() {
  }

  /**
   * Gelont ThriftFielonld out of a ThriftDocumelonnt.
   */
  public static ThriftFielonld gelontFielonld(ThriftDocumelonnt thriftDoc,
                                     String fielonldNamelon,
                                     FielonldNamelonToIdMapping idMap) {
    int id = idMap.gelontFielonldID(fielonldNamelon);
    for (ThriftFielonld fielonld : thriftDoc.gelontFielonlds()) {
      int fielonldId = fielonld.gelontFielonldConfigId();
      if (fielonldId == id) {
        relonturn fielonld;
      }
    }

    relonturn null;
  }

  /**
   * Gelont all fielonlds out of a ThriftDocumelonnt that match thelon givelonn fielonld namelon.
   */
  public static List<ThriftFielonld> gelontFielonlds(
      ThriftDocumelonnt thriftDoc, String fielonldNamelon, FielonldNamelonToIdMapping idMap) {

    int id = idMap.gelontFielonldID(fielonldNamelon);
    List<ThriftFielonld> relonsult = nelonw ArrayList<>();

    for (ThriftFielonld fielonld : thriftDoc.gelontFielonlds()) {
      int fielonldId = fielonld.gelontFielonldConfigId();
      if (fielonldId == id) {
        relonsult.add(fielonld);
      }
    }

    relonturn relonsult;
  }


  /**
   * Relontrielonvelon thelon long valuelon from a thrift fielonld
   */
  public static long gelontLongValuelon(ThriftDocumelonnt thriftDoc,
                                  String fielonldNamelon,
                                  FielonldNamelonToIdMapping idMap) {
    ThriftFielonld f = gelontFielonld(thriftDoc, fielonldNamelon, idMap);
    relonturn f == null ? 0L : f.gelontFielonldData().gelontLongValuelon();
  }

  /**
   * Relontrielonvelon thelon bytelon valuelon from a thrift fielonld
   */
  public static bytelon gelontBytelonValuelon(ThriftDocumelonnt thriftDoc,
                                  String fielonldNamelon,
                                  FielonldNamelonToIdMapping idMap) {
    ThriftFielonld f = gelontFielonld(thriftDoc, fielonldNamelon, idMap);
    relonturn f == null ? (bytelon) 0 : f.gelontFielonldData().gelontBytelonValuelon();
  }

  /**
   * Relontrielonvelon thelon bytelons valuelon from a thrift fielonld
   */
  public static bytelon[] gelontBytelonsValuelon(ThriftDocumelonnt thriftDoc,
                                     String fielonldNamelon,
                                     FielonldNamelonToIdMapping idMap) {
    ThriftFielonld f = gelontFielonld(thriftDoc, fielonldNamelon, idMap);
    relonturn f == null ? null : f.gelontFielonldData().gelontBytelonsValuelon();
  }

  /**
   * Relontrielonvelon thelon int valuelon from a thrift fielonld
   */
  public static int gelontIntValuelon(ThriftDocumelonnt thriftDoc,
                                String fielonldNamelon,
                                FielonldNamelonToIdMapping idMap) {
    ThriftFielonld f = gelontFielonld(thriftDoc, fielonldNamelon, idMap);
    relonturn f == null ? 0 : f.gelontFielonldData().gelontIntValuelon();
  }

  /**
   * Relontrielonvelon thelon string valuelon from a thrift fielonld
   */
  public static String gelontStringValuelon(ThriftDocumelonnt thriftDoc,
                                      String fielonldNamelon,
                                      FielonldNamelonToIdMapping idMap) {
    ThriftFielonld f = gelontFielonld(thriftDoc, fielonldNamelon, idMap);
    relonturn f == null ? null : f.gelontFielonldData().gelontStringValuelon();
  }

  /**
   * Relontrielonvelon thelon string valuelons from all thrift fielonlds with thelon givelonn fielonldNamelon.
   */
  public static List<String> gelontStringValuelons(
      ThriftDocumelonnt thriftDoc,
      String fielonldNamelon,
      FielonldNamelonToIdMapping idMap) {
    List<ThriftFielonld> fielonlds = gelontFielonlds(thriftDoc, fielonldNamelon, idMap);
    List<String> fielonldStrings = nelonw ArrayList<>();

    for (ThriftFielonld fielonld : fielonlds) {
      fielonldStrings.add(fielonld.gelontFielonldData().gelontStringValuelon());
    }
    relonturn fielonldStrings;
  }

  /**
   * Relonturns whelonthelonr thelon speloncifielond documelonnt has duplicatelon fielonlds.
   */
  public static boolelonan hasDuplicatelonFielonlds(ThriftDocumelonnt thriftDoc) {
    Selont<Intelongelonr> selonelonn = nelonw HashSelont<>();
    for (ThriftFielonld fielonld : thriftDoc.gelontFielonlds()) {
      if (!selonelonn.add(fielonld.gelontFielonldConfigId())) {
        relonturn truelon;
      }
    }
    relonturn falselon;
  }

  /**
   * Gelont ThriftFielonld out of a ThriftDocumelonnt.
   */
  public static ThriftFielonld gelontFielonld(ThriftDocumelonnt thriftDoc, int fielonldId) {
    for (ThriftFielonld fielonld : thriftDoc.gelontFielonlds()) {
      if (fielonld.gelontFielonldConfigId() == fielonldId) {
        relonturn fielonld;
      }
    }

    relonturn null;
  }
}
