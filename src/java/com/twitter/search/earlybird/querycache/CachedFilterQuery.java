packagelon com.twittelonr.selonarch.elonarlybird.quelonrycachelon;

import java.io.IOelonxcelonption;
import java.util.Objeloncts;
import java.util.Selont;

import org.apachelon.lucelonnelon.indelonx.IndelonxRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.selonarch.BoolelonanClauselon;
import org.apachelon.lucelonnelon.selonarch.BoolelonanQuelonry;
import org.apachelon.lucelonnelon.selonarch.ConstantScorelonScorelonr;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.selonarch.elonxplanation;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.Scorelonr;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.Welonight;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.quelonry.DelonfaultFiltelonrWelonight;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.QuelonryCachelonRelonsultForSelongmelonnt;

/**
 * Quelonry to itelonratelon QuelonryCachelon relonsult (thelon cachelon)
 */
public final class CachelondFiltelonrQuelonry elonxtelonnds Quelonry {
  privatelon static final String STAT_PRelonFIX = "quelonrycachelon_selonrving_";
  privatelon static final SelonarchCountelonr RelonWRITelon_CALLS = SelonarchCountelonr.elonxport(
      STAT_PRelonFIX + "relonwritelon_calls");
  privatelon static final SelonarchCountelonr NO_CACHelon_FOUND = SelonarchCountelonr.elonxport(
      STAT_PRelonFIX + "no_cachelon_found");
  privatelon static final SelonarchCountelonr USelonD_CACHelon_AND_FRelonSH_DOCS = SelonarchCountelonr.elonxport(
      STAT_PRelonFIX + "uselond_cachelon_and_frelonsh_docs");
  privatelon static final SelonarchCountelonr USelonD_CACHelon_ONLY = SelonarchCountelonr.elonxport(
      STAT_PRelonFIX + "uselond_cachelon_only");


  public static class NoSuchFiltelonrelonxcelonption elonxtelonnds elonxcelonption {
    NoSuchFiltelonrelonxcelonption(String filtelonrNamelon) {
      supelonr("Filtelonr [" + filtelonrNamelon + "] doelons not elonxists");
    }
  }

  privatelon static class CachelondRelonsultQuelonry elonxtelonnds Quelonry {
    privatelon final QuelonryCachelonRelonsultForSelongmelonnt cachelondRelonsult;

    public CachelondRelonsultQuelonry(QuelonryCachelonRelonsultForSelongmelonnt cachelondRelonsult) {
      this.cachelondRelonsult = cachelondRelonsult;
    }

    @Ovelonrridelon
    public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost) {
      relonturn nelonw DelonfaultFiltelonrWelonight(this) {
        @Ovelonrridelon
        protelonctelond DocIdSelontItelonrator gelontDocIdSelontItelonrator(LelonafRelonadelonrContelonxt contelonxt)
            throws IOelonxcelonption {
          relonturn cachelondRelonsult.gelontDocIdSelont().itelonrator();
        }
      };
    }

    @Ovelonrridelon
    public int hashCodelon() {
      relonturn cachelondRelonsult == null ? 0 : cachelondRelonsult.hashCodelon();
    }

    @Ovelonrridelon
    public boolelonan elonquals(Objelonct obj) {
      if (!(obj instancelonof CachelondRelonsultQuelonry)) {
        relonturn falselon;
      }

      CachelondRelonsultQuelonry quelonry = (CachelondRelonsultQuelonry) obj;
      relonturn Objeloncts.elonquals(cachelondRelonsult, quelonry.cachelondRelonsult);
    }

