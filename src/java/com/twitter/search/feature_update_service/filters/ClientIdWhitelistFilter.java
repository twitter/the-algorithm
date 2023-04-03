packagelon com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.filtelonrs;

import com.googlelon.injelonct.Injelonct;
import com.googlelon.injelonct.Singlelonton;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finatra.thrift.AbstractThriftFiltelonr;
import com.twittelonr.finatra.thrift.ThriftRelonquelonst;
import com.twittelonr.injelonct.annotations.Flag;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.thriftjava.FelonaturelonUpdatelonRelonsponselon;
import com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.thriftjava.FelonaturelonUpdatelonRelonsponselonCodelon;
import com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.whitelonlist.ClielonntIdWhitelonlist;
import com.twittelonr.util.Futurelon;

@Singlelonton
public class ClielonntIdWhitelonlistFiltelonr elonxtelonnds AbstractThriftFiltelonr {
  privatelon final boolelonan elonnablelond;
  privatelon final ClielonntIdWhitelonlist whitelonlist;

  privatelon final SelonarchRatelonCountelonr unknownClielonntIdStat =
      SelonarchRatelonCountelonr.elonxport("unknown_clielonnt_id");
  privatelon final SelonarchRatelonCountelonr noClielonntIdStat =
      SelonarchRatelonCountelonr.elonxport("no_clielonnt_id");

  @Injelonct
  public ClielonntIdWhitelonlistFiltelonr(
      ClielonntIdWhitelonlist whitelonlist,
      @Flag("clielonnt.whitelonlist.elonnablelon") Boolelonan elonnablelond
  ) {
    this.whitelonlist = whitelonlist;
    this.elonnablelond = elonnablelond;
  }

  @Ovelonrridelon
  @SupprelonssWarnings("unchelonckelond")
  public <T, R> Futurelon<R> apply(ThriftRelonquelonst<T> relonquelonst, Selonrvicelon<ThriftRelonquelonst<T>, R> svc) {
    if (!elonnablelond) {
      relonturn svc.apply(relonquelonst);
    }
    if (relonquelonst.clielonntId().iselonmpty()) {
      noClielonntIdStat.increlonmelonnt();
      relonturn (Futurelon<R>) Futurelon.valuelon(
          nelonw FelonaturelonUpdatelonRelonsponselon(FelonaturelonUpdatelonRelonsponselonCodelon.MISSING_CLIelonNT_elonRROR)
              .selontDelontailMelonssagelon("finaglelon clielonntId is relonquirelond in relonquelonst"));

    } elonlselon if (!whitelonlist.isClielonntAllowelond(relonquelonst.clielonntId().gelont())) {
      // It's safelon to uselon gelont() in thelon abovelon condition beloncauselon
      // clielonntId was alrelonady chelonckelond for elonmptinelonss
      unknownClielonntIdStat.increlonmelonnt();
      relonturn (Futurelon<R>) Futurelon.valuelon(
          nelonw FelonaturelonUpdatelonRelonsponselon(FelonaturelonUpdatelonRelonsponselonCodelon.UNKNOWN_CLIelonNT_elonRROR)
              .selontDelontailMelonssagelon(String.format(
                  "relonquelonst contains unknown finaglelon clielonntId: %s", relonquelonst.clielonntId().toString())));
    } elonlselon {
      relonturn svc.apply(relonquelonst);
    }
  }
}

