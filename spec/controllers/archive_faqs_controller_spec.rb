# frozen_string_literal: true

require "spec_helper"

describe ArchiveFaqsController do
  include LoginMacros
  include RedirectExpectationHelper

  describe "GET #index" do
    it "renders the index page anyway when the locale param is invalid" do
      expect(I18n).to receive(:with_locale).with("eldritch").and_call_original
      get :index, params: { language_id: "eldritch" }
      expect(response).to render_template(:index)
    end

    it "redirects to the default locale when the locale param is empty" do
      expect(I18n).not_to receive(:with_locale)
      get :index, params: { language_id: "" }
      it_redirects_to(archive_faqs_path(language_id: I18n.default_locale))
    end

    it "redirects to the default locale when the locale param is empty and the session locale is not" do
      expect(I18n).not_to receive(:with_locale)
      get :index, params: { language_id: "" }, session: { language_id: "eldritch" }
      it_redirects_to(archive_faqs_path(language_id: "en"))
    end

    it "redirects to the default locale when the locale param and the session locale are empty" do
      expect(I18n).not_to receive(:with_locale)
      get :index, params: { language_id: "" }, session: { language_id: "" }
      it_redirects_to(archive_faqs_path(language_id: "en"))
    end

    context "when logged in as a regular user" do
      before { fake_login }

      it "renders the index page anyway when the locale param is invalid" do
        expect(I18n).to receive(:with_locale).with("eldritch").and_call_original
        get :index, params: { language_id: "eldritch" }
        expect(response).to render_template(:index)
      end
    end

    context "when logged in as an admin" do
      before { fake_login_admin(create(:admin)) }

      it "redirects to the default locale with an error message when the locale param is invalid" do
        expect(I18n).not_to receive(:with_locale)
        get :index, params: { language_id: "eldritch" }
        it_redirects_to_with_error(archive_faqs_path(language_id: I18n.default_locale),
                                   "The specified locale does not exist.")
      end
    end
  end

  describe "GET #show" do
    it "raises a 404 for an invalid id" do
      params = { id: "angst", language_id: "en" }
      expect { get :show, params: params }.to raise_error ActiveRecord::RecordNotFound
    end
  end

  describe "PATCH #update" do
    let(:faq) { create(:archive_faq) }

    context "when logged in as an admin" do
      before { fake_login_admin(create(:admin)) }

      it "redirects to the default locale when the locale param is empty" do
        expect(I18n).not_to receive(:with_locale)
        patch :update, params: { id: faq.id, language_id: "" }
        it_redirects_to(archive_faq_path(id: faq.id, language_id: I18n.default_locale))
      end

      it "redirects to the default locale with an error message when the locale param is invalid" do
        expect(I18n).not_to receive(:with_locale)
        patch :update, params: { id: faq.id, language_id: "eldritch" }
        it_redirects_to_with_error(archive_faq_path(id: faq.id, language_id: I18n.default_locale),
                                   "The specified locale does not exist.")
      end
    end
  end
end
