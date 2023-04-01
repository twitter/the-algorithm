# frozen_string_literal: true

require "spec_helper"

describe Admin::SkinsController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:admin) { create(:admin, roles: []) }

  before { fake_login_admin(admin) }

  describe "GET #index" do
    context "when admin does not have correct authorization" do
      context "when admin has no role" do
        it "redirects with error when admin has no role" do
          get :index

          it_redirects_to_with_error(root_path, "Sorry, only an authorized admin can access the page you were trying to reach.")
        end
      end

      %w[board communications docs open_doors policy_and_abuse tag_wrangling translation].each do |role|
        context "when admin has #{role} role" do
          let(:admin) { create(:admin, roles: [role]) }

          it "redirects with error" do
            get :index

            it_redirects_to_with_error(root_path, "Sorry, only an authorized admin can access the page you were trying to reach.")
          end
        end
      end
    end

    %w[superadmin support].each do |role|
      context "when admin is authorized with the #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }

        it "renders index template" do
          get :index

          expect(response).to render_template(:index)
        end
      end
    end
  end

  describe "GET #index_approved" do
    context "when admin does not have correct authorization" do
      it "redirects with error when admin has no role" do
        get :index_approved

        it_redirects_to_with_error(root_path, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end

      %w[board communications docs open_doors policy_and_abuse tag_wrangling translation].each do |role|
        context "when admin has #{role} role" do
          let(:admin) { create(:admin, roles: [role]) }

          it "redirects with error" do
            get :index_approved

            it_redirects_to_with_error(root_path, "Sorry, only an authorized admin can access the page you were trying to reach.")
          end
        end
      end
    end

    %w[superadmin support].each do |role|
      context "when admin is authorized with the #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }

        it "renders index_approved template" do
          get :index_approved

          expect(response).to render_template(:index_approved)
        end
      end
    end
  end

  describe "GET #index_rejected" do
    context "when admin does not have correct authorization" do
      it "redirects with error when admin has no role" do
        get :index_rejected

        it_redirects_to_with_error(root_path, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end

      %w[board communications docs open_doors policy_and_abuse tag_wrangling translation].each do |role|
        context "when admin has #{role} role" do
          let(:admin) { create(:admin, roles: [role]) }

          it "redirects with error" do
            get :index_rejected

            it_redirects_to_with_error(root_path, "Sorry, only an authorized admin can access the page you were trying to reach.")
          end
        end
      end
    end

    %w[superadmin support].each do |role|
      context "when admin is authorized with the #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }

        it "renders index_rejected template" do
          get :index_rejected

          expect(response).to render_template(:index_rejected)
        end
      end
    end
  end

  describe "PUT #update" do
    let(:site_skin) { create(:skin, :public) }
    let(:work_skin) { create(:work_skin, :public) }

    shared_examples "unauthorized admin cannot update default skin" do
      before { site_skin.update(official: true) }

      it "does not modify the default skin" do
        expect do
          put :update, params: { id: :update, set_default: site_skin.title, last_updated_by: admin.id }
        end.not_to change { AdminSetting.first.default_skin }
      end

      it "redirects with error" do
        put :update, params: { id: :update, set_default: site_skin.title, last_updated_by: admin.id }
        it_redirects_to_simple(root_path)
        expect(flash[:error]).to eq("Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    shared_examples "authorized admin can update default skin" do
      before { site_skin.update(official: true) }

      it "modifies the default skin" do
        expect do
          put :update, params: { id: :update, set_default: site_skin.title, last_updated_by: admin.id }
        end.to change { AdminSetting.first.default_skin }.from(nil).to(site_skin)
      end

      it "redirects with notice" do
        put :update, params: { id: :update, set_default: site_skin.title, last_updated_by: admin.id }
        it_redirects_to_simple(admin_skins_path)
        expect(flash[:notice]).to include("Default skin changed to #{site_skin.title}")
      end
    end

    shared_examples "unauthorized admin cannot update site skin" do
      it "does not modify site skin" do
        expect do
          put :update, params: { id: :update, make_unofficial: [site_skin.id] }
        end.not_to change { site_skin.reload.official }
      end

      it "redirects with error" do
        put :update, params: { id: :update, make_unofficial: [site_skin.id] }
        it_redirects_to_with_error(root_path, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    shared_examples "authorized admin can update site skin" do
      it "modifies site skin" do
        expect do
          put :update, params: { id: :update, make_unofficial: [site_skin.id] }
        end.to change { site_skin.reload.official }
      end

      it "redirects with notice" do
        put :update, params: { id: :update, make_unofficial: [site_skin.id] }
        it_redirects_to_simple(admin_skins_path)
        expect(flash[:notice]).to include("The following skins were updated: #{site_skin.title}")
      end
    end

    shared_examples "unauthorized admin cannot update work skin" do
      it "does not modify work skin" do
        expect do
          put :update, params: { id: :update, make_unofficial: [work_skin.id] }
        end.not_to change { work_skin.reload.official }
      end

      it "redirects with error" do
        put :update, params: { id: :update, make_unofficial: [work_skin.id] }
        it_redirects_to_with_error(root_path, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    shared_examples "authorized admin can update work skin" do
      it "modifies work skin" do
        expect do
          put :update, params: { id: :update, make_unofficial: [work_skin.id] }
        end.to change { work_skin.reload.official }
      end

      it "redirects with notice" do
        put :update, params: { id: :update, make_unofficial: [work_skin.id] }
        it_redirects_to_simple(admin_skins_path)
        expect(flash[:notice]).to include("The following skins were updated: #{work_skin.title}")
      end
    end

    context "when admin has no role" do
      it_behaves_like "unauthorized admin cannot update default skin"
      it_behaves_like "unauthorized admin cannot update site skin"
      it_behaves_like "unauthorized admin cannot update work skin"
    end

    %w[board communications docs open_doors policy_and_abuse tag_wrangling translation].each do |role|
      context "when admin has #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }

        it_behaves_like "unauthorized admin cannot update default skin"
        it_behaves_like "unauthorized admin cannot update site skin"
        it_behaves_like "unauthorized admin cannot update work skin"
      end
    end

    context "when admin has superadmin role" do
      let(:admin) { create(:admin, roles: ["superadmin"]) }

      it_behaves_like "authorized admin can update default skin"
      it_behaves_like "authorized admin can update site skin"
      it_behaves_like "authorized admin can update work skin"

      context "when updating site and work skin simultaneously" do
        it "modifies work skin" do
          expect do
            put :update, params: { id: :update, make_unofficial: [work_skin.id, site_skin.id] }
          end.to change { work_skin.reload.official }
        end

        it "modifies site skin" do
          expect do
            put :update, params: { id: :update, make_unofficial: [work_skin.id, site_skin.id] }
          end.to change { site_skin.reload.official }
        end

        it "redirects with notice" do
          put :update, params: { id: :update, make_unofficial: [work_skin.id, site_skin.id] }
          it_redirects_to_with_notice(admin_skins_path, ["The following skins were updated: #{work_skin.title}, #{site_skin.title}"])
        end
      end
    end

    context "when admin has support role" do
      let(:admin) { create(:admin, roles: ["support"]) }

      it_behaves_like "unauthorized admin cannot update default skin"
      it_behaves_like "authorized admin can update work skin"

      context "when attempting to update a site skin" do
        it "does not modify the site skin" do
          expect do
            put :update, params: { id: :update, make_unofficial: [site_skin.id] }
          end.not_to change { site_skin.reload.official }
        end

        it "redirects with no notice" do
          put :update, params: { id: :update, make_unofficial: [site_skin.id] }
          it_redirects_to_with_notice(admin_skins_path, ["The following skins were updated: "])
        end
      end

      context "when updating site and work skin simultaneously" do
        it "modifies work skin" do
          expect do
            put :update, params: { id: :update, make_unofficial: [work_skin.id, site_skin.id] }
          end.to change { work_skin.reload.official }
        end

        it "does not modify site skin" do
          expect do
            put :update, params: { id: :update, make_unofficial: [work_skin.id, site_skin.id] }
          end.not_to change { site_skin.reload.official }
        end

        it "redirects with notice" do
          put :update, params: { id: :update, make_unofficial: [work_skin.id, site_skin.id] }
          it_redirects_to_with_notice(admin_skins_path, ["The following skins were updated: #{work_skin.title}"])
        end
      end
    end
  end
end
