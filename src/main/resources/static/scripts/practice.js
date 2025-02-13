//function fetchPractice() {
//      let id = document.getElementById("practiceId").value;
//      if (!id) {
//          alert("Please enter a valid Practice ID");
//          return;
//      }
//
//      document.getElementById("loading").style.display = "block";
//      fetch(`http://localhost:8080/api/practices/${id}`)
//          .then(response => {
//              if (!response.ok) {
//                  throw new Error("Practice not found");
//              }
//              return response.json();
//          })
//          .then(data => {
//              document.getElementById("practiceDetails").innerHTML = `
//                  <p><strong>Name:</strong> ${data.name}</p>
//                  <p><strong>Address:</strong> ${data.address}</p>
//                  <p><strong>City:</strong> ${data.city}</p>
//                  <p><strong>State:</strong> ${data.state}</p>
//                  <p><strong>Website:</strong> <a href="${data.website}" target="_blank">${data.website}</a></p>
//              `;
//          })
//          .catch(error => {
//              document.getElementById("practiceDetails").innerHTML = `<p style="color: red;">${error.message}</p>`;
//          })
//          .finally(() => {
//              document.getElementById("loading").style.display = "none";
//          });
//  }
//

document.addEventListener("DOMContentLoaded", function () {
    // Extract URL parameters
    const urlParams = new URLSearchParams(window.location.search);
    const practiceId = urlParams.get("id"); // Retrieve the practice ID from URL

    console.log("Extracted Practice ID:", practiceId); // Debugging log

    // If doctorId is missing, show an error message
    if (!practiceId) {
        console.error("Practice ID not found in URL parameters.");
        alert("Hospital/Clinic ID not found!");
        return;
    }

    // Fetch doctor details from the backend API
    fetch(`http://localhost:8080/api/practices/${encodeURIComponent(practiceId)}`) // Ensure proper encoding
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch practice details");
            }
            return response.json();
        })
        .then(data => {
            // Populate the HTML elements with doctor data
            document.getElementById("practiceName").textContent = data.name || "N/A";
            document.getElementById("practiceAddress").textContent = data.address || "N/A";
            document.getElementById("practiceCity").textContent = data.city || "N/A";
            document.getElementById("practiceState").textContent = data.state || "N/A";
            document.getElementById("practiceWebsite").textContent = data.website|| "N/A";
        })
        .catch(error => {
            console.error("Error fetching doctor details:", error);
            document.getElementById("doctorDetails").innerHTML = "<p>Error loading doctor details.</p>";
        });

    // Back to Search button functionality
    document.getElementById("backToSearch").addEventListener("click", function () {
        window.location.href = "http://localhost:3000/src/main/resources/static/"; // Redirect back to the search page
    });
});
