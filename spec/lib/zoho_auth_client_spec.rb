require "spec_helper"

describe ZohoAuthClient do
  before do
    stub_const("ArchiveConfig", OpenStruct.new(ArchiveConfig))
    ArchiveConfig.ZOHO_CLIENT_ID = "111"
    ArchiveConfig.ZOHO_CLIENT_SECRET = "a1b2c3"
    ArchiveConfig.ZOHO_REFRESH_TOKEN = "x1y2z3"
    ArchiveConfig.ZOHO_ORG_ID = "123"
    ArchiveConfig.ZOHO_REDIRECT_URI = "https://archiveofourown.org/support"

    WebMock.disable_net_connect!
  end

  after do
    WebMock.allow_net_connect!
  end

  describe "#access_token" do
    context "when no token is cached" do
      before do
        WebMock.stub_request(:post, /zoho/)
          .with(query: hash_including({ grant_type: "refresh_token" }))
          .to_return(
            headers: { content_type: "application/json" },
            body: '{"access_token":"1a2b3c","expires_in_sec":3600}'
          )
      end

      it "fetches a new access token from Zoho and caches it" do
        expect(ZohoAuthClient.new.access_token).to eq("1a2b3c")
        expect(Rails.cache.read(ZohoAuthClient::ACCESS_TOKEN_CACHE_KEY)).to eq("1a2b3c")

        expected_query = {
          client_id: "111",
          client_secret: "a1b2c3",
          redirect_uri: "https://archiveofourown.org/support",
          scope: "Desk.contacts.CREATE,Desk.contacts.READ,Desk.search.READ,Desk.tickets.CREATE,Desk.tickets.READ",
          grant_type: "refresh_token",
          refresh_token: "x1y2z3"
        }
        expect(WebMock).to have_requested(:post, "https://accounts.zoho.com/oauth/v2/token")
          .with(query: expected_query)
      end
    end

    context "when a token is cached" do
      before do
        Rails.cache.write(ZohoAuthClient::ACCESS_TOKEN_CACHE_KEY, "1a2b3c-cached")
      end

      it "returns the cached token without making any Zoho requests" do
        expect(ZohoAuthClient.new.access_token).to eq("1a2b3c-cached")
        expect(WebMock).not_to have_requested(:any, /zoho/)
      end
    end
  end
end