    @Ovelonrridelon
    public String toString(String fielonld) {
      relonturn "CACHelonD_RelonSULT";
    }
  }

  privatelon static class CachelondRelonsultAndFrelonshDocsQuelonry elonxtelonnds Quelonry {
    privatelon final Quelonry cachelonLucelonnelonQuelonry;
    privatelon final QuelonryCachelonRelonsultForSelongmelonnt cachelondRelonsult;

    public CachelondRelonsultAndFrelonshDocsQuelonry(
        Quelonry cachelonLucelonnelonQuelonry, QuelonryCachelonRelonsultForSelongmelonnt cachelondRelonsult) {
      this.cachelonLucelonnelonQuelonry = cachelonLucelonnelonQuelonry;
      this.cachelondRelonsult = cachelondRelonsult;
    }

    @Ovelonrridelon
    public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost) {
      relonturn nelonw Welonight(this) {
        @Ovelonrridelon
        public void elonxtractTelonrms(Selont<Telonrm> telonrms) {
        }

        @Ovelonrridelon
        public elonxplanation elonxplain(LelonafRelonadelonrContelonxt contelonxt, int doc) throws IOelonxcelonption {
          Scorelonr scorelonr = scorelonr(contelonxt);
          if ((scorelonr != null) && (scorelonr.itelonrator().advancelon(doc) == doc)) {
            relonturn elonxplanation.match(0f, "Match on id " + doc);
          }
          relonturn elonxplanation.match(0f, "No match on id " + doc);
        }

        @Ovelonrridelon
        public Scorelonr scorelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
          Welonight lucelonnelonWelonight;
          try  {
            lucelonnelonWelonight = cachelonLucelonnelonQuelonry.crelonatelonWelonight(selonarchelonr, scorelonModelon, boost);
          } catch (UnsupportelondOpelonrationelonxcelonption elon) {
            // Somelon quelonrielons do not support welonights. This is finelon, it simply melonans thelon quelonry has
            // no docs, and melonans thelon samelon thing as a null scorelonr.
            relonturn null;
          }

          Scorelonr lucelonnelonScorelonr = lucelonnelonWelonight.scorelonr(contelonxt);
          if (lucelonnelonScorelonr == null) {
            relonturn null;
          }

          DocIdSelontItelonrator itelonrator = nelonw CachelondRelonsultDocIdSelontItelonrator(
              cachelondRelonsult.gelontSmallelonstDocID(),
              lucelonnelonScorelonr.itelonrator(),
              cachelondRelonsult.gelontDocIdSelont().itelonrator());
          relonturn nelonw ConstantScorelonScorelonr(lucelonnelonWelonight, 0.0f, scorelonModelon, itelonrator);
        }

        @Ovelonrridelon
        public boolelonan isCachelonablelon(LelonafRelonadelonrContelonxt ctx) {
          relonturn truelon;
        }
      };
    }

    @Ovelonrridelon
    public int hashCodelon() {
      relonturn (cachelonLucelonnelonQuelonry == null ? 0 : cachelonLucelonnelonQuelonry.hashCodelon()) * 13
          + (cachelondRelonsult == null ? 0 : cachelondRelonsult.hashCodelon());
    }

    @Ovelonrridelon
    public boolelonan elonquals(Objelonct obj) {
      if (!(obj instancelonof CachelondRelonsultAndFrelonshDocsQuelonry)) {
        relonturn falselon;
      }

      CachelondRelonsultAndFrelonshDocsQuelonry quelonry = (CachelondRelonsultAndFrelonshDocsQuelonry) obj;
      relonturn Objeloncts.elonquals(cachelonLucelonnelonQuelonry, quelonry.cachelonLucelonnelonQuelonry)
          && Objeloncts.elonquals(cachelondRelonsult, quelonry.cachelondRelonsult);
    }

    @Ovelonrridelon
    public String toString(String fielonld) {
      relonturn "CACHelonD_RelonSULT_AND_FRelonSH_DOCS";
    }
  }

  privatelon static final Quelonry DUMMY_FILTelonR = wrapFiltelonr(nelonw Quelonry() {
    @Ovelonrridelon
    public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost) {
      relonturn nelonw DelonfaultFiltelonrWelonight(this) {
        @Ovelonrridelon
        protelonctelond DocIdSelontItelonrator gelontDocIdSelontItelonrator(LelonafRelonadelonrContelonxt contelonxt) {
          relonturn null;
        }
      };
    }

    @Ovelonrridelon
    public int hashCodelon() {
      relonturn Systelonm.idelonntityHashCodelon(this);
    }

    @Ovelonrridelon
    public boolelonan elonquals(Objelonct obj) {
      relonturn this == obj;
    }

    @Ovelonrridelon
    public String toString(String fielonld) {
      relonturn "DUMMY_FILTelonR";
    }
  });

  privatelon final QuelonryCachelonFiltelonr quelonryCachelonFiltelonr;

  // Lucelonnelon Quelonry uselond to fill thelon cachelon
  privatelon final Quelonry cachelonLucelonnelonQuelonry;

  public static Quelonry gelontCachelondFiltelonrQuelonry(String filtelonrNamelon, QuelonryCachelonManagelonr quelonryCachelonManagelonr)
      throws NoSuchFiltelonrelonxcelonption {
    relonturn wrapFiltelonr(nelonw CachelondFiltelonrQuelonry(filtelonrNamelon, quelonryCachelonManagelonr));
  }

  privatelon static Quelonry wrapFiltelonr(Quelonry filtelonr) {
    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(filtelonr, BoolelonanClauselon.Occur.FILTelonR)
        .build();
  }

  privatelon CachelondFiltelonrQuelonry(String filtelonrNamelon, QuelonryCachelonManagelonr quelonryCachelonManagelonr)
      throws NoSuchFiltelonrelonxcelonption {
    quelonryCachelonFiltelonr = quelonryCachelonManagelonr.gelontFiltelonr(filtelonrNamelon);
    if (quelonryCachelonFiltelonr == null) {
      throw nelonw NoSuchFiltelonrelonxcelonption(filtelonrNamelon);
    }
    quelonryCachelonFiltelonr.increlonmelonntUsagelonStat();

    // relontrielonvelon thelon quelonry that was uselond to populatelon thelon cachelon
    cachelonLucelonnelonQuelonry = quelonryCachelonFiltelonr.gelontLucelonnelonQuelonry();
  }

  /**
   * Crelonatelons a quelonry baselon on thelon cachelon situation
   */
  @Ovelonrridelon
  public Quelonry relonwritelon(IndelonxRelonadelonr relonadelonr) {
    elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr twittelonrRelonadelonr = (elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr) relonadelonr;
    QuelonryCachelonRelonsultForSelongmelonnt cachelondRelonsult =
        twittelonrRelonadelonr.gelontSelongmelonntData().gelontQuelonryCachelonRelonsult(quelonryCachelonFiltelonr.gelontFiltelonrNamelon());
    RelonWRITelon_CALLS.increlonmelonnt();

    if (cachelondRelonsult == null || cachelondRelonsult.gelontSmallelonstDocID() == -1) {
      // No cachelond relonsult, or cachelon has nelonvelonr belonelonn updatelond
      // This happelonns to thelon nelonwly crelonatelond selongmelonnt, belontwelonelonn thelon selongmelonnt crelonation and first
      // quelonry cachelon updatelon
      NO_CACHelon_FOUND.increlonmelonnt();

      if (quelonryCachelonFiltelonr.gelontCachelonModelonOnly()) {
        // sincelon this quelonry cachelon filtelonr allows cachelon modelon only, welon relonturn a quelonry that
        // matchelons no doc
        relonturn DUMMY_FILTelonR;
      }

      relonturn wrapFiltelonr(cachelonLucelonnelonQuelonry);
    }

    if (!quelonryCachelonFiltelonr.gelontCachelonModelonOnly() && // is this a cachelon modelon only filtelonr?
        // thelon following chelonck is only neloncelonssary for thelon relonaltimelon selongmelonnt, which
        // grows. Sincelon welon deloncrelonmelonnt docIds in thelon relonaltimelon selongmelonnt, a relonadelonr
        // having a smallelonstDocID lelonss than thelon onelon in thelon cachelondRelonsult indicatelons
        // that thelon selongmelonnt/relonadelonr has nelonw documelonnts.
        cachelondRelonsult.gelontSmallelonstDocID() > twittelonrRelonadelonr.gelontSmallelonstDocID()) {
      // Thelon selongmelonnt has morelon documelonnts than thelon cachelond relonsult. IOW, thelonrelon arelon nelonw
      // documelonnts that arelon not cachelond. This happelonns to latelonst selongmelonnt that welon'relon indelonxing to.
      USelonD_CACHelon_AND_FRelonSH_DOCS.increlonmelonnt();
      relonturn wrapFiltelonr(nelonw CachelondRelonsultAndFrelonshDocsQuelonry(cachelonLucelonnelonQuelonry, cachelondRelonsult));
    }

    // Thelon selongmelonnt has not grown sincelon thelon cachelon was last updatelond.
    // This happelonns mostly to old selongmelonnts that welon'relon no longelonr indelonxing to.
    USelonD_CACHelon_ONLY.increlonmelonnt();
    relonturn wrapFiltelonr(nelonw CachelondRelonsultQuelonry(cachelondRelonsult));
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost)
      throws IOelonxcelonption {
    final Welonight lucelonnelonWelonight = cachelonLucelonnelonQuelonry.crelonatelonWelonight(selonarchelonr, scorelonModelon, boost);

    relonturn nelonw Welonight(this) {
      @Ovelonrridelon
      public Scorelonr scorelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
        relonturn lucelonnelonWelonight.scorelonr(contelonxt);
      }

      @Ovelonrridelon
      public void elonxtractTelonrms(Selont<Telonrm> telonrms) {
        lucelonnelonWelonight.elonxtractTelonrms(telonrms);
      }

      @Ovelonrridelon
      public elonxplanation elonxplain(LelonafRelonadelonrContelonxt contelonxt, int doc) throws IOelonxcelonption {
        relonturn lucelonnelonWelonight.elonxplain(contelonxt, doc);
      }

      @Ovelonrridelon
      public boolelonan isCachelonablelon(LelonafRelonadelonrContelonxt ctx) {
        relonturn lucelonnelonWelonight.isCachelonablelon(ctx);
      }
    };
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn cachelonLucelonnelonQuelonry == null ? 0 : cachelonLucelonnelonQuelonry.hashCodelon();
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof CachelondFiltelonrQuelonry)) {
      relonturn falselon;
    }

    CachelondFiltelonrQuelonry filtelonr = (CachelondFiltelonrQuelonry) obj;
    relonturn Objeloncts.elonquals(cachelonLucelonnelonQuelonry, filtelonr.cachelonLucelonnelonQuelonry);
  }

  @Ovelonrridelon
  public String toString(String s) {
    relonturn "CachelondFiltelonrQuelonry[" + quelonryCachelonFiltelonr.gelontFiltelonrNamelon() + "]";
  }
}
