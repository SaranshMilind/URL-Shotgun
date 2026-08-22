import { useState } from "react";
import { createShortUrl } from "../services/api";

export default function UrlShortener() {
  const [url, setUrl] = useState("");
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [copied, setCopied] = useState(false);

  async function handleSubmit(event) {
    event.preventDefault();
    setError("");
    setResult(null);
    setCopied(false);

    if (!url.trim()) {
      setError("Please enter a URL.");
      return;
    }

    try {
      setLoading(true);
      const data = await createShortUrl(url.trim());
      const shortUrl = `${window.location.origin.replace("5173", "8080")}/${data.shortCode}`;
      setResult({ ...data, shortUrl });
    } catch (err) {
      const message = err.response?.data?.message;
      setError(message || "Could not connect to URL Shotgun. Make sure Spring Boot is running on port 8080.");
    } finally {
      setLoading(false);
    }
  }

  async function copyShortUrl() {
    if (!result?.shortUrl) return;
    await navigator.clipboard.writeText(result.shortUrl);
    setCopied(true);
    window.setTimeout(() => setCopied(false), 1800);
  }

  return (
    <>
      <form className="shortener" onSubmit={handleSubmit}>
        <div className="url-field">
          <span className="field-icon">↗</span>
          <input
            type="url"
            value={url}
            onChange={(event) => setUrl(event.target.value)}
            placeholder="Paste your long URL here..."
            aria-label="Long URL"
          />
        </div>
        <button className="primary-button" type="submit" disabled={loading}>
          {loading ? "Shortening..." : "Shorten URL"}
          {!loading && <span>→</span>}
        </button>
      </form>

      {error && <div className="error-box">{error}</div>}

      {result && (
        <div className="result-card">
          <div className="result-copy">
            <span className="eyebrow">YOUR SHORT URL</span>
            <a href={result.shortUrl} target="_blank" rel="noreferrer">
              {result.shortUrl}
            </a>
          </div>

          <div className="result-actions">
            <button className="secondary-button" onClick={copyShortUrl} type="button">
              {copied ? "Copied!" : "Copy"}
            </button>
            <a className="secondary-button link-button" href={result.shortUrl} target="_blank" rel="noreferrer">
              Open ↗
            </a>
            <a className="secondary-button link-button" href={`/analytics/${result.shortCode}`}>
              Analytics
            </a>
          </div>
        </div>
      )}
    </>
  );
}
