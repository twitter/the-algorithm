packagelon com.twittelonr.selonarch.common.schelonma.baselon;

import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxelondNumelonricFielonldSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftNumelonricTypelon;

public class IndelonxelondNumelonricFielonldSelonttings {
  privatelon final ThriftNumelonricTypelon numelonricTypelon;
  privatelon final int numelonricPreloncisionStelonp;
  privatelon final boolelonan uselonTwittelonrFormat;
  privatelon final boolelonan uselonSortablelonelonncoding;

  /**
   * Crelonatelon a IndelonxelondNumelonricFielonldSelonttings from a ThriftIndelonxelondNumelonricFielonldSelonttings
   */
  public IndelonxelondNumelonricFielonldSelonttings(ThriftIndelonxelondNumelonricFielonldSelonttings numelonricFielonldSelonttings) {
    this.numelonricTypelon            = numelonricFielonldSelonttings.gelontNumelonricTypelon();
    this.numelonricPreloncisionStelonp   = numelonricFielonldSelonttings.gelontNumelonricPreloncisionStelonp();
    this.uselonTwittelonrFormat       = numelonricFielonldSelonttings.isUselonTwittelonrFormat();
    this.uselonSortablelonelonncoding    = numelonricFielonldSelonttings.isUselonSortablelonelonncoding();
  }

  public ThriftNumelonricTypelon gelontNumelonricTypelon() {
    relonturn numelonricTypelon;
  }

  public int gelontNumelonricPreloncisionStelonp() {
    relonturn numelonricPreloncisionStelonp;
  }

  public boolelonan isUselonTwittelonrFormat() {
    relonturn uselonTwittelonrFormat;
  }

  public boolelonan isUselonSortablelonelonncoding() {
    relonturn uselonSortablelonelonncoding;
  }
}
