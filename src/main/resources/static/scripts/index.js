//document.getElementById("searchInput").addEventListener("keypress", function (event) {
//    if (event.key === "Enter") {
//        event.preventDefault(); // Prevent form submission (if any)
//        performSearch(); // Call search function
//    }
//});
//
//function performSearch() {
//    let keyword = document.getElementById("searchInput").value.trim();
//    let loadingSpinner = document.getElementById("loading");
//    let resultsContainer = document.getElementById("resultsContainer");
//
//    if (keyword === "") {
//        alert("Please enter a search term.");
//        return;
//    }
//
//    // Clear previous results and show loading indicator
//    resultsContainer.innerHTML = "";
//    loadingSpinner.style.display = "block";
//
//    fetch(`http://localhost:8080/api/search?keyword=${keyword}`)
//        .then(response => response.json())
//        .then(data => {
//            displayResults(data);
//        })
//        .catch(error => {
//            console.error("Error fetching search results:", error);
//            resultsContainer.innerHTML = "<p>Error fetching results. Please try again.</p>";
//        })
//        .finally(() => {
//            // Hide loading spinner after results are loaded
//            loadingSpinner.style.display = "none";
//        });
//}
//
//function displayResults(results) {
//    let resultsContainer = document.getElementById("resultsContainer");
//    resultsContainer.innerHTML = ""; // Clear previous results
//
//    if (results.length === 0) {
//        resultsContainer.innerHTML = "<p>No results found.</p>";
//        return;
//    }
//
//    results.forEach(result => {
//        let div = document.createElement("div");
//        div.classList.add("result-item");
//
//        div.innerHTML = `
//            <strong>Type:</strong> ${result.type} <br>
//            <strong>Name:</strong> ${result.name} <br>
//            <strong>Details:</strong> ${result.additionalInfo} <br>
//            <hr>
//        `;
//
//        resultsContainer.appendChild(div);
//    });
//}
//

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

//function displayResults(filteredResults) {
//    let resultsContainer = document.getElementById("resultsContainer");
//    resultsContainer.innerHTML = ""; // Clear previous results
//
//    // Filter only Doctors and Practices (ignore Specialities)
//    //let filteredResults = results.filter(result => result.type === "Doctor" || result.type === "Practice");
//
//    if (filteredResults.length === 0) {
//        resultsContainer.innerHTML = "<p>No results found.</p>";
//        return;
//    }
//
//    filteredResults.forEach(result => {
//        let div = document.createElement("div");
//        div.classList.add("result-item");
//
//        let resultLink = document.createElement("a");
//        resultLink.href = result.type === "Doctor" ? `doctor.html?id=${result.id}` : `practice.html?id=${result.id}`;
//        resultLink.innerHTML = `<strong>${result.name}</strong>`;
//
//        div.appendChild(resultLink);
//        div.innerHTML += `<br><strong>Details:</strong> ${result.additionalInfo} <hr>`;
//        resultsContainer.appendChild(div);
//    });
//}

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
            ? `doctor.html?id=${result.id}`
            : `practice.html?id=${result.id}`;

        div.innerHTML = `
            <strong>Name:</strong> <a href="${targetUrl}">${result.name}</a> <br>
            ${specialitiesText}
            <strong>Details:</strong> ${result.additionalInfo} <br>
            <hr>
        `;

        resultsContainer.appendChild(div);
    });
}
