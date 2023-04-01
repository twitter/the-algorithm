require "spec_helper"

describe Admin::SettingsController do
  include LoginMacros
  include RedirectExpectationHelper

  describe "GET #index" do
    it "denies access to guest users" do
      get :index
      it_redirects_to_with_notice(root_url, "I'm sorry, only an admin can look at that area")
    end

    context "when logged in as admin" do
      let(:admin) { create(:admin, roles: []) }

      before { fake_login_admin(admin) }

      it "denies access to admins without correct roles" do
        get :index
        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end

      it "allows access to admins with correct roles" do
        admin.update(roles: ["policy_and_abuse"])
        get :index
        expect(response).to have_http_status(:success)
      end
    end
  end

  describe "PUT #update" do
    let(:admin) { create(:admin, roles: []) }
    let(:setting) { AdminSetting.default }

    context "when admin does not have correct authorization" do
      it "denies random admin access" do
        fake_login_admin(admin)
        put :update, params: { id: setting.id, admin_setting: { hide_spam: "1" } }
        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    context "when admin has correct authorization" do
      before { fake_login_admin(admin) }

      context "when admin has superadmin role" do
        before { admin.update(roles: ["superadmin"]) }

        it "allows superadmins to update all settings" do
          put :update, params: {
            id: setting.id,
            admin_setting: {
              account_creation_enabled: "1",
              creation_requires_invite: "1",
              request_invite_enabled: "0",
              invite_from_queue_enabled: "1",
              invite_from_queue_number: "10",
              invite_from_queue_frequency: "7",
              days_to_purge_unactivated: "7",
              disable_support_form: "1",
              disabled_support_form_text: "Disable support",
              suspend_filter_counts: "0",
              tag_wrangling_off: "0",
              downloads_enabled: "1",
              enable_test_caching: "0",
              cache_expiration: "10",
              hide_spam: "1"
            }
          }

          it_redirects_to_with_notice(admin_settings_path, "Archive settings were successfully updated.")
        end
      end

      context "when admin has policy_and_abuse role" do
        before { admin.update(roles: ["policy_and_abuse"]) }

        {
          disable_support_form: true,
          downloads_enabled: false,
          tag_wrangling_off: true
        }.each_pair do |field, value|
          it "prevents admins with policy_and_abuse role from updating #{field}" do
            expect do
              put :update, params: { id: setting.id, admin_setting: { field => value } }
            end.to raise_exception(ActionController::UnpermittedParameters)
            expect(setting.reload.send(field)).not_to eq(value)
          end
        end

        {
          hide_spam: true,
          invite_from_queue_enabled: false,
          invite_from_queue_number: 11
        }.each_pair do |field, value|
          it "allows admins with policy_and_abuse role to update #{field}" do
            put :update, params: { id: setting.id, admin_setting: { field => value } }
            expect(setting.reload.send(field)).to eq(value)
            it_redirects_to_with_notice(admin_settings_path, "Archive settings were successfully updated.")
          end
        end
      end

      context "when admin has support role" do
        before { admin.update(roles: ["support"]) }

        {
          downloads_enabled: false,
          hide_spam: true,
          tag_wrangling_off: true
        }.each_pair do |field, value|
          it "prevents admins with support role from updating #{field}" do
            expect do
              put :update, params: { id: setting.id, admin_setting: { field => value } }
            end.to raise_exception(ActionController::UnpermittedParameters)
            expect(setting.reload.send(field)).not_to eq(value)
          end
        end

        it "allows admins with support role to update support form settings" do
          put :update, params: {
            id: setting.id,
            admin_setting: {
              disable_support_form: true,
              disabled_support_form_text: "Disable support"
            }
          }
          expect(setting.reload.disable_support_form?).to be_truthy
          expect(setting.reload.disabled_support_form_text).to include("Disable support")
          it_redirects_to_with_notice(admin_settings_path, "Archive settings were successfully updated.")
        end
      end

      context "when admin has tag_wrangling role" do
        before { admin.update(roles: ["tag_wrangling"]) }

        {
          disable_support_form: true,
          downloads_enabled: false,
          hide_spam: true
        }.each_pair do |field, value|
          it "prevents admins with tag_wrangling role from updating #{field}" do
            expect do
              put :update, params: { id: setting.id, admin_setting: { field => value } }
            end.to raise_exception(ActionController::UnpermittedParameters)
            expect(setting.reload.send(field)).not_to eq(value)
          end
        end

        it "allows admins with tag_wrangling role to turn wrangling off" do
          put :update, params: { id: setting.id, admin_setting: { tag_wrangling_off: "1" } }
          expect(setting.reload.tag_wrangling_off?).to be_truthy
          it_redirects_to_with_notice(admin_settings_path, "Archive settings were successfully updated.")
        end
      end
    end
  end
end
