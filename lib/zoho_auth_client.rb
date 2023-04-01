# frozen_string_literal: true

class ZohoAuthClient
  ACCESS_TOKEN_REQUEST_ENDPOINT = "https://accounts.zoho.com/oauth/v2/token"
  ACCESS_TOKEN_CACHE_KEY = "/v2/zoho_access_token"

  SCOPE = [
    # - Find and create contacts before submitting tickets
    # - Create tickets
    # - Find tickets to justify admin actions
    "Desk.contacts.CREATE",
    "Desk.contacts.READ",
    "Desk.search.READ",
    "Desk.tickets.CREATE",
    "Desk.tickets.READ"
  ].join(",").freeze

  def access_token
    if (cached_token = Rails.cache.read(ACCESS_TOKEN_CACHE_KEY)).present?
      return cached_token
    end

    response = HTTParty.post(ACCESS_TOKEN_REQUEST_ENDPOINT, query: access_token_params).parsed_response
    access_token = response["access_token"]

    if (expires_in = response["expires_in_sec"]).present?
      # We don't want the token to expire while we're in the middle of a sequence
      # of requests, so we take the stated expiration time and subtract a little.
      Rails.cache.write(ACCESS_TOKEN_CACHE_KEY, access_token,
                        expires_in: expires_in - 1.minute)
    end

    # Return the access token:
    access_token
  end

  private

  def access_token_params
    {
      client_id: ArchiveConfig.ZOHO_CLIENT_ID,
      client_secret: ArchiveConfig.ZOHO_CLIENT_SECRET,
      redirect_uri: ArchiveConfig.ZOHO_REDIRECT_URI,
      scope: SCOPE,
      grant_type: "refresh_token",
      refresh_token: ArchiveConfig.ZOHO_REFRESH_TOKEN
    }
  end
end
