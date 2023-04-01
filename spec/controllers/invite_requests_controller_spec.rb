require "spec_helper"

describe InviteRequestsController do
  include LoginMacros
  include RedirectExpectationHelper
  let(:admin) { create(:admin) }

  before { fake_logout }

  describe "GET #index" do
    it "renders" do
      get :index
      expect(response).to render_template("index")
      expect(assigns(:invite_request)).to be_a_new(InviteRequest)
    end
  end

  describe "GET #show" do
    context "when given invalid emails" do
      it "redirects to index with error" do
        message = "You can search for the email address you signed up with below. If you can't find it, your invitation may have already been emailed to that address; please check your email spam folder as your spam filters may have placed it there."
        get :show, params: { id: 0 }
        it_redirects_to_with_error(status_invite_requests_path, message)
        expect(assigns(:invite_request)).to be_nil
        get :show, params: { id: 0, email: "mistressofallevil@example.org" }
        it_redirects_to_with_error(status_invite_requests_path, message)
        expect(assigns(:invite_request)).to be_nil
      end

      it "renders for an ajax call" do
        get :show, params: { id: 0 }, xhr: true
        expect(response).to render_template("show")
        expect(assigns(:invite_request)).to be_nil
        get :show, params: { id: 0, email: "mistressofallevil@example.org" }, xhr: true
        expect(response).to render_template("show")
        expect(assigns(:invite_request)).to be_nil
      end
    end

    context "when given valid emails" do
      let(:invite_request) { create(:invite_request) }

      it "renders" do
        get :show, params: { id: 0, email: invite_request.email }
        expect(response).to render_template("show")
        expect(assigns(:invite_request)).to eq(invite_request)
      end

      it "renders for an ajax call" do
        get :show, params: { id: 0, email: invite_request.email }, xhr: true
        expect(response).to render_template("show")
        expect(assigns(:invite_request)).to eq(invite_request)
      end
    end
  end

  describe "POST #create" do
    it "redirects to index with error given invalid emails" do
      post :create, params: { invite_request: { email: "wat" } }
      expect(response).to render_template("index")
    end

    context "with valid emails" do
      let(:ip) { "127.0.0.1" }

      before do
        allow_any_instance_of(ActionDispatch::Request).to receive(:remote_ip).and_return(ip)
      end

      it "redirects to index with notice" do
        email = generate(:email)
        post :create, params: { invite_request: { email: email } }
        invite_request = InviteRequest.find_by!(email: email)
        it_redirects_to_with_notice(invite_requests_path, "You've been added to our queue! Yay! We estimate that you'll receive an invitation around #{invite_request.proposed_fill_date}. We strongly recommend that you add do-not-reply@archiveofourown.org to your address book to prevent the invitation email from getting blocked as spam by your email provider.")
      end

      it "assigns an IP address to the request" do
        post :create, params: { invite_request: { email: generate(:email) } }
        expect(assigns(:invite_request).ip_address).to eq(ip)
      end
    end

    context "when invite queue is disabled" do
      before do
        AdminSetting.first.update_attribute(:invite_from_queue_enabled, false)
      end

      it "redirects to index with error" do
        post :create, params: { invite_request: { email: generate(:email) } }
        it_redirects_to_simple(invite_requests_path)
        expect(flash[:error]).to include("New invitation requests are currently closed.")
        expect(assigns(:admin_settings).invite_from_queue_enabled?).to be_falsey
      end
    end
  end

  describe "DELETE #destroy" do
    context "when logged out" do
      it "redirects to root with authorization error" do
        delete :destroy, params: { id: 0 }
        it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
      end
    end

    context "when logged in as user" do
      it "redirects to root with authorization error" do
        fake_login
        delete :destroy, params: { id: 0 }
        it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
      end
    end

    context "when logged in as admin" do
      let(:invite_request) { create(:invite_request) }

      %w[superadmin policy_and_abuse support].each do |admin_role|
        context "with #{admin_role} role" do

          before do
            admin.update(roles: [admin_role])
            fake_login_admin(admin)
          end

          context "when format is HTML" do
            it "deletes request and redirects to manage with notice" do
              delete :destroy, params: { id: invite_request.id }
              it_redirects_to_with_notice(manage_invite_requests_path, "Request for #{invite_request.email} was removed from the queue.")
              expect { invite_request.reload }.to raise_error ActiveRecord::RecordNotFound
            end

            it "redirects to manage at specified page when page params are present" do
              page = 45_789
              delete :destroy, params: { id: invite_request.id, page: page }
              it_redirects_to_with_notice(manage_invite_requests_path(page: page), "Request for #{invite_request.email} was removed from the queue.")
            end

            it "redirects to manage with error when deletion fails" do
              allow_any_instance_of(InviteRequest).to receive(:destroy).and_return(false)
              delete :destroy, params: { id: invite_request.id }
              it_redirects_to_with_error(manage_invite_requests_path, "Request could not be removed. Please try again.")
            end

            it "raises RecordNotFound exception for nonexistent request" do
              invite_request.destroy
              expect do
                delete :destroy, params: { id: invite_request.id }
              end.to raise_exception(ActiveRecord::RecordNotFound)
            end
          end

          context "when there are multiple requests" do
            let!(:invite_request_1) { create(:invite_request) }
            let!(:invite_request_2) { create(:invite_request) }
            let!(:invite_request_3) { create(:invite_request) }

            it "deletes the specified request" do
              delete :destroy, params: { id: invite_request_2.id }
              it_redirects_to_with_notice(manage_invite_requests_path, "Request for #{invite_request_2.email} was removed from the queue.")
              expect { invite_request_2.reload }.to raise_error ActiveRecord::RecordNotFound
              invite_request_1.reload
              invite_request_3.reload
            end
          end

          context "when format is JSON" do
            it "deletes request and responds with success status and message" do
              delete :destroy, params: { id: invite_request.id, format: :json }
              parsed_body = JSON.parse(response.body, symbolize_names: true)
              expect(parsed_body[:item_success_message]).to eq("Request for #{invite_request.email} was removed from the queue.")
              expect(response).to have_http_status(:success)
              expect { invite_request.reload }.to raise_error ActiveRecord::RecordNotFound
            end

            it "fails with an error" do
              allow_any_instance_of(InviteRequest).to receive(:destroy).and_return(false)
              delete :destroy, params: { id: invite_request.id, format: :json }
              parsed_body = JSON.parse(response.body, symbolize_names: true)
              expect(parsed_body[:errors]).to eq("Request could not be removed. Please try again.")
            end
          end
        end
      end

      %w[board communications docs open_doors tag_wrangling translation].each do |admin_role|
        context "with #{admin_role} role" do
          before do
            admin.update(roles: [admin_role])
            fake_login_admin(admin)
          end

          context "when format is HTML" do
            it "redirects to root with authorization error" do
              delete :destroy, params: { id: invite_request.id }
              it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
            end

            it "raises RecordNotFound exception for nonexistent request" do
              invite_request.destroy
              expect do
                delete :destroy, params: { id: invite_request.id }
              end.to raise_exception(ActiveRecord::RecordNotFound)
            end
          end

          context "when format is JSON" do
            it "has forbidden response status" do
              delete :destroy, params: { id: invite_request.id, format: :json }
              parsed_body = JSON.parse(response.body, symbolize_names: true)
              expect(parsed_body[:errors]).to include("Sorry, only an authorized admin can do that.")
              expect(response).to have_http_status(:forbidden)
            end
          end
        end
      end
    end
  end

  describe "GET #manage" do
    context "when logged out" do
      it "redirects to root with authorization error" do
        get :manage
        it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
      end
    end

    context "when logged in as user" do
      it "redirects to root with authorization error" do
        fake_login
        get :manage
        it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
      end
    end

    context "when logged in as admin" do
      %w[superadmin policy_and_abuse support].each do |admin_role|
        context "with #{admin_role} role" do
          let(:ip) { "127.0.0.1" }
          let(:ip_2) { "128.0.0.1" }
          let!(:invite_request_1) { create(:invite_request, id: 9001, ip_address: ip_2) }
          let!(:invite_request_2) { create(:invite_request, id: 2) }
          let!(:invite_request_3) do
            create(
              :invite_request,
              id: 7,
              email: "hello_world@example.com"
            )
          end
          let!(:invite_request_4) do
            create(
              :invite_request,
              id: 500,
              ip_address: ip
            )
          end

          before do
            admin.update(roles: [admin_role])
            fake_login_admin(admin)
          end

          it "returns requests from email address matching query" do
            get :manage, params: { query: "hello_world" }
            expect(response).to render_template("manage")
            expect(assigns(:invite_requests)).to eq([invite_request_3])
          end

          it "returns requests from IP matching query" do
            get :manage, params: { query: ip }
            expect(response).to render_template("manage")
            expect(assigns(:invite_requests)).to eq([invite_request_4])
          end

          it "renders with invite requests in order" do
            get :manage
            expect(response).to render_template("manage")
            expect(assigns(:invite_requests)).to eq([
              invite_request_2,
              invite_request_3,
              invite_request_4,
              invite_request_1
            ])
          end
        end
      end

      %w[board communications docs open_doors tag_wrangling translation].each do |admin_role|
        context "with #{admin_role} role" do
          before do
            admin.update(roles: [admin_role])
            fake_login_admin(admin)
          end

          it "redirects to root with authorization error" do
            get :manage
            it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
          end

          it "redirects to root with authorization error when query params are present" do
            get :manage, params: { query: "hello_world" }
            it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
          end
        end
      end

      context "with no admin role" do
        before do
          admin.update(roles: [])
          fake_login_admin(admin)
        end

        it "redirects to root with authorization error" do
          get :manage
          it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
        end

        it "redirects to root with authorization error when query params are present" do
          get :manage, params: { query: "hello_world" }
          it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
        end
      end
    end
  end
end
