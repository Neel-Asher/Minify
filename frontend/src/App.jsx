import { useState } from "react";
import "./App.css";

function App() {
  const [url, setUrl] = useState("");
  const [result, setResult] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const [copied, setCopied] = useState(false);
  const [darkMode, setDarkMode] = useState(false);

  const [history, setHistory] = useState(() => {
    try {
      return JSON.parse(localStorage.getItem("history") || "[]");
    } catch {
      return [];
    }
  });

  const saveHistory = (item) => {
    setHistory((prev) => {
      const updated = [item, ...prev];
      localStorage.setItem("history", JSON.stringify(updated));
      return updated;
    });
  };

  const shortenUrl = async () => {
    if (!url.trim()) {
      setError("Please enter a URL");
      return;
    }

    setLoading(true);
    setError("");
    setResult(null);

    try {
      const response = await fetch("http://localhost:8080/shorten", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ url }),
      });

      if (!response.ok) {
        throw new Error("Failed to shorten URL");
      }

      const data = await response.json();

      setResult(data);
      saveHistory(data);
      setUrl("");
    } catch (err) {
      setError(err.message);

      setTimeout(() => {
        setError("");
      }, 3000);
    } finally {
      setLoading(false);
    }
  };

  const copyToClipboard = async () => {
    if (!result) return;

    const shortUrl = `http://localhost:8080/shorten/r/${result.shortCode}`;

    try {
      await navigator.clipboard.writeText(shortUrl);
      setCopied(true);

      setTimeout(() => {
        setCopied(false);
      }, 2000);
    } catch {
      setError("Failed to copy");
    }
  };

  return (
    <div className={darkMode ? "container dark" : "container"}>
      <div className={darkMode ? "card dark-card" : "card"}>
        <h2>Minify: URL Shortener</h2>

        <button className="theme-toggle" onClick={() => setDarkMode(!darkMode)}>
          {darkMode ? "☀ Light Mode" : "☾ Dark Mode"}
        </button>

        <input
          type="text"
          placeholder="Enter long URL..."
          value={url}
          onChange={(e) => setUrl(e.target.value)}
        />

        <button onClick={shortenUrl} disabled={loading}>
          {loading ? "Shortening..." : "Shorten URL"}
        </button>

        {error && <div className="error">{error}</div>}

        {result && (
          <div className="result">
            <p>
              <b>Short Code:</b> {result.shortCode}
            </p>

            <p>
              <b>Short URL:</b>{" "}
              <a
                href={`http://localhost:8080/shorten/r/${result.shortCode}`}
                target="_blank"
                rel="noreferrer"
              >
                http://localhost:8080/shorten/r/{result.shortCode}
              </a>
            </p>

            <button className="copy-btn" onClick={copyToClipboard}>
              {copied ? "Copied!" : "Copy Link"}
            </button>
          </div>
        )}

        {history.length > 0 && (
          <div className="result" style={{ marginTop: "20px" }}>
            <h3>Recent Links</h3>

            {history.map((item, index) => (
              <div key={index} style={{ marginBottom: "8px" }}>
                <a
                  href={`http://localhost:8080/shorten/r/${item.shortCode}`}
                  target="_blank"
                  rel="noreferrer"
                >
                  http://localhost:8080/shorten/r/{item.shortCode}
                </a>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default App;