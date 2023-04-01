# frozen_string_literal: true

require "spec_helper"

describe KudosController do
  include LoginMacros
  include RedirectExpectationHelper

  describe "POST #create" do
    context "when work is public" do
      let(:work) { create(:work) }
      let(:referer) { work_path(work) }
      before { request.headers["HTTP_REFERER"] = referer }

      context "when kudos giver is a guest" do
        context "when kudos are given from work" do
          it "redirects to referer with a notice" do
            post :create, params: { kudo: { commentable_id: work.id, commentable_type: "Work" } }
            it_redirects_to_with_kudos_notice(referer, "Thank you for leaving kudos!")
          end

          it "does not save user on kudos" do
            post :create, params: { kudo: { commentable_id: work.id, commentable_type: "Work" } }
            expect(assigns(:kudo)).to be_persisted
            expect(assigns(:kudo).user).to be_nil
          end
        end

        context "when kudos are given from chapter" do
          it "redirects to referer with an error" do
            post :create, params: { kudo: { commentable_id: work.first_chapter.id, commentable_type: "Chapter" } }
            it_redirects_to_with_kudos_error(referer, "What did you want to leave kudos on?")
          end

          it "does not save kudos" do
            post :create, params: { kudo: { commentable_id: work.first_chapter.id, commentable_type: "Chapter" } }
            expect(assigns(:kudo)).not_to be_persisted
          end
        end
      end

      context "when kudos giver is logged in" do
        let(:user) { create(:user) }
        before { fake_login_known_user(user) }

        it "redirects to referer with a notice" do
          post :create, params: { kudo: { commentable_id: work.id, commentable_type: "Work" } }
          it_redirects_to_with_kudos_notice(referer, "Thank you for leaving kudos!")
        end

        it "saves user on kudos" do
          post :create, params: { kudo: { commentable_id: work.id, commentable_type: "Work" } }
          expect(assigns(:kudo).user).to eq(user)
        end

        context "when kudos giver has already left kudos on the work" do
          let!(:old_kudo) { create(:kudo, user: user, commentable: work) }

          it "redirects to referer with an error" do
            post :create, params: { kudo: { commentable_id: work.id, commentable_type: "Work" } }
            it_redirects_to_with_kudos_error(referer, "You have already left kudos here. :)")
          end

          context "when duplicate database inserts happen despite Rails validations" do
            # https://api.rubyonrails.org/v5.1/classes/ActiveRecord/Validations/ClassMethods.html#method-i-validates_uniqueness_of-label-Concurrency+and+integrity
            #
            # We fake this scenario by skipping Rails validations.
            before do
              allow_any_instance_of(Kudo).to receive(:save).and_call_original
              allow_any_instance_of(Kudo).to receive(:save).with(no_args) do |kudo|
                kudo.save(validate: false)
              end
            end

            it "redirects to referer with an error" do
              post :create, params: { kudo: { commentable_id: work.id, commentable_type: "Work" } }
              it_redirects_to_with_kudos_error(referer, "You have already left kudos here. :)")
            end

            context "with format: :js" do
              it "returns an error in JSON format" do
                post :create, params: { kudo: { commentable_id: work.id, commentable_type: "Work" }, format: :js }
                expect(JSON.parse(response.body)["error_message"]).to eq("You have already left kudos here. :)")
              end
            end
          end
        end
      end

      context "when kudos giver is creator of work" do
        before { fake_login_known_user(work.users.first) }

        it "redirects to referer with an error" do
          post :create, params: { kudo: { commentable_id: work.id, commentable_type: "Work" } }
          it_redirects_to_with_kudos_error(referer, "You can't leave kudos on your own work.")
        end

        it "does not save kudos" do
          post :create, params: { kudo: { commentable_id: work.id, commentable_type: "Work" } }
          expect(assigns(:kudo).new_record?).to be_truthy
        end

        context "with format: :js" do
          it "returns an error in JSON format" do
            post :create, params: { kudo: { commentable_id: work.id, commentable_type: "Work" }, format: :js }
            expect(JSON.parse(response.body)["error_message"]).to eq("You can't leave kudos on your own work.")
          end
        end
      end
    end

    context "when work does not exist" do
      it "redirects to referer with an error" do
        referer = root_path
        request.headers["HTTP_REFERER"] = referer
        post :create, params: { kudo: { commentable_id: "333", commentable_type: "Work" } }
        it_redirects_to_with_kudos_error(referer, "What did you want to leave kudos on?")
      end

      context "with format: :js" do
        it "returns an error in JSON format" do
          post :create, params: { kudo: { commentable_id: "333", commentable_type: "Work" }, format: :js }
          expect(JSON.parse(response.body)["error_message"]).to eq("What did you want to leave kudos on?")
        end
      end
    end

    context "when work is restricted" do
      let(:work) { create(:work, restricted: true) }

      it "redirects to referer with an error" do
        referer = work_path(work)
        request.headers["HTTP_REFERER"] = referer
        post :create, params: { kudo: { commentable_id: work.id, commentable_type: "Work" } }
        it_redirects_to_with_kudos_error(referer, "You can't leave guest kudos on a restricted work.")
      end

      context "with format: :js" do
        it "returns an error in JSON format" do
          post :create, params: { kudo: { commentable_id: work.id, commentable_type: "Work" }, format: :js }
          expect(JSON.parse(response.body)["error_message"]).to eq("You can't leave guest kudos on a restricted work.")
        end
      end
    end

    context "when kudos giver is suspended" do
      let(:work) { create(:work, restricted: true) }
      let(:suspended_user) { create(:user, suspended: true, suspended_until: 4.days.from_now) }

      before { fake_login_known_user(suspended_user) }

      it "errors and redirects to user page" do
        post :create, params: { kudo: { commentable_id: work.id, commentable_type: "Work" } }
        it_redirects_to_simple(user_path(suspended_user))
        expect(flash[:error]).to include("Your account has been suspended")
      end

      context "with format: :js" do
        it "returns an error in JSON format" do
          post :create, params: { kudo: { commentable_id: work.id, commentable_type: "Work" }, format: :js }
          expect(JSON.parse(response.body)["error_message"]).to eq("You cannot leave kudos while your account is suspended.")
        end
      end
    end

    context "when kudos giver is banned" do
      let(:work) { create(:work, restricted: true) }
      let(:banned_user) { create(:user, banned: true) }

      before { fake_login_known_user(banned_user) }

      it "errors and redirects to user page" do
        post :create, params: { kudo: { commentable_id: work.id, commentable_type: "Work" } }
        it_redirects_to_simple(user_path(banned_user))
        expect(flash[:error]).to include("Your account has been banned.")
      end

      context "with format: :js" do
        it "returns an error in JSON format" do
          post :create, params: { kudo: { commentable_id: work.id, commentable_type: "Work" }, format: :js }
          expect(JSON.parse(response.body)["error_message"]).to eq("You cannot leave kudos while your account is banned.")
        end
      end
    end
  end
end
