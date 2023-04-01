# frozen_string_literal: true

require "spec_helper"

describe SupportReporter do
  include ZohoClientSpecHelper

  let(:support_report_attributes) do
    {
      title: "This is a tragesy",
      description: "Nothing more to say",
      language: "English",
      email: "walrus@example.org",
      username: "Walrus",
      user_agent: "HTTParty",
      site_revision: "eternal_beta",
      rollout: "rollout_value"
    }
  end

  let(:expected_ticket_attributes) do
    {
      "departmentId" => "support_dep_id",
      "email" => "walrus@example.org",
      "contactId" => "1",
      "subject" => "[AO3] Support - This is a tragesy",
      "description" => "Nothing more to say",
      "cf" => {
        "cf_language" => "English",
        "cf_name" => "Walrus",
        "cf_archive_version" => "eternal_beta",
        "cf_rollout" => "rollout_value",
        "cf_user_agent" => "HTTParty"
      }
    }
  end

  before(:each) do
    stub_const("ArchiveConfig", OpenStruct.new(ArchiveConfig))
    ArchiveConfig.SUPPORT_ZOHO_DEPARTMENT_ID = "support_dep_id"

    stub_zoho_auth_client
    stub_zoho_resource_client
  end

  let(:subject) { SupportReporter.new(support_report_attributes) }

  describe "#report_attributes" do
    it "returns the expected attributes" do
      expect(subject.report_attributes).to eq(expected_ticket_attributes)
    end

    context "if the report has an empty title" do
      it "returns a hash containing a placeholder subject" do
        allow(subject).to receive(:title).and_return("")

        expect(subject.report_attributes.fetch("subject")).to eq("[AO3] Support - No Title")
      end
    end

    context "if the report does not have a description" do
      it "returns a hash containing placeholder text" do
        allow(subject).to receive(:description).and_return("")

        expect(subject.report_attributes.fetch("description")).to eq("No description submitted.")
      end
    end

    context "if the report does not have the Archive Version" do
      it "returns a hash containing 'Unknown site revision'" do
        allow(subject).to receive(:site_revision).and_return("")

        expect(subject.report_attributes.fetch("cf").fetch("cf_archive_version")).to eq("Unknown site revision")
      end
    end

    context "if the report does not have the Rollout" do
      it "returns a hash containing 'Unknown'" do
        allow(subject).to receive(:rollout).and_return("")

        expect(subject.report_attributes.fetch("cf").fetch("cf_rollout")).to eq("Unknown")
      end
    end

    context "if the report does not have a User Agent" do
      it "returns a hash containing 'Unknown user agent'" do
        allow(subject).to receive(:user_agent).and_return("")

        expect(subject.report_attributes.fetch("cf").fetch("cf_user_agent")).to eq("Unknown user agent")
      end
    end
  end
end
