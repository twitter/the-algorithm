# frozen_string_literal: true
require "spec_helper"

describe WorksController, work_search: true do
  include LoginMacros
  include RedirectExpectationHelper

  describe "before_action #clean_work_search_params" do
    let(:params) { {} }

    def call_with_params(params)
      controller.params = { work_search: params }
      controller.params[:work_search] = controller.clean_work_search_params
    end

    context "when no work search parameters are given" do
      it "redirects to the login screen when no user is logged in" do
        get :clean_work_search_params, params: params
        it_redirects_to_with_error(new_user_session_path,
                                   "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
      end
    end

    context "when the query contains countable search parameters" do
      it "escapes less and greater than in query" do
        [
          { params: "< 5 words", expected: "&lt; 5 words", message: "Should escape <" },
          { params: "> 5 words", expected: "&gt; 5 words", message: "Should escape >" },
        ].each do |settings|
          call_with_params(query: settings[:params])
          expect(controller.params[:work_search][:query])
            .to eq(settings[:expected]), settings[:message]
        end
      end

      it "converts 'word' to 'word_count'" do
        call_with_params(query: "word:6")
        expect(controller.params[:work_search][:word_count]).to eq("6")
      end

      it "converts 'words' to 'word_count'" do
        call_with_params(query: "words:7")
        expect(controller.params[:work_search][:word_count]).to eq("7")
      end

      it "converts 'hits' queries to 'hits'" do
        call_with_params(query: "hits:8")
        expect(controller.params[:work_search][:hits]).to eq("8")
      end

      it "converts other queries to (pluralized term)_count" do
        %w(kudo comment bookmark).each do |term|
          call_with_params(query: "#{term}:9")
          expect(controller.params[:work_search]["#{term.pluralize}_count"])
            .to eq("9"), "Search term '#{term}' should become #{term.pluralize}_count key"
        end
      end
    end

    context "when sort parameters are provided" do
      it "converts variations on 'sorted by: X' into :sort_column key" do
        [
          "sort by: words",
          "sorted by: words",
          "sorted: words",
          "sort: words",
          "sort by < words",
          "sort by > words",
          "sort by = words"
        ].each do |query|
          call_with_params(query: query)
          expect(controller.params[:work_search][:sort_column])
            .to eq("word_count"), "Sort command '#{query}' should be converted to :sort_column"
        end
      end

      it "converts variations on sort columns to column name" do
        [
          { query: "sort by: word count", expected: "word_count" },
          { query: "sort by: words", expected: "word_count" },
          { query: "sort by: word", expected: "word_count" },
          { query: "sort by: author", expected: "authors_to_sort_on" },
          { query: "sort by: title", expected: "title_to_sort_on" },
          { query: "sort by: date", expected: "created_at" },
          { query: "sort by: date posted", expected: "created_at" },
          { query: "sort by: hits", expected: "hits" },
          { query: "sort by: kudos", expected: "kudos_count" },
          { query: "sort by: comments", expected: "comments_count" },
          { query: "sort by: bookmarks", expected: "bookmarks_count" },
        ].each do |settings|
          call_with_params(query: settings[:query])
          actual = controller.params[:work_search][:sort_column]
          expect(actual)
            .to eq(settings[:expected]),
                "Query '#{settings[:query]}' should be converted to :sort_column '#{settings[:expected]}' but is '#{actual}'"
        end
      end

      it "converts 'ascending' or '>' into :sort_direction key 'asc'" do
        [
          "sort > word_count",
          "sort: word_count ascending",
          "sort: hits ascending",
        ].each do |query|
          call_with_params(query: query)
          expect(controller.params[:work_search][:sort_direction]).to eq("asc")
        end
      end

      it "converts 'descending' or '<' into :sort_direction key 'desc'" do
        [
          "sort < word_count",
          "sort: word_count descending",
          "sort: hits descending",
        ].each do |query|
          call_with_params(query: query)
          expect(controller.params[:work_search][:sort_direction]).to eq("desc")
        end
      end

      # The rest of these are probably bugs
      it "returns no sort column if there is NO punctuation after 'sort by' clause" do
        call_with_params(query: "sort by word count")
        expect(controller.params[:work_search][:sort_column]).to be_nil
      end

      it "can't search by date updated" do
        [
          { query: "sort by: date updated", expected: "revised_at" },
        ].each do |settings|
          call_with_params(query: settings[:query])
          expect(controller.params[:work_search][:sort_column]).to eq("created_at") # should be revised_at
        end
      end

      it "can't sort ascending if more than one word follows the colon" do
        [
          "sort by: word count ascending",
        ].each do |query|
          call_with_params(query: query)
          expect(controller.params[:work_search][:sort_direction]).to be_nil
        end
      end
    end

    context "when the query contains categories" do
      it "surrounds categories in quotes" do
        [
          { query: "M/F sort by: comments", expected: "\"m/f\"" },
          { query: "f/f Scully/Reyes", expected: "\"f/f\" Scully/Reyes" },
        ].each do |settings|
          call_with_params(query: settings[:query])
          expect(controller.params[:work_search][:query]).to eq(settings[:expected])
        end
      end

      it "does not surround categories in quotes when it shouldn't" do
        query = "sam/frodo sort by: word"
        call_with_params(query: query)
        expect(controller.params[:work_search][:query]).to eq("sam/frodo")
      end
    end
  end

  describe "new" do
    it "doesn't return the form for anyone not logged in" do
      get :new
      it_redirects_to_with_error(new_user_session_path,
                                 "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
    end

    it "renders the form if logged in" do
      fake_login
      get :new
      expect(response).to render_template("new")
    end
  end

  describe "create" do
    let(:user) { create(:user) }

    before { fake_login_known_user(user) }

    it "doesn't allow a user to create a work in a series that they don't own" do
      @series = create(:series)
      work_attributes = attributes_for(:work).except(:posted)
      work_attributes[:series_attributes] = { id: @series.id }
      expect {
        post :create, params: { work: work_attributes }
      }.not_to change { @series.works.all.count }
      expect(response).to render_template :new
      expect(assigns[:work].errors.full_messages).to \
        include("You can't add a work to that series.")
    end

    it "doesn't allow a user to submit only a pseud that is not theirs" do
      user2 = create(:user)
      work_attributes = attributes_for(:work).except(:posted)
      work_attributes[:author_attributes] = { ids: [user2.pseuds.first.id] }
      expect {
        post :create, params: { work: work_attributes }
      }.to_not change(Work, :count)
      expect(response).to render_template("new")
      expect(assigns[:work].errors.full_messages).to \
        include "You're not allowed to use that pseud."
    end

    it "renders new if the work has invalid pseuds" do
      work_attributes = attributes_for(:work).except(:posted)
      work_attributes[:author_attributes] = { ids: user.pseud_ids,
                                              byline: "*impossible*" }
      post :create, params: { work: work_attributes }
      expect(response).to render_template("new")
      expect(assigns[:work].errors.full_messages).to \
        include "Invalid creator: Could not find a pseud *impossible*."
    end

    it "renders new if the work has ambiguous pseuds" do
      create(:pseud, name: "ambiguous")
      create(:pseud, name: "ambiguous")
      work_attributes = attributes_for(:work).except(:posted)
      work_attributes[:author_attributes] = { ids: user.pseud_ids,
                                              byline: "ambiguous" }
      post :create, params: { work: work_attributes }
      expect(response).to render_template("new")
      expect(assigns[:work].errors.full_messages).to \
        include "Invalid creator: The pseud ambiguous is ambiguous."
    end

    it "renders new if the work has noncanonical warnings" do
      work_attributes = attributes_for(:work).except(:posted, :archive_warning_string).merge(archive_warning_string: "Warning")
      post :create, params: { work: work_attributes }
      expect(response).to render_template("new")
      expect(assigns[:work].errors.full_messages).to \
        include /Only canonical warning tags are allowed./
    end

    it "renders new if the work has noncanonical rating" do
      work_attributes = attributes_for(:work).except(:posted, :rating_string).merge(rating_string: "Rating")
      post :create, params: { work: work_attributes }
      expect(response).to render_template("new")
      expect(assigns[:work].errors.full_messages).to \
        include /Only canonical rating tags are allowed./
    end

    it "renders new if the work has noncanonical category" do
      work_attributes = attributes_for(:work).except(:posted).merge(category_strings: ["Category"])
      post :create, params: { work: work_attributes }
      expect(response).to render_template("new")
      expect(assigns[:work].errors.full_messages).to \
        include /Only canonical category tags are allowed./
    end

    context "as a tag wrangler" do
      let(:user) { create(:tag_wrangler) }

      it "does not set wrangling activity when posting with a new fandom" do
        work_attributes = attributes_for(:work).except(:posted, :fandom_string).merge(fandom_string: "New Fandom")
        post :create, params: { work: work_attributes }
        expect(user.last_wrangling_activity).to be_nil
      end

      it "does not set wrangling activity when posting with an unsorted tag" do
        tag = create(:unsorted_tag)
        work_attributes = attributes_for(:work).except(:posted, :freeform_string).merge(freeform_string: tag.name)
        post :create, params: { work: work_attributes }
        expect(user.last_wrangling_activity).to be_nil
      end
    end
  end

  describe "show" do
    let(:work) { create(:work) }

    it "doesn't error when a work has no fandoms" do
      work_no_fandoms = build(:work, fandom_string: "")
      work_no_fandoms.save!(validate: false)
      fake_login

      get :show, params: { id: work_no_fandoms.id }

      expect(assigns(:page_title)).to include "No fandom specified"
    end
  end

  describe "share" do
    it "returns a 404 response for unrevealed works" do
      unrevealed_collection = create :unrevealed_collection
      unrevealed_work = create :work, collections: [unrevealed_collection]

      get :share, params: { id: unrevealed_work.id }, xhr: true
      expect(response.status).to eq(404)
    end

    it "redirects to referer with an error for non-ajax warnings requests" do
      work = create(:work)
      referer = work_path(work)
      request.headers["HTTP_REFERER"] = referer
      get :share, params: { id: work.id }
      it_redirects_to_with_error(referer, "Sorry, you need to have JavaScript enabled for this.")
    end
  end

  describe "index" do
    before do
      @fandom = create(:canonical_fandom)
      @work = create(:work, fandom_string: @fandom.name)
    end

    it "returns the work" do
      get :index
      expect(assigns(:works)).to include(@work)
    end

    it "sets the fandom when given a fandom id" do
      params = { fandom_id: @fandom.id }
      get :index, params: params
      expect(assigns(:fandom)).to eq(@fandom)
    end

    describe "without caching" do
      before do
        AdminSetting.first.update_attribute(:enable_test_caching, false)
      end

      it "returns the result with different works the second time" do
        get :index
        expect(assigns(:works)).to include(@work)
        work2 = create(:work)
        get :index
        expect(assigns(:works)).to include(work2)
      end
    end

    describe "with caching" do
      before do
        AdminSetting.first.update_attribute(:enable_test_caching, true)
      end

      context "with NO owner tag" do
        it "returns the same result the second time when a new work is created within the expiration time" do
          get :index
          expect(assigns(:works)).to include(@work)
          work2 = create(:work)
          run_all_indexing_jobs
          get :index
          expect(assigns(:works)).not_to include(work2)
        end
      end

      context "with a valid owner tag" do
        before do
          @fandom2 = create(:canonical_fandom)
          @work2 = create(:work, fandom_string: @fandom2.name)
          run_all_indexing_jobs
        end

        it "only gets works under that tag" do
          get :index, params: { tag_id: @fandom.name }
          expect(assigns(:works).items).to include(@work)
          expect(assigns(:works).items).not_to include(@work2)
        end

        it "shows different results on second page" do
          get :index, params: { tag_id: @fandom.name, page: 2 }
          expect(assigns(:works).items).not_to include(@work)
        end

        context "with restricted works" do
          before do
            @work2 = create(:work, fandom_string: @fandom.name, restricted: true)
            run_all_indexing_jobs
          end

          it "shows restricted works to guests" do
            get :index, params: { tag_id: @fandom.name }
            expect(assigns(:works).items).to include(@work)
            expect(assigns(:works).items).not_to include(@work2)
          end

        end

        context "when tag is a synonym" do
          let(:fandom_synonym) { create(:fandom, merger: @fandom) }

          it "redirects to the merger's work index" do
            params = { tag_id: fandom_synonym.name }
            get :index, params: params
            it_redirects_to tag_works_path(@fandom)
          end

          context "when collection is specified" do
            let(:collection) { create(:collection) }

            it "redirects to the merger's collection works index" do
              params = { tag_id: fandom_synonym.name, collection_id: collection.name }
              get :index, params: params
              it_redirects_to collection_tag_works_path(collection, @fandom)
            end
          end
        end
      end
    end

    context "with an invalid owner tag" do
      it "raises an error" do
        params = { tag_id: "nonexistent_tag" }
        expect { get :index, params: params }.to raise_error(
          ActiveRecord::RecordNotFound,
          "Couldn't find tag named 'nonexistent_tag'"
        )
      end
    end

    context "with an invalid owner user" do
      it "raises an error" do
        params = { user_id: "nonexistent_user" }
        expect { get :index, params: params }.to raise_error(
          ActiveRecord::RecordNotFound
        )
      end

      context "with an invalid pseud" do
        it "raises an error" do
          params = { user_id: "nonexistent_user", pseud_id: "nonexistent_pseud" }
          expect { get :index, params: params }.to raise_error(
            ActiveRecord::RecordNotFound
          )
        end
      end
    end

    context "with a valid owner user" do
      let(:user) { create(:user) }
      let!(:user_work) { create(:work, authors: [user.default_pseud]) }
      let(:pseud) { create(:pseud, user: user) }
      let!(:pseud_work) { create(:work, authors: [pseud]) }

      before { run_all_indexing_jobs }

      it "includes only works for that user" do
        params = { user_id: user.login }
        get :index, params: params
        expect(assigns(:works).items).to include(user_work, pseud_work)
        expect(assigns(:works).items).not_to include(@work)
      end

      context "with a valid pseud" do
        it "includes only works for that pseud" do
          params = { user_id: user.login, pseud_id: pseud.name }
          get :index, params: params
          expect(assigns(:works).items).to include(pseud_work)
          expect(assigns(:works).items).not_to include(user_work, @work)
        end
      end

      context "with an invalid pseud" do
        it "includes all of that user's works" do
          params = { user_id: user.login, pseud_id: "nonexistent_pseud" }
          get :index, params: params
          expect(assigns(:works).items).to include(user_work, pseud_work)
          expect(assigns(:works).items).not_to include(@work)
        end
      end
    end
  end

  describe "update" do
    let(:update_user) { create(:user) }
    let(:update_work) {
      work = create(:work, authors: [update_user.default_pseud])
      create(:chapter, work: work)
      work
    }

    before do
      fake_login_known_user(update_user)
    end

    it "doesn't allow the user to add a series that they don't own" do
      @series = create(:series)
      attrs = { series_attributes: { id: @series.id } }
      expect {
        put :update, params: { id: update_work.id, work: attrs }
      }.not_to change { @series.works.all.count }
      expect(response).to render_template :edit
      expect(assigns[:work].errors.full_messages).to \
        include("You can't add a work to that series.")
    end

    it "redirects to the edit page if the work could not be saved" do
      allow_any_instance_of(Work).to receive(:save).and_return(false)
      update_work.fandom_string = "Testing"
      attrs = { title: "New Work Title" }
      put :update, params: { id: update_work.id, work: attrs }
      expect(response).to render_template :edit
      allow_any_instance_of(Work).to receive(:save).and_call_original
    end

    it "updates the editor's pseuds for all chapters" do
      new_pseud = create(:pseud, user: update_user)
      put :update, params: { id: update_work.id, work: { author_attributes: { ids: [new_pseud.id] } } }
      expect(update_work.pseuds.reload).to contain_exactly(new_pseud)
      update_work.chapters.reload.each do |c|
        expect(c.pseuds.reload).to contain_exactly(new_pseud)
      end
    end

    it "allows the user to invite co-creators" do
      co_creator = create(:user)
      co_creator.preference.update(allow_cocreator: true)
      put :update, params: { id: update_work.id, work: { author_attributes: { byline: co_creator.login } } }
      expect(update_work.pseuds.reload).not_to include(co_creator.default_pseud)
      expect(update_work.user_has_creator_invite?(co_creator)).to be_truthy
    end

    it "prevents inviting users who have disallowed co-creators" do
      no_co_creator = create(:user)
      no_co_creator.preference.update(allow_cocreator: false)
      put :update, params: { id: update_work.id, work: { author_attributes: { byline: no_co_creator.login } } }
      expect(response).to render_template :edit
      expect(assigns[:work].errors.full_messages).to \
        include "Invalid creator: #{no_co_creator.login} does not allow others to invite them to be a co-creator."
      expect(update_work.pseuds.reload).not_to include(no_co_creator.default_pseud)
      expect(update_work.user_has_creator_invite?(no_co_creator)).to be_falsey
    end

    context "when the work has broken dates" do
      let(:update_work) { create(:work, authors: [update_user.default_pseud]) }
      let(:update_chapter) { update_work.first_chapter }

      let(:attributes) do
        {
          backdate: "1",
          chapter_attributes: {
            published_at: "2021-09-01"
          }
        }
      end

      before do
        # Work where chapter published_at did not override revised_at, times
        # taken from AO3-5392
        update_work.update_column(:revised_at, Time.new(2018, 4, 22, 23, 51, 42, "+04:00"))
        update_chapter.update_column(:published_at, Date.new(2015, 7, 23))
      end

      it "can be backdated" do
        put :update, params: { id: update_work.id, work: attributes }

        expect(update_chapter.reload.published_at).to eq(Date.new(2021, 9, 1))
        expect(update_work.reload.revised_at).to eq(Time.utc(2021, 9, 1, 12)) # noon UTC
      end
    end

    # If the time zone in config/application.rb is changed to something other
    # than the default (UTC), these tests will need adjusting:
    context "when redating to the present" do
      let!(:update_work) do
        # November 30, 2 PM UTC -- no time zone oddities here
        travel_to(Time.utc(2021, 11, 30, 14)) do
          create(:work, authors: [update_user.default_pseud])
        end
      end

      let(:attributes) do
        {
          backdate: "1",
          chapter_attributes: {
            published_at: "2021-12-05"
          }
        }
      end

      before do
        travel_to(redate_time)

        # Simulate the system time being UTC:
        allow(Time).to receive(:now).and_return(redate_time)
        allow(DateTime).to receive(:now).and_return(redate_time)
        allow(Date).to receive(:today).and_return(redate_time.to_date)

        put :update, params: { id: update_work.id, work: attributes }
      end

      context "before midnight UTC and after midnight Samara" do
        # December 5, 3 AM Europe/Samara (UTC+04:00) -- still December 4 in UTC
        let(:redate_time) { Time.new(2021, 12, 5, 3, 0, 0, "+04:00") }

        it "prevents setting the publication date to the future" do
          expect(response).to render_template :edit
          expect(assigns[:work].errors.full_messages).to \
            include("Publication date can't be in the future.")
        end
      end

      context "before noon UTC" do
        # December 5, 6 AM Europe/Samara -- before noon, but after midnight in both time zones
        let(:redate_time) { Time.new(2021, 12, 5, 6, 0, 0, "+04:00") }

        it "doesn't set revised_at to the future" do
          update_work.reload
          expect(update_work.revised_at).to be <= Time.current
        end
      end
    end
  end

  describe "collected" do
    let(:collection) { create(:collection) }
    let(:collected_user) { create(:user) }

    it "returns not found error if user does not exist" do
      expect do
        get :collected, params: { user_id: "dummyuser" }
      end.to raise_error(ActiveRecord::RecordNotFound)
    end

    it "returns not found error if no user is set" do
      expect do
        get :collected
      end.to raise_error(ActiveRecord::RecordNotFound)
    end

    context "with anonymous works" do
      let(:anonymous_collection) { create(:anonymous_collection) }

      let!(:work) do
        create(:work,
               authors: [collected_user.default_pseud],
               collection_names: collection.name)
      end

      let!(:anonymous_work) do
        create(:work,
               authors: [collected_user.default_pseud],
               collection_names: anonymous_collection.name)
      end

      before { run_all_indexing_jobs }

      it "does not return anonymous works in collections for guests" do
        get :collected, params: { user_id: collected_user.login }
        expect(assigns(:works)).to include(work)
        expect(assigns(:works)).not_to include(anonymous_work)
      end

      it "does not return anonymous works in collections for logged-in users" do
        fake_login
        get :collected, params: { user_id: collected_user.login }
        expect(assigns(:works)).to include(work)
        expect(assigns(:works)).not_to include(anonymous_work)
      end

      it "returns anonymous works in collections for the author" do
        fake_login_known_user(collected_user)
        get :collected, params: { user_id: collected_user.login }
        expect(assigns(:works)).to include(work, anonymous_work)
      end
    end

    context "with restricted works" do
      let(:collected_fandom) { create(:canonical_fandom) }
      let(:collected_fandom_2) { create(:canonical_fandom) }

      let!(:unrestricted_work) do
        create(:work,
               authors: [collected_user.default_pseud],
               fandom_string: collected_fandom.name)
      end

      let!(:unrestricted_work_in_collection) do
        create(:work,
               authors: [collected_user.default_pseud],
               collection_names: collection.name,
               fandom_string: collected_fandom.name)
      end

      let!(:unrestricted_work_2_in_collection) do
        create(:work,
               authors: [collected_user.default_pseud],
               collection_names: collection.name,
               fandom_string: collected_fandom_2.name)
      end

      let!(:restricted_work_in_collection) do
        create(:work,
               restricted: true,
               authors: [collected_user.default_pseud],
               collection_names: collection.name,
               fandom_string: collected_fandom.name)
      end

      before { run_all_indexing_jobs }

      context "as a guest" do
        it "renders the collected form" do
          get :collected, params: { user_id: collected_user.login }
          expect(response).to render_template("collected")
        end

        it "returns ONLY unrestricted works in collections" do
          get :collected, params: { user_id: collected_user.login }
          expect(assigns(:works)).to include(unrestricted_work_in_collection, unrestricted_work_2_in_collection)
          expect(assigns(:works)).not_to include(unrestricted_work, restricted_work_in_collection)
        end

        it "returns filtered works when search parameters are provided" do
          get :collected, params: { user_id: collected_user.login, work_search: { query: "fandom_ids:#{collected_fandom_2.id}" }}
          expect(assigns(:works)).to include(unrestricted_work_2_in_collection)
          expect(assigns(:works)).not_to include(unrestricted_work_in_collection)
        end
      end

      context "with a logged-in user" do
        before { fake_login }

        it "returns ONLY works in collections" do
          get :collected, params: { user_id: collected_user.login }
          expect(assigns(:works)).to include(unrestricted_work_in_collection, restricted_work_in_collection)
          expect(assigns(:works)).not_to include(unrestricted_work)
        end
      end
    end

    context "with unrevealed works" do
      let(:unrevealed_collection) { create(:unrevealed_collection) }

      let!(:work) do
        create(:work,
               authors: [collected_user.default_pseud],
               collection_names: collection.name)
      end

      let!(:unrevealed_work) do
        create(:work,
               authors: [collected_user.default_pseud],
               collection_names: unrevealed_collection.name)
      end

      before { run_all_indexing_jobs }

      it "doesn't return unrevealed works in collections for guests" do
        get :collected, params: { user_id: collected_user.login }
        expect(assigns(:works)).to include(work)
        expect(assigns(:works)).not_to include(unrevealed_work)
      end

      it "doesn't return unrevealed works in collections for logged-in users" do
        fake_login
        get :collected, params: { user_id: collected_user.login }
        expect(assigns(:works)).to include(work)
        expect(assigns(:works)).not_to include(unrevealed_work)
      end

      it "returns unrevealed works in collections for the author" do
        fake_login_known_user(collected_user)
        get :collected, params: { user_id: collected_user.login }
        expect(assigns(:works)).to include(work, unrevealed_work)
      end
    end
  end

  describe "destroy" do
    let(:work) { create(:work) }
    let(:work_title) { work.title }

    context "when a work has consecutive deleted comments in a thread" do
      before do
        thread_depth = 4
        chapter = work.first_chapter

        commentable = chapter
        comments = []
        thread_depth.times do
          commentable = create(:comment, commentable: commentable, parent: chapter)
          comments << commentable
        end

        # Delete all but the last comment in the thread.
        # We should have (thread_depth - 1) consecutive deleted comment placeholders.
        comments.reverse.drop(1).each(&:destroy_or_mark_deleted)

        fake_login_known_user(work.users.first)
      end

      it "deletes the work and redirects to the user's works with a notice" do
        delete :destroy, params: { id: work.id }

        it_redirects_to_with_notice(user_works_path(controller.current_user), "Your work #{work_title} was deleted.")
        expect { work.reload }.to raise_exception(ActiveRecord::RecordNotFound)
        expect(Comment.count).to eq(0)
      end
    end
  end
end
