document.addEventListener("DOMContentLoaded", async function () {
    await populateCheckboxes("http://localhost:8080/api/specialities", "specialityContainer");
    await populateCheckboxes("http://localhost:8080/api/practices", "practiceContainer");
});

async function populateCheckboxes(apiUrl, containerId) {
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) throw new Error(`Error fetching data: ${response.status}`);

        const data = await response.json();
        const container = document.getElementById(containerId);
        container.innerHTML = ""; // Clear previous content if any

        data.forEach(item => {
            const checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.value = item.id;  // Assuming response has {id, name}
            checkbox.id = `${containerId}_${item.id}`;

            const label = document.createElement("label");
            label.htmlFor = checkbox.id;
            label.textContent = item.name;

            const div = document.createElement("div");
            div.appendChild(checkbox);
            div.appendChild(label);

            container.appendChild(div);
        });

    } catch (error) {
        console.error("Error fetching data:", error);
    }
}

// Handle form submission
document.getElementById("doctorForm").addEventListener("submit", async function (event) {
    event.preventDefault();

    const name = document.getElementById("name").value;
    const experience = document.getElementById("experience").value;
    const qualifications = document.getElementById("qualifications").value;

    // Get selected checkboxes for specialities & practices
    const specialityIds = Array.from(document.querySelectorAll("#specialityContainer input:checked"))
                              .map(checkbox => checkbox.value);

    const practiceIds = Array.from(document.querySelectorAll("#practiceContainer input:checked"))
                             .map(checkbox => checkbox.value);

    const doctorData = {
        name,
        experience,
        qualifications,
        specialityIds,
        practiceIds
    };

    console.log("Submitting Doctor Data:", doctorData);

    try {
        const response = await fetch("http://localhost:8080/api/doctors", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(doctorData)
        });

        if (!response.ok) throw new Error(`Error: ${response.status}`);

        document.getElementById("responseMessage").textContent = "Doctor saved successfully!";
    } catch (error) {
        console.error("Error saving doctor:", error);
        document.getElementById("responseMessage").textContent = "Error saving doctor.";
    }
});
