import React, { useState } from "react";
import API from "../api";
import { Link, useNavigate } from "react-router-dom";

const Register = () => {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    username: "",
    password: "",
  });

  const register = async () => {
    try {
      await API.post("/auth/register", form);
      alert("Registered successfully!");
      navigate("/login");
    } catch {
      alert("Error registering");
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100">

      <div className="glass-card" style={{ width: "350px" }}>
        <h3 className="text-center mb-4">Create Account</h3>

        <input
          type="text"
          className="form-control mb-3"
          placeholder="Username"
          onChange={(e) => setForm({ ...form, username: e.target.value })}
        />

        <input
          type="password"
          className="form-control mb-3"
          placeholder="Password"
          onChange={(e) => setForm({ ...form, password: e.target.value })}
        />

        <button onClick={register} className="btn btn-success w-100 btn-custom">
          Sign Up
        </button>

        <p className="text-center mt-3">
          Already have an account?{" "}
          <Link to="/login">Login</Link>
        </p>
      </div>
    </div>
  );
};

export default Register;