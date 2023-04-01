require "spec_helper"

describe "Security headers" do
  it "includes the required headers" do
    get "/"
    headers = response.headers
    expect(headers["Content-Security-Policy"]).to eq("frame-ancestors 'self'")
    expect(headers["Referrer-Policy"]).to eq("strict-origin-when-cross-origin")
    expect(headers["X-Frame-Options"]).to eq("SAMEORIGIN")
    expect(headers["X-XSS-Protection"]).to eq("1; mode=block")
    expect(headers["X-Content-Type-Options"]).to eq("nosniff")
    expect(headers["X-Download-Options"]).to eq("noopen")
    expect(headers["X-Permitted-Cross-Domain-Policies"]).to eq("none")
  end
end
