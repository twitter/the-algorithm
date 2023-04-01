require "spec_helper"

describe ZohoResourceClient do
  let(:resource_params) do
    {
      access_token: "1a2b3c",
      email: "email@example.org"
    }
  end

  let(:expected_request_headers) do
    {
      "Content-Type" => "application/json",
      "orgId" => "123",
      "Authorization" => "Zoho-oauthtoken 1a2b3c"
    }
  end

  let(:subject) { ZohoResourceClient.new(**resource_params) }

  before do
    stub_const("ArchiveConfig", OpenStruct.new(ArchiveConfig))
    ArchiveConfig.ZOHO_ORG_ID = "123"

    WebMock.disable_net_connect!
  end

  after do
    WebMock.allow_net_connect!
  end

  describe "#retrieve_contact_id" do
    context "for an existing contact" do
      before do
        WebMock.stub_request(:get, /zoho/)
          .with(query: hash_including({ email: "email@example.org" }))
          .to_return(headers: { content_type: "application/json" }, body: '{"data":[{"id":"1"}]}')
      end

      it "returns the contact id, without attempting to create a new one" do
        expect(subject.retrieve_contact_id).to eq("1")

        expect(WebMock).to have_requested(:get, "https://desk.zoho.com/api/v1/contacts/search")
          .with(
            headers: expected_request_headers,
            query: { email: "email@example.org", limit: 1, sortBy: "modifiedTime" }
          )

        expect(WebMock).not_to have_requested(:post, /zoho/)
      end
    end

    context "when no contact was found" do
      before do
        WebMock.stub_request(:get, /zoho/)
          .with(query: hash_including({ email: "email@example.org" }))
          .to_return(status: 204)

        WebMock.stub_request(:post, /zoho/)
          .to_return(headers: { content_type: "application/json" }, body: '{"id":"2"}')
      end

      it "creates a new contact using the email for the required field lastName" do
        expect(subject.retrieve_contact_id).to eq("2")

        expect(WebMock).to have_requested(:get, "https://desk.zoho.com/api/v1/contacts/search")
          .with(
            headers: expected_request_headers,
            query: { email: "email@example.org", limit: 1, sortBy: "modifiedTime" }
          )

        expect(WebMock).to have_requested(:post, "https://desk.zoho.com/api/v1/contacts")
          .with(
            headers: expected_request_headers,
            body: { "lastName" => "email@example.org", "email" => "email@example.org" }.to_json
          )
      end
    end
  end

  describe "#find_ticket" do
    context "for an existing ticket" do
      before do
        WebMock.stub_request(:get, /zoho/)
          .with(query: hash_including({ ticketNumber: "480000" }))
          .to_return(headers: { content_type: "application/json" }, body: '{"data":[{"ticketNumber":"480000"}]}')
      end

      it "returns the ticket content" do
        expect(subject.find_ticket(480_000).fetch("ticketNumber")).to eq("480000")

        expect(WebMock).to have_requested(:get, "https://desk.zoho.com/api/v1/tickets/search")
          .with(
            headers: expected_request_headers,
            query: { ticketNumber: "480000", limit: 1, sortBy: "modifiedTime" }
          )
      end
    end

    context "when no ticket was found" do
      before do
        WebMock.stub_request(:get, /zoho/)
          .with(query: hash_including({ ticketNumber: "480000" }))
          .to_return(status: 204)
      end

      it "returns nil" do
        expect(subject.find_ticket(480_000)).to be_nil
      end
    end

    context "when Zoho returns an error" do
      before do
        WebMock.stub_request(:get, /zoho/)
          .with(query: hash_including({ ticketNumber: "4" }))
          .to_return(status: 422, headers: { content_type: "application/json" }, body: '{"errorCode":"UNPROCESSABLE_ENTITY"}')
      end

      it "returns nil" do
        expect(subject.find_ticket(4)).to be_nil
      end
    end
  end

  describe "#create_ticket" do
    let(:ticket_attributes) do
      { foo: "bar" }
    end

    before do
      WebMock.stub_request(:post, /zoho/)
        .to_return(headers: { content_type: "application/json" }, body: '{"id":"3"}')
    end

    it "submits a post request to the correct endpoint with the expected arguments" do
      expect(subject.create_ticket(ticket_attributes: ticket_attributes).fetch("id")).to eq("3")

      expect(WebMock).to have_requested(:post, "https://desk.zoho.com/api/v1/tickets")
        .with(
          headers: expected_request_headers,
          body: ticket_attributes.to_json
        )
    end
  end
end
