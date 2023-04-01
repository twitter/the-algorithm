# frozen_string_literal: true

require "spec_helper"

describe AbuseReporter do
  include ZohoClientSpecHelper

  let(:abuse_report_attributes) do
    {
      title: "This is a tragesy",
      description: "Nothing more to say",
      language: "English",
      email: "walrus@example.org",
      username: "Walrus",
      ip_address: "127.0.0.1",
      url: "http://localhost"
    }
  end

  let(:expected_ticket_attributes) do
    {
      "departmentId" => "abuse_dep_id",
      "email" => "walrus@example.org",
      "contactId" => "1",
      "subject" => "[AO3] Abuse - This is a tragesy",
      "description" => "Nothing more to say",
      "cf" => {
        "cf_language" => "English",
        "cf_name" => "Walrus",
        "cf_ip" => "127.0.0.1",
        "cf_url" => "http://localhost"
      }
    }
  end

  before(:each) do
    stub_const("ArchiveConfig", OpenStruct.new(ArchiveConfig))
    ArchiveConfig.ABUSE_ZOHO_DEPARTMENT_ID = "abuse_dep_id"

    stub_zoho_auth_client
    stub_zoho_resource_client
  end

  let(:subject) { AbuseReporter.new(abuse_report_attributes) }

  describe "#report_attributes" do
    it "returns the expected attributes" do
      expect(subject.report_attributes).to eq(expected_ticket_attributes)
    end

    context "if the report has an empty title" do
      it "returns a hash containing a placeholder subject" do
        allow(subject).to receive(:title).and_return("")

        expect(subject.report_attributes.fetch("subject")).to eq("[AO3] Abuse - No Subject")
      end
    end

    context "if the report does not have a description" do
      it "returns a hash containing placeholder text" do
        allow(subject).to receive(:description).and_return("")

        expect(subject.report_attributes.fetch("description")).to eq("No comment submitted.")
      end
    end

    context "if the report does not have an IP address" do
      it "returns a hash containing 'Unknown IP'" do
        allow(subject).to receive(:ip_address).and_return("")

        expect(subject.report_attributes.fetch("cf").fetch("cf_ip")).to eq("Unknown IP")
      end
    end

    context "if the report does not have an URL" do
      it "returns a hash containing 'Unknown URL'" do
        allow(subject).to receive(:url).and_return("")

        expect(subject.report_attributes.fetch("cf").fetch("cf_url")).to eq("Unknown URL")
      end
    end
  end
end
