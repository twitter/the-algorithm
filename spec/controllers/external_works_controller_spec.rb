require "spec_helper"

describe ExternalWorksController do
  include LoginMacros
  include RedirectExpectationHelper

  describe "GET #fetch" do
    let(:url) { "http://example.org/200" }

    before { WebMock.stub_request(:any, url) }

    context "when the URL has an external work" do
      let!(:external_work) { create(:external_work, url: url) }

      it "responds with json" do
        get :fetch, params: { external_work_url: url, format: :json }
        expect(response.content_type).to match("application/json")
      end

      it "responds with the matching work" do
        get :fetch, params: { external_work_url: url, format: :json }
        expect(assigns(:external_work)).to eq(external_work)
      end

      context "when the URL has a second external work" do
        let!(:external_work2) { create(:external_work, url: url) }

        it "responds with the first matching work" do
          get :fetch, params: { external_work_url: url, format: :json }
          expect(assigns(:external_work)).to eq(external_work)
          expect(assigns(:external_work)).not_to eq(external_work2)
        end
      end
    end

    context "when the URL doesn't have an exteral work" do
      it "responds with json" do
        get :fetch, params: { external_work_url: url, format: :json }
        expect(response.content_type).to match("application/json")
      end

      it "responds with blank" do
        get :fetch, params: { external_work_url: url, format: :json }
        expect(assigns(:external_work)).to be_nil
      end
    end
  end

  describe "POST #update" do
    let(:admin) { create(:admin) }
    let(:external_work) { create(:external_work) }

    before { fake_login_admin(admin) }

    shared_examples "unauthorized admin cannot update external works" do
      it "redirects with error" do
        expect do
          post :update, params: {
            id: external_work, external_work: attributes_for(:external_work), work: { relationship_string: "takotori" }
          }
        end.to avoid_changing { external_work.reload.updated_at }
        it_redirects_to_with_error(root_path, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    shared_examples "authorized admin can update external works" do
      it "updates the external work and redirects with notice" do
        post :update, params: {
          id: external_work, external_work: attributes_for(:external_work), work: { relationship_string: "takotori" }
        }
        it_redirects_to_with_notice(external_work_path(external_work), "External work was successfully updated.")
        expect(external_work.reload.relationship_string).to eq("takotori")
      end
    end

    context "when admin does not have correct authorization" do
      before { admin.update(roles: []) }

      it_behaves_like "unauthorized admin cannot update external works"
    end

    %w[superadmin policy_and_abuse].each do |role|
      context "when admin has #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }

        it_behaves_like "authorized admin can update external works"
      end
    end
  end
end
