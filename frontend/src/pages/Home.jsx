import React from "react";
import { Link } from "react-router-dom";

const Home = () => {
  const username = localStorage.getItem("username");

  return (
    <div className="container-fluid text-center d-flex flex-column justify-content-center align-items-center vh-100">

      <h1 className="display-3 fw-bold">
        Problem2Product 🚀
      </h1>

      <p className="lead text-secondary mt-3">
        Turn real-world problems into powerful solutions
      </p>

      {username && (
        <h3 className="text-success mt-3">
          Welcome, {username} 👋
        </h3>
      )}

      <div className="mt-4">
        <Link to="/login">
          <button className="btn btn-success btn-lg me-3 btn-custom">
            Login
          </button>
        </Link>

        <Link to="/register">
          <button className="btn btn-primary btn-lg btn-custom">
            Sign Up
          </button>
        </Link>
      </div>
    </div>
  );
};

export default Home;