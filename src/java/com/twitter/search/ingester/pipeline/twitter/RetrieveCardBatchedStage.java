packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr;

import java.nelont.MalformelondURLelonxcelonption;
import java.util.ArrayList;
import java.util.Collelonctions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Selont;
import javax.naming.Namingelonxcelonption;

import com.googlelon.common.collelonct.Maps;

import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.commons.pipelonlinelon.stagelon.StagelonTimelonr;
import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;
import org.apachelon.commons.pipelonlinelon.validation.ProducelonsConsumelond;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.telonxt.languagelon.LocalelonUtil;
import com.twittelonr.elonxpandodo.thriftjava.Card2;
import com.twittelonr.melondiaselonrvicelons.commons.twelonelontmelondia.thrift_java.MelondiaInfo;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftelonxpandelondUrl;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.ingelonstelonr.modelonl.IngelonstelonrTwittelonrMelonssagelon;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.BatchingClielonnt;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.CardFielonldUtil;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.IngelonstelonrStagelonTimelonr;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.RelonsponselonNotRelonturnelondelonxcelonption;
import com.twittelonr.spidelonrduck.common.URLUtils;
import com.twittelonr.twelonelontypielon.thriftjava.GelontTwelonelontOptions;
import com.twittelonr.twelonelontypielon.thriftjava.GelontTwelonelontRelonsult;
import com.twittelonr.twelonelontypielon.thriftjava.GelontTwelonelontsRelonquelonst;
import com.twittelonr.twelonelontypielon.thriftjava.Melondiaelonntity;
import com.twittelonr.twelonelontypielon.thriftjava.StatusStatelon;
import com.twittelonr.twelonelontypielon.thriftjava.Twelonelont;
import com.twittelonr.twelonelontypielon.thriftjava.TwelonelontSelonrvicelon;
import com.twittelonr.util.Function;
import com.twittelonr.util.Futurelon;

