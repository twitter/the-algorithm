require "spec_helper"

describe Admin::BlacklistedEmailsController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:banned_email) { create(:admin_blacklisted_email) }
  let(:banned_email_params) { attributes_for(:admin_blacklisted_email) }

  shared_examples "only authorized admins are allowed" do
    %w[superadmin policy_and_abuse support].each do |role|
      it "succeeds for #{role} admins" do
        fake_login_admin(create(:admin, roles: [role]))
        subject
        success
      end
    end

    %w[board communications docs open_doors tag_wrangling translation].each do |role|
      it "redirects to root with error for #{role} admins" do
        fake_login_admin(create(:admin, roles: [role]))
        subject
        it_redirects_to_with_error(root_path, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    it "redirects to root with error for admins without role" do
      fake_login_admin(create(:admin, roles: []))
      subject
      it_redirects_to_with_error(root_path, "Sorry, only an authorized admin can access the page you were trying to reach.")
    end
  end

  describe "GET #index" do
    subject { get :index }

    let(:success) do
      expect(response).to render_template(:index)
    end

    it_behaves_like "only authorized admins are allowed"
  end

  describe "POST #create" do
    subject { post :create, params: { admin_blacklisted_email: banned_email_params } }

    let(:email) { banned_email_params.dig(:email) }

    let(:success) do
      it_redirects_to_with_notice(admin_blacklisted_emails_path, "Email address #{email} banned.")
    end

    it_behaves_like "only authorized admins are allowed"
  end

  describe "DELETE #destroy" do
    subject { delete :destroy, params: { id: banned_email } }

    let(:email) { banned_email.email }

    let(:success) do
      expect { banned_email.reload }.to raise_exception(ActiveRecord::RecordNotFound)
      it_redirects_to_with_notice(admin_blacklisted_emails_path, "Email address #{email} removed from banned emails list.")
    end

    it_behaves_like "only authorized admins are allowed"
  end
end
