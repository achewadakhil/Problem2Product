import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />         {/* Home page */}
        <Route path="/login" element={<Login />} />   {/* Login */}
        <Route path="/register" element={<Register />} /> {/* Signup */}
      </Routes>
    </Router>
  );
}

export default App;