function fetchPractice() {
      let id = document.getElementById("practiceId").value;
      if (!id) {
          alert("Please enter a valid Practice ID");
          return;
      }

      document.getElementById("loading").style.display = "block";
      fetch(`http://localhost:8080/api/practices/${id}`)
          .then(response => {
              if (!response.ok) {
                  throw new Error("Practice not found");
              }
              return response.json();
          })
          .then(data => {
              document.getElementById("practiceDetails").innerHTML = `
                  <p><strong>Name:</strong> ${data.name}</p>
                  <p><strong>Address:</strong> ${data.address}</p>
                  <p><strong>City:</strong> ${data.city}</p>
                  <p><strong>State:</strong> ${data.state}</p>
                  <p><strong>Website:</strong> <a href="${data.website}" target="_blank">${data.website}</a></p>
              `;
          })
          .catch(error => {
              document.getElementById("practiceDetails").innerHTML = `<p style="color: red;">${error.message}</p>`;
          })
          .finally(() => {
              document.getElementById("loading").style.display = "none";
          });
  }