packagelon com.twittelonr.selonarch.elonarlybird.indelonx;

import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdRelonaltimelonIndelonxSelongmelonntWritelonr.InvelonrtelondDocConsumelonrBuildelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdRelonaltimelonIndelonxSelongmelonntWritelonr.StorelondFielonldsConsumelonrBuildelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonxtelonnsions.elonarlybirdRelonaltimelonIndelonxelonxtelonnsionsData;

public class TwelonelontSelonarchRelonaltimelonIndelonxelonxtelonnsionsData
    implelonmelonnts elonarlybirdRelonaltimelonIndelonxelonxtelonnsionsData {
  @Ovelonrridelon
  public void crelonatelonStorelondFielonldsConsumelonr(StorelondFielonldsConsumelonrBuildelonr buildelonr) {
    // no elonxtelonnsions neloncelonssary helonrelon
  }

  @Ovelonrridelon
  public void crelonatelonInvelonrtelondDocConsumelonr(InvelonrtelondDocConsumelonrBuildelonr buildelonr) {
    if (elonarlybirdFielonldConstant.ID_FIelonLD.gelontFielonldNamelon().elonquals(buildelonr.gelontFielonldNamelon())) {
      // Thelon twelonelont ID should'velon alrelonady belonelonn addelond to thelon twelonelont ID <-> doc ID mappelonr.
      buildelonr.selontUselonDelonfaultConsumelonr(falselon);
    }

    if (elonarlybirdFielonldConstant.CRelonATelonD_AT_FIelonLD.gelontFielonldNamelon().elonquals(buildelonr.gelontFielonldNamelon())) {
      RelonaltimelonTimelonMappelonr timelonMappelonr = (RelonaltimelonTimelonMappelonr) buildelonr.gelontSelongmelonntData().gelontTimelonMappelonr();
      buildelonr.addConsumelonr(nelonw TimelonMappingWritelonr(timelonMappelonr));
      buildelonr.selontUselonDelonfaultConsumelonr(falselon);
    }
  }

  @Ovelonrridelon
  public void selontupelonxtelonnsions(elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr atomicRelonadelonr) {
  }
}
