require "spec_helper"

describe "n+1 queries in the WorksController" do
  include LoginMacros

  shared_examples "displaying multiple works efficiently" do
    context "when all works are cached", :n_plus_one do
      populate do |n|
        User.current_user = nil # prevent treating the logged-in user as the work creator
        WorkIndexer.prepare_for_testing
        create_list(:work, n, **work_attributes)
        run_all_indexing_jobs
        subject.call
      end

      it "performs a constant number of queries" do
        expect do
          subject.call
          expect(response.body.scan('<li id="work_').size).to eq(current_scale.to_i)
        end.to perform_constant_number_of_queries
      end
    end

    context "when no works are cached", :n_plus_one do
      populate do |n|
        User.current_user = nil # prevent treating the logged-in user as the work creator
        WorkIndexer.prepare_for_testing
        create_list(:work, n, **work_attributes)
        run_all_indexing_jobs
      end

      it "performs around 13 queries per work" do
        # TODO: Ideally, we'd like the uncached work listings to also have a
        # constant number of queries, instead of the linear number of queries
        # we're checking for here. But we also don't want to put too much
        # unnecessary load on the databases when we have a bunch of cache hits,
        # so this requires some care & tuning.
        expect do
          subject.call
          expect(response.body.scan('<li id="work_').size).to eq(current_scale.to_i)
        end.to perform_linear_number_of_queries(slope: 13).with_warming_up
      end
    end
  end

  describe "#index" do
    context "when viewing the works for a tag" do
      subject do
        proc do
          get tag_works_path(fandom)
        end
      end

      let!(:work_attributes) { { fandom_string: fandom.name } }
      let!(:fandom) { create(:canonical_fandom) }

      context "when logged in" do
        before { fake_login }

        it_behaves_like "displaying multiple works efficiently"
      end

      context "when logged out" do
        it_behaves_like "displaying multiple works efficiently"
      end
    end

    context "when viewing recent works" do
      subject do
        proc do
          get works_path
        end
      end

      let!(:work_attributes) { {} }

      context "when logged in" do
        before { fake_login }

        it_behaves_like "displaying multiple works efficiently"
      end

      context "when logged out" do
        it_behaves_like "displaying multiple works efficiently"
      end
    end
  end

  describe "#collected" do
    subject do
      proc do
        get collected_user_works_path(user)
      end
    end

    let!(:work_attributes) { { authors: [user.default_pseud], collections: [collection] } }
    let!(:user) { create(:user) }
    let!(:collection) { create(:collection) }

    context "when logged in" do
      before { fake_login }

      it_behaves_like "displaying multiple works efficiently"
    end

    context "when logged out" do
      it_behaves_like "displaying multiple works efficiently"
    end
  end

  describe "#drafts" do
    subject do
      proc do
        fake_login_known_user(user.reload)
        get drafts_user_works_path(user)
      end
    end

    let!(:work_attributes) { { authors: [user.default_pseud], posted: false } }
    let!(:user) { create(:user) }

    it_behaves_like "displaying multiple works efficiently"
  end

  describe "#search" do
    subject do
      proc do
        get search_works_path(work_search: { query: fandom.name })
      end
    end

    let!(:work_attributes) { { fandom_string: fandom.name } }
    let!(:fandom) { create(:canonical_fandom) }

    context "when logged in" do
      before { fake_login }

      it_behaves_like "displaying multiple works efficiently"
    end

    context "when logged out" do
      it_behaves_like "displaying multiple works efficiently"
    end
  end
end
