require "spec_helper"

describe "rake resanitize:all" do
  include ActiveJob::TestHelper

  shared_examples "resanitizes" do |field|
    field_sanitizer_version = "#{field}_sanitizer_version"

    context "when the #{field} attribute has an old sanitizer version" do
      it "removes invalid content and updates the sanitizer version" do
        record.update_columns(field => "<invalid>",
                              field_sanitizer_version => ArchiveConfig.SANITIZER_VERSION - 1)

        expect do
          perform_enqueued_jobs { subject.invoke }
          record.reload
        end.to output.to_stdout
          .and change(record, field).to("")
          .and change(record, field_sanitizer_version).to(ArchiveConfig.SANITIZER_VERSION)
      end
    end

    context "when the #{field} attribute has an up-to-date sanitizer version" do
      it "doesn't resanitize the field" do
        record.update_columns(field => "<invalid>",
                              field_sanitizer_version => ArchiveConfig.SANITIZER_VERSION)

        expect do
          perform_enqueued_jobs { subject.invoke }
          record.reload
        end.to output.to_stdout
          .and avoid_changing(record, field)
          .and avoid_changing(record, field_sanitizer_version)
      end
    end
  end

  {
    abuse_report: [:summary, :comment],
    admin_activity: [:summary],
    admin_banner: [:content],
    admin_post: [:content],
    bookmark: [:bookmarker_notes],
    chapter: [:content, :summary, :notes, :endnotes],
    collection: [:description],
    collection_profile: [:intro, :faq, :rules],
    comment: [:comment_content],
    external_work: [:summary],
    feedback: [:summary, :comment],
    gift_exchange: [:signup_instructions_general,
                    :signup_instructions_requests,
                    :signup_instructions_offers],
    known_issue: [:content],
    owned_tag_set: [:description],
    prompt_meme: [:signup_instructions_general,
                  :signup_instructions_requests],
    pseud: [:description],
    series: [:summary, :series_notes],
    skin: [:description],
    work: [:summary, :notes, :endnotes],
    wrangling_guideline: [:content]
  }.each do |factory, fields|
    context "when there is a #{factory}" do
      let(:record) { create(factory) }

      fields.each do |field|
        include_examples "resanitizes", field
      end
    end
  end

  context "when there is a user profile" do
    let(:record) { create(:user).profile }

    include_examples "resanitizes", :about_me
  end

  context "when there is a prompt" do
    let(:record) { create(:challenge_signup).requests.first }

    include_examples "resanitizes", :description
  end

  context "when there is an admin settings object" do
    let(:record) { AdminSetting.first }

    include_examples "resanitizes", :disabled_support_form_text
  end

  context "when there is a question" do
    let!(:question) { create(:question) }
    let(:record) { question.translations.find_by(locale: :en) }

    include_examples "resanitizes", :content

    context "when the question has multiple translations" do
      let(:locale) { create(:locale) }
      let(:translation) { question.translations.find_by(locale: locale.iso) }

      before do
        I18n.with_locale(locale.iso) do
          question.update(content: Faker::Lorem.paragraph)
        end
      end

      it "doesn't change translations with an up-to-date content sanitizer version" do
        translation.update_columns(content: "<invalid>")
        record.update_columns(content_sanitizer_version: ArchiveConfig.SANITIZER_VERSION - 1)

        expect do
          perform_enqueued_jobs { subject.invoke }
          record.reload
          translation.reload
        end.to output.to_stdout
          .and change { record.content_sanitizer_version }.to(ArchiveConfig.SANITIZER_VERSION)
          .and avoid_changing { translation.content }
      end
    end
  end
end
