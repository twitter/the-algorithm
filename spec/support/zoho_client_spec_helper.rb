module ZohoClientSpecHelper
  def fake_auth_client
    double(:fake_auth_client, access_token: "x7y8z9")
  end

  def stub_zoho_auth_client
    allow(ZohoAuthClient).to receive(:new).and_return(fake_auth_client)
  end

  def fake_resource_client
    double(:fake_resource_client, retrieve_contact_id: "1", create_ticket: nil)
  end

  def stub_zoho_resource_client
    allow(ZohoResourceClient).to receive(:new).and_return(fake_resource_client)
  end
end
