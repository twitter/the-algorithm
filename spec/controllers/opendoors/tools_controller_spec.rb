require "spec_helper"

describe Opendoors::ToolsController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:user) { create(:user) }
  let(:opendoors_user) { create(:opendoors_user) }

  describe "GET #index" do
    it "denies access if not logged in with Open Doors privileges" do
      fake_logout
      get :index
      it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")

      fake_login_known_user(user)
      get :index
      it_redirects_to_with_error(user_path(user), "Sorry, you don't have permission to access the page you were trying to reach.")
    end

    context "when logged in with Open Doors privileges" do
      before { fake_login_known_user(opendoors_user) }

      it "shows the tools page" do
        get :index
        expect(response).to render_template("index")
        expect(assigns(:external_author)).to be_a_new(ExternalAuthor)
        expect(assigns(:imported_from_url)).to be_nil
      end

      it "optionally recognizes the imported-from URL" do
        url = "http://example.com"
        get :index, params: { imported_from_url: url }
        expect(assigns(:imported_from_url)).to eq(url)
      end
    end
  end

  describe "POST #url_update" do
    it "denies access if not logged in with Open Doors privileges" do
      fake_logout
      post :url_update
      it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")

      fake_login_known_user(user)
      post :url_update
      it_redirects_to_with_error(user_path(user), "Sorry, you don't have permission to access the page you were trying to reach.")
    end

    context "when logged in with Open Doors privileges" do
      before { fake_login_known_user(opendoors_user) }

      it "redirects to tools if work URL is missing" do
        post :url_update
        it_redirects_to_with_error(opendoors_tools_path, "We couldn't find that work on the Archive. Have you put in the full URL?")
      end

      it "redirects to tools if work URL is invalid" do
        post :url_update, params: { work_url: "/faq" }
        it_redirects_to_with_error(opendoors_tools_path, "We couldn't find that work on the Archive. Have you put in the full URL?")
      end

      it "redirects to tools if work ID is not found" do
        post :url_update, params: { work_url: "/works/7331278/" }
        it_redirects_to_with_error(opendoors_tools_path, "We couldn't find that work on the Archive. Have you put in the full URL?")
      end

      context "with a valid work ID" do
        let(:work) { create(:work) }
        let(:work_with_imported_from_url) { create(:work, imported_from_url: "http://example.org/my-immortal") }

        it "redirects to tools if imported-from URL is missing" do
          post :url_update, params: { work_url: "/works/#{work.id}/" }
          it_redirects_to_with_error(opendoors_tools_path, "The imported-from url you are trying to set doesn't seem valid.")
        end

        it "redirects to tools if imported-from URL is invalid" do
          post :url_update, params: { work_url: "/works/#{work.id}/", imported_from_url: " " }
          it_redirects_to_with_error(opendoors_tools_path, "The imported-from url you are trying to set doesn't seem valid.")
        end

        it "redirects to tools if imported-from URL is already used in another work" do
          url = work_with_imported_from_url.imported_from_url
          post :url_update, params: { work_url: "/works/#{work.id}/", imported_from_url: url }
          it_redirects_to_with_error(opendoors_tools_path(imported_from_url: url), "There is already a work imported from the url #{url}.")
        end

        it "updates work if imported-from URL is valid" do
          url = "https://example.com/share/?url=https://example.com/dead-archive"
          post :url_update, params: { work_url: "http://example.org/works/#{work.id}/", imported_from_url: url }
          it_redirects_to_with_notice(opendoors_tools_path(imported_from_url: url), "Updated imported-from url for #{work.title} to #{url}")
          work.reload
          expect(work.imported_from_url).to eq(url)
        end

        it "updates work if imported-from URL has non-ASCII characters" do
          url = "https://example.com/work/resurrección"
          post :url_update, params: { work_url: "http://example.org/works/#{work.id}/", imported_from_url: url }
          encoded_url = URI::Parser.new.escape(url)
          it_redirects_to_with_notice(opendoors_tools_path(imported_from_url: encoded_url), "Updated imported-from url for #{work.title} to #{encoded_url}")
          work.reload
          expect(work.imported_from_url).to eq(encoded_url)
        end
      end
    end
  end
end
