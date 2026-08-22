import Navbar from "../components/Navbar";
import UrlShortener from "../components/UrlShortener";

const features = [
  ["⚡", "Instant shortening", "Generate a unique six-character link and share it immediately."],
  ["📊", "Click analytics", "Track the total number of successful redirects for every short URL."],
  ["🔗", "Simple sharing", "Copy your generated link and open it directly from the result card."]
];

export default function Home() {
  return (
    <div className="site-shell">
      <div className="ambient ambient-purple" />
      <div className="ambient ambient-blue" />

      <Navbar />

      <main>
        <section className="hero" id="shorten">
          <div className="status-pill"><span /> Fast · Simple · Trackable</div>
          <h1>Fire your links<br /><em>faster.</em></h1>
          <p className="hero-copy">
            Turn long, messy URLs into clean, powerful short links.
            Share them anywhere and track every click.
          </p>

          <UrlShortener />

          <div className="trust-row">
            <span>⚡ Instant links</span>
            <span>📊 Click tracking</span>
            <span>🔒 Reliable redirects</span>
          </div>
        </section>

        <section className="section" id="features">
          <div className="section-heading">
            <span className="eyebrow">POWERFUL BY DESIGN</span>
            <h2>Everything you need for your links.</h2>
          </div>

          <div className="feature-grid">
            {features.map(([icon, title, text]) => (
              <article className="feature-card" key={title}>
                <div className="feature-icon">{icon}</div>
                <h3>{title}</h3>
                <p>{text}</p>
              </article>
            ))}
          </div>
        </section>

        <section className="section how-section" id="how-it-works">
          <div>
            <span className="eyebrow">HOW IT WORKS</span>
            <h2>Three steps.<br /><em>One powerful link.</em></h2>
          </div>

          <div className="steps">
            {[
              ["01", "Paste", "Enter the long URL you want to shorten."],
              ["02", "Fire", "Spring Boot generates and stores a unique six-character code."],
              ["03", "Track", "Every successful redirect increments clicks and records analytics."]
            ].map(([number, title, text]) => (
              <div className="step" key={number}>
                <span className="step-number">{number}</span>
                <div>
                  <h3>{title}</h3>
                  <p>{text}</p>
                </div>
              </div>
            ))}
          </div>
        </section>

        <section className="analytics-preview" id="analytics">
          <div>
            <span className="eyebrow">ANALYTICS</span>
            <h2>Know what happens<br /><em>after the click.</em></h2>
            <p>
              URL Shotgun records click counts, IP address, user agent,
              referrer, and timestamp on every successful redirect.
            </p>
            <a className="outline-button" href="/analytics/demo">Open analytics</a>
          </div>

          <div className="preview-card">
            <div className="preview-top">
              <div>
                <span>Total clicks</span>
                <strong>—</strong>
              </div>
              <span className="live-tag">REAL DATA</span>
            </div>
            <div className="fake-chart">
              <i /><i /><i /><i /><i /><i /><i />
            </div>
            <p>Shorten a URL to start collecting click data.</p>
          </div>
        </section>
      </main>

      <footer>
        <div className="brand"><span className="brand-mark">↗</span><span>URL Shotgun</span></div>
        <span>Fire shorter. Reach farther.</span>
        <span>© 2026 URL Shotgun</span>
      </footer>
    </div>
  );
}