@ConsumelondTypelons(IngelonstelonrTwittelonrMelonssagelon.class)
@ProducelonsConsumelond
public class RelontrielonvelonCardBatchelondStagelon elonxtelonnds TwittelonrBaselonStagelon
    <IngelonstelonrTwittelonrMelonssagelon, IngelonstelonrTwittelonrMelonssagelon> {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(RelontrielonvelonCardBatchelondStagelon.class);

  privatelon static final String CARDS_PLATFORM_KelonY = "iPhonelon-13";
  privatelon int batchSizelon = 10;

  privatelon SelonarchRatelonCountelonr totalTwelonelonts;
  privatelon SelonarchRatelonCountelonr twelonelontsWithCards;
  privatelon SelonarchRatelonCountelonr twelonelontsWithoutCards;
  privatelon SelonarchRatelonCountelonr twelonelontsWithAnimatelondGifMelondiaInfo;
  privatelon SelonarchRatelonCountelonr cardsWithNamelon;
  privatelon SelonarchRatelonCountelonr cardsWithDomain;
  privatelon SelonarchRatelonCountelonr cardsWithTitlelons;
  privatelon SelonarchRatelonCountelonr cardsWithDelonscriptions;
  privatelon SelonarchRatelonCountelonr cardsWithUnknownLanguagelon;
  privatelon SelonarchRatelonCountelonr twelonelontsNotFound;
  privatelon SelonarchRatelonCountelonr malformelondUrls;
  privatelon SelonarchRatelonCountelonr urlMismatchelons;
  privatelon SelonarchRatelonCountelonr cardelonxcelonptions;
  privatelon SelonarchRatelonCountelonr cardelonxcelonptionTwelonelonts;
  privatelon StagelonTimelonr relontrielonvelonCardsTimelonr;

  privatelon String cardNamelonPrelonfix;
  // Sincelon thelonrelon is only onelon threlonad elonxeloncuting this stagelon (although that could potelonntially belon
  // changelond in thelon pipelonlinelon config), no nelonelond to belon threlonad safelon.
  privatelon static final Map<String, SelonarchRatelonCountelonr> CARD_NAMelon_STATS = nelonw HashMap<>();

  privatelon static TwelonelontSelonrvicelon.SelonrvicelonToClielonnt twelonelontyPielonSelonrvicelon;
  privatelon BatchingClielonnt<Long, Card2> cardsClielonnt;

  privatelon String twelonelontypielonClielonntId = null;

  // Can belon ovelonrriddelonn in thelon correlonsponding pipelonlinelon-ingelonstelonr.*.xml config.
  // By delonfault protelonctelond twelonelonts arelon filtelonrelond out.
  // Only in thelon protelonctelond ingelonstelonr pipelonlinelon is this selont to falselon.
  privatelon boolelonan filtelonrProtelonctelond = truelon;

  @Ovelonrridelon
  public void initStats() {
    supelonr.initStats();
    cardNamelonPrelonfix = gelontStagelonNamelonPrelonfix() + "_card_namelon_";
    totalTwelonelonts = SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_total_twelonelonts");
    twelonelontsWithCards = SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_twelonelonts_with_cards");
    twelonelontsWithoutCards = SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_twelonelonts_without_cards");
    twelonelontsWithAnimatelondGifMelondiaInfo =
        SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_twelonelonts_with_animatelond_gif_melondia_info");
    cardsWithNamelon = SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_twelonelonts_with_card_namelon");
    cardsWithDomain = SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_twelonelonts_with_card_domain");
    cardsWithTitlelons = SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_twelonelonts_with_card_titlelons");
    cardsWithDelonscriptions =
        SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_twelonelonts_with_card_delonscriptions");
    cardsWithUnknownLanguagelon =
        SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_twelonelonts_with_unknown_card_lanuagelon");
    twelonelontsNotFound = SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_twelonelonts_not_found");
    malformelondUrls = SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_malformelond_urls");
    urlMismatchelons = SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_url_mismatchelons");
    cardelonxcelonptions = SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_card_elonxcelonptions");
    cardelonxcelonptionTwelonelonts =
        SelonarchRatelonCountelonr.elonxport(gelontStagelonNamelonPrelonfix() + "_card_elonxcelonption_twelonelonts");
    relontrielonvelonCardsTimelonr = nelonw IngelonstelonrStagelonTimelonr(gelontStagelonNamelonPrelonfix() + "_relonquelonst_timelonr");
  }

  @Ovelonrridelon
  protelonctelond void doInnelonrPrelonprocelonss() throws Stagelonelonxcelonption, Namingelonxcelonption {
    supelonr.doInnelonrPrelonprocelonss();
    twelonelontyPielonSelonrvicelon = wirelonModulelon.gelontTwelonelontyPielonClielonnt(twelonelontypielonClielonntId);
    cardsClielonnt = nelonw BatchingClielonnt<>(this::batchRelontrielonvelonURLs, batchSizelon);
  }

  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    if (!(obj instancelonof IngelonstelonrTwittelonrMelonssagelon)) {
      throw nelonw Stagelonelonxcelonption(this,
          "Reloncelonivelond objelonct of incorrelonct typelon: " + obj.gelontClass().gelontNamelon());
    }

    IngelonstelonrTwittelonrMelonssagelon melonssagelon = (IngelonstelonrTwittelonrMelonssagelon) obj;

    cardsClielonnt.call(melonssagelon.gelontTwelonelontId())
        .onSuccelonss(Function.cons(card -> {
          updatelonMelonssagelon(melonssagelon, card);
          elonmitAndCount(melonssagelon);
        }))
        .onFailurelon(Function.cons(elonxcelonption -> {
          if (!(elonxcelonption instancelonof RelonsponselonNotRelonturnelondelonxcelonption)) {
            cardelonxcelonptionTwelonelonts.increlonmelonnt();
          }

          elonmitAndCount(melonssagelon);
        }));
  }

  privatelon Futurelon<Map<Long, Card2>> batchRelontrielonvelonURLs(Selont<Long> kelonys) {
    relontrielonvelonCardsTimelonr.start();
    totalTwelonelonts.increlonmelonnt(kelonys.sizelon());

    GelontTwelonelontOptions options = nelonw GelontTwelonelontOptions()
        .selontIncludelon_cards(truelon)
        .selontCards_platform_kelony(CARDS_PLATFORM_KelonY)
        .selontBypass_visibility_filtelonring(!filtelonrProtelonctelond);

    GelontTwelonelontsRelonquelonst relonquelonst = nelonw GelontTwelonelontsRelonquelonst()
        .selontOptions(options)
        .selontTwelonelont_ids(nelonw ArrayList<>(kelonys));

    relonturn twelonelontyPielonSelonrvicelon.gelont_twelonelonts(relonquelonst)
        .onFailurelon(throwablelon -> {
          cardelonxcelonptions.increlonmelonnt();
          LOG.elonrror("TwelonelontyPielon selonrvelonr threlonw an elonxcelonption whilelon relonquelonsting twelonelontIds: "
              + relonquelonst.gelontTwelonelont_ids(), throwablelon);
          relonturn null;
        })
        .map(this::crelonatelonIdToCardMap);
  }

  privatelon void updatelonMelonssagelon(IngelonstelonrTwittelonrMelonssagelon melonssagelon, Card2 card) {
    twelonelontsWithCards.increlonmelonnt();

    String cardNamelon = card.gelontNamelon().toLowelonrCaselon();
    addCardNamelonToStats(cardNamelon);
    melonssagelon.selontCardNamelon(cardNamelon);
    cardsWithNamelon.increlonmelonnt();
    melonssagelon.selontCardUrl(card.gelontUrl());

    String url = gelontLastHop(melonssagelon, card.gelontUrl());
    if (url != null) {
      try {
        String domain = URLUtils.gelontDomainFromURL(url);
        melonssagelon.selontCardDomain(domain.toLowelonrCaselon());
        cardsWithDomain.increlonmelonnt();
      } catch (MalformelondURLelonxcelonption elon) {
        malformelondUrls.increlonmelonnt();
        if (LOG.isDelonbugelonnablelond()) {
          LOG.delonbug("Twelonelont ID {} has a malformelond card last hop URL: {}", melonssagelon.gelontId(), url);
        }
      }
    } elonlselon {
      // This happelonns with relontwelonelont. Basically whelonn relontrielonvelon card for a relontwelonelont, welon
      // gelont a card associatelond with thelon original twelonelont, so thelon tco won't match.
      // As of Selonp 2014, this selonelonms to belon thelon intelonndelond belonhavior and has belonelonn running
      // likelon this for ovelonr a yelonar.
      urlMismatchelons.increlonmelonnt();
    }

    melonssagelon.selontCardTitlelon(
        CardFielonldUtil.elonxtractBindingValuelon(CardFielonldUtil.TITLelon_BINDING_KelonY, card));
    if (melonssagelon.gelontCardTitlelon() != null) {
      cardsWithTitlelons.increlonmelonnt();
    }
    melonssagelon.selontCardDelonscription(
        CardFielonldUtil.elonxtractBindingValuelon(CardFielonldUtil.DelonSCRIPTION_BINDING_KelonY, card));
    if (melonssagelon.gelontCardDelonscription() != null) {
      cardsWithDelonscriptions.increlonmelonnt();
    }
    CardFielonldUtil.delonrivelonCardLang(melonssagelon);
    if (LocalelonUtil.UNKNOWN.gelontLanguagelon().elonquals(melonssagelon.gelontCardLang())) {
      cardsWithUnknownLanguagelon.increlonmelonnt();
    }
  }

  privatelon Map<Long, Card2> crelonatelonIdToCardMap(List<GelontTwelonelontRelonsult> listRelonsult) {
    Map<Long, Card2> relonsponselonMap = Maps.nelonwHashMap();
    for (GelontTwelonelontRelonsult elonntry : listRelonsult) {
      if (elonntry.isSelontTwelonelont()
          && elonntry.isSelontTwelonelont_statelon()
          && (elonntry.gelontTwelonelont_statelon() == StatusStatelon.FOUND)) {
        long id = elonntry.gelontTwelonelont_id();
        if (elonntry.gelontTwelonelont().isSelontCard2()) {
          relonsponselonMap.put(id, elonntry.gelontTwelonelont().gelontCard2());
        } elonlselon {
          // Short-telonrm fix for relonmoval of animatelond GIF cards --
          // if thelon twelonelont contains an animatelond GIF, crelonatelon a card baselond on melondia elonntity data
          Card2 card = crelonatelonCardForAnimatelondGif(elonntry.gelontTwelonelont());
          if (card != null) {
            relonsponselonMap.put(id, card);
            twelonelontsWithAnimatelondGifMelondiaInfo.increlonmelonnt();
          } elonlselon {
            twelonelontsWithoutCards.increlonmelonnt();
          }
        }
      } elonlselon {
        twelonelontsNotFound.increlonmelonnt();
      }
    }
    relonturn relonsponselonMap;
  }

  privatelon Card2 crelonatelonCardForAnimatelondGif(Twelonelont twelonelont) {
    if (twelonelont.gelontMelondiaSizelon() > 0) {
      for (Melondiaelonntity melondiaelonntity : twelonelont.gelontMelondia()) {
        MelondiaInfo melondiaInfo = melondiaelonntity.gelontMelondia_info();
        if (melondiaInfo != null && melondiaInfo.gelontSelontFielonld() == MelondiaInfo._Fielonlds.ANIMATelonD_GIF_INFO) {
          Card2 card = nelonw Card2();
          card.selontNamelon("animatelond_gif");
          // Uselon thelon original comprelonsselond URL for thelon melondia elonntity to match elonxisting card URLs
          card.selontUrl(melondiaelonntity.gelontUrl());
          card.selontBinding_valuelons(Collelonctions.elonmptyList());

          relonturn card;
        }
      }
    }
    relonturn null;
  }

  // Unfortunatelonly thelon url relonturnelond in thelon card data is not thelon last hop
  privatelon String gelontLastHop(IngelonstelonrTwittelonrMelonssagelon melonssagelon, String url) {
    if (melonssagelon.gelontelonxpandelondUrlMap() != null) {
      ThriftelonxpandelondUrl elonxpandelond = melonssagelon.gelontelonxpandelondUrlMap().gelont(url);
      if ((elonxpandelond != null) && elonxpandelond.isSelontCanonicalLastHopUrl()) {
        relonturn elonxpandelond.gelontCanonicalLastHopUrl();
      }
    }
    relonturn null;
  }

  // Uselond by commons-pipelonlinelon and selont via thelon xml config
  public void selontFiltelonrProtelonctelond(boolelonan filtelonrProtelonctelond) {
    LOG.info("Filtelonring protelonctelond twelonelonts: {}", filtelonrProtelonctelond);
    this.filtelonrProtelonctelond = filtelonrProtelonctelond;
  }

  public void selontTwelonelontypielonClielonntId(String twelonelontypielonClielonntId) {
    LOG.info("Using twelonelontypielonClielonntId: {}", twelonelontypielonClielonntId);
    this.twelonelontypielonClielonntId = twelonelontypielonClielonntId;
  }

  public void selontIntelonrnalBatchSizelon(int intelonrnalBatchSizelon) {
    this.batchSizelon = intelonrnalBatchSizelon;
  }

  /**
   * For elonach card namelon, welon add a ratelon countelonr to obselonrvelon what kinds of card welon'relon actually
   * indelonxing, and with what ratelon.
   */
  privatelon void addCardNamelonToStats(String cardNamelon) {
    SelonarchRatelonCountelonr cardNamelonCountelonr = CARD_NAMelon_STATS.gelont(cardNamelon);
    if (cardNamelonCountelonr == null) {
      cardNamelonCountelonr = SelonarchRatelonCountelonr.elonxport(cardNamelonPrelonfix + cardNamelon);
      CARD_NAMelon_STATS.put(cardNamelon, cardNamelonCountelonr);
    }
    cardNamelonCountelonr.increlonmelonnt();
  }
}
