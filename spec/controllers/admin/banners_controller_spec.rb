require "spec_helper"

describe Admin::BannersController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:admin_banner) { create(:admin_banner) }
  let(:admin_banner_params) { attributes_for(:admin_banner) }

  shared_examples "only authorized admins are allowed" do
    %w[support communications superadmin board].each do |role|
      it "succeeds for #{role} admins" do
        fake_login_admin(create(:admin, roles: [role]))
        subject
        success
      end
    end

    %w[translation tag_wrangling docs policy_and_abuse open_doors].each do |role|
      it "displays an error to #{role} admins" do
        fake_login_admin(create(:admin, roles: [role]))
        subject
        it_redirects_to_with_error(root_path, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end
  end

  describe "GET #index" do
    subject { get :index }

    let(:success) do
      expect(response).to render_template(:index)
    end

    it_behaves_like "only authorized admins are allowed"
  end

  describe "GET #show" do
    subject { get :show, params: { id: admin_banner } }

    let(:success) do
      expect(response).to render_template(:show)
    end

    it_behaves_like "only authorized admins are allowed"
  end

  describe "GET #new" do
    subject { get :new }

    let(:success) do
      expect(response).to render_template(:new)
    end

    it_behaves_like "only authorized admins are allowed"
  end

  describe "POST #create" do
    subject { post :create, params: { admin_banner: admin_banner_params } }

    let(:success) do
      it_redirects_to_with_notice(assigns[:admin_banner], "Banner successfully created.")
    end

    it_behaves_like "only authorized admins are allowed"
  end

  describe "GET #edit" do
    subject { get :edit, params: { id: admin_banner } }

    let(:success) do
      expect(response).to render_template(:edit)
    end

    it_behaves_like "only authorized admins are allowed"
  end

  describe "PUT #update" do
    subject { put :update, params: { id: admin_banner, admin_banner: admin_banner_params } }

    let(:success) do
      expect { admin_banner.reload }.to change { admin_banner.content }
      it_redirects_to_with_notice(admin_banner, "Banner successfully updated.")
    end

    it_behaves_like "only authorized admins are allowed"
  end

  describe "GET #confirm_delete" do
    subject { get :confirm_delete, params: { id: admin_banner } }

    let(:success) do
      expect(response).to render_template(:confirm_delete)
    end

    it_behaves_like "only authorized admins are allowed"
  end

  describe "DELETE #destroy" do
    subject { delete :destroy, params: { id: admin_banner } }

    let(:success) do
      expect { admin_banner.reload }.to raise_exception(ActiveRecord::RecordNotFound)
      it_redirects_to_with_notice(admin_banners_path, "Banner successfully deleted.")
    end

    it_behaves_like "only authorized admins are allowed"
  end
end
