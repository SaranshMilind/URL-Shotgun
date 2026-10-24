import { Link } from "react-router-dom";

export default function Navbar() {
  return (
    <header className="navbar">
      <Link to="/" className="brand">
        <span className="brand-mark">↗</span>
        <span>URL Shotgun</span>
      </Link>

      <nav className="nav-links">
        <a href="/#features">Features</a>
        <a href="/#how-it-works">How it works</a>
        <Link to="/analytics/demo">Analytics</Link>
      </nav>

      <a className="nav-cta" href="/#shorten">Get Started</a>
    </header>
  );
}
