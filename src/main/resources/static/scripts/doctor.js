document.addEventListener("DOMContentLoaded", function () {
    // Extract URL parameters
    console.log("doctor.js loaded", window.location.href);
    const urlParams = new URLSearchParams(window.location.search);
    const doctorId = urlParams.get("id"); // Retrieve the doctor ID from URL

    console.log("Extracted Doctor ID:", doctorId); // Debugging log

    // If doctorId is missing, show an error message
    if (!doctorId) {
        console.error("Doctor ID not found in URL parameters.");
        alert("Doctor ID not found!");
        return;
    }

    // Fetch doctor details from the backend API
    fetch(`http://localhost:8080/api/doctors/${encodeURIComponent(doctorId)}`) // Ensure proper encoding
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch doctor details");
            }
            return response.json();
        })
        .then(data => {
            // Populate the HTML elements with doctor data
            document.getElementById("doctorName").textContent = data.name || "N/A";
            document.getElementById("doctorExperience").textContent = data.experience || "N/A";
            document.getElementById("doctorQualifications").textContent = data.qualifications || "N/A";
            document.getElementById("doctorSpecialities").textContent = data.specialities?.join(", ") || "N/A";
            document.getElementById("doctorPractices").textContent = data.practices?.join(", ") || "N/A";
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
