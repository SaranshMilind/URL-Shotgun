import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import Navbar from "../components/Navbar";
import { getAnalytics } from "../services/api";

export default function Analytics() {
  const { shortCode } = useParams();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    if (!shortCode || shortCode === "demo") {
      setLoading(false);
      return;
    }

    getAnalytics(shortCode)
      .then(setData)
      .catch((err) => {
        setError(err.response?.data?.message || "Analytics could not be loaded.");
      })
      .finally(() => setLoading(false));
  }, [shortCode]);

  return (
    <div className="site-shell analytics-page">
      <div className="ambient ambient-purple" />
      <Navbar />

      <main className="dashboard">
        <Link to="/" className="back-link">← Back to URL Shotgun</Link>
        <span className="eyebrow">LINK ANALYTICS</span>
        <h1>Analytics for <em>{shortCode}</em></h1>

        {loading && <div className="state-card">Loading analytics...</div>}

        {!loading && error && <div className="error-box">{error}</div>}

        {!loading && !error && shortCode === "demo" && (
          <div className="state-card">
            Create a short URL first, then open its Analytics link.
          </div>
        )}

        {!loading && !error && data && (
          <>
            <section className="metric-grid">
              <article className="metric-card">
                <span>Total clicks</span>
                <strong>{data.clickCount ?? 0}</strong>
              </article>
              <article className="metric-card">
                <span>Short code</span>
                <strong>{data.shortCode}</strong>
              </article>
              <article className="metric-card">
                <span>Status</span>
                <strong className="active">Active</strong>
              </article>
            </section>

            <section className="url-detail-card">
              <span className="eyebrow">ORIGINAL URL</span>
              <a href={data.originalUrl} target="_blank" rel="noreferrer">
                {data.originalUrl}
              </a>
              <p>
                Detailed click records are stored by the Spring Boot backend
                in <code>click_analytics</code>. A dedicated detailed analytics
                endpoint can be added later.
              </p>
            </section>
          </>
        )}
      </main>
    </div>
  );
}
