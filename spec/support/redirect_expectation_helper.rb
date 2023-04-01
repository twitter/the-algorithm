# frozen_string_literal: true

module RedirectExpectationHelper
  def it_redirects_to_with_notice(path, notice)
    it_redirects_to_simple(path)
    expect(flash[:notice]).to eq notice
    expect(flash[:error]).to be_blank
    expect(flash[:caution]).to be_blank
  end

  def it_redirects_to_with_caution(path, caution)
    it_redirects_to_simple(path)
    expect(flash[:caution]).to eq caution
    expect(flash[:notice]).to be_blank
    expect(flash[:error]).to be_blank
  end

  def it_redirects_to_with_caution_and_notice(path, caution, notice)
    it_redirects_to_simple(path)
    expect(flash[:caution]).to eq caution
    expect(flash[:notice]).to eq notice
    expect(flash[:error]).to be_blank
  end

  def it_redirects_to_with_error(path, error)
    it_redirects_to_simple(path)
    expect(flash[:error]).to eq error
    expect(flash[:notice]).to be_blank
    expect(flash[:caution]).to be_blank
  end

  def it_redirects_to_with_error_and_notice(path, error, notice)
    it_redirects_to_simple(path)
    expect(flash[:error]).to eq error
    expect(flash[:notice]).to eq notice
    expect(flash[:caution]).to be_blank
  end

  def it_redirects_to_with_comment_notice(path, notice)
    it_redirects_to_simple(path)
    expect(flash[:comment_notice]).to eq notice
    expect(flash[:comment_error]).to be_blank
  end

  def it_redirects_to_with_kudos_notice(path, notice)
    it_redirects_to_simple(path)
    expect(flash[:kudos_notice]).to eq notice
    expect(flash[:kudos_error]).to be_blank
  end

  def it_redirects_to_with_comment_error(path, error)
    it_redirects_to_simple(path)
    expect(flash[:comment_error]).to eq error
    expect(flash[:comment_notice]).to be_blank
  end

  def it_redirects_to_with_kudos_error(path, error)
    it_redirects_to_simple(path)
    expect(flash[:kudos_error]).to eq error
    expect(flash[:kudos_notice]).to be_blank
  end

  def it_redirects_to_simple(path)
    expect(response).to have_http_status :redirect
    expect(response).to redirect_to path
  end

  def it_redirects_to(path)
    it_redirects_to_simple(path)
    expect(flash).to be_empty
  end
end
