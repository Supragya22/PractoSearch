function fetchDoctor() {
      let id = document.getElementById("doctorId").value;
      if (!id) {
          alert("Please enter a valid Doctor ID");
          return;
      }

      document.getElementById("loading").style.display = "block";
      fetch(`http://localhost:8080/api/doctors/${id}`)
          .then(response => {
              if (!response.ok) {
                  throw new Error("Doctor not found");
              }
              return response.json();
          })
          .then(data => {
              document.getElementById("doctorDetails").innerHTML = `
                  <p><strong>Name:</strong> ${data.name}</p>
                  <p><strong>Experience:</strong> ${data.experience} years</p>
                  <p><strong>Qualifications:</strong> ${data.qualifications}</p>
                  <p><strong>Specialities:</strong> ${data.specialities.join(", ")}</p>
                  <p><strong>Practices:</strong> ${data.practices.join(", ")}</p>
              `;
          })
          .catch(error => {
              document.getElementById("doctorDetails").innerHTML = `<p style="color: red;">${error.message}</p>`;
          })
          .finally(() => {
              document.getElementById("loading").style.display = "none";
          });
  }