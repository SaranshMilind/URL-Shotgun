import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Analytics from "./pages/Analytics";
import "./App.css";

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/analytics/:shortCode" element={<Analytics />} />
      <Route path="*" element={<Home />} />
    </Routes>
  );
}
