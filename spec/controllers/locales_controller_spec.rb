require "spec_helper"

describe LocalesController do
  include LoginMacros
  include RedirectExpectationHelper

  describe "GET #index" do
    context "when not logged in" do
      it "redirects with error" do
        get :index

        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end
    
    context "when logged in as user" do
      it "redirects with error" do
        fake_login
        get :index

        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end
    
    %w[board communications policy_and_abuse tag_wrangling docs support open_doors].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }
        
        it "redirects with error" do
          fake_login_admin(admin)
          get :index

          it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
        end
      end
    end
    
    %w[translation superadmin].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }

        it "displays the default locale" do
          fake_login_admin(admin)
          get :index
          expect(response).to render_template("index")
          expect(assigns(:locales)).to eq([Locale.default])
        end
      end
    end
  end

  describe "GET #new" do
    context "when not logged in" do
      it "redirects with error" do
        get :new

        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end
  
    context "when logged in as user" do
      it "redirects with error" do
        fake_login
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

        it "displays the form to create a locale" do
          fake_login_admin(admin)
          get :new

          expect(response).to render_template("new")
          expect(assigns(:languages)).to eq(Language.default_order)
          expect(assigns(:locale)).to be_a_new(Locale)
        end
      end
    end
  end

  describe "GET #edit" do
    let(:locale) { create(:locale) }

    context "when not logged in" do
      it "redirects with error" do
        get :edit, params: { id: locale.iso }

        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end
    
    context "when logged in as user" do
      it "redirects with error" do
        fake_login
        get :edit, params: { id: locale.iso }

        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    %w[board communications policy_and_abuse tag_wrangling docs support open_doors].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }
        
        it "redirects with error" do
          fake_login_admin(admin)
          get :edit, params: { id: locale.iso }

          it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
        end
      end
    end

    %w[translation superadmin].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }

        it "displays the form to update a locale" do
          fake_login_admin(admin)
          get :edit, params: { id: locale.iso }
          expect(response).to render_template("edit")
          expect(assigns(:locale)).to eq(locale)
          expect(assigns(:languages)).to eq(Language.default_order)
        end
      end
    end
  end

  describe "PUT #update" do
    let(:locale) { create(:locale) }

    context "when not logged in" do
      it "redirects with notice" do
        put :update, params: { id: locale.iso, locale: { name: "Tiếng Việt", email_enabled: true } }

        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    context "when logged in as user" do
      it "redirects with notice" do
        fake_login
        put :update, params: { id: locale.iso, locale: { name: "Tiếng Việt", email_enabled: true } }

        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    %w[board communications policy_and_abuse tag_wrangling docs support open_doors].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }
        
        it "redirects with error" do
          fake_login_admin(admin)
          put :update, params: { id: locale.iso, locale: { name: "Tiếng Việt", email_enabled: true } }

          it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
        end
      end
    end

    %w[translation superadmin].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }
        let(:locale) { create(:locale) }
        
        it "updates an existing locale" do
          fake_login_admin(admin)
          params = { name: "Tiếng Việt", email_enabled: true }
  
          put :update, params: { id: locale.iso, locale: params }
          it_redirects_to_with_notice(locales_path, "Your locale was successfully updated.")
  
          locale.reload
          expect(locale.name).to eq(params[:name])
          expect(locale.email_enabled).to eq(params[:email_enabled])
        end

        it "redirects to the edit form for the same locale if the new iso is not unique" do
          fake_login_admin(admin)
          put :update, params: { id: locale.iso, locale: { iso: Locale.default.iso } }
          expect(response).to render_template("edit")
          expect(assigns(:locale)).to eq(locale)
          expect(assigns(:languages)).to eq(Language.default_order)
        end
      end
    end
  end

  describe "POST #create" do
    let(:locale) { create(:locale) }

    context "when not logged in" do
      it "redirects with notice" do
        post :create, params: { id: locale.iso, locale: { name: "Tiếng Việt", email_enabled: true } }

        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end
    
    context "when logged in as user" do
      it "redirects with notice" do
        fake_login
        post :create, params: { id: locale.iso, locale: { name: "Tiếng Việt", email_enabled: true } }

        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    %w[board communications policy_and_abuse tag_wrangling docs support open_doors].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }
        
        it "redirects with error" do
          fake_login_admin(admin)
          post :create, params: { id: locale.iso, locale: { name: "Tiếng Việt", email_enabled: true } }

          it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
        end
      end
    end

    %w[translation superadmin].each do |role|
      context "when logged in as an admin with #{role} role" do
        let(:admin) { create(:admin, roles: [role]) }
        
        it "adds a new locale and redirects to list of locales" do
          fake_login_admin(admin)
          params = {
            name: "Español", iso: "es", language_id: Language.default.id,
            email_enabled: true, interface_enabled: false
          }
  
          post :create, params: { locale: params }
          it_redirects_to_with_notice(locales_path, "Locale was successfully added.")
  
          locale = Locale.last
          expect(locale.iso).to eq(params[:iso])
          expect(locale.name).to eq(params[:name])
          expect(locale.language.id).to eq(params[:language_id])
          expect(locale.email_enabled).to eq(params[:email_enabled])
          expect(locale.interface_enabled).to eq(params[:interface_enabled])
        end

        it "renders the create form if iso is missing" do
          fake_login_admin(admin)
          params = {
            name: "Español", language_id: Language.default.id,
            email_enabled: true, interface_enabled: false
          }
  
          post :create, params: { locale: params }
          expect(response).to render_template("new")
          expect(assigns(:languages)).to eq(Language.default_order)
          expect(Locale.last).to eq(Locale.default)
        end
      end
    end
  end
end
