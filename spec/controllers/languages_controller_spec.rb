require "spec_helper"

describe LanguagesController do
  include LoginMacros
  include RedirectExpectationHelper

  describe "GET index" do
    context "when not logged in" do
      it "renders the index template" do
        get :index
        expect(response).to render_template("index")
      end
    end
    
    %w[board communications policy_and_abuse tag_wrangling docs support open_doors translation superadmin].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }

        it "renders the index template" do
          fake_login_admin(admin)
          get :index
          expect(response).to render_template("index")
        end
      end
    end
  end

  describe "GET show" do
    context "when not logged in" do
      it "renders the show template" do
        get :show, params: { id: "en" }
        expect(response).to render_template("show")
      end
    end

    %w[board communications policy_and_abuse tag_wrangling docs support open_doors translation superadmin].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }

        it "renders the show template" do
          get :show, params: { id: "en" }
          expect(response).to render_template("show")
        end
      end
    end
  end

  describe "GET new" do
    context "when not logged in" do
      it "redirects with error" do
        get :new
        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    %w[board communications policy_and_abuse tag_wrangling docs support open_doors].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }
        
        it "redirects with error" do
          fake_login_admin(admin)
          get :new
          it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
        end
      end
    end

    %w[translation superadmin].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }

        it "renders the new template" do
          fake_login_admin(admin)
          get :new
          expect(response).to render_template("new")
        end
      end
    end
  end

  describe "POST create" do
    let(:language_params) { 
      {
        language: {
          name: "Suomi",
          short: "fi",
          support_available: false,
          abuse_support_available: false,
          sortable_name: "su"
        }
      }
    }

    context "when not logged in" do
      it "redirects with error" do
        post :create, params: language_params

        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    %w[board communications policy_and_abuse tag_wrangling docs support open_doors].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }
        
        it "redirects with error" do
          fake_login_admin(admin)
          post :create, params: language_params

          it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
        end
      end
    end

    %w[translation superadmin].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }
        
        before do
          fake_login_admin(admin)
          post :create, params: language_params
        end

        it "creates the new language" do
          new_lang = Language.last
          expect(new_lang.name).to eq("Suomi")
          expect(new_lang.short).to eq("fi")
          expect(new_lang.support_available).to eq(false)
          expect(new_lang.abuse_support_available).to eq(false)
          expect(new_lang.sortable_name).to eq("su")
        end

        it "redirects to languages_path with notice" do
          it_redirects_to_with_notice(languages_path, "Language was successfully added.")
        end
      end
    end
  end

  describe "GET edit" do
    context "when not logged in" do
      it "redirects with error" do
        get :edit, params: { id: "en" }

        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end
    
    %w[board communications policy_and_abuse tag_wrangling docs support open_doors].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }
        
        it "redirects with error" do
          fake_login_admin(admin)
          get :edit, params: { id: "en" }

          it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
        end
      end
    end

    %w[translation superadmin].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }

        it "renders the edit template" do
          fake_login_admin(admin)
          get :edit, params: { id: "en" }
          expect(response).to render_template("edit")
        end
      end
    end
  end

  describe "PUT update" do
    let(:finnish) { Language.create(name: "Suomi", short: "fi") }
    let(:language_params) { 
      {
        id: finnish.short,
        language: {
          name: "Suomi",
          short: "fi",
          support_available: true,
          abuse_support_available: false,
          sortable_name: "su"
        }
      }
    }

    context "when not logged in" do
      it "redirects with error" do
        put :update, params: language_params

        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    %w[board communications policy_and_abuse tag_wrangling docs support open_doors].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }
        
        it "redirects with error" do
          fake_login_admin(admin)
          put :update, params: language_params

          it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
        end
      end
    end

    %w[translation superadmin].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }

        before do
          fake_login_admin(admin)
          put :update, params: language_params
        end

        it "updates the language" do
          finnish.reload
          expect(finnish.name).to eq("Suomi")
          expect(finnish.short).to eq("fi")
          expect(finnish.support_available).to eq(true)
          expect(finnish.abuse_support_available).to eq(false)
          expect(finnish.sortable_name).to eq("su")
        end

        it "redirects and returns success message" do
          it_redirects_to_with_notice(finnish, "Language was successfully updated.")
        end
      end
    end
  end
end
