require "spec_helper"

describe Admin::ActivitiesController do
  include LoginMacros
  include RedirectExpectationHelper

  let!(:admin_activity) { create(:admin_activity) }
  let(:admin) { create(:admin) }

  before { fake_login_admin(admin) }

  shared_examples "unauthorized" do
    it "redirects with an error" do
      it_redirects_to_with_error(root_path, "Sorry, only an authorized admin can access the page you were trying to reach.")
    end
  end

  describe "GET #index" do
    before { get :index }

    context "when logged in as an admin with no role" do
      it_behaves_like "unauthorized"
    end

    %w[board communications translation tag_wrangling docs support open_doors].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }

        it_behaves_like "unauthorized"
      end
    end

    %w[policy_and_abuse superadmin].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }

        it "renders the index" do
          expect(assigns[:activities]).to eq([admin_activity])
          expect(response).to render_template(:index)
        end
      end
    end
  end

  describe "GET #show" do
    before { get :show, params: { id: admin_activity.id } }

    context "when logged in as an admin with no role" do
      it_behaves_like "unauthorized"
    end

    %w[board communications translation tag_wrangling docs support open_doors].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }

        it_behaves_like "unauthorized"
      end
    end

    %w[policy_and_abuse superadmin].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }

        it "renders the info about the activity" do
          expect(assigns[:activity]).to eq(admin_activity)
          expect(response).to render_template(:show)
        end
      end
    end
  end
end
