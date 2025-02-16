//document.addEventListener("DOMContentLoaded", function () {
//  // Fetch specialities, doctors when the page loads
//  fetchSpecialities();
//  fetchDoctors();
//
//  const practiceForm = document.getElementById("practiceForm");
//
//  // Handle form submission
//  practiceForm.addEventListener("submit", function (event) {
//    event.preventDefault();
//    savePractice();
//  });
//});
//
//// Fetch all specialities from the backend and populate the speciality dropdown
//function fetchSpecialities() {
//  fetch("/api/specialities")
//    .then(response => response.json())
//    .then(data => {
//      const specialitySelect = document.getElementById("speciality");
//      data.forEach(speciality => {
//        const option = document.createElement("option");
//        option.value = speciality.id;
//        option.text = speciality.name;
//        specialitySelect.appendChild(option);
//      });
//    })
//    .catch(error => console.error('Error fetching specialities:', error));
//}
//
//// Fetch all doctors from the backend and populate the doctor dropdown
//function fetchDoctors() {
//  fetch("/api/doctors")
//    .then(response => response.json())
//    .then(data => {
//      const doctorSelect = document.getElementById("doctor");
//      data.forEach(doctor => {
//        const option = document.createElement("option");
//        option.value = doctor.id;
//        option.text = doctor.name;
//        doctorSelect.appendChild(option);
//      });
//    })
//    .catch(error => console.error('Error fetching doctors:', error));
//}
//
//// Save practice by making an API call
//function savePractice() {
//  const formData = new FormData(document.getElementById("practiceForm"));
//  const practiceData = {
//    name: formData.get("name"),
//    address: formData.get("address"),
//    state: formData.get("state"),
//    city: formData.get("city"),
//    website: formData.get("website"),
//    specialityIds: Array.from(formData.getAll("specialityIds")),
//    doctorIds: Array.from(formData.getAll("doctorIds")),
//  };
//
//  fetch("/api/practices", {
//    method: "POST",
//    headers: {
//      "Content-Type": "application/json",
//    },
//    body: JSON.stringify(practiceData),
//  })
//    .then(response => response.json())
//    .then(data => {
//      alert("Practice added successfully!");
//      window.location.href = "admin-dashboard.html"; // Redirect to admin dashboard
//    })
//    .catch(error => console.error('Error saving practice:', error));
//}

document.addEventListener("DOMContentLoaded", function () {
    fetchSpecialities();
    fetchDoctors();

    document.getElementById("practiceForm").addEventListener("submit", function (event) {
        event.preventDefault(); // Prevent default form submission
        submitPracticeForm();
    });
});

// Fetch Specialities from API
function fetchSpecialities() {
    fetch("http://localhost:8080/api/specialities")
        .then(response => response.json())
        .then(data => {
            let container = document.getElementById("specialityContainer");
            container.innerHTML = ""; // Clear existing options
            data.forEach(speciality => {
                let checkbox = document.createElement("input");
                checkbox.type = "checkbox";
                checkbox.value = speciality.id;
                checkbox.id = `speciality-${speciality.id}`;
                checkbox.name = "specialities";

                let label = document.createElement("label");
                label.htmlFor = `speciality-${speciality.id}`;
                label.textContent = speciality.name;

                let div = document.createElement("div");
                div.appendChild(checkbox);
                div.appendChild(label);
                container.appendChild(div);
            });
        })
        .catch(error => console.error("Error fetching specialities:", error));
}

// Fetch Doctors from API
function fetchDoctors() {
    fetch("http://localhost:8080/api/doctors")
        .then(response => response.json())
        .then(data => {
            let container = document.getElementById("doctorContainer");
            container.innerHTML = ""; // Clear existing options
            data.forEach(doctor => {
                let checkbox = document.createElement("input");
                checkbox.type = "checkbox";
                checkbox.value = doctor.id;
                checkbox.id = `doctor-${doctor.id}`;
                checkbox.name = "doctors";

                let label = document.createElement("label");
                label.htmlFor = `doctor-${doctor.id}`;
                label.textContent = doctor.name;

                let div = document.createElement("div");
                div.appendChild(checkbox);
                div.appendChild(label);
                container.appendChild(div);
            });
        })
        .catch(error => console.error("Error fetching doctors:", error));
}

// Submit the form data
function submitPracticeForm() {
    let selectedSpecialities = Array.from(document.querySelectorAll("input[name='specialities']:checked")).map(cb => cb.value);
    let selectedDoctors = Array.from(document.querySelectorAll("input[name='doctors']:checked")).map(cb => cb.value);

    let formData = {
        name: document.getElementById("name").value,
        address: document.getElementById("address").value,
        state: document.getElementById("state").value,
        city: document.getElementById("city").value,
        website: document.getElementById("website").value,
        specialityIds: selectedSpecialities,
        doctorIds: selectedDoctors
    };

    fetch("http://localhost:8080/api/practices", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(formData)
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById("responseMessage").textContent = "Practice saved successfully!";
        console.log("Success:", data);
    })
    .catch(error => {
        document.getElementById("responseMessage").textContent = "Error saving practice.";
        console.error("Error:", error);
    });
}

