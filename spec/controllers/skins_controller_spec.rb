# frozen_string_literal: true

require "spec_helper"

describe SkinsController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:admin) { create(:admin) }

  before { fake_login_admin(admin) }

  describe "GET #edit" do
    shared_examples "unauthorized admin cannot edit" do
      it "redirects with error" do
        get :edit, params: { id: skin.id }
        it_redirects_to_with_error(root_path(skin), "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    shared_examples "authorized admin can edit" do
      it "renders edit template" do
        get :edit, params: { id: skin.id }
        expect(response).to render_template(:edit)
      end
    end

    context "with a site skin" do
      let(:skin) { create(:skin, :public) }

      context "when admin has no role" do
        it_behaves_like "unauthorized admin cannot edit"
      end

      %w[board communications docs open_doors policy_and_abuse support tag_wrangling translation].each do |role|
        context "when admin has #{role} role" do
          let(:admin) { create(:admin, roles: [role]) }

          it_behaves_like "unauthorized admin cannot edit"
        end
      end

      context "when admin has superadmin role" do
        let(:admin) { create(:admin, roles: ["superadmin"]) }

        it_behaves_like "authorized admin can edit"
      end
    end

    context "with a work skin" do
      let(:skin) { create(:work_skin, :public) }

      context "when admin has no role" do
        it_behaves_like "unauthorized admin cannot edit"
      end

      %w[board communications docs open_doors policy_and_abuse tag_wrangling translation].each do |role|
        context "when admin has #{role} role" do
          let(:admin) { create(:admin, roles: [role]) }

          it_behaves_like "unauthorized admin cannot edit"
        end
      end

      %w[superadmin support].each do |role|
        context "when admin has #{role} role" do
          let(:admin) { create(:admin, roles: [role]) }

          it_behaves_like "authorized admin can edit"
        end
      end
    end
  end

  describe "PUT #update" do
    let(:skin_params) do
      {
        skin: {
          title: "Edited title"
        }
      }
    end

    shared_examples "unauthorized admin cannot update" do
      it "does not modify the skin" do
        expect do
          put :update, params: { id: skin.id }.merge(skin_params)
        end.not_to change { skin.reload.title }
      end
    end

    shared_examples "authorized admin can update" do
      it "modifies the skin" do
        expect do
          put :update, params: { id: skin.id }.merge(skin_params)
        end.to change { skin.reload.title }.to("Edited title")
      end
    end

    context "with a site skin" do
      let(:skin) { create(:skin, :public) }

      context "when admin has no role" do
        it_behaves_like "unauthorized admin cannot update"
      end

      %w[board communications docs open_doors policy_and_abuse support tag_wrangling translation].each do |role|
        context "when admin has #{role} role" do
          let(:admin) { create(:admin, roles: [role]) }

          it_behaves_like "unauthorized admin cannot update"
        end
      end

      context "when admin has superadmin role" do
        let(:admin) { create(:admin, roles: ["superadmin"]) }

        it_behaves_like "authorized admin can update"
      end
    end

    context "with a work skin" do
      let(:skin) { create(:work_skin, :public) }

      context "when admin has no role" do
        it_behaves_like "unauthorized admin cannot update"
      end

      %w[board communications docs open_doors policy_and_abuse tag_wrangling translation].each do |role|
        context "when admin has #{role} role" do
          let(:admin) { create(:admin, roles: [role]) }

          it_behaves_like "unauthorized admin cannot update"
        end
      end

      %w[superadmin support].each do |role|
        context "when admin has #{role} role" do
          let(:admin) { create(:admin, roles: [role]) }

          it_behaves_like "authorized admin can update"
        end
      end
    end
  end
end
