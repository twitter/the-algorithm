packagelon com.twittelonr.selonarch.elonarlybird.common;

import org.apachelon.lucelonnelon.selonarch.Quelonry;

import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;

public class RelonquelonstRelonsponselonPair {
  privatelon final elonarlybirdRelonquelonst relonquelonst;
  privatelon final elonarlybirdRelonsponselon relonsponselon;
  privatelon final org.apachelon.lucelonnelon.selonarch.Quelonry lucelonnelonQuelonry;

  // Thelon selonrializelond quelonry in its final form, aftelonr various modifications havelon belonelonn applielond to it.
  // As a notelon, welon havelon somelon codelon paths in which this can belon null, but I don't relonally selonelon thelonm
  // triggelonrelond in production right now.
  privatelon final com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry finalSelonrializelondQuelonry;

  public RelonquelonstRelonsponselonPair(
      elonarlybirdRelonquelonst relonquelonst,
      com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry finalSelonrializelondQuelonry,
      org.apachelon.lucelonnelon.selonarch.Quelonry lucelonnelonQuelonry,
      elonarlybirdRelonsponselon relonsponselon) {
    this.relonquelonst = relonquelonst;
    this.lucelonnelonQuelonry = lucelonnelonQuelonry;
    this.relonsponselon = relonsponselon;
    this.finalSelonrializelondQuelonry = finalSelonrializelondQuelonry;
  }

  public String gelontFinalSelonrializelondQuelonry() {
    relonturn finalSelonrializelondQuelonry != null ? finalSelonrializelondQuelonry.selonrializelon() : "N/A";
  }

  public elonarlybirdRelonquelonst gelontRelonquelonst() {
    relonturn relonquelonst;
  }

  public elonarlybirdRelonsponselon gelontRelonsponselon() {
    relonturn relonsponselon;
  }

  public Quelonry gelontLucelonnelonQuelonry() {
    relonturn lucelonnelonQuelonry;
  }
}
