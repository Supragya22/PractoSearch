// API endpoint (replace with your actual API URL)
const API_URL = 'http://localhost:8080/api/doctors';

// Handle form submission for saving or modifying a doctor
document.getElementById('doctorForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const doctorData = {
        name: document.getElementById('name').value,
        experience: parseInt(document.getElementById('experience').value),
        qualifications: document.getElementById('qualifications').value,
        specialityIds: document.getElementById('specialityIds').value.split(',').map(id => parseInt(id.trim())),
        practiceIds: document.getElementById('practiceIds').value.split(',').map(id => parseInt(id.trim()))
    };

    // Check if we are saving a new doctor or modifying an existing one
    const doctorId = getUrlParameter('id');  // To get the doctor's ID from URL, if modifying

    if (doctorId) {
        // If doctorId exists, we modify the doctor
        updateDoctor(doctorId, doctorData);
    } else {
        // If no doctorId, we save a new doctor
        saveDoctor(doctorData);
    }
});

// Function to save a new doctor
function saveDoctor(doctorData) {
    fetch(API_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(doctorData)
    })
    .then(response => response.json())
    .then(data => {
        displayResponse('Doctor saved successfully!', 'success');
    })
    .catch(error => {
        console.error('Error:', error);
        displayResponse('Error saving doctor.', 'error');
    });
}

// Function to update an existing doctor
function updateDoctor(id, doctorData) {
    fetch(`${API_URL}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(doctorData)
    })
    .then(response => response.json())
    .then(data => {
        displayResponse('Doctor updated successfully!', 'success');
    })
    .catch(error => {
        console.error('Error:', error);
        displayResponse('Error updating doctor.', 'error');
    });
}

// Utility function to display the response message
function displayResponse(message, status) {
    const responseElement = document.getElementById('responseMessage');
    responseElement.textContent = message;
    responseElement.style.color = status === 'success' ? 'green' : 'red';
}

// Function to get the URL parameter (for modifying a doctor)
function getUrlParameter(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
}
