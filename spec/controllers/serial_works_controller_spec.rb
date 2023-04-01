require 'spec_helper'

describe SerialWorksController do
  include LoginMacros
  include RedirectExpectationHelper
  let(:user) { create(:user) }
  let(:series) { create(:series, authors: user.pseuds) }
  let!(:first_work) { create(:serial_work, series: series) }
  let!(:second_work) { create(:serial_work, series: series) }
  
  it "will fail if you're not the owner" do
    fake_login
    delete :destroy, params: { id: first_work.id }
    it_redirects_to_with_error(series_path(series), "Sorry, you don't have permission to access the page you were trying to reach.")
  end
  
  it "redirects to series when destroying one of many" do
    fake_login_known_user(user)
    delete :destroy, params: { id: first_work.id }
    # TODO: AO3-5006
    it_redirects_to(series_path(series))
  end
  
  it "redirects to user when destroying last one" do
    fake_login_known_user(user)
    delete :destroy, params: { id: first_work.id }
    delete :destroy, params: { id: second_work.id }
    # TODO: AO3-5006
    it_redirects_to(user_path(user))
  end
end
