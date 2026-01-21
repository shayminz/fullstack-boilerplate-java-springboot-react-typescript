import logo from "./logo.svg";
import "./App.css";

function App() {
  const runAction = async () => {
    console.log("runAction clicked");
    try {
      const response = await fetch("http://localhost:8080/ping", {
        headers: {
          Authorization: "HUMANZ",
        },
      });
      if (!response.ok) {
        throw new Error(`HTTP error ${response.status}`);
      }

      const data = await response.text();
      console.log("Ping response:", data);
    } catch (error) {
      console.error("Ping failed:", error);
    }
  };

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} onClick={runAction} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          onClick={runAction}
          target="_blank"
          rel="noopener noreferrer"
          style={{
            cursor: "pointer",
            fontSize: "16px",
          }}
        >
          Shay Btn
        </a>
      </header>
    </div>
  );
}

export default App;
