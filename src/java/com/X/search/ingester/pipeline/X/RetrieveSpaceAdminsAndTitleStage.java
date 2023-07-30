package com.X.search.ingester.pipeline.X;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import scala.Option;
import scala.Tuple2;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducesConsumed;

import com.X.search.common.decider.DeciderUtil;
import com.X.search.common.metrics.SearchRateCounter;
import com.X.search.common.relevance.entities.XMessageUser;
import com.X.search.ingester.model.IngesterXMessage;
import com.X.search.ingester.pipeline.strato_fetchers.AudioSpaceCoreFetcher;
import com.X.search.ingester.pipeline.strato_fetchers.AudioSpaceParticipantsFetcher;
import com.X.strato.catalog.Fetch;
import com.X.ubs.thriftjava.AudioSpace;
import com.X.ubs.thriftjava.ParticipantUser;
import com.X.ubs.thriftjava.Participants;
import com.X.util.Function;
import com.X.util.Future;
import com.X.util.Futures;
import com.X.util.Try;

@ConsumedTypes(IngesterXMessage.class)
@ProducesConsumed
public class RetrieveSpaceAdminsAndTitleStage extends XBaseStage
    <IngesterXMessage, CompletableFuture<IngesterXMessage>> {

  @VisibleForTesting
  protected static final String RETRIEVE_SPACE_ADMINS_AND_TITLE_DECIDER_KEY =
      "ingester_all_retrieve_space_admins_and_title";

  private AudioSpaceCoreFetcher coreFetcher;
  private AudioSpaceParticipantsFetcher participantsFetcher;

  private SearchRateCounter tweetsWithSpaceAdmins;
  private SearchRateCounter tweetsWithSpaceTitle;
  private SearchRateCounter coreFetchSuccess;
  private SearchRateCounter coreFetchFailure;
  private SearchRateCounter participantsFetchSuccess;
  private SearchRateCounter participantsFetchFailure;
  private SearchRateCounter emptyCore;
  private SearchRateCounter emptyParticipants;
  private SearchRateCounter emptySpaceTitle;
  private SearchRateCounter emptySpaceAdmins;
  private SearchRateCounter parallelFetchAttempts;
  private SearchRateCounter parallelFetchFailure;


  @Override
  protected void doInnerPreprocess() {
    innerSetup();
  }

  @Override
  protected void innerSetup() {
    coreFetcher = wireModule.getAudioSpaceCoreFetcher();
    participantsFetcher = wireModule.getAudioSpaceParticipantsFetcher();

    tweetsWithSpaceAdmins = getStageStat("tweets_with_audio_space_admins");
    tweetsWithSpaceTitle = getStageStat("tweets_with_audio_space_title");
    coreFetchSuccess = getStageStat("core_fetch_success");
    coreFetchFailure = getStageStat("core_fetch_failure");
    participantsFetchSuccess = getStageStat("participants_fetch_success");
    participantsFetchFailure = getStageStat("participants_fetch_failure");
    emptyCore = getStageStat("empty_core");
    emptyParticipants = getStageStat("empty_participants");
    emptySpaceTitle = getStageStat("empty_space_title");
    emptySpaceAdmins = getStageStat("empty_space_admins");
    parallelFetchAttempts = getStageStat("parallel_fetch_attempts");
    parallelFetchFailure = getStageStat("parallel_fetch_failure");
  }

  private SearchRateCounter getStageStat(String statSuffix) {
    return SearchRateCounter.export(getStageNamePrefix() + "_" + statSuffix);
  }

  private Future<Tuple2<Try<Fetch.Result<AudioSpace>>, Try<Fetch.Result<Participants>>>>
  tryRetrieveSpaceAdminAndTitle(IngesterXMessage XMessage) {
    Set<String> spaceIds = XMessage.getSpaceIds();

    if (spaceIds.isEmpty()) {
      return null;
    }

    if (!(DeciderUtil.isAvailableForRandomRecipient(decider,
        RETRIEVE_SPACE_ADMINS_AND_TITLE_DECIDER_KEY))) {
      return null;
    }

    String spaceId = spaceIds.iterator().next();

    // Query both columns in parallel.
    parallelFetchAttempts.increment();
    Future<Fetch.Result<AudioSpace>> core = coreFetcher.fetch(spaceId);
    Future<Fetch.Result<Participants>> participants = participantsFetcher.fetch(spaceId);

    return Futures.join(core.liftToTry(), participants.liftToTry());
  }

  @Override
  protected CompletableFuture<IngesterXMessage> innerRunStageV2(IngesterXMessage
                                                                            XMessage) {
    Future<Tuple2<Try<Fetch.Result<AudioSpace>>, Try<Fetch.Result<Participants>>>>
        tryRetrieveSpaceAdminAndTitle = tryRetrieveSpaceAdminAndTitle(XMessage);

    CompletableFuture<IngesterXMessage> cf = new CompletableFuture<>();

    if (tryRetrieveSpaceAdminAndTitle == null) {
      cf.complete(XMessage);
    } else {
      tryRetrieveSpaceAdminAndTitle.onSuccess(Function.cons(tries -> {
        handleFutureOnSuccess(tries, XMessage);
        cf.complete(XMessage);
      })).onFailure(Function.cons(throwable -> {
        handleFutureOnFailure();
        cf.complete(XMessage);
      }));
    }

    return cf;
  }

  @Override
  public void innerProcess(Object obj) throws StageException {
    if (!(obj instanceof IngesterXMessage)) {
      throw new StageException(this, "Object is not a IngesterXMessage object: " + obj);
    }
    IngesterXMessage XMessage = (IngesterXMessage) obj;
    Future<Tuple2<Try<Fetch.Result<AudioSpace>>, Try<Fetch.Result<Participants>>>>
        tryRetrieveSpaceAdminAndTitle = tryRetrieveSpaceAdminAndTitle(XMessage);

    if (tryRetrieveSpaceAdminAndTitle == null) {
      emitAndCount(XMessage);
      return;
    }

    tryRetrieveSpaceAdminAndTitle.onSuccess(Function.cons(tries -> {
            handleFutureOnSuccess(tries, XMessage);
            emitAndCount(XMessage);
          })).onFailure(Function.cons(throwable -> {
            handleFutureOnFailure();
            emitAndCount(XMessage);
          }));
  }

  private void handleFutureOnSuccess(Tuple2<Try<Fetch.Result<AudioSpace>>,
      Try<Fetch.Result<Participants>>> tries, IngesterXMessage XMessage) {
    handleCoreFetchTry(tries._1(), XMessage);
    handleParticipantsFetchTry(tries._2(), XMessage);
  }

  private void handleFutureOnFailure() {
    parallelFetchFailure.increment();
  }

  private void handleCoreFetchTry(
      Try<Fetch.Result<AudioSpace>> fetchTry,
      IngesterXMessage XMessage) {

    if (fetchTry.isReturn()) {
      coreFetchSuccess.increment();
      addSpaceTitleToMessage(XMessage, fetchTry.get().v());
    } else {
      coreFetchFailure.increment();
    }
  }

  private void handleParticipantsFetchTry(
      Try<Fetch.Result<Participants>> fetchTry,
      IngesterXMessage XMessage) {

    if (fetchTry.isReturn()) {
      participantsFetchSuccess.increment();
      addSpaceAdminsToMessage(XMessage, fetchTry.get().v());
    } else {
      participantsFetchFailure.increment();
    }
  }

  private void addSpaceTitleToMessage(
      IngesterXMessage XMessage,
      Option<AudioSpace> audioSpace) {

    if (audioSpace.isDefined()) {
      String audioSpaceTitle = audioSpace.get().getTitle();
      if (StringUtils.isNotEmpty(audioSpaceTitle)) {
        XMessage.setSpaceTitle(audioSpaceTitle);
        tweetsWithSpaceTitle.increment();
      } else {
        emptySpaceTitle.increment();
      }
    } else {
      emptyCore.increment();
    }
  }

  private void addSpaceAdminsToMessage(
      IngesterXMessage XMessage,
      Option<Participants> participants) {

    if (participants.isDefined()) {
      List<ParticipantUser> admins = getAdminsFromParticipants(participants.get());
      if (!admins.isEmpty()) {
        for (ParticipantUser admin : admins) {
          addSpaceAdminToMessage(XMessage, admin);
        }
        tweetsWithSpaceAdmins.increment();
      } else {
        emptySpaceAdmins.increment();
      }
    } else {
      emptyParticipants.increment();
    }
  }

  private List<ParticipantUser> getAdminsFromParticipants(Participants participants) {
    if (!participants.isSetAdmins()) {
      return Lists.newArrayList();
    }
    return participants.getAdmins();
  }

  private void addSpaceAdminToMessage(IngesterXMessage XMessage,
                                      ParticipantUser admin) {
    XMessageUser.Builder userBuilder = new XMessageUser.Builder();
    if (admin.isSetX_screen_name()
        && StringUtils.isNotEmpty(admin.getX_screen_name())) {
      userBuilder.withScreenName(Optional.of(admin.getX_screen_name()));
    }
    if (admin.isSetDisplay_name() && StringUtils.isNotEmpty(admin.getDisplay_name())) {
      userBuilder.withDisplayName(Optional.of(admin.getDisplay_name()));
    }
    XMessage.addSpaceAdmin(userBuilder.build());
  }
}
