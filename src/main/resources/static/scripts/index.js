document.getElementById("searchInput").addEventListener("keypress", function (event) {
    if (event.key === "Enter") {
        event.preventDefault(); // Prevent form submission (if any)
        performSearch(); // Call search function
    }
});

document.getElementById("searchButton").addEventListener("click", performSearch); // Allow search button click

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

    if (!results || results.length === 0) {
        resultsContainer.innerHTML = "<p>No results found.</p>";
        return;
    }

    results.forEach(result => {
        let div = document.createElement("div");
        div.classList.add("result-item");

        let specialitiesText = result.specialities && result.specialities.length > 0
            ? `<strong>Specialities:</strong> ${result.specialities.join(", ")} <br>`
            : "";

            let targetUrl = result.type === "Doctor"
                        ? `doctor?id=${encodeURIComponent(result.id)}`
                        : `practice?id=${encodeURIComponent(result.id)}`;

            console.log("TARGET URL", targetUrl);

        div.innerHTML = `
            <img src="https://cdn.pixabay.com/photo/2018/11/13/22/01/avatar-3814081_1280.png" alt="Doctor Image">
            <div class="result-details">
                <strong>Name:</strong> <a href="${targetUrl}">${result.name}</a>
                ${specialitiesText ? `<p><strong>Specialities:</strong> ${result.specialities.join(", ")}</p>` : ""}
                <p><strong>Details:</strong> ${result.additionalInfo}</p>
            </div>
        `;


        resultsContainer.appendChild(div);
    });
}
