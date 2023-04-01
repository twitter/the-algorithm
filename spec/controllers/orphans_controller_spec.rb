require "spec_helper"

describe OrphansController do
  include LoginMacros
  include RedirectExpectationHelper

  # Make sure that we have an orphan account:
  before { create(:user, login: "orphan_account") }

  let!(:user) { create(:user) }

  let!(:pseud) { create(:pseud, user: user) }
  let!(:work) { create(:work, authors: [pseud]) }
  let(:second_work) { create(:work, authors: user.pseuds) }
  let(:series) { create(:series, works: [work], authors: [pseud]) }

  let(:other_user) { create(:user) }
  let(:other_work) { create(:work, authors: [other_user.default_pseud]) }

  describe "GET #new" do
    render_views

    context "when logged in as the owner" do
      before { fake_login_known_user(user.reload) }

      it "shows the form for orphaning a work" do
        get :new, params: { work_id: work }
        expect(response).to render_template(partial: "orphans/_orphan_work")
      end

      it "shows the form for orphaning multiple works" do
        get :new, params: { work_ids: [work, second_work] }
        expect(response).to render_template(partial: "orphans/_orphan_work")
      end

      it "shows the form for orphaning a series" do
        get :new, params: { series_id: series }
        expect(response).to render_template(partial: "orphans/_orphan_series")
      end

      it "shows the form for orphaning a pseud" do
        get :new, params: { pseud_id: pseud.id }
        expect(response).to render_template(partial: "orphans/_orphan_pseud")
      end

      it "shows the form for orphaning all your works" do
        get :new, params: {}
        expect(response).to render_template(partial: "orphans/_orphan_user")
      end
    end

    context "when logged in as another user" do
      before { fake_login_known_user(other_user.reload) }

      it "orphaning someone else's work shows an error and redirects" do
        get :new, params: { work_id: work }
        it_redirects_to_with_error(root_path, "You don't have permission to orphan that!")
      end

      it "orphaning multiple works by someone else shows an error and redirects" do
        get :new, params: { work_ids: [work, second_work] }
        it_redirects_to_with_error(root_path, "You don't have permission to orphan that!")
      end

      it "orphaning a mix of owned and un-owned works shows an error and redirects" do
        get :new, params: { work_ids: [work, other_work] }
        it_redirects_to_with_error(root_path, "You don't have permission to orphan that!")
      end

      it "orphaning someone else's series shows an error and redirects" do
        get :new, params: { series_id: series }
        it_redirects_to_with_error(root_path, "You don't have permission to orphan that!")
      end

      it "orphaning someone else's pseud shows an error and redirects" do
        get :new, params: { pseud_id: pseud.id }
        it_redirects_to_with_error(root_path, "You don't have permission to orphan that!")
      end
    end
  end

  describe "POST #create" do
    context "when logged in as the owner" do
      before { fake_login_known_user(user.reload) }

      it "successfully orphans a single work and redirects" do
        post :create, params: { work_ids: [work], use_default: "true" }
        expect(work.reload.users).not_to include(user)
        it_redirects_to_with_notice(user_path(user), "Orphaning was successful.")

        expect(work.original_creators.map(&:user_id)).to contain_exactly(user.id)
      end

      it "successfully orphans multiple works and redirects" do
        post :create, params: { work_ids: [work, second_work], use_default: "true" }
        expect(work.reload.users).not_to include(user)
        expect(second_work.reload.users).not_to include(user)
        it_redirects_to_with_notice(user_path(user), "Orphaning was successful.")

        expect(work.original_creators.map(&:user_id)).to contain_exactly(user.id)
        expect(second_work.original_creators.map(&:user_id)).to contain_exactly(user.id)
      end

      context "when a work has multiple pseuds for the same user" do
        let(:second_pseud) { create(:pseud, user: user) }
        let(:work) { create(:work, authors: [pseud, second_pseud]) }

        it "only saves the original creator once" do
          post :create, params: { work_ids: [work], use_default: "true" }
          expect(work.reload.users).not_to include(user)

          expect(work.original_creators.map(&:user_id)).to contain_exactly(user.id)
        end
      end

      it "successfully orphans a series and redirects" do
        post :create, params: { series_id: series, use_default: "true" }
        expect(series.reload.users).not_to include(user)
        it_redirects_to_with_notice(user_path(user), "Orphaning was successful.")
      end

      it "successfully orphans a pseud and redirects" do
        post :create, params: { work_ids: pseud.works.pluck(:id),
                                pseud_id: pseud.id, use_default: "true" }
        expect(work.reload.users).not_to include(user)
        it_redirects_to_with_notice(user_path(user), "Orphaning was successful.")
      end

      it "errors and redirects if you don't specify any works or series" do
        post :create, params: { pseud_id: pseud.id, use_default: "true" }
        it_redirects_to_with_error(user_path(user), "What did you want to orphan?")
      end
    end

    context "when logged in as another user" do
      before { fake_login_known_user(other_user.reload) }

      it "orphaning someone else's work shows an error and redirects" do
        post :create, params: { work_ids: [work], use_default: "true" }
        expect(work.reload.users).to include(user)
        it_redirects_to_with_error(root_path, "You don't have permission to orphan that!")
      end

      it "orphaning multiple works by someone else shows an error and redirects" do
        post :create, params: { work_ids: [work, second_work], use_default: "true" }
        expect(work.reload.users).to include(user)
        expect(second_work.reload.users).to include(user)
        it_redirects_to_with_error(root_path, "You don't have permission to orphan that!")
      end

      it "orphaning a mix of owned and un-owned works shows an error and redirects" do
        post :create, params: { work_ids: [work, other_work], use_default: "true" }
        expect(work.reload.users).to include(user)
        expect(other_work.reload.users).to include(other_user)
        it_redirects_to_with_error(root_path, "You don't have permission to orphan that!")
      end

      it "orphaning someone else's series shows an error and redirects" do
        post :create, params: { series_id: series, use_default: "true" }
        expect(series.reload.users).to include(user)
        it_redirects_to_with_error(root_path, "You don't have permission to orphan that!")
      end

      it "orphaning your own work with someone else's pseud shows an error and redirects" do
        post :create, params: { work_ids: [other_work],
                                pseud_id: pseud.id,
                                use_default: "true" }
        expect(work.reload.users).to include(user)
        expect(other_work.reload.users).to include(other_user)
        it_redirects_to_with_error(root_path, "You don't have permission to orphan that!")
      end

      it "orphaning someone else's work with your own pseud shows an error and redirects" do
        post :create, params: { work_ids: [work],
                                pseud_id: other_user.default_pseud_id,
                                use_default: "true" }
        expect(work.reload.users).to include(user)
        expect(other_work.reload.users).to include(other_user)
        it_redirects_to_with_error(root_path, "You don't have permission to orphan that!")
      end

      it "orphaning someone else's work with someone else's pseud shows an error and redirects" do
        post :create, params: { work_ids: [work],
                                pseud_id: pseud.id,
                                use_default: "true" }
        expect(work.reload.users).to include(user)
        expect(other_work.reload.users).to include(other_user)
        it_redirects_to_with_error(root_path, "You don't have permission to orphan that!")
      end
    end
  end
end
