package com.tw ter.search.common.ut l.earlyb rd;

 mport java.ut l.concurrent.T  Un ;

 mport com.tw ter.search.earlyb rd.thr ft.Thr ft togramSett ngs;

/**
 * A ut l y class to prov de so  funct ons for TermStat st cs request process ng
 */
publ c f nal class TermStat st csUt l {

  pr vate stat c f nal org.slf4j.Logger LOG =
      org.slf4j.LoggerFactory.getLogger(TermStat st csUt l.class);

  pr vate TermStat st csUt l() {
  }

  /**
   * Determ ne t  b ns ze base on sett ngs  n Thr ft togramSett ngs.granular y
   */
  publ c stat c  nt determ neB nS ze(Thr ft togramSett ngs  togramSett ngs) {
    f nal  nt DEFAULT_B NS ZE = ( nt) T  Un .HOURS.toSeconds(1);
     nt b nS ze;
    sw ch ( togramSett ngs.getGranular y()) {
      case DAYS:
        b nS ze = ( nt) T  Un .DAYS.toSeconds(1);
        break;
      case HOURS:
        b nS ze = ( nt) T  Un .HOURS.toSeconds(1);
        break;
      case M NUTES:
        b nS ze = ( nt) T  Un .M NUTES.toSeconds(1);
        break;
      case CUSTOM:
        b nS ze =  togramSett ngs. sSetB nS ze nSeconds()
                      ?  togramSett ngs.getB nS ze nSeconds()
                      : DEFAULT_B NS ZE;
        break;
      default:
        b nS ze = DEFAULT_B NS ZE;
        LOG.warn("Unknown Thr ft togramGranular yType {} us ng default b ns ze: {}",
                  togramSett ngs.getGranular y(), DEFAULT_B NS ZE);
    }

    return b nS ze;
  }
}
