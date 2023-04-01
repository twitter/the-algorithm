require "spec_helper"

describe WranglingGuidelinesController do
  include HtmlCleaner
  include LoginMacros
  include RedirectExpectationHelper
  let(:admin) { create(:admin) }

  describe "GET #index" do
    let!(:guideline_1) { create(:wrangling_guideline, position: 9001) }
    let!(:guideline_2) { create(:wrangling_guideline, position: 2) }
    let!(:guideline_3) { create(:wrangling_guideline, position: 7) }

    it "renders" do
      get :index
      expect(response).to render_template("index")
      expect(assigns(:wrangling_guidelines)).to eq([guideline_2, guideline_3, guideline_1])
    end
  end

  describe "GET #show" do
    let(:guideline) { create(:wrangling_guideline) }

    it "renders" do
      get :show, params: { id: guideline.id }
      expect(response).to render_template("show")
      expect(assigns(:wrangling_guideline)).to eq(guideline)
    end
  end

  describe "GET #new" do
    it "blocks non-admins" do
      fake_logout
      get :new
      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")

      fake_login
      get :new
      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
    end

    context "when logged in as admin" do
      before { fake_login_admin(admin) }

      it "renders" do
        get :new
        expect(response).to render_template("new")
        expect(assigns(:wrangling_guideline)).to be_a_new(WranglingGuideline)
      end
    end
  end

  describe "GET #edit" do
    it "blocks non-admins" do
      fake_logout
      get :edit, params: { id: 1 }
      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")

      fake_login
      get :edit, params: { id: 1 }
      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
    end

    context "when logged in as admin" do
      let(:guideline) { create(:wrangling_guideline) }

      before { fake_login_admin(admin) }

      it "renders" do
        get :edit, params: { id: guideline.id }
        expect(response).to render_template("edit")
        expect(assigns(:wrangling_guideline)).to eq(guideline)
      end
    end
  end

  describe "GET #manage" do
    it "blocks non-admins" do
      fake_logout
      get :manage
      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")

      fake_login
      get :manage
      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
    end

    context "when logged in as admin" do
      let!(:guideline_1) { create(:wrangling_guideline, position: 9001) }
      let!(:guideline_2) { create(:wrangling_guideline, position: 2) }
      let!(:guideline_3) { create(:wrangling_guideline, position: 7) }

      before { fake_login_admin(admin) }

      it "renders" do
        get :manage
        expect(response).to render_template("manage")
        expect(assigns(:wrangling_guidelines)).to eq([guideline_2, guideline_3, guideline_1])
      end
    end
  end

  describe "POST #create" do
    it "blocks non-admins" do
      fake_logout
      post :create
      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")

      fake_login
      post :create
      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
    end

    context "when logged in as admin" do
      before { fake_login_admin(admin) }

      it "creates and redirects to new wrangling guideline" do
        title = "Wrangling 101"
        content = "JUST DO IT!"
        post :create, params: { wrangling_guideline: { title: title, content: content } }

        guideline = WranglingGuideline.find_by_title(title)
        expect(assigns(:wrangling_guideline)).to eq(guideline)
        expect(assigns(:wrangling_guideline).content).to eq(sanitize_value("content", content))
        it_redirects_to_with_notice(wrangling_guideline_path(guideline), "Wrangling Guideline was successfully created.")
      end

      it "renders new if create fails" do
        # Cannot save a content-free guideline
        post :create, params: { wrangling_guideline: { title: "Wrangling 101" } }
        expect(response).to render_template("new")
      end
    end
  end

  describe "PUT #update" do
    it "blocks non-admins" do
      fake_logout
      put :update, params: { id: 1 }
      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")

      fake_login
      put :update, params: { id: 1 }
      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
    end

    context "when logged in as admin" do
      let(:guideline) { create(:wrangling_guideline) }

      before { fake_login_admin(admin) }

      it "updates and redirects to updated wrangling guideline" do
        title = "Wrangling 101"
        expect(guideline.title).not_to eq(title)

        put :update, params: { id: guideline.id, wrangling_guideline: { title: title } }

        expect(assigns(:wrangling_guideline)).to eq(guideline)
        expect(assigns(:wrangling_guideline).title).to eq(title)
        it_redirects_to_with_notice(wrangling_guideline_path(guideline), "Wrangling Guideline was successfully updated.")
      end

      it "renders edit if update fails" do
        put :update, params: { id: guideline.id, wrangling_guideline: { title: nil } }
        expect(response).to render_template("edit")
      end
    end
  end

  describe "POST #update_positions" do
    it "blocks non-admins" do
      fake_logout
      post :update_positions
      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")

      fake_login
      post :update_positions
      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
    end

    context "when logged in as admin" do
      let!(:guideline_1) { create(:wrangling_guideline, position: 1) }
      let!(:guideline_2) { create(:wrangling_guideline, position: 2) }
      let!(:guideline_3) { create(:wrangling_guideline, position: 3) }

      before { fake_login_admin(admin) }

      it "updates positions and redirects to index" do
        expect(WranglingGuideline.order('position ASC')).to eq([guideline_1, guideline_2, guideline_3])
        post :update_positions, params: { wrangling_guidelines: [3, 2, 1] }

        expect(assigns(:wrangling_guidelines)).to eq(WranglingGuideline.order('position ASC'))
        expect(assigns(:wrangling_guidelines)).to eq([guideline_3, guideline_2, guideline_1])
        it_redirects_to_with_notice(wrangling_guidelines_path, "Wrangling Guidelines order was successfully updated.")
      end

      it "redirects to index given no params" do
        post :update_positions
        it_redirects_to(wrangling_guidelines_path)
      end
    end
  end

  describe "DELETE #destroy" do
    it "blocks non-admins" do
      fake_logout
      delete :destroy, params: { id: 1 }
      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")

      fake_login
      delete :destroy, params: { id: 1 }
      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
    end

    context "when logged in as admin" do
      let(:guideline) { create(:wrangling_guideline) }

      before { fake_login_admin(admin) }

      it "deletes and redirects to index" do
        delete :destroy, params: { id: guideline.id }
        expect(WranglingGuideline.find_by_id(guideline.id)).to be_nil
        it_redirects_to_with_notice(wrangling_guidelines_path, "Wrangling Guideline was successfully deleted.")
      end
    end
  end
end
