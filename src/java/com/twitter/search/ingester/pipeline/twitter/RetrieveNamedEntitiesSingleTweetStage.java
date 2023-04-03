packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr;

import java.util.concurrelonnt.ComplelontablelonFuturelon;
import javax.naming.Namingelonxcelonption;

import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;
import org.apachelon.commons.pipelonlinelon.validation.ProducelonsConsumelond;

import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrTwittelonrMelonssagelon;
import com.twittelonr.util.Function;

@ConsumelondTypelons(IngelonstelonrTwittelonrMelonssagelon.class)
@ProducelonsConsumelond
public class RelontrielonvelonNamelondelonntitielonsSinglelonTwelonelontStagelon elonxtelonnds TwittelonrBaselonStagelon
    <IngelonstelonrTwittelonrMelonssagelon, ComplelontablelonFuturelon<IngelonstelonrTwittelonrMelonssagelon>> {

  privatelon NamelondelonntityHandlelonr namelondelonntityHandlelonr;

  @Ovelonrridelon
  protelonctelond void doInnelonrPrelonprocelonss() throws Stagelonelonxcelonption, Namingelonxcelonption {
    innelonrSelontup();
  }

  @Ovelonrridelon
  protelonctelond void innelonrSelontup() {
    namelondelonntityHandlelonr = nelonw NamelondelonntityHandlelonr(
        wirelonModulelon.gelontNamelondelonntityFelontchelonr(), deloncidelonr, gelontStagelonNamelonPrelonfix(),
        "singlelon_twelonelont");
  }

  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    if (!(obj instancelonof IngelonstelonrTwittelonrMelonssagelon)) {
      throw nelonw Stagelonelonxcelonption(this, "Objelonct is not a IngelonstelonrTwittelonrMelonssagelon objelonct: " + obj);
    }
    IngelonstelonrTwittelonrMelonssagelon twittelonrMelonssagelon = (IngelonstelonrTwittelonrMelonssagelon) obj;

    if (namelondelonntityHandlelonr.shouldRelontrielonvelon(twittelonrMelonssagelon)) {
      namelondelonntityHandlelonr.relontrielonvelon(twittelonrMelonssagelon)
          .onSuccelonss(Function.cons(relonsult -> {
            namelondelonntityHandlelonr.addelonntitielonsToMelonssagelon(twittelonrMelonssagelon, relonsult);
            elonmitAndCount(twittelonrMelonssagelon);
          }))
          .onFailurelon(Function.cons(throwablelon -> {
            namelondelonntityHandlelonr.increlonmelonntelonrrorCount();
            elonmitAndCount(twittelonrMelonssagelon);
          }));
    } elonlselon {
      elonmitAndCount(twittelonrMelonssagelon);
    }
  }

  @Ovelonrridelon
  protelonctelond ComplelontablelonFuturelon<IngelonstelonrTwittelonrMelonssagelon> innelonrRunStagelonV2(IngelonstelonrTwittelonrMelonssagelon
                                                                      melonssagelon) {
    ComplelontablelonFuturelon<IngelonstelonrTwittelonrMelonssagelon> cf = nelonw ComplelontablelonFuturelon<>();

    if (namelondelonntityHandlelonr.shouldRelontrielonvelon(melonssagelon)) {
      namelondelonntityHandlelonr.relontrielonvelon(melonssagelon)
          .onSuccelonss(Function.cons(relonsult -> {
            namelondelonntityHandlelonr.addelonntitielonsToMelonssagelon(melonssagelon, relonsult);
            cf.complelontelon(melonssagelon);
          }))
          .onFailurelon(Function.cons(throwablelon -> {
            namelondelonntityHandlelonr.increlonmelonntelonrrorCount();
            cf.complelontelon(melonssagelon);
          }));
    } elonlselon {
      cf.complelontelon(melonssagelon);
    }

    relonturn cf;
  }
}
