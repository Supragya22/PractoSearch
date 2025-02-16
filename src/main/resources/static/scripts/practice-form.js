document.addEventListener("DOMContentLoaded", function () {
  // Fetch specialities, doctors when the page loads
  fetchSpecialities();
  fetchDoctors();

  const practiceForm = document.getElementById("practiceForm");

  // Handle form submission
  practiceForm.addEventListener("submit", function (event) {
    event.preventDefault();
    savePractice();
  });
});

// Fetch all specialities from the backend and populate the speciality dropdown
function fetchSpecialities() {
  fetch("/api/specialities")
    .then(response => response.json())
    .then(data => {
      const specialitySelect = document.getElementById("speciality");
      data.forEach(speciality => {
        const option = document.createElement("option");
        option.value = speciality.id;
        option.text = speciality.name;
        specialitySelect.appendChild(option);
      });
    })
    .catch(error => console.error('Error fetching specialities:', error));
}

// Fetch all doctors from the backend and populate the doctor dropdown
function fetchDoctors() {
  fetch("/api/doctors")
    .then(response => response.json())
    .then(data => {
      const doctorSelect = document.getElementById("doctor");
      data.forEach(doctor => {
        const option = document.createElement("option");
        option.value = doctor.id;
        option.text = doctor.name;
        doctorSelect.appendChild(option);
      });
    })
    .catch(error => console.error('Error fetching doctors:', error));
}

// Save practice by making an API call
function savePractice() {
  const formData = new FormData(document.getElementById("practiceForm"));
  const practiceData = {
    name: formData.get("name"),
    address: formData.get("address"),
    state: formData.get("state"),
    city: formData.get("city"),
    website: formData.get("website"),
    specialityIds: Array.from(formData.getAll("specialityIds")),
    doctorIds: Array.from(formData.getAll("doctorIds")),
  };

  fetch("/api/practices", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(practiceData),
  })
    .then(response => response.json())
    .then(data => {
      alert("Practice added successfully!");
      window.location.href = "admin-dashboard.html"; // Redirect to admin dashboard
    })
    .catch(error => console.error('Error saving practice:', error));
}
