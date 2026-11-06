package main

import (
	"fmt"
	"net/http"
	"net/http/httptest"
	"strings"
	"testing"
)

const testToken = "test-internal-token-with-at-least-32-characters"
const validRequest = "{\"limit\":1,\"candidates\":[{\"id\":\"post-1\",\"authorId\":\"author-1\",\"visible\":true,\"createdAt\":\"2026-11-06T12:00:00Z\"}]}"

func sendRequest(handler http.Handler, body string, token bool) *httptest.ResponseRecorder {
	req := httptest.NewRequest(http.MethodPost, "/v1/feed/rank", strings.NewReader(body))
	req.Header.Set("Content-Type", "application/json")
	if token {
		req.Header.Set("Authorization", "Bearer "+testToken)
	}
	response := httptest.NewRecorder()
	handler.ServeHTTP(response, req)
	return response
}

func TestRankRequiresInternalBearerToken(t *testing.T) {
	if got := sendRequest(newHandler(testToken), validRequest, false).Code; got != http.StatusUnauthorized {
		t.Fatalf("expected 401, got %d", got)
	}
}
func TestRankAcceptsValidRequestAndSetsSecurityHeaders(t *testing.T) {
	response := sendRequest(newHandler(testToken), validRequest, true)
	if response.Code != http.StatusOK {
		t.Fatalf("expected 200, got %d: %s", response.Code, response.Body.String())
	}
	if response.Header().Get("Cache-Control") != "no-store" || response.Header().Get("X-Content-Type-Options") != "nosniff" {
		t.Fatalf("security headers missing: %#v", response.Header())
	}
}
func TestRankRejectsWrongContentType(t *testing.T) {
	req := httptest.NewRequest(http.MethodPost, "/v1/feed/rank", strings.NewReader(validRequest))
	req.Header.Set("Authorization", "Bearer "+testToken)
	req.Header.Set("Content-Type", "text/plain")
	response := httptest.NewRecorder()
	newHandler(testToken).ServeHTTP(response, req)
	if response.Code != http.StatusUnsupportedMediaType {
		t.Fatalf("expected 415, got %d", response.Code)
	}
}
func TestRankRejectsUnknownAndTrailingJSON(t *testing.T) {
	cases := map[string]string{
		"unknown field":  strings.TrimSuffix(validRequest, "}") + ",\"unexpected\":true}",
		"trailing value": validRequest + " {}",
	}
	for name, body := range cases {
		t.Run(name, func(t *testing.T) {
			response := sendRequest(newHandler(testToken), body, true)
			if response.Code != http.StatusBadRequest {
				t.Fatalf("expected 400, got %d: %s", response.Code, response.Body.String())
			}
		})
	}
}
func TestRankRejectsOutOfRangeLimitsAndCandidateCounts(t *testing.T) {
	cases := map[string]string{
		"zero limit":  "{\"limit\":0,\"candidates\":[{\"id\":\"p\",\"authorId\":\"a\",\"visible\":true}]}",
		"empty batch": "{\"limit\":1,\"candidates\":[]}",
		"too many":    fmt.Sprintf("{\"limit\":1,\"candidates\":[%s]}", strings.TrimSuffix(strings.Repeat("{\"id\":\"p\",\"authorId\":\"a\",\"visible\":true},", 201), ",")),
	}
	for name, body := range cases {
		t.Run(name, func(t *testing.T) {
			response := sendRequest(newHandler(testToken), body, true)
			if response.Code != http.StatusBadRequest {
				t.Fatalf("expected 400, got %d", response.Code)
			}
		})
	}
}
func TestRankRejectsOversizedBody(t *testing.T) {
	body := "{\"limit\":1,\"candidates\":[{\"id\":\"p\",\"authorId\":\"a\",\"visible\":true}],\"padding\":\"" + strings.Repeat("x", maxRequestBody) + "\"}"
	req := httptest.NewRequest(http.MethodPost, "/v1/feed/rank", strings.NewReader(body))
	req.Header.Set("Authorization", "Bearer "+testToken)
	req.Header.Set("Content-Type", "application/json")
	response := httptest.NewRecorder()
	newHandler(testToken).ServeHTTP(response, req)
	if response.Code != http.StatusRequestEntityTooLarge {
		t.Fatalf("expected 413, got %d", response.Code)
	}
}
