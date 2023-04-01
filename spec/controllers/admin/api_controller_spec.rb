# frozen_string_literal: true
require "spec_helper"

describe Admin::ApiController do
  include LoginMacros
  include RedirectExpectationHelper

  describe "GET #index" do
    let(:params) { {} }

    context "where there is no user or admin logged in" do
      it "redirects to the homepage with a notice" do
        get :index, params: params
        it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
      end
    end

    context "where user is not an admin" do
      let(:user) { FactoryBot.create(:user) }

      before do
        fake_login_known_user(user)
      end

      it "redirects to the homepage with a notice" do
        get :index, params: params
        it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
      end
    end

    context "where admin is logged in" do
      render_views
      let(:admin) { FactoryBot.create(:admin) }

      before do
        fake_login_admin(admin)
      end

      let(:api_key_prefixes) { %w(a b c) }
      let!(:api_keys) do
        api_key_prefixes.each do |p|
          FactoryBot.create(:api_key, name: "#{p}_key")
        end
      end

      context "where no query params are set" do
        it "returns a successful response with all api keys" do
          get :index, params: params
          expect(response).to have_http_status(:success)
          api_key_prefixes.each do |p|
            expect(response.body).to include("#{p}_key")
          end
        end
      end

      context "where query params are set" do
        let(:params) { { query: "a_key" } }
        it "returns a successful response with a filtered list of api keys" do
          get :index, params: params
          expect(response).to have_http_status(:success)
          expect(response.body).to include("a_key")
          expect(response.body).to_not include("b_key")
          expect(response.body).to_not include("c_key")
        end
      end
    end
  end

  describe "GET #new" do
    context "where an admin is logged in" do
      let(:admin) { FactoryBot.create(:admin) }

      before do
        fake_login_admin(admin)
      end
      it "responds with the new api key form" do
        get :new
        expect(response).to have_http_status(:success)
        assert_template :new
      end
    end
  end

  describe "POST #create" do
    context "where an admin is logged in" do
      let(:admin) { FactoryBot.create(:admin) }
      let(:params) { {} }

      before do
        fake_login_admin(admin)
      end

      context "with an api key param" do
        let(:api_key_name) { "api_key" }
        let(:api_key_params) { { name: api_key_name } }
        let(:params) { { api_key: api_key_params } }

        it "redirects to the homepage and notifies of the success" do
          post :create, params: params
          expect(ApiKey.where(name: api_key_name)).to_not be_empty
          it_redirects_to_with_notice(admin_api_index_path, "New token successfully created")
        end
      end

      context "without an api key param" do
        it "shows the new api view" do
          post :create, params: params
          expect(response).to have_http_status(:success)
          assert_template :new
        end
      end

      context "the save was unsuccessful" do
        let(:api_key_name) { "api_key" }
        let(:api_key_params) { { name: api_key_name } }
        let(:params) { { api_key: api_key_params } }

        before do
          allow_any_instance_of(ApiKey).to receive(:save).and_return(false)
        end

        it "shows the new api view" do
          post :create, params: params
          expect(ApiKey.where(name: api_key_name)).to be_empty
          assert_template :new
        end
      end

      context "cancel_button is present" do
        let(:params) { { cancel_button: "Cancel" } }

        it "redirects to index" do
          post :create, params: params
          it_redirects_to admin_api_index_path
        end
      end
    end
  end

  describe "GET #edit" do
    context "where an admin is logged in" do
      let(:admin) { FactoryBot.create(:admin) }

      before do
        fake_login_admin(admin)
      end

      context "where the api key exists" do
        render_views
        let!(:api_key) { FactoryBot.create(:api_key, name: "api_key") }

        it "populates the form with the api key" do
          get :edit, params: { id: api_key.id }
          expect(response).to have_http_status(:success)
          expect(response.body).to include(api_key.name)
        end
      end

      context "where the api key can't be found" do
        it "raises an error" do
          assert_raises ActiveRecord::RecordNotFound do
            get :edit, params: { id: 123 }
          end
        end
      end
    end
  end

  describe "POST #update" do
    context "where an admin is logged in" do
      let(:admin) { FactoryBot.create(:admin) }

      before do
        fake_login_admin(admin)
      end

      context "where the api key exists" do
        let(:api_key) { FactoryBot.create(:api_key) }
        let(:new_name) { "new_name" }
        let(:params) do
          {
            id: api_key.id,
            api_key: { name: new_name }
          }
        end

        context "where the update is successful" do
          it "updates the api key and redirects to index" do
            expect(ApiKey.where(name: new_name)).to be_empty
            post :update, params: params
            expect(ApiKey.where(name: new_name)).to_not be_empty
            it_redirects_to_with_notice(admin_api_index_path, "Access token was successfully updated")
          end
        end

        context "where the update is unsuccessful" do
          before do
            allow_any_instance_of(ApiKey).to receive(:update).and_return(false)
          end

          it "shows the edit view" do
            post :update, params: { id: api_key.id, api_key: { name: "new_name" } }
            expect(ApiKey.where(name: "new_name")).to be_empty
            assert_template :edit
          end
        end
      end

      context "where the api key doesn't exist" do
        it "raises an error" do
          assert_raises ActiveRecord::RecordNotFound do
            post :update, params: { id: 123, api_key: { name: "new_name" } }
          end
        end
      end

      context "cancel_button is true" do
        let(:api_key_id) { 123 }
        let!(:api_key) { FactoryBot.create(:api_key, id: api_key_id) }

        it "redirects to index" do
          post :update, params: { id: api_key_id, cancel_button: "Cancel" }
          it_redirects_to admin_api_index_path
        end
      end
    end
  end

  describe "POST #destroy" do
    context "where an admin is logged in" do
      let(:admin) { FactoryBot.create(:admin) }

      before do
        fake_login_admin(admin)
      end

      context "where the api key exists" do
        let(:api_key_id) { 123 }
        let!(:api_key) { FactoryBot.create(:api_key, id: api_key_id) }

        it "destroys the api key, then redirects to edit" do
          post :destroy, params: { id: api_key_id }
          expect(ApiKey.where(id: api_key_id)).to be_empty
          expect(response).to redirect_to admin_api_path
        end
      end

      context "where the api key doesn't exist" do
        it "raises an error" do
          assert_raises ActiveRecord::RecordNotFound do
            post :destroy, params: { id: 123 }
          end
        end
      end
    end
  end
end
