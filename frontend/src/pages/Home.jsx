import React from "react";
import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div style={styles.container}>
      <h1 style={styles.title}>Problem2Product 🚀</h1>

      <p style={styles.subtitle}>
        Turn real-world problems into real solutions
      </p>

      <div style={styles.buttons}>
        <Link to="/login">
          <button style={styles.loginBtn}>Login</button>
        </Link>

        <Link to="/register">
          <button style={styles.signupBtn}>Sign Up</button>
        </Link>
      </div>
    </div>
  );
};

const styles = {
  container: {
    height: "100vh",
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
    background: "#0f172a",
    color: "white",
    textAlign: "center",
  },
  title: {
    fontSize: "40px",
    marginBottom: "10px",
  },
  subtitle: {
    fontSize: "18px",
    marginBottom: "30px",
    color: "#cbd5f5",
  },
  buttons: {
    display: "flex",
    gap: "15px",
  },
  loginBtn: {
    padding: "10px 20px",
    background: "#22c55e",
    border: "none",
    borderRadius: "5px",
    color: "white",
    cursor: "pointer",
  },
  signupBtn: {
    padding: "10px 20px",
    background: "#3b82f6",
    border: "none",
    borderRadius: "5px",
    color: "white",
    cursor: "pointer",
  },
};

export default Home;