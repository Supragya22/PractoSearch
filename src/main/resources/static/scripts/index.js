// Perform search when "Enter" key is pressed
document.getElementById("searchInput").addEventListener("keypress", function (event) {
    if (event.key === "Enter") {
        event.preventDefault(); // Prevent form submission (if any)
        performSearch(); // Call search function
    }
});

function performSearch() {
    let keyword = document.getElementById("searchInput").value.trim();
    let loadingSpinner = document.getElementById("loading");
    let resultsContainer = document.getElementById("resultsContainer");

    if (keyword === "") {
        alert("Please enter a search term.");
        return;
    }

    // Clear previous results and show loading indicator
    resultsContainer.innerHTML = "";
    loadingSpinner.style.display = "block";

    fetch(`http://localhost:8080/api/search?keyword=${keyword}`)
        .then(response => response.json())
        .then(data => {
            displayResults(data);
        })
        .catch(error => {
            console.error("Error fetching search results:", error);
            resultsContainer.innerHTML = "<p>Error fetching results. Please try again.</p>";
        })
        .finally(() => {
            // Hide loading spinner after results are loaded
            loadingSpinner.style.display = "none";
        });
}

function displayResults(results) {
    let resultsContainer = document.getElementById("resultsContainer");
    resultsContainer.innerHTML = ""; // Clear previous results

    if (results.length === 0) {
        resultsContainer.innerHTML = "<p>No results found.</p>";
        return;
    }

    results.forEach(result => {
        let div = document.createElement("div");
        div.classList.add("result-item");

        div.innerHTML = `
            <strong>Type:</strong> ${result.type} <br>
            <strong>Name:</strong> ${result.name} <br>
            <strong>Details:</strong> ${result.additionalInfo} <br>
            <hr>
        `;

        resultsContainer.appendChild(div);
    });
}
