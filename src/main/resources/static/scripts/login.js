// Handle the login functionality
function handleLogin(event) {
  event.preventDefault(); // Prevent the default form submission

  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;

  // For demonstration, using hardcoded credentials
  const validUsername = "admin";
  const validPassword = "admin123";

  if (username === validUsername && password === validPassword) {
    // Redirect to the admin dashboard or another page
    window.location.href = "admin-dashboard.html";  // Replace with actual page
  } else {
    // Show error message
    const errorMessage = document.getElementById("error-message");
    errorMessage.style.display = "block";
  }
}
