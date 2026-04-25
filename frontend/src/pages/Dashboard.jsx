import React from "react";

const Dashboard = () => {
  const username = localStorage.getItem("username");

  return (
    <div className="container mt-5 text-white">

      <h2>Welcome, {username} 👋</h2>

      <div className="mt-4">
        <button className="btn btn-primary">+ Post a Problem</button>
      </div>

      <div className="mt-5">
        <h4>Recent Problems</h4>

        <div className="card bg-dark text-white p-3 mt-3">
          <h5>Campus Parking Issue</h5>
          <p>Need a smart solution for parking management...</p>
        </div>

      </div>

    </div>
  );
};

export default Dashboard;