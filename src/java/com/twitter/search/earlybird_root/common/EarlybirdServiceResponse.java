package com.twitter.search.earlybird_root.common;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;

import com.twitter.search.earlybird.thrift.EarlybirdResponse;

/**
 * A class that wraps an EarlybirdResponse and a flag that determines if a request was sent to a
 * service.
 */
public final class EarlybirdServiceResponse {
  public static enum ServiceState {
    // The service was called (or will be called).
    SERVICE_CALLED(true),

    // The service is not available (turned off by a decider, for example).
    SERVICE_NOT_AVAILABLE(false),

    // The client did not request results from this service.
    SERVICE_NOT_REQUESTED(false),

    // The service is available and the client wants results from this service, but the service
    // was not called (because we got enough results from other services, for example).
    SERVICE_NOT_CALLED(false);

    private final boolean serviceWasCalled;

    private ServiceState(boolean serviceWasCalled) {
      this.serviceWasCalled = serviceWasCalled;
    }

    public boolean serviceWasCalled() {
      return serviceWasCalled;
    }

    public boolean serviceWasRequested() {
      return this != SERVICE_NOT_REQUESTED;
    }

  }

  private final EarlybirdResponse earlybirdResponse;
  private final ServiceState serviceState;

  private EarlybirdServiceResponse(@Nullable EarlybirdResponse earlybirdResponse,
                                   ServiceState serviceState) {
    this.earlybirdResponse = earlybirdResponse;
    this.serviceState = serviceState;
    if (!serviceState.serviceWasCalled()) {
      Preconditions.checkArgument(earlybirdResponse == null);
    }
  }

  /**
   * Creates a new EarlybirdServiceResponse instance, indicating that the service was not called.
   *
   * @param serviceState The state of the service.
   * @return a new EarlybirdServiceResponse instance, indicating that the service was not called.
   */
  public static EarlybirdServiceResponse serviceNotCalled(ServiceState serviceState) {
    Preconditions.checkArgument(!serviceState.serviceWasCalled());
    return new EarlybirdServiceResponse(null, serviceState);
  }

  /**
   * Creates a new EarlybirdServiceResponse instance that wraps the given earlybird response.
   *
   * @param earlybirdResponse The EarlybirdResponse instance returned by the service.
   * @return a new EarlybirdServiceResponse instance that wraps the given earlybird response.
   */
  public static EarlybirdServiceResponse serviceCalled(EarlybirdResponse earlybirdResponse) {
    return new EarlybirdServiceResponse(earlybirdResponse, ServiceState.SERVICE_CALLED);
  }

  /** Returns the wrapped earlybird response. */
  @Nullable
  public EarlybirdResponse getResponse() {
    return earlybirdResponse;
  }

  /** Returns the state of the service. */
  public ServiceState getServiceState() {
    return serviceState;
  }
}
